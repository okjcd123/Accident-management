import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class AccidentAnalysis extends JDialog{

	protected static final String LS = System.getProperty("line.separator");		//html 문서 개행 명령어 
	private	String[] year = {"년도", "2014","2015","2016","2017", "2018"};
	
	private ArrayList <AccidentCase> accList = new ArrayList<AccidentCase>();
	
	protected JLabel menuBarAnalysis = new JLabel(ImageData.menuBarAnalysis);
	protected JButton analysisExit = new JButton(ImageData.exitButtonBasic);
	protected int mouseX; int mouseY;
	
	private JPanel primary;
	private JPanel boxPanel;
	private JLabel yearlbl;
	
	private JComboBox yearBox;
	private JButton searchDateBtn;
	
	private JButton parsingButton;
	
	private Javascript script = new Javascript();
	private JPanel infoPanel;
	
	public AccidentAnalysis()
	{
		setUndecorated(true);
		setSize(1300,640);
  	  	setLayout(null);
  	  	setResizable(false);
  	  
	    JPanel upPanel = new JPanel();
	    upPanel.setBounds(0,0,1300,40);
	    upPanel.setLayout(null);
      
	    analysisExit.setBounds(1260, 5, 30, 30);
	    analysisExit.setBorderPainted(false);
	    analysisExit.setContentAreaFilled(false);
	    analysisExit.setFocusPainted(false);
	    analysisExit.addMouseListener(new MouseAdapter(){
					
					@Override
					public void mouseEntered(MouseEvent e)
					{
						analysisExit.setIcon(ImageData.exitButtonEntered);
					}
					@Override
					public void mouseExited(MouseEvent e)
					{
						analysisExit.setIcon(ImageData.exitButtonBasic);
					}
					@Override
					public void mouseReleased(MouseEvent e)
					{
						dispose();
					}
				});
	    upPanel.add(analysisExit);
	    
	    menuBarAnalysis.setBounds(0,0,1300, 40);
	    menuBarAnalysis.addMouseListener(new MouseAdapter()
		{	@Override
				public void mousePressed(MouseEvent e)							//메뉴바를 잡았을 떄 절대좌표를 받아옴
				{
					mouseX = e.getX();
					mouseY = e.getY();
				}
		});
	    menuBarAnalysis.addMouseMotionListener(new MouseMotionAdapter()	
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
			}
		});
	    upPanel.add(menuBarAnalysis);
	    add(upPanel);
      
		primary = new JPanel();
		primary.setBackground(Color.black);
		primary.setLayout(null);
		primary.setBounds(0,40,1300,600);
		
		boxPanel = new JPanel();
		boxPanel.setLayout(null);
		boxPanel.setBounds(0,0,650,50);
		
		yearlbl = new JLabel("년", JLabel.CENTER);
		yearlbl.setBounds(5,10,40,40);
		boxPanel.add(yearlbl);
		
		yearBox = new JComboBox(year);
		yearBox.setBounds(50,10,250,40);
		boxPanel.add(yearBox);
			
		searchDateBtn = new JButton("검색");
		searchDateBtn.setBounds(320, 10, 100,40);
		boxPanel.add(searchDateBtn);
		
		parsingButton = new JButton("System Update");
		parsingButton.setBounds(430,10,210,40);
		boxPanel.add(parsingButton);
	
		primary.add(boxPanel);
		
		infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBackground(Color.blue);
		infoPanel.setBounds(650,0,650,600);
		primary.add(infoPanel);
		
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
		
			JPanel webBrowserPanel = new JPanel(new BorderLayout());					//지도를 출력할 패널 생성 및 초기화
			webBrowserPanel.setBounds(10,60,630,480);									//패널 크기 설정
			  
		    final JWebBrowser webBrowser = new JWebBrowser();							//웹브라우져 객체 생성
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
		      "	</head>" + LS +
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
				public void locationChanged(WebBrowserNavigationEvent arg0) {						//웹브라우져 현재 위치가 바뀌었을 때
					script.setMain();
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
		    webBrowserPanel.add(webBrowser);													//웹브라우져를 지도 패널(webBrowserPanel)에 붙이기
		    primary.add(webBrowserPanel);														//subPanel 에 지도 패널 붙이기
		   
	}

	private class YearSearch implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object obj = e.getSource();
			
			if(obj == searchDateBtn)
			{
				//script.setMarker();
			}
			else if(obj == parsingButton)
			{
				new ParsingAccidentData();
			}
		}
	}
	
	public void paint(Graphics g)
	{
		super.paintComponents(g);
		this.repaint();
	}
	

}