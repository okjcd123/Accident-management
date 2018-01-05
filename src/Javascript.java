/**
 * Javascript.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 2일
 */
import java.util.ArrayList;

public class Javascript {
	private String offset;													//(javascript)기본 옵션
	private String markers;													//(javascript)마커 (아이콘) 옵션
	private String others;													//(javascript)별도 옵션
	private String src;														//(javascript)필수 소스 옵션
	
	private ArrayList <AccidentCase> accCase = new ArrayList<AccidentCase>();
	
	public Javascript() {
		offset = new String("");
		markers = new String("");
		others = new String("");
		src = new String("");
	}
	
	public String getScript() {									//설정된 javascript 코드 반환
		String script = new String(offset+markers+others+src);
		return script;
	}
	public void resetScript()									//script코드 전면 reset
	{
		offset = "";
		markers = "";
		others = "";
		src = "";
		
	}
	public void setDetailMain(String dstLat, String dstLng)		//DetailInfo Dialog 에서 사용할 함수 javascript에 필요한 코드를 한번에 생성
	{
		offset = new String("document.body.style.padding = 0;"+											//지도 초기화
				"document.body.style.margin = 0;"+
				"var infowindow;\r\n" + 
				"var map;\r\n" + 		
				"var maps = document.getElementById('map');\r\n" + 
  	            "maps.style.width = '100%';\r\n" + 
  	            "maps.style.height = '100%';" +     
  	            "var seoul = {lat:"+ dstLat +", lng:"+ dstLng + "};\r\n" + 								//중심이 되는 위도, 경도 설정
  	            "function initMap() {\r\n" + 
  	            "	map = new google.maps.Map(document.getElementById('map'), {\r\n" + 
  	            "       zoom: 13," + 																	//GoogleMap 기준 줌 13 설정
  	            "       center: seoul"+																	//기본 중심 서울로 지정
  	            "   });\r\n"
  	            );
		markers = new String(
				"   var h = {lat: "+dstLat+", lng: "+ dstLng+"};\r\n" + 							//목적지 위도 경도  설정
		    	"   var marker = new google.maps.Marker({\r\n" + 
		    	"	animation: google.maps.Animation.DROP," +
				"   	position: h,\r\n" + 														//위도 경도 위치 설정
				"       map: map\r\n" + 															//맵에 붙여넣기
				"   });\n"
				);
		others = new String(
				"}"
				);
		src = new String("function includeJs(jsFilePath)\r\n" + 									//googleAPI의 javascript 코드를 원격으로 사용할수 있는 권한 설정
                "{ var js = document.createElement(\"script\")\r\n" + 
                "\r\n" + 
                "js.type = \"text/javascript\";\r\n" + 
                "js.src = jsFilePath;\r\n" + 
                "\r\n" + 
                "document.body.appendChild(js);\r\n" + 
                "}\r\n" + 
                "includeJs(\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDu5LHktof5c2e81HbeiMy3tm4vIfkEs9c&libraries=places&callback=initMap\");\r\n");
	}
	
	public void setAnalysisMain(ArrayList<AccidentCase> temp)
	{
		
		offset = new String(		"document.body.style.padding = 0;"+								//지도 초기화
				"document.body.style.margin = 0;"+
				"var infowindow;\r\n" + 
				"var map;\r\n" + 		
				"var maps = document.getElementById('map');\r\n" + 
  	            "maps.style.width = '100%';\r\n" + 
  	            "maps.style.height = '100%';" +     
  	            "var seoul = {lat: 37.566294 , lng: 126.977423};\r\n" + 
  	            "function initMap() {\r\n" +
  	            "	map = new google.maps.Map(document.getElementById('map'), {\r\n" + 
  	            "       zoom: 8," + 																//줌: 8 원거리 설정
  	            "       center: seoul"+																//중심 서울로 지정
  	            "   });\r\n" +
	  	        "var icon1 = {\r\n" +                                           					//사고 발생 지점 아이콘
	            "    url:'https://cdn.pixabay.com/photo/2013/07/13/11/42/map-158493_960_720.png',\r\n" + 
	            "    scaledSize: new google.maps.Size(13, 16) \r\n" + 								//마커 사이즈 지정
	            "};"
  	            );
		
		for(int i =0; i<temp.size(); i++)
		{
			String dstLat = Double.toString(temp.get(i).getLatitude());
			String dstLng = Double.toString(temp.get(i).getLongitude());
			System.out.println(dstLat + dstLng);
			markers +=
					"   var h = {lat: "+dstLat+", lng: "+ dstLng+"};\r\n" + 							//찍은 마커 위도 경도  설정
			    	"   var marker = new google.maps.Marker({\r\n" + 
			    	"	animation: google.maps.Animation.DROP," +
					"   	position: h,\r\n" + 
			    	"		icon: icon1,\r\n"+															//저장된 아이콘 사용
					"       map: map\r\n" + 															//맵에 붙여넣기
					"   });"+ 
					"\n";
		}
		
		others = new String("}");
		src = new String("function includeJs(jsFilePath)\r\n" + 																//googleAPI의 javascript 코드를 원격으로 사용할수 있는 권한 설정
                "{ var js = document.createElement(\"script\")\r\n" + 
                "\r\n" + 
                "js.type = \"text/javascript\";\r\n" + 
                "js.src = jsFilePath;\r\n" + 
                "\r\n" + 
                "document.body.appendChild(js);\r\n" + 
                "}\r\n" + 
                "includeJs(\"https://maps.googleapis.com/maps/api/js?"
                + "key=AIzaSyDu5LHktof5c2e81HbeiMy3tm4vIfkEs"
                + "9c&libraries=places&callback=initMap\");\r\n");

	}

}
