package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class employee {

	private String employee_id;
	private String employee_name;
	private String employee_email;
	private String emp_Date_hire;
	private String employee_phone;
	private static Connecter con = Main.con;

	public employee() {
		super();
	}

	public employee(String employee_id, String employee_name, String employee_email, String date_hire,
			String employee_phone) {
		super();
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.employee_email = employee_email;
		emp_Date_hire = date_hire;
		this.employee_phone = employee_phone;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_email() {
		return employee_email;
	}

	public void setEmployee_email(String employee_email) {
		this.employee_email = employee_email;
	}

	public String getDate_hire() {
		return emp_Date_hire;
	}

	public void setDate_hire(String date_hire) {
		emp_Date_hire = date_hire;
	}

	public String getEmployee_phone() {
		return employee_phone;
	}

	public void setEmployee_phone(String employee_phone) {
		this.employee_phone = employee_phone;
	}

	public void addEmployee() throws SQLException, ParseException {
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

		String sql = "insert into employee (employee_id, Full_Name,Email,Date_Of_Hire,Phone) values ('"
				+ this.employee_id + "','" + this.employee_name + "','" + this.employee_email + "',Date '"
				+ this.emp_Date_hire + "','" + this.employee_phone + "')";
		try {
			myStmt.executeUpdate(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		con.getCon().close();

	}

	public void updateEmployee() throws SQLException {
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
		try {
			Date date = Utils.convertStringToDate(this.emp_Date_hire);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sql = "UPDATE employee SET Full_Name = '" + this.employee_name + "', Email = '" + this.employee_email
				+ "', Date_Of_Hire = '" + this.emp_Date_hire + "', Phone = '" + this.employee_phone
				+ "' WHERE (employee_id = '" + this.employee_id + "');";
		try {
			myStmt.executeUpdate(sql);
			con.getCon().close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}

	public static ArrayList<employee> searchEmloyee(String condition, String search) throws Exception {

		ArrayList<employee> employeesSearch = new ArrayList<employee>();
		con.connectDB();

		Statement myStmt = con.getCon().createStatement();

		String sql = "SELECT * FROM employee where " + condition + " like'%" + search + "%'; ";
		ResultSet rs = myStmt.executeQuery(sql);

		while (rs.next()) {
			try {

				employeesSearch.add(new employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		myStmt.close();
		con.getCon().close();

		return employeesSearch;

	}

	public void deleteEmployee() throws Exception {
		con.connectDB();

		Statement myStmt = con.getCon().createStatement();

		String sql = "DELETE FROM employee WHERE (employee_id = '" + this.employee_id + "');";
		try {
			myStmt.executeUpdate(sql);
			con.getCon().close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}

}