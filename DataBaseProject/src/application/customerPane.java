package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class customerPane extends BorderPane {
	private Connecter con = Main.con;
	private TableView<customer> customerTable = new TableView<customer>();
	private ObservableList<customer> data;

	public BorderPane customerclass() throws Exception {

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #FAE7E9");

		// the table view for customer

		TableColumn<customer, String> customer_id = new TableColumn<customer, String>("ID");
		TableColumn<customer, String> customer_name = new TableColumn<customer, String>("Name");
		TableColumn<customer, String> customer_email = new TableColumn<customer, String>("Email");
		TableColumn<customer, String> customer_date = new TableColumn<customer, String>("Date Of Birth");
		TableColumn<customer, String> customer_phone = new TableColumn<customer, String>("phone");
		TableColumn<customer, String> customer_age = new TableColumn<customer, String>("Age");
		TableColumn<customer, String> customer_address = new TableColumn<customer, String>("Address");
		TableColumn<customer, String> customer_license_number = new TableColumn<customer, String>("License Number");

		customerTable.getColumns().addAll(customer_id, customer_name, customer_date, customer_age, customer_address,
				customer_email, customer_phone, customer_license_number);

		customer_id.setCellValueFactory(new PropertyValueFactory<customer, String>("customer_id"));
		customer_name.setCellValueFactory(new PropertyValueFactory<customer, String>("customer_name"));
		customer_email.setCellValueFactory(new PropertyValueFactory<customer, String>("customer__email"));
		customer_date.setCellValueFactory(new PropertyValueFactory<customer, String>("customer_date_birth"));
		customer_phone.setCellValueFactory(new PropertyValueFactory<customer, String>("customer__phone"));
		customer_age.setCellValueFactory(new PropertyValueFactory<customer, String>("customer_age"));
		customer_license_number
				.setCellValueFactory(new PropertyValueFactory<customer, String>("customer_license_number"));
		customer_address.setCellValueFactory(new PropertyValueFactory<customer, String>("customer_address"));

		customer_id.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		customer_name.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		customer_email.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		customer_phone.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		customer_date.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		customer_age.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		customer_address.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		customer_license_number.setCellFactory(TextFieldTableCell.<customer>forTableColumn());

		data = FXCollections.observableArrayList(getData());
		customerTable.setItems(data);

		customer_id.setEditable(false);
		customer_name.setEditable(true);
		customer_date.setEditable(false);
		customer_email.setEditable(true);
		customer_phone.setEditable(true);
		customer_age.setEditable(false);
		customer_license_number.setEditable(true);
		customer_address.setEditable(true);
		customerTable.setEditable(true);

		customer_name.setOnEditCommit((CellEditEvent<customer, String> t) -> {
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomer_name(t.getNewValue()); // display

			customer obj = new customer(t.getRowValue().getCustomer_id(), t.getNewValue(),
					t.getRowValue().getCustomer__email(), t.getRowValue().getCustomer__phone(),
					t.getRowValue().getCustomer_age(), t.getRowValue().getCustomer_address(),
					t.getRowValue().getCustomer_date_birth(), t.getRowValue().getCustomer_license_number());
			try {
				obj.updateCustomer();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		customer_email.setOnEditCommit((CellEditEvent<customer, String> t) -> {
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomer__email(t.getNewValue()); // display

			customer obj = new customer(t.getRowValue().getCustomer_id(), t.getRowValue().getCustomer_name(),
					t.getNewValue(), t.getRowValue().getCustomer__phone(), t.getRowValue().getCustomer_age(),
					t.getRowValue().getCustomer_address(), t.getRowValue().getCustomer_date_birth(),
					t.getRowValue().getCustomer_license_number());
			try {
				obj.updateCustomer();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		customer_phone.setOnEditCommit((CellEditEvent<customer, String> t) -> {
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomer__phone(t.getNewValue()); // display

			customer obj = new customer(t.getRowValue().getCustomer_id(), t.getRowValue().getCustomer_name(),
					t.getRowValue().getCustomer__email(), t.getNewValue(), t.getRowValue().getCustomer_age(),
					t.getRowValue().getCustomer_address(), t.getRowValue().getCustomer_date_birth(),
					t.getRowValue().getCustomer_license_number());
			try {
				obj.updateCustomer();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		customer_address.setOnEditCommit((CellEditEvent<customer, String> t) -> {
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomer_address(t.getNewValue()); // display

			customer obj = new customer(t.getRowValue().getCustomer_id(), t.getRowValue().getCustomer_name(),
					t.getRowValue().getCustomer__email(), t.getRowValue().getCustomer__phone(),
					t.getRowValue().getCustomer_age(), t.getNewValue(), t.getRowValue().getCustomer_date_birth(),
					t.getRowValue().getCustomer_license_number());
			try {
				obj.updateCustomer();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		customer_license_number.setOnEditCommit((CellEditEvent<customer, String> t) -> {
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomer_license_number(t.getNewValue()); // display

			customer obj = new customer(t.getRowValue().getCustomer_id(), t.getRowValue().getCustomer_name(),
					t.getRowValue().getCustomer__email(), t.getRowValue().getCustomer__phone(),
					t.getRowValue().getCustomer_age(), t.getRowValue().getCustomer_address(),
					t.getRowValue().getCustomer_date_birth(), t.getNewValue());
			try {
				obj.updateCustomer();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		// Design Button
		TextField searchTextcustomer = new TextField();
		searchTextcustomer.setPromptText("search for a Customer");
		ComboBox searchchoiceCustomer = new ComboBox();
		searchchoiceCustomer.setMinWidth(150);

		searchchoiceCustomer.getItems().addAll("ID", "Name", "Email", "Date of Birth", "Age", "Adress", "Phone",
				"License Number");
		searchchoiceCustomer.getSelectionModel().select(0);
		ImageView img = new ImageView(new Image("Search.png"));
		Button customerSearchButton = new Button("", img);
//			customerSearchButton.setMinWidth(150);
		HBox searchBoxcustomer = new HBox();
		HBox searchBox = new HBox();
		searchBox.getChildren().addAll(searchTextcustomer, customerSearchButton);
		searchBoxcustomer.getChildren().addAll(searchBox, searchchoiceCustomer);
		searchBoxcustomer.setSpacing(10);
		customerSearchButton.setStyle("-fx-background-color:white; -fx-border-color:black;");
		customerSearchButton.setPrefSize(45, 45);
		img.setFitHeight(20);
		img.setFitWidth(20);

		Button customerAddButton = new Button("Add");
		Button customerDeleteButton = new Button("Delete");
		customerAddButton.setMinWidth(150);
		customerDeleteButton.setMinWidth(150);
		Button customerRefreshButton = new Button("Refresh");
		customerRefreshButton.setMinWidth(150);
		HBox addDeleteBox = new HBox();
		addDeleteBox.getChildren().addAll(customerAddButton, customerDeleteButton, customerRefreshButton);
		addDeleteBox.setSpacing(10);

		VBox vcustomer = new VBox();
		vcustomer.getChildren().addAll(searchBoxcustomer, customerTable, addDeleteBox);
		vcustomer.setSpacing(20);
		vcustomer.setPadding(new Insets(40));
		root.setCenter(vcustomer);

		customerTable.setMaxHeight(700);
		customerTable.setMaxWidth(1300);
		customer_name.setPrefWidth(150);
		customer_date.setPrefWidth(150);
		customer_email.setPrefWidth(200);
		customer_phone.setPrefWidth(150);
		customer_id.setPrefWidth(150);
		customer_age.setPrefWidth(70);
		customer_address.setPrefWidth(200);
		customer_license_number.setPrefWidth(130);
		customerTable.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

		customerSearchButton.setOnAction(e -> {

			String s = searchchoiceCustomer.getSelectionModel().getSelectedItem().toString();

			ArrayList<customer> customerSearch = new ArrayList<customer>();

			if (s.equals(new String("ID"))) {
				try {
					customerSearch = customer.searchCustomer("customer_id", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Name"))) {
				try {
					customerSearch = customer.searchCustomer("Full_Name", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Email"))) {
				try {
					customerSearch = customer.searchCustomer("Email", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Date of Birth"))) {
				try {
					customerSearch = customer.searchCustomer("Date_Of_Birth", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Age"))) {
				try {
					customerSearch = customer.searchCustomer("Age", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else if (s.equals(new String("Adress"))) {
				try {
					customerSearch = customer.searchCustomer("Adress", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Phone"))) {
				try {
					customerSearch = customer.searchCustomer("Phone", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else if (s.equals(new String("License Number"))) {
				try {
					customerSearch = customer.searchCustomer("License_Number", searchTextcustomer.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			ObservableList<customer> dataSearch = FXCollections.observableArrayList(customerSearch);
			customerTable.setItems(dataSearch);

		});

		customerAddButton.setOnAction(e -> {

			customerTable.getSelectionModel().clearSelection();

			try {
				createAddcustomerWindow();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		customerDeleteButton.setOnAction(e -> {

			ObservableList selectedRow = customerTable.getSelectionModel().getSelectedItems();

			ObservableList<customer> selectedRows = customerTable.getSelectionModel().getSelectedItems();
			ArrayList<customer> rows = new ArrayList<>(selectedRows);

			if (customerTable.getSelectionModel().getSelectedItem() != null) {
				rows.forEach(row -> {
					try {
						row.deleteCustomer();

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					customerTable.getItems().remove(row);
				});
			}

			else {
				Utils.showAlert("Please Seleact A Row");

			}

			customerTable.getSelectionModel().clearSelection();

		});

		customerRefreshButton.setOnAction(e -> {

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
			customerTable.setItems(data);
			customerTable.getSelectionModel().clearSelection();

		});

		return root;

	}

	// operation in customer

	// add customer
	public void createAddcustomerWindow() throws Exception {

		Button add = new Button("Add");

		BorderPane rootAddcustomer = new BorderPane();
		Label title = new Label("\nAdd A Customer");
		title.setFont(Font.font("Elephant", FontWeight.BOLD, 30));

		rootAddcustomer.setTop(title);
		title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		title.setAlignment(Pos.BOTTOM_CENTER);

		Label customerIdLabel = new Label("Customer Id");
		Label customerNameLabel = new Label("Customer Name");
		Label customerEmailLabel = new Label("Customer Email");
		Label customerDateLabel = new Label("Customer Date of Birth");
		Label customerPhoneLabel = new Label("Customer Phone");
		Label customerPhoneLisenceLabel = new Label("Customer License Number");
		Label customerAgeLabel = new Label("Customer Age");
		Label customerAddressLabel = new Label("Customer Address");

		TextField customerIdText = new TextField();
		TextField customerNameText = new TextField();
		TextField customerEmailText = new TextField();
		DatePicker date = new DatePicker(LocalDate.of(1980, 1, 1));
		TextField customerPhoneText = new TextField();
		TextField customerPhoneLisenceText = new TextField();
		TextField customerAgeText = new TextField();
		TextField customerAddressText = new TextField();
		date.getEditor().setDisable(true);
		date.setConverter(Utils.dateFormatter());

		GridPane PaneAddcustomer = new GridPane();
		PaneAddcustomer.add(customerIdLabel, 0, 0);
		PaneAddcustomer.add(customerNameLabel, 0, 1);
		PaneAddcustomer.add(customerDateLabel, 0, 2);
		PaneAddcustomer.add(customerAgeLabel, 0, 3);
		PaneAddcustomer.add(customerAddressLabel, 0, 4);
		PaneAddcustomer.add(customerEmailLabel, 0, 5);
		PaneAddcustomer.add(customerPhoneLabel, 0, 6);
		PaneAddcustomer.add(customerPhoneLisenceLabel, 0, 7);

		PaneAddcustomer.add(customerIdText, 1, 0);
		PaneAddcustomer.add(customerNameText, 1, 1);
		PaneAddcustomer.add(date, 1, 2);
		PaneAddcustomer.add(customerAgeText, 1, 3);
		PaneAddcustomer.add(customerAddressText, 1, 4);
		PaneAddcustomer.add(customerEmailText, 1, 5);
		PaneAddcustomer.add(customerPhoneText, 1, 6);
		PaneAddcustomer.add(customerPhoneLisenceText, 1, 7);

		date.setOnMouseEntered(e -> {
			int age = 2024 - date.getValue().getYear();
			customerAgeText.setText(Utils.convertIntegerToString(age));

		});

		customerIdText.setText(Utils.maxId("customer"));
		customerIdText.setEditable(false);
		customerAgeText.setEditable(false);

		PaneAddcustomer.add(add, 0, 8);
		GridPane.setColumnSpan(add, 2);
		add.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		PaneAddcustomer.setVgap(10);
		PaneAddcustomer.setHgap(35);
		PaneAddcustomer.setAlignment(Pos.CENTER);

		rootAddcustomer.setCenter(PaneAddcustomer);
		rootAddcustomer.setStyle("-fx-background-color: #FAE7E9");

		add.setOnAction(e -> {
			customer obj = new customer(customerIdText.getText(), customerNameText.getText(),
					customerEmailText.getText(), customerPhoneText.getText(), customerAgeText.getText(),
					customerAddressText.getText(), date.getValue().toString(), customerPhoneLisenceText.getText());
			try {
				obj.addCustomer();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				data = FXCollections.observableArrayList(getData());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			customerTable.setItems(data);

			try {
				customerIdText.setText(Utils.maxId("customer"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			customerNameText.setText("");
			customerEmailText.setText("");
			customerPhoneText.setText("");
			customerPhoneLisenceText.setText("");
			customerAddressText.setText("");
			date.setValue(LocalDate.of(1980, 1, 1));

		});

		Stage stage = new Stage();
		Scene scene = new Scene(rootAddcustomer, 700, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

	// delete customer

	public ArrayList<customer> getData() throws Exception {

		ArrayList<customer> customers = new ArrayList<customer>();
		con.connectDB();

		Statement stat = con.getCon().createStatement();
		String SQL = ("select * from customer order by convert(customer_id,signed)");
		ResultSet rs = stat.executeQuery(SQL);
//			new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(4))

		while (rs.next()) {
			try {

				customers.add(new customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		stat.close();
		con.getCon().close();

		return customers;
	}

}