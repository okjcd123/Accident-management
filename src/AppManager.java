
public class AppManager {
	
	private AppMain m_appMain;
	private AccidentCaseDAO	m_accidentCaseDAO;
	private AccidentAnalysis m_accidentAnalysis;
	private AppController m_appController;

	public AppMain getM_appMain() {
		return m_appMain;
	}
	public void setM_appMain(AppMain m_appMain) {
		this.m_appMain = m_appMain;
	}
	public AccidentCaseDAO getM_accidentCaseDAO() {
		return m_accidentCaseDAO;
	}
	public void setM_accidentCaseDAO(AccidentCaseDAO m_accidentCaseDAO) {
		this.m_accidentCaseDAO = m_accidentCaseDAO;
	}
	public AccidentAnalysis getM_accidentAnalysis() {
		return m_accidentAnalysis;
	}
	public void setM_accidentAnalysis(AccidentAnalysis m_accidentAnalysis) {
		this.m_accidentAnalysis = m_accidentAnalysis;
	}
	public AppController getM_appController() {
		return m_appController;
	}
	public void setM_appController(AppController m_appController) {
		this.m_appController = m_appController;
	}
	
	private static AppManager s_instance;
	
	public static AppManager getInstance()
	{
		if(s_instance == null)
		{
			s_instance = new AppManager();
		}
		return s_instance;		
	}
	
}
