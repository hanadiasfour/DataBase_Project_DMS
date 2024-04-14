package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddAppointment extends GridPane {
	private Label carId, customerId, date, time, dot1, assignLabel;
	private TextField carIdBox, customerIdBox;
	private TableView<Car> carInfoTableView;
	private TableView<customer> customerInfoTableView;
	private TableView<employee> EmployeeInfoTableView;
	private DatePicker datePicker;
	private TextField minBox;
	private TextField hourBox;
	private ArrayList<customer> customerDataList;
	private ArrayList<Car> cardata;
	private ObservableList<customer> customerList;
	private ObservableList<Car> carList;
	private ObservableList<employee> empDataList;
	private ArrayList<employee> empList;
	private Button assignbt, addbt, addcar, delcar;
	private VBox vbox;
	private HBox hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7;
	private Stage stage;
	private Connecter connector = Main.con;

	public AddAppointment() {

		carId = new Label("Car ID:");
		customerId = new Label("Cus. ID:");
		ImageView img = new ImageView(new Image("addCar2.png"));
		addcar = new Button("", img);

		ImageView img2 = new ImageView(new Image("delCar.png"));
		delcar = new Button("", img2);

		carIdBox = new TextField();
		carIdBox.setMaxSize(160, 50);
		HBox h = new HBox();
		h.setSpacing(2);
		h.getChildren().addAll(carIdBox, addcar);

		customerIdBox = new TextField();
		customerIdBox.setMaxSize(carIdBox.getMaxWidth(), carIdBox.getMaxHeight());

		carInfoTableView = new TableView<Car>();
		carInfoTableView.setMaxSize(800, 100);

		TableColumn carID = new TableColumn("Car_ID");
		carID.setMaxWidth(150);
		carID.setCellValueFactory(new PropertyValueFactory<Car, String>("car_id"));

		TableColumn Status = new TableColumn("Status");
		Status.setMaxWidth(150);
		Status.setCellValueFactory(new PropertyValueFactory<Car, String>("Status"));

		TableColumn VIN = new TableColumn("VIN");
		VIN.setMaxWidth(150);
		VIN.setCellValueFactory(new PropertyValueFactory<Car, String>("VIN"));

		TableColumn Price = new TableColumn("Price");
		Price.setMaxWidth(150);
		Price.setCellValueFactory(new PropertyValueFactory<Car, String>("Price"));

		TableColumn Mileage = new TableColumn("Mileage");
		Mileage.setMaxWidth(150);
		Mileage.setCellValueFactory(new PropertyValueFactory<Car, String>("Mileage"));

		TableColumn Model = new TableColumn("Model");
		Model.setMaxWidth(150);
		Model.setCellValueFactory(new PropertyValueFactory<Car, String>("Model"));
		TableColumn Color = new TableColumn("Color");
		Color.setMaxWidth(150);
		Color.setCellValueFactory(new PropertyValueFactory<Car, String>("Color"));

		TableColumn Year = new TableColumn("Year");
		Year.setMaxWidth(150);
		Year.setCellValueFactory(new PropertyValueFactory<Car, Integer>("Year"));
		TableColumn<Car, String> compCol = new TableColumn<Car, String>("company");
		compCol.setMinWidth(100);
		compCol.setCellValueFactory(new PropertyValueFactory<>("company"));

		TableColumn<Car, String> statusCol = new TableColumn<>("status");
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		carInfoTableView.getColumns().addAll(carID, Color, Year, Model, compCol, statusCol, VIN, Price, Mileage);

		cardata = new ArrayList<>();
		carList = FXCollections.observableArrayList(cardata);

		addcar.setOnAction(e -> {
			try {
				int carAdded = getCarData(carIdBox.getText().trim(), cardata);

				if (carAdded == 1) {
					carList.setAll(cardata);

					carInfoTableView.setItems(carList);
					ConfirmWindow c = new ConfirmWindow();
					c.getText().setText("added");
					c.getStage().show();
				} else if (carAdded == -1) {
					showAlert("Car with the same ID already exists!");
				} else if (carAdded == 0) {
					showAlert("Car does not exist!");
				}

			} catch (ClassNotFoundException | SQLException ex) {
				showAlert("sorry please try again!");
//		        ex.printStackTrace();
			}
		});

		carInfoTableView.setEditable(false);
		delcar.setOnAction(e -> {
			Car selectedCar = carInfoTableView.getSelectionModel().getSelectedItem();

			if (selectedCar != null) {
				cardata.remove(selectedCar);
				carList.setAll(cardata);
				carInfoTableView.setItems(carList);
				ConfirmWindow c = new ConfirmWindow();
				c.getText().setText("Car deleted");
				c.getStage().show();
			} else {
				showAlert("Please select a car to delete.");
			}
		});

		// Create TableView for customerInfo
		customerInfoTableView = new TableView<customer>();
		customerInfoTableView.setEditable(false);
		customerInfoTableView.setMinSize(800, 150);

		customerInfoTableView = new TableView<customer>();
		customerInfoTableView.setEditable(false);
		customerInfoTableView.setMaxHeight(90);
		carInfoTableView.setMaxHeight(90);
		customerInfoTableView.setMinHeight(90);
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
		customerInfoTableView.setEditable(false);
		customerDataList = new ArrayList<>();
		customerList = FXCollections.observableArrayList(customerDataList);
		customerIdBox.setOnAction(e -> {
			try {
				customerDataList.clear();
				getCustomerData(customerDataList, customerIdBox.getText().trim());
				customerList.setAll(customerDataList);
				customerInfoTableView.setItems(customerList);

			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
			}
		});
		HBox hbox11 = new HBox(10, carId, h);

		date = new Label("Date:");

		datePicker = new DatePicker();

		time = new Label("Time:");
		minBox = new TextField("00");
		minBox.setMaxSize(50, 50);
		minBox.setMinSize(50, 50);

		hourBox = new TextField("00");
		hourBox.setMaxSize(50, 50);
		hourBox.setMinSize(50, 50);

		dot1 = new Label(" : ");
		hbox4 = new HBox(10, hourBox, dot1, minBox);

		EmployeeInfoTableView = new TableView<employee>();
		EmployeeInfoTableView.setEditable(false);
		EmployeeInfoTableView.setMaxSize(800, 100);

		TableColumn employeeIDCol = new TableColumn("Employee_ID");
		employeeIDCol.setMaxWidth(150);
		employeeIDCol.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_id"));

		TableColumn EmpfullNameCol = new TableColumn("employee_name");
		EmpfullNameCol.setMaxWidth(150);
		EmpfullNameCol.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_name"));

		TableColumn email = new TableColumn("Email");
		email.setMaxWidth(150);
		email.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_email"));

		TableColumn phone = new TableColumn("Phone");
		phone.setMaxWidth(150);
		phone.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_phone"));

		TableColumn dateOfHire = new TableColumn("Date_Of_Hire");
		dateOfHire.setMaxWidth(150);
		dateOfHire.setCellValueFactory(new PropertyValueFactory<employee, String>("emp_Date_hire"));

		EmployeeInfoTableView.getColumns().addAll(employeeIDCol, EmpfullNameCol, email, phone);
		customerInfoTableView.setEditable(false);

		assignbt = new Button("Assign to employee");
		assignbt.getStyleClass().add("button");

		hbox7 = new HBox(10, assignbt);

		addbt = new Button("Add Appointment");
		HBox h2 = new HBox(addbt);

		setStyles();
		setTableViewStyles(EmployeeInfoTableView);
		setTableViewStyles(carInfoTableView);
		setTableViewStyles(customerInfoTableView);
		SelectEmployee sel = new SelectEmployee();

		assignbt.setOnAction(e -> {
			LocalDate localDate = datePicker.getValue();

			if (localDate != null) {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String formattedDate = localDate.format(dateFormatter);
				java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.parse(formattedDate, dateFormatter));

				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				LocalTime localTime = LocalTime.parse(hourBox.getText() + ":" + minBox.getText() + ":00",
						timeFormatter);
				Time sqlTime = Time.valueOf(localTime);

				sel.setAppdate(sqlDate);
				sel.setApptime(sqlTime);
				sel.getStage().show();
				sel.setOpen(true);
			}
		});
		empDataList = FXCollections.observableArrayList();
		empList = new ArrayList<>();
		sel.getSelect().setOnAction(e -> {

			String id = sel.getId();
			if (id != null) {
				try {
					empDataList.clear();
					getEmployeeData(empList, id);
					empDataList.addAll(empList);
					// empDataList = FXCollections.observableArrayList(empList);
					EmployeeInfoTableView.setItems(empDataList);
					sel.getStage().close();
				} catch (ClassNotFoundException e1) {
					showAlert("sorry assigning the employee failed!");
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					showAlert("sorry assigning the employee failed!");

				}

			} else {
				showAlert("sorry assigning the employee failed!");

			}
		});

		addbt.setOnAction(e -> {
			if (EmployeeInfoTableView.getItems().isEmpty()) {
				try {
					String id = autoAssign(hourBox.getText() + ":" + minBox.getText() + ":00",
							datePicker.getPromptText()) + "";
					String appid = getAppointmentID();
					if (appid != null) {

						insertToAppointment(appid, customerIdBox.getText(), id, datePicker.getPromptText(),
								hourBox.getText() + ":" + minBox.getText() + ":00", cardata);

//						insertToAppointment(appid, customerIdBox.getText(), id, datePicker.getPromptText(),
//								hourBox + ":" + minBox + ":00");
					} else {
						showAlert("please recheck your data0");

					}

					carIdBox.setText("");
					customerIdBox.setText("");
					datePicker.setValue(null);
					cardata = new ArrayList<>();
					empDataList = FXCollections.observableArrayList(new ArrayList<>());
					EmployeeInfoTableView.setItems(empDataList);
					empList = new ArrayList<>();
					customerList = FXCollections.observableArrayList(new ArrayList<>());
					customerInfoTableView.setItems(customerList);
					customerDataList = new ArrayList<>();
					carList = FXCollections.observableArrayList(new ArrayList<>());
					carInfoTableView.setItems(carList);

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					showAlert("please recheck your data");

					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					showAlert("please recheck your data");

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					showAlert("please recheck your data");

				} catch (NullPointerException e2) {
					// TODO: handle exception
					showAlert("make sure u have assgined an employee");
				}
			} else {
				ObservableList<employee> items = EmployeeInfoTableView.getItems();

				if (!items.isEmpty()) {
					employee firstEmployee = items.get(0);
					String employeeId = (String) employeeIDCol.getCellData(firstEmployee);
					String appid;
					try {
						appid = getAppointmentID();
						if (appid != null) {
							try {
								insertToAppointment(appid, customerIdBox.getText(), employeeId,
										datePicker.getPromptText(), hourBox.getText() + ":" + minBox.getText() + ":00",
										cardata);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								showAlert("please recheck your data");
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								showAlert("please recheck your data");
							}
						}
						carIdBox.setText("");
						customerIdBox.setText("");
						datePicker.setValue(null);
						empDataList = FXCollections.observableArrayList(new ArrayList<>());
						EmployeeInfoTableView.setItems(empDataList);
						customerList = FXCollections.observableArrayList(new ArrayList<>());
						customerInfoTableView.setItems(customerList);
						carList = FXCollections.observableArrayList(new ArrayList<>());
						carInfoTableView.setItems(carList);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						showAlert("please recheck your data");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						showAlert("please recheck your data");
					}

				} else {
					showAlert("please recheck your data");
				}
			}
		});

		add(carId, 0, 0);
		add(h, 1, 0);
		add(carInfoTableView, 2, 0);
		add(delcar, 3, 0);

		add(customerId, 0, 1);
		add(customerIdBox, 1, 1);
		add(customerInfoTableView, 2, 1);

		add(date, 0, 2);
		add(datePicker, 1, 2);

		add(hbox4, 1, 3);
		add(time, 0, 3);

		add(hbox7, 2, 3);
		add(EmployeeInfoTableView, 2, 2);
		add(h2, 0, 4);

		setStyle("-fx-background-color: #FAE7E9;");

		setStyle("-fx-background-color: #FAE7E9;");
		setHgap(20);
		setVgap(60);
		setPadding(new Insets(50, 50, 50, 50));
		setAlignment(Pos.CENTER);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Label getCarId() {
		return carId;
	}

	public void setCarId(Label carId) {
		this.carId = carId;
	}

	public Label getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Label customerId) {
		this.customerId = customerId;
	}

	public Label getDate() {
		return date;
	}

	public void setDate(Label date) {
		this.date = date;
	}

	public Label getTime() {
		return time;
	}

	public void setTime(Label time) {
		this.time = time;
	}

	public Label getDot1() {
		return dot1;
	}

	public void setDot1(Label dot1) {
		this.dot1 = dot1;
	}

	public Label getAssignLabel() {
		return assignLabel;
	}

	public void setAssignLabel(Label assignLabel) {
		this.assignLabel = assignLabel;
	}

	public TextField getCarIdBox() {
		return carIdBox;
	}

	public void setCarIdBox(TextField carIdBox) {
		this.carIdBox = carIdBox;
	}

	public TextField getCustomerIdBox() {
		return customerIdBox;
	}

	public void setCustomerIdBox(TextField customerIdBox) {
		this.customerIdBox = customerIdBox;
	}

	public TableView<Car> getCarInfoTableView() {
		return carInfoTableView;
	}

	public void setCarInfoTableView(TableView<Car> carInfoTableView) {
		this.carInfoTableView = carInfoTableView;
	}

	public TableView<customer> getCustomerInfoTableView() {
		return customerInfoTableView;
	}

	public void setCustomerInfoTableView(TableView<customer> customerInfoTableView) {
		this.customerInfoTableView = customerInfoTableView;
	}

	public TableView<employee> getEmployeeInfoTableView() {
		return EmployeeInfoTableView;
	}

	public void setEmployeeInfoTableView(TableView<employee> employeeInfoTableView) {
		EmployeeInfoTableView = employeeInfoTableView;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}

	public TextField getMinBox() {
		return minBox;
	}

	public void setMinBox(TextField minBox) {
		this.minBox = minBox;
	}

	public TextField getHourBox() {
		return hourBox;
	}

	public void setHourBox(TextField hourBox) {
		this.hourBox = hourBox;
	}

	public Button getAssignbt() {
		return assignbt;
	}

	public void setAssignbt(Button assignbt) {
		this.assignbt = assignbt;
	}

	public Button getAddbt() {
		return addbt;
	}

	public void setAddbt(Button addbt) {
		this.addbt = addbt;
	}

	public VBox getVbox() {
		return vbox;
	}

	public void setVbox(VBox vbox) {
		this.vbox = vbox;
	}

	public HBox getHbox1() {
		return hbox1;
	}

	public void setHbox1(HBox hbox1) {
		this.hbox1 = hbox1;
	}

	public HBox getHbox2() {
		return hbox2;
	}

	public void setHbox2(HBox hbox2) {
		this.hbox2 = hbox2;
	}

	public HBox getHbox3() {
		return hbox3;
	}

	public void setHbox3(HBox hbox3) {
		this.hbox3 = hbox3;
	}

	public HBox getHbox4() {
		return hbox4;
	}

	public void setHbox4(HBox hbox4) {
		this.hbox4 = hbox4;
	}

	public HBox getHbox5() {
		return hbox5;
	}

	public void setHbox5(HBox hbox5) {
		this.hbox5 = hbox5;
	}

	public HBox getHbox6() {
		return hbox6;
	}

	public void setHbox6(HBox hbox6) {
		this.hbox6 = hbox6;
	}

	public HBox getHbox7() {
		return hbox7;
	}

	public void setHbox7(HBox hbox7) {
		this.hbox7 = hbox7;
	}

	private void setTableViewStyles(TableView<?> tableView) {
		tableView.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		for (TableColumn<?, ?> column : tableView.getColumns()) {
			column.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		}
	}

	private void setStyles() {
		Label[] labels = { carId, customerId, date, dot1, time, };
		for (Label label : labels) {
			label.setStyle(
					"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");
		}

		TextField[] textFields = { carIdBox, customerIdBox, hourBox, minBox };
		for (TextField textField : textFields) {
			textField.setStyle(
					"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #6E7072; -fx-font-style: italic;");
		}

		carIdBox.setStyle(
				"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");

		customerIdBox.setStyle(

				"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");

		minBox.setStyle(
				"-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224; -fx-font-style: italic;");

		hourBox.setStyle(

				"-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224; -fx-font-style: italic;");

	}

	public String autoAssign(String time, String date) throws SQLException, ParseException, ClassNotFoundException {
		connector.connectDB();
		String assignedEmployeeId = null;

		try {
			java.sql.Time sqlTime = java.sql.Time.valueOf(LocalTime.parse(time));

			java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.parse(date));

			String sqlQuery = "SELECT employee_id FROM employee WHERE employee_id NOT IN "
					+ "(SELECT employee_id FROM appointment WHERE appointment_time = '" + sqlTime
					+ "' AND appointment_date = '" + sqlDate + "') "
					+ "AND employee_id NOT IN (SELECT employee_id FROM appointment WHERE appointment_date = '" + sqlDate
					+ "')";

			PreparedStatement preparedStatement = connector.getCon().prepareStatement(sqlQuery);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				assignedEmployeeId = rs.getString("employee_id");
			}

		} catch (SQLException | DateTimeParseException e) {
			showAlert("please recheck your data");

		} finally {
			connector.getCon().close();
		}

		return assignedEmployeeId;
	}

	private int getCarData(String carId, ArrayList<Car> carDataList) throws SQLException, ClassNotFoundException {
		String SQL = "SELECT car_id, color, year, model, status, VIN, price, mileage, company_name "
				+ "FROM Car WHERE car_id = '" + carId + "'";
		connector.connectDB();

		try (Statement stmt = connector.getCon().createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {

			while (rs.next()) {
				Car carInstance = new Car(rs.getString("car_id"), rs.getString("color"), rs.getString("year"),
						rs.getString("model"), rs.getString("status"), rs.getString("VIN"), rs.getString("price"),
						rs.getString("mileage"), rs.getString("company_name"));

				if (!carDataList.contains(carInstance)) {
					carDataList.add(carInstance);
					return 1;
				} else {

					return -1;
				}
			}
		} catch (SQLException e) {
			showAlert("sorry somthing went wrong");
		} finally {
			connector.getCon().close();
		}

		return 0;
	}

	private void getCustomerData(ArrayList<customer> customerList, String customerId)
			throws SQLException, ClassNotFoundException {
		String SQL = "SELECT customer_id, Full_Name, Email, Phone, Age, " + "Adress, Date_Of_Birth, License_Number "
				+ "FROM customer " + "WHERE customer_id = '" + customerId + "'";

		connector.connectDB();

		try (Statement stmt = connector.getCon().createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {
				customer customerInstance = new customer(rs.getString("customer_id"), rs.getString("Full_Name"),
						rs.getString("Email"), rs.getString("Phone"), rs.getString("Age"), rs.getString("Adress"),
						rs.getString("Date_Of_Birth"), rs.getString("License_Number"));
				customerList.add(customerInstance);
			}
		} catch (SQLException e) {
			showAlert("sorry somthing went wrong");
			// e.printStackTrace();
		} finally {
			connector.getCon().close();
		}
	}

	private void getEmployeeData(ArrayList<employee> employeeList, String employeeId)
			throws SQLException, ClassNotFoundException {
		String SQL = "SELECT *  " + "FROM employee " + "WHERE employee_id = '" + employeeId + "'";

		connector.connectDB();

		try (Statement stmt = connector.getCon().createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {
				employee employeeInstance = new employee(rs.getString("employee_id"), rs.getString("Full_Name"),
						rs.getString("Email"), rs.getString("Date_Of_Hire"), rs.getString("Phone"));
				employeeList.add(employeeInstance);
			}
		} catch (SQLException e) {
			showAlert("sorry somthing went wrong");
			e.printStackTrace();
		} finally {
			connector.getCon().close();
		}
	}

	public void insertToAppointment(String appointmentId, String customerId, String employeeId, String appointmentDate,
			String appointmentTime, List<Car> carData) throws SQLException, ClassNotFoundException {
		connector.connectDB();
		LocalDate localDate = datePicker.getValue();

		if (localDate != null) {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String formattedDate = localDate.format(dateFormatter);
			java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.parse(formattedDate, dateFormatter));

			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalTime localTime = LocalTime.parse(hourBox.getText() + ":" + minBox.getText() + ":00", timeFormatter);
			Time sqlTime = Time.valueOf(localTime);
			try {
				String sqlQuery = "INSERT INTO appointment (appointment_id, customer_id, employee_id, appointment_date, appointment_time) "
						+ "VALUES ('" + appointmentId + "', '" + customerId + "', '" + employeeId + "', '" + sqlDate
						+ "', '" + sqlTime + "')";

				Statement statement = connector.getCon().createStatement();
				statement.executeUpdate(sqlQuery);

				for (Car car : carData) {
					String insertCarQuery = "INSERT INTO associated (appointment_id, car_id) VALUES ('" + appointmentId
							+ "', '" + car.getCar_id() + "')";
					Statement insertCarStatement = connector.getCon().createStatement();
					insertCarStatement.executeUpdate(insertCarQuery);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				showAlert("Sorry, something went wrong!");
			} finally {
				connector.getCon().close();
			}
		}
	}

	private void showAlert(String message) {
		Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
		informationAlert.setTitle("Information");
		informationAlert.setHeaderText(null);
		informationAlert.setContentText(message);
		informationAlert.showAndWait();

	}

	private String getAppointmentID() throws SQLException, ClassNotFoundException {
		String SQL = "SELECT appointment_id FROM appointment ORDER BY CONVERT(appointment_id, SIGNED) DESC";
		String appointmentID = null;

		connector.connectDB();

		try (PreparedStatement pstmt = connector.getCon().prepareStatement(SQL)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					appointmentID = rs.getString(1);
					if (appointmentID == null) {
						appointmentID = "1";
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connector.getCon().close();
		}

		int id = Integer.parseInt(appointmentID) + 1;
		return String.valueOf(id);
	}

}
