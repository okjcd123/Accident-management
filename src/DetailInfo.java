/**
 * DetailInfo.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */
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

   protected JLabel menuBarDetail = new JLabel(ImageData.menuBarDetail);		//메뉴바
   protected JButton detailExit = new JButton(ImageData.exitButtonBasic);		//종료 버튼
   protected int mouseX; int mouseY;											//마우스 좌표값 저장 예정 변수
   
   private AccidentCase detailCase = new AccidentCase();						//생성자로 입력받은 case 입력 받을 예정
   
   private String lat;							//위도
   private String lng;							//경도

   private JPanel primary;						//primary 패널 생성
   
   private Javascript script = new Javascript(); //Javascript 객체 생성
   private JPanel infoPanel;		//구체적인 정보를 담은 패널 생성
   
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
   
   public DetailInfo(AccidentCase tempCase)
   {
	   //DetailInfo Dialog 기본 옵션 설정
       setSize(930,505);
       setLayout(null);
       setResizable(false);
       setUndecorated(true);
       
	  JPanel upPanel = new JPanel();					//메뉴바를 붙일 패널 생성 및 선언
	  upPanel.setBounds(0,0,940,40);
	  upPanel.setLayout(null);
	 
	  detailExit.setBounds(900, 5, 30, 30);				//종료 버튼 설정 및 메뉴바 패널에 붙임.
	  detailExit.setBorderPainted(false);
	  detailExit.setContentAreaFilled(false);
	  detailExit.setFocusPainted(false);
	  upPanel.add(detailExit);
	  
	  menuBarDetail.setBounds(0,0,940, 40);				//메뉴바를 upPanel에 붙임.
	  upPanel.add(menuBarDetail);
	  add(upPanel);
	 
      detailCase = tempCase;							//parameter 생성자로 받은 사고Case 를 detailCase 에 붙임. 
      
      this.lat = lat;									//parameter 생성자로 받은 사고 위도를 lat 에 붙임
      this.lng = lng;									//parameter 생성자로 받은 사고 경도를 lng 에 붙임.
      
      primary = new JPanel();							//primary 패널 선언
      primary.setLayout(null);
      primary.setBounds(0,40,940,500);
      
      //세부정보 패널 관련------------------------------------------------------------------------------------------
      infoPanel = new JPanel();							//primary 패널에 infoPanel 붙이기
      infoPanel.setLayout(null);
      infoPanel.setBounds(0,10,200,300);
      primary.add(infoPanel);
      
      cscode = new JLabel();								//사고번호 라벨 설정
      cscode.setText("사고번호: " +detailCase.getCscode());
      cscode.setBounds(10,0,210,300/13);
      infoPanel.add(cscode);
      
      province = new JLabel();								//시/도 라벨 설정
      province.setText("시/도: " + detailCase.getProvince());
      province.setBounds(10, 300/13 + 10, 200, 300/13);
      infoPanel.add(province);
      
      town = new JLabel();									//군/구 라벨 설정
      town.setText("구/군: " + detailCase.getTown());
      town.setBounds(10, 300/13*2 + 10, 200, 300/13);
      infoPanel.add(town);
      
      year = new JLabel();									//발생 년 라벨 설정
      year.setText("발생 날짜: " + detailCase.getYear() + "년 " + detailCase.getMonth() + "월 " + detailCase.getDay() + "일");
      year.setBounds(10, 300/13*3 + 10, 200, 300/13);
      infoPanel.add(year);
      
      casulity = new JLabel();								//발생 월 라벨 설정
      casulity.setText("사상자수: " + detailCase.getCasulity());
      casulity.setBounds(10,300/13*5 + 10, 200, 300/13);
      infoPanel.add(casulity);
      
      dead = new JLabel();									//사망자수 라벨 설정
      dead.setText("사망자수: " + detailCase.getDead());
      dead.setBounds(10,300/13*6 + 10, 200, 300/13);
      infoPanel.add(dead);
      
      injured = new JLabel();								//사상자수  라벨설정
      injured.setText("부상자수: " + detailCase.getInjured());
      injured.setBounds(10, 300/13*7 + 10, 200, 300/13);
      infoPanel.add(injured);
      
      actype = new JLabel();								//사고 유형 라벨 설정
      actype.setText("사고유형: " + detailCase.getActype());
      actype.setBounds(10, 300/13*8 + 10,  200, 300/13);
      infoPanel.add(actype);
      
      latitude = new JLabel();								//위도 라벨 설정
      latitude.setText("위도: " + detailCase.getLatitude());
      latitude.setBounds(10,300/13*9 + 10, 200,300/13);
      infoPanel.add(latitude);
      
      longitude = new JLabel();								//경도 라벨 설정
      longitude.setText("경도: " + detailCase.getLongitude());
      longitude.setBounds(10, 300/13*10 +10, 200, 300/13);
      infoPanel.add(longitude);
      
      //지도 관련---------------------------------------------------------------------
      NativeInterface.open();    							//Native Interface 시작 준비     
       SwingUtilities.invokeLater(new Runnable() {			//쓰래드 형태로 시작 
         public void run() {
            createContent();
            add(primary);
           
            repaint();
         }
       });
       setVisible(true);
      
   }
   
   public void createContent() {
          
         JPanel webBrowserPanel = new JPanel(new BorderLayout());              //지도를 출력할 패널 생성 및 초기화
         webBrowserPanel.setBounds(200,10,700,430);                           //패널 크기 설정
      
         final JWebBrowser webBrowser = new JWebBrowser();                    //웹브라우져 객체 생성 및 선언 기본 옵션 초기화
         webBrowser.setBounds(0,0,680,400);
         webBrowser.setBarsVisible(false);
         webBrowser.setStatusBarVisible(true);
          final String htmlContent =
            "<html>" + LS +
            "<head>" + LS +
            "<style>\r\n"  + LS +
            "body {background-image: url(\"https://i.ytimg.com/vi/7Uvmww35oxw/maxresdefault.jpg\");\r\n"  + LS +	//HTML 기본 이미지 설정
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
          
          webBrowser.setHTMLContent(htmlContent);									//웹브라우져 객체에 HTML 코드 붙이기
          webBrowser.addWebBrowserListener(new WebBrowserListener(){				//웹브라우져 이벤트 Listener 생성
            public void commandReceived(WebBrowserCommandEvent arg0) {
               
            }
            public void loadingProgressChanged(WebBrowserEvent arg0) {
            }
            public void locationChangeCanceled(WebBrowserNavigationEvent arg0) {
            }
            public void locationChanged(WebBrowserNavigationEvent arg0) {                  //웹브라우져 현재 위치가 바뀌었을 때
               
               script.setDetailMain(Double.toString(detailCase.getLatitude()), Double.toString(detailCase.getLongitude()));	//스트립트 코드 초기화
               webBrowser.executeJavascript(script.getScript());   							//설정된 script 코드 가져와 실행
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

   public void addMouseDetailMenuBarListener(MouseAdapter mouse)				//메뉴바 이벤트 관련 메소드
   {
	   menuBarDetail.addMouseListener(mouse);
   }
   public void addMouseDetailMenuBarMotionListener(MouseMotionAdapter mouse)	//메뉴바 드래그 이벤트 관련 메소드
   {
	   menuBarDetail.addMouseMotionListener(mouse);
   }
   public void addMouseDetailExitListener(MouseAdapter mouse)					//종료 버튼 관련 이벤트 메소드ㅡ
   {
	   detailExit.addMouseListener(mouse);
   }
   
   public void paint(Graphics g)												//Google 지도를 지속적으로 초기화해주기 위한 paint함수 
   {
      super.paintComponents(g);
      this.repaint();
   }
}