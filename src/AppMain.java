
//table 전환작업 필요
//등록, 삭제 디테일 잡기
//Detail info 개요 측면 잡기
//Analysis 틀 잡기
//Detail info, Analysis 버튼 이벤트 클래스 별도 생성

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class AppMain extends JFrame{

	protected String [] header = {"사고번호", "시/도", "구/군", "발생연", "월", "일", "사상자", "사망자", "부상자", "사고 유형"};
	protected String contents[][] = {{"1", "서울특별시", "강남구", "2017", "02", "28", "1", "0" ,"1", "차대사람"}
	,{"1", "서울특별시", "강남구", "2017", "02", "28", "1", "0" ,"1", "차대차"}};
	
	protected	String[] year = {"년도","2000","2001","2002","2003","2004","2005","2006","2007","2008",
			"2009","2010","2011","2012","2013","2014","2015","2016","2017", "2018"};
	protected String[] month = {"월","1","2","3","4","5","6","7","8","9","10","11","12",};
	protected String[] day = {"일","1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	
	protected String[] province = {"전체","서울특별시","인천광역시","경기도"};
	protected String[] accOptionType = {"차대차", "차대사람", "차량단독"};

	//기본 옵션 패널 라벨----------------------------------------------------------------------
	protected JPanel[] panels;
	protected JLabel[] labels;
	
	protected CardLayout cardLayout = new CardLayout();
	protected JPanel primary;
	protected Container cardPanel = new JPanel();
	protected JPanel tablePanel = new JPanel();
	protected JPanel imagePanel = new JPanel();
	protected JLabel imageLabel = new JLabel(ImageData.mainImage);
	
	//버튼----------------------------------------------------------------------------------
	protected JPanel bPanel;
	protected JButton[] btns;
	
	//JTable---------------------------------------------------------------------------------
	//DefaultTableModel 기본표모델=new DefaultTableModel();

	DefaultTableModel basicTable =new DefaultTableModel();
	
	protected JTable table = new JTable(basicTable)
	{
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	};
	protected JScrollPane scroll;
	
	//사고 검색--------------------------------------------------------------------------------
	protected JDialog diaSearch;
	protected JPanel searchUpPanel;
	protected JPanel searchDownPanel;
	protected JPanel searchButtonPanel;
	
	protected JLabel siDolbl;
	protected JLabel guGunlbl;
	protected JComboBox siDo = new JComboBox(province);
	protected JComboBox guGun = new JComboBox();
	
	protected JButton searchButton = new JButton();
	
	//사고 등록--------------------------------------------------------------------------------
	protected JDialog dia;
	protected JPanel leftPanel;
	
	protected JLabel label1;	//장소
	protected JLabel label2; 	//날짜
	protected JLabel label3; 	//경찰번호
	protected JLabel label5; 	//사상자 수
	protected JLabel label6;	//사고 타입
	protected JLabel label7; 	//"위도, 경도
	//protected JLabel label8 //"등록"
	
	protected JPanel rightPanel;
	
	protected JTextField polno = new JTextField(10);
	protected JTextField dead = new JTextField(10);
	protected JTextField injured = new JTextField(10);
	protected JTextField lati = new JTextField(10);
	protected JTextField longi = new JTextField(10);
	
	//장소입력부분
	protected JPanel loc;
	protected JComboBox pro = new JComboBox(province);
	protected JComboBox tow = new JComboBox();
	
	//날짜입력부분
	protected JPanel time;
	protected JComboBox yearcb = new JComboBox(year);
	protected JComboBox monthcb = new JComboBox(month);
	protected JComboBox daycb = new JComboBox(day);
	
	//사상사주 입력 부분
	protected JPanel casualty;
	protected	JLabel tmp1;
	protected	JLabel tmp2;
	
	//사고 타입 부분
	protected JComboBox accType = new JComboBox(accOptionType);
	
	//위도 경도 입력부분
	protected	JPanel locInfo;
	protected	JLabel laTmp;
	protected	JLabel loTmp;
	
	//하단 패널
	protected JPanel subPanel;
	protected JButton regBtn = new JButton("등록");
	
	//사고 수정/삭제-----------------------------------------------------------------------------------------
	protected JDialog diaUpdate;
	protected JPanel leftUpdatePanel;
	protected JLabel caseNum;			//사건 번호
	
	protected JLabel labelUpdate1;	//장소
	protected JLabel labelUpdate2; 	//날짜
	protected JLabel labelUpdate3; 	//경찰번호
	protected JLabel labelUpdate5; 	//사상자 수
	protected JLabel labelUpdate6;	//사고 타입
	protected JLabel labelUpdate7; 	//"위도, 경도
	
	protected JPanel rightUpdatePanel;
	
	protected JPanel searchCaseNumPanel;	//케이스 검색 패널
	protected JTextField caseNumTxt = new JTextField();		//사건 번호 입력란
	protected JButton searchUpdateBtn = new JButton("검색");	//사건 번호 검색;		//사건 번호 검색
	
	protected JTextField polnoUpdate = new JTextField(10);
	protected JTextField deadUpdate = new JTextField(10);
	protected JTextField injuredUpdate = new JTextField(10);
	protected JTextField latiUpdate = new JTextField(10);
	protected JTextField longiUpdate = new JTextField(10);

	//장소입력부분
	protected JPanel locUpdate;
	protected JComboBox proUpdate = new JComboBox(province);
	protected JComboBox towUpdate = new JComboBox();
	
	//날짜입력부분
	protected JPanel timeUpdate;
	protected JComboBox yearcbUpdate = new JComboBox(year);
	protected JComboBox monthcbUpdate = new JComboBox(month);
	protected JComboBox daycbUpdate = new JComboBox(day);
	
	//사상사주 입력 부분
	protected	JPanel casualtyUpdate;
	protected	JLabel tmp1Update;
	protected	JLabel tmp2Update;
	
	//사고타입 부분
	protected JComboBox accTypeUpdate = new JComboBox(accOptionType);
	
	
	//위도 경도 입력부분
	protected	JPanel locInfoUpdate;
	protected	JLabel laTmpUpdate;
	protected	JLabel loTmpUpdate;
	
	//하단 패널
	protected JPanel subUpdatePanel;
	protected JButton updateButton = new JButton("수정");
	protected JButton deleteButton = new JButton("삭제");
	
	//사고 분석----------------------------------------------------------------------------
	
	//하단 메세지-------------------------------------------------------------------------------
	protected JLabel accInfo;

	public AppMain() {
		
		AppManager.CreateInstance().setAppMain(this);
		
		setTitle("교통 사고 관리 시스템");
		setSize(Execute.WIDTH,Execute.HEIGHT);
		setResizable(false);
		//setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		primary = new JPanel();
		primary.setBounds(0,0,Execute.WIDTH,Execute.HEIGHT);
		primary.setLayout(null);
		getContentPane().add(primary);
		
		//해당 날짜 사고 정보들 실시간? 띄우기
		
		accInfo = new JLabel("해당 날짜 사고 정보");
		accInfo.setBounds(0, 670, 1200, 30);
		primary.add(accInfo);
		
		//기능 버튼들 추가
		//버튼 추가 예정
		bPanel = new JPanel();
		bPanel.setLayout(null);
		bPanel.setBounds(0, 0, 1300, 110);
		
		btns = new JButton[4];
		btns[0] = new JButton("사고 정보 검색");
		btns[0].setBounds(20,45,311,60);
		bPanel.add(btns[0]);
		
		btns[1] = new JButton("사고 등록");
		btns[1].setBounds(20+311 + 5,45,311,60);
		bPanel.add(btns[1]);
		
		btns[2] = new JButton("사고 정보 수정/삭제");
		btns[2].setBounds(20 + 311 + 5 + 311 + 5,45,311,60);
		bPanel.add(btns[2]);
		
		btns[3] = new JButton("사고 분석");
		btns[3].setBounds(20 +311 + 5+ 311 + 5 + 311 +5,45,311,60);
		
		//1300
		bPanel.add(btns[3]);
		
		primary.add(bPanel);
		
		//Table과 기본 이미지 관련 사항------------------------------------------------------------------------------
		
		cardPanel.setBounds(20,110,1260,510);
		cardPanel.setLayout(cardLayout);
		
		imagePanel.setBounds(0,0,1260,510);
		imageLabel.setBounds(0,0,1260,510);
		imagePanel.add(imageLabel);
		
		//table 관련 사항 만들기
		tablePanel.setBounds(0,0,1260,510);
		tablePanel.setLayout(null);
		for(int i =0; i<header.length; i++)
		{
			basicTable.addColumn(header[i]);	
		}
		for(int i =0; i<contents.length; i++)
		{
			basicTable.addRow(contents[i]);
		}
		scroll = new JScrollPane();
		scroll.setViewportView(table);
      	scroll.setBounds(0,0,1260,510);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); //가로바정책
      	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //가로바정책
      	tablePanel.add(scroll);
      	
      	cardPanel.add(tablePanel, "table");
      	cardPanel.add(imagePanel, "image");
      	cardLayout.show(cardPanel,"image");		
      	
      	primary.add(cardPanel);
      	
		add(primary);
		setVisible(true);
	}
	
	public void search() {
		diaSearch = new JDialog();
		diaSearch.setSize(400,300);
		diaSearch.setResizable(false);
		diaSearch.setLayout(null);
		diaSearch.setTitle("사고 정보 검색");
		
		searchUpPanel = new JPanel();
		searchUpPanel.setBounds(0,0,400,100);
		searchUpPanel.setBackground(Color.white);
		searchUpPanel.setLayout(null);
		
		siDolbl = new JLabel("시/도");
		siDolbl.setBounds(0,40,50,50);
		searchUpPanel.add(siDolbl);
		
		siDo.setBounds(100,40,250,50);
		searchUpPanel.add(siDo);
		
		searchDownPanel = new JPanel();
		searchDownPanel.setBounds(0,100,400,100);
		searchDownPanel.setBackground(Color.white);
		searchDownPanel.setLayout(null);
		
		guGunlbl = new JLabel("군/구");
		guGunlbl.setBounds(0,0,50,50);
		searchDownPanel.add(guGunlbl);
		
		guGun.setBounds(100,0,250,50);
		searchDownPanel.add(guGun);
		
		searchButtonPanel = new JPanel();
		searchButtonPanel.setLayout(null);
		searchButtonPanel.setBounds(0,200,400,100);
		searchButton.setBounds(100, 0, 200, 100);
		searchButtonPanel.add(searchButton);
		
		diaSearch.add(searchUpPanel);
		diaSearch.add(searchDownPanel);
		diaSearch.add(searchButtonPanel);
		diaSearch.setVisible(true);
	}

	 public void registration() {

	      dia = new JDialog();
	      dia.setTitle("사고 등록");
	      dia.setLayout(null);
	      dia.setResizable(false);
	      dia.setSize(550,550);
	      
	      //leftPanel 관련 사항 --------------------------------------------------
	      leftPanel = new JPanel();
	      leftPanel.setBackground(Color.white);
	      leftPanel.setLayout(null);
	      leftPanel.setBounds(0,0,100,430);

	      label1 = new JLabel("장소",JLabel.CENTER);
	      label2 = new JLabel("날짜",JLabel.CENTER);
	      label3 = new JLabel("경찰번호",JLabel.CENTER);
	      label5 = new JLabel("사상자 수",JLabel.CENTER);
	      label6 = new JLabel("사고 타입",JLabel.CENTER);
	      label7 = new JLabel("위도, 경도",JLabel.CENTER);

	      label1.setBounds(0, 20,100,50);
	      label2.setBounds(0, 90, 100, 50);
	      label3.setBounds(0, 160, 100, 50);
	      label5.setBounds(0, 230, 100, 50);
	      label6.setBounds(0, 300, 100, 50);
	      label7.setBounds(0, 370, 100, 50);
	      leftPanel.add(label1);
	      leftPanel.add(label2);
	      leftPanel.add(label3);
	      leftPanel.add(label5);
	      leftPanel.add(label6);
	      leftPanel.add(label7);

	      dia.add(leftPanel);

	      //rightPanel 관련 사항 --------------------------------------------------
	      rightPanel = new JPanel();
	      rightPanel.setLayout(null);
	      rightPanel.setBounds(100,0,700,500);
	      //rightPanel.setBackground(Color.GREEN);
	      dia.add(rightPanel);


	      //장소입력부분
	      loc = new JPanel();
	      loc.setLayout(null);
	      loc.setBounds(10, 20, 370, 50);
	      pro.setBounds(0, 0, 200, 50);
	      tow.setBounds(220, 0, 150, 50);
	      loc.add(pro);loc.add(tow);
	      rightPanel.add(loc);

	      //날짜입력부분
	      time = new JPanel();
	      time.setLayout(null);
	      time.setBounds(10, 90, 410, 50);
	      yearcb.setBounds(0,0,170,50);
	      monthcb.setBounds(190,0,100,50);
	      daycb.setBounds(310,0,100,50);
	      time.add(yearcb);time.add(monthcb);time.add(daycb);
	      rightPanel.add(time);

	      //경찰번호 입력부분
	      //JPanel tm = new JPanel();
	      polno.setBounds(10,160,70,50);
	      rightPanel.add(polno);

	      //사상사주 입력 부분
	      casualty = new JPanel();
	      casualty.setLayout(null);
	      casualty.setBounds(10, 230, 440, 50);
	      tmp1 = new JLabel("사망자 수");
	      tmp2 = new JLabel("부상자 수");
	      tmp1.setBounds(0, 0, 70, 50);
	      dead.setBounds(80, 0, 70, 50);
	      tmp2.setBounds(160, 0, 70, 50);
	      injured.setBounds(240,0,70,50);
	      casualty.add(tmp1); casualty.add(dead);
	      casualty.add(tmp2); casualty.add(injured);
	      rightPanel.add(casualty);

	      //사고타입 입력부분
	      accType.setBounds(10, 300, 150, 50);
	      rightPanel.add(accType);

	      //위도 경도 입력부분
	      locInfo = new JPanel();
	      locInfo.setLayout(null);
	      locInfo.setBounds(10, 370, 740, 50);
	      laTmp = new JLabel("위도");
	      loTmp = new JLabel("경도");
	      laTmp.setBounds(0, 0,50,50);
	      lati.setBounds(60, 0, 70, 50);
	      loTmp.setBounds(140, 0, 50, 50);
	      longi.setBounds(200, 0, 70, 50);
	      locInfo.add(laTmp);locInfo.add(lati);
	      locInfo.add(loTmp);locInfo.add(longi);
	      rightPanel.add(locInfo);

	      //subPanel----------------------------------------------------------------------------
	      /*subPanel = new JPanel();
	      subPanel.setBounds(0,500,810,100);
	      dia.add(subPanel);*/

	      /*regBtn.setBounds(405-20, 0, 40, 30);
	      subPanel.add(regBtn);*/

	      regBtn.setBounds(130,450,60,50);
	      rightPanel.add(regBtn);

	      dia.setVisible(true);
	   }
	   public void modifyDelete() {

	      diaUpdate = new JDialog();
	      diaUpdate.setTitle("사고 수정 / 삭제");
	      diaUpdate.setLayout(null);
	      diaUpdate.setResizable(false);
	      diaUpdate.setSize(550,600);

	      //leftPanel 관련 사항 --------------------------------------------------
	      leftUpdatePanel = new JPanel();
	      leftUpdatePanel.setBackground(Color.white);
	      leftUpdatePanel.setLayout(null);
	      leftUpdatePanel.setBounds(0,0,100,500);


	      caseNum = new JLabel("사고 번호 입력",JLabel.CENTER);
	      labelUpdate1 = new JLabel("장소",JLabel.CENTER);
	      labelUpdate2 = new JLabel("날짜",JLabel.CENTER);
	      labelUpdate3 = new JLabel("경찰번호",JLabel.CENTER);
	      labelUpdate5 = new JLabel("사상자 수",JLabel.CENTER);
	      labelUpdate6 = new JLabel("사고 타입",JLabel.CENTER);
	      labelUpdate7 = new JLabel("위도, 경도",JLabel.CENTER);

	      caseNum.setBounds(0, 20, 100, 50);
	      labelUpdate1.setBounds(0, 20+70,100,50);
	      labelUpdate2.setBounds(0, 90+70, 100, 50);
	      labelUpdate3.setBounds(0, 160+70, 100, 50);
	      labelUpdate5.setBounds(0, 230+70, 100, 50);
	      labelUpdate6.setBounds(0, 300+70, 100, 50);
	      labelUpdate7.setBounds(0, 370+70, 100, 50);
	      leftUpdatePanel.add(caseNum);
	      leftUpdatePanel.add(labelUpdate1);
	      leftUpdatePanel.add(labelUpdate2);
	      leftUpdatePanel.add(labelUpdate3);
	      leftUpdatePanel.add(labelUpdate5);
	      leftUpdatePanel.add(labelUpdate6);
	      leftUpdatePanel.add(labelUpdate7);

	      diaUpdate.add(leftUpdatePanel);

	      //rightPanel 관련 사항 --------------------------------------------------
	      rightUpdatePanel = new JPanel();
	      rightUpdatePanel.setLayout(null);
	      rightUpdatePanel.setBounds(100,0,700,500);
	      //rightPanel.setBackground(Color.GREEN);
	      diaUpdate.add(rightUpdatePanel);

	      //사건 번호 입력 부분
	      searchCaseNumPanel = new JPanel();//케이스 검색 패널
	      searchCaseNumPanel.setLayout(null);
	      searchCaseNumPanel.setBounds(10, 20, 370, 50);
	      caseNumTxt = new JTextField(10);      //사건 번호 입력란
	      caseNumTxt.setBounds(0,0,70,50);
	      searchUpdateBtn.setBounds(90,0,70,50);
	      searchCaseNumPanel.add(caseNumTxt);
	      searchCaseNumPanel.add(searchUpdateBtn);
	      rightUpdatePanel.add(searchCaseNumPanel);

	      //장소입력부분
	      locUpdate = new JPanel();
	      locUpdate.setLayout(null);
	      locUpdate.setBounds(10, 20+70, 370, 50);
	      proUpdate.setBounds(0, 0, 200, 50);
	      towUpdate.setBounds(220, 0, 150, 50);
	      locUpdate.add(proUpdate);locUpdate.add(towUpdate);
	      rightUpdatePanel.add(locUpdate);

	      //날짜입력부분
	      timeUpdate = new JPanel();
	      timeUpdate.setLayout(null);
	      timeUpdate.setBounds(10, 90+70, 410, 50);
	      yearcbUpdate.setBounds(0,0,170,50);
	      monthcbUpdate.setBounds(190,0,100,50);
	      daycbUpdate.setBounds(310,0,100,50);
	      timeUpdate.add(yearcbUpdate);timeUpdate.add(monthcbUpdate);timeUpdate.add(daycbUpdate);
	      rightUpdatePanel.add(timeUpdate);

	      //경찰번호 입력부분
	      //JPanel tm = new JPanel();
	      polnoUpdate.setBounds(10,160+70,70,50);
	      rightUpdatePanel.add(polnoUpdate);

	      //사상사주 입력 부분
	      casualtyUpdate = new JPanel();
	      casualtyUpdate.setLayout(null);
	      casualtyUpdate.setBounds(10, 230+70, 440, 50);
	      tmp1Update = new JLabel("사망자 수");
	      tmp2Update = new JLabel("부상자 수");
	      tmp1Update.setBounds(0, 0, 70, 50);
	      deadUpdate.setBounds(80, 0, 70, 50);
	      tmp2Update.setBounds(160, 0, 70, 50);
	      injuredUpdate.setBounds(240,0,70,50);
	      casualtyUpdate.add(tmp1Update); casualtyUpdate.add(deadUpdate);
	      casualtyUpdate.add(tmp2Update); casualtyUpdate.add(injuredUpdate);
	      rightUpdatePanel.add(casualtyUpdate);

	      //사고타입 입력부분
	      accTypeUpdate.setBounds(10, 300+70, 150, 50);
	      rightUpdatePanel.add(accTypeUpdate);

	      //위도 경도 입력부분
	      locInfoUpdate = new JPanel();
	      locInfoUpdate.setLayout(null);
	      locInfoUpdate.setBounds(10, 370+70, 740, 50);
	      laTmpUpdate = new JLabel("위도");
	      loTmpUpdate = new JLabel("경도");
	      laTmpUpdate.setBounds(0, 0,50,50);
	      latiUpdate.setBounds(60, 0, 70, 50);
	      loTmpUpdate.setBounds(140, 0, 50, 50);
	      longiUpdate.setBounds(200, 0, 70, 50);
	      locInfoUpdate.add(laTmpUpdate);locInfoUpdate.add(latiUpdate);
	      locInfoUpdate.add(loTmpUpdate);locInfoUpdate.add(longiUpdate);
	      rightUpdatePanel.add(locInfoUpdate);

	      //550 600
	      subUpdatePanel = new JPanel();
	      subUpdatePanel.setBounds(0, 500, 550, 100);
	      subUpdatePanel.setLayout(null);
	      
	      updateButton.setBounds(200,0,70,40);
	      deleteButton.setBounds(300,0,70,40);
	      subUpdatePanel.add(updateButton);subUpdatePanel.add(deleteButton);
	      diaUpdate.add(subUpdatePanel);
	      diaUpdate.setVisible(true);
	   }


	
	public void analysis()
	{
		new AccidentAnalysis();
	}
	
	
	public void addActionButtonListener(ActionListener action)
	{
		for(int i=0;i<btns.length;i++)
		{
			btns[i].addActionListener(action);
		}
	}
	
	//Search 내부 버튼 관련---------------------------------------------------------------------
	
	public void addActionSearchProListener(ActionListener action)
	{
		siDo.addActionListener(action);
	}
	
	public void addActionSearchListener(ActionListener action)
	{
		searchButton.addActionListener(action);
	}

	//Register 내부 버튼 관련---------------------------------------------------------------------
	public void addActionRegisterProListener(ActionListener action)
	{
		pro.addActionListener(action);		
	}
	public void addActionRegisterListener(ActionListener action)
	{
		regBtn.addActionListener(action);
	}
	
	//Update 내부 버튼 관련---------------------------------------------------------------------
	public void addActionUpdateProListener(ActionListener action)
	{
		proUpdate.addActionListener(action);			
	}
	
	public void addActionUpdateSearchListener(ActionListener action)
	{
		searchUpdateBtn.addActionListener(action);
	}
	
	public void addActionUpdateListener(ActionListener action)
	{
		updateButton.addActionListener(action);
		deleteButton.addActionListener(action);
	}
	
	public void addMouseMainButtonListener(MouseListener mouse)
	{
	}
	
	public void addTableListener(MouseAdapter mouse)
	{
		table.addMouseListener(mouse);
	}
	
}