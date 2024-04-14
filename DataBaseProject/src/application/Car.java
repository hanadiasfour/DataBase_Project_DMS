package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Car {

	static ObservableList<Car> carList = FXCollections.observableArrayList();
	static ObservableList<String> colorList = FXCollections.observableArrayList();
	static ObservableList<String> yearList = FXCollections
			.observableArrayList(Arrays.asList("1980", "1990", "2000", "2010", "2020", "2024"));
	static ObservableList<String> priceList = FXCollections
			.observableArrayList(Arrays.asList("1000", "10000", "100000", "1000000"));
	static ObservableList<String> statusList = FXCollections.observableArrayList(Arrays.asList("Exist", "OutOfStock"));

	private static Connecter con = Main.con;
	private String car_id;
	private String color;
	private String year;
	private String model;
	private String status;
	private String VIN;
	private String price;
	private String mileage;
	private String company;

	public Car(String car_id, String year, String color, String model, String status, String VIN, String price,
			String mileage, String company) {
		this.car_id = car_id;
		this.color = color;
		this.year = year;
		this.model = model;
		this.status = status;
		this.VIN = VIN;
		this.price = price;
		this.mileage = mileage;
		this.company = company;

	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String VIN) {
		this.VIN = VIN;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public static void getData() throws ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		try {

			con.connectDB();

			SQL = "select car_id,year,color,model,status,VIN,price,mileage,company_name from car order by car_id";
			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				carList.add(new Car(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));

				if (!colorList.contains(rs.getString(3))) {

					colorList.add(rs.getString(3));

				}

			}

			rs.close();
			stmt.close();
			con.getCon().close();
			;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ObservableList<Car> getSpecificData(String color, String status, String price, String year,
			String company) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		StringBuilder queryBuilder = new StringBuilder("SELECT * FROM car WHERE ");

		// Check each condition and append to the query if the value is not null or
		// empty
		if (color != null) {
			queryBuilder.append("color = '").append(color).append("' AND ");
		}

		if (status != null) {
			queryBuilder.append("status = '").append(status).append("' AND ");
		}

		if (price != null) {
			queryBuilder.append("price < ").append(price).append(" AND ");
		}

		if (year != null) {
			queryBuilder.append("year < ").append(year).append(" AND ");
		}

		if (company != null) {
			queryBuilder.append("company_name = '").append(company).append("' AND ");
		}

		// Remove the trailing " AND " if it exists
		if (queryBuilder.toString().endsWith(" AND ")) {
			queryBuilder.setLength(queryBuilder.length() - 5);
		}

		String SQL = queryBuilder.toString();

		con.connectDB();

		Statement stmt = con.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		ObservableList<Car> filteredList = FXCollections.observableArrayList();

		while (rs.next())
			filteredList.add(new Car(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));

		rs.close();
		stmt.close();
		con.getCon().close();

		return filteredList;

	}

	// to execute the sql command given
	public static void ExecuteStatement(String SQL) throws SQLException {

		Statement stmt = con.getCon().createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();

	}

	public static void deleteRow(Car row) {
		// TODO Auto-generated method stub

		try {
			con.connectDB();

			ExecuteStatement("delete from  car where car_id='" + row.getCar_id() + "';");
			con.getCon().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void addCar(String company_name, String color, String year, String vin, String mileage, String price,
			String model, String car_id) {

		try {
			String sql = "INSERT INTO car (company_name, color, year, vin, mileage, price, model, car_id,status) VALUES "
					+ "('" + company_name + "','" + color + "'," + year + "," + vin + "," + mileage + "," + price + ",'"
					+ model + "','" + car_id + "', 'Exist')";
			con.connectDB();
			Car.ExecuteStatement(sql);
			con.getCon().close();

			carList.add(new Car(car_id, year, color, model, "Exist", vin, price, mileage, company_name));
		} catch (SQLException e) {
			Alert informationAlert = new Alert(Alert.AlertType.ERROR);
			informationAlert.setTitle("Information");
			informationAlert.setHeaderText(null);
			informationAlert.setContentText("Car ID and VIN must be unique.");
			informationAlert.showAndWait();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Car otherCar = (Car) obj;

		return this.car_id.equals(otherCar.car_id);
	}

}
