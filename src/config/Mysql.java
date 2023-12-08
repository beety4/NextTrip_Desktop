package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Mysql {
	public static Connection getConnection() {
		try {
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
