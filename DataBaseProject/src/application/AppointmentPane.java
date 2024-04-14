package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AppointmentPane extends GridPane {
	private Button addAppoitment, cancelAppoitmern, viewAppoitment, refresh, search;
	private ComboBox<String> feature;
	private TableView<Appointment> table;
	private TextField searchtxf;
	private Connecter con = Main.con;
	private ArrayList<Appointment> data;

	public AppointmentPane() {

		ImageView img = new ImageView(new Image("Search.png"));

		addAppoitment = new Button("Add appointemnt");
		cancelAppoitmern = new Button("cancel appointemnt");
		viewAppoitment = new Button("view appoitment");
		refresh = new Button("refresh");

		search = new Button("", img);
		searchtxf = new TextField();
		feature = new ComboBox<String>();
		table = new TableView<Appointment>();

		search.setStyle("-fx-background-color:white; -fx-border-color:black;");
		search.setPrefSize(45, 45);
		searchtxf.setPromptText("Search appoitments");

		img.setFitHeight(20);
		img.setFitWidth(20);
		addAppoitment.getStyleClass().add("my-button");

		cancelAppoitmern.getStyleClass().add("my-button");

		feature.setPrefSize(150, 40);
		feature.setPromptText("");
		feature.getItems().addAll("empID", "CusID");
		feature.getSelectionModel().select(0);

		HBox searching = new HBox();
		searching.getChildren().addAll(searchtxf, search);

		HBox top = new HBox(15);
		top.getChildren().addAll(searching, feature);

		HBox control = new HBox(15);
		control.getChildren().addAll(addAppoitment, cancelAppoitmern);

		TableView<Appointment> table = new TableView<>();
		TableColumn<Appointment, String> empidCol = new TableColumn<>("Employee ID");
		empidCol.setMinWidth(50);
		empidCol.setCellValueFactory(new PropertyValueFactory<>("employee_id"));

		TableColumn<Appointment, String> cusCol = new TableColumn<>("Customer ID");
		cusCol.setMinWidth(100);
		cusCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

		TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
		dateCol.setMinWidth(100);
		dateCol.setCellValueFactory(new PropertyValueFactory<>("appointment_date"));

		TableColumn<Appointment, String> timeCol = new TableColumn<>("Time");
		timeCol.setMinWidth(100);
		timeCol.setCellValueFactory(new PropertyValueFactory<>("appointment_time"));

		table.getColumns().addAll(empidCol, cusCol, dateCol, timeCol);
		data = new ArrayList<>();
		table.setEditable(true);
		timeCol.setCellFactory(TextFieldTableCell.<Appointment>forTableColumn());
		dateCol.setCellFactory(TextFieldTableCell.<Appointment>forTableColumn());
		timeCol.setOnEditCommit((CellEditEvent<Appointment, String> t) -> {
			((Appointment) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAppointment_time(t.getNewValue());
			update(t.getRowValue().getEmployee_id(), t.getNewValue(), "appointment_time");
		});

		dateCol.setOnEditCommit((CellEditEvent<Appointment, String> t) -> {
			((Appointment) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAppointment_date(t.getNewValue());
			update(t.getRowValue().getEmployee_id(), t.getNewValue(), "appointment_date");
		});
		ObservableList<Appointment> list = FXCollections.observableArrayList(data);

		try {
			getData(data);
			System.out.println(data);
			list.setAll(data);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table.setItems(list);

		cancelAppoitmern.setOnAction(e -> {
			Appointment selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				String employeeId = selectedItem.getEmployee_id();

				table.getItems().remove(selectedItem);

				deleteAppointment(employeeId);
			} else {
				showAlert("Please select an appointment to cancel.");
			}
		});

		viewAppoitment.setOnAction(e -> {
			Appointment selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				String employeeId = selectedItem.getEmployee_id();
				String appid = selectedItem.getAppointment_id();

				ViewAppoitment v = new ViewAppoitment();
				v.getTextArea().setText(v.getAppointmentDetails(appid));
			} else {
				showAlert("Please select an appointment to view its details");
			}
		});
		search.setOnAction(e -> {

			String s = feature.getSelectionModel().getSelectedItem().toString();

			ArrayList<Appointment> empSearch = new ArrayList<Appointment>();
			ArrayList<Appointment> cusSearch = new ArrayList<Appointment>();

			if (s.equals(new String("empID"))) {
				try {
					list.setAll(empSearch);

					searchEmployee(searchtxf.getText().trim(), empSearch);
					list.setAll(empSearch);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("CusID"))) {
				try {
					try {
						list.setAll(cusSearch);

						searchCustomer(searchtxf.getText().trim(), cusSearch);
						list.setAll(cusSearch);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		refresh.setOnAction(e -> {
			try {
				data = new ArrayList<>();
				getData(data);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
			list.setAll(data);
		});

		add(top, 0, 0);
		add(table, 0, 1);
		add(control, 0, 2);
		HBox h = new HBox();
		h.getChildren().addAll(refresh, viewAppoitment);
		h.setSpacing(10);
		add(h, 2, 2);

		setHgap(-80);
		setVgap(15);
		setPadding(new Insets(50, 10, 10, 50));
		table.setMinHeight(400);
		table.setMinWidth(800);
		cusCol.setPrefWidth(150);
		dateCol.setPrefWidth(150);
		empidCol.setPrefWidth(150);
		timeCol.setPrefWidth(150);

		setStyle("-fx-background-color:#FAE7E9;");
		table.setItems(list);

		setTableViewStyles(table);
	}

	public Button getAddAppoitment() {
		return addAppoitment;
	}

	public void setAddAppoitment(Button addAppoitment) {
		this.addAppoitment = addAppoitment;
	}

	public Button getCancelAppoitmern() {
		return cancelAppoitmern;
	}

	public void setCancelAppoitmern(Button cancelAppoitmern) {
		this.cancelAppoitmern = cancelAppoitmern;
	}

	public Button getViewAppoitment() {
		return viewAppoitment;
	}

	public void setViewAppoitment(Button viewAppoitment) {
		this.viewAppoitment = viewAppoitment;
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

	public TableView<Appointment> getTable() {
		return table;
	}

	public void setTable(TableView<Appointment> table) {
		this.table = table;
	}

	public TextField getSearchtxf() {
		return searchtxf;
	}

	public void setSearchtxf(TextField searchtxf) {
		this.searchtxf = searchtxf;
	}

	private void getData(ArrayList<Appointment> appointmentList) throws SQLException, ClassNotFoundException {
		String SQL = "SELECT employee_id, customer_id, appointment_date,appointment_time,appointment_id  FROM appointment";

		con.connectDB();

		try (PreparedStatement pstmt = con.getCon().prepareStatement(SQL); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				Appointment appointmentInstance = new Appointment(rs.getString("employee_id"),
						rs.getString("customer_id"), rs.getString("appointment_date"),
						rs.getString("appointment_time"));
				appointmentInstance.setAppointment_id(rs.getString("appointment_id"));
				appointmentList.add(appointmentInstance);
				System.out.println(appointmentInstance.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.getCon().close();
		}
	}

	private void update(String employeeId, String newValue, String column) {
		try {
			con.connectDB();
			String updateSQL = "UPDATE appointment SET " + column + " = '" + newValue + "' WHERE employee_id = '"
					+ employeeId + "'";
			try (PreparedStatement pstmt = con.getCon().prepareStatement(updateSQL)) {
				pstmt.executeUpdate();
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			// Handle exceptions
		} finally {
			try {
				con.getCon().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void setTableViewStyles(TableView<?> tableView) {
		tableView.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		for (TableColumn<?, ?> column : tableView.getColumns()) {
			column.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		}
	}

	private void showAlert(String message) {
		Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
		informationAlert.setTitle("Information");
		informationAlert.setHeaderText(null);
		informationAlert.setContentText(message);
		informationAlert.showAndWait();
	}

	private void deleteAppointment(String employeeId) {
		try {
			con.connectDB();
			String deleteSQL = "DELETE FROM appointment WHERE employee_id = '" + employeeId + "'";
			try (PreparedStatement pstmt = con.getCon().prepareStatement(deleteSQL)) {
				pstmt.executeUpdate();
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			showAlert("Failed to cancel appointment.");
		} finally {
			try {
				con.getCon().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void searchDate(SQLData date, ArrayList<Appointment> list) throws SQLException, ClassNotFoundException {
		String SQL = "SELECT employee_id, customer_id, appointment_date, appointment_time FROM appointment WHERE appointment_date = "
				+ date + ";";

		con.connectDB();

		try (PreparedStatement pstmt = con.getCon().prepareStatement(SQL)) {
			pstmt.setString(0, date.toString());

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Appointment appointmentInstance = new Appointment(rs.getString("employee_id"),
							rs.getString("customer_id"), rs.getString("appointment_date"),
							rs.getString("appointment_time"));
					list.add(appointmentInstance);
					System.out.println(appointmentInstance.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.getCon().close();
		}
	}

	private void searchTime(String time, ArrayList<Appointment> list) throws SQLException, ClassNotFoundException {
		String SQL = "SELECT employee_id, customer_id, appointment_date, appointment_time FROM appointment WHERE appointment_time = "
				+ time + ";";

		con.connectDB();

		try (PreparedStatement pstmt = con.getCon().prepareStatement(SQL)) {
			pstmt.setString(1, time);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Appointment appointmentInstance = new Appointment(rs.getString("employee_id"),
							rs.getString("customer_id"), rs.getString("appointment_date"),
							rs.getString("appointment_time"));
					list.add(appointmentInstance);
					System.out.println(appointmentInstance.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.getCon().close();
		}
	}

	private void searchEmployee(String employeeId, ArrayList<Appointment> list)
			throws SQLException, ClassNotFoundException {
		String SQL = "SELECT employee_id, customer_id, appointment_date, appointment_time FROM appointment WHERE employee_id = ?";

		con.connectDB();

		try (PreparedStatement pstmt = con.getCon().prepareStatement(SQL)) {
			pstmt.setString(1, employeeId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Appointment appointmentInstance = new Appointment(rs.getString("employee_id"),
							rs.getString("customer_id"), rs.getString("appointment_date"),
							rs.getString("appointment_time"));
					list.add(appointmentInstance);
					System.out.println(appointmentInstance.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.getCon().close();
		}
	}

	private void searchCustomer(String searchText, ArrayList<Appointment> list)
			throws SQLException, ClassNotFoundException {
		String SQL = "SELECT employee_id, customer_id, appointment_date, appointment_time FROM appointment WHERE customer_id = '"
				+ searchText + "'";

		con.connectDB();

		try (Statement stmt = con.getCon().createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {
				Appointment appointmentInstance = new Appointment(rs.getString("employee_id"),
						rs.getString("customer_id"), rs.getString("appointment_date"),
						rs.getString("appointment_time"));
				list.add(appointmentInstance);
				System.out.println(appointmentInstance.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.getCon().close();
		}
	}
}