package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Connecter {
	private static Connection con;
	public static final String URL = "127.0.0.1";
	public static final String dbName = "car-dealership";
	public static final String username = "root";
	public static final String password = "pinkflowers";

	// to connect to the data base
	public static void connectDB() throws ClassNotFoundException, SQLException {


		String dbURL = "jdbc:mysql://" + URL + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", username);
		p.setProperty("password", password);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(dbURL, p);

	}

	public Connection getCon() {
		return con;
	}

	public void ExecuteStatement(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();

		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");
		}

	}
}
