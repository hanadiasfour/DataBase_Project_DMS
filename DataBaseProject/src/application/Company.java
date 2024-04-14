package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Company {

	private String name, country;
	static ObservableList<String> nameList = FXCollections.observableArrayList();
	static ObservableList<Company> companyList = FXCollections.observableArrayList();

	private static Connecter con = Main.con;

	public Company(String name, String country) {
		this.name = name;
		this.country = country;

		if (!nameList.contains(name))
			nameList.add(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public static void getData() {
		// TODO Auto-generated method stub
		try {
			String SQL;

			con.connectDB();

			SQL = "select name,country from company order by name";
			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				companyList.add(new Company(rs.getString(1), rs.getString(2)));

				if (!nameList.contains(rs.getString(1)))

					nameList.add(rs.getString(1));

			}

			rs.close();
			stmt.close();
			con.getCon().close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ObservableList<Company> getSpecificData(String feature) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		StringBuilder queryBuilder = new StringBuilder("SELECT * FROM company WHERE ");

		// Check each condition and append to the query if the value is not null or
		// empty
		if (feature != null) {
			queryBuilder.append("name LIKE '%").append(feature).append("%' OR ");

			queryBuilder.append("country LIKE '%").append(feature).append("%'");
		}

		String SQL = queryBuilder.toString();
		System.out.println(SQL);

		con.connectDB();

		Statement stmt = con.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		ObservableList<Company> filteredList = FXCollections.observableArrayList();

		while (rs.next())
			filteredList.add(new Company(rs.getString(1), rs.getString(2)));

		rs.close();
		stmt.close();
		con.getCon().close();

		return filteredList;

	}

	// to execute the sql command given
	public static void ExecuteStatement(String SQL) throws SQLException {
		System.out.println(SQL);

		Statement stmt = con.getCon().createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();

	}

	public static void deleteRow(Company row) {
		// TODO Auto-generated method stub

		try {
			con.connectDB();

			ExecuteStatement("delete from  company where name='" + row.getName() + "';");
			con.getCon().close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void addCompany(String name, String country) {

		try {
			String sql = "INSERT INTO company (name, country) VALUES " + "('" + name + "','" + country + "')";
			con.connectDB();
			Company.ExecuteStatement(sql);
			con.getCon().close();

			companyList.add(new Company(name, country));
		} catch (SQLException e) {
			Alert informationAlert = new Alert(Alert.AlertType.ERROR);
			informationAlert.setTitle("Information");
			informationAlert.setHeaderText(null);
			informationAlert.setContentText("Company name already exists.");
			informationAlert.showAndWait();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
