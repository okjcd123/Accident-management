/**
 * ImageData.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */
import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageData {
	
	public static Image introImage = new ImageIcon(Execute.class.getResource("images/introPanel.png")).getImage();			//intro이미지
	public static ImageIcon mainImage = new ImageIcon(Execute.class.getResource("images/mainImage.png"));					//main table 에 들어갈 이미지
	public static ImageIcon loginBaiscImage = new ImageIcon(Execute.class.getResource("images/loginBasicButton.png"));		//로그인 버튼 basic
	public static ImageIcon loginEnteredImage = new ImageIcon(Execute.class.getResource("images/loginEnteredButton.png"));	//로그인 버튼 Hover 상태 이미지

	public static ImageIcon analysisBasic = new ImageIcon(Execute.class.getResource("images/analysisBasic.png"));			//분석 Dialog 버튼 이미지
	public static ImageIcon analysisEntered = new ImageIcon(Execute.class.getResource("images/analysisEntered.png"));		//분석 Dialog Hover 버튼 이미지
	public static ImageIcon regBasic = new ImageIcon(Execute.class.getResource("images/regBasic.png"));						//등록 Dialog 버튼 이미지
	public static ImageIcon regEntered = new ImageIcon(Execute.class.getResource("images/regEntered.png"));					//등록 Dialog Hover 버튼 이미지
	public static ImageIcon searchBasic = new ImageIcon(Execute.class.getResource("images/searchBasic.png"));				//검색 Dialog 버튼 이미지
	public static ImageIcon searchEntered = new ImageIcon(Execute.class.getResource("images/searchEntered.png"));			//검색 Dialog 버튼 Hover 이미지
	public static ImageIcon updateBasic = new ImageIcon(Execute.class.getResource("images/updateBasic.png"));				//수정/삭제 Dialog 버튼 이미지
	public static ImageIcon updateEntered = new ImageIcon(Execute.class.getResource("images/updateEntered.png"));			//수정/삭제 Dialog Hover 이미지
	
	public static ImageIcon menuBar = new ImageIcon(Execute.class.getResource("images/menuBar.png"));						//기본 메뉴바 이미지
	public static ImageIcon menuBarAnalysis = new ImageIcon(Execute.class.getResource("images/menuBarAnalysis.png"));		//분석 Dialog 메뉴바 이미지
	public static ImageIcon menuBarDetail = new ImageIcon(Execute.class.getResource("images/menuBarDetail.png"));			//DetailInfo Dialog 메뉴바 이미지
	public static ImageIcon menuBarRegist = new ImageIcon(Execute.class.getResource("images/menuBarRegist.png"));			//등록 Dialog 메뉴바 이미지
	public static ImageIcon menuBarSearch = new ImageIcon(Execute.class.getResource("images/menuBarSearch.png"));			//검색 Dialog 메뉴바 이미지
	public static ImageIcon menuBarUpdate = new ImageIcon(Execute.class.getResource("images/menuBarUpdate.png"));			//수정/삭제 Dialog 메뉴바 이미지
	public static ImageIcon exitButtonBasic = new ImageIcon(Execute.class.getResource("images/exitButtonBasic.png"));		//종료 버튼 이미지
	public static ImageIcon exitButtonEntered = new ImageIcon(Execute.class.getResource("images/exitButtonEntered.png"));	//종료 버튼 Hover 이미지
	
	//-----------------------------------------------------------------------------------------------------------------------------
	public static ImageIcon searchDialogBtnBasic = new ImageIcon(Execute.class.getResource("images/searchDialogBtnBasic.png"));		//검색 버튼 이미지
	public static ImageIcon searchDialogBtnEntered = new ImageIcon(Execute.class.getResource("images/searchDialogBtnEntered.png"));	//검색 버튼 Hover 이미지
	 
	public static ImageIcon regDialogBtnBasic = new ImageIcon(Execute.class.getResource("images/regDialogBtnBasic.png"));			//등록 버튼 이미지
	public static ImageIcon regDialogBtnEntered = new ImageIcon(Execute.class.getResource("images/regDialogBtnEntered.png"));		//등록 버튼 Hover이미지
   
	public static ImageIcon updateSearchBtnBasic = new ImageIcon(Execute.class.getResource("images/updateSearchBtnBasic.png"));		//수정/삭제 Dialog 내부 검색 버튼 이미지 
	public static ImageIcon updateSearchBtnEntered = new ImageIcon(Execute.class.getResource("images/updateSearchBtnEntered.png")); //수정/삭제 Dialog 내부 검색 버튼 Hover이미지
   
	public static ImageIcon updateDialogBtnBasic = new ImageIcon(Execute.class.getResource("images/updateDialogBtnBasic.png"));		//수정 버튼 이미지
	public static ImageIcon updateDialogBtnEntered = new ImageIcon(Execute.class.getResource("images/updateDialogBtnEntered.png"));	//수정 버튼 Hover 이미지
   
	public static ImageIcon deleteDialogBtnBasic = new ImageIcon(Execute.class.getResource("images/deleteDialogBtnBasic.png"));		//삭제 버튼 이미지
	public static ImageIcon deleteDialogBtnEntered = new ImageIcon(Execute.class.getResource("images/deleteDialogBtnEntered.png"));	//식제 버튼 Hover 이미지

	
	//----------------------------------------------------------------------------------------------------------------
	public static ImageIcon viewChartBasic = new ImageIcon(Execute.class.getResource("images/viewChartBasic.png"));					//차트보기 버튼 이미지
	public static ImageIcon viewChartEntered = new ImageIcon(Execute.class.getResource("images/viewChartEntered.png"));				//차트보기 버튼 Hover이미지

	public static ImageIcon viewMapBasic = new ImageIcon(Execute.class.getResource("images/viewMapBasic.png"));						//지도보기 버튼 이미지
	public static ImageIcon viewMapEntered = new ImageIcon(Execute.class.getResource("images/viewMapEntered.png"));					//지도 보기 버튼 Hover이미지
	
	public static ImageIcon parsingBasic = new ImageIcon(Execute.class.getResource("images/parsingBtnBasic.png"));					//Parsing 버튼 이미지
	public static ImageIcon parsingEntered = new ImageIcon(Execute.class.getResource("images/parsingBtnEntered.png"));				//Parsing 버튼 Hover 이미지

	public static ImageIcon resetBasic = new ImageIcon(Execute.class.getResource("images/resetBtnBasic.png"));						//초기화 버튼 이미지
	public static ImageIcon resetEntered = new ImageIcon(Execute.class.getResource("images/resetBtnEntered.png"));					//초기화 버튼 Hover이미지


}
