import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

public class DetailInfo extends JDialog{

   protected static final String LS = System.getProperty("line.separator");      //html 문서 개행 명령어 

   protected JLabel menuBarDetail = new JLabel(ImageData.menuBarDetail);
   protected JButton detailExit = new JButton(ImageData.exitButtonBasic);
   protected int mouseX; int mouseY;
   
   private AccidentCase detailCase = new AccidentCase();
   private Police police = new Police();
   
   private String lat;
   private String lng;

   private JPanel primary;
   private JPanel boxPanel;
   private JLabel yearlbl;
   private JLabel monthlbl;
   private JButton searchDateBtn;
   
   private Javascript script = new Javascript();
   private JPanel infoPanel;
   
   private JLabel cscode;         //사고 번호
   private JLabel province;        //시,도
   private JLabel town;            //군,구
   private JLabel year;            //년도
   private JLabel policeno;        //경찰번호
   private JLabel casulity;        //사상자 = 사망자 + 부상자
   private JLabel dead;            //사망자
   private JLabel injured;         //부상자 
   private JLabel actype;          //사고유형 (차대차, 차대사람, 차량단독)
   private JLabel latitude;        //위도
   private JLabel longitude;       //경도 
   
   private JPanel policePanel;
   private JLabel policeNo;   //경찰 번호
   private JLabel polname;    //경찰 이름
   private JLabel rank;       //경찰 계급
   private JLabel depart;     //소속 부서
   private JLabel dpcode;     //소속 부서 코드
   
   private JPanel mapPanel;
   
   public DetailInfo(AccidentCase tempCase, Police tempPolice)
   {
       setSize(940,540);
       setLayout(null);
       setResizable(false);
       setUndecorated(true);
       
	  JPanel upPanel = new JPanel();
	  upPanel.setBounds(0,0,940,40);
	  upPanel.setLayout(null);
	 
	  detailExit.setBounds(900, 5, 30, 30);
	  detailExit.setBorderPainted(false);
	  detailExit.setContentAreaFilled(false);
	  detailExit.setFocusPainted(false);
	  detailExit.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				detailExit.setIcon(ImageData.exitButtonEntered);
			}
			@Override
			public void mouseExited(MouseEvent e)
			{
				detailExit.setIcon(ImageData.exitButtonBasic);
			}
			@Override
			public void mouseReleased(MouseEvent e)
			{
				dispose();
			}
		});
	  upPanel.add(detailExit);
	  
	  menuBarDetail.setBounds(0,0,940, 40);
	  menuBarDetail.addMouseListener(new MouseAdapter()
		{	@Override
				public void mousePressed(MouseEvent e)							//메뉴바를 잡았을 떄 절대좌표를 받아옴
				{
					mouseX = e.getX();
					mouseY = e.getY();
				}
		});
	  menuBarDetail.addMouseMotionListener(new MouseMotionAdapter()	
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
			}
		});
	  upPanel.add(menuBarDetail);
	  add(upPanel);
	 
      detailCase = tempCase;
      police = tempPolice;
      
      this.lat = lat;
      this.lng = lng;
      
      primary = new JPanel();
      primary.setLayout(null);
      primary.setBounds(0,40,940,500);
      
      //AccidentCase Panel-----------------------------------------------------------------------------
      infoPanel = new JPanel();
      infoPanel.setLayout(null);
      infoPanel.setBounds(0,0,200,300);
      primary.add(infoPanel);
      
      cscode = new JLabel();
      cscode.setText("사고번호: " +detailCase.getCscode());
      cscode.setBounds(10,0,200,300/13);
      infoPanel.add(cscode);
      
      province = new JLabel();
      province.setText("시/도: " + detailCase.getProvince());
      province.setBounds(10, 300/13, 200, 300/13);
      infoPanel.add(province);
      
      town = new JLabel();
      town.setText("구/군: " + detailCase.getTown());
      town.setBounds(10, 300/13*2, 200, 300/13);
      infoPanel.add(town);
      
      year = new JLabel();
      year.setText("발생 날짜: " + detailCase.getYear() + "년 " + detailCase.getMonth() + "월 " + detailCase.getDay() + "일");
      year.setBounds(10, 300/13*3, 200, 300/13);
      infoPanel.add(year);
      
      policeno = new JLabel();
      policeno.setText("담당 경찰번호: "+ detailCase.getPoliceno());
      policeno.setBounds(10, 300/13*4, 200, 300/13);
      infoPanel.add(policeno);
      
      casulity = new JLabel();
      casulity.setText("사상자수: " + detailCase.getCasulity());
      casulity.setBounds(10,300/13*5, 200, 300/13);
      infoPanel.add(casulity);
      
      dead = new JLabel();
      dead.setText("사망자수: " + detailCase.getDead());
      dead.setBounds(10,300/13*6, 200, 300/13);
      infoPanel.add(dead);
      
      injured = new JLabel();
      injured.setText("부상자수: " + detailCase.getInjured());
      injured.setBounds(10, 300/13*7, 200, 300/13);
      infoPanel.add(injured);
      
      actype = new JLabel();
      actype.setText("사고유형: " + detailCase.getActype());
      actype.setBounds(10, 300/13*8,  200, 300/13);
      infoPanel.add(actype);
      
      latitude = new JLabel();
      latitude.setText("위도: " + detailCase.getLatitude());
      latitude.setBounds(10,300/13*9, 200,300/13);
      infoPanel.add(latitude);
      
      longitude = new JLabel();
      longitude.setText("경도: " + detailCase.getLongitude());
      longitude.setBounds(10, 300/13*10, 200, 300/13);
      infoPanel.add(longitude);
      
      //경찰 패널---------------------------------------------------------------------------------
      policePanel = new JPanel();
      policePanel.setLayout(null);
      policePanel.setBounds(0,300,200,200);
      
      policeNo = new JLabel();   //경찰 번호
      policeNo.setText("담당 경찰번호: " + police.getPoliceno());
      policeNo.setBounds(10,0, 200,300/13);
      policePanel.add(policeNo);
      
      polname = new JLabel();    //경찰 이름
      polname.setText("담당경찰관: " + police.getPolname());
      polname.setBounds(10,300/13, 200,300/13);
      policePanel.add(polname);
      
      rank = new JLabel();       //경찰 계급
      rank.setText("계급: " + police.getRank());
      rank.setBounds(10, 300/13*2, 200,300/13);
      policePanel.add(rank);
      
      depart = new JLabel();     //소속 부서
      depart.setText("소속부서: " + police.getDepart());
      depart.setBounds(10,300/13*3, 200,300/13);
      policePanel.add(depart);
      
      dpcode = new JLabel();     //소속 부서 코드
      dpcode.setText("소속부서코드: "  + police.getDpcode());
      dpcode.setBounds(10,300/13*4, 200,300/13);
      policePanel.add(dpcode);
      primary.add(policePanel);
      
      //지도 관련---------------------------------------------------------------------
      
      
      NativeInterface.open();         
       SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createContent();
            add(primary);
           
            repaint();
         }
       });
       setVisible(true);
      
   }
   
   public void createContent() {
          
         JPanel webBrowserPanel = new JPanel(new BorderLayout());               //지도를 출력할 패널 생성 및 초기화
         webBrowserPanel.setBounds(200,10,700,430);                           //패널 크기 설정
      
         final JWebBrowser webBrowser = new JWebBrowser();                     //웹브라우져 객체 생성
          webBrowser.setBounds(0,0,680,400);
         webBrowser.setBarsVisible(false);
          webBrowser.setStatusBarVisible(true);
          final String htmlContent =
            "<html>" + LS +
            "<head>" + LS +
            "<style>\r\n"  + LS +
            "body {background-image: url(\"https://i.ytimg.com/vi/7Uvmww35oxw/maxresdefault.jpg\");\r\n"  + LS +
            "      background-repeat: no-repeat;\r\n"  + LS +
            "      background-attachment: fixed;\r\n"  + LS +
            "      background-position: right bottom;}\r\n"  + LS +
            "table, td {background-color: transparent;} \r\n"  + LS + 
            "</style>" + LS +
            "   </head>" + LS +
            "  <body>" + LS +
            "   <div id = \"map\"> <font size=30, color = white> <center> Loading... </center> </font> </div>" + LS +
            "  </body>" + LS +
            "</html>";
          
          webBrowser.setHTMLContent(htmlContent);
          webBrowser.addWebBrowserListener(new WebBrowserListener(){
            public void commandReceived(WebBrowserCommandEvent arg0) {
               
            }
            public void loadingProgressChanged(WebBrowserEvent arg0) {
            }
            public void locationChangeCanceled(WebBrowserNavigationEvent arg0) {
            }
            public void locationChanged(WebBrowserNavigationEvent arg0) {                  //웹브라우져 현재 위치가 바뀌었을 때
               
               script.setDetailMain(Double.toString(detailCase.getLatitude()), Double.toString(detailCase.getLongitude()));
               webBrowser.executeJavascript(script.getScript());   
            }
            public void locationChanging(WebBrowserNavigationEvent arg0) {
            }
            public void statusChanged(WebBrowserEvent arg0) {
            }
            public void titleChanged(WebBrowserEvent arg0) {
            }
            public void windowClosing(WebBrowserEvent arg0) {
            }
            public void windowOpening(WebBrowserWindowOpeningEvent arg0) {
            }
            public void windowWillOpen(WebBrowserWindowWillOpenEvent arg0) {
            }
          });            
          webBrowserPanel.add(webBrowser);                                       //웹브라우져를 지도 패널(webBrowserPanel)에 붙이기
          primary.add(webBrowserPanel);                                          //subPanel 에 지도 패널 붙이기
         
   }

   public void paint(Graphics g)
   {
      super.paintComponents(g);
      this.repaint();
   }
}