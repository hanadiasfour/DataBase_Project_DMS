package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class payment {

	private String payment_id;
	private String payment_date;
	private String amount;
	private String purchase_id;
	private String payment_method;
	private static Connecter con = Main.con;

	public payment() {
		super();
	}

	public payment(String payment_id, String payment_date, String amount, String purchase_id, String payment_method) {
		super();
		this.payment_id = payment_id;
		this.payment_date = payment_date;
		this.amount = amount;
		this.purchase_id = purchase_id;
		this.payment_method = payment_method;
	}

	public String getPurchase_id() {
		return purchase_id;
	}

	public void setPurchase_id(String purchase_id) {
		this.purchase_id = purchase_id;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}

	public void addPayment() throws Exception {
		con.connectDB();

		Statement myStmt = con.getCon().createStatement();

		String sql = "INSERT INTO payment (payment_id, payment_date, amount, purchase_ID, payment_method) VALUES "
				+ "('" + this.payment_id + "', '" + this.payment_date + "','" + this.amount + "', '" + this.purchase_id
				+ "', '" + this.payment_method + "');";

		try {
			myStmt.executeUpdate(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		con.getCon().close();

	}

	public static ArrayList<payment> searchPayment(String condition, String search) throws Exception {

		ArrayList<payment> paymentSearch = new ArrayList<payment>();
		con.connectDB();

		Statement myStmt = con.getCon().createStatement();

		String sql = "SELECT * FROM payment where " + condition + " like '%" + search + "%'; ";
		ResultSet rs = myStmt.executeQuery(sql);

		while (rs.next()) {
			try {

				paymentSearch.add(new payment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		myStmt.close();
		con.getCon().close();

		return paymentSearch;

	}

}