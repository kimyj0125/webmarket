package mvc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	public static Connection getConnection() 
			throws SQLException, ClassNotFoundException {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "shop";
		String password = "1234";

		Class.forName("oracle.jdbc.OracleDriver"); // jdbc드라이버 로딩
		conn = DriverManager.getConnection(url, user, password);

		return conn;
	}
	
	public static void close(ResultSet rs, Statement stmt, Connection conn) 
			throws SQLException {
		if(rs != null)
			rs.close();
		if(stmt != null)
			stmt.close();
		if(conn !=null)
			conn.close();
	}
	public static void close(Statement stmt, Connection conn) 
			throws SQLException {
		if(stmt != null)
			stmt.close();
		if(conn !=null)
			conn.close();
	}	
}//DBConnection
