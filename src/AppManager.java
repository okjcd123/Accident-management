
public class AppManager {
	private static AppManager s_instance;
	
	private AppMain m_appMain;
	private AppController m_appController;
	private AccidentCaseDAO m_accidentCaseDAO;
	
	public AppMain getAppMain() {
		return m_appMain;
	}

	public void setAppMain(AppMain m_appMain) {
		this.m_appMain = m_appMain;
	}

	public AppController getAppController() {
		return m_appController;
	}

	public void setAppController(AppController m_appController) {
		this.m_appController = m_appController;
	}

	public AccidentCaseDAO getAccidentCaseDAO() {
		return m_accidentCaseDAO;
	}

	public void setAccidentCaseDAO(AccidentCaseDAO m_accidentCaseDAO) {
		this.m_accidentCaseDAO = m_accidentCaseDAO;
	}

	public static AppManager CreateInstance()
	{
		if(s_instance == null)
		{
			s_instance = new AppManager();
		}
		return s_instance;
	}
	
}
