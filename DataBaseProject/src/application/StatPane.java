package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StatPane extends ScrollPane {

	private Label sold, popular, cars, employees, customers, suppliers;
	private Button refresh;
	private TableView<appointmentNode> doneTable, comingTable;
	private TableView<LeaderBoardNode> employeesLeaderBoard;
	private TableView<DeptNode> deptTable;
	private TableView<soldCarNode> soldTable;
	private static Connecter con = Main.con;

	public StatPane() {

		doneTable = new TableView<appointmentNode>();
		comingTable = new TableView<appointmentNode>();
		employeesLeaderBoard = new TableView<LeaderBoardNode>();
		deptTable = new TableView<DeptNode>();
		soldTable = new TableView<soldCarNode>();
		refresh = new Button("Refresh");

		refresh.getStyleClass().add("my-button");

		doneTable.setPrefSize(700, 300);
		comingTable.setPrefSize(700, 300);
		employeesLeaderBoard.setPrefSize(700, 300);
		deptTable.setPrefSize(700, 300);
		soldTable.setPrefSize(700, 300);

		sold = new Label("");
		popular = new Label("");
		cars = new Label("");
		employees = new Label("");
		customers = new Label("");
		suppliers = new Label("");

		ImageView soldimg = new ImageView(new Image("totalSold.png"));
		ImageView popularimg = new ImageView(new Image("brand.png"));
		ImageView suppliersimg = new ImageView(new Image("popularCompany.png"));
		ImageView carsimg = new ImageView(new Image("cars.png"));
		ImageView employeesimg = new ImageView(new Image("employee.png"));
		ImageView customersimg = new ImageView(new Image("customer.png"));

		GridPane top = new GridPane();
		top.setHgap(35);
		top.setVgap(20);

		top.setPadding(new Insets(0, 0, 0, 60));

		top.add(getMiniPane(suppliersimg, "Number of Suppliers", suppliers), 0, 1);
		top.add(getMiniPane(employeesimg, "Number of Employees", employees), 1, 1);
		top.add(getMiniPane(customersimg, "Number of Customers", customers), 2, 1);
		top.add(getMiniPane(carsimg, "Number of Cars", cars), 3, 1);
		top.add(getMiniPane(soldimg, "Sold Cars", sold), 4, 1);
		top.add(getMiniPane(popularimg, "Most Popular Brand", popular), 5, 1);
		top.add(refresh, 0, 0);

		BorderPane main = new BorderPane();
		main.setTop(top);
		main.setBottom(getTheBottom());
		main.setPadding(new Insets(50, 0, 50, 0));
		main.setStyle("-fx-background-color:#FAE7E9;");

		setContent(main);
		setStyle("-fx-background-color:#FAE7E9;");
		setSoldCarsColumns();
		setDeptColumns();
		setLeaderColumns();
		setDoneColumns();
		setUpcomingColumns();

	}

	private GridPane getTheBottom() {

		ImageView doneAppimg = new ImageView(new Image("doneAppointment.png"));
		ImageView comingAppimg = new ImageView(new Image("comingAppointment.png"));
		ImageView empOfMontheimg = new ImageView(new Image("employeeOfTheMonth.png"));
		ImageView deptimg = new ImageView(new Image("dept.png"));
		ImageView soldcarsimg = new ImageView(new Image("soldCar.png"));

		doneAppimg.setFitHeight(40);
		doneAppimg.setFitWidth(40);
		comingAppimg.setFitHeight(40);
		comingAppimg.setFitWidth(40);
		empOfMontheimg.setFitHeight(40);
		empOfMontheimg.setFitWidth(40);
		deptimg.setFitHeight(40);
		deptimg.setFitWidth(40);
		soldcarsimg.setFitHeight(40);
		soldcarsimg.setFitWidth(40);

		Label donelbl = new Label("Done Appointments:");
		Label cominglbl = new Label("Upcoming Appointments:");
		Label empOfMonthlbl = new Label("Employees Leader Board:");
		Label deptlbl = new Label("Customers In Dept:");
		Label soldlbl = new Label("Sold Cars This Month:");

		donelbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		cominglbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		empOfMonthlbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		deptlbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		soldlbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

		donelbl.setGraphic(doneAppimg);
		cominglbl.setGraphic(comingAppimg);
		empOfMonthlbl.setGraphic(empOfMontheimg);
		deptlbl.setGraphic(deptimg);
		soldlbl.setGraphic(soldcarsimg);

		donelbl.setContentDisplay(ContentDisplay.LEFT);
		cominglbl.setContentDisplay(ContentDisplay.LEFT);
		empOfMonthlbl.setContentDisplay(ContentDisplay.LEFT);
		deptlbl.setContentDisplay(ContentDisplay.LEFT);
		soldlbl.setContentDisplay(ContentDisplay.LEFT);

		VBox box1 = new VBox(5);
		box1.getChildren().addAll(donelbl, doneTable);

		VBox box2 = new VBox(5);
		box2.getChildren().addAll(cominglbl, comingTable);

		VBox box3 = new VBox(5);
		box3.getChildren().addAll(empOfMonthlbl, employeesLeaderBoard);

		VBox box4 = new VBox(5);
		box4.getChildren().addAll(deptlbl, deptTable);

		VBox box5 = new VBox(5);
		box5.getChildren().addAll(soldlbl, soldTable);

		GridPane root = new GridPane();
		root.add(box1, 0, 0);
		root.add(box2, 1, 0);
		root.add(box3, 0, 1);
		root.add(box4, 1, 1);
		root.add(box5, 0, 2);
		root.add(new Text(""), 2, 0);
		root.setHgap(40);
		root.setVgap(40);
		root.setStyle("-fx-background-color:#FAE7E9;");
		root.setPadding(new Insets(50, 0, 50, 40));

		return root;

	}

	private Pane getMiniPane(ImageView img, String discription, Label information) {

		img.setFitHeight(60);
		img.setFitWidth(60);

		Label title = new Label(discription);
		title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		information.setFont(Font.font("Times New Roman", 25));

		VBox box = new VBox(20);
		box.getChildren().addAll(img, title, information);
		box.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(box);
		root.setPrefSize(200, 200);
		root.setStyle("-fx-border-color:black;-fx-background-color:white;");
		root.setPadding(new Insets(50, 10, 50, 10));
		return root;

	}

	public void setInfo() {

		suppliers.setText(Company.companyList.size() + "");
		cars.setText(Car.carList.size() + "");
		sold.setText(getNumberOF("purchase") + "");
		popular.setText(getPopularBrand());
		customers.setText(getNumberOF("customer") + "");
		employees.setText(getNumberOF("employee") + "");
		soldTable.setItems(getSoldCarsData());
		deptTable.setItems(getDeptData());
		employeesLeaderBoard.setItems(getEmployeeLeaderBoard());
		comingTable.setItems(getUpcomingApp());
		doneTable.setItems(getDoneApp());
	}

	private ObservableList<soldCarNode> getSoldCarsData() {
		// TODO Auto-generated method stub

		ObservableList<soldCarNode> soldCarsList = FXCollections.observableArrayList();

		String SQL;

		try {
			con.connectDB();

			SQL = "SELECT c.car_id,c.year,c.color,c.model,c.price,c.company_name, COUNT(*) as purchase_count FROM purchase p, car c \r\n"
					+ "WHERE c.car_id=p.car_ID AND c.car_id IN ( SELECT p.car_ID FROM purchase p WHERE p.Date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY))\r\n"
					+ "GROUP BY c.car_id;";

			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				Car c = new Car(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "", "",
						rs.getString(5), "", rs.getString(6));

				soldCarNode node = new soldCarNode(c, rs.getString(7));
				soldCarsList.add(node);

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

		return soldCarsList;

	}

	private ObservableList<appointmentNode> getDoneApp() {
		// TODO Auto-generated method stub
		ObservableList<appointmentNode> doneList = FXCollections.observableArrayList();

		String SQL;

		try {
			con.connectDB();

			SQL = "SELECT app.appointment_id, app.appointment_date, app.employee_id, e.Full_Name, app.customer_id, c.Full_Name"
					+ " FROM appointment app,  employee e, customer c WHERE app.customer_id = c.customer_id"
					+ " AND app.employee_id = e.employee_id AND app.appointment_date <= CURDATE()";

			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {

				doneList.add(new appointmentNode(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6)));

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
		return doneList;

	}

	private ObservableList<appointmentNode> getUpcomingApp() {
		// TODO Auto-generated method stub

		ObservableList<appointmentNode> comingList = FXCollections.observableArrayList();

		String SQL;

		try {
			con.connectDB();

			SQL = "SELECT app.appointment_id, app.appointment_date, app.employee_id, e.Full_Name, app.customer_id, c.Full_Name"
					+ " FROM appointment app,  employee e, customer c WHERE app.customer_id = c.customer_id"
					+ " AND app.employee_id = e.employee_id AND app.appointment_date >= CURDATE()";

			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {

				comingList.add(new appointmentNode(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6)));

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
		return comingList;

	}

	private ObservableList<LeaderBoardNode> getEmployeeLeaderBoard() {
		// TODO Auto-generated method stub

		ObservableList<LeaderBoardNode> leaderList = FXCollections.observableArrayList();
		String SQL;

		try {
			con.connectDB();

			SQL = "SELECT e.employee_id,e.Full_Name, COUNT(*) as appointments_Done"
					+ " FROM employee e,  appointment app"
					+ " WHERE  e.employee_id=app.employee_id AND e.employee_id IN (" + " SELECT a.employee_id"
					+ " FROM  appointment a" + " WHERE a.appointment_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY))"
					+ " GROUP BY  e.employee_id" + " ORDER BY appointments_Done DESC;";

			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			int rank = 1;

			while (rs.next()) {

				leaderList.add(new LeaderBoardNode(rank + "", rs.getString(1), rs.getString(2), rs.getString(3)));
				rank++;
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

		return leaderList;

	}

	private ObservableList<DeptNode> getDeptData() {
		// TODO Auto-generated method stub

		ObservableList<DeptNode> deptList = FXCollections.observableArrayList();
		String SQL;

		try {
			con.connectDB();

			SQL = "SELECT c.customer_id, c.Full_Name, p.Amount_Left_To_Pay, p.Agreed_Price, p.purchase_ID, p.Date FROM purchase p, customer c WHERE p.Amount_Left_To_Pay <> 0 AND c.customer_id = p.customer_ID LIMIT 0, 1000;";
			;

			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);// in this line is the expection

			while (rs.next()) {

				deptList.add(new DeptNode(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6)));

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

		return deptList;

	}

	private int getNumberOF(String tableName) {

		String SQL;
		int result = 0;

		try {
			con.connectDB();

			SQL = "select COUNT(*) from " + tableName;
			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
				// Handle the case when no result is returned
				result = 0; // or any default value you want
			}
			rs.close();
			stmt.close();
			con.getCon().close();

		} catch (

		ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	private String getPopularBrand() {

		String SQL;
		String result = "";

		try {
			con.connectDB();

			SQL = " SELECT c.company_name FROM car c WHERE c.car_id = ( SELECT car_ID FROM "
					+ "( SELECT car_ID, COUNT(*) as car_count "
					+ "FROM purchase p GROUP BY p.car_ID ) as car_counts ORDER BY car_count DESC LIMIT 1);";

			Statement stmt = con.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			if (rs.next())
				result = rs.getString("company_name");

			rs.close();
			stmt.close();
			con.getCon().close();

		} catch (

		ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// to execute the sql command given
	public static void ExecuteStatement(String SQL) throws SQLException {

		Statement stmt = con.getCon().createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();

	}

	// to connect to the data base

	public Button getRefresh() {
		return refresh;
	}

	public Label getSold() {
		return sold;
	}

	public Label getPopular() {
		return popular;
	}

	public Label getCars() {
		return cars;
	}

	public Label getEmployees() {
		return employees;
	}

	public Label getCustomers() {
		return customers;
	}

	public Label getSuppliers() {
		return suppliers;
	}

	public TableView<appointmentNode> getDoneTable() {
		return doneTable;
	}

	public TableView<appointmentNode> getComingTable() {
		return comingTable;
	}

	public TableView<LeaderBoardNode> getEmployeesOfMonthTable() {
		return employeesLeaderBoard;
	}

	public TableView<DeptNode> getDeptTable() {
		return deptTable;
	}

	public TableView<soldCarNode> getSoldTable() {
		return soldTable;
	}

	public class soldCarNode {

		private String count;
		private String car_id;
		private String color;
		private String year;
		private String model;
		private String price;
		private String company;

		soldCarNode(Car car, String count) {

			this.car_id = car.getCar_id();
			this.color = car.getColor();
			this.year = car.getYear();
			this.model = car.getModel();
			this.price = car.getPrice();
			this.company = car.getCompany();
			this.count = count;

		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
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

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

	}

	public class DeptNode {

		private String purchase_id;
		private String customer_id;
		private String amount;
		private String original;
		private String name;
		private String date;

		public DeptNode(String customer_id, String name, String amount, String original, String purchase_id,
				String date) {
			this.purchase_id = purchase_id;
			this.customer_id = customer_id;
			this.amount = amount;
			this.original = original;
			this.name = name;
			this.date = date;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getPurchase_id() {
			return purchase_id;
		}

		public void setPurchase_id(String purchase_id) {
			this.purchase_id = purchase_id;
		}

		public String getCustomer_id() {
			return customer_id;
		}

		public void setCustomer_id(String customer_id) {
			this.customer_id = customer_id;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getOriginal() {
			return original;
		}

		public void setOriginal(String original) {
			this.original = original;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public class LeaderBoardNode {

		private String employee_id;
		private String name;
		private String appointments;
		private String ranking;

		public LeaderBoardNode(String ranking, String employee_id, String name, String appointments) {
			this.employee_id = employee_id;
			this.name = name;
			this.appointments = appointments;
			this.ranking = ranking;
		}

		public String getEmployee_id() {
			return employee_id;
		}

		public void setEmployee_id(String employee_id) {
			this.employee_id = employee_id;
		}

		public String getAppointments() {
			return appointments;
		}

		public void setAppointments(String appointments) {
			this.appointments = appointments;
		}

		public String getRanking() {
			return ranking;
		}

		public void setRanking(String ranking) {
			this.ranking = ranking;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public class appointmentNode {

		private String id;
		private String date;
		private String emp_id;
		private String empName;
		private String cust_id;
		private String custName;

		public appointmentNode(String id, String date, String emp_id, String empName, String cust_id, String custName) {
			this.id = id;
			this.date = date;
			this.emp_id = emp_id;
			this.empName = empName;
			this.cust_id = cust_id;
			this.custName = custName;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getEmp_id() {
			return emp_id;
		}

		public void setEmp_id(String emp_id) {
			this.emp_id = emp_id;
		}

		public String getEmpName() {
			return empName;
		}

		public void setEmpName(String empName) {
			this.empName = empName;
		}

		public String getCust_id() {
			return cust_id;
		}

		public void setCust_id(String cust_id) {
			this.cust_id = cust_id;
		}

		public String getCustName() {
			return custName;
		}

		public void setCustName(String custName) {
			this.custName = custName;
		}

	}

	private void setSoldCarsColumns() {

		// setting columns
		TableColumn<soldCarNode, String> car_idc = new TableColumn<>("Car_ID");
		TableColumn<soldCarNode, String> colorc = new TableColumn<>("Color");
		TableColumn<soldCarNode, String> yearc = new TableColumn<>("Year");
		TableColumn<soldCarNode, String> modelc = new TableColumn<>("Model");
		TableColumn<soldCarNode, String> pricec = new TableColumn<>("Price");
		TableColumn<soldCarNode, String> companyc = new TableColumn<>("Company");
		TableColumn<soldCarNode, String> countc = new TableColumn<>("Amount Sold");

		car_idc.setCellValueFactory(new PropertyValueFactory<>("car_id"));
		colorc.setCellValueFactory(new PropertyValueFactory<>("color"));
		yearc.setCellValueFactory(new PropertyValueFactory<>("year"));
		modelc.setCellValueFactory(new PropertyValueFactory<>("model"));
		pricec.setCellValueFactory(new PropertyValueFactory<>("price"));
		companyc.setCellValueFactory(new PropertyValueFactory<>("company"));
		countc.setCellValueFactory(new PropertyValueFactory<>("count"));

		// Set column widths
		countc.setMinWidth(120);
		car_idc.setMinWidth(87);
		colorc.setMinWidth(100);
		yearc.setMinWidth(90);
		modelc.setMinWidth(100);
		pricec.setMinWidth(100);
		companyc.setMinWidth(100);

		// Set alignment
		countc.setStyle("-fx-alignment: CENTER;");
		car_idc.setStyle("-fx-alignment: CENTER;");
		colorc.setStyle("-fx-alignment: CENTER;");
		yearc.setStyle("-fx-alignment: CENTER;");
		modelc.setStyle("-fx-alignment: CENTER;");
		pricec.setStyle("-fx-alignment: CENTER;");
		companyc.setStyle("-fx-alignment: CENTER;");

		// Add columns to the table
		soldTable.getColumns().addAll(countc, car_idc, yearc, colorc, modelc, pricec, companyc);

		// Set table properties
		soldTable.setEditable(false);
		soldTable.setSelectionModel(null);
	}

	private void setDeptColumns() {

		// setting columns
		TableColumn<DeptNode, String> customer_idc = new TableColumn<DeptNode, String>("Customer_ID");
		TableColumn<DeptNode, String> namec = new TableColumn<DeptNode, String>("Full_Name");
		TableColumn<DeptNode, String> amountc = new TableColumn<DeptNode, String>("Amount_Left");
		TableColumn<DeptNode, String> originalc = new TableColumn<DeptNode, String>("Agreed_Price");
		TableColumn<DeptNode, String> purchase_idc = new TableColumn<DeptNode, String>("Purchase_ID");
		TableColumn<DeptNode, String> datec = new TableColumn<DeptNode, String>("Purchase Date");

		customer_idc.setCellValueFactory(new PropertyValueFactory<DeptNode, String>("customer_id"));
		namec.setCellValueFactory(new PropertyValueFactory<DeptNode, String>("name"));
		amountc.setCellValueFactory(new PropertyValueFactory<DeptNode, String>("amount"));
		originalc.setCellValueFactory(new PropertyValueFactory<DeptNode, String>("original"));
		purchase_idc.setCellValueFactory(new PropertyValueFactory<DeptNode, String>("purchase_id"));
		datec.setCellValueFactory(new PropertyValueFactory<DeptNode, String>("date"));

		customer_idc.setEditable(false);
		namec.setEditable(false);
		amountc.setEditable(false);
		originalc.setEditable(false);
		purchase_idc.setEditable(false);
		datec.setEditable(false);

		customer_idc.setMinWidth(110);
		namec.setMinWidth(155);
		amountc.setMinWidth(110);
		originalc.setMinWidth(110);
		purchase_idc.setMinWidth(110);
		datec.setMinWidth(100);

		customer_idc.setStyle("-fx-alignment: CENTER;");
		namec.setStyle("-fx-alignment: CENTER;");
		amountc.setStyle("-fx-alignment: CENTER;");
		originalc.setStyle("-fx-alignment: CENTER;");
		purchase_idc.setStyle("-fx-alignment: CENTER;");
		datec.setStyle("-fx-alignment: CENTER;");

		deptTable.setEditable(false);
		deptTable.setSelectionModel(null);
		deptTable.getColumns().addAll(customer_idc, namec, amountc, originalc, purchase_idc, datec);

	}

	private void setLeaderColumns() {

		// setting columns
		TableColumn<LeaderBoardNode, String> rankc = new TableColumn<LeaderBoardNode, String>("Rank");
		TableColumn<LeaderBoardNode, String> employee_idc = new TableColumn<LeaderBoardNode, String>("Employee_ID");
		TableColumn<LeaderBoardNode, String> namec = new TableColumn<LeaderBoardNode, String>("Full_Name");
		TableColumn<LeaderBoardNode, String> amountc = new TableColumn<LeaderBoardNode, String>(
				"Appointments Managed Last Month");

		employee_idc.setCellValueFactory(new PropertyValueFactory<LeaderBoardNode, String>("employee_id"));
		namec.setCellValueFactory(new PropertyValueFactory<LeaderBoardNode, String>("name"));
		amountc.setCellValueFactory(new PropertyValueFactory<LeaderBoardNode, String>("appointments"));
		rankc.setCellValueFactory(new PropertyValueFactory<LeaderBoardNode, String>("ranking"));

		employee_idc.setEditable(false);
		namec.setEditable(false);
		amountc.setEditable(false);
		rankc.setEditable(false);

		employee_idc.setMinWidth(118);
		namec.setMinWidth(170);
		amountc.setMinWidth(300);
		rankc.setMinWidth(110);

		employee_idc.setStyle("-fx-alignment: CENTER;");
		namec.setStyle("-fx-alignment: CENTER;");
		amountc.setStyle("-fx-alignment: CENTER;");
		rankc.setStyle("-fx-alignment: CENTER;");

		employeesLeaderBoard.setEditable(false);
		employeesLeaderBoard.setSelectionModel(null);
		employeesLeaderBoard.getColumns().addAll(rankc, employee_idc, namec, amountc);

	}

	private void setDoneColumns() {

		// setting columns
		TableColumn<appointmentNode, String> app_idc = new TableColumn<appointmentNode, String>("Appointment_ID");
		TableColumn<appointmentNode, String> datec = new TableColumn<appointmentNode, String>("Date");
		TableColumn<appointmentNode, String> employee_idc = new TableColumn<appointmentNode, String>("Employee_ID");
		TableColumn<appointmentNode, String> name1 = new TableColumn<appointmentNode, String>("Employee_Name");
		TableColumn<appointmentNode, String> customer_idc = new TableColumn<appointmentNode, String>("Customer_ID");
		TableColumn<appointmentNode, String> name2 = new TableColumn<appointmentNode, String>("Customer_Name");

		app_idc.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("id"));
		datec.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("date"));
		employee_idc.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("emp_id"));
		name1.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("empName"));
		customer_idc.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("cust_id"));
		name2.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("custName"));

		app_idc.setEditable(false);
		datec.setEditable(false);
		employee_idc.setEditable(false);
		name1.setEditable(false);
		customer_idc.setEditable(false);
		name2.setEditable(false);

		app_idc.setMinWidth(120);
		datec.setMinWidth(87);
		employee_idc.setMinWidth(110);
		name1.setMinWidth(135);
		customer_idc.setMinWidth(110);
		name2.setMinWidth(135);

		app_idc.setStyle("-fx-alignment: CENTER;");
		datec.setStyle("-fx-alignment: CENTER;");
		employee_idc.setStyle("-fx-alignment: CENTER;");
		name1.setStyle("-fx-alignment: CENTER;");
		customer_idc.setStyle("-fx-alignment: CENTER;");
		name2.setStyle("-fx-alignment: CENTER;");

		doneTable.setEditable(false);
		doneTable.setSelectionModel(null);
		doneTable.getColumns().addAll(app_idc, datec, employee_idc, name1, customer_idc, name2);

	}

	private void setUpcomingColumns() {

		// setting columns
		TableColumn<appointmentNode, String> app_idc = new TableColumn<appointmentNode, String>("Appointment_ID");
		TableColumn<appointmentNode, String> datec = new TableColumn<appointmentNode, String>("Date");
		TableColumn<appointmentNode, String> employee_idc = new TableColumn<appointmentNode, String>("Employee_ID");
		TableColumn<appointmentNode, String> name1 = new TableColumn<appointmentNode, String>("Employee_Name");
		TableColumn<appointmentNode, String> customer_idc = new TableColumn<appointmentNode, String>("Customer_ID");
		TableColumn<appointmentNode, String> name2 = new TableColumn<appointmentNode, String>("Customer_Name");

		app_idc.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("id"));
		datec.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("date"));
		employee_idc.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("emp_id"));
		name1.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("empName"));
		customer_idc.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("cust_id"));
		name2.setCellValueFactory(new PropertyValueFactory<appointmentNode, String>("custName"));

		app_idc.setEditable(false);
		datec.setEditable(false);
		employee_idc.setEditable(false);
		name1.setEditable(false);
		customer_idc.setEditable(false);
		name2.setEditable(false);

		app_idc.setMinWidth(120);
		datec.setMinWidth(87);
		employee_idc.setMinWidth(110);
		name1.setMinWidth(135);
		customer_idc.setMinWidth(110);
		name2.setMinWidth(135);

		app_idc.setStyle("-fx-alignment: CENTER;");
		datec.setStyle("-fx-alignment: CENTER;");
		employee_idc.setStyle("-fx-alignment: CENTER;");
		name1.setStyle("-fx-alignment: CENTER;");
		customer_idc.setStyle("-fx-alignment: CENTER;");
		name2.setStyle("-fx-alignment: CENTER;");

		comingTable.setEditable(false);
		comingTable.setSelectionModel(null);
		comingTable.getColumns().addAll(app_idc, datec, employee_idc, name1, customer_idc, name2);

	}

}
