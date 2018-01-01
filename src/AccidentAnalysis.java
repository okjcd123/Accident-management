import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
	private	String[] year = {"전체", "2012","2013","2014","2015","2016"};
	
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
	private JButton deleteButton;
	
	private Javascript script = new Javascript();
	private JPanel infoPanel;

	// 차트
	private JFreeChart chart;
	private ChartPanel chartPanel;
	
	final JWebBrowser webBrowser = new JWebBrowser();							//웹브라우져 객체 생성
    ;
	
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
						AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
						AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
						AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
						AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
						AppManager.CreateInstance().getAppController().analysisOpenedFlag = false;
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
		primary.setBackground(Color.LIGHT_GRAY);
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
			
		searchDateBtn = new JButton("차트 보기");
		searchDateBtn.setBounds(320, 10, 100,40);
		boxPanel.add(searchDateBtn);
		
		parsingButton = new JButton("파싱");
		parsingButton.setBounds(430,10,100,40);
		boxPanel.add(parsingButton);
		
		deleteButton = new JButton("초기화");
		deleteButton.setBounds(540,10,100,40);
		boxPanel.add(deleteButton);
	
		primary.add(boxPanel);
		
		chart = new JFreeChart(new CategoryPlot());
  	  	chartPanel = new ChartPanel(chart);
  	  	
	    chartPanel.setChart(getChart("All"));
	    chartPanel.setLayout(null);
	    chartPanel.setBounds(650,60,640,400);
	    primary.add(chartPanel);	
		
		NativeInterface.open();			
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	    	  createContent();
	    	  add(primary);
	    	  repaint();
	      }
	    });
	    
	    searchDateBtn.addActionListener(new YearSearch());    //버튼 이벤트 추가
	    parsingButton.addActionListener(new YearSearch()); 
	    deleteButton.addActionListener(new YearSearch()); 

	    setVisible(true);
		
	}
	
	public void createContent() {
		
			JPanel webBrowserPanel = new JPanel(new BorderLayout());					//지도를 출력할 패널 생성 및 초기화
			webBrowserPanel.setBounds(10,60,630,480);									//패널 크기 설정
			  
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
					script.resetScript();
					script.setAnalysisMain(accList);
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
			String selectedYear;
			
			if(obj == searchDateBtn)
			{
				
				selectedYear = (String) yearBox.getSelectedItem();
				if(selectedYear.equals("전체"))
				{
					chart = getChart("All");	
					accList = AppManager.CreateInstance().getAccidentCaseDAO().getAll(); 
					script.resetScript();
					script.setAnalysisMain(accList);
					webBrowser.executeJavascript(script.getScript());					//지도 설정	
				}
				else
				{
					chart = getChart(selectedYear);
					accList = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(selectedYear);
					script.resetScript();
					script.setAnalysisMain(accList);
					webBrowser.executeJavascript(script.getScript());					//지도 설정	
				}
					
			    chartPanel.setChart(chart);
			}
			else if(obj == parsingButton)
			{
				selectedYear = (String) yearBox.getSelectedItem();
		    	AppManager.CreateInstance().getAccidentCaseDAO().ParsingAccidentData(selectedYear);
			}
			else if(obj == deleteButton)
			{
		    	AppManager.CreateInstance().getAccidentCaseDAO().deleteAllCase();
			}
		}
	}

	
	public void paint(Graphics g)
	{
		super.paintComponents(g);
		this.repaint();
	}
	
	public JFreeChart getChart(String selectedYear)
	{
	
	    	int sNum[] = new int[13];
	    	int kNum[] = new int[13];
	    	int iNum[] = new int[13];
	    	ArrayList<AccidentCase> datas = new ArrayList<AccidentCase>();
	    	
	    	if(selectedYear.equals("All"))
	    		datas = AppManager.CreateInstance().getAccidentCaseDAO().getAll();
	    	else
	    		datas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(selectedYear);

	    	for(int iter = 0 ; iter < datas.size(); iter++)
	    		{
	    			if(datas.get(iter).getProvince().equals("서울특별시"))
	    			{
	    				sNum[Integer.parseInt(datas.get(iter).getMonth())]++;
	    			}
	    			else if(datas.get(iter).getProvince().equals("경기도"))
	    			{
	    				kNum[Integer.parseInt(datas.get(iter).getMonth())]++;
	    			}
	    			else if(datas.get(iter).getProvince().equals("인천광역시"))
	    			{
	    				iNum[Integer.parseInt(datas.get(iter).getMonth())]++;
	    			}
	    		}
	    	
	        // 데이터 생성
	        DefaultCategoryDataset sDataSet = new DefaultCategoryDataset();                // bar chart 
	        DefaultCategoryDataset kDataSet = new DefaultCategoryDataset();               // bar chart 2
	        DefaultCategoryDataset iDataSet = new DefaultCategoryDataset();                // line chart 1

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

	        // 렌더링 생성
	        
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
	        // 그래프 1
	        sRender.setSeriesPaint(0, new Color(0,0,255));
	        sRender.setBaseItemLabelFont(f);
	        sRender.setBasePositiveItemLabelPosition(p_center);
	        sRender.setBaseItemLabelGenerator(generator);
	        sRender.setBaseItemLabelsVisible(true);

	        // 그래프 2       
	        kRender.setSeriesPaint(0, new Color(255,0,0));
	        kRender.setBaseItemLabelFont(f);
	        kRender.setBasePositiveItemLabelPosition(p_center);
	        kRender.setBaseItemLabelGenerator(generator);
	        kRender.setBaseItemLabelsVisible(true);

	        // 그래프 3       
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

	        plot.setOrientation(PlotOrientation.VERTICAL);             // 그래프 표시 방향
	        plot.setRangeGridlinesVisible(true);                       // X축 가이드 라인 표시여부
	        plot.setDomainGridlinesVisible(true);                      // Y축 가이드 라인 표시여부


	        // 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
	        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
	       
	        // X축 세팅

	        plot.setDomainAxis(new CategoryAxis());              // X축 종류 설정
	        plot.getDomainAxis().setTickLabelFont(axisF);  		 // X축 눈금라벨 폰트 조정
	        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // 카테고리 라벨 위치 조정

	        // Y축 세팅

	        plot.setRangeAxis(new NumberAxis());                 // Y축 종류 설정
	        plot.getRangeAxis().setTickLabelFont(axisF);        // Y축 눈금라벨 폰트 조정


	        // 세팅된 plot을 바탕으로 chart 생성

	        JFreeChart chart = new JFreeChart(plot);
	        
	        if(selectedYear.equals("All"))
	        	chart.setTitle("전체 사망 교통 사고 월별 발생 건수"); // 차트 타이틀
	    	else
	    		chart.setTitle(selectedYear+ "년" + "사망 교통 사고 월별 발생 건수"); // 차트 타이틀
	        
	        return chart;
	    }



}