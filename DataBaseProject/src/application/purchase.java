package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class purchase {

	String purchase_id;
	String remaining_amount;
	String discount;
	String agreed_price;
	String purchase_date;
	String car_id;
	String customer_id;
	private static Connecter con = Main.con;

	public purchase(String purchase_id, String remaining_amount, String discount, String agreed_price,
			String purchase_date, String car_id, String customer_id) {
		super();
		this.purchase_id = purchase_id;
		this.remaining_amount = remaining_amount;
		this.discount = discount;
		this.agreed_price = agreed_price;
		this.purchase_date = purchase_date;
		this.car_id = car_id;
		this.customer_id = customer_id;
	}

	public purchase() {
		super();

	}

	public String getPurchase_id() {
		return purchase_id;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getAgreed_price() {
		return agreed_price;
	}

	public void setAgreed_price(String agreed_price) {
		this.agreed_price = agreed_price;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getRemaining_amount() {
		return remaining_amount;
	}

	public void setRemaining_amount(String remaining_amount) {
		this.remaining_amount = remaining_amount;
	}

	public String getPurchase_date() {
		return purchase_date;
	}

	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}

	public static ArrayList<purchase> searchPurchase(String condition, String search) throws Exception {

		ArrayList<purchase> purchaseSearch = new ArrayList<purchase>();
		con.connectDB();

		Statement myStmt = con.getCon().createStatement();

		String sql = "SELECT * FROM purchase where " + condition + " like'%" + search + "%'; ";
		ResultSet rs = myStmt.executeQuery(sql);

		while (rs.next()) {
			try {

				purchaseSearch.add(new purchase(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		myStmt.close();
		con.getCon().close();

		return purchaseSearch;

	}

	public void updatePurchase() throws SQLException {
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

		String sql = "UPDATE purchase SET purchase_ID = '" + this.purchase_id + "', Amount_Left_To_Pay = '"
				+ this.remaining_amount + "', Discount = '" + this.discount + "', Agreed_Price = '" + this.agreed_price
				+ "', " + "Date = '" + this.purchase_date + "', car_ID = '" + this.car_id + "', customer_ID = '"
				+ this.customer_id + "' WHERE (purchase_ID = '" + this.purchase_id + "');" + "";

		try {
			myStmt.executeUpdate(sql);
			con.getCon().close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}

	public static ArrayList<Car> serchPurCar(String condition, String search) throws Exception {

		ArrayList<Car> purCarSearch = new ArrayList<Car>();
		con.connectDB();

		Statement myStmt = con.getCon().createStatement();

		String sql = "SELECT * FROM car where " + condition + " like'%" + search + "%'; ";
		ResultSet rs = myStmt.executeQuery(sql);

		while (rs.next()) {
			try {
				Car car = new Car(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

				purCarSearch.add(car);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		myStmt.close();
		con.getCon().close();

		return purCarSearch;

	}

}