import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class AppMain extends JFrame{

	private String initText = "<html>버튼 세부사항 보시려면<br/>마우스를 버튼위에 올려주세요</html>";
	
	private JLabel[] labels;
	private JPanel[] panels;
	
	private JPanel primary;
	
	//버튼----------------------------------------------------------------------------------
	private JPanel bPanel;
	private JButton[] btns;
	
	//JTable---------------------------------------------------------------------------------
	private JTable table;
	private JScrollPane scroll;
	private String [] header = {"사고번호", "시/도", "구/군", "발생연", "월", "일", "사상자", "사망자", "부상자", "사고 유형"};
	private String contents[][] = {{"1", "서울특별시", "강남구", "2017", "02", "28", "1", "0" ,"1", "차대사람"}
	,{"1", "서울특별시", "강남구", "2017", "02", "28", "1", "0" ,"1", "차대차"}};
	
	//사고 검색--------------------------------------------------------------------------------
	private JDialog diaSearch;
	private JPanel searchUpPanel;
	private JPanel searchDownPanel;
	
	private JLabel siDolbl;
	private JLabel guGunlbl;
	private JComboBox siDo;
	private JComboBox guGun;
	
	//사고 등록--------------------------------------------------------------------------------
	private JDialog dia;
	private	String[] year = {"년도","전체","2000","2001","2002","2003","2004","2005","2006","2007","2008",
			"2009","2010","2011","2012","2013","2014","2015","2016","2017", "2018"};
	private String[] month = {"월","1","2","3","4","5","6","7","8","9","10","11","12",};
	private String[] day = {"일","1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private String[] province = {"전체","서울특별시","인천광역시","경기도"};
	private String[] sTown = {"노원구","도봉구","강남구","서초구","강서구","강동구","종로구","중구","중랑구","성북구","금천구","영등포구",
			"서대문구","은평구","동작구","마포구","송파구","광진구","용산구","양천구","구로구","성동구","관악구","동대문구","강북구"};
	private String[] iTown = {"중구","동구","남구","연수구","남동구","부평구","계양구","서구","강화군","옹진군"};
	private String[] gyTown = {"수원시","성남시","의정부시","안양시","부천시","광명시","평택시","동두천시","안산시","고양시","과천시","구리시","남양주시","오산시","시흥시",
			"군포시","의왕시","하남시","용인시","파주시","이천시","김포시","화성시","광주시","양주시","포천시","여주군","연천군","가평군","양평군"};
	
	private JPanel leftPanel;
	
	private JLabel label1;	//장소
	private JLabel label2; 	//날짜
	private	JLabel label3; 	//경찰번호
	private JLabel label4; 	//차번호
	private JLabel label5; 	//사상자 수
	private JLabel label6;	//사고 타입
	private JLabel label7; 	//"위도, 경도
	//private JLabel label8 //"등록"
	
	private JPanel rightPanel;
	
	private JTextField polno;
	private JTextField carno;
	private JTextField dead;
	private JTextField injured;
	private JTextField accType;
	private JTextField lati;
	private JTextField longi;
	
	//장소입력부분
	private JPanel loc;
	private JComboBox pro;
	private JComboBox tow;
	
	//날짜입력부분
	private JPanel time;
	private JComboBox yearcb;
	private JComboBox monthcb;
	private JComboBox daycb;
	
	//사상사주 입력 부분
	private JPanel casualty;
	private	JLabel tmp1;
	private	JLabel tmp2;
	
	//위도 경도 입력부분
	private	JPanel locInfo;
	private	JLabel laTmp;
	private	JLabel loTmp;
	
	//하단 패널
	private JPanel subPanel;
	private	JButton regBtn;
	
	//사고 수정/삭제-----------------------------------------------------------------------------------------
	private JDialog diaUpdate;
	private JPanel leftUpdatePanel;
	private JLabel caseNum;			//사건 번호
	
	private JLabel labelUpdate1;	//장소
	private JLabel labelUpdate2; 	//날짜
	private	JLabel labelUpdate3; 	//경찰번호
	private JLabel labelUpdate4; 	//차번호
	private JLabel labelUpdate5; 	//사상자 수
	private JLabel labelUpdate6;	//사고 타입
	private JLabel labelUpdate7; 	//"위도, 경도
	//private JLabel label8 //"등록"
	
	private JPanel rightUpdatePanel;
	
	private JPanel searchCaseNumPanel;	//케이스 검색 패널
	private JTextField caseNumTxt;		//사건 번호 입력란
	private JButton searchUpdateBtn;	//사건 번호 검색
	
	private JTextField polnoUpdate;
	private JTextField carnoUpdate;
	private JTextField deadUpdate;
	private JTextField injuredUpdate;
	private JTextField accTypeUpdate;
	private JTextField latiUpdate;
	private JTextField longiUpdate;
	
	//장소입력부분
	private JPanel locUpdate;
	private JComboBox proUpdate;
	private JComboBox towUpdate;
	
	//날짜입력부분
	private JPanel timeUpdate;
	private JComboBox yearcbUpdate;
	private JComboBox monthcbUpdate;
	private JComboBox daycbUpdate;
	
	//사상사주 입력 부분
	private JPanel casualtyUpdate;
	private	JLabel tmp1Update;
	private	JLabel tmp2Update;
	
	//위도 경도 입력부분
	private	JPanel locInfoUpdate;
	private	JLabel laTmpUpdate;
	private	JLabel loTmpUpdate;
	
	//하단 패널
	private JPanel subUpdatePanel;
	private JButton updateButton;
	private JButton deleteButton;
	
	//사고 분석----------------------------------------------------------------------------
	
	
	
	//하단 메세지-------------------------------------------------------------------------------
	private JLabel accInfo;
	//마우스 이벤트-----------------------------------------------------------------------------
	MouseAction action = new MouseAction();
	MouseListen mouseMove = new MouseListen();
	
	public AppMain() {
		
		setTitle("교통 사고 관리 시스템");
		setSize(Execute.WIDTH,Execute.HEIGHT);
		setResizable(false);
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
		//bPanel.setBackground(Color.cyan);
		bPanel.setBounds(0, 0, 1300, 110);
		
		btns = new JButton[4];
		btns[0] = new JButton("사고 정보 검색");
		btns[0].setBounds(120 + 20,10,200,90);
		bPanel.add(btns[0]);
		
		
		btns[1] = new JButton("사고 등록");
		btns[1].setBounds(120 + 20 + 280,10,200,90);
		bPanel.add(btns[1]);
		
		btns[2] = new JButton("사고 정보 수정/삭제");
		btns[2].setBounds(120 + 20 + 260 +20 +260 + 20,10,200,90);
		bPanel.add(btns[2]);
		
		btns[3] = new JButton("사고 분석");
		btns[3].setBounds(120 + 20 + 260 + 20 + 260 + 20 +260 + 20,10,200,90);
		
		//1300
		bPanel.add(btns[3]);
		
		for(int i=0;i<btns.length;i++) {
			btns[i].addActionListener(action);
			btns[i].addMouseListener(mouseMove);
		}
		primary.add(bPanel);
		
		
		//List 관련 사항------------------------------------------------------------------------------
		table = new JTable(contents, header);

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
		searchUpPanel.setBounds(0,0,400,150);
		searchUpPanel.setBackground(Color.white);
		searchUpPanel.setLayout(null);
		
		siDolbl = new JLabel("시/도");
		siDolbl.setBounds(0,40,50,50);
		searchUpPanel.add(siDolbl);
		
		siDo = new JComboBox();
		siDo.setBounds(100,40,250,50);
		searchUpPanel.add(siDo);
		
		searchDownPanel = new JPanel();
		searchDownPanel.setBounds(0,150,400,150);
		searchDownPanel.setBackground(Color.white);
		searchDownPanel.setLayout(null);
		
		guGunlbl = new JLabel("군/구");
		guGunlbl.setBounds(0,0,50,50);
		searchDownPanel.add(guGunlbl);
		
		guGun = new JComboBox();
		guGun.setBounds(100,0,250,50);
		searchDownPanel.add(guGun);
		
		diaSearch.add(searchUpPanel);
		diaSearch.add(searchDownPanel);
		
		diaSearch.setVisible(true);
	}

	public void registration() {
		dia = new JDialog();
		dia.setTitle("사고 등록");
		dia.setLayout(null);
		dia.setResizable(false);
		dia.setSize(810,700);
		
		//leftPanel 관련 사항 --------------------------------------------------
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		leftPanel.setLayout(new GridLayout(7,1));
		leftPanel.setBounds(0,0,100,600);
		dia.add(leftPanel);
		
		label1 = new JLabel("장소",JLabel.CENTER);
		label2 = new JLabel("날짜",JLabel.CENTER);
		label3 = new JLabel("경찰번호",JLabel.CENTER);
		label4 = new JLabel("차번호",JLabel.CENTER);
		label5 = new JLabel("사상자 수",JLabel.CENTER);
		label6 = new JLabel("사고 타입",JLabel.CENTER);
		label7 = new JLabel("위도, 경도",JLabel.CENTER);
		
		leftPanel.add(label1);
		leftPanel.add(label2);
		leftPanel.add(label3);
		leftPanel.add(label4);
		leftPanel.add(label5);
		leftPanel.add(label6);
		leftPanel.add(label7);
		
		//rightPanel 관련 사항 --------------------------------------------------
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(7,1));
		rightPanel.setBounds(100,0,700,600);
		dia.add(rightPanel);
		
		polno = new JTextField(10);
		carno = new JTextField(10);
		dead = new JTextField(10);
		injured = new JTextField(10);
		accType = new JTextField(10);
		lati = new JTextField(10);
		longi = new JTextField(10);

		//장소입력부분
		loc = new JPanel();
		loc.setLayout(new GridLayout(1,2));
		pro = new JComboBox(province);
		tow = new JComboBox();
		loc.add(pro);loc.add(tow);
		pro.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JComboBox tmp = (JComboBox)arg0.getSource();
				String select = (String)tmp.getSelectedItem();

				if(select.equals("전체")) {
					tow.setModel(new DefaultComboBoxModel());
				}
				else if(select.equals("서울특별시")) {
					tow.setModel(new DefaultComboBoxModel(sTown));
				}
				else if(select.equals("인천광역시")) {
					tow.setModel(new DefaultComboBoxModel(iTown));
				}
				else {
					tow.setModel(new DefaultComboBoxModel(gyTown));
				}
			}
		});
		rightPanel.add(loc);
		
		//날짜입력부분
		time = new JPanel();
		time.setLayout(new GridLayout(1,3));
		yearcb = new JComboBox(year);
		monthcb = new JComboBox(month);
		daycb = new JComboBox(day);
		time.add(yearcb);time.add(monthcb);time.add(daycb);
		yearcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JComboBox box = (JComboBox)arg0.getSource();
				String select = (String)box.getSelectedItem();

				if(select.equals("전체")) {
					monthcb.setModel(new DefaultComboBoxModel());
					daycb.setModel(new DefaultComboBoxModel());
				}
				else {
					monthcb.setModel(new DefaultComboBoxModel(month));
					daycb.setModel(new DefaultComboBoxModel(day));
				}
			}
			
		});
		rightPanel.add(time);
		
		//경찰번호 입력부분
		rightPanel.add(polno);
		rightPanel.add(carno);
		
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
		subPanel.setBounds(0,600,810,100);
		dia.add(subPanel);
		
		regBtn = new JButton("등록");
		regBtn.setBounds(405-20, 0, 40, 30);
		regBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JButton btn = (JButton)arg0.getSource();
				dia.dispose();
			}
		});
		subPanel.add(regBtn);
		
		dia.setVisible(true);
	}
	public void modifyDelete() {
		
		diaUpdate = new JDialog();
		diaUpdate.setTitle("사고 등록");
		diaUpdate.setLayout(null);
		diaUpdate.setResizable(false);
		diaUpdate.setSize(810,800);
		
		//leftPanel 관련 사항 --------------------------------------------------
		leftUpdatePanel = new JPanel();
		leftUpdatePanel.setBackground(Color.white);
		leftUpdatePanel.setLayout(new GridLayout(8,1));
		leftUpdatePanel.setBounds(0,0,100,700);
		diaUpdate.add(leftUpdatePanel);
		
		caseNum = new JLabel("사고 번호 입력", JLabel.CENTER);
		labelUpdate1 = new JLabel("장소",JLabel.CENTER);
		labelUpdate2 = new JLabel("날짜",JLabel.CENTER);
		labelUpdate3 = new JLabel("경찰번호",JLabel.CENTER);
		labelUpdate4 = new JLabel("차번호",JLabel.CENTER);
		labelUpdate5 = new JLabel("사상자 수",JLabel.CENTER);
		labelUpdate6 = new JLabel("사고 타입",JLabel.CENTER);
		labelUpdate7 = new JLabel("위도, 경도",JLabel.CENTER);
		
		leftUpdatePanel.add(caseNum);
		leftUpdatePanel.add(labelUpdate1);
		leftUpdatePanel.add(labelUpdate2);
		leftUpdatePanel.add(labelUpdate3);
		leftUpdatePanel.add(labelUpdate4);
		leftUpdatePanel.add(labelUpdate5);
		leftUpdatePanel.add(labelUpdate6);
		leftUpdatePanel.add(labelUpdate7);
		
		//rightPanel 관련 사항 --------------------------------------------------
		rightUpdatePanel = new JPanel();
		rightUpdatePanel.setLayout(new GridLayout(8,1));
		rightUpdatePanel.setBounds(100,0,700,700);
		diaUpdate.add(rightUpdatePanel);
		
		polnoUpdate = new JTextField(10);
		carnoUpdate = new JTextField(10);
		deadUpdate = new JTextField(10);
		injuredUpdate = new JTextField(10);
		accTypeUpdate = new JTextField(10);
		latiUpdate = new JTextField(10);
		longiUpdate = new JTextField(10);

		//사건 번호 입력 부분
		searchCaseNumPanel = new JPanel(new GridLayout(1,2));	//케이스 검색 패널
		caseNumTxt = new JTextField(10);		//사건 번호 입력란
		searchUpdateBtn = new JButton("검색");	//사건 번호 검색
		searchCaseNumPanel.add(caseNumTxt);
		searchCaseNumPanel.add(searchUpdateBtn);
		rightUpdatePanel.add(searchCaseNumPanel);
		
		//장소입력부분
		locUpdate = new JPanel();
		locUpdate.setLayout(new GridLayout(1,2));
		proUpdate = new JComboBox(province);
		towUpdate = new JComboBox();
		locUpdate.add(proUpdate);locUpdate.add(towUpdate);
		proUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JComboBox tmp = (JComboBox)arg0.getSource();
				String select = (String)tmp.getSelectedItem();

				if(select.equals("전체")) {
					towUpdate.setModel(new DefaultComboBoxModel());
				}
				else if(select.equals("서울특별시")) {
					towUpdate.setModel(new DefaultComboBoxModel(sTown));
				}
				else if(select.equals("인천광역시")) {
					towUpdate.setModel(new DefaultComboBoxModel(iTown));
				}
				else {
					towUpdate.setModel(new DefaultComboBoxModel(gyTown));
				}
			}
		});
		rightUpdatePanel.add(locUpdate);
		
		//날짜입력부분
		timeUpdate = new JPanel();
		timeUpdate.setLayout(new GridLayout(1,3));
		yearcbUpdate = new JComboBox(year);
		monthcbUpdate = new JComboBox(month);
		daycbUpdate = new JComboBox(day);
		timeUpdate.add(yearcbUpdate);timeUpdate.add(monthcbUpdate);timeUpdate.add(daycbUpdate);
		yearcbUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JComboBox box = (JComboBox)arg0.getSource();
				String select = (String)box.getSelectedItem();

				if(select.equals("전체")) {
					monthcbUpdate.setModel(new DefaultComboBoxModel());
					daycbUpdate.setModel(new DefaultComboBoxModel());
				}
				else {
					monthcbUpdate.setModel(new DefaultComboBoxModel(month));
					daycbUpdate.setModel(new DefaultComboBoxModel(day));
				}
			}
			
		});
		rightUpdatePanel.add(timeUpdate);
		
		//경찰번호 입력부분
		rightUpdatePanel.add(polnoUpdate);
		rightUpdatePanel.add(carnoUpdate);
		
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
		subUpdatePanel.setBounds(0,700,810,100);
		
		updateButton = new JButton("수정");
		updateButton.setBounds(600,0,80,50);
		subUpdatePanel.add(updateButton);
		
		deleteButton = new JButton("삭제");
		deleteButton.setBounds(690, 0, 80,50);
		subUpdatePanel.add(deleteButton);
		
		diaUpdate.add(subUpdatePanel);
		
		
		
		diaUpdate.setVisible(true);
	}
	
	public void analysis()
	{
		new AccidentAnalysis();
	}
	
	private class MouseListen implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	private class MouseAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton btn = (JButton)arg0.getSource();
			for(int i=0;i<btns.length;i++) {
				if(btns[i]==btn) {
					if(i==0) {
						search();
					}
					else if(i==1) {
						registration();
					}
					else if(i==2) {
						modifyDelete();
					}
					else
					{
						analysis();
					}
				}
			}
		}			
	}
}

