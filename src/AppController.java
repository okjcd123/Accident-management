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
	
	public static boolean searchOpenedFlag;													//검색 Dialog 열림 유무 확인 변수
	public static boolean registerOpenedFlag;												//등록 Dialog 열림 유무 확인 변수
	public static boolean updateOpenedFlag;													//수정, 삭제 Dialog 열림 유무 확인 변수
	public static boolean analysisOpenedFlag;												//분석 Dialog 열림 유무 확인 변수
	
	protected String[] sTown = {"노원구","도봉구","강남구","서초구","강서구","강동구","종로구","중구","중랑구","성북구","금천구","영등포구",
			"서대문구","은평구","동작구","마포구","송파구","광진구","용산구","양천구","구로구","성동구","관악구","동대문구","강북구"};				//서울 구 정의 String
	protected String[] iTown = {"중구","동구","남구","연수구","남동구","부평구","계양구","서구","강화군","옹진군"};								//인천 하위 구역 정의 String
	protected String[] gyTown = {"수원시","성남시","의정부시","안양시","부천시","광명시","평택시","동두천시","안산시","고양시","과천시","구리시","남양주시","오산시","시흥시",
			"군포시","의왕시","하남시","용인시","파주시","이천시","김포시","화성시","광주시","양주시","포천시","여주군","연천군","가평군","양평군"};	//경기도 하위 구역 정의 String
	protected String[] month = {"월","01","02","03","04","05","06","07","08","09","10","11","12"};									//월 정의 String
	
	protected String[] day = {"일","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",						//일 정의 String
			"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	

	public AppController()
	{
		//모든 Dialog 닫혀져 있는 상태
		searchOpenedFlag = false;
		registerOpenedFlag = false;
		updateOpenedFlag = false;
		analysisOpenedFlag = false;
		
		AppManager.CreateInstance().setAppController(this);																			//AppManager 에 AppController 객체  등록
		
		AppManager.CreateInstance().getAppMain().introPanel.addActionLoginButtonListener(new ActionListener()						//로그인 Action Listener 선언
				{
					@Override
					public void actionPerformed(ActionEvent e) {																	//로그인 버튼 클릭시
						
						String id = AppManager.CreateInstance().getAppMain().introPanel.idField.getText();							//로그인 id 가져오기
						String pwd = new String(AppManager.CreateInstance().getAppMain().introPanel.pwField.getPassword());			//로그인 비밀번호 가져오기
						if(AppManager.CreateInstance().getAccidentCaseDAO().connectTest(id, pwd))									//연결 테스트 실시
						{
							AppManager.CreateInstance().getAppMain().getContentPane().removeAll();									//로그인 성공시 기존 로그인 Content 삭제
							AppManager.CreateInstance().getAppMain().getContentPane().add(AppManager.CreateInstance().getAppMain().primary);	//primary Panel 붙임.
							AppManager.CreateInstance().getAppMain().revalidate();
							AppManager.CreateInstance().getAppMain().primary.requestFocus();
							AppManager.CreateInstance().getAppMain().status = true;													//Status =true 로 paint 함수 강제 종료
							AppManager.CreateInstance().getAppMain().repaint();														//primary 패널 다시 paint
						}
						else																										//접속 실패시
						{
							JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().introPanel.idField, "DB 접속 오류");//DB접속 오류
							AppManager.CreateInstance().getAppMain().introPanel.idField.setText("");								//아이디 칸 초기화
							AppManager.CreateInstance().getAppMain().introPanel.pwField.setText("");								//비밀번호 칸 초기화
						}
					}
				});
		AppManager.CreateInstance().getAppMain().introPanel.addMouseLoginButtonListener(new MouseAdapter()							//로그인 마우스 이벤트 관련			
				{
					public void mouseEntered(MouseEvent e)																			//마우스 로그인 버튼 입장시
					{
						Object obj = e.getSource();	
						if(obj == AppManager.CreateInstance().getAppMain().introPanel.loginBtn)
						{
							AppManager.CreateInstance().getAppMain().introPanel.loginBtn.setIcon(ImageData.loginEnteredImage);		//로그인 아이콘 바꾸기
						}
					}
					@Override
					public void mouseExited(MouseEvent e)																			//마우스 로그인 버튼 밖으로 나갈시
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().introPanel.loginBtn)
						{
							AppManager.CreateInstance().getAppMain().introPanel.loginBtn.setIcon(ImageData.loginBaiscImage);		//로그인 아이콘 원래대로 마꾸기
						}
						
					}
				});
	
		AppManager.CreateInstance().getAppMain().addActionButtonListener(new ActionListener()										//Main 화면 기본 버튼 ActionListener
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						JButton btn = (JButton)arg0.getSource();
						for(int i=0;i<AppManager.CreateInstance().getAppMain().btns.length;i++) {
							if(AppManager.CreateInstance().getAppMain().btns[i]==btn) {
								if(i==0 && searchOpenedFlag == false) {																//검색 버튼이고, 닫혀있는 상태일경우
									
									AppManager.CreateInstance().getAppMain().search();												//검색 Dialog 키기

									AppManager.CreateInstance().getAppMain().searchExit.setIcon(ImageData.exitButtonBasic);			//아이콘 원래대로 설정
									
									//타 버튼들 클릭 무효화
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									
									//Dialog 기본 옵션으로 모두 설정
									AppManager.CreateInstance().getAppMain().siDo.setSelectedItem("전체");
									AppManager.CreateInstance().getAppMain().guGun.setSelectedItem("");
									AppManager.CreateInstance().getAppMain().yearcbSearch.setSelectedItem("년도");
									AppManager.CreateInstance().getAppMain().monthcbSearch.setSelectedItem("월");
									searchOpenedFlag =true;																			//검색 Dialog open 상태로 설정
								}
								else if(i==1 && registerOpenedFlag == false) {														//등록 버튼이고, 닫혀있는 상태일경우
									AppManager.CreateInstance().getAppMain().registration();										//등록 Dialog 키기
									AppManager.CreateInstance().getAppMain().registerExit.setIcon(ImageData.exitButtonBasic);		//아이콘 원래대로 설정
									//타 버튼들 클릭 무효화
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									
									//Dialog 기본 옵션으로 모두 설정
									AppManager.CreateInstance().getAppMain().pro.setSelectedItem("전체");
									AppManager.CreateInstance().getAppMain().tow.setSelectedItem("");
									AppManager.CreateInstance().getAppMain().yearcb.setSelectedItem("년도");
									AppManager.CreateInstance().getAppMain().monthcb.setSelectedItem("월");
									AppManager.CreateInstance().getAppMain().daycb.setSelectedItem("일");
									AppManager.CreateInstance().getAppMain().dead.setText("");
									AppManager.CreateInstance().getAppMain().injured.setText("");
									AppManager.CreateInstance().getAppMain().accType.setSelectedItem("차대차");
									AppManager.CreateInstance().getAppMain().lati.setText("");
									AppManager.CreateInstance().getAppMain().longi.setText("");
									
									registerOpenedFlag =true;																		//등록 Dialog open 상태로 설정
								}
								else if(i==2 && updateOpenedFlag == false) {														//수정/삭제 버튼이고, 닫혀있는 상태인경우
									AppManager.CreateInstance().getAppMain().modifyDelete();										//수정 Dialog 키기
									AppManager.CreateInstance().getAppMain().updateExit.setIcon(ImageData.exitButtonBasic);			//아이콘 원래대로 설정
									
									//타 버튼들 클릭 무효화
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									
									//Dialog 기본 옵션으로 모두 설정
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
									
									updateOpenedFlag =true;																			//수정/삭제 Dialog open 상태로 설정
								}
								else if(i == 3 && analysisOpenedFlag == false)														//분석 버튼이고, 닫혀있는 상태인 경우
								{
									AppManager.CreateInstance().getAppMain().analysis();											//분석 Dialog 키기
									
									//타 버튼들 클릭 무효화
									AppManager.CreateInstance().getAppMain().btns[0].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[1].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[2].setEnabled(false);
									AppManager.CreateInstance().getAppMain().btns[3].setEnabled(false);
									analysisOpenedFlag = true;																		//분석 Dialog 키기
								}
							}
						}
					}			
				});
		AppManager.CreateInstance().getAppMain().addMouseAdapterHoverListener(new MouseAdapter()									//메인 버튼 Hover Listener
				{
					public void mouseEntered(MouseEvent e)																			//마우스를 올려놓았을때
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().btns[0])
						{
							AppManager.CreateInstance().getAppMain().btns[0].setIcon(ImageData.searchEntered);						//검색 버튼 Hover 이미지로 설정
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[1])
						{
							AppManager.CreateInstance().getAppMain().btns[1].setIcon(ImageData.regEntered);							//등록 버튼 Hover 이미지로 설정
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[2])
						{
							AppManager.CreateInstance().getAppMain().btns[2].setIcon(ImageData.updateEntered);						//수정 버튼 Hover 이미지로 설정
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[3])
						{
							AppManager.CreateInstance().getAppMain().btns[3].setIcon(ImageData.analysisEntered);					//분석 버튼 Hover 이미지로 설정 
						}
					}
					@Override
					public void mouseExited(MouseEvent e)																			//마우스를 밖으로 놓았을때
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().btns[0])
						{
							AppManager.CreateInstance().getAppMain().btns[0].setIcon(ImageData.searchBasic);						//검색버튼 기본 이미지로 설정
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[1])
						{
							AppManager.CreateInstance().getAppMain().btns[1].setIcon(ImageData.regBasic);							//등록 버튼 기본 이미지로 설정
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[2])
						{
							AppManager.CreateInstance().getAppMain().btns[2].setIcon(ImageData.updateBasic);						//수정 버튼 기본 이미지로 설정
						}
						else if(obj == AppManager.CreateInstance().getAppMain().btns[3])
						{
							AppManager.CreateInstance().getAppMain().btns[3].setIcon(ImageData.analysisBasic);						//분석 버튼 기본 이미지로 설정 
						}
					}
				});
		AppManager.CreateInstance().getAppMain().addMouseAdapterButtonListener(new MouseAdapter()									//메뉴바관련 마우스 이벤트
				{
						public void mousePressed(MouseEvent e)																		//메뉴바를 잡았을 떄 절대좌표를 받아옴
						{
							Object obj = e.getSource();
							if(obj == AppManager.CreateInstance().getAppMain().menuBar)
							{
								AppManager.CreateInstance().getAppMain().mainMouseX = e.getX();										//마우스 x 절대좌표 가져오기
								AppManager.CreateInstance().getAppMain().mainMouseY = e.getY();										//마우스 y 절대좌표 가져오기
							}
							else if(obj == AppManager.CreateInstance().getAppMain().menuBarSearch)									//검색 Dialog 메뉴바인경우
							{
								AppManager.CreateInstance().getAppMain().searchMouseX = e.getX();									//마우스 x 절대좌표 가져오기
								AppManager.CreateInstance().getAppMain().searchMouseY = e.getY();									//마우스 y 절대좌표 가져오기
							}
							else if(obj == AppManager.CreateInstance().getAppMain().menuBarRegist)									//등록 Dialog 메뉴바인경우
							{
								AppManager.CreateInstance().getAppMain().registMouseX = e.getX();									//마우스 x 절대 좌표 가져오기
								AppManager.CreateInstance().getAppMain().registMouseY = e.getY();									//마우스 y 절대 좌표 가져오기
							}
							else if(obj == AppManager.CreateInstance().getAppMain().menuBarUpdate)									//수정 Dilaog 메뉴바인경우
							{
								AppManager.CreateInstance().getAppMain().updateMouseX = e.getX();									//마우스 x 절대좌표 가져오기
								AppManager.CreateInstance().getAppMain().updateMouseY = e.getY();									//마우스 y 절대좌표 가져오기
							}
						}
				});
		AppManager.CreateInstance().getAppMain().addMouseMotionButtonListener(new MouseMotionAdapter() {							//메뉴바 마우스 모션 이벤트 관련
			
			public void mouseDragged(MouseEvent e)
			{
				Object obj = e.getSource();
				if(obj == AppManager.CreateInstance().getAppMain().menuBar)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().setLocation(x - AppManager.CreateInstance().getAppMain().mainMouseX,
							y - AppManager.CreateInstance().getAppMain().mainMouseY);												//Main 메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
					
				}
				else if(obj == AppManager.CreateInstance().getAppMain().menuBarSearch)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().diaSearch.setLocation(x - AppManager.CreateInstance().getAppMain().searchMouseX,
							y - AppManager.CreateInstance().getAppMain().searchMouseY);												//검색 Dialog 메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
					
				}
				else if(obj == AppManager.CreateInstance().getAppMain().menuBarRegist)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().dia.setLocation(x - AppManager.CreateInstance().getAppMain().registMouseX,
							y - AppManager.CreateInstance().getAppMain().registMouseY);												//등록 Dialog 메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
					
				}
				else if(obj == AppManager.CreateInstance().getAppMain().menuBarUpdate)
				{
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					AppManager.CreateInstance().getAppMain().diaUpdate.setLocation(x - AppManager.CreateInstance().getAppMain().updateMouseX,
							y - AppManager.CreateInstance().getAppMain().updateMouseY);												//수정 Dialog 메뉴바를 잡고 움직였을 때 전체 프레임도 움직이게 만듦
				}
			}
			
		});
		AppManager.CreateInstance().getAppMain().addExitMouseListener(new MouseAdapter()											//종료 버튼 마우스 이벤트 관련
				{
					@Override
					public void mouseEntered(MouseEvent e)																			//종료 버튼 마우스 Hover 상태 일경우
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().exit)	
						{
							AppManager.CreateInstance().getAppMain().exit.setIcon(ImageData.exitButtonEntered);						//메인종료 버튼 아이콘 변경
						}
						else if(obj == AppManager.CreateInstance().getAppMain().searchExit)
						{
							AppManager.CreateInstance().getAppMain().searchExit.setIcon(ImageData.exitButtonEntered);				//검색 dialog 종료 버튼 아이콘 변경
						}
						else if(obj == AppManager.CreateInstance().getAppMain().registerExit)
						{
							AppManager.CreateInstance().getAppMain().registerExit.setIcon(ImageData.exitButtonEntered);				//등록 dialog 종료 버튼 아이콘 변경
						}
						else if(obj == AppManager.CreateInstance().getAppMain().updateExit)
						{
							AppManager.CreateInstance().getAppMain().updateExit.setIcon(ImageData.exitButtonEntered);				//수정 dialog 종료 버튼 아이콘 변경
						}
					}
					@Override
					public void mouseExited(MouseEvent e)																			//종료 버튼 마우스가 나갈때
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().exit)							
						{
							AppManager.CreateInstance().getAppMain().exit.setIcon(ImageData.exitButtonBasic);						//메인 종료 버튼 원래상태로 변경			
						}
						else if(obj == AppManager.CreateInstance().getAppMain().searchExit)
						{
							AppManager.CreateInstance().getAppMain().searchExit.setIcon(ImageData.exitButtonBasic);					//검색 dialog 종료 버튼 원래상태로 변경
						}
						else if(obj == AppManager.CreateInstance().getAppMain().registerExit)
						{
							AppManager.CreateInstance().getAppMain().registerExit.setIcon(ImageData.exitButtonBasic);				//등록 dialog 종료 버튼 원래상태로 변경
						}
						else if(obj == AppManager.CreateInstance().getAppMain().updateExit)
						{
							AppManager.CreateInstance().getAppMain().updateExit.setIcon(ImageData.exitButtonBasic);					//수정 dialog 종료 버튼 원래 상태로 변경
						}
					}
					@Override
					public void mouseReleased(MouseEvent e)																			//마우스가 띄어졌을때
					{
						Object obj = e.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().exit)													//종료 버튼 띄어졌을때
						{
							System.exit(0);																							//프로그램 종료
						}
						else if(obj == AppManager.CreateInstance().getAppMain().searchExit)
						{
							AppManager.CreateInstance().getAppMain().diaSearch.dispose();											//검색 dialog 종료
							
							//나머지 버튼 활성화
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);	
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
							searchOpenedFlag = false;																				//검색 dialog 종료 상태로 바꿈
						}
						else if(obj == AppManager.CreateInstance().getAppMain().registerExit)
						{
							AppManager.CreateInstance().getAppMain().dia.dispose();													//등록 dialog 종료
							
							//나머지 버튼 활성화
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
	
							registerOpenedFlag = false;																				//등록 dialog 종료 상태로 바꿈
						}
						else if(obj == AppManager.CreateInstance().getAppMain().updateExit)
						{
							AppManager.CreateInstance().getAppMain().diaUpdate.dispose();											//수정 dialog 종료
							
							//나머지 버튼 활성화
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
							updateOpenedFlag = false;																				//수정 dialog 종료 상태로 바꿈
						}
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionSearchProListener(new ActionListener()									//검색 Dialog 에서 나머지 ComboBox 초기화 이벤트 관려
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						JComboBox tmp = (JComboBox)arg0.getSource();
						String select = (String)tmp.getSelectedItem();
						if(select.equals("전체")) {																					//전체일경우
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel());					//하위 구역 null 상태로 초기화
						}
						else if(select.equals("서울특별시")) {
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel(sTown));				//서울특별시일경우 하위구역 sTown String으로 초기화
						}
						else if(select.equals("인천광역시")) {
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel(iTown));				//인천광역시일경우 하위구역 iTown String으로 초기화
						}
						else {
							AppManager.CreateInstance().getAppMain().guGun.setModel(new DefaultComboBoxModel(gyTown));				//경기도 일경우 하위 구역 gyTown String 으로 초기화
						}	
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionSearchListener(new ActionListener()										//등록 관련 이벤트
				{
					public void actionPerformed(ActionEvent e) {
						
						JButton btn = (JButton)e.getSource();
						if(btn == AppManager.CreateInstance().getAppMain().searchButton)
						{
							ArrayList <AccidentCase> outputDatas = new ArrayList<AccidentCase>();									//사고 정보 저장할 ArrayList 선언
							
							//데이터 받아오기
							String pro = (String)AppManager.CreateInstance().getAppMain().siDo.getSelectedItem();					//시도 옵션 가져오기
							String tow = (String)AppManager.CreateInstance().getAppMain().guGun.getSelectedItem();					//군구 옵션 가져오기
							String year = (String)AppManager.CreateInstance().getAppMain().yearcbSearch.getSelectedItem();			//년 옵션 가져오기
							String month = (String)AppManager.CreateInstance().getAppMain().monthcbSearch.getSelectedItem();		//원 옵션 가져오기
							
							if(pro.equals("전체"))																					//장소가 전체 일경우
							{
								if(year.contentEquals("년도") && month.equals("월"))		//장소가 전체 이면서 날짜도 없는 경우 모두 가져오기.
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().getAll();	
								}
								else if(!year.equals("년도") && month.equals("월"))				//년은 선택 되어 있고, 월은 선택 안되어있는 경우
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(year);
								}
								else if(year.equals("년도") && !month.equals("월"))				//년은 선택되어 있지 않고, 월은 선택되어있는 경우
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseMonthTime(month);
								}
								else												//장소가 전체 이면서 특정날짜가 설정되어있는 경우
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseTime(year,month);
								}
							}
							else													//장소가 전체가 아닌 경우 (장소가 결정되어있는 경우
							{
								if(year.equals("년도") && month.equals("월"))					//특정 장소 + 날짜 비설정 
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseLoca(pro, tow);							
								}
								else if(year.equals("년도") && !month.equals("월"))				//특정장소 + 월 설정
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseLocaMonth(pro, tow, month);
								}
								else if(!year.equals("년도") && month.equals("월"))				//특정장소 + 년 설정
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCaseLocaYear(pro, tow, year);
								}
								else															//특정장소 + 년월 설정
								{
									outputDatas = AppManager.CreateInstance().getAccidentCaseDAO().searchCase(pro, tow, year, month);			
								}
							}
							
							// 출력 절차
							AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table");		//table 화면 보여주기.
							AppManager.CreateInstance().getAppMain().basicTable.setRowCount(0);															//table 전체 초기화
							
							//Table 데이터 다시 채우기
							for(AccidentCase outputCase: outputDatas)																					//반환 받은 데이터 순차적으로 table 에 채우기
							{
								String [] temp = {Integer.toString(outputCase.getCscode()),
										outputCase.getProvince(),outputCase.getTown(), outputCase.getYear(),
										outputCase.getMonth(), outputCase.getDay(),Integer.toString(outputCase.getCasulity()),
										Integer.toString(outputCase.getDead()),Integer.toString(outputCase.getInjured()),
										outputCase.getActype()};
								AppManager.CreateInstance().getAppMain().basicTable.addRow(temp);														//row 추가
							}
							
							//버튼 모두 활성화
							AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
							AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
	
							AppManager.CreateInstance().getAppMain().diaSearch.dispose();																//검색 dialog 닫기
							searchOpenedFlag = false;																									//검색 dialog 닫힌 상태로 바꾸기
							AppManager.CreateInstance().getAppMain().searchButton.setIcon(ImageData.searchDialogBtnBasic);								//버튼 이미지 원래대로 바꾸기
							
						}
					}
			
				});
	
		AppManager.CreateInstance().getAppMain().addActionRegisterProListener(new ActionListener()														//등록 Dialog 내 ComboBox관련 이벤트
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox tmp = (JComboBox)e.getSource();
						String select = (String)tmp.getSelectedItem();

						if(select.equals("전체")) {	
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel());											//전체로 설정되어있을 경우, 하위구역 null로 초기화
						}
						else if(select.equals("서울특별시")) {
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel(sTown));										//서울특별시로 설정되었을 경우, 하위구역 sTown 으로 초기화
						}
						else if(select.equals("인천광역시")) {
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel(iTown));										//인천광역시일 경우, 하위구역 iTown 으로 초기화
						}
						else {
							AppManager.CreateInstance().getAppMain().tow.setModel(new DefaultComboBoxModel(gyTown));									//경기도일 경우 , 하위구역 gyTown 으로 초기화
						}	
					}
				});

		AppManager.CreateInstance().getAppMain().addActionRegisterListener(new ActionListener()													//등록관련 ActionListener
				{
					public void actionPerformed(ActionEvent arg0) {
						Object obj = arg0.getSource();
						
						if(obj == AppManager.CreateInstance().getAppMain().regBtn)																//등록 버튼을 눌렀을 경우
						{
							//데이터
							boolean insertFlag;																									//등록 성공여부 boolean
							AccidentCase tempCase = new AccidentCase();
							
							//설정된 값들 모두 tempCase 에저장
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
								insertFlag = false;																								//데이터가 한개라도 비워있을 경우 등록 실패로 간주
							else
								insertFlag = AppManager.CreateInstance().getAccidentCaseDAO().insertCase(tempCase);								//모두 채워질 경우 등록 성공으로 간주
							
							
							//가져오는 부분-----------------------------------------------------------------------------------------------------------------------
							if(insertFlag == true)																								//등록이 성공되어있을경우
							{	
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().regBtn, "등록되었습니다.");					//등록되었다는 메세지를 띄움
								
								int maxIndex = AppManager.CreateInstance().getAccidentCaseDAO().getNewCaseCode();								//제일 최상의 csCode를 가져옴
								AccidentCase outputCase = new AccidentCase();
								
								outputCase = AppManager.CreateInstance().getAccidentCaseDAO().getCase(maxIndex);								//csCode를 다시검색하여 case 를 가져옴
								String [] temp = {Integer.toString(outputCase.getCscode()),
										outputCase.getProvince(),outputCase.getTown(), outputCase.getYear(),
										outputCase.getMonth(), outputCase.getDay(),Integer.toString(outputCase.getCasulity()),
										Integer.toString(outputCase.getDead()),Integer.toString(outputCase.getInjured()),
										outputCase.getActype()};
								
								//Table 초기화
								AppManager.CreateInstance().getAppMain().basicTable.setRowCount(0);												//table 초기화
								
								//Table 데이터 다시 채우기
								AppManager.CreateInstance().getAppMain().basicTable.addRow(temp);
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table");	//table 화면 보여주기.
								AppManager.CreateInstance().getAppMain().dia.dispose();															//등록 Dialog 종료
								
								//나머지 버튼 활성화
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
								
								registerOpenedFlag = false;																						//등록 Dialog 종료 상태로 만들기
								AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnBasic);							//등록 버튼 초기화
				
							}
							else																												//등록 실패
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "등록 실패!\n정보 정확히 입력하십시오!", "경고", JOptionPane.PLAIN_MESSAGE);
								AppManager.CreateInstance().getAppMain().dia.dispose();															//등록 Dialog 종료
								
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
								registerOpenedFlag = false;																						//등록 Dialoag 종료 상태로 만들기

								AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnBasic);							//등록 버튼 아이콘 원래대로 만들기
								
							}
						}		
						
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionUpdateProListener(new ActionListener()												//수정 Dialog ComboBox 관련 이벤트
				{
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						JComboBox tmp = (JComboBox)arg0.getSource();
						String select = (String)tmp.getSelectedItem();
						
						if(select.equals("전체")) {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel());							//전체로 설정되어있을경우 하위 구역 null로 설정
						}
						else if(select.equals("서울특별시")) {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel(sTown));						//서울특별시로 설정되어 있을경우 하위 구역 sTown으로 설정
						}
						else if(select.equals("인천광역시")) {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel(iTown));						//인천광역시로 설정되어 있을 경우 하위 구역 iTown 으로 설정
						}
						else {
							AppManager.CreateInstance().getAppMain().towUpdate.setModel(new DefaultComboBoxModel(gyTown));						//경기도로 설정되어 있을 경우 하위 구역 gyTown 으로 설정
						}
					}
				});
		
		AppManager.CreateInstance().getAppMain().addActionUpdateSearchListener(new ActionListener()											//Dialog 내 사고 검색 관련 이벤트
				{
					public void actionPerformed(ActionEvent arg0)
					{
						Object obj = arg0.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().searchUpdateBtn)													//검색클릭시
						{
							AccidentCase temp = new AccidentCase();
							int accNum = Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText());					//사고 번호 가져오기
							temp = AppManager.CreateInstance().getAccidentCaseDAO().getCase(accNum);										//temp 에 가져온 정보 저장
							
							if(temp != null)																								//검색 성공시
							{
								//각 옵션에 맞게 재 설정
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
							else																											//검색 실패시 경고창 띄움
							{	
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "사고번호를 정확히 입력하시오!", "경고", JOptionPane.PLAIN_MESSAGE);
							}
						}
						
					}
					
				});
		
		AppManager.CreateInstance().getAppMain().addActionUpdateListener(new ActionListener()												//수정/삭제 Dialog 와 관견된 이벤트
				{
					public void actionPerformed(ActionEvent arg0)
					{
						Object obj = arg0.getSource();
						if(obj == AppManager.CreateInstance().getAppMain().updateButton)													//수정 버튼을 클릭할 경우
						{
							
							AccidentCase tempCase = new AccidentCase();																		//임시 AccidentCase 저장소 선언
							boolean succFlag;																								//수정 성공 여부 boolean
							
							int caseNum = Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText());					//사고 번호 가져오기
							
							//재설정된 값들 tempCase에 저장
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
								succFlag = false;																							//하나라도 빈 옵션이 있으면 실패로 간주
							}
							else
							{
								succFlag = AppManager.CreateInstance().getAccidentCaseDAO().updateCase(tempCase);							//성공할 경우
							}
							
							
							if(succFlag == true)
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().deleteButton, "수정되었습니다.");		//수정 성공 메세지 띄우기
								
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table"); //메인프레임 table 로 바꿈.
								
								AccidentCase outputCase = new AccidentCase();
								outputCase = AppManager.CreateInstance().getAccidentCaseDAO().getCase(caseNum);								//수정된 데이터 다시 가져괴
								
								String [] temp = {Integer.toString(outputCase.getCscode()),
										outputCase.getProvince(),outputCase.getTown(), outputCase.getYear(),
										outputCase.getMonth(), outputCase.getDay(),Integer.toString(outputCase.getCasulity()),
										Integer.toString(outputCase.getDead()),Integer.toString(outputCase.getInjured()),
										outputCase.getActype()};
							
								//Table 초기화
								AppManager.CreateInstance().getAppMain().basicTable.setRowCount(0);											//table 초기화
								//Table 데이터 다시 채우기
								AppManager.CreateInstance().getAppMain().basicTable.addRow(temp);											//가져온 데이터 row 추가하기.
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "table");	//table 화면 보여주기.
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();												//수정 Dialog 종료
								
								//나머지 버튼 활성화
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
								
								updateOpenedFlag = false;																					//수정 Dialog 종료상태로 바꿈
								
								//버튼 원래 이미지로 바꿈.
								AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnBasic);
								AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnBasic);
								AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnBasic);
								
							}
							else
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "수정 실패!!", "경고", JOptionPane.PLAIN_MESSAGE);	//수정실패 창 띄우기
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();												//수정 Dialog 종료
								//나머지 버튼 활성화
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
		
								updateOpenedFlag = false;																					//수정 Dialog 종료 상태로 바꿈.
								//버튼 원래 이미지로 바꿈
								AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnBasic);
								AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnBasic);
								AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnBasic);
							
							}
						}
						else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)												//삭제버튼일경우
						{
							boolean delSuccess;																								//삭제 성공 여부 변수
							delSuccess = AppManager.CreateInstance().getAccidentCaseDAO().deleteCase(Integer.parseInt(AppManager.CreateInstance().getAppMain().caseNumTxt.getText())); //데이터 삭제
							
							if(delSuccess == true)																							//삭제 성공시
							{
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().deleteButton, "삭제되었습니다.");		//삭제 성공 메세지 띄우기
								//화면 CardLayout 으로 사진 으로 전환
								AppManager.CreateInstance().getAppMain().cardLayout.show(AppManager.CreateInstance().getAppMain().cardPanel, "image");	//table 상태에서 image 로 바꿈.
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();												//수정 Dialog 종료
								
								//나머지 버튼 활성화
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
		
								updateOpenedFlag = false;																					//수정 Dialog 종료 상태로 바꿈.
								
							}
							else																											//삭제 실패시
							{	
								JOptionPane.showMessageDialog(AppManager.CreateInstance().getAppMain().diaUpdate, "삭제 실패!!", "경고", JOptionPane.PLAIN_MESSAGE);	//삭제 실패 메세지 띄우기
								AppManager.CreateInstance().getAppMain().diaUpdate.dispose();												//삭제 실패 메세지 띄우기
								
								//나머지 버튼 활성화
								AppManager.CreateInstance().getAppMain().btns[0].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[1].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[2].setEnabled(true);
								AppManager.CreateInstance().getAppMain().btns[3].setEnabled(true);
		
								updateOpenedFlag = false;																					//수정 Dialog 종료 상태로 바꿈.
							}
							
						}
					}
					
				});
		AppManager.CreateInstance().getAppMain().addTableListener(new MouseAdapter()														//main table 관려 이벤트
				{
					public void mouseClicked(MouseEvent e)
					{
						if(e.getButton() ==1)
						{
							
						}
						if(e.getClickCount() ==2)																							//더블 클릭을 할경우
						{
							int row = AppManager.CreateInstance().getAppMain().table.getSelectedRow();										//행 번호 가져오기
							int accNum = Integer.parseInt((String)AppManager.CreateInstance().getAppMain().table.getValueAt(row, 0));		//행 번호 제일 앞 열의 속성값 가져오기
			
							AccidentCase tempCase = new AccidentCase();																		//임시 Case 변수 선언
							tempCase = AppManager.CreateInstance().getAccidentCaseDAO().getCase(accNum);									//사고 번호 겁색후 데이터 case 에 저장
				
							
							DetailInfo accDetailInfo = new DetailInfo(tempCase);															//DetailInfo Dialog 키기
							accDetailInfo.addMouseDetailMenuBarListener(new MouseAdapter()													//DetailInfo Dialog 메뉴바 관련 이벤트
							{
								@Override
								public void mousePressed(MouseEvent e)																		//메뉴바를 잡았을 떄 절대좌표를 받아옴
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.menuBarDetail)
									{
										accDetailInfo.mouseX = e.getX();
										accDetailInfo.mouseY = e.getY();				
									}
								}
							});
							accDetailInfo.addMouseDetailMenuBarMotionListener(new MouseMotionAdapter()										//메뉴바를 잡고 움직였을 떄 위치 초기화 시킴
							{
								@Override
								public void mouseDragged(MouseEvent e)
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.menuBarDetail)
									{
										int x = e.getXOnScreen();
										int y = e.getYOnScreen();
										accDetailInfo.setLocation(x - accDetailInfo.mouseX, y - accDetailInfo.mouseY);							//메뉴바를 잡고 움직였을 때 전체 Dialog도 움직이게 만듦		
									}
								}
							});
							accDetailInfo.addMouseDetailExitListener(new MouseAdapter()														//DetailInfo 종료 버튼 Mouse Listener
							{
								@Override
								public void mouseEntered(MouseEvent e)
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.detailExit)
									{
										accDetailInfo.detailExit.setIcon(ImageData.exitButtonEntered);										//마우스가 올라가있으면 Hover 이미지로 변경
									}
								}
								@Override
								public void mouseExited(MouseEvent e)
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.detailExit)
									{
											accDetailInfo.detailExit.setIcon(ImageData.exitButtonBasic);									//마우스가 내려가면 원래 이미지로 변경	
										}
								}
								@Override
								public void mouseReleased(MouseEvent e)																		//마우스가 띄우지면
								{
									Object obj = e.getSource();
									if(obj == accDetailInfo.detailExit)
									{
										accDetailInfo.dispose();																			//DetailInfo Dialog 종료
									}
								}
							});
						}
						if(e.getButton() == 3)
						{
							
						}
					}
					
				});
		AppManager.CreateInstance().getAppMain().addInsideMouseListener(new MouseAdapter()											//개별  Dialog 내부 버튼 관련 마우스 Hover Listener 
		{
				public void mouseEntered(MouseEvent e)																				//마우스가 올라가져 있을때
				{
					Object obj = e.getSource();	
					if(obj == AppManager.CreateInstance().getAppMain().searchButton)												
					{
						AppManager.CreateInstance().getAppMain().searchButton.setIcon(ImageData.searchDialogBtnEntered);			//검색 버튼 Hover 이미지로 변환
					}
					else if(obj == AppManager.CreateInstance().getAppMain().regBtn)
					{
						AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnEntered);						//등록 버튼 hover 이미지로 변환
					}
					else if(obj == AppManager.CreateInstance().getAppMain().searchUpdateBtn)
					{
						 AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnEntered);		//수정/삭제 Dialog 내부 검색 버튼 hover이미지로 변환
					}
					else if(obj == AppManager.CreateInstance().getAppMain().updateButton)
					{
						AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnEntered);			//수정 버튼 hover이미지로 변환
					}
					else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)
					{
						AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnEntered);			//삭제 버튼 Hover 이미지로 변환
					}
					
				}
				@Override
				public void mouseExited(MouseEvent e)																				//마우스가 밖으로 나갔을 경우
				{
					Object obj = e.getSource();
					if(obj == AppManager.CreateInstance().getAppMain().searchButton)
					{
						AppManager.CreateInstance().getAppMain().searchButton.setIcon(ImageData.searchDialogBtnBasic);				//검색 버튼 원래 이미지로 변경
					}
					else if(obj == AppManager.CreateInstance().getAppMain().regBtn)
					{
						AppManager.CreateInstance().getAppMain().regBtn.setIcon(ImageData.regDialogBtnBasic);						//등록 버튼 원래 이미지로 변경
					}
					else if(obj == AppManager.CreateInstance().getAppMain().searchUpdateBtn)
					{
						 AppManager.CreateInstance().getAppMain().searchUpdateBtn.setIcon(ImageData.updateSearchBtnBasic);			//수정 Dialog 내부 검색 버튼 원래 이미지로 변경
					}
					else if(obj == AppManager.CreateInstance().getAppMain().updateButton)
					{
						AppManager.CreateInstance().getAppMain().updateButton.setIcon(ImageData.updateDialogBtnBasic);				//수정 버튼 원래 이미지로 변경
					}
					else if(obj == AppManager.CreateInstance().getAppMain().deleteButton)
					{
						AppManager.CreateInstance().getAppMain().deleteButton.setIcon(ImageData.deleteDialogBtnBasic);				//삭제 버튼 원래 이미지로 변경
					}

					
				}
		});

	}
}




