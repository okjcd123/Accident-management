/**
 * AppMain.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

public class AppMain extends JFrame{

   public boolean status = false;
   
   protected String [] header = {"사고번호", "시/도", "구/군", "발생 년도", "월", "일", "사상자", "사망자", "부상자", "사고 유형"};
   protected String contents[][] = {{"1", "서울특별시", "강남구", "2017", "02", "28", "1", "0" ,"1", "차대사람"}
   ,{"1", "서울특별시", "강남구", "2017", "02", "28", "1", "0" ,"1", "차대차"}};
   
   //각각 콤보박스에 필요한 스트링 배열
   protected   String[] year = {"년도","2012","2013","2014","2015","2016","2017","2018"};
   protected String[] month = {"월","01","02","03","04","05","06","07","08","09","10","11","12",};
   protected String[] day = {"일","01","02","03","04","05","06","07","08","09","10","11","12","13",
         "14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
   
   protected String[] province = {"전체","서울특별시","인천광역시","경기도"};
   protected String[] accOptionType = {"차대차", "차대사람", "차량단독"};

   //각 Dialog MenuBar 및  Exit 버튼 통제
   protected JLabel menuBar = new JLabel(ImageData.menuBar);
   protected JLabel menuBarSearch = new JLabel(ImageData.menuBarSearch);
   protected JLabel menuBarRegist = new JLabel(ImageData.menuBarRegist);
   protected JLabel menuBarUpdate = new JLabel(ImageData.menuBarUpdate);
   
   protected JButton exit = new JButton(ImageData.exitButtonBasic);
   protected JButton searchExit = new JButton(ImageData.exitButtonBasic);
   protected JButton registerExit = new JButton(ImageData.exitButtonBasic);
   protected JButton updateExit = new JButton(ImageData.exitButtonBasic);
   
   protected int mainMouseX; int mainMouseY;
   protected int searchMouseX; int searchMouseY;
   protected int registMouseX; int registMouseY;
   protected int updateMouseX; int updateMouseY;
   
   //기본 옵션 패널 라벨----------------------------------------------------------------------
   protected JPanel[] panels;
   protected JLabel[] labels;
   protected Intro introPanel = new Intro();
   protected JPanel primary = new JPanel();
   

   //버튼----------------------------------------------------------------------------------
   protected JPanel bPanel;
   protected JButton[] btns;
   
   //JTable---------------------------------------------------------------------------------

   DefaultTableModel basicTable =new DefaultTableModel();
   
   protected JTable table = new JTable(basicTable)
   {
      public boolean isCellEditable(int row, int column)
      {
         return false;
      }
   };
   protected JScrollPane scroll;
   protected CardLayout cardLayout = new CardLayout();
   protected Container cardPanel = new JPanel();
   protected JPanel tablePanel = new JPanel();
   protected JPanel imagePanel = new JPanel();
   protected JLabel imageLabel = new JLabel(ImageData.mainImage);

   //사고 검색--------------------------------------------------------------------------------
   protected JDialog diaSearch;
   protected JPanel searchUpPanel;
   protected JPanel searchDownPanel;
   protected JPanel searchButtonPanel;
   
   protected JLabel siDolbl;
   protected JLabel guGunlbl;
   protected JComboBox siDo = new JComboBox(province);
   protected JComboBox guGun = new JComboBox();
   
   protected JComboBox yearcbSearch = new JComboBox(year);
   protected JComboBox monthcbSearch  = new JComboBox(month);
   protected JButton searchButton = new JButton(ImageData.searchDialogBtnBasic);
   
   
   //사고 등록--------------------------------------------------------------------------------
   protected JDialog dia;
   protected JPanel leftPanel;
   
   protected JLabel label1;   //장소
   protected JLabel label2;   //날짜
   protected JLabel label3;   //사상자 수
   protected JLabel label4;   //사고 타입
   protected JLabel label5;   //"위도, 경도
   
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
   
   //사상사수 입력 부분
   protected JPanel casualty;
   protected   JLabel tmp1;
   protected   JLabel tmp2;
   
   //사고 타입 부분
   protected JComboBox accType = new JComboBox(accOptionType);
   
   //위도 경도 입력부분
   protected   JPanel locInfo;
   protected   JLabel laTmp;
   protected   JLabel loTmp;
   
   //하단 패널
   protected JPanel subPanel;
   protected JButton regBtn = new JButton(ImageData.regDialogBtnBasic);
   
   //사고 수정/삭제-----------------------------------------------------------------------------------------
   protected JDialog diaUpdate;
   protected JPanel leftUpdatePanel;
   protected JLabel caseNum;         //사건 번호
   protected JLabel labelUpdate1;     //장소
   protected JLabel labelUpdate2;    //날짜
   protected JLabel labelUpdate3;    //사상자 수
   protected JLabel labelUpdate4;     //사고 타입
   protected JLabel labelUpdate5;    //"위도, 경도
   
   protected JPanel rightUpdatePanel;
   
   protected JPanel searchCaseNumPanel;   //케이스 검색 패널
   
   protected JTextField caseNumTxt = new JTextField();      //사건 번호 입력란
   protected JButton searchUpdateBtn = new JButton(ImageData.updateSearchBtnBasic);   //사건 번호 검색;      //사건 번호 검색
   
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
   protected   JPanel casualtyUpdate;
   protected   JLabel tmp1Update;
   protected   JLabel tmp2Update;
   
   //사고타입 부분
   protected JComboBox accTypeUpdate = new JComboBox(accOptionType);
   
   
   //위도 경도 입력부분
   protected   JPanel locInfoUpdate;
   protected   JLabel laTmpUpdate;
   protected   JLabel loTmpUpdate;
   
   //하단 패널
   protected JPanel subUpdatePanel;
   protected JButton updateButton = new JButton(ImageData.updateDialogBtnBasic);
   protected JButton deleteButton = new JButton(ImageData.deleteDialogBtnBasic);
   
   //사고 분석----------------------------------------------------------------------------
   
   //하단 메세지-------------------------------------------------------------------------------
   protected JLabel accInfo;

   public AppMain() {
      
      AppManager.CreateInstance().setAppMain(this);//AppManager에 AppMain 객체 등록
      setTitle("교통 사고 관리 시스템");
      setSize(Execute.WIDTH,Execute.HEIGHT-60);
      setResizable(false);                        // 프레임크기 변경 불가하게 false 처리
      setUndecorated(true);						  // 프레임 타이틀바를 없앤다.
      setLocation(250,250);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      getContentPane().add(introPanel);
     
      //getContentPane().add(primary);
      //------------------------------------------------------------------------
      exit.setBounds(1260, 5, 30, 30);           // 종료버튼 위치지정
      exit.setBorderPainted(false);              // 버튼의 외곽선 없앤다.
      exit.setContentAreaFilled(false);          // 버튼 내용영역 채우지 않음
      exit.setFocusPainted(false);           	 // 버튼 선택되었을때 생기는 테두리 사용안함
      primary.add(exit);
      //-----------------------
      menuBar.setBounds(0,0,1300, 40);
      primary.add(menuBar);
      
      primary.setBounds(0,0,Execute.WIDTH,Execute.HEIGHT-60);
      primary.setLayout(null);
   
      bPanel = new JPanel();
      bPanel.setLayout(null);
      bPanel.setBounds(0, 0, 1300, 110);
      
      // 메인화면 버튼을 배열로 생성후 패널에 추가
      btns = new JButton[4];
      btns[0] = new JButton(ImageData.searchBasic);
      btns[0].setBounds(20,45,311,60);
      btns[0].setBorder(null);
      bPanel.add(btns[0]);
      
      btns[1] = new JButton(ImageData.regBasic);
      btns[1].setBounds(20+311 + 5,45,311,60);
      btns[1].setBorder(null);
      bPanel.add(btns[1]);
      
      btns[2] = new JButton(ImageData.updateBasic);
      btns[2].setBounds(20 + 311 + 5 + 311 + 5,45,311,60);
      btns[2].setBorder(null);
      bPanel.add(btns[2]);
      
      btns[3] = new JButton(ImageData.analysisBasic);
      btns[3].setBorder(null);
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
         
      setVisible(true);
   }
   
   public class Intro extends JPanel
   {
      //intro관련 요소들------------------------------------------------------------------------
      JTextField idField = new JTextField("");
      JPasswordField pwField = new JPasswordField("");
      JButton loginBtn = new JButton(ImageData.loginBaiscImage);
        
      public Intro()
      {
    	  // 각 컴포넌트 위치 지정후 패널에 추가
         setBounds(0,0,Execute.WIDTH,Execute.HEIGHT);
         setLayout(null);
         idField.setBounds(980, 500, 140, 30);
         pwField.setBounds(980, 540, 140,30);
         loginBtn.setBounds(1130,500,70,70);
           
         add(idField);
         add(pwField);
         add(loginBtn);
      }
      public void paintComponent(Graphics g)
      {
         if(status == false)                 // status가 false 이면 인트로 이미지를 패널에 채워준다.
         {
            super.paintComponents(g);
            g.drawImage(ImageData.introImage,0, 0, null);
         }
      }
      //버튼에 리스너를 달아준다.
      public void addActionLoginButtonListener(ActionListener action) {
         // TODO Auto-generated method stub
         introPanel.loginBtn.addActionListener(action);
      }
      
      public void addMouseLoginButtonListener(MouseAdapter mouse)
      {
    	  introPanel.loginBtn.addMouseListener(mouse);
      }

   }
   
   public void search() 
   {
       diaSearch = new JDialog();
       diaSearch.setSize(430,300);
       diaSearch.setResizable(false);    // 프레임크기 변경 불가하게 만들기
       diaSearch.setUndecorated(true);   // 타이틀바 없애기
       diaSearch.setAlwaysOnTop(true);   // 항상 제일 위의 창
       diaSearch.setLayout(null);
       
       JPanel upPanel = new JPanel();
       upPanel.setBounds(0,0,430,40);
       upPanel.setLayout(null);
       
       searchExit.setBounds(390, 5, 30, 30);
       searchExit.setBorderPainted(false);
       searchExit.setContentAreaFilled(false);
       searchExit.setFocusPainted(false);
       upPanel.add(searchExit);
       
       //-----------------------
       menuBarSearch.setBounds(0,0,430, 40);
       upPanel.add(menuBarSearch);
       diaSearch.add(upPanel);
       
       //라벨패널
       searchUpPanel = new JPanel();
       searchUpPanel.setBounds(0,40,100,180);
       searchUpPanel.setLayout(new GridLayout(3,1));
       searchUpPanel.setBackground(Color.LIGHT_GRAY);
      
       JPanel siDoPn = new JPanel();
       siDoPn.setLayout(null);
       siDoPn.setBorder(new EtchedBorder(EtchedBorder.LOWERED)); // 패널에 테두리 추가
       siDolbl = new JLabel("시/도");
       siDolbl.setBounds(35,18,50,30);
       siDoPn.add(siDolbl);
       searchUpPanel.add(siDoPn);
       
       JPanel guGunPn = new JPanel();
       guGunPn.setLayout(null);
       guGunPn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       guGunlbl = new JLabel("군/구");
       guGunlbl.setBounds(35,18,50,30);
       guGunPn.add(guGunlbl);
       searchUpPanel.add(guGunPn);
       
       JPanel CalPn = new JPanel();
       CalPn.setLayout(null);
       CalPn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
      
       JLabel calLbl = new JLabel("년/월");
       calLbl.setBounds(35,18,50,30);
       CalPn.add(calLbl);
       
       searchUpPanel.add(CalPn);
       
       
       //콤보박스 패널관리
       searchDownPanel = new JPanel();
       searchDownPanel.setBounds(100,40,330,180);
       searchDownPanel.setBackground(Color.WHITE);
       searchDownPanel.setLayout(new GridLayout(3,1));
       
       JPanel sidoCbPn = new JPanel();
       sidoCbPn.setLayout(null);
       sidoCbPn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       sidoCbPn.setBackground(Color.WHITE);
       siDo.setBounds(10,10,120,40);
       sidoCbPn.add(siDo);
       searchDownPanel.add(sidoCbPn);

       JPanel gunguCbPn = new JPanel();
       gunguCbPn.setLayout(null);
       gunguCbPn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       gunguCbPn.setBackground(Color.WHITE);
       guGun.setBounds(10,10,120,40);
       gunguCbPn.add(guGun);
       searchDownPanel.add(gunguCbPn);
       
       JPanel calCbPn = new JPanel();
       calCbPn.setLayout(null);
       calCbPn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       calCbPn.setBackground(Color.WHITE);
       yearcbSearch.setBounds(10,10,120,40);
       monthcbSearch.setBounds(140,10,100,40);
       calCbPn.add(yearcbSearch);
       calCbPn.add(monthcbSearch);
        
       searchDownPanel.add(calCbPn);
      
       //버튼패널관리
       searchButtonPanel = new JPanel();
       searchButtonPanel.setLayout(null);
       searchButtonPanel.setBounds(0,220,400,80);
       searchButton.setBounds(135, 15, 150, 50);
       searchButtonPanel.add(searchButton);

       diaSearch.add(searchUpPanel);
       diaSearch.add(searchDownPanel);
       diaSearch.add(searchButtonPanel);
       diaSearch.setVisible(true);
    }
  
   //사고 등록 버튼을 눌렀을 때
  public void registration() {

       dia = new JDialog();
       dia.setLayout(null);
       dia.setResizable(false);
       dia.setSize(550,420);
       dia.setUndecorated(true);
       dia.setAlwaysOnTop(true);  //항상 제일 위에 창에 위치
       
       JPanel upRegist = new JPanel();
       upRegist.setLayout(null);
       upRegist.setBounds(0,0,550,40);
       dia.add(upRegist);
       
       registerExit.setBounds(520, 5,30 ,30);
       registerExit.setBorderPainted(false);
       registerExit.setContentAreaFilled(false);
       registerExit.setFocusPainted(false);
       upRegist.add(registerExit);
       
       menuBarRegist.setBounds(0,0,550,40);
       upRegist.add(menuBarRegist);

       //leftPanel 관련 사항 --------------------------------------------------
       leftPanel = new JPanel();
       leftPanel.setBackground(Color.LIGHT_GRAY);

       leftPanel.setLayout(new GridLayout(5,1));
       leftPanel.setBounds(0,40,100,300);

       JPanel lap1 = new JPanel();
       JPanel lap2 = new JPanel();
       JPanel lap3 = new JPanel();
       JPanel lap4 = new JPanel();
       JPanel lap5 = new JPanel();
       //각 패널에 테두리 추가
       lap1.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lap2.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lap3.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lap4.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lap5.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

       lap1.setLayout(null);
       lap2.setLayout(null);
       lap3.setLayout(null);
       lap4.setLayout(null);
       lap5.setLayout(null);

       label1 = new JLabel("장소",JLabel.CENTER);
       label2 = new JLabel("날짜",JLabel.CENTER);
       label3 = new JLabel("사상자 수",JLabel.CENTER);
       label4 = new JLabel("사고 타입",JLabel.CENTER);
       label5 = new JLabel("위도, 경도",JLabel.CENTER);
       
       label1.setBounds(35,18,30,30);
       label2.setBounds(35,18,30,30);
       label3.setBounds(20,18,60,30);
       label4.setBounds(20,18,60,30);
       label5.setBounds(20,18,60,30);
       
       lap1.add(label1);
       lap2.add(label2);
       lap3.add(label3);
       lap4.add(label4);
       lap5.add(label5);

       leftPanel.add(lap1);
       leftPanel.add(lap2);
       leftPanel.add(lap3);
       leftPanel.add(lap4);
       leftPanel.add(lap5);

       dia.add(leftPanel);

       //rightPanel 관련 사항 --------------------------------------------------

       rightPanel = new JPanel();
       rightPanel.setBounds(100,40,700,300);
       rightPanel.setLayout(new GridLayout(5,1));
       rightPanel.setBackground(Color.WHITE);
       dia.add(rightPanel);

       //장소입력부분
       loc = new JPanel();
       loc.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       loc.setLayout(null);
       loc.setBackground(Color.WHITE);
       pro.setBounds(10,10,120,40);
       tow.setBounds(140,10,100,40);

       loc.add(pro);
       loc.add(tow);
       rightPanel.add(loc);

       //날짜입력부분
       time = new JPanel();
       time.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       time.setLayout(null);
       time.setBackground(Color.WHITE);

       yearcb.setBounds(10,10,120,40);
       monthcb.setBounds(140,10,100,40);
       daycb.setBounds(260,10,100,40);

       time.add(yearcb);
       time.add(monthcb);
       time.add(daycb);
       rightPanel.add(time);

       //사상사수 입력 부분
       casualty = new JPanel();
       casualty.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       casualty.setLayout(null);
       casualty.setBackground(Color.WHITE);

       tmp1 = new JLabel("사망자 수");
       tmp2 = new JLabel("부상자 수");

       tmp1.setBounds(10,10,60,40);
       dead.setBounds(80,10,50,40);
       dead.setFont(new Font("돋움",Font.PLAIN,15)); //텍스트 필드 폰트값 지정
       tmp2.setBounds(140,10,60,40);
       injured.setBounds(210,10,50,40);
       injured.setFont(new Font("돋움",Font.PLAIN,15)); //텍스트 필드 폰트값 지정

       casualty.add(tmp1); casualty.add(dead);
       casualty.add(tmp2); casualty.add(injured);
       rightPanel.add(casualty);

       //사고타입 입력부분
       JPanel typeTmp = new JPanel();
       typeTmp.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       typeTmp.setLayout(null);
       accType.setBounds(10,10,120,40);
       typeTmp.setBackground(Color.WHITE);
       typeTmp.add(accType);
       rightPanel.add(typeTmp);

       //위도 경도 입력부분
       locInfo = new JPanel();
       locInfo.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       locInfo.setLayout(null);
       locInfo.setBackground(Color.WHITE);
       laTmp = new JLabel("위도");
       loTmp = new JLabel("경도");

       laTmp.setBounds(10,10,40,40);
       lati.setBounds(60,10,70,40);
       lati.setFont(new Font("돋움",Font.PLAIN,15));
       loTmp.setBounds(140,10,40,40);
       longi.setBounds(190,10,70,40);
       longi.setFont(new Font("돋움",Font.PLAIN,15));
       locInfo.add(laTmp);locInfo.add(lati);
       locInfo.add(loTmp);locInfo.add(longi);
       rightPanel.add(locInfo);

       //subPanel----------------------------------------------------------------------------
       subPanel = new JPanel();
       subPanel.setLayout(null);
       subPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       subPanel.setBounds(0,340,550,80);
       regBtn.setBounds(193, 15, 150, 50);
       subPanel.add(regBtn);
       dia.add(subPanel);

       dia.setVisible(true);
    }
  
  //사고 수정/삭제 버튼을 눌렀을 때
    public void modifyDelete() {
         
       diaUpdate = new JDialog();
       diaUpdate.setLayout(null);
       diaUpdate.setResizable(false);
       diaUpdate.setSize(550,480);
       diaUpdate.setUndecorated(true);
       diaUpdate.setAlwaysOnTop(true);  //항상 제일 위에 창에 위치
       
       JPanel upUpdate = new JPanel();
       upUpdate.setLayout(null);
       upUpdate.setBounds(0,0,550,40);
       diaUpdate.add(upUpdate);
       
       updateExit.setBounds(520, 5,30 ,30);
       updateExit.setBorderPainted(false);
       updateExit.setContentAreaFilled(false);
       updateExit.setFocusPainted(false);
       upUpdate.add(updateExit);
       
       menuBarUpdate.setBounds(0,0,550,40);
       upUpdate.add(menuBarUpdate);

       
       //leftPanel 관련 사항 --------------------------------------------------
       leftUpdatePanel = new JPanel();
       leftUpdatePanel.setBackground(Color.LIGHT_GRAY);
       leftUpdatePanel.setLayout(new GridLayout(6,1));
       leftUpdatePanel.setBounds(0,40,120,360);

       JPanel caseLap = new JPanel();
       JPanel lapUpdate1 = new JPanel();
       JPanel lapUpdate2 = new JPanel();
       JPanel lapUpdate3 = new JPanel();
       JPanel lapUpdate4 = new JPanel();
       JPanel lapUpdate5 = new JPanel();

       //각 패널에 테두리 추가
       caseLap.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lapUpdate1.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lapUpdate2.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lapUpdate3.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lapUpdate4.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       lapUpdate5.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

       caseLap.setLayout(null);
       lapUpdate1.setLayout(null);
       lapUpdate2.setLayout(null);
       lapUpdate3.setLayout(null);
       lapUpdate4.setLayout(null);
       lapUpdate5.setLayout(null);
       
       caseNum = new JLabel("사고번호 입력",JLabel.CENTER);
       labelUpdate1 = new JLabel("장소",JLabel.CENTER);
       labelUpdate2 = new JLabel("날짜",JLabel.CENTER);
       labelUpdate3 = new JLabel("사상자 수",JLabel.CENTER);
       labelUpdate4 = new JLabel("사고 타입",JLabel.CENTER);
       labelUpdate5 = new JLabel("위도, 경도",JLabel.CENTER);

       caseNum.setBounds(15, 15, 90, 30);
       labelUpdate1.setBounds(43,18,30,30);
       labelUpdate2.setBounds(43,18,30,30);
       labelUpdate3.setBounds(28,18,60,30);
       labelUpdate4.setBounds(28,18,60,30);
       labelUpdate5.setBounds(28,18,60,30);

       caseLap.add(caseNum);
       lapUpdate1.add(labelUpdate1);
       lapUpdate2.add(labelUpdate2);
       lapUpdate3.add(labelUpdate3);
       lapUpdate4.add(labelUpdate4);
       lapUpdate5.add(labelUpdate5);


       leftUpdatePanel.add(caseLap);
       leftUpdatePanel.add(lapUpdate1);
       leftUpdatePanel.add(lapUpdate2);
       leftUpdatePanel.add(lapUpdate3);
       leftUpdatePanel.add(lapUpdate4);
       leftUpdatePanel.add(lapUpdate5);

       diaUpdate.add(leftUpdatePanel);

       //rightPanel 관련 사항 --------------------------------------------------

       rightUpdatePanel = new JPanel();
       rightUpdatePanel.setBounds(120,40,680,360);
       rightUpdatePanel.setLayout(new GridLayout(6,1));
       rightUpdatePanel.setBackground(Color.WHITE);

       diaUpdate.add(rightUpdatePanel);

       //사건 번호 입력 부분
       searchCaseNumPanel = new JPanel();//케이스 검색 패널
       searchCaseNumPanel.setLayout(null);
       searchCaseNumPanel.setBackground(Color.WHITE);
       searchCaseNumPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       caseNumTxt = new JTextField(10);      //사건 번호 입력란
       caseNumTxt.setFont(new Font("돋움",Font.PLAIN,20));
       caseNumTxt.setBounds(10,10,70,40);
       searchUpdateBtn.setBounds(90,10,70,40);
       searchCaseNumPanel.add(caseNumTxt);
       searchCaseNumPanel.add(searchUpdateBtn);
       rightUpdatePanel.add(searchCaseNumPanel);

       //장소입력부분
       locUpdate = new JPanel();
       locUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       locUpdate.setLayout(null);
       locUpdate.setBackground(Color.WHITE);
       proUpdate.setBounds(10,10,120,40);
       towUpdate.setBounds(140,10,100,40);

       locUpdate.add(proUpdate);
       locUpdate.add(towUpdate);
       rightUpdatePanel.add(locUpdate);

       //날짜입력부분
       timeUpdate = new JPanel();
       timeUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       timeUpdate.setLayout(null);
       timeUpdate.setBackground(Color.WHITE);

       yearcbUpdate.setBounds(10,10,120,40);
       monthcbUpdate.setBounds(140,10,100,40);
       daycbUpdate.setBounds(260,10,100,40);

       timeUpdate.add(yearcbUpdate);
       timeUpdate.add(monthcbUpdate);
       timeUpdate.add(daycbUpdate);
       rightUpdatePanel.add(timeUpdate);

       //사상사수 입력 부분
       casualtyUpdate = new JPanel();
       casualtyUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       casualtyUpdate.setLayout(null);
       casualtyUpdate.setBackground(Color.WHITE);

       tmp1Update = new JLabel("사망자 수");
       tmp2Update = new JLabel("부상자 수");

       tmp1Update.setBounds(10,10,60,40);
       deadUpdate.setBounds(80,10,50,40);
       deadUpdate.setFont(new Font("돋움",Font.PLAIN,15));
       tmp2Update.setBounds(140,10,60,40);
       injuredUpdate.setBounds(210,10,50,40);
       injuredUpdate.setFont(new Font("돋움",Font.PLAIN,15));

       casualtyUpdate.add(tmp1Update); casualtyUpdate.add(deadUpdate);
       casualtyUpdate.add(tmp2Update); casualtyUpdate.add(injuredUpdate);
       rightUpdatePanel.add(casualtyUpdate);

       //사고타입 입력부분
       JPanel typeTmpUpdate = new JPanel();
       typeTmpUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       typeTmpUpdate.setLayout(null);
       accTypeUpdate.setBounds(10,10,120,40);
       typeTmpUpdate.setBackground(Color.WHITE);
       typeTmpUpdate.add(accTypeUpdate);
       rightUpdatePanel.add(typeTmpUpdate);

       //위도 경도 입력부분
       locInfoUpdate = new JPanel();
       locInfoUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       locInfoUpdate.setLayout(null);
       locInfoUpdate.setBackground(Color.WHITE);
       laTmpUpdate = new JLabel("위도");
       loTmpUpdate = new JLabel("경도");

       
       laTmpUpdate.setBounds(10,15,40,40);
       latiUpdate.setBounds(60,15,70,40);
       latiUpdate.setFont(new Font("돋움",Font.PLAIN,15));
       loTmpUpdate.setBounds(140,15,40,40);
       longiUpdate.setBounds(190,15,70,40);
       
       longiUpdate.setFont(new Font("돋움",Font.PLAIN,15));
       laTmpUpdate.setBounds(10,10,40,40);
       latiUpdate.setBounds(60,10,70,40);
       latiUpdate.setFont(new Font("돋움",Font.PLAIN,15));
       loTmpUpdate.setBounds(140,10,40,40);
       longiUpdate.setBounds(190,10,70,40);
       longiUpdate.setFont(new Font("돋움",Font.PLAIN,15));
       locInfoUpdate.add(laTmpUpdate);locInfoUpdate.add(latiUpdate);
       locInfoUpdate.add(loTmpUpdate);locInfoUpdate.add(longiUpdate);
       rightUpdatePanel.add(locInfoUpdate);

       //subPanel----------------------------------------------------------------------------
       subUpdatePanel = new JPanel();
       subUpdatePanel.setLayout(null);
       subUpdatePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
       subUpdatePanel.setBounds(0,400,550,80);
       updateButton.setBounds(100,15,150,50);
       deleteButton.setBounds(290,15,150,50);
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
   
   public void addMouseAdapterHoverListener(MouseAdapter mouse)
   {
	   btns[0].addMouseListener(mouse);
	   btns[1].addMouseListener(mouse);
	   btns[2].addMouseListener(mouse);
	   btns[3].addMouseListener(mouse);
   }
   
   public void addMouseAdapterButtonListener(MouseAdapter mouse)
   {
      menuBar.addMouseListener(mouse);
      menuBarSearch.addMouseListener(mouse);
      menuBarRegist.addMouseListener(mouse);
      menuBarUpdate.addMouseListener(mouse);
   }
   
   public void addMouseMotionButtonListener(MouseMotionAdapter mouse)
   {
      menuBar.addMouseMotionListener(mouse);
      menuBarSearch.addMouseMotionListener(mouse);
      menuBarRegist.addMouseMotionListener(mouse);
      menuBarUpdate.addMouseMotionListener(mouse);
   }
   
   public void addExitMouseListener(MouseAdapter mouse)
   {
      exit.addMouseListener(mouse);
      searchExit.addMouseListener(mouse);
      registerExit.addMouseListener(mouse);
      updateExit.addMouseListener(mouse);   
   }
   
   public void addTableListener(MouseAdapter mouse)
   {
      table.addMouseListener(mouse);
   }
   
   public void addInsideMouseListener(MouseAdapter mouse)
   {
	   searchButton.addMouseListener(mouse);
	   regBtn.addMouseListener(mouse);
	   searchUpdateBtn.addMouseListener(mouse);
	   updateButton.addMouseListener(mouse);
	   deleteButton.addMouseListener(mouse);
	   
   }

}