/**
 * AccidentController.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class AppController {
	
	public static boolean searchOpenedFlag;
	public static boolean registerOpenedFlag;
	public static boolean updateOpenedFlag;
	public static boolean analysisOpenedFlag;
	
	protected String[] sTown = {"노원구","도봉구","강남구","서초구","강서구","강동구","종로구","중구","중랑구","성북구","금천구","영등포구",
			"서대문구","은평구","동작구","마포구","송파구","광진구","용산구","양천구","구로구","성동구","관악구","동대문구","강북구"};
	protected String[] iTown = {"중구","동구","남구","연수구","남동구","부평구","계양구","서구","강화군","옹진군"};
	protected String[] gyTown = {"수원시","성남시","의정부시","안양시","부천시","광명시","평택시","동두천시","안산시","고양시","과천시","구리시","남양주시","오산시","시흥시",
			"군포시","의왕시","하남시","용인시","파주시","이천시","김포시","화성시","광주시","양주시","포천시","여주군","연천군","가평군","양평군"};
	protected String[] month = {"월","01","02","03","04","05","06","07","08","09","10","11","12"};
	
	protected String[] day = {"일","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
			"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	

	public AppController()
	{
		searchOpenedFlag = false;
		registerOpenedFlag = false;
		updateOpenedFlag = false;
		analysisOpenedFlag = false;
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
							JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().introPanel.idField, "DB 접속 오류");
							AppManager.CreateInstance().getAppMain().introPanel.idField.setText("");
							AppManager.CreateInstance().getAppMain().introPanel.pwField.setText("");
						}
					}
				});
		AppManager.CreateInstance().getAppMain().introPanel.addMouseLoginButtonListener(new MouseAdapter()
				{
					public void mouseEntered(MouseEvent e)
					{
						Object obj = e.getSource();	
						if(obj == AppManager.CreateInstance().getAppMain().introPanel.loginBtn)
						{
							AppManager.CreateInstance().getAppMain().introPanel.loginBtn.setIcon(ImageData.loginEnteredImage);
						}
					}
					@Override
					public void mouseExited(MouseEvent e)
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().introPanel.loginBtn)
						{
							AppManager.CreateInstance().getAppMain().introPanel.loginBtn.setIcon(ImageData.loginBaiscImage);
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
								if(i==0 && searchOpenedFlag == false) {
									AppManager.CreateInstance().getAppMain().search();
									AppManager.CreateInstance().getAppMain().searchExit.setIcon(ImageData.exitButtonBasic);
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									
									AppManager.CreateInstance().getAppMain().siDo.setSelectedItem("전체");
									AppManager.CreateInstance().getAppMain().guGun.setSelectedItem("");
									AppManager.CreateInstance().getAppMain().yearcbSearch.setSelectedItem("년도");
									AppManager.CreateInstance().getAppMain().monthcbSearch.setSelectedItem("월");
									searchOpenedFlag =true;
								}
								else if(i==1 && registerOpenedFlag == false) {
									AppManager.CreateInstance().getAppMain().registration();
									AppManager.CreateInstance().getAppMain().registerExit.setIcon(ImageData.exitButtonBasic);
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									
									AppManager.CreateInstance().getAppMain().pro.setSelectedItem("전체");
									AppManager.CreateInstance().getAppMain().tow.setSelectedItem("");
									AppManager.CreateInstance().getAppMain().yearcb.setSelectedItem("년도");
									AppManager.CreateInstance().getAppMain().monthcb.setSelectedItem("월");
									AppManager.CreateInstance().getAppMain().daycb.setSelectedItem("일");
									//AppManager.CreateInstance().getAppMain().polno.setText("");
									AppManager.CreateInstance().getAppMain().dead.setText("");
									AppManager.CreateInstance().getAppMain().injured.setText("");
									AppManager.CreateInstance().getAppMain().accType.setSelectedItem("차대차");
									AppManager.CreateInstance().getAppMain().lati.setText("");
									AppManager.CreateInstance().getAppMain().longi.setText("");
									
									registerOpenedFlag =true;
								}
								else if(i==2 && updateOpenedFlag == false) {
									AppManager.CreateInstance().getAppMain().modifyDelete();
									AppManager.CreateInstance().getAppMain().updateExit.setIcon(ImageData.exitButtonBasic);
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									
									AppManager.CreateInstance().getAppMain().caseNumTxt.setText("");
									AppManager.CreateInstance().getAppMain().proUpdate.setSelectedItem("전체");
									AppManager.CreateInstance().getAppMain().towUpdate.setSelectedItem("");
									AppManager.CreateInstance().getAppMain().yearcbUpdate.setSelectedItem("년도");
									AppManager.CreateInstance().getAppMain().monthcbUpdate.setSelectedItem("월");
									AppManager.CreateInstance().getAppMain().daycbUpdate.setSelectedItem("일");
									AppManager.CreateInstance().getAppMain().polnoUpdate.setText("");
									AppManager.CreateInstance().getAppMain().deadUpdate.setText("");
									AppManager.CreateInstance().getAppMain().injuredUpdate.setText("");
									AppManager.CreateInstance().getAppMain().accTypeUpdate.setSelectedItem("차대차");
									AppManager.CreateInstance().getAppMain().latiUpdate.setText("");
									AppManager.CreateInstance().getAppMain().longiUpdate.setText("");
									
									updateOpenedFlag =true;
								}
								else if(i == 3 && analysisOpenedFlag == false)
								{
									AppManager.CreateInstance().getAppMain().analysis();
									
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									analysisOpenedFlag = true;
								}
							}
						}
					}			
				});
		AppManager.CreateInstance().getAppMain().addMouseAdapterHoverListener(new MouseAdapter()
				{
					public void mouseEntered(MouseEvent e)
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().btns[0])
						{
							AppManager.CreateInstance().getAppMain().btns[0].setIcon(ImageData.searchEntered);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[1])
						{
							AppManager.CreateInstance().getAppMain().btns[1].setIcon(ImageData.regEntered);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[2])
						{
							AppManager.CreateInstance().getAppMain().btns[2].setIcon(ImageData.updateEntered);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[3])
						{
							AppManager.CreateInstance().getAppMain().btns[3].setIcon(ImageData.analysisEntered);
						}
					}
					@Override
					public void mouseExited(MouseEvent e)
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().btns[0])
						{
							AppManager.CreateInstance().getAppMain().btns[0].setIcon(ImageData.searchBasic);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[1])
						{
							AppManager.CreateInstance().getAppMain().btns[1].setIcon(ImageData.regBasic);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[2])
						{
							AppManager.CreateInstance().getAppMain().btns[2].setIcon(ImageData.updateBasic);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[3])
						{
							AppManager.CreateInstance().getAppMain().btns[3].setIcon(ImageData.analysisBasic);
						}
					}
				});
		AppManager.CreateInstance().getAppMain().addMouseAdapterButtonListener(new MouseAdapter()
				{
						public void mousePressed(MouseEvent e)							//메뉴바를 잡았을 떄 절대좌표를 받아옴
						{
							Object obj = e.getSource();
							if(obj == AppManager.CreateInstance().getAppMain().menuBar)
							{
								AppManager.CreateInstance().getAppMain().mainMouseX = e.getX();
								AppManager.CreateInstance().getAppMain().mainMouseY = e.getY();			
							}
							else if(obj == AppManager.CreateInstance().getAppMain().menuBarSearch)
							{
								AppManager.CreateInstance().getAppMain().searchMouseX = e.getX();
								AppManager.CreateInstance().getAppMain().searchMouseY = e.getY();						
							}
							else if(obj == AppManager.CreateInstance().getAppMain().menuBarRegist)
							{
								AppManager.CreateInstance().getAppMain().registMouseX = e.getX();
								AppManager.CreateInstance().getAppMain().registMouseY = e.getY();					
							}
							else if(obj == AppManager.CreateInstance().getAppMain().menuBarUpdate)
							{
								AppManager.CreateInstance().getAppMain().updateMouseX = e.getX();
								AppManager.CreateInstance().getAppMain().updateMouseY = e.getY();					
							}
						}
				});
		AppManager.CreateInstance().getAppMain().addMouseMotionButtonListener(new MouseMotionAdapter() {
			
			public void mouseDragged(MouseEvent e)
			{
				Object obj = e.getSource();
				if(obj == AppManager.CreateInstance().getAppMain().menuBar)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().setLocation(x - AppManager.CreateInstance().getAppMain().mainMouseX,
							y - AppManager.CreateInstance().getAppMain().mainMouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
					
				}
				else if(obj == AppManager.CreateInstance().getAppMain().menuBarSearch)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().diaSearch.setLocation(x - AppManager.CreateInstance().getAppMain().searchMouseX,
							y - AppManager.CreateInstance().getAppMain().searchMouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
					
				}
				else if(obj == AppManager.CreateInstance().getAppMain().menuBarRegist)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().dia.setLocation(x - AppManager.CreateInstance().getAppMain().registMouseX,
							y - AppManager.CreateInstance().getAppMain().registMouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
					
				}
				else if(obj == AppManager.CreateInstance().getAppMain().menuBarUpdate)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().diaUpdate.setLocation(x - AppManager.CreateInstance().getAppMain().updateMouseX,
							y - AppManager.CreateInstance().getAppMain().updateMouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
				}
			}
			
		});
		AppManager.CreateInstance().getAppMain().addExitMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseEntered(MouseEvent e)
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().exit)
						{
							AppManager.CreateInstance().getAppMain().exit.setIcon(ImageData.exitButtonEntered);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().searchExit)
						{
							AppManager.CreateInstance().getAppMain().searchExit.setIcon(ImageData.exitButtonEntered);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().registerExit)
						{
							AppManager.CreateInstance().getAppMain().registerExit.setIcon(ImageData.exitButtonEntered);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().updateExit)
						{
							AppManager.CreateInstance().getAppMain().updateExit.setIcon(ImageData.exitButtonEntered);
						}
					}
					@Override
					public void mouseExited(MouseEvent e)
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().exit)
						{
							AppManager.CreateInstance().getAppMain().exit.setIcon(ImageData.exitButtonBasic);			
						}
						else if(obj == AppManager.CreateInstance().getAppMain().searchExit)
						{
							AppManager.CreateInstance().getAppMain().searchExit.setIcon(ImageData.exitButtonBasic);			
						}
						else if(obj == AppManager.CreateInstance().getAppMain().registerExit)
						{
							AppManager.CreateInstance().getAppMain().registerExit.setIcon(ImageData.exitButtonBasic);		
						}
						else if(obj == AppManager.CreateInstance().getAppMain().updateExit)
						{
							AppManager.CreateInstance().getAppMain().updateExit.setIcon(ImageData.exitButtonBasic);	
						}
					}
					@Override
					public void mouseReleased(MouseEvent e)
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().exit)
						{
							System.exit(0);
						}
						else if(obj == AppManager.CreateInstance().getAppMain().searchExit)
						{
							AppManager.CreateInstance().getAppMain().diaSearch.dispose();
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
							searchOpenedFlag = false;
						}
						else if(obj == AppManager.CreateInstance().getAppMain().registerExit)
						{
							AppManager.CreateInstance().getAppMain().dia.dispose();
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
	
							registerOpenedFlag = false;
						}
						else if(obj == AppManager.CreateInstance().getAppMain().updateExit)
						{
							AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
							updateOpenedFlag = false;
						}
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionSearchProListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
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
							
							if(pro.equals("전체"))//장소가 전체 일경우
							{
								if(year == "년도" && month == "월")		//장소가 전체 이면서 날짜도 없는 경우 모두 가져오기.
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().getAll();	
								}
								else if(year != "년도" && month == "월")				//년은 선택 되어 있고, 월은 선택 안되어있는 경우
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(year);
								}
								else if(year == "년도 " && month != "월")				//년은 선택되어 있지 않고, 월은 선택되어있는 경우
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseMonthTime(month);
								}
								else												//장소가 전체 이면서 특정날짜가 설정되어있는 경우
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(year,month);
								}
							}
							else									//장소가 전체가 아닌 경우 (장소가 결정되어있는 경우
							{
								if(year == "년도" && month == "월")	//특정 장소 + 날짜 비설정 
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseLoca(pro, tow);							
								}
								else if(year == "년도" && month != "월")
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseLocaMonth(pro, tow, month);
								}
								else if(year != "년도" && month == "월")
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseLocaYear(pro, tow, year);
								}
								else if(year != "년도" && month != "월")
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCase(pro, tow, year, month);			
								}
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
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
	
							AppManager.CreateInstance().getAppMain().diaSearch.dispose();
							searchOpenedFlag = false;
							AppManager.CreateInstance().getAppMain().searchButton.setIcon(ImageData.searchDialogBtnBasic);
							
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
							tempCase.setDead(Integer.parseInt(AppManager.CreateInstance().getAppMain().dead.getText()));
							tempCase.setInjured(Integer.parseInt(AppManager.CreateInstance().getAppMain().injured.getText()));
							tempCase.setCasulity();
							tempCase.setActype((String)AppManager.CreateInstance().getAppMain().accType.getSelectedItem());
							tempCase.setLatitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().lati.getText()));
							tempCase.setLongitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().longi.getText()));
						
							if(tempCase.getProvince().equals("전체")|| tempCase.getYear().equals("년도") || tempCase.getMonth().equals("월") || tempCase.getDay().equals("일"))
								insertFlag = false;
							else
								insertFlag = AppManager.CreateInstance().getAccidentCaseDAO().insertCase(tempCase);		
							
							
							//가져오는 부분--------------------------------------------------------------------------------
							if(insertFlag == true)
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().regBtn, "등록되었습니다.");
								
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
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
								
								registerOpenedFlag = false;
								AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnBasic);
				
							}
							else
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "등록 실패!\n정보 정확히 입력하십시오!", "경고", JOptionPane.PLAIN_MESSAGE);
								AppManager.CreateInstance().getAppMain().dia.dispose();
								
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
								registerOpenedFlag = false;

								AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnBasic);
								
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
							tempCase.setDead(Integer.parseInt(AppManager.CreateInstance().getAppMain().deadUpdate.getText()));
							tempCase.setInjured(Integer.parseInt(AppManager.CreateInstance().getAppMain().injuredUpdate.getText()));
							tempCase.setCasulity();
							tempCase.setActype((String)AppManager.CreateInstance().getAppMain().accTypeUpdate.getSelectedItem());
							tempCase.setLatitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().latiUpdate.getText()));
							tempCase.setLongitude(Double.parseDouble(AppManager.CreateInstance().getAppMain().longiUpdate.getText()));
							
							//tempCase 업데이트 실시
							if(tempCase.getProvince().equals("전체") || tempCase.getYear().equals("년도") ||
									tempCase.getMonth().equals("월") || tempCase.getDay().equals("일"))
							{
								succFlag = false;
							}
							else
							{
								succFlag = AppManager.CreateInstance().getAccidentCaseDAO().updateCase(tempCase);		
							}
							
							
							if(succFlag == true)
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().deleteButton, "수정되었습니다.");
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
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
								updateOpenedFlag = false;
								AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnBasic);
								AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnBasic);
								AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnBasic);
								
							}
							else
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "수정 실패!!", "경고", JOptionPane.PLAIN_MESSAGE);
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
		
								updateOpenedFlag = false;
								AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnBasic);
								AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnBasic);
								AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnBasic);
							
							}
						}
						else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)
						{
							boolean delSuccess;
							delSuccess = AppManager.CreateInstance().getAccidentCaseDAO().deleteCase(Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText()));
							
							if(delSuccess == true)
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().deleteButton, "삭제되었습니다.");
								//화면 CardLayout 으로 사진 으로 전환
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "image");
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
		
								updateOpenedFlag = false;
								
							}
							else
							{	
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "삭제 실패!!", "경고", JOptionPane.PLAIN_MESSAGE);
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
		
								updateOpenedFlag = false;
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
				
							
							DetailInfo accDetailInfo = new DetailInfo(tempCase);
							accDetailInfo.addMouseDetailMenuBarListener(new MouseAdapter()
							{
								@Override
								public void mousePressed(MouseEvent e)							//메뉴바를 잡았을 떄 절대좌표를 받아옴
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.menuBarDetail)
									{
										accDetailInfo.mouseX = e.getX();
										accDetailInfo.mouseY = e.getY();				
									}
								}
							});
							accDetailInfo.addMouseDetailMenuBarMotionListener(new MouseMotionAdapter()
							{
								@Override
								public void mouseDragged(MouseEvent e)
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.menuBarDetail)
									{
										int x = e.getXOnScreen();
										int y = e.getYOnScreen();
										accDetailInfo.setLocation(x - accDetailInfo.mouseX, y - accDetailInfo.mouseY);							//메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦		
									}
								}
							});
							accDetailInfo.addMouseDetailExitListener(new MouseAdapter()
							{
								@Override
								public void mouseEntered(MouseEvent e)
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.detailExit)
									{
										accDetailInfo.detailExit.setIcon(ImageData.exitButtonEntered);			
									}
								}
								@Override
								public void mouseExited(MouseEvent e)
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.detailExit)
									{
										accDetailInfo.detailExit.setIcon(ImageData.exitButtonBasic);			
									}
								}
								@Override
								public void mouseReleased(MouseEvent e)
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.detailExit)
									{
										accDetailInfo.dispose();			
									}
								}
							});
						}
						if(e.getButton() == 3)
						{
							
						}
					}
					
				});
		AppManager.CreateInstance().getAppMain().addInsideMouseListener(new MouseAdapter()
		{
				public void mouseEntered(MouseEvent e)
				{
					Object obj = e.getSource();	
					if(obj == AppManager.CreateInstance().getAppMain().searchButton)
					{
						AppManager.CreateInstance().getAppMain().searchButton.setIcon(ImageData.searchDialogBtnEntered);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().regBtn)
					{
						AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnEntered);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().searchUpdateBtn)
					{
						 AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnEntered);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().updateButton)
					{
						AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnEntered);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)
					{
						AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnEntered);
					}
					
				}
				@Override
				public void mouseExited(MouseEvent e)
				{
					Object obj = e.getSource();	
					if(obj == AppManager.CreateInstance().getAppMain().searchButton)
					{
						AppManager.CreateInstance().getAppMain().searchButton.setIcon(ImageData.searchDialogBtnBasic);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().regBtn)
					{
						AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnBasic);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().searchUpdateBtn)
					{
						 AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnBasic);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().updateButton)
					{
						AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnBasic);
					}
					else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)
					{
						AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnBasic);
					}

					
				}
		});

	}
}




