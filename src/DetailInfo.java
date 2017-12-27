import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

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

public class DetailInfo extends JDialog{

	protected static final String LS = System.getProperty("line.separator");		//html 문서 개행 명령어 

	private JPanel primary;
	
	private JPanel boxPanel;
	private JLabel yearlbl;
	private JLabel monthlbl;
	private JButton searchDateBtn;
	private Javascript script = new Javascript();
	
	private JPanel infoPanel;
	
	public DetailInfo()
	{
		primary = new JPanel();
		primary.setLayout(null);
		primary.setBounds(0,0,1000,600);
		
		
		infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBackground(Color.blue);
		infoPanel.setBounds(650,0,350,600);
		primary.add(infoPanel);
		
		NativeInterface.open();			
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	    	  setSize(1000,600);
	    	  setLayout(null);
	    	  setResizable(false);
	  		
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

	public void paint(Graphics g)
	{
		super.paintComponents(g);
		this.repaint();
	}
}
