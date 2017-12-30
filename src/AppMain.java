
import java.awt.Color;
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
	protected String[] province = {"서울특별시","인천광역시","경기도"};
	protected String[] accOptionType = {"차대차", "차대사람", "차량단독"};

	//기본 옵션 패널 라벨----------------------------------------------------------------------
	protected JPanel[] panels;
	protected JPanel primary;
	protected JLabel[] labels;
	
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
	protected	JButton regBtn = new JButton("등록");
	
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
	protected JTextField caseNumTxt;		//사건 번호 입력란
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
		
		//Table 관련 사항------------------------------------------------------------------------------
	
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
      	scroll.setBounds(20,110,1260, 510);
      	scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); //가로바정책
      	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //가로바정책
      	primary.add(scroll);
		
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
		dia.setSize(810,600);
		
		//leftPanel 관련 사항 --------------------------------------------------
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		leftPanel.setLayout(new GridLayout(6,1));
		leftPanel.setBounds(0,0,100,500);
		dia.add(leftPanel);
		
		label1 = new JLabel("장소",JLabel.CENTER);
		label2 = new JLabel("날짜",JLabel.CENTER);
		label3 = new JLabel("경찰번호",JLabel.CENTER);
		label5 = new JLabel("사상자 수",JLabel.CENTER);
		label6 = new JLabel("사고 타입",JLabel.CENTER);
		label7 = new JLabel("위도, 경도",JLabel.CENTER);
		
		leftPanel.add(label1);
		leftPanel.add(label2);
		leftPanel.add(label3);
		leftPanel.add(label5);
		leftPanel.add(label6);
		leftPanel.add(label7);
		
		//rightPanel 관련 사항 --------------------------------------------------
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(6,1));
		rightPanel.setBounds(100,0,700,500);
		dia.add(rightPanel);
		
		polno = new JTextField(10);
		dead = new JTextField(10);
		injured = new JTextField(10);
		lati = new JTextField(10);
		longi = new JTextField(10);

		//장소입력부분
		loc = new JPanel();
		loc.setLayout(new GridLayout(1,2));
		loc.add(pro);loc.add(tow);
		rightPanel.add(loc);
		
		//날짜입력부분
		time = new JPanel();
		time.setLayout(new GridLayout(1,3));
		time.add(yearcb);time.add(monthcb);time.add(daycb);
		rightPanel.add(time);
		
		//경찰번호 입력부분
		rightPanel.add(polno);
		
		//사상사주 입력 부분
		casualty = new JPanel();
		casualty.setLayout(new GridLayout(1,4));
		tmp1 = new JLabel("사망자 수");
		tmp2 = new JLabel("부상자 수");
		casualty.add(tmp1); casualty.add(dead);
		casualty.add(tmp2); casualty.add(injured);
		rightPanel.add(casualty);
		
		//사고타입 입력부분
		rightPanel.add(accType);
		
		//위도 경도 입력부분
		locInfo = new JPanel();
		locInfo.setLayout(new GridLayout(1,4,70,0));
		laTmp = new JLabel("위도");
		loTmp = new JLabel("경도");
		locInfo.add(laTmp);locInfo.add(lati);
		locInfo.add(loTmp);locInfo.add(longi);
		rightPanel.add(locInfo);

		//subPanel----------------------------------------------------------------------------
		subPanel = new JPanel();
		subPanel.setBounds(0,500,810,100);
		dia.add(subPanel);
		
		regBtn.setBounds(405-20, 0, 40, 30);
		subPanel.add(regBtn);
		
		dia.setVisible(true);
	}
	public void modifyDelete() {
		
		diaUpdate = new JDialog();
		diaUpdate.setTitle("사고 수정 / 삭제");
		diaUpdate.setLayout(null);
		diaUpdate.setResizable(false);
		diaUpdate.setSize(810,700);
		
		//leftPanel 관련 사항 --------------------------------------------------
		leftUpdatePanel = new JPanel();
		leftUpdatePanel.setBackground(Color.white);
		leftUpdatePanel.setLayout(new GridLayout(7,1));
		leftUpdatePanel.setBounds(0,0,100,600);
		diaUpdate.add(leftUpdatePanel);
		
		caseNum = new JLabel("사고 번호 입력", JLabel.CENTER);
		labelUpdate1 = new JLabel("장소",JLabel.CENTER);
		labelUpdate2 = new JLabel("날짜",JLabel.CENTER);
		labelUpdate3 = new JLabel("경찰번호",JLabel.CENTER);
		labelUpdate5 = new JLabel("사상자 수",JLabel.CENTER);
		labelUpdate6 = new JLabel("사고 타입",JLabel.CENTER);
		labelUpdate7 = new JLabel("위도, 경도",JLabel.CENTER);
		
		leftUpdatePanel.add(caseNum);
		leftUpdatePanel.add(labelUpdate1);
		leftUpdatePanel.add(labelUpdate2);
		leftUpdatePanel.add(labelUpdate3);
		leftUpdatePanel.add(labelUpdate5);
		leftUpdatePanel.add(labelUpdate6);
		leftUpdatePanel.add(labelUpdate7);
		
		//rightPanel 관련 사항 --------------------------------------------------
		rightUpdatePanel = new JPanel();
		rightUpdatePanel.setLayout(new GridLayout(7,1));
		rightUpdatePanel.setBounds(100,0,700,600);
		diaUpdate.add(rightUpdatePanel);
		
		//사건 번호 입력 부분
		searchCaseNumPanel = new JPanel(new GridLayout(1,2));	//케이스 검색 패널
		caseNumTxt = new JTextField(10);		//사건 번호 입력란
		
		searchCaseNumPanel.add(caseNumTxt);
		searchCaseNumPanel.add(searchUpdateBtn);
		rightUpdatePanel.add(searchCaseNumPanel);
		
		//장소입력부분
		locUpdate = new JPanel();
		locUpdate.setLayout(new GridLayout(1,2));
		locUpdate.add(proUpdate);locUpdate.add(towUpdate);
		rightUpdatePanel.add(locUpdate);
		
		//날짜입력부분
		timeUpdate = new JPanel();
		timeUpdate.setLayout(new GridLayout(1,3));
		timeUpdate.add(yearcbUpdate);timeUpdate.add(monthcbUpdate);timeUpdate.add(daycbUpdate);
		rightUpdatePanel.add(timeUpdate);
		
		//경찰번호 입력부분
		rightUpdatePanel.add(polnoUpdate);
		
		//사상사주 입력 부분
		casualtyUpdate= new JPanel();
		casualtyUpdate.setLayout(new GridLayout(1,4));
		tmp1Update = new JLabel("사망자 수");
		tmp2Update = new JLabel("부상자 수");
		casualtyUpdate.add(tmp1Update); casualtyUpdate.add(deadUpdate);
		casualtyUpdate.add(tmp2Update); casualtyUpdate.add(injuredUpdate);
		rightUpdatePanel.add(casualtyUpdate);
		
		//사고타입 입력부분
		rightUpdatePanel.add(accTypeUpdate);
		
		//위도 경도 입력부분
		locInfoUpdate = new JPanel();
		locInfoUpdate.setLayout(new GridLayout(1,4,70,0));
		laTmpUpdate = new JLabel("위도");
		loTmpUpdate = new JLabel("경도");
		locInfoUpdate.add(laTmpUpdate);locInfoUpdate.add(latiUpdate);
		locInfoUpdate.add(loTmpUpdate);locInfoUpdate.add(longiUpdate);
		rightUpdatePanel.add(locInfoUpdate);

		//subPanel----------------------------------------------------------------------------
		//diaUpdate.setSize(810,800);
		subUpdatePanel = new JPanel();
		subUpdatePanel.setLayout(null);
		subUpdatePanel.setBounds(0,600,810,100);
		
		updateButton.setBounds(600,0,80,50);
		subUpdatePanel.add(updateButton);
		
		deleteButton.setBounds(690, 0, 80,50);
		subUpdatePanel.add(deleteButton);
		
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