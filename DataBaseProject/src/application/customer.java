package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class customer {

	String customer_id;
	String customer_name;
	String customer__email;
	String customer__phone;
	String customer_age;
	String customer_address;
	String customer_date_birth;
	String customer_license_number;
	static Connecter con = Main.con;

	public customer() {
		super();
	}

	public customer(String customer_id, String customer_name, String customer__email, String customer__phone,
			String customer_age, String customer_address, String customer_date_birth, String customer_license_number) {
		super();
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.customer__email = customer__email;
		this.customer__phone = customer__phone;
		this.customer_age = customer_age;
		this.customer_address = customer_address;
		this.customer_date_birth = customer_date_birth;
		this.customer_license_number = customer_license_number;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer__email() {
		return customer__email;
	}

	public void setCustomer__email(String customer__email) {
		this.customer__email = customer__email;
	}

	public String getCustomer_date_birth() {
		return customer_date_birth;
	}

	public void setCustomer_date_birth(String customer_date_birth) {
		this.customer_date_birth = customer_date_birth;
	}

	public String getCustomer__phone() {
		return customer__phone;
	}

	public void setCustomer__phone(String customer__phone) {
		this.customer__phone = customer__phone;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_license_number() {
		return customer_license_number;
	}

	public void setCustomer_license_number(String customer_license_number) {
		this.customer_license_number = customer_license_number;
	}

	public String getCustomer_age() {
		return customer_age;
	}

	public void setCustomer_age(String customer_age) {
		this.customer_age = customer_age;
	}

	public void addCustomer() throws SQLException {
		try {
			con.connectDB();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Statement myStmt = con.getCon().createStatement();

		String sql = "insert into customer (customer_id, Full_Name,Email,Phone , Age,Adress,Date_Of_Birth,License_Number) values ('"
				+ this.customer_id + "','" + this.customer_name + "','" + this.customer__email + "','"
				+ this.customer__phone + "','" + this.customer_age + "','" + this.customer_address + "','"
				+ this.customer_date_birth + "','" + this.customer_license_number + "');";

		try {
			myStmt.executeUpdate(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		con.getCon().close();

	}

	public void updateCustomer() throws SQLException {
		try {
			con.connectDB();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Statement myStmt = con.getCon().createStatement();

		String sql = "UPDATE customer SET Full_Name = '" + this.customer_name + "', Email = '" + this.customer__email
				+ "', Phone = '" + this.customer__phone + "', Adress = '" + this.customer_address
				+ "', Date_Of_Birth = '" + this.customer_date_birth + "', License_Number = '"
				+ this.customer_license_number + "' WHERE (customer_id = '" + this.customer_id + "');";

		try {
			myStmt.executeUpdate(sql);
			con.getCon().close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}

	public void deleteCustomer() throws Exception {
		con.connectDB();

		Statement myStmt = con.getCon().createStatement();

		String sql = "DELETE FROM customer WHERE (customer_id ='" + this.customer_id + "');";
		try {
			myStmt.executeUpdate(sql);
			con.getCon().close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}

	public static ArrayList<customer> searchCustomer(String condition, String search) throws Exception {
		con.connectDB();

		ArrayList<customer> customerSearch = new ArrayList<customer>();
		Statement myStmt = con.getCon().createStatement();

		String sql = "SELECT * FROM customer where " + condition + " like'%" + search + "%'; ";
		ResultSet rs = myStmt.executeQuery(sql);

		while (rs.next()) {
			try {

				customerSearch.add(new customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		myStmt.close();
		con.getCon().close();

		return customerSearch;

	}

}