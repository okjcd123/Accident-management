import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class AppController {
	
	protected boolean searchUpdateFlag;
	protected String contents[][] = {{"133333", "인천광역시", "미친구", "2017", "02", "28", "1", "0" ,"1", "차대사람"}
	,{"1", "서울특별시", "강남구", "2017", "02", "28", "1", "0" ,"1", "차대차"}};
	
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
		searchUpdateFlag = false;
		
		AppManager.CreateInstance().setAppController(this);
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

						if(select.equals("서울특별시")) {
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
							//데이터 받아오기
							
							// 출력 절차
							
							System.out.println("해해");
							AppManager.CreateInstance().getAppMain().diaSearch.dispose();			
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
							//AppManager.CreateInstance().getAccidentCaseDAO().insertCase(tempCase);
							AppManager.CreateInstance().getAppMain().dia.dispose();
							
							//가져오는 부분 보류--------------------------------------------------------------------------------
							
							//Table 초기화
							AppManager.CreateInstance().getAppMain().basicTable.setRowCount(0);
							//Table 데이터 다시 채우기
							AppManager.CreateInstance().getAppMain().basicTable.addRow(contents[0]);
							
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
								searchUpdateFlag = true;
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
							//번호 가져오기
							AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
							searchUpdateFlag = false;					
						}
						else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)
						{
							//번호 가져오기
							AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
							searchUpdateFlag = false;
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
							System.out.println(accNum);
							new DetailInfo(accNum);
						}
						if(e.getButton() == 3)
						{
							
						}
					}
					
				});
		}
	
}



/*	AppManager.CreateInstance().getAppMain().addActionRegisterYearListener(new ActionListener()
{
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JComboBox box = (JComboBox)arg0.getSource();
		String select = (String)box.getSelectedItem();
		
		if(select.equals("전체")) {
			AppManager.CreateInstance().getAppMain().monthcb.setModel(new DefaultComboBoxModel());
			AppManager.CreateInstance().getAppMain().daycb.setModel(new DefaultComboBoxModel());
		}
		else {
			AppManager.CreateInstance().getAppMain().monthcb.setModel(new DefaultComboBoxModel(month));
			AppManager.CreateInstance().getAppMain().daycb.setModel(new DefaultComboBoxModel(day));
		}
	}
});*/


