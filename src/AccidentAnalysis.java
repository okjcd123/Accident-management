/**
 * AccidentAnalysis.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

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
	private	String[] year = {"년도","2012","2013","2014","2015","2016"};
	protected String[] month = {"월","01","02","03","04","05","06","07","08","09","10","11","12"};
	
	private ArrayList <AccidentCase> accList = new ArrayList<AccidentCase>();		//AccidentCase 들을 모아 반환할 예정
	
	protected JLabel menuBarAnalysis = new JLabel(ImageData.menuBarAnalysis);		//메뉴바 객체 생성 및 선언
	protected JButton analysisExit = new JButton(ImageData.exitButtonBasic);		//종료 버튼 객체 생성 및선언
	protected int mouseX; int mouseY;												//마우스 위치 변수 선언
	
	private JPanel primary;															//primary 패널 생성
	private JPanel boxPanel;														//box 패널 생성
	
	private JComboBox yearBox;														//년 콤보박스 생성
	private JComboBox monthBox;														//월 콤보박스 생성
	
	private JButton showChartBtn = new JButton(ImageData.viewChartBasic);			//차트보기 버튼 생성 및 선언
	private JButton parsingButton = new JButton(ImageData.parsingBasic);			//파싱 버튼 생성 및 선언

	private JButton showMapBtn = new JButton(ImageData.viewMapBasic);				//지도보기 보튼 생성 및 선언
	private JButton deleteButton = new JButton(ImageData.resetBasic);				//초기화 버튼 생성 및 선언

	
	private Javascript script = new Javascript();									//자바스크립트 객체 생성
	private JPanel infoPanel;

	// 차트
	private JFreeChart chart;														//차트 객체 생성
	private ChartPanel chartPanel;													//차트 패널 생성
	
    protected String [] header = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월","11월","12월","평균"};
	
	private JLabel seoul;															//서울 라벨 생성
	private JLabel kyeongki;														//경기 라벨 생성
	private JLabel incheon;															//인천 라벨 생성
	
	private JScrollPane scroll;														//스크롤 객체 생성
	DefaultTableModel basic =new DefaultTableModel();								//basic table 객체 생성 및 선언
	protected JTable table = new JTable(basic)										//table 생성
	{
	       public boolean isCellEditable(int row, int column)						//표 편집 불가 설정
	       {
	          return false;
	       }
	};
	final JWebBrowser webBrowser = new JWebBrowser();							//웹브라우져 객체 생성
  
	public AccidentAnalysis()
	{
		setUndecorated(true);													//기본 메뉴바삭제
		setSize(1300,600);														//사이즈 설정
  	  	setLayout(null);
  	  	setResizable(false);
  	  	setAutoRequestFocus(true);
  	  	
	    JPanel upPanel = new JPanel();											//상단 패널 생성및 선언
	    upPanel.setBounds(0,0,1300,40);
	    upPanel.setLayout(null);
      
	    //종료 버튼 옵션 설정------------------------------------------------------------------------------------
	    analysisExit.setBounds(1260, 5, 30, 30);
	    analysisExit.setBorderPainted(false);
	    analysisExit.setContentAreaFilled(false);
	    analysisExit.setFocusPainted(false);
	    analysisExit.addMouseListener(new MouseAdapter(){
					
					@Override
					public void mouseEntered(MouseEvent e)
					{
						analysisExit.setIcon(ImageData.exitButtonEntered);		//마우스가 종료 버튼 위에 있을 떄 이미지 바뀜
					}
					@Override
					public void mouseExited(MouseEvent e)
					{
						analysisExit.setIcon(ImageData.exitButtonBasic);		//마우스가 종료 버튼 밖으로 나갔을 떄 이미지 바뀜
					}
					@Override
					public void mouseReleased(MouseEvent e)
					{
						//마우스가 종료 버튼을 눌렀을떄 화면 종료 및 나머지 main frame 버튼 활성화
						dispose();
						AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
						AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
						AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
						AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
						AppManager.CreateInstance().getAppController().analysisOpenedFlag = false;
					}
				});
	    upPanel.add(analysisExit);
	    
	    //메뉴바 관련--------------------------------------------------------------------------------------------
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
				//마우스를 드래그할 떄 마다 dialog 의 위치를 지속적으로 바꿔줌
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
			}
		});
	    upPanel.add(menuBarAnalysis);
	    add(upPanel);
      
	    //primary 패널 내부 옵션--------------------------------------------------------------------------------------
		primary = new JPanel();	
		primary.setLayout(null);
		primary.setBounds(0,40,1300,600);
		
		boxPanel = new JPanel();												//좌측 상단 패널 배치
		boxPanel.setLayout(null);
		boxPanel.setBounds(0,0,650,50);
		
		yearBox = new JComboBox(year);											//년 콤보박스 선언 및 옵션 설정
		yearBox.setBounds(10,10,90,40);
		boxPanel.add(yearBox);
		
		showChartBtn.setBounds(110, 10, 100,40);								//차트보기 버튼 선언 및 옵션 설정
		boxPanel.add(showChartBtn);
		
		monthBox = new JComboBox(month);										//월 콤보박스 선언 및 옵션 설정
		monthBox.setBounds(220,10, 90,40);
		boxPanel.add(monthBox);
		
		showMapBtn.setBounds(320, 10, 100,40);									//지도보기 버튼 옵션 설정
		boxPanel.add(showMapBtn);

		parsingButton.setBounds(430,10,100,40);									//파싱 버튼 옵션 설정
		boxPanel.add(parsingButton);
		
		deleteButton.setBounds(540,10,100,40);									//초기화 버튼 옵션 설정
		boxPanel.add(deleteButton);
	
		primary.add(boxPanel);
		 
		// Table
	    for(int i =0; i<header.length; i++)										//Table 컬럼 추가
	    {
	         basic.addColumn(header[i]);   
	    }

	    scroll = new JScrollPane();												//table을 scroll에 붙이기
	    scroll.setViewportView(table);
	    scroll.setBounds(690,450,600,100);
	    
	    primary.add(scroll);													//primary panel 에 스크롤 붙이기
	    
		// 차트 관련-----------------------------------------------------------------------------------------
		chart = new JFreeChart(new CategoryPlot());								//차트 객체 선언
  	  	chartPanel = new ChartPanel(chart);										//차트 패널 선언
  	  	
	    chartPanel.setChart(getChart("All"));									//파트 세팅
	    chartPanel.setLayout(null);
	    chartPanel.setBounds(650,30,640,400);
	    primary.add(chartPanel);	
	  
	    seoul = new JLabel("서울", JLabel.CENTER);								//서울 라벨 관련 옵션 설정 및 붙이기
	    seoul.setBounds(650,470,40,20);
	    seoul.setFont(new Font("Gothic", Font.BOLD, 11));
	    primary.add(seoul);
	    
	    kyeongki = new JLabel("경기",JLabel.CENTER);								//경기 라벨 관련 옵션 설정 및 붙이기
		kyeongki.setBounds(650,487,40,20);
		kyeongki.setFont(new Font("Gothic", Font.BOLD, 11));
		primary.add(kyeongki);
		
	    incheon = new JLabel("인천",JLabel.CENTER);								//인천 라벨 관련 옵션 설정 및 붙이기
	    incheon.setBounds(650,504,40,20);
	    incheon.setFont(new Font("Gothic", Font.BOLD, 11));
	    primary.add(incheon);
	    
		NativeInterface.open();													//Native 브라우져 오픈
	    SwingUtilities.invokeLater(new Runnable() {								//Swing 환경 내 쓰레드 지속 작동
	      public void run() {
	    	  createContent();													//CreateContent 메소드 실행
	    	  add(primary);
	    	  repaint();
	      }
	    });
	    
	    showChartBtn.addActionListener(new YearSearch());   					//버튼 이벤트 추가
	    showChartBtn.addMouseListener(new MouseAdapter()						//mouse이벤트 추가						
	    		{
			    	public void mouseEntered(MouseEvent e)
					{
			    		showChartBtn.setIcon(ImageData.viewChartEntered);		//차트보기 버튼 위에 마우스가 입장하면 아이콘이 바뀜
					}
					public void mouseExited(MouseEvent e)
					{
						showChartBtn.setIcon(ImageData.viewChartBasic);			//차트보기 버튼 위에 마우스 나오면 아이콘이 바뀜
					}
	    		});
	    
	    showMapBtn.addActionListener(new YearSearch());							//지도보기 버튼 이벤트 추가
	    showMapBtn.addMouseListener(new MouseAdapter()							//mouse 이벤트 추가
		{
	    	public void mouseEntered(MouseEvent e)
			{
	    		showMapBtn.setIcon(ImageData.viewMapEntered);					//지도보기 버튼 위에 마우스가 입장하면 아이콘이 바뀜
			}
			public void mouseExited(MouseEvent e)
			{
				showMapBtn.setIcon(ImageData.viewMapBasic);						//지도보기 버튼 위에 마우스가 나오면 아이콘이 바뀜
			}
		});	    
	    
	    parsingButton.addActionListener(new YearSearch()); 						//파싱 버튼 이벤트 추가
	    parsingButton.addMouseListener(new MouseAdapter()						//mouse 이벤트 추가
		{
	    	public void mouseEntered(MouseEvent e)
			{
	    		parsingButton.setIcon(ImageData.parsingEntered);				//파싱 버튼 위에 마우스가 입장하면 아이콘이 바뀜
			}
			public void mouseExited(MouseEvent e)
			{
				parsingButton.setIcon(ImageData.parsingBasic);					//파싱 버튼 위에 마우스가 나오면 아이콘이 바뀜
			}
		});
	    
	    deleteButton.addActionListener(new YearSearch()); 						//초기화 버튼 이벤트 추가
	    deleteButton.addMouseListener(new MouseAdapter()						//초기화 mouse 이벤트 추가 
		{
	    	public void mouseEntered(MouseEvent e)
			{
	    		deleteButton.setIcon(ImageData.resetEntered);					//초기화 버튼 위에 마우스가 입장하면 아이콘이 바뀜
			}
			public void mouseExited(MouseEvent e)
			{
				deleteButton.setIcon(ImageData.resetBasic);						//초기화 버튼 위에 마우스가 나오면 아이콘이 바뀜
			}
		});
	    setVisible(true);
	}
	
	public void createContent() {
		
			JPanel webBrowserPanel = new JPanel(new BorderLayout());					//지도를 출력할 패널 생성 및 초기화
			webBrowserPanel.setBounds(10,60,630,480);									//패널 크기 설정
			  
		    webBrowser.setBarsVisible(false);											//웹브라우져 바 삭제
		    webBrowser.setStatusBarVisible(true);										//웹브라우져 상태바 삭제
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
		      "  <body>" + LS +															//로딩 메세지 출력
		      "   <div id = \"map\"> <font size=30, color = white> <center> Loading... </center> </font> </div>" + LS +
		      "  </body>" + LS +
		      "</html>";
		    
		    webBrowser.setHTMLContent(htmlContent);										//기본 html 코드 웹브라우져에 추가
		    webBrowser.addWebBrowserListener(new WebBrowserListener(){					//웹브라우져이벤트 리스너
				public void commandReceived(WebBrowserCommandEvent arg0) {
					
				}
				public void loadingProgressChanged(WebBrowserEvent arg0) {
				}
				public void locationChangeCanceled(WebBrowserNavigationEvent arg0) {
				}
				public void locationChanged(WebBrowserNavigationEvent arg0) {						//웹브라우져 현재 위치가 바뀌었을 때
					script.resetScript();												//javascript 코드 전면 초기화
					script.setDetailMain(Double.toString(37.566542),Double.toString(126.977874));	//서울 중심위도경도로 설정
					webBrowser.executeJavascript(script.getScript());					//설정된 javascript 문 실행
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

	private class YearSearch implements ActionListener									//차트관련 이벤트 설정 ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object obj = e.getSource();
			String selectedYear;
			String selectedMonth;
			
			if(obj == showChartBtn)														//차트보기 버튼 클릭시
			{
				selectedYear = (String) yearBox.getSelectedItem();						//선택된 년 
				
				if(selectedYear.equals("년도"))											//년도(아무것도 설정 안함)로 선택되었을 경우
				{
					chart = getChart("All");											//차트에 모두 가져오기
					JOptionPane.showMessageDialog(showChartBtn, "모든 년도의 사고에 대한 차트가 보여집니다.");
				}
				else
				{
					chart = getChart(selectedYear);										//차트에 선택된 년것만 가져오기
					JOptionPane.showMessageDialog(showChartBtn, selectedYear+" 년도에 대한 차트가 보여집니다.");
				}
			    chartPanel.setChart(chart);
			}
			else if(obj == showMapBtn)													//지도보기 버튼 클릭시
			{
				selectedYear = (String) yearBox.getSelectedItem();						//선택된 년 가져오기
				selectedMonth = (String) monthBox.getSelectedItem();					//선택된 월 가져오기
				
				if(selectedYear.equals("년도"))											//전체일 경우
				{
					JOptionPane.showMessageDialog(showChartBtn, "모든 사고에 대한 위치가 표시됩니다.");
					accList = AppManager.CreateInstance().getAccidentCaseDAO().getAll(); 	//데이터베이스에서 전체 데이터 가져오기
					script.resetScript();
					script.setAnalysisMain(accList);
					webBrowser.executeJavascript(script.getScript());					//지도 재설정	
					repaint();															//지도 다시 그리기
				}
				else 																	//년이 설정되었을 경우
				{
					if(selectedMonth.equals("월"))										//월이 설정이 안되었을경우
					{
						JOptionPane.showMessageDialog(showChartBtn, selectedYear + " 년도의 사고에 대한 위치가 표시됩니다.");
						accList = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(selectedYear);
						script.resetScript();
						script.setAnalysisMain(accList);
						webBrowser.executeJavascript(script.getScript());				//지도 설정	
						repaint();	
					}
					else																//월이 설정되었을경우
					{
						JOptionPane.showMessageDialog(showChartBtn, selectedYear + " 년도 " + selectedMonth + " 월의 사고에 대한 위치가 표시됩니다.");
						accList = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(selectedYear, selectedMonth);
						script.resetScript();
						script.setAnalysisMain(accList);
						webBrowser.executeJavascript(script.getScript());				//지도 설정	
						repaint();														//지도 다시그리기
					}
				}
			}
			else if(obj == parsingButton)												//파싱버튼 클릭시
			{
				selectedYear = (String) yearBox.getSelectedItem();						//선택된 년 가져오기
				
				if(selectedYear.equals("년도"))											//년이 선택되어있지 않을경우
				{
					JOptionPane.showMessageDialog(parsingButton, "Parsing 할 년도를 선택하십시오.");	//경고창 띄우기
				}
				else																	//년이 선택되어있을 경우
				{
					//Parsing 할 것인지를 확인
					int result = JOptionPane.showConfirmDialog(parsingButton, selectedYear+"년도 데이터를 Parsing 하시겠습니까?"); 
				
					if(result == JOptionPane.YES_OPTION)    							 //Yes 버튼(초기화 함)
					{	
						 JOptionPane msg = new JOptionPane("잠시만 기다려 주십시오..\n"+selectedYear + " 년도 데이터 Parsing이 진행 중 입니다.", JOptionPane.PLAIN_MESSAGE);
						 JDialog dlg = msg.createDialog("메시지");
						 dlg.setLocationRelativeTo(parsingButton);
						 dlg.setAlwaysOnTop(true);
						
						 //파싱하는 과정에서 새로운 Dialog 를 쓰래드로 띄어줌
						 dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
						    
						    new Thread(new Runnable() {
						      @Override
						      public void run() 
						      {
						        if(AppManager.CreateInstance().getAccidentCaseDAO().ParsingAccidentData(selectedYear))
						        {
						        	dlg.setVisible(false);
						        	JOptionPane.showMessageDialog(parsingButton, selectedYear+" 년도 데이터 Parsing 완료");		//Parsing 이 완료되었을때
						        }
						      }
						    }).start();
						    
						    dlg.setVisible(true);
					    
					}
				}
				
			}
			else if(obj == deleteButton)																		//초기화버튼일경우
			{
				int result = JOptionPane.showConfirmDialog(deleteButton, "DB를 초기화 하시겠습니까? ");				//정말로 초기화 할 것인지를 확인
				if(result == JOptionPane.YES_OPTION)     //Yes 버튼(초기화 함)
				{
					AppManager.CreateInstance().getAccidentCaseDAO().deleteAllCase();
				}
			}
			
		}
	}
	
	
	public void paint(Graphics g)																				//웹브라우져 지도를 위해 지속적으로 repaint 실시
	{
		super.paintComponents(g);
		this.repaint();
	}
	
	public JFreeChart getChart(String selectedYear)																//차트 가져오기
	{
	    	int sNum[] = new int[13];																			//서울 배열 선언
	    	int kNum[] = new int[13];																			//경기 배열 선언
	    	int iNum[] = new int[13];																			//인천 배열 선언
	    	ArrayList<AccidentCase> datas = new ArrayList<AccidentCase>();										//사고 경우 
	    	
	    	if(selectedYear.equals("All"))																		//선택된 해가없을경우
	    		datas = AppManager.CreateInstance().getAccidentCaseDAO().getAll();								//모든 데이터를 가져옴
	    	else																								//선택된 해가 있을경우
	    		datas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(selectedYear);			//해당년의 데이터를 가져오기

	    	for(int iter = 0 ; iter < datas.size(); iter++)
	    		{
	    			if(datas.get(iter).getProvince().equals("서울특별시"))											//가져온 데이터가 서울일경우 
	    			{
	    				sNum[Integer.parseInt(datas.get(iter).getMonth())]++;									//서울 사고 건수 올리기
	    			}
	    			else if(datas.get(iter).getProvince().equals("경기도"))										//가져온 데이터가 경기도일 경우
	    			{
	    				kNum[Integer.parseInt(datas.get(iter).getMonth())]++;									//경기도 사고 건수 올리기
	    			}
	    			else if(datas.get(iter).getProvince().equals("인천광역시"))									//가져온 데이터가 인천일 경우
	    			{
	    				iNum[Integer.parseInt(datas.get(iter).getMonth())]++;									//인천 사고 건수 올리기
	    			}
	    		}
	    	
	    	 String [][] times = {{Integer.toString(sNum[1]),Integer.toString(sNum[2]),Integer.toString(sNum[3]),Integer.toString(sNum[4]),Integer.toString(sNum[5]),Integer.toString(sNum[6]),Integer.toString(sNum[7]),Integer.toString(sNum[8]),Integer.toString(sNum[9]),Integer.toString(sNum[10]),Integer.toString(sNum[11]),Integer.toString(sNum[12]), Integer.toString(average(sNum))},
			    		{Integer.toString(kNum[1]),Integer.toString(kNum[2]),Integer.toString(kNum[3]),Integer.toString(kNum[4]),Integer.toString(kNum[5]),Integer.toString(kNum[6]),Integer.toString(kNum[7]),Integer.toString(kNum[8]),Integer.toString(kNum[9]),Integer.toString(kNum[10]),Integer.toString(kNum[11]),Integer.toString(kNum[12]), Integer.toString(average(kNum)) },
			    		{Integer.toString(iNum[1]),Integer.toString(iNum[2]),Integer.toString(iNum[3]),Integer.toString(iNum[4]),Integer.toString(iNum[5]),Integer.toString(iNum[6]),Integer.toString(iNum[7]),Integer.toString(iNum[8]),Integer.toString(iNum[9]),Integer.toString(iNum[10]),Integer.toString(iNum[11]),Integer.toString(iNum[12]), Integer.toString(average(iNum)) }};
			    basic.setRowCount(0);																			//table 초기화
			    
			    for(int i =0; i<times.length; i++)																//가져온데이터들을 table 에 붙이기
			    {
			    	System.out.println();
			    	basic.addRow(times[i]);	
			    }
			    
	    	
	        // 데이터 생성
	        DefaultCategoryDataset sDataSet = new DefaultCategoryDataset();               // bar chart 
	        DefaultCategoryDataset kDataSet = new DefaultCategoryDataset();               // bar chart 2
	        DefaultCategoryDataset iDataSet = new DefaultCategoryDataset();               // line chart 1

	        // 데이터 입력 ( 값, 범례, 카테고리 )
	        
	        // 그래프 1      
	        sDataSet.addValue(sNum[1], "서울", "1월");
	        sDataSet.addValue(sNum[2], "서울", "2월");
	        sDataSet.addValue(sNum[3], "서울", "3월");
	        sDataSet.addValue(sNum[4], "서울", "4월");
	        sDataSet.addValue(sNum[5], "서울", "5월");
	        sDataSet.addValue(sNum[6], "서울", "6월");
	        sDataSet.addValue(sNum[7], "서울", "7월");
	        sDataSet.addValue(sNum[8], "서울", "8월");
	        sDataSet.addValue(sNum[9], "서울", "9월");
	        sDataSet.addValue(sNum[10], "서울", "10월");
	        sDataSet.addValue(sNum[11], "서울", "11월");
	        sDataSet.addValue(sNum[12], "서울", "12월");
	        
	        // 그래프 2       
	        kDataSet.addValue(kNum[1], "경기", "1월");
	        kDataSet.addValue(kNum[2], "경기", "2월");
	        kDataSet.addValue(kNum[3], "경기", "3월");
	        kDataSet.addValue(kNum[4], "경기", "4월");
	        kDataSet.addValue(kNum[5],  "경기", "5월");
	        kDataSet.addValue(kNum[6],  "경기", "6월");
	        kDataSet.addValue(kNum[7],  "경기", "7월");
	        kDataSet.addValue(kNum[8],  "경기", "8월");
	        kDataSet.addValue(kNum[9], "경기", "9월");
	        kDataSet.addValue(kNum[10],  "경기", "10월");
	        kDataSet.addValue(kNum[11],  "경기", "11월");
	        kDataSet.addValue(kNum[12],  "경기", "12월");

	        // 그래프 3       
	        iDataSet.addValue(iNum[1], "인천", "1월");
	        iDataSet.addValue(iNum[2], "인천", "2월");
	        iDataSet.addValue(iNum[3], "인천", "3월");
	        iDataSet.addValue(iNum[4], "인천", "4월");
	        iDataSet.addValue(iNum[5], "인천", "5월");
	        iDataSet.addValue(iNum[6], "인천", "6월");
	        iDataSet.addValue(iNum[7], "인천", "7월");
	        iDataSet.addValue(iNum[8], "인천", "8월");
	        iDataSet.addValue(iNum[9], "인천", "9월");
	        iDataSet.addValue(iNum[10], "인천", "10월");
	        iDataSet.addValue(iNum[11], "인천", "11월");
	        iDataSet.addValue(iNum[12], "인천", "12월");

	        // 렌더링 생성 및 세팅
	        final LineAndShapeRenderer sRender = new LineAndShapeRenderer();
	        final LineAndShapeRenderer kRender = new LineAndShapeRenderer();
	        final LineAndShapeRenderer iRender = new LineAndShapeRenderer();

	        // 공통 옵션 정의
	        final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
	        final ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
	        final ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);

	        Font f = new Font("Gothic", Font.BOLD, 15);
	        Font axisF = new Font("Gulim", Font.PLAIN, 14);

	        // 렌더링 세팅
	        // 그래프 1 옵션 세팅
	        sRender.setSeriesPaint(0, new Color(0,0,255));
	        sRender.setBaseItemLabelFont(f);
	        sRender.setBasePositiveItemLabelPosition(p_center);
	        sRender.setBaseItemLabelGenerator(generator);
	        sRender.setBaseItemLabelsVisible(true);

	        // 그래프 2 옵션 세팅      
	        kRender.setSeriesPaint(0, new Color(255,0,0));
	        kRender.setBaseItemLabelFont(f);
	        kRender.setBasePositiveItemLabelPosition(p_center);
	        kRender.setBaseItemLabelGenerator(generator);
	        kRender.setBaseItemLabelsVisible(true);

	        // 그래프 3 옵션 세팅
	        iRender.setSeriesPaint(0, new Color(0,255,0));
	        iRender.setBaseItemLabelFont(f);
	        iRender.setBasePositiveItemLabelPosition(p_center);
	        iRender.setBaseItemLabelGenerator(generator);
	        iRender.setBaseItemLabelsVisible(true);

	        // plot 생성
	        CategoryPlot plot = new CategoryPlot();

	        // plot 에 데이터 적재
	        plot.setDataset(sDataSet);
	        plot.setRenderer(sRender);
	        plot.setDataset(1,kDataSet);
	        plot.setRenderer(1,kRender);
	        plot.setDataset(2, iDataSet);
	        plot.setRenderer(2, iRender);

	        // plot 기본 설정
	        plot.setOrientation(PlotOrientation.VERTICAL);         							    // 그래프 표시 방향
	        plot.setRangeGridlinesVisible(true);                       							// X축 가이드 라인 표시여부
	        plot.setDomainGridlinesVisible(true);                    							  // Y축 가이드 라인 표시여부

	        // 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
	        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
	       
	        // X축 세팅

	        plot.setDomainAxis(new CategoryAxis());              									// X축 종류 설정
	        plot.getDomainAxis().setTickLabelFont(axisF);  		 									// X축 눈금라벨 폰트 조정
	        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       	// 카테고리 라벨 위치 조정

	        // Y축 세팅
	        plot.setRangeAxis(new NumberAxis());                									 // Y축 종류 설정
	        plot.getRangeAxis().setTickLabelFont(axisF);        									// Y축 눈금라벨 폰트 조정


	        // 세팅된 plot을 바탕으로 chart 생성
	        JFreeChart chart = new JFreeChart(plot);
	        
	        if(selectedYear.equals("All"))
	        	chart.setTitle("전체 사망 교통 사고 월별 발생 건수"); 										// 차트 타이틀
	    	else
	    		chart.setTitle(selectedYear+ "년" + " 사망 교통 사고 월별 발생 건수"); 						// 차트 타이틀
	        
	        return chart;
	    }
	
	public int average(int [] t)																	//각 지역별 평균을 구해주는 메소드
	{
		int sum = 0;
		for(int i = 1; i<=12 ; i++)
		{
			sum += t[i];
		}
		
		return sum/12;
	}


}