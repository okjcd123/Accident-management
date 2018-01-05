/**
 * AppManager.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */
public class AppManager {												//별도로 객체를 선언 하지 않음
	
	private static AppManager s_instance;								//AppManager instance 객체 생성
	
	private AppMain m_appMain;											//appMain 객체 생성
	private AppController m_appController;								//appController 객체 생성
	private AccidentCaseDAO m_accidentCaseDAO;							//accidentCaseDAO 객체 생성
	
	private AppManager()												//AppManager private 생성자 
	{
		 s_instance =null;
	}
	public AppMain getAppMain() {										//AppMain 반환
		return m_appMain;
	}

	public void setAppMain(AppMain m_appMain) {							//AppMain 객체 붙이기
		this.m_appMain = m_appMain;
	}

	public AppController getAppController() {							//AppController 반환
		return m_appController;
	}

	public void setAppController(AppController m_appController) {		//AppController 객체붙이기
		this.m_appController = m_appController;
	}

	public AccidentCaseDAO getAccidentCaseDAO() {						//AccidentCaseDAO 반환
		return m_accidentCaseDAO;
	}

	public void setAccidentCaseDAO(AccidentCaseDAO m_accidentCaseDAO) {	//AccidentCaseDAO 객체 붙이기
		this.m_accidentCaseDAO = m_accidentCaseDAO;
	}

	public static AppManager CreateInstance()							//AppManager Instance 설정
	{
		if(s_instance == null)											//Instance 변수가 없으면 생성 하여반환
		{
			s_instance = new AppManager();
			return s_instance;
		}
		else															//Instance 변수가 있다면 그대로 반환
		{
			return s_instance;		
		}
	}
	
}
