package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConn {
	public static Connection getConnection() {
		try {
			// DB 연결 커넥션 반환
			String server = "jdbc:mysql://akotis.kr:3306/nextTrip";
			String id = "nextTriproot";
			String pw = "P@ssw0rd!";
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(server, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
