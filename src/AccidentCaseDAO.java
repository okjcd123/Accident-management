/**
 * AccidentCaseDAO.class
 * @author 김준혁, 김준혁, 문희호
 * 
 * 최종 작성일: 2017년 12월 23일
 * 최종 수정일: 2018년 1월 5일
 */
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
public class AccidentCaseDAO {
	
	String DBid;     // MySQL username
	String DBpw;     // MySQL password
	
	String jdbcDriver = "com.mysql.jdbc.Driver";      // MySQL jdbc 드라이버
	String jdbcUrl = "jdbc:mysql://localhost/javadb"; // MySQL javadb schema    

	Connection conn;
	String sql;   		    	// SQL 문
	PreparedStatement pstmt;
	ResultSet rs;  				// SQL문 실행 결과로 반환되는 튜플을 저장

	
	ArrayList<AccidentCase> datas;  // AccidentCase 클래스 반환하는 ArrayList

	// 인스턴스를 생성하여 AppManager 클래스에 저장하는 메소드
	public AccidentCaseDAO()
	{
		AppManager.CreateInstance().setAccidentCaseDAO(this);
	}
	
	// DB(MySQL)의 username과 password가 일치하는지 확인하는 메소드
	boolean connectTest(String id, String pw)
	{
		try {
			this.DBid= id;
			this.DBpw = pw;
			
			Class.forName(jdbcDriver);
			DriverManager.getConnection(jdbcUrl,DBid,DBpw);
		
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// DB와 연결하는 메소드
	void connectDB(){
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(jdbcUrl,DBid,DBpw);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//DB와 연결을 종료하는 메소드
	void closeDB(){
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 새로 추가된 사고 번호를 반환 하는 메소드(현재 DB에 존재하는 가장 큰 사고번호 반환)
	int getNewCaseCode() {
		
		connectDB();   //DB 연결
		int newCsCode = 0;
		
		sql = "select MAX(cscode) from accidentcase";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				newCsCode = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeDB();    //DB 연결 닫기
		return newCsCode;
	}
	
	// 사고 번호를 전달 받아 해당하는 AccidentCase를 반환하는 메소드 
	AccidentCase getCase(int cscode) 
	{
		connectDB();    //DB 연결
		AccidentCase accCase = new AccidentCase();
		sql = "select * from accidentcase where cscode = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cscode);         
			rs = pstmt.executeQuery();       

			if(rs.next())
			{
				accCase.setCscode(cscode);
				accCase.setProvince(rs.getString("province"));
				accCase.setTown(rs.getString("town"));
				accCase.setYear(rs.getString("year"));
				accCase.setMonth(rs.getString("month"));
				accCase.setDay(rs.getString("day"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
			}
			
			rs.close(); // resultSet 닫기
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeDB();   //DB 연결 닫기
		return accCase;
	}
	
	// 시/도, 군/구, 년, 월을 전달 받아 조건에 맞는 ArrayList를 반환하는 메소드 
	ArrayList<AccidentCase> searchCase(String province, String town, String year, String month)
	{
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		connectDB(); //DB 연결
		
		sql = "select * from accidentcase where (province = ? and town = ? and year = ? and month = ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, province);
			pstmt.setString(2, town);
			pstmt.setString(3, year);
			pstmt.setString(4, month);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				accCase = new AccidentCase();
				accCase.setCscode(rs.getInt("cscode"));
				accCase.setProvince(rs.getString("province"));
				accCase.setTown(rs.getString("town"));
				accCase.setYear(rs.getString("year"));
				accCase.setMonth(rs.getString("month"));
				accCase.setDay(rs.getString("day"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
				
				datas.add(accCase);
			}
			
			rs.close();   //resultSet 닫기

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();    // DB 연결 닫기
		return datas;
	}	

	// 시/도, 군/구, 년을 전달 받아 조건에 맞는 ArrayList를 반환하는 메소드 
	ArrayList<AccidentCase> searchCaseLocaYear(String province, String town, String year)
	{
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		connectDB();   //DB 연결
		sql = "select * from accidentcase where (province = ? and town = ? and year = ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, province);
			pstmt.setString(2, town);
			pstmt.setString(3, year);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				accCase = new AccidentCase();
				accCase.setCscode(rs.getInt("cscode"));
				accCase.setProvince(rs.getString("province"));
				accCase.setTown(rs.getString("town"));
				accCase.setYear(rs.getString("year"));
				accCase.setMonth(rs.getString("month"));
				accCase.setDay(rs.getString("day"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
				
				datas.add(accCase);
			}
			
			rs.close();   // resultSet 닫음

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();        //DB 연결 닫기
		return datas;
	}
	
	// 시/도, 군/구, 년, 월을 전달 받아 조건에 맞는 ArrayList를 반환하는 메소드 
	ArrayList<AccidentCase> searchCaseLocaMonth(String province, String town, String month)
	{
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		connectDB(); //DB 연결 
		sql = "select * from accidentcase where (province = ? and town = ? and month = ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, province);
			pstmt.setString(2, town);
			pstmt.setString(3, month);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				accCase = new AccidentCase();
				accCase.setCscode(rs.getInt("cscode"));
				accCase.setProvince(rs.getString("province"));
				accCase.setTown(rs.getString("town"));
				accCase.setYear(rs.getString("year"));
				accCase.setMonth(rs.getString("month"));
				accCase.setDay(rs.getString("day"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
				
				datas.add(accCase);
			}
			
			rs.close();           // resultSet 닫음

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();                // DB 연결 종료  
		return datas;
	}
	
	// 시/도, 군/구을 전달 받아 조건에 맞는 ArrayList를 반환하는 메소드 
	ArrayList<AccidentCase> searchCaseLoca(String province, String town)
	{
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>(); 
		
		connectDB();   // DB 연결
		sql = "select * from accidentcase where (province = ? and town = ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);   
			pstmt.setString(1, province);         // 첫 번째 물음표 위치에 province 지정 
			pstmt.setString(2, town);             // 두 번째 물음표 위치에 town 지정
			rs = pstmt.executeQuery();            
	
			while(rs.next()) {
				// AccidentCase객체 생성
				accCase = new AccidentCase();   
				// 실행 결과로 반환된 행을 AccidentCase 객체의 변수에 지정 
				accCase.setCscode(rs.getInt("cscode"));              
				accCase.setProvince(rs.getString("province"));       
				accCase.setTown(rs.getString("town"));
				accCase.setYear(rs.getString("year"));
				accCase.setMonth(rs.getString("month"));
				accCase.setDay(rs.getString("day"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
				// ArrayList에 AccidentCase 추가
				datas.add(accCase);
			}
			
			rs.close(); //resultSet 닫기

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();   // DB 연결 닫음 
		return datas;
	}	
	
	//년, 월을 전달 받아 조건에 맞는 ArrayList를 반환하는 메소드 
	ArrayList<AccidentCase> searchCaseTime(String year, String month)
	   {
	      AccidentCase accCase;
	      datas = new ArrayList<AccidentCase>();
	      connectDB();    // DB 연결 
	      
	         sql = "select * from accidentcase where (year = ? and month = ?)";
	         try {
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1,year);
	            pstmt.setString(2,month);
	            rs = pstmt.executeQuery();
	            
	            while(rs.next()) {
	               accCase = new AccidentCase();
	               accCase.setCscode(rs.getInt("cscode"));
	               accCase.setProvince(rs.getString("province"));
	               accCase.setTown(rs.getString("town"));
	               accCase.setYear(rs.getString("year"));
	               accCase.setMonth(rs.getString("month"));
	               accCase.setDay(rs.getString("day"));
	               accCase.setDead(rs.getInt("dead"));
	               accCase.setInjured(rs.getInt("injured"));
	               accCase.setCasulity();
	               accCase.setActype(rs.getString("actype"));
	               accCase.setLatitude(rs.getDouble("latitude"));
	               accCase.setLongitude(rs.getDouble("longitude"));
	               
	               datas.add(accCase);
	            }
	            
	            rs.close();   // resultSet 닫음 
	         } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	         
	         closeDB(); // DB 연결 닫음
	         return datas;  
	      }
		
	//년을 전달 받아 조건에 맞는 AccidentCase를 ArrayList에 담아 반환하는 메소드 	
	ArrayList<AccidentCase> searchCaseTime(String year)
	{
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		connectDB();    // DB 연결 
		
	      sql = "select * from accidentcase where year = ?";
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1,year);
	         rs = pstmt.executeQuery();
	         
	         while(rs.next())
	         {
					accCase = new AccidentCase();
					accCase.setCscode(rs.getInt("cscode"));
					accCase.setProvince(rs.getString("province"));
					accCase.setTown(rs.getString("town"));
					accCase.setYear(rs.getString("year"));
					accCase.setMonth(rs.getString("month"));
					accCase.setDay(rs.getString("day"));
					accCase.setDead(rs.getInt("dead"));
					accCase.setInjured(rs.getInt("injured"));
					accCase.setCasulity();
					accCase.setActype(rs.getString("actype"));
					accCase.setLatitude(rs.getDouble("latitude"));
					accCase.setLongitude(rs.getDouble("longitude"));
					
					datas.add(accCase);
	         }
	         
	     	rs.close();  //resultSet 닫음
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      closeDB(); // DB 연결 닫음
	      return datas;
	   }

	//월을 전달 받아 조건에 맞는 AccidentCase를 ArrayList에 담아 반환하는 메소드 	
	ArrayList<AccidentCase> searchCaseMonthTime(String month)
	   {
	      AccidentCase accCase;
	      datas = new ArrayList<AccidentCase>();
	      connectDB();    // DB 연결 
	      
	         sql = "select * from accidentcase where month = ?";
	         try {
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1,month);
	            rs = pstmt.executeQuery();
	            
	            while(rs.next()) {
	               accCase = new AccidentCase();
	               accCase.setCscode(rs.getInt("cscode"));
	               accCase.setProvince(rs.getString("province"));
	               accCase.setTown(rs.getString("town"));
	               accCase.setYear(rs.getString("year"));
	               accCase.setMonth(rs.getString("month"));
	               accCase.setDay(rs.getString("day"));
	               accCase.setDead(rs.getInt("dead"));
	               accCase.setInjured(rs.getInt("injured"));
	               accCase.setCasulity();
	               accCase.setActype(rs.getString("actype"));
	               accCase.setLatitude(rs.getDouble("latitude"));
	               accCase.setLongitude(rs.getDouble("longitude"));
	               
	               datas.add(accCase);
	            }
	            rs.close();  // resultSet 닫음
	         } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	         
	         closeDB();   // DB 연결 닫음
	         return datas;  
	      }

	// DB에 존재하는 모든 사고 사례를 ArrayList에 담아 반환하는 메소드 
	ArrayList<AccidentCase> getAll() {
		
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		
		connectDB();  // DB 연결
		
		sql = "select * from accidentcase";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				accCase = new AccidentCase();
				accCase.setCscode(rs.getInt("cscode"));
				accCase.setProvince(rs.getString("province"));
				accCase.setTown(rs.getString("town"));
				accCase.setYear(rs.getString("year"));
				accCase.setMonth(rs.getString("month"));
				accCase.setDay(rs.getString("day"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
				
				datas.add(accCase);
			}
			
			rs.close();  // resultSet 닫음

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();   // DB 연결 닫음
		return datas;
	}
	 
	//AccidentCase를 전달받아 DB에 삽입하는 메소드  	
	 boolean insertCase(AccidentCase accCase) 
		{
			connectDB();     // DB 연결
			int chk =0;
			boolean flag=false; 
			sql = "INSERT "+
				  "INTO accidentcase(province,town,year,month,day,dead,injured,actype,latitude,longitude) "+
				  "VALUES(?,?,?,?,?,?,?,?,?,?)";
	
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, accCase.getProvince());
				pstmt.setString(2, accCase.getTown());
				pstmt.setString(3, accCase.getYear());
				pstmt.setString(4, accCase.getMonth());
				pstmt.setString(5, accCase.getDay());
				pstmt.setInt(6, accCase.getDead());
				pstmt.setInt(7, accCase.getInjured());
				pstmt.setString(8, accCase.getActype());
				pstmt.setDouble(9, accCase.getLatitude());
				pstmt.setDouble(10, accCase.getLongitude());
				
				chk = pstmt.executeUpdate();
				
				if(chk >0)   // 변화시킨 행의 수가 존재할 때 
					flag = true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			closeDB();    // DB 연결 종료
			return flag;
		}
		
		//AccidentCase를 전달받아 DB에 존재하는 정보를 수정하는 메소드	
		boolean updateCase(AccidentCase accCase) 
		{
			connectDB();   // DB 연결
			int chk;
			boolean flag = false; 
			
			sql = "UPDATE accidentcase " + 
				  "SET province = ? , town = ?, year = ? , month = ?, day = ?, dead = ?, injured =? , actype = ?, latitude = ?, longitude = ? " + 
				  "WHERE cscode = ? ";
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, accCase.getProvince());
				pstmt.setString(2, accCase.getTown());
				pstmt.setString(3, accCase.getYear());
				pstmt.setString(4, accCase.getMonth());
				pstmt.setString(5, accCase.getDay());
				pstmt.setInt(6, accCase.getDead());
				pstmt.setInt(7, accCase.getInjured());
				pstmt.setString(8, accCase.getActype());
				pstmt.setDouble(9, accCase.getLatitude());
				pstmt.setDouble(10, accCase.getLongitude());
				pstmt.setInt(11, accCase.getCscode());
				
				chk = pstmt.executeUpdate();
				
				if(chk>0)   // 변화시킨 행의 수가 존재할 때 
					flag = true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			closeDB();     // DB 종료
			return flag;
		}

		// cscode를 전달받아 해당하는 데이터 삭제하는 메소드
		boolean deleteCase(int cscode)
		{
			connectDB();    // DB 연결
			sql = "delete from accidentcase where cscode = ?";
			int chk;
			boolean flag = false;
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, cscode);   			// 첫 번째 물음표에 cscode 지정
				chk = pstmt.executeUpdate();        // pstmt 실행
				
				if(chk>0) // 변화 시킨 행의 수가 존재할 때
					flag = true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeDB();      // DB 종료
			return flag;
		}
		
		// DB에 존재하는 모든 데이터를 삭제하는 메소드
		void deleteAllCase()
		{
			connectDB();        // DB 연결
			sql = "TRUNCATE accidentcase;";

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();        // pstmt 실행
				
			} catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			closeDB();         // DB 종료
		}
		
		//년도를 입력받아 해당하는 데이터 파싱하는 메소드
		boolean ParsingAccidentData(String year)
		{
			// JSON 데이터가 저장된 url 주소를 저장하는 String 배열
			String url[] = new String[3];
			
			//서울
			url[0] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear="+year+"&siDo=1100&guGun=";
			//경기
			url[1] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear="+year+"&siDo=1300&guGun=";
			//인천
			url[2] = "http://apis.data.go.kr/B552061/trafficAccidentDeath/getRestTrafficAccidentDeath?servicekey=2%2BbS76NE8TFgiPqTxDyXneu97RYUJJxFo3c1K5FHwD5tja3W8etlkdp6jXOr9zHl6xAZJB9LUaLrcEudogq5iQ%3D%3D&searchYear="+year+"&siDo=2300&guGun=";

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
				
			boolean flag = false;
			try
			{
				for(int i =0 ; i<3; i++)
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
			                
			 				accCase.setProvince(findProvince((String) jObj.get("cd_003_lv1")));
			 				accCase.setTown(findTown((String)jObj.get("cd_003"))); 
			 				
			 				String date = (String)jObj.get("dt_006");
			 				accCase.setYear(date.substring(0,4));
			 				accCase.setMonth(date.substring(4,6));
			 				accCase.setDay(date.substring(6,8));
			 				
			 				accCase.setDead(((Long)jObj.get("no_010")).intValue());
			 				accCase.setInjured(((Long)jObj.get("injpsn_co")).intValue());
			 				accCase.setCasulity();
			 				
			 				accCase.setActype(findAcctype((String) jObj.get("cd_014_lv1")));
			 				accCase.setLatitude(Double.parseDouble((String)jObj.get("grd_la")));
			 				accCase.setLongitude(Double.parseDouble((String)jObj.get("grd_lo")));
			 				
			 				insertCase(accCase);
			 				
			 				System.out.println("시도:"+accCase.getProvince());
			 				System.out.println("군구:"+accCase.getTown());
			 				System.out.println("year:" +accCase.getYear());
			 				System.out.println("month:" +accCase.getMonth());
			 				System.out.println("day:" +accCase.getDay());
			 				System.out.println("사상자수:" +accCase.getCasulity());
			 				System.out.println("사망자수:" +accCase.getDead());
			 				System.out.println("부상자수:" +accCase.getInjured());
			 				System.out.println("사고유형:" + accCase.getActype());
			                System.out.println("경도:" + accCase.getLongitude());
			                System.out.println("위도:" + accCase.getLatitude());
			   
			 			}
			 		
			 		flag = true;
				}
				} catch (Exception e) {
	
	 			e.printStackTrace();
	
	 		}
			
			return flag;
						
		}
		
		// 시/도 코드를 전달받아 해당하는 시/도 문자열을 반환하는 메소드
		private String findProvince(String code)
		{
			String str[] = {"서울특별시","경기도","인천광역시"};
			
			if(code.equals("1100"))
				return str[0];
			else if(code.equals("1300"))
				return str[1];
			else if(code.equals("2300"))
				return str[2];
			else
				return "NULL";
		}
		
		// 군/구 코드를 전달받아 해당하는 군/구 문자열을 반환하는 메소드 
		private String findTown(String code)
		{
			String seoul[] = {"종로구","중구","용상구","성동구","동대문구","성북구","도봉구","은평구","서대문구","마포구","강서구","구로구","영등포구","동작구",
					"관악구","강남구","강동구","송파구","서초구","양천구","중랑구","노원구","광진구","강북구","금천구"};
			
			String kyenggi[] = {"수원시","성남시", "의정부시","안양시","부천시","안산시","평택시",	"광명시",	"구리시", "양주시",	"남양주군","여주시","평택군","화성시","시흥시","파주시","고양시","광주시",
					"연천군","포천시","가평군","양평군","이천시","용인시","안성시","김포시","강화군","옹진군","동두천시","송탄시","과천시","군포시","남양주시","오산시","의왕시","하남시"};

			String incheon[] = {"중구","동구","남구","부평구","남동구","서구","연수구","계양구","강화군","옹진군"	};
		
			int codeInt = Integer.parseInt(code);
			
			if(codeInt >1100 && codeInt <1200 )
			{
				return seoul[codeInt-1101];
			}
			else if(codeInt >1300 && codeInt <1400)
			{
				return kyenggi[codeInt-1302];
			}
			else if(codeInt >2300 && codeInt <2400)
			{
				return incheon[codeInt-2301];
			}
			else
				return "NULL";
			
		}
		
		// 차량 사고 타입 코드를 전달받아 해당하는 사고타입 문자열 반환하는 메소드
		private String findAcctype(String code)
		{
			String acctype[] = {"차대사람","차대차","차량단독"};
			
			if(code.equals("01"))
				return acctype[0];
			else if(code.equals("02"))
				return acctype[1];
			else if(code.equals("03"))
				return acctype[2];
			else 
				return "NULL";
		}

}
