import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JTable;
public class AccidentCaseDAO {

	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/javadb";//mysql이 연결 안되는 관계로 강의자료값을 넣었습니다.
	Connection conn;

	PreparedStatement pstmt;
	ResultSet rs;

	String sql;
	ArrayList<AccidentCase> datas;


	//DB연결
	void connectDB(){
		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(jdbcUrl,"javabook","1234");
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

	int entireColNum() {
		int colCnt=0;
		connectDB();
		sql = "select * from accidentcase";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			colCnt = md.getColumnCount();
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
			rs.last();
			rowCnt = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return rowCnt;
	}

	boolean insertCase(AccidentCase info) {
		connectDB();
		int chk;
		boolean flag=false; 
		sql = "insert into accidentcase "
				+ "(province,town,year,month,day,policeno,carno,casulity,dead,injured,actype,latitude,longitude) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, info.getProvince());
			pstmt.setString(2, info.getTown());
			pstmt.setString(3, info.getYear());
			pstmt.setString(4, info.getMonth());
			pstmt.setString(5, info.getDay());
			pstmt.setString(6, info.getPoliceno());
			pstmt.setString(7, info.getCarno());
			pstmt.setInt(8, info.getCasulity());
			pstmt.setInt(9, info.getDead());
			pstmt.setInt(10, info.getInjured());
			pstmt.setString(11, info.getActype());
			pstmt.setFloat(12, info.getLatitude());
			pstmt.setFloat(13, info.getLongitude());
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

	boolean deleteCase(int cscode) {
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

	AccidentCase getCase(int cscode) {
		connectDB();
		AccidentCase tmp = new AccidentCase();
		sql = "select * from accidentcase where cscode = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			tmp.setCscode(rs.getInt("cscode"));
			tmp.setProvince(rs.getString("province"));
			tmp.setTown(rs.getString("town"));
			tmp.setYear(rs.getString("year"));
			tmp.setMonth(rs.getString("month"));
			tmp.setDay(rs.getString("day"));
			tmp.setCarno(rs.getString("carno"));
			tmp.setPoliceno(rs.getString("policeno"));
			tmp.setCasulity(rs.getInt("casulity"));
			tmp.setDead(rs.getInt("dead"));
			tmp.setInjured(rs.getInt("injured"));
			tmp.setActype(rs.getString("actype"));
			tmp.setLatitude(rs.getFloat("latitude"));
			tmp.setLongitude(rs.getFloat("longitude"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeDB();
		return tmp;
	}

	Police getPolice(int cscode) {
		connectDB();
		Police pTmp = new Police();
		AccidentCase ac = new AccidentCase();
		String nTmp;
		sql = "select policeno from where cscode = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			nTmp = rs.getString("policeno");
			sql = "select * from police where policeno = ?";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			pTmp.setPoliceno(rs.getString("policeno"));
			pTmp.setPolname(rs.getString("polname"));
			pTmp.setRank(rs.getString("rank"));
			pTmp.setDepart(rs.getString("depart"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeDB();
		return pTmp;
	}


	ArrayList<AccidentCase> getAll() {
		AccidentCase a;
		int col=0,row=0;

		datas = new ArrayList<AccidentCase>();
		connectDB();
		sql = "select * from accidentcase";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			col = md.getColumnCount();
			rs.last();
			row = rs.getRow(); rs.beforeFirst();

			while(rs.next()) {
				a = new AccidentCase();
				a.setCscode(rs.getInt("cscode"));
				a.setProvince(rs.getString("province"));
				a.setTown(rs.getString("town"));
				a.setYear(rs.getString("year"));
				a.setMonth(rs.getString("month"));
				a.setDay(rs.getString("day"));
				a.setCarno(rs.getString("carno"));
				a.setPoliceno(rs.getString("policeno"));
				a.setCasulity(rs.getInt("casulity"));
				a.setDead(rs.getInt("dead"));
				a.setInjured(rs.getInt("injured"));
				a.setActype(rs.getString("actype"));
				a.setLatitude(rs.getFloat("latitude"));
				a.setLongitude(rs.getFloat("longitude"));
				datas.add(a);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeDB();
		return datas;
	}




}
