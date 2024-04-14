package application;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class PurchesPane extends GridPane {
	private Connecter connector = Main.con;
	private Label carId, customerId, discount, date, priceAfterDiscount;
	private TextField carIdBox, customerIdBox, priceBox, discountBox;
	private Button confirmButton;
	private ConfirmWindow confirm;
	private DatePicker datePicker;
	private TableView<Car> carInfoTableView;
	private TableView<customer> customerInfoTableView;
	private ArrayList<customer> customerDataList;
	private ArrayList<Car> cardata;
//	private ObservableList<customer> customerList;
//	private ObservableList<Car> carList;
//	private ComboBox methods;

	public TextField getCarIdBox() {

		return carIdBox;

	}

	public PurchesPane() {
		Image image = new Image("icons8-percent-50.png");
		ImageView imageView = new ImageView(image);
		confirm = new ConfirmWindow();
		carId = new Label("Car ID: ");
		customerId = new Label("Cus ID:");
		discount = new Label("Discount:");
		date = new Label("Date: ");
		priceAfterDiscount = new Label("Price After Discount: ");
		cardata = new ArrayList<>();
		customerDataList = new ArrayList<>();
//		customerList = FXCollections.observableArrayList(customerDataList);
//		carList = FXCollections.observableArrayList(cardata);

		carIdBox = new TextField(" ");
		carIdBox.setMaxSize(160, 50);

		customerIdBox = new TextField(" ");
		customerIdBox.setMaxSize(carIdBox.getMaxWidth(), carIdBox.getMaxHeight());
		datePicker = new DatePicker();
		datePicker.setPromptText("mm/dd/yyyy");
		priceBox = new TextField("price");
		priceBox.setMinSize(carIdBox.getMinWidth(), carIdBox.getMinHeight());
		priceBox.setEditable(false);
		priceBox.setMaxSize(carIdBox.getMaxWidth(), carIdBox.getMaxHeight());

//		methods = new ComboBox<>();
// 		methods.getItems().addAll("cash", "visa");
//		methods.getSelectionModel().select(0);
//        methods.setEditable(false); 

		carInfoTableView = new TableView<Car>();
		TableColumn<Car, String> car_idCol = new TableColumn<Car, String>("car_id");
		car_idCol.setMinWidth(50);
		car_idCol.setCellValueFactory(new PropertyValueFactory<Car, String>("car_id"));

		TableColumn<Car, String> colorCol = new TableColumn<Car, String>("color");
		colorCol.setMinWidth(100);
		colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

		TableColumn<Car, String> yearCol = new TableColumn<Car, String>("year");
		yearCol.setMinWidth(100);
		yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

		TableColumn<Car, String> modelCol = new TableColumn<Car, String>("model");
		modelCol.setMinWidth(100);
		modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

		TableColumn<Car, String> statusCol = new TableColumn<Car, String>("status");
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

		TableColumn<Car, String> VINCol = new TableColumn<Car, String>("VIN");
		VINCol.setMinWidth(100);
		VINCol.setCellValueFactory(new PropertyValueFactory<>("VIN"));

		TableColumn<Car, String> priceCol = new TableColumn<Car, String>("price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn<Car, String> mileageCol = new TableColumn<Car, String>("mileage");
		mileageCol.setMinWidth(100);
		mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));

		TableColumn<Car, String> compCol = new TableColumn<Car, String>("company");
		compCol.setMinWidth(100);
		compCol.setCellValueFactory(new PropertyValueFactory<>("company"));

		carInfoTableView.getColumns().addAll(car_idCol, colorCol, yearCol, modelCol, compCol, statusCol, VINCol,
				priceCol, mileageCol);

		cardata = new ArrayList<>();
		ObservableList<Car> carList = FXCollections.observableArrayList(cardata);

		carIdBox.setOnAction(e -> {
			try {
				cardata.clear();
				getCarData(carIdBox.getText().trim(), cardata);
				carList.setAll(cardata);
				carInfoTableView.setItems(carList);

				priceBox.setText(getCarPrice(carIdBox.getText().trim()) + "");
			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
			}
		});

		customerInfoTableView = new TableView<customer>();
		customerInfoTableView.setEditable(false);
		customerInfoTableView.setMaxHeight(75);
		carInfoTableView.setMaxHeight(90);
		customerInfoTableView.setMinHeight(75);
		carInfoTableView.setMinHeight(90);

		TableColumn<customer, String> customer_idCol = new TableColumn<>(" id");
		customer_idCol.setMinWidth(50);
		customer_idCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

		TableColumn<customer, String> nameCol = new TableColumn<>("Name");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("customer_name"));

		TableColumn<customer, String> emailCol = new TableColumn<>("Email");
		emailCol.setMinWidth(100);
		emailCol.setCellValueFactory(new PropertyValueFactory<>("customer__email"));

		TableColumn<customer, String> phoneCol = new TableColumn<>("Phone");
		phoneCol.setMinWidth(100);
		phoneCol.setCellValueFactory(new PropertyValueFactory<>("customer__phone"));

		TableColumn<customer, String> addressCol = new TableColumn<>("Address");
		addressCol.setMinWidth(100);
		addressCol.setCellValueFactory(new PropertyValueFactory<>("customer_address"));

		TableColumn<customer, String> dateCol = new TableColumn<>("Birth Date");
		dateCol.setMinWidth(100);
		dateCol.setCellValueFactory(new PropertyValueFactory<>("customer_date_birth"));

		TableColumn<customer, String> licenseCol = new TableColumn<>("License_number");
		licenseCol.setMinWidth(100);
		licenseCol.setCellValueFactory(new PropertyValueFactory<>("customer_license_number"));

		TableColumn<customer, String> ageCol = new TableColumn<>("customer_age");
		ageCol.setMinWidth(100);
		ageCol.setCellValueFactory(new PropertyValueFactory<>("customer_age"));

		customerInfoTableView.getColumns().addAll(customer_idCol, nameCol, emailCol, phoneCol, addressCol, dateCol,
				licenseCol, ageCol);

		customerDataList = new ArrayList<>();
		ObservableList<customer> customerList = FXCollections.observableArrayList(customerDataList);

		customerIdBox.setOnAction(e -> {
			try {
				customerDataList.clear();
				getCustomerData(customerIdBox.getText().trim(), customerDataList);
				customerList.setAll(customerDataList);
			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
			}
		});

		carInfoTableView.setItems(carList);
		customerInfoTableView.setItems(customerList);
		setTableViewStyles(carInfoTableView);
		setTableViewStyles(customerInfoTableView);

		discountBox = new TextField("00");
		discountBox.setMaxSize(100, 50);
		HBox hbox = new HBox(5);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().addAll(discountBox, imageView);

		confirmButton = new Button("Confirm Deal");
		confirmButton.setMinWidth(200);
		confirmButton.setMinHeight(60);

		add(carId, 0, 0);
		add(carIdBox, 1, 0);
		add(carInfoTableView, 3, 0);

		add(customerId, 0, 1);
		add(customerIdBox, 1, 1);
		add(customerInfoTableView, 3, 1);

		add(discount, 0, 2);
		add(hbox, 1, 2);
		HBox hbox2 = new HBox(5);
		hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox2.getChildren().addAll(priceAfterDiscount, priceBox);
		add(hbox2, 3, 2);

		add(date, 0, 3);
		add(datePicker, 1, 3);

//		add(methods, 3, 3);

		HBox hbox3 = new HBox(5);
		hbox3.setAlignment(Pos.CENTER_RIGHT);
		hbox3.getChildren().addAll(confirmButton);
		add(hbox3, 3, 3);
		setHgap(10);
		setVgap(50);
		setPadding(new Insets(100, 50, 50, 50));

		confirmButton.setOnAction(e -> {
			try {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				LocalDate localDate = datePicker.getValue();
				String formattedDate = localDate.format(dateFormatter);

				java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.parse(formattedDate, dateFormatter));
				int dis;
				try {
					double price = Double.parseDouble(getCarPrice(carIdBox.getText().trim()));
					int discount = Integer.parseInt(discountBox.getText().trim());
					try {
						dis = Integer.parseInt(discountBox.getText().trim());
					} catch (NumberFormatException nfe) {
						dis = 0;

					}

					insertPurchase(price, dis, Double.parseDouble(priceBox.getText().trim()), sqlDate,
							carIdBox.getText().trim(), customerIdBox.getText().trim());
					confirm.getText().setText("Deal is confirmed !");
					confirm.getStage().show();
					carIdBox.setText("");
					customerIdBox.setText("");
					datePicker.setPromptText("");
					discountBox.setText("");
					priceBox.setText("");
					datePicker.setValue(null);
					cardata.clear();
					carList.clear();
					carInfoTableView.setItems(carList);

					customerDataList.clear();
					customerList.clear();
					customerInfoTableView.setItems(customerList);

					customerInfoTableView.setItems(customerList);
					cardata = new ArrayList<>();

					carInfoTableView.setItems(carList);

				} catch (NumberFormatException nfe) {
				}

			} catch (ClassNotFoundException | SQLException e1) {
			} catch (NumberFormatException nfe) {
			}

		});

		discountBox.setOnAction(e -> {
			if (discountBox.getText() != null) {
				try {

					double dis = Double.parseDouble(discountBox.getText().trim());
					if (dis < 0)
						dis = dis * -1;
					double price = Double.parseDouble(getCarPrice(carIdBox.getText().trim()));
					double calc = price * dis / 100;
					if (dis != 0) {
						priceBox.setText(calc + "");

					}
				} catch (NumberFormatException exp) {

				} catch (ClassNotFoundException e1) {
				} catch (SQLException e1) {
				}
			}
		});

		setStyles();

		setStyle("-fx-background-color: #FAE7E9;");
	}

	private void setStyles() {
		Label[] labels = { carId, customerId, discount, date, priceAfterDiscount };
		for (Label label : labels) {
			label.setStyle(
					"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");
		}

		TextField[] textFields = { carIdBox, customerIdBox, priceBox };
		for (TextField textField : textFields) {
			textField.setStyle(
					"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #6E7072; -fx-font-style: italic;");
		}

		discountBox.setStyle(
				"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");

		confirmButton.setStyle(

				"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");

//		methods.setStyle(
//				"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");
//		methods.setStyle("");
//		methods.setMinWidth(ComboBox.USE_PREF_SIZE); 
	}

	private void getCarData(String carId, ArrayList<Car> carDataList) throws SQLException, ClassNotFoundException {
		String SQL = "SELECT car_id, color, year, model, status, VIN, price, mileage, company_name "
				+ "FROM Car WHERE car_id = '" + carId + "'";
		connector.connectDB();

		try (Statement stmt = connector.getCon().createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {

			while (rs.next()) {
				Car carInstance = new Car(rs.getString("car_id"), rs.getString("color"), rs.getString("year"),
						rs.getString("model"), rs.getString("status"), rs.getString("VIN"), rs.getString("price"),
						rs.getString("mileage"), rs.getString("company_name"));

				carDataList.add(carInstance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connector.getCon().close();

		}

	}

	private void getCustomerData(String customerId, ArrayList<customer> customerDataList)
			throws SQLException, ClassNotFoundException {
		String SQL = "SELECT customer_id, Full_Name, Email, Phone, Age, Adress, Date_Of_Birth, License_Number "
				+ "FROM customer WHERE customer_id = '" + customerId + "'";

		connector.connectDB();

		try (Statement stmt = connector.getCon().createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {

			while (rs.next()) {
				customer customerInstance = new customer(rs.getString("customer_id"), rs.getString("Full_Name"),
						rs.getString("Email"), rs.getString("Phone"), rs.getString("Age"), rs.getString("Adress"),
						rs.getString("Date_Of_Birth"), rs.getString("License_Number"));
				customerDataList.add(customerInstance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			connector.getCon().close();
		}
	}

	private String getCarPrice(String carid) throws SQLException, ClassNotFoundException {
		String SQL = "SELECT price FROM Car WHERE car_id = '" + carid + "'";
		String carPrice = null;

		connector.connectDB();

		try (Statement stmt = connector.getCon().createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {

			if (rs.next()) {
				carPrice = rs.getString("price");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connector.getCon().close();

		}

		return carPrice;
	}

	private String getPurchaseID() throws SQLException, ClassNotFoundException {
		String SQL = "Select purchase_id from purchase order by convert (purchase_id,signed)  desc";
		String purchaseID = null;

		connector.connectDB();

		try (PreparedStatement pstmt = connector.getCon().prepareStatement(SQL)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					purchaseID = rs.getString(1);
					if (purchaseID == null) {
						purchaseID = "1";
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connector.getCon().close();
		}
		int id = Integer.parseInt(purchaseID) + 1;
		return String.valueOf(id);
	}

	private void insertPurchase(double amountLeftToPay, int discount, double agreedPrice, Date date, String carID,
			String customerID) throws SQLException, ClassNotFoundException {
		String purchaseID = getPurchaseID();

		String SQL = "INSERT INTO purchase (`purchase_ID`, `Amount Left To Pay`, `Discount`, `Agreed Price`, `Date`, `car_id`, `customer_ID`) "
				+ "VALUES ('" + purchaseID + "', " + amountLeftToPay + ", " + discount + ", " + agreedPrice + ", '"
				+ new java.sql.Date(date.getTime()) + "', '" + carID + "', '" + customerID + "')";

		connector.connectDB();
		connector.getCon().setAutoCommit(false);

		try (Statement stmt = connector.getCon().createStatement()) {
			stmt.executeUpdate(SQL);
			connector.getCon().commit();

		} catch (SQLException e) {
			connector.getCon().rollback();
			e.printStackTrace();
		} finally {
			connector.getCon().close();

		}
	}

	private void setTableViewStyles(TableView<?> tableView) {
		tableView.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		for (TableColumn<?, ?> column : tableView.getColumns()) {
			column.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		}
	}

}
