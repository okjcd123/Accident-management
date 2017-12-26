import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AppMain extends JFrame implements ActionListener,MouseListener{

	String initText = "<html>버튼 세부사항 보시려면<br/>마우스를 버튼위에 올려주세요</html>";
	JLabel accInfo;
	JLabel[] labels;
	JPanel[] panels;
	JButton[] btns;
	JList[] list;
	AccidentDataDAO dao;
	ArrayList<AccidentData> datas;
	Container c;

	public AppMain() {
		setTitle("사고 관리 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(null);


		//해당 날짜 사고 정보들 실시간? 띄우기
		accInfo = new JLabel("해당 날짜 사고 정보");
		accInfo.setBounds(0, 0, 1200, 30);
		c.add(accInfo);


		//기능 버튼들 추가
		labels = new JLabel[5];
		labels[0] = new JLabel("사고 정보 검색",JLabel.CENTER);
		labels[1] = new JLabel("사고 등록",JLabel.CENTER);
		labels[2] = new JLabel("사고 정보 수정",JLabel.CENTER);
		labels[3] = new JLabel("사고 사례 삭제",JLabel.CENTER);


		btns = new JButton[4];
		btns[0] = new JButton();
		btns[1] = new JButton();
		btns[2] = new JButton();
		btns[3] = new JButton();

		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(4,2,5,5));
		bPanel.setBounds(0, 30, 500, 570);
		for(int i=0;i<btns.length;i++) {
			labels[i].setSize(150,140);
			bPanel.add(labels[i]);
			btns[i].setSize(350,140);
			bPanel.add(btns[i]);
			btns[i].addActionListener(this);
			btns[i].addMouseListener(this);
			btns[i].setText(initText);
		}

		c.add(bPanel);

		//메신저자리 임시버튼
		JButton b = new JButton("메신저부분");
		b.setBounds(820, 40, 400, 570);

		c.add(b);


		setSize(1300,700);
		setVisible(true);
	}
	public void search() {
		JDialog dia = new JDialog();
		String[] tmp1 = {"1","2","3"};
		String[] tmp2 = {"4","5","6"};
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel2.setLayout(new FlowLayout());
		BorderLayout layout = (BorderLayout)dia.getLayout();
		for(int i=0;i<10;i++) {
			panel1.add(new JList(tmp1));
		}
		for(int i=0;i<10;i++) {
			panel2.add(new JList(tmp2));
		}
		
		String[] list = {"날짜, 시, 구 설정 이용 검색","차번호를 이용한 차 정보 검색","경찰 번호를 이용한 담당 경찰 검색"};
		JComboBox cb = new JComboBox(list);
		Component[] compoList = {panel1,panel2};
		dia.setTitle("사고 정보 검색");
		dia.setLayout(new BorderLayout());
		dia.add(cb,BorderLayout.NORTH);
		
		dia.add(panel1,BorderLayout.CENTER);
		cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JComboBox cb = (JComboBox)arg0.getSource();
				String tmp = (String)cb.getSelectedItem();
				
				
				System.out.println(layout.getLayoutComponent(BorderLayout.CENTER));
				//Component[] list = dia.getComponents();
				if(tmp.equals("날짜, 시, 구 설정 이용 검색")) {

					//해당 위치에 컴포넌트 새로고침 하고 싶은데 안됨
					//dia.remove(layout.getLayoutComponent(BorderLayout.CENTER));
					dia.add(panel1,BorderLayout.CENTER);
					dia.revalidate();
					dia.repaint();
				}
				else if(tmp.equals("차번호를 이용한 차 정보 검색")) {
					dia.remove(layout.getLayoutComponent(BorderLayout.CENTER));
					dia.add(panel2,BorderLayout.CENTER);
					dia.revalidate();
					dia.repaint();
				}
				else {
					
				}
			}
			
		});
		
		
		/*datas = dao.getAll();
		int col = dao.entireColNum(), row = dao.entireRowNum();

		list = new JList[col];*/
		//list[0] = 
		//해당 정보에 관련된 내용들을 행과 칼럼수 대로 출력
		/*JPanel information = new JPanel();
		information.setLayout(new GridLayout(1,col));
		for(int i=0;i<col;i++) {

		}*/
		dia.setSize(1000,600);
		dia.setVisible(true);
	}
	
	public void registration() {
		JDialog dia = new JDialog();
		String[] year = {"년도","2000","2001","2002","2003","2004","2005","2006","2007","2008",
				"2009","2010","2011","2012","2013","2014","2015","2016","2017"};
		String[] month = {"월","1","2","3","4","5","6","7","8","9","10","11","12",};
		String[] day = {"일","1","2","3","4","5","6","7","8","9","10","11","12","13",
				"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",};
		String[] province = {"전체","서울특별시","부산광역시","대전광역시","대구광역시","인천광역시","광주광역시","울산광역시"};
		String[] sTown = {"노원구","도봉구","강남구","서초구","강서구","강동구","종로구","중구","중랑구","성북구","금천구","영등포구",
				"서대문구","은평구","동작구","마포구","송파구","광진구","용산구","양천구","구로구","성동구","관악구","동대문구","강북구"};
		String[] bTown = {"중구","서구","동구","영도구","부산진구","동래구","남구","북구","해운대구","사하구","금정구","강서구","연제구","수영구","사상수","기장군"};
		String [] dgTown = {"중구","동구","서구","남구","북구","수서구","달서구","달성군"};
		String[] iTown = {"중구","동구","남구","연수구","남동구","부평구","계양구","서구","강화군","옹진군"};
		String[] gwTown = {"동구","서구","남구","북구","광산구"};
		String[] dzTown = {"동구","중구","서구","유성구","대덕구"};
		String[] wTown = {"중구","동구","남구","북구","울주군"};

		dia.setTitle("사고 등록");
		//dia.setLayout(null);
		dia.setLayout(new GridLayout(9,2));
		JLabel label1 = new JLabel("장소",JLabel.CENTER);
		JLabel label2 = new JLabel("날짜",JLabel.CENTER);
		JLabel label3 = new JLabel("경찰번호",JLabel.CENTER);
		JLabel label4 = new JLabel("차번호",JLabel.CENTER);
		JLabel label5 = new JLabel("사상자 수",JLabel.CENTER);
		JLabel label6 = new JLabel("사고 타입",JLabel.CENTER);
		JLabel label7 = new JLabel("위도, 경도",JLabel.CENTER);
		JLabel label8 = new JLabel("등록",JLabel.CENTER);
		
		
		JTextField polno = new JTextField(10);
		JTextField carno = new JTextField(10);
		JTextField dead = new JTextField(10);
		JTextField injured = new JTextField(10);
		JTextField accType = new JTextField(10);
		JTextField lati = new JTextField(10);
		JTextField longi = new JTextField(10);

		//장소입력부분
		JPanel loc = new JPanel();
		loc.setLayout(new GridLayout(1,2));
		JComboBox pro = new JComboBox(province);
		JComboBox tow = new JComboBox();
		loc.add(pro);loc.add(tow);
		pro.addActionListener(new ActionListener() {

			@Override
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
				else if(select.equals("부산광역시")) {
					tow.setModel(new DefaultComboBoxModel(bTown));
				}
				else if(select.equals("대전광역시")) {
					tow.setModel(new DefaultComboBoxModel(dzTown));
				}
				else if(select.equals("대구광역시")) {
					tow.setModel(new DefaultComboBoxModel(dgTown));
				}
				else if(select.equals("인천광역시")) {
					tow.setModel(new DefaultComboBoxModel(iTown));
				}
				else if(select.equals("광주광역시")) {
					tow.setModel(new DefaultComboBoxModel(gwTown));
				}
				else {
					tow.setModel(new DefaultComboBoxModel(wTown));
				}

			}

		});
		dia.add(label1);
		dia.add(loc);
		
		//날짜입력부분
		JPanel time = new JPanel();
		time.setLayout(new GridLayout(1,3));
		JComboBox yearcb = new JComboBox(year);
		JComboBox monthcb = new JComboBox(month);
		JComboBox daycb = new JComboBox(day);
		time.add(yearcb);time.add(monthcb);time.add(daycb);
		
		dia.add(label2);
		dia.add(time);
		
		//경찰번호 입력부분
		dia.add(label3);
		dia.add(polno);
		
		//차번호 입력부분
		dia.add(label4);
		dia.add(carno);
		
		//사상사주 입력 부분
		JPanel casualty = new JPanel();
		casualty.setLayout(new GridLayout(1,4));
		JLabel tmp1 = new JLabel("사망자 수");
		JLabel tmp2 = new JLabel("부상자 수");
		casualty.add(tmp1); casualty.add(dead);
		casualty.add(tmp2); casualty.add(injured);
		dia.add(label5);
		dia.add(casualty);
		
		//사고타입 입력부분
		dia.add(label6);
		dia.add(accType);
		
		//위도 경도 입력부분
		JPanel locInfo = new JPanel();
		locInfo.setLayout(new GridLayout(1,4,70,0));
		JLabel laTmp = new JLabel("위도");
		JLabel loTmp = new JLabel("경도");
		locInfo.add(laTmp);locInfo.add(lati);
		locInfo.add(loTmp);locInfo.add(longi);
		
		dia.add(label7);
		dia.add(locInfo);
		
		//등록버튼
		JButton regBtn = new JButton("누르기 전에 검토 한번 부탁드립니다.");
		dia.add(label8);
		dia.add(regBtn);
		regBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JButton btn = (JButton)arg0.getSource();
				dia.dispose();
			}
			
		});
		
		dia.setSize(800,700);
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AppMain();

	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
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
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		JButton btn = (JButton)arg0.getSource();
		btn.setText(initText);
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
