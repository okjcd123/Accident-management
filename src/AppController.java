import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class AppController {
	
	protected String[] sTown = {"노원구","도봉구","강남구","서초구","강서구","강동구","종로구","중구","중랑구","성북구","금천구","영등포구",
			"서대문구","은평구","동작구","마포구","송파구","광진구","용산구","양천구","구로구","성동구","관악구","동대문구","강북구"};
	protected String[] iTown = {"중구","동구","남구","연수구","남동구","부평구","계양구","서구","강화군","옹진군"};
	protected String[] gyTown = {"수원시","성남시","의정부시","안양시","부천시","광명시","평택시","동두천시","안산시","고양시","과천시","구리시","남양주시","오산시","시흥시",
			"군포시","의왕시","하남시","용인시","파주시","이천시","김포시","화성시","광주시","양주시","포천시","여주군","연천군","가평군","양평군"};
	protected String[] month = {"월","1","2","3","4","5","6","7","8","9","10","11","12"};
	
	protected String[] day = {"일","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
			"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	

	public AppController()
	{
		AppManager.CreateInstance().setAppController(this);
		
		AppManager.CreateInstance().getAppMain().introPanel.addActionLoginButtonListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						String id = AppManager.CreateInstance().getAppMain().introPanel.idField.getText();
						String pwd = new String(AppManager.CreateInstance().getAppMain().introPanel.pwField.getPassword());
						if(AppManager.CreateInstance().getAccidentCaseDAO().connectTest(id, pwd))
						{
							AppManager.CreateInstance().getAppMain().getContentPane().removeAll();
							AppManager.CreateInstance().getAppMain().getContentPane().add(AppManager.CreateInstance().getAppMain().primary);
							AppManager.CreateInstance().getAppMain().revalidate();
							AppManager.CreateInstance().getAppMain().primary.requestFocus();
							AppManager.CreateInstance().getAppMain().status = true;
							AppManager.CreateInstance().getAppMain().repaint();
							
						}
						else
						{
							AppManager.CreateInstance().getAppMain().introPanel.idField.setText("오류");
							AppManager.CreateInstance().getAppMain().introPanel.pwField.setText("");
						}
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionButtonListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JButton btn = (JButton)arg0.getSource();
						for(int i=0;i<AppManager.CreateInstance().getAppMain().btns.length;i++) {
							if(AppManager.CreateInstance().getAppMain().btns[i]==btn) {
								if(i==0) {
									AppManager.CreateInstance().getAppMain().search();
								}
								else if(i==1) {
									AppManager.CreateInstance().getAppMain().registration();
								}
								else if(i==2) {
									AppManager.CreateInstance().getAppMain().modifyDelete();
								}
								else
								{
									AppManager.CreateInstance().getAppMain().analysis();
								}
							}
						}
					}			
				});
		AppManager.CreateInstance().getAppMain().addActionSearchProListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JComboBox tmp = (JComboBox)arg0.getSource();
						String select = (String)tmp.getSelectedItem();
					
						if(select.equals("전체")) {
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel());
						}
						else if(select.equals("서울특별시")) {
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel(sTown));
						}
						else if(select.equals("인천광역시")) {
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel(iTown));
						}
						else {
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel(gyTown));
						}	
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionSearchListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) {
						
						JButton btn = (JButton)e.getSource();
						if(btn == AppManager.CreateInstance().getAppMain().searchButton)
						{
							ArrayList <AccidentCase> outputDatas = new ArrayList<AccidentCase>();
							//데이터 받아오기
							String pro = (String)AppManager.CreateInstance().getAppMain().siDo.getSelectedItem();
							String tow = (String)AppManager.CreateInstance().getAppMain().guGun.getSelectedItem();
							String year = (String)AppManager.CreateInstance().getAppMain().yearcbSearch.getSelectedItem();
							String month = (String)AppManager.CreateInstance().getAppMain().monthcbSearch.getSelectedItem();
							
							if(pro.equals("전체"))
							{
								if(year == "년도" || month == "월")
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().getAll();	
								}
								else
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(year,month);
								}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
							}
							else
							{
								outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseLoca(pro, tow);					
							}
							// 출력 절차
							AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table");	//table 화면 보여주기.
							AppManager.CreateInstance().getAppMain().basicTable.setRowCount(0);
							//Table 데이터 다시 채우기
							for(AccidentCase outputCase: outputDatas)
							{
								String [] temp = {Integer.toString(outputCase.getCscode()),
										outputCase.getProvince(),outputCase.getTown(), outputCase.getYear(),
										outputCase.getMonth(), outputCase.getDay(),Integer.toString(outputCase.getCasulity()),
										Integer.toString(outputCase.getDead()),Integer.toString(outputCase.getInjured()),
										outputCase.getActype()};
								AppManager.CreateInstance().getAppMain().basicTable.addRow(temp);				
							}
							AppManager.CreateInstance().getAppMain().diaSearch.dispose();			
						}
						else
						{
							
						}
					}
			
				});
	
		AppManager.CreateInstance().getAppMain().addActionRegisterProListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox tmp = (JComboBox)e.getSource();
						String select = (String)tmp.getSelectedItem();

						if(select.equals("전체")) {
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel());
						}
						else if(select.equals("서울특별시")) {
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel(sTown));
						}
						else if(select.equals("인천광역시")) {
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel(iTown));
						}
						else {
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel(gyTown));
						}	
					}
				});


		AppManager.CreateInstance().getAppMain().addActionRegisterListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0) {
						Object obj = arg0.getSource();
						
						if(obj == AppManager.CreateInstance().getAppMain().regBtn)
						{
							//데이터
							boolean insertFlag;
							
							AccidentCase tempCase = new AccidentCase();
							tempCase.setProvince((String)AppManager.CreateInstance().getAppMain().pro.getSelectedItem());
							tempCase.setTown((String)AppManager.CreateInstance().getAppMain().tow.getSelectedItem());
							tempCase.setYear((String)AppManager.CreateInstance().getAppMain().yearcb.getSelectedItem());
							tempCase.setMonth((String)AppManager.CreateInstance().getAppMain().monthcb.getSelectedItem());
							tempCase.setDay((String)AppManager.CreateInstance().getAppMain().daycb.getSelectedItem());
							tempCase.setPoliceno((String)AppManager.CreateInstance().getAppMain().polno.getText());
							tempCase.setDead(Integer.parseInt(AppManager.CreateInstance().getAppMain().dead.getText()));
							tempCase.setInjured(Integer.parseInt(AppManager.CreateInstance().getAppMain().injured.getText()));
							tempCase.setCasulity();
							tempCase.setActype((String)AppManager.CreateInstance().getAppMain().accType.getSelectedItem());
							tempCase.setLatitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().lati.getText()));
							tempCase.setLongitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().longi.getText()));
							
							insertFlag = AppManager.CreateInstance().getAccidentCaseDAO().insertCase(tempCase);
							
							//가져오는 부분--------------------------------------------------------------------------------
							if(insertFlag == true)
							{
								int maxIndex = AppManager.CreateInstance().getAccidentCaseDAO().getNewCaseCode();
								AccidentCase outputCase = new AccidentCase();
								outputCase = AppManager.CreateInstance().getAccidentCaseDAO().getCase(maxIndex);
								String [] temp = {Integer.toString(outputCase.getCscode()),
										outputCase.getProvince(),outputCase.getTown(), outputCase.getYear(),
										outputCase.getMonth(), outputCase.getDay(),Integer.toString(outputCase.getCasulity()),
										Integer.toString(outputCase.getDead()),Integer.toString(outputCase.getInjured()),
										outputCase.getActype()};
							
								//Table 초기화
								AppManager.CreateInstance().getAppMain().basicTable.setRowCount(0);
								
								//Table 데이터 다시 채우기
								AppManager.CreateInstance().getAppMain().basicTable.addRow(temp);
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table");	//table 화면 보여주기.
								AppManager.CreateInstance().getAppMain().dia.dispose();		
							}
							else
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "등록 실패!", "경고", JOptionPane.PLAIN_MESSAGE);
								AppManager.CreateInstance().getAppMain().dia.dispose();
							}
						}		
						
					}
				});
		AppManager.CreateInstance().getAppMain().addActionUpdateProListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						JComboBox tmp = (JComboBox)arg0.getSource();
						String select = (String)tmp.getSelectedItem();
						
						if(select.equals("전체")) {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel());
						}
						else if(select.equals("서울특별시")) {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel(sTown));
						}
						else if(select.equals("인천광역시")) {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel(iTown));
						}
						else {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel(gyTown));
						}
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionUpdateSearchListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0)
					{
						Object obj = arg0.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().searchUpdateBtn)
						{
							AccidentCase temp = new AccidentCase();
							int accNum = Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText());
							temp = AppManager.CreateInstance().getAccidentCaseDAO().getCase(accNum);
							
							if(temp != null)
							{
								AppManager.CreateInstance().getAppMain().proUpdate.setSelectedItem(temp.getProvince());
								AppManager.CreateInstance().getAppMain().towUpdate.setSelectedItem(temp.getTown());
								AppManager.CreateInstance().getAppMain().yearcbUpdate.setSelectedItem(temp.getYear());
								AppManager.CreateInstance().getAppMain().monthcbUpdate.setSelectedItem(temp.getMonth());
								AppManager.CreateInstance().getAppMain().daycbUpdate.setSelectedItem(temp.getDay());
								AppManager.CreateInstance().getAppMain().polnoUpdate.setText(temp.getPoliceno());
								AppManager.CreateInstance().getAppMain().deadUpdate.setText(Integer.toString(temp.getDead()));
								AppManager.CreateInstance().getAppMain().injuredUpdate.setText(Integer.toString(temp.getInjured()));
								AppManager.CreateInstance().getAppMain().accTypeUpdate.setSelectedItem(temp.getActype());
								AppManager.CreateInstance().getAppMain().latiUpdate.setText(Double.toString(temp.getLatitude()));
								AppManager.CreateInstance().getAppMain().longiUpdate.setText(Double.toString(temp.getLongitude()));
							}
							else
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "사고번호를 정확히 입력하시오!", "경고", JOptionPane.PLAIN_MESSAGE);
							}
						}
						
					}
					
				});
		
		AppManager.CreateInstance().getAppMain().addActionUpdateListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0)
					{
						Object obj = arg0.getSource();
						
						if(obj == AppManager.CreateInstance().getAppMain().updateButton)
						{
							
							//모든 정보 가져와서 tempCase 에 저장하기.
							AccidentCase tempCase = new AccidentCase();
							boolean succFlag;
							
							int caseNum = Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText());
							
							tempCase.setCscode(Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText()));
							tempCase.setProvince((String)AppManager.CreateInstance().getAppMain().proUpdate.getSelectedItem());
							tempCase.setTown((String)AppManager.CreateInstance().getAppMain().towUpdate.getSelectedItem());
							tempCase.setYear((String)AppManager.CreateInstance().getAppMain().yearcbUpdate.getSelectedItem());
							tempCase.setMonth((String)AppManager.CreateInstance().getAppMain().monthcbUpdate.getSelectedItem());
							tempCase.setDay((String)AppManager.CreateInstance().getAppMain().daycbUpdate.getSelectedItem());
							tempCase.setPoliceno((String)AppManager.CreateInstance().getAppMain().polnoUpdate.getText());
							tempCase.setDead(Integer.parseInt(AppManager.CreateInstance().getAppMain().deadUpdate.getText()));
							tempCase.setInjured(Integer.parseInt(AppManager.CreateInstance().getAppMain().injuredUpdate.getText()));
							tempCase.setCasulity();
							tempCase.setActype((String)AppManager.CreateInstance().getAppMain().accTypeUpdate.getSelectedItem());
							tempCase.setLatitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().latiUpdate.getText()));
							tempCase.setLongitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().longiUpdate.getText()));
							
							//tempCase 업데이트 실시
							succFlag = AppManager.CreateInstance().getAccidentCaseDAO().updateCase(tempCase);
							
							if(succFlag == true)
							{
								//다시 가져와서 Table에 뿌리기
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table");
								
								AccidentCase outputCase = new AccidentCase();
								outputCase = AppManager.CreateInstance().getAccidentCaseDAO().getCase(caseNum);
								
								String [] temp = {Integer.toString(outputCase.getCscode()),
										outputCase.getProvince(),outputCase.getTown(), outputCase.getYear(),
										outputCase.getMonth(), outputCase.getDay(),Integer.toString(outputCase.getCasulity()),
										Integer.toString(outputCase.getDead()),Integer.toString(outputCase.getInjured()),
										outputCase.getActype()};
							
								//Table 초기화
								AppManager.CreateInstance().getAppMain().basicTable.setRowCount(0);
								//Table 데이터 다시 채우기
								AppManager.CreateInstance().getAppMain().basicTable.addRow(temp);
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table");	//table 화면 보여주기.
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
										
							}
							else
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "수정 실패!!", "경고", JOptionPane.PLAIN_MESSAGE);
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
							}
						}
						else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)
						{
							boolean delSuccess;
							delSuccess = AppManager.CreateInstance().getAccidentCaseDAO().deleteCase(Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText()));
							
							if(delSuccess == true)
							{
								//화면 CardLayout 으로 사진 으로 전환
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "image");
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();				
							}
							else
							{	
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "삭제 실패!!", "경고", JOptionPane.PLAIN_MESSAGE);
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
							}
							
						}
					}
					
				});
		AppManager.CreateInstance().getAppMain().addTableListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(e.getButton() ==1)
						{
							
						}
						if(e.getClickCount() ==2)
						{
							int row = AppManager.CreateInstance().getAppMain().table.getSelectedRow();
							int accNum = Integer.parseInt((String)AppManager.CreateInstance().getAppMain().table.getValueAt(row, 0));
			
							AccidentCase tempCase = new AccidentCase();
							tempCase = AppManager.CreateInstance().getAccidentCaseDAO().getCase(accNum);
							String polNum = tempCase.getPoliceno();
							Police pol =new Police();
							pol = AppManager.CreateInstance().getAccidentCaseDAO().getPolice(polNum);
							
							new DetailInfo(tempCase, pol);
						}
						if(e.getButton() == 3)
						{
							
						}
					}
					
				});
		}
	public class detailInfoAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}




