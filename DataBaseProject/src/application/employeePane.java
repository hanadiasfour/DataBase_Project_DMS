package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class employeePane extends BorderPane {
	private static Connecter con = Main.con;
	private TableView<employee> employeeTable = new TableView<employee>();
	private ObservableList<employee> data;

	public BorderPane employeeclass() throws Exception {

		BorderPane root = new BorderPane();

		Image m = new Image("car.png");
		ImageView image = new ImageView(m);

		root.setStyle("-fx-background-color:#FAE7E9;");

		TableColumn<employee, String> emp_id = new TableColumn<employee, String>("ID");
		TableColumn<employee, String> emp_name = new TableColumn<employee, String>("Name");
		TableColumn<employee, String> emp_email = new TableColumn<employee, String>("Email");
		TableColumn<employee, String> emp_date = new TableColumn<employee, String>("Date Of Hire");
		TableColumn<employee, String> emp_phone = new TableColumn<employee, String>("phone");

		employeeTable.getColumns().addAll(emp_id, emp_name, emp_email, emp_date, emp_phone);

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

		data = FXCollections.observableArrayList(getData());
		employeeTable.setItems(data);

		emp_id.setEditable(false);
		emp_name.setEditable(true);
		emp_date.setEditable(true);
		emp_email.setEditable(true);
		emp_phone.setEditable(true);
		employeeTable.setEditable(true);

		emp_name.setOnEditCommit((CellEditEvent<employee, String> t) -> {
			((employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setEmployee_name(t.getNewValue()); // display

			employee obj = new employee(t.getRowValue().getEmployee_id(), t.getNewValue(),
					t.getRowValue().getEmployee_email(), t.getRowValue().getDate_hire(),
					t.getRowValue().getEmployee_phone());

			try {
				obj.updateEmployee();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		emp_email.setOnEditCommit((CellEditEvent<employee, String> t) -> {
			((employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setEmployee_email(t.getNewValue()); // display

			employee obj = new employee(t.getRowValue().getEmployee_id(), t.getRowValue().getEmployee_name(),
					t.getNewValue(), t.getRowValue().getDate_hire(), t.getRowValue().getEmployee_phone());

			try {
				obj.updateEmployee();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		emp_date.setOnEditCommit((CellEditEvent<employee, String> t) -> {
			((employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDate_hire(t.getNewValue()); // display

			employee obj = new employee(t.getRowValue().getEmployee_id(), t.getRowValue().getEmployee_name(),
					t.getRowValue().getEmployee_email(), t.getNewValue(), t.getRowValue().getEmployee_phone());

			try {
				obj.updateEmployee();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		emp_phone.setOnEditCommit((CellEditEvent<employee, String> t) -> {
			((employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setEmployee_phone(t.getNewValue()); // display

			employee obj = new employee(t.getRowValue().getEmployee_id(), t.getRowValue().getEmployee_name(),
					t.getRowValue().getEmployee_email(), t.getRowValue().getDate_hire(), t.getNewValue());

			try {
				obj.updateEmployee();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		// Design Button
		TextField searchTextEmp = new TextField();
		searchTextEmp.setPromptText("search for an employee");
		ComboBox searchchoiceEmp = new ComboBox();
		searchchoiceEmp.getItems().addAll("ID", "Name", "Email", "Date of Hire", "Phone");
		searchchoiceEmp.getSelectionModel().select(0);
		ImageView img = new ImageView(new Image("Search.png"));
		Button empSearchButton = new Button("", img);
		searchchoiceEmp.setMinWidth(150);
		HBox searchBoxEmp = new HBox();
		HBox searchBox = new HBox();
		searchBox.getChildren().addAll(searchTextEmp, empSearchButton);
		searchBoxEmp.getChildren().addAll(searchBox, searchchoiceEmp);
		searchBoxEmp.setSpacing(10);
		empSearchButton.setStyle("-fx-background-color:white; -fx-border-color:black;");
		empSearchButton.setPrefSize(45, 45);
		img.setFitHeight(20);
		img.setFitWidth(20);

		Button empAddButton = new Button("Add");
		Button empDeleteButton = new Button("Delete");
		empAddButton.setMinWidth(150);
		empDeleteButton.setMinWidth(150);
		Button EmpRefreshButton = new Button("Refresh");
		EmpRefreshButton.setMinWidth(150);
		HBox addDelete = new HBox();
		addDelete.getChildren().addAll(empAddButton, empDeleteButton, EmpRefreshButton);
		EmpRefreshButton.setAlignment(Pos.BASELINE_RIGHT);
		addDelete.setSpacing(10);

		VBox vEmp = new VBox();
		vEmp.getChildren().addAll(searchBoxEmp, employeeTable, addDelete);
		vEmp.setSpacing(20);
		vEmp.setPadding(new Insets(40));
		root.setCenter(vEmp);

		employeeTable.setMaxHeight(1300);
		employeeTable.setMaxWidth(1000);
		emp_id.setPrefWidth(200);
		emp_name.setPrefWidth(200);
		emp_date.setPrefWidth(200);
		emp_email.setPrefWidth(200);
		emp_phone.setPrefWidth(200);
		employeeTable.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

		empSearchButton.setOnAction(e -> {
			String s = searchchoiceEmp.getSelectionModel().getSelectedItem().toString();

			ArrayList<employee> employeesSearch = new ArrayList<employee>();

			if (s.equals(new String("ID"))) {
				try {
					employeesSearch = employee.searchEmloyee("employee_id", searchTextEmp.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Name"))) {
				try {
					employeesSearch = employee.searchEmloyee("Full_Name", searchTextEmp.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Email"))) {
				try {
					employeesSearch = employee.searchEmloyee("Email", searchTextEmp.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Date of Hire"))) {
				try {
					employeesSearch = employee.searchEmloyee("Date_Of_Hire", searchTextEmp.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Phone"))) {
				try {
					employeesSearch = employee.searchEmloyee("Phone", searchTextEmp.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			ObservableList<employee> dataSearch = FXCollections.observableArrayList(employeesSearch);
			employeeTable.setItems(dataSearch);

		});

		empDeleteButton.setOnAction(e -> {

			ObservableList<employee> selectedRows = employeeTable.getSelectionModel().getSelectedItems();

			if (employeeTable.getSelectionModel().getSelectedItem() != null) {
				ArrayList<employee> rows = new ArrayList<>(selectedRows);

				rows.forEach(row -> {
					try {
						row.deleteEmployee();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					employeeTable.getItems().remove(row);
				});

			}

			else {
				Utils.showAlert("Please Seleact A Row");
			}
			employeeTable.getSelectionModel().clearSelection();

		});

		empAddButton.setOnAction(e -> {

			employeeTable.getSelectionModel().clearSelection();

			try {
				createAddEmpWindow();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		EmpRefreshButton.setOnAction(e -> {
			try {
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				data = FXCollections.observableArrayList(getData());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			employeeTable.setItems(data);
			employeeTable.getSelectionModel().clearSelection();

		});

		return root;
	}

	// add employee
	public void createAddEmpWindow() throws Exception {

		Button add = new Button("Add");

		BorderPane rootAddEmp = new BorderPane();
		Label title = new Label("\nAdd An Employee");
		title.setFont(Font.font("Elephant", FontWeight.BOLD, 30));

		rootAddEmp.setTop(title);
		title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		title.setAlignment(Pos.BOTTOM_CENTER);

		Label employeeIdLabel = new Label("Employee Id");
		Label employeeNameLabel = new Label("Employee Name");
		Label employeeEmailLabel = new Label("Employee Email");
		Label employeeDateLabel = new Label("Employee Date Of Hire");
		Label employeePhoneLabel = new Label("Employee Phone");

		TextField employeeIdText = new TextField();
		TextField employeeNameText = new TextField();
		TextField employeeEmailText = new TextField();
		TextField employeePhoneText = new TextField();
		DatePicker datePicker = new DatePicker(LocalDate.of(2024, 1, 1));
		datePicker.getEditor().setDisable(true);
		datePicker.setConverter(Utils.dateFormatter());

		GridPane PaneAddEmp = new GridPane();
		PaneAddEmp.add(employeeIdLabel, 0, 0);
		PaneAddEmp.add(employeeNameLabel, 0, 1);
		PaneAddEmp.add(employeeDateLabel, 0, 2);
		PaneAddEmp.add(employeeEmailLabel, 0, 3);
		PaneAddEmp.add(employeePhoneLabel, 0, 4);
		PaneAddEmp.add(employeeIdText, 1, 0);
		PaneAddEmp.add(employeeNameText, 1, 1);
		PaneAddEmp.add(datePicker, 1, 2);
		PaneAddEmp.add(employeeEmailText, 1, 3);
		PaneAddEmp.add(employeePhoneText, 1, 4);
		PaneAddEmp.add(add, 0, 5);
		GridPane.setColumnSpan(add, 2);
		add.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		PaneAddEmp.setVgap(15);
		PaneAddEmp.setHgap(35);
		PaneAddEmp.setAlignment(Pos.CENTER);

		employeeIdText.setText(Utils.maxId("employee"));
		employeeIdText.setEditable(false);

		rootAddEmp.setCenter(PaneAddEmp);
		rootAddEmp.setStyle("-fx-background-color:#FAE7E9;");

		add.setOnAction(e -> {

			employee obj = null;

			obj = new employee(employeeIdText.getText(), employeeNameText.getText(), employeeEmailText.getText(),
					datePicker.getValue().toString(), employeePhoneText.getText());

			try {
				obj.addEmployee();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				data = FXCollections.observableArrayList(getData());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			employeeTable.setItems(data);

			try {
				employeeIdText.setText(Utils.maxId("employee"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			employeeNameText.setText("");
			employeeEmailText.setText("");
			employeePhoneText.setText("");
			datePicker.setValue(LocalDate.of(2024, 1, 1));

		});

		Stage stage = new Stage();
		Scene scene = new Scene(rootAddEmp, 500, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

	public ArrayList<employee> getData() throws Exception {

		ArrayList<employee> employees = new ArrayList<employee>();
		con.connectDB();

		Statement stat = con.getCon().createStatement();
		String SQL = ("select * from employee order by convert(employee_id,signed)");
		ResultSet rs = stat.executeQuery(SQL);

		while (rs.next()) {
			try {

				employees.add(new employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		stat.close();
		con.getCon().close();

		return employees;
	}

}