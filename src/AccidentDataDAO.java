import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JTable;
public class AccidentDataDAO {

	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/javadb";//mysql이 연결 안되는 관계로 강의자료값을 넣었습니다.
	Connection conn;

	PreparedStatement pstmt;
	ResultSet rs;

	String sql;
	ArrayList<AccidentData> datas;

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


	ArrayList<AccidentData> getAll() {
		AccidentData a;
		int col=0,row=0;

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
				a = new AccidentData();
				a.setCscode(rs.getInt("cscode"));
				a.setProvince(rs.getString("province"));
				a.setTown(rs.getString("town"));
				a.setDate(rs.getDate("date"));
				a.setCarno(rs.getString("carno"));
				a.setPolno(rs.getString("polno"));
				a.setCasualty(rs.getInt("casualty"));
				a.setDead(rs.getInt("dead"));
				a.setInjured(rs.getInt("injured"));
				a.setType(rs.getString("type"));
				a.setLatitude(rs.getDouble("latitude"));
				a.setLongitude(rs.getDouble("longitude"));
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
