import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class ParsingAccidentData {

		ParsingAccidentData()
		{
			// JSON 데이터가 저장된 url 주소를 저장하는 String 배열
			String url[] = new String[9];
			
			//2016 년도
			url[0] = new String();  //서울
			url[0] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2016&siDo=1100&guGun=";
			url[1] = new String();  //경기도
			url[1] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2016&siDo=1300&guGun=";
			url[2] = new String();  //인천광역시
			url[2] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2016&siDo=2300&guGun=";
	
			//2015
			url[3] = new String();  //서울
			url[3] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2015&siDo=1100&guGun=";
			url[4] = new String();  //경기도
			url[4] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2015&siDo=1300&guGun=";
			url[5] = new String();  //인천광역시
			url[5] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2015&siDo=2300&guGun=";
	
			//2014
			url[6] = new String();   //서울
			url[6] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2014&siDo=1100&guGun=";
			url[7] = new String();   //경기도
			url[7] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2014&siDo=1300&guGun=";
			url[8] = new String();   //인천광역시
			url[8] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear=2014&siDo=2300&guGun=";
	
			
			// Parsing을 진행할 Parser 생성
			JSONParser parser = new JSONParser(); 
			
			// 특정 웹 주소를 가리키는 URL 객체
			URL postUrl;
			
			// 출력 스트림 생성
			InputStreamReader isr;
			
			//JSON 형식 데이터를 저장하는 객체
			JSONObject jsonObj;
			//JSON 형식의 데이터를 문자열을 저장하는 String
			String jsonStr;
			JSONArray jsonArr;
			
			AccidentCaseDAO dao = new AccidentCaseDAO();
			
			dao.deleteAllCase();
			
			try
			{
				for(int i =0 ; i<9; i++)
				{
					// url[i] 주소를 갖는 웹을 가리키는 postUrl
					postUrl = new URL(url[i]);
					
					//postUrl로 부터 "UTF-8" 형식의 문자가져옴
					isr = new InputStreamReader(postUrl.openConnection().getInputStream(), "UTF-8");
					
					//키 searchResult에 해당하는 값 jsonObject에 저장
					jsonObj = (JSONObject)((JSONObject)JSONValue.parseWithException(isr)).get("searchResult");
					//키 accidentDeath에 해당하는 문자열을 jsonStr에 저장
					jsonStr = jsonObj.get("accidentDeath").toString();
					System.out.println(jsonObj.get("accidentDeath").toString());
		
			 		//파싱한 결과러 JSONobject 여러개를 JSONarray에 저장
			 		jsonArr = (JSONArray)parser.parse(jsonStr);
	
			 		//사고사례를 저장할 객체 생성함
			 		AccidentCase accCase = new AccidentCase();
			 		//JSON 배열에서 하나를 꺼내 저장할  JSON 객체
			 		
	 				JSONObject jObj;
			 		
			 		for (int j = 0; j < jsonArr.size(); j++) 
			 		{
			 				jObj = (JSONObject) jsonArr.get(j);
			                
			 				accCase.setProvince(funcProvince((String) jObj.get("cd_003_lv1")));
			 				accCase.setTown(funcTown((String)jObj.get("cd_003"))); 
			 				
			 				String date = (String)jObj.get("dt_006");
			 				accCase.setYear(date.substring(0,4));
			 				accCase.setMonth(date.substring(4,6));
			 				accCase.setDay(date.substring(6,8));
			 				
			 				accCase.setPoliceno(dao.getPolCode((String)jObj.get("cd_003"))); 
			 				
			 				accCase.setDead(((Long)jObj.get("no_010")).intValue());
			 				accCase.setInjured(((Long)jObj.get("injpsn_co")).intValue());
			 				accCase.setCasulity();
			 				
			 				accCase.setActype(funcAcctype((String) jObj.get("cd_014_lv1")));
			 				accCase.setLatitude(Double.parseDouble((String)jObj.get("grd_la")));
			 				accCase.setLongitude(Double.parseDouble((String)jObj.get("grd_lo")));
			 				
			 				dao.insertCase(accCase);
			 				
			 				System.out.println("시도:"+accCase.getProvince());
			 				System.out.println("군구:"+accCase.getTown());
			 				System.out.println("year:" +accCase.getYear());
			 				System.out.println("month:" +accCase.getMonth());
			 				System.out.println("day:" +accCase.getDay());
			 				System.out.println("경찰번호:" +accCase.getPoliceno());
			 				System.out.println("사상자수:" +accCase.getCasulity());
			 				System.out.println("사망자수:" +accCase.getDead());
			 				System.out.println("부상자수:" +accCase.getInjured());
			 				System.out.println("사고유형:" + accCase.getActype());
			                System.out.println("경도:" + accCase.getLongitude());
			                System.out.println("위도:" + accCase.getLatitude());
			   
			 			}
				}
			
	
	 		} catch (Exception e) {
	
	 			e.printStackTrace();
	
	 		}
	
	 	}
	
	private String funcProvince(String code)
	{
		String str[] = {"서울특별시","경기도","인천광역시"};
		
		if(code.equals("1100"))
			return str[0];
		else if(code.equals("1300"))
			return str[1];
		else if(code.equals("2300"))
			return str[2];
		
		return "NULL";
	}
	
	private String funcTown(String code)
	{
		String seoul[] = {"강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구"
				,"서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"};
		
		String kyenggi[] = {"가평군","강화군","고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주군","남양주시","동두천시","부천시","성남시","송탄시","수원시","시흥시","안산시","안성시","안양시","양주시","양펑군","여주시","연천군","오산시","옹진군","용인시","의왕시","의정부시"
				,"이천시","파주시","평택군","평택시","포천시","하남시","화성시"};
		
		String incheon[] = {"강화군","계양구","남구","남동구","동구","부평구","서구","연수구","옹진군","중구"	};
		
		if(code.equals("1116"))
			return seoul[0];
		else if(code.equals("1117"))
			return seoul[1];
		else if(code.equals("1124"))
			return seoul[2];
		else if(code.equals("1111"))
			return seoul[3];
		else if(code.equals("1115"))
			return seoul[4];
		else if(code.equals("1123"))
			return seoul[5];
		else if(code.equals("1112"))
			return seoul[6];
		else if(code.equals("1125"))
			return seoul[7];
		else if(code.equals("1122"))
			return seoul[8];
		else if(code.equals("1107"))
			return seoul[9];
		else if(code.equals("1105"))
			return seoul[10];
		else if(code.equals("1114"))
			return seoul[11];
		else if(code.equals("1110"))
			return seoul[12];
		else if(code.equals("1109"))
			return seoul[13];
		else if(code.equals("1119"))
			return seoul[14];
		else if(code.equals("1104"))
			return seoul[15];
		else if(code.equals("1106"))
			return seoul[16];
		else if(code.equals("1118"))
			return seoul[17];
		else if(code.equals("1120"))
			return seoul[18];
		else if(code.equals("1113"))
			return seoul[19];
		else if(code.equals("1103"))
			return seoul[20];
		else if(code.equals("1108"))
			return seoul[21];
		else if(code.equals("1101"))
			return seoul[22];
		else if(code.equals("1102"))
			return seoul[23];
		else if(code.equals("1121"))
			return seoul[24];
		
		else if(code.equals("1322"))
			return kyenggi[0];
		else if(code.equals("1328"))
			return kyenggi[1];
		else if(code.equals("1318"))
			return kyenggi[2];
		else if(code.equals("1332"))
			return kyenggi[3];
		else if(code.equals("1309"))
			return kyenggi[4];
		else if(code.equals("1319"))
			return kyenggi[5];
		else if(code.equals("1310"))
			return kyenggi[6];
		else if(code.equals("1333"))
			return kyenggi[7];
		else if(code.equals("1327"))
			return kyenggi[8];
		else if(code.equals("1312"))
			return kyenggi[9];
		else if(code.equals("1334"))
			return kyenggi[10];
		else if(code.equals("1330"))
			return kyenggi[11];
		else if(code.equals("1306"))
			return kyenggi[12];
		else if(code.equals("1303"))
			return kyenggi[13];
		else if(code.equals("1331"))
			return kyenggi[14];
		else if(code.equals("1302"))
			return kyenggi[15];
		else if(code.equals("1316"))
			return kyenggi[16];
		else if(code.equals("1307"))
			return kyenggi[17];
		else if(code.equals("1326"))
			return kyenggi[18];
		else if(code.equals("1305"))
			return kyenggi[19];
		else if(code.equals("1311"))
			return kyenggi[20];
		else if(code.equals("1323"))
			return kyenggi[21];
		else if(code.equals("1313"))
			return kyenggi[22];
		else if(code.equals("1320"))
			return kyenggi[23];
		else if(code.equals("1335"))
			return kyenggi[24];
		else if(code.equals("1329"))
			return kyenggi[25];
		else if(code.equals("1325"))
			return kyenggi[26];
		else if(code.equals("1336"))
			return kyenggi[27];
		else if(code.equals("1304"))
			return kyenggi[28];
		else if(code.equals("1324"))
			return kyenggi[29];
		else if(code.equals("1317"))
			return kyenggi[30];
		else if(code.equals("1314"))
			return kyenggi[31];
		else if(code.equals("1308"))
			return kyenggi[32];
		else if(code.equals("1321"))
			return kyenggi[33];
		else if(code.equals("1337"))
			return kyenggi[34];
		else if(code.equals("1315"))
			return kyenggi[35];
		
		
		else if(code.equals("2309"))
			return incheon[0];
		else if(code.equals("2308"))
			return incheon[1];
		else if(code.equals("2303"))
			return incheon[2];
		else if(code.equals("2305"))
			return incheon[3];
		else if(code.equals("2302"))
			return incheon[4];
		else if(code.equals("2304"))
			return incheon[5];
		else if(code.equals("2306"))
			return incheon[6];
		else if(code.equals("2307"))
			return incheon[7];
		else if(code.equals("2310"))
			return incheon[8];
		else if(code.equals("2301"))
			return incheon[9];
		
		return "NULL";
	}
	
	private String funcAcctype(String code)
	{
		String acctype[] = {"차대사람","차대차","차량단독"};
		
		if(code.equals("01"))
			return acctype[0];
		else if(code.equals("02"))
			return acctype[1];
		else if(code.equals("03"))
			return acctype[2];
		
		return "NULL";
	}
		
		


}
