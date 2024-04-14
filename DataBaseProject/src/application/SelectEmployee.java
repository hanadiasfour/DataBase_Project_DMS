package application;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SelectEmployee {

	private Button select, search, refresh;
	private ComboBox<String> feature;
	private TableView<employee> table;
	private TextField searchtxf;
	private GridPane grid;
	private Stage stage;
	private Connecter con = Main.con;
	private Time apptime;
	private ObservableList<employee> data;
	private Date appdate;
	private String id;
	private boolean isOpen = false, isRead = false;

	public SelectEmployee() {
		ImageView img = new ImageView(new Image("Search.png"));

		select = new Button("select employee");

		search = new Button("", img);
		searchtxf = new TextField();
		feature = new ComboBox<String>();
		table = new TableView<>();

		search.setStyle("-fx-background-color:white; -fx-border-color:black;");
		search.setPrefSize(45, 45);
		searchtxf.setPromptText("Search appoitments");

		img.setFitHeight(20);
		img.setFitWidth(20);
		table.setPrefSize(800, 200);
		TableColumn<employee, String> emp_id = new TableColumn<employee, String>("ID");
		TableColumn<employee, String> emp_name = new TableColumn<employee, String>("Name");
		TableColumn<employee, String> emp_email = new TableColumn<employee, String>("Email");
		TableColumn<employee, String> emp_date = new TableColumn<employee, String>("Date Of Hire");
		TableColumn<employee, String> emp_phone = new TableColumn<employee, String>("phone");

		table.getColumns().addAll(emp_id, emp_name, emp_email, emp_date, emp_phone);

		emp_id.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_id"));
		emp_name.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_name"));
		emp_email.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_email"));
		emp_date.setCellValueFactory(new PropertyValueFactory<employee, String>("Date_hire"));
		emp_phone.setCellValueFactory(new PropertyValueFactory<employee, String>("employee_phone"));

		emp_id.setCellFactory(TextFieldTableCell.<employee>forTableColumn());
		emp_name.setCellFactory(TextFieldTableCell.<employee>forTableColumn());
		emp_email.setCellFactory(TextFieldTableCell.<employee>forTableColumn());
		emp_phone.setCellFactory(TextFieldTableCell.<employee>forTableColumn());
		emp_date.setCellFactory(TextFieldTableCell.<employee>forTableColumn());

		data = assignTo(this.apptime, this.appdate);
		table.setItems(data);

		table.setEditable(false);
		setTableViewStyles(table);
		search.getStyleClass().add("my-button");

		feature.setPrefSize(150, 40);
		feature.setPromptText("");
		feature.getItems().addAll("empID", "name");
		feature.getSelectionModel().select(0);

		HBox searching = new HBox();
		searching.getChildren().addAll(searchtxf, search);

		HBox top = new HBox(15);
		top.getChildren().addAll(searching, feature);

		HBox control = new HBox(15);
		grid = new GridPane();
		grid.add(top, 0, 0);
		grid.add(table, 0, 1);
		grid.add(control, 0, 2);

		HBox h = new HBox();
		refresh = new Button("refresh");
		h.getChildren().addAll(refresh, select);
		h.setSpacing(10);
		grid.add(h, 2, 2);

		grid.setHgap(-80);
		grid.setVgap(15);
		grid.setPadding(new Insets(50, 10, 10, 50));
		grid.setStyle("-fx-background-color:#FAE7E9;");

		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		select.setOnAction(e -> {
			handleRowSelection();
		});

		refresh.setOnAction(e -> {

			data = assignTo(apptime, appdate);
			table.setItems(data);

		});

		table.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				handleRowSelection();

			}
		});

		search.setOnAction(e -> {

			String s = feature.getSelectionModel().getSelectedItem().toString();

			ArrayList<Appointment> empSearch = new ArrayList<Appointment>();
			ArrayList<Appointment> cusSearch = new ArrayList<Appointment>();

			if (s.equals(new String("empID"))) {
				try {

					data = FXCollections.observableArrayList();

					data = searchById(searchtxf.getText().trim());
					table.setItems(data);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (s.equals(new String("name"))) {
				try {
					try {
						data = FXCollections.observableArrayList();

						data = FXCollections.observableArrayList(searchByName(searchtxf.getText().trim()));
						table.setItems(data);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		stage = new Stage();

		stage.setMaxWidth(400);
		stage.setMaxHeight(300);

		stage.setScene(scene);

		stage.setOnShown(e -> {
			data = assignTo(this.apptime, this.appdate);
			table.setItems(data);

		});

	}

	public Button getSelect() {
		return select;
	}

	public void setSelect(Button select) {
		this.select = select;
	}

	public Button getSearch() {
		return search;
	}

	public void setSearch(Button search) {
		this.search = search;
	}

	public ComboBox<String> getFeature() {
		return feature;
	}

	public void setFeature(ComboBox<String> feature) {
		this.feature = feature;
	}

	public TextField getSearchtxf() {
		return searchtxf;
	}

	public void setSearchtxf(TextField searchtxf) {
		this.searchtxf = searchtxf;
	}

	public GridPane getGrid() {
		return grid;
	}

	public void setGrid(GridPane grid) {
		this.grid = grid;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public ObservableList<employee> assignTo(Time time, Date date) {
		try {
			return FXCollections.observableArrayList(getData(time, date));
		} catch (SQLException | ParseException | ClassNotFoundException e) {

			e.printStackTrace();
			return FXCollections.observableArrayList();
		}
	}

	public ArrayList<employee> getData(Time time, Date date)
			throws SQLException, ParseException, ClassNotFoundException {
		ArrayList<employee> employees = new ArrayList<>();
		con.connectDB();

		if (time != null && date != null) {
			String sqlQuery = "SELECT * FROM employee WHERE employee_id NOT IN (SELECT employee_id FROM appointment WHERE appointment_time = '"
					+ time + "' AND appointment_date = '" + date
					+ "') AND employee_id NOT IN (SELECT employee_id FROM appointment WHERE appointment_date = '" + date
					+ "')";

			PreparedStatement preparedStatement = con.getCon().prepareStatement(sqlQuery);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				try {
					employees.add(new employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		con.getCon().close();

		return employees;
	}

	private void setTableViewStyles(TableView<?> tableView) {
		tableView.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		for (TableColumn<?, ?> column : tableView.getColumns()) {
			column.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		}
	}

	public ObservableList<employee> searchById(String employeeId) {
		try {
			return FXCollections.observableArrayList(getDataById(employeeId));
		} catch (SQLException | ParseException | ClassNotFoundException e) {
			e.printStackTrace();
			return FXCollections.observableArrayList();
		}
	}

	public Button getRefresh() {
		return refresh;
	}

	public void setRefresh(Button refresh) {
		this.refresh = refresh;
	}

	public TableView<employee> getTable() {
		return table;
	}

	public void setTable(TableView<employee> table) {
		this.table = table;
	}

	public Connecter getCon() {
		return con;
	}

	public void setCon(Connecter con) {
		this.con = con;
	}

	public ObservableList<employee> getData() {
		return data;
	}

	public void setData(ObservableList<employee> data) {
		this.data = data;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	private ArrayList<employee> getDataById(String employeeId)
			throws SQLException, ParseException, ClassNotFoundException {
		ArrayList<employee> employees = new ArrayList<>();
		con.connectDB();

		try {
			String sqlQuery = "SELECT * FROM employee WHERE employee_id = '" + employeeId
					+ "' AND employee_id NOT IN (SELECT employee_id FROM appointment WHERE appointment_time = '"
					+ apptime + "' AND appointment_date = '" + appdate + "')";
			PreparedStatement preparedStatement = con.getCon().prepareStatement(sqlQuery);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				try {
					employees.add(new employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.getCon().close();
		}

		return employees;
	}

	private ArrayList<employee> searchByName(String employeeName)
			throws SQLException, ParseException, ClassNotFoundException {
		ArrayList<employee> employees = new ArrayList<>();
		con.connectDB();

		try {
			String sqlQuery = "SELECT * FROM employee WHERE Full_Name = '" + employeeName
					+ "' AND employee_id NOT IN (SELECT employee_id FROM appointment WHERE appointment_time = '"
					+ apptime + "' AND appointment_date = '" + appdate
					+ "') AND employee_id NOT IN (SELECT employee_id FROM appointment WHERE appointment_date = '"
					+ appdate + "')";
			PreparedStatement preparedStatement = con.getCon().prepareStatement(sqlQuery);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				try {
					employees.add(new employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.getCon().close();
		}

		return employees;
	}

	private void handleRowSelection() {
		employee selectedEmployee = table.getSelectionModel().getSelectedItem();
		if (selectedEmployee != null) {
			this.id = selectedEmployee.getEmployee_id();
			isRead = true;
			setOpen(false);

		} else {
			showAlert("please select a row first");
			isRead = false;

		}
	}

	public Time getApptime() {
		return apptime;
	}

	public void setApptime(Time apptime) {
		this.apptime = apptime;
	}

	public Date getAppdate() {
		return appdate;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private void showAlert(String message) {
		Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
		informationAlert.setTitle("Information");
		informationAlert.setHeaderText(null);
		informationAlert.setContentText(message);
		informationAlert.showAndWait();

	}
}
