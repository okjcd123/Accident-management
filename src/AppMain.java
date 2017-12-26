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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class AppMain extends JFrame{

	private String initText = "<html>버튼 세부사항 보시려면<br/>마우스를 버튼위에 올려주세요</html>";
	private JLabel accInfo;
	private JLabel[] labels;
	private JPanel[] panels;
	private JButton[] btns;
	
	private JPanel primary;
	
	//버튼----------------------------------------------------------------------------------
	private JPanel bPanel;
	
	
	//JList---------------------------------------------------------------------------------
	private JList list;	
	private JScrollPane scroll;
	private String [] listStr = {"대한민국", "미국"};
	
	//사고 검색--------------------------------------------------------------------------------
	private JDialog diaSearch;
	private JPanel searchPanel;
	
	private JLabel siDolbl;
	private JLabel guGunlbl;
	private JComboBox siDo;
	private JComboBox guGun;
	
	//사고 등록--------------------------------------------------------------------------------
	private JDialog dia;
	private	String[] year = {"년도","전체","2000","2001","2002","2003","2004","2005","2006","2007","2008",
			"2009","2010","2011","2012","2013","2014","2015","2016","2017"};
	private String[] month = {"월","1","2","3","4","5","6","7","8","9","10","11","12",};
	private String[] day = {"일","1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",};
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
	
	private JPanel subPanel;
	JButton regBtn;
	
	//사고 수정/삭제----------------------------------------------------------------------------
	
	
	
	//사고 분석----------------------------------------------------------------------------
	
	
	
	
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
		
		/*accInfo = new JLabel("해당 날짜 사고 정보");
		accInfo.setBounds(0, 670, 1200, 30);
		accInfo.setVerticalAlignment(SwingConstants.TOP);
		accInfo.setHorizontalAlignment(SwingConstants.LEFT);
		primary.add(accInfo);
		 */

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
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setListData(listStr); //리스트의 데이터가 될 목록 설정
		//list.addListSelectionListener();
		
		scroll = new JScrollPane();
		scroll.setViewportView(list);
      	scroll.setBounds(20,110,1260, 510);
      	scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); //가로바정책
      	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //가로바정책
      	primary.add(scroll);
		
		add(primary);
		setVisible(true);
	}
	
	public void search() {
		
		diaSearch = new JDialog();
		diaSearch.setSize(new Dimension(400,300));
		diaSearch.setResizable(false);
		diaSearch.setTitle("사고 정보 검색");
		
		searchPanel = new JPanel();
		searchPanel.setBounds(0,0,400,300);
		searchPanel.setBackground(Color.white);
		searchPanel.setLayout(null);
		diaSearch.add(searchPanel);
		
		siDolbl = new JLabel("시/도");
		siDolbl.setBounds(0,0,50,150);
		searchPanel.add(siDolbl);
		
		guGunlbl = new JLabel("군/구");
		guGunlbl.setBounds(0,150,50,150);
		searchPanel.add(guGunlbl);
		
		siDo = new JComboBox();
		siDo.setBounds(50,0,250,50);
		searchPanel.add(siDo);
		
		guGun = new JComboBox();
		guGun.setBounds(50,150,250,50);
		searchPanel.add(guGun);
		
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
		JPanel loc = new JPanel();
		loc.setLayout(new GridLayout(1,2));
		JComboBox pro = new JComboBox(province);
		JComboBox tow = new JComboBox();
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
		JPanel time = new JPanel();
		time.setLayout(new GridLayout(1,3));
		JComboBox yearcb = new JComboBox(year);
		JComboBox monthcb = new JComboBox(month);
		JComboBox daycb = new JComboBox(day);
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
		JPanel casualty = new JPanel();
		casualty.setLayout(new GridLayout(1,4));
		JLabel tmp1 = new JLabel("사망자 수");
		JLabel tmp2 = new JLabel("부상자 수");
		casualty.add(tmp1); casualty.add(dead);
		casualty.add(tmp2); casualty.add(injured);
		rightPanel.add(casualty);
		
		//사고타입 입력부분
		rightPanel.add(accType);
		
		//위도 경도 입력부분
		JPanel locInfo = new JPanel();
		locInfo.setLayout(new GridLayout(1,4,70,0));
		JLabel laTmp = new JLabel("위도");
		JLabel loTmp = new JLabel("경도");
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
	
	

	
	public void modify() {
		JDialog dia = new JDialog();
		dia.setTitle("사고 정보 수정");
		dia.setSize(300,300);
		dia.setVisible(true);
	}
	public void delete() {
		JDialog dia = new JDialog();
		dia.setTitle("사고 사례 삭제");
		dia.setSize(300,300);
		dia.setVisible(true);
	}
	
	private class MouseListen implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			JButton btn = (JButton)arg0.getSource();
			for(int i=0;i<btns.length;i++) {
				if(btns[i] == btn) {
					if(i==0) {
						/*btns[i].setLayout(new GridLayout(3,1));
						btns[i].add(new JLabel("1. 기간, 시, 구 설정 이용 검색"));
						btns[i].add(new JLabel("2. 차번호를 이용한 차 정보 검색"));
						btns[i].add(new JLabel("3. 경찰 번호를 이용한 담당 경찰 검색"));*/
						btns[i].setText("");
						btns[i].setText("<html>1. 날짜, 시, 구 설정 이용 검색"
								+ "<br/>2. 차번호를 이용한 차 정보 검색"
								+ "<br/>3. 경찰 번호를 이용한 담당 경찰 검색</html>");
					}
					else if(i==1) {
						btns[i].setText("");
						btns[i].setText("<html>1. 직접 현장 정보 접수<br/>2. 메신저 통한 내용 접수</html>");
					}
					else if(i==2) {
						btns[i].setText("");
						btns[i].setText("1. 사고 조회 후 사고 사례 데이터 수정");
					}
					else {
						btns[i].setText("");
						btns[i].setText("<html>1. 처리된 사례 삭제<br/>2. 허위 사례 삭제</html>");
					}
				}
			}
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			JButton btn = (JButton)arg0.getSource();
			btn.setText(initText);
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
						modify();
					}
					else {
						delete();
					}
				}
			}
		}			
	}

}
