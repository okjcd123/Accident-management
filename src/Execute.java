/**
 * Execute.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */
public class Execute {
	
	public final static int WIDTH = 1300;					//프레임 가로 길이
	public final static int HEIGHT = 700;					//프레임 세로 길이
	
	public static void main(String[] args) {
		
		new AppMain();										//AppMain 객체 생성
		new AppController();								//AppController 객체 생성
		new AccidentCaseDAO();								//AccidentCaseDAO 객체 생성
		
	}
}
