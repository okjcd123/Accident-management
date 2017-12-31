import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JTable;
public class AccidentCaseDAO {

	String DBid = "heeho";
	String DBpw = "1234";
	
//	String DBid;
//	String DBpw;
	
	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/javadb";//mysql이 연결 안되는 관계로 강의자료값을 넣었습니다.
	
	Connection conn;

	PreparedStatement pstmt;
	ResultSet rs;

	String sql;
	ArrayList<AccidentCase> datas;

	public AccidentCaseDAO()
	{
		AppManager.CreateInstance().setAccidentCaseDAO(this);
	}
	
	boolean connectTest(String id, String pw)
	{
		try {
			Class.forName(jdbcDriver);
			DriverManager.getConnection(jdbcUrl,id,pw);
			this.DBid= id;
			this.DBpw = pw;
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
	
	
	//DB연결
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
	
	//DB 닫기
	void closeDB(){
		try {
			pstmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int getNewCaseCode() {

		int newCode = 0;
		connectDB();
		sql = "select MAX(cscode) FROM accidentcase;";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				newCode = rs.getInt(1) + 1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return newCode;
	}
	
	int entireColNum() 
	{
		int colCnt=0;
		connectDB();
		sql = "select * from accidentcase";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				ResultSetMetaData md = rs.getMetaData();
				colCnt = md.getColumnCount();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return colCnt;
	}
	
	int entireRowNum() {
		int rowCnt=0;
		connectDB();
		sql = "select * from accidentcase";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				rs.last();
				rowCnt = rs.getRow();
				rs.beforeFirst();
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return rowCnt;
	}

	boolean insertCase(AccidentCase info) 
	{
		connectDB();
		int chk;
		boolean flag=false; 
		sql = "insert into accidentcase(province,town,year,month,day,policeno,dead,injured,actype,latitude,longitude) values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, info.getProvince());
			pstmt.setString(2, info.getTown());
			pstmt.setString(3, info.getYear());
			pstmt.setString(4, info.getMonth());
			pstmt.setString(5, info.getDay());
			pstmt.setString(6, info.getPoliceno());
			pstmt.setInt(7, info.getDead());
			pstmt.setInt(8, info.getInjured());
			pstmt.setString(9, info.getActype());
			pstmt.setDouble(10, info.getLatitude());
			pstmt.setDouble(11, info.getLongitude());
			
			chk = pstmt.executeUpdate();
			
			if(chk>0)
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return flag;
	}

	boolean deleteCase(int cscode)
	{
		connectDB();
		sql = "delete from accidentcase where cscode = ?";
		int chk;
		boolean flag = false;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cscode);
			chk = pstmt.executeUpdate();
			
			if(chk>0)
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeDB();
		return flag;
	}

	
	
	AccidentCase getCase(int cscode) 
	{
		connectDB();
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
				accCase.setPoliceno(rs.getString("policeno"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeDB();
		return accCase;
	}
	
	ArrayList<AccidentCase> searchCaseLoca(String province, String town)
	{
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		connectDB();
		sql = "select * from accidentcase where (province = ? and town = ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, province);
			pstmt.setString(2, town);
			rs = pstmt.executeQuery();
		

			while(rs.next()) {
				accCase = new AccidentCase();
				accCase.setCscode(rs.getInt("cscode"));
				accCase.setProvince(rs.getString("province"));
				accCase.setTown(rs.getString("town"));
				accCase.setYear(rs.getString("year"));
				accCase.setMonth(rs.getString("month"));
				accCase.setDay(rs.getString("day"));
				accCase.setPoliceno(rs.getString("policeno"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
				
				datas.add(accCase);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return datas;
	}	
	
	ArrayList<AccidentCase> searchCaseTime(String year, String month)
	{
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		connectDB();
		
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
					accCase.setPoliceno(rs.getString("policeno"));
					accCase.setDead(rs.getInt("dead"));
					accCase.setInjured(rs.getInt("injured"));
					accCase.setCasulity();
					accCase.setActype(rs.getString("actype"));
					accCase.setLatitude(rs.getDouble("latitude"));
					accCase.setLongitude(rs.getDouble("longitude"));
					
					datas.add(accCase);
	         }
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return datas;
	   }
	
	String getPolCode(String dpcode)
	{
		int num = 0;
		Vector<String> list = new Vector<String>();
		
		connectDB();
		sql = "select * from police where dpcode = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dpcode);
			rs = pstmt.executeQuery();
				
			while(rs.next())
			{
				list.add(rs.getString("policeno"));
			}
			
			num = list.size();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeDB();
		
		if(num>0)
			return list.get((int) (Math.random()*num));
		else
		{
			return "NULL";
		}
	}

	ArrayList<AccidentCase> getAll() {
		
		AccidentCase accCase;
		datas = new ArrayList<AccidentCase>();
		connectDB();
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
				accCase.setPoliceno(rs.getString("policeno"));
				accCase.setDead(rs.getInt("dead"));
				accCase.setInjured(rs.getInt("injured"));
				accCase.setCasulity();
				accCase.setActype(rs.getString("actype"));
				accCase.setLatitude(rs.getDouble("latitude"));
				accCase.setLongitude(rs.getDouble("longitude"));
				
				datas.add(accCase);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return datas;
	}




}
