package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class paymentPane extends BorderPane {

	private Connecter con = Main.con;
	TableView<purchase> purchaseTable = new TableView<purchase>();
	TableView<payment> paymentTable = new TableView<payment>();
	ObservableList<purchase> dataPurchase;
	ObservableList<payment> dataPayment;

	public BorderPane paymentclass() throws Exception {

		BorderPane root = new BorderPane();

		TableColumn<purchase, String> purchase_id = new TableColumn<purchase, String>("Purchaes ID");
		TableColumn<purchase, String> purchase_discount = new TableColumn<purchase, String>("discount");
		TableColumn<purchase, String> purchase_price = new TableColumn<purchase, String>("Agreed Price");
		TableColumn<purchase, String> purchase_date = new TableColumn<purchase, String>("Date");
		TableColumn<purchase, String> purchase_remaning = new TableColumn<purchase, String>("Remaining Amount");
		TableColumn<purchase, String> customer_id = new TableColumn<purchase, String>("Customer ID");
		TableColumn<purchase, String> car_id = new TableColumn<purchase, String>("Car ID");

		purchaseTable.getColumns().addAll(purchase_id, car_id, customer_id, purchase_date, purchase_discount,
				purchase_price, purchase_remaning);

		purchase_id.setCellValueFactory(new PropertyValueFactory<purchase, String>("purchase_id"));
		purchase_discount.setCellValueFactory(new PropertyValueFactory<purchase, String>("discount"));
		purchase_price.setCellValueFactory(new PropertyValueFactory<purchase, String>("agreed_price"));
		purchase_date.setCellValueFactory(new PropertyValueFactory<purchase, String>("purchase_date"));
		purchase_remaning.setCellValueFactory(new PropertyValueFactory<purchase, String>("remaining_amount"));
		customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
		car_id.setCellValueFactory(new PropertyValueFactory<>("car_id"));

		purchase_id.setCellFactory(TextFieldTableCell.<purchase>forTableColumn());
		purchase_discount.setCellFactory(TextFieldTableCell.<purchase>forTableColumn());
		purchase_price.setCellFactory(TextFieldTableCell.<purchase>forTableColumn());
		purchase_date.setCellFactory(TextFieldTableCell.<purchase>forTableColumn());
		purchase_remaning.setCellFactory(TextFieldTableCell.<purchase>forTableColumn());
		customer_id.setCellFactory(TextFieldTableCell.<purchase>forTableColumn());
		car_id.setCellFactory(TextFieldTableCell.<purchase>forTableColumn());

		purchaseTable.setEditable(false);

		dataPurchase = FXCollections.observableArrayList(getDataPurchase());
		purchaseTable.setItems(dataPurchase);

		TableColumn<payment, String> payment_id = new TableColumn<payment, String>("Payment ID");
		TableColumn<payment, String> payment_method = new TableColumn<payment, String>("Method");
		TableColumn<payment, String> payment_amount = new TableColumn<payment, String>("Amount");
		TableColumn<payment, String> payment_date = new TableColumn<payment, String>("Date");
		TableColumn<payment, String> payment_purchase_id = new TableColumn<payment, String>("Purchase ID");

		paymentTable.getColumns().addAll(payment_purchase_id, payment_id, payment_method, payment_date, payment_amount);

		payment_id.setCellValueFactory(new PropertyValueFactory<payment, String>("payment_id"));
		payment_method.setCellValueFactory(new PropertyValueFactory<payment, String>("payment_method"));
		payment_amount.setCellValueFactory(new PropertyValueFactory<payment, String>("amount"));
		payment_date.setCellValueFactory(new PropertyValueFactory<payment, String>("payment_date"));
		payment_purchase_id.setCellValueFactory(new PropertyValueFactory<payment, String>("purchase_id"));

		payment_id.setCellFactory(TextFieldTableCell.<payment>forTableColumn());
		payment_method.setCellFactory(TextFieldTableCell.<payment>forTableColumn());
		payment_date.setCellFactory(TextFieldTableCell.<payment>forTableColumn());
		payment_amount.setCellFactory(TextFieldTableCell.<payment>forTableColumn());
		payment_purchase_id.setCellFactory(TextFieldTableCell.<payment>forTableColumn());

		paymentTable.setEditable(false);

		dataPayment = FXCollections.observableArrayList(getDataPayment());
		paymentTable.setItems(dataPayment);

		purchaseTable.setMaxHeight(700);
		purchaseTable.setMaxWidth(710);
		purchase_id.setPrefWidth(100);
		purchase_discount.setPrefWidth(100);
		purchase_price.setPrefWidth(100);
		purchase_date.setPrefWidth(100);
		purchase_remaning.setPrefWidth(150);
		customer_id.setPrefWidth(110);
		car_id.setPrefWidth(80);
		purchaseTable.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

		paymentTable.setMaxHeight(700);
		paymentTable.setMaxWidth(480);
		payment_id.setPrefWidth(100);
		payment_method.setPrefWidth(80);
		payment_date.setPrefWidth(100);
		payment_amount.setPrefWidth(100);
		payment_purchase_id.setPrefWidth(100);
		paymentTable.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

		ImageView img = new ImageView(new Image("Search.png"));
		Button searchPurchesButton = new Button("", img);
		ComboBox searchchoicePurches = new ComboBox();
		searchchoicePurches.getItems().addAll("Purchase ID", "Car ID", "Customer ID", "Date", "Discount",
				"Agreed Price", "Remaning Amount");
		searchchoicePurches.getSelectionModel().select(0);
		TextField searchPurchesText = new TextField();
		HBox searchBoxPurches = new HBox();
		HBox searchBox1 = new HBox();
		searchBox1.getChildren().addAll(searchPurchesText, searchPurchesButton);
		searchBoxPurches.getChildren().addAll(searchBox1, searchchoicePurches);
		searchBoxPurches.setSpacing(10);
		searchPurchesButton.setStyle("-fx-background-color:white; -fx-border-color:black;");
		searchPurchesButton.setPrefSize(45, 45);
		img.setFitHeight(20);
		img.setFitWidth(20);
		Button refresh_purchase = new Button("Refresh");
		refresh_purchase.setMinWidth(150);

		searchPurchesText.setPromptText("search for a purchase");
		Label purchasetitle = new Label("Purchases");
		purchasetitle.setFont(new Font("Cambria", 15));

		ImageView img2 = new ImageView(new Image("Search.png"));
		Button paymentSearchButton = new Button("", img2);
		TextField searchpaymentText = new TextField();
		ComboBox searchchoicePayment = new ComboBox();
		searchchoicePayment.getItems().addAll("Date", "Method", "Amount");
		searchchoicePayment.getSelectionModel().select(0);
		HBox searchBoxpaymenrt = new HBox();
		HBox searchBox2 = new HBox();
		searchBox2.getChildren().addAll(searchpaymentText, paymentSearchButton);
		searchBoxpaymenrt.getChildren().addAll(searchBox2, searchchoicePayment);
		searchBoxpaymenrt.setSpacing(10);
		paymentSearchButton.setStyle("-fx-background-color:white; -fx-border-color:black;");
		paymentSearchButton.setPrefSize(45, 45);
		img2.setFitHeight(20);
		img2.setFitWidth(20);
		Button refresh_payment = new Button("Refresh");
		refresh_payment.setMinWidth(150);

		searchpaymentText.setPromptText("search for a payment");
		Label paymenttitle = new Label("Payments");
		paymenttitle.setFont(new Font("Cambria", 15));
		Button addNewPayment = new Button("Add A New Payment");
		addNewPayment.setMinWidth(150);

		VBox vPurches = new VBox();
		vPurches.getChildren().addAll(purchasetitle, searchBoxPurches, purchaseTable, refresh_purchase);
		vPurches.setSpacing(20);

		VBox vPayment = new VBox();
		HBox h = new HBox();
		h.getChildren().addAll(addNewPayment, refresh_payment);
		h.setSpacing(10);
		vPayment.getChildren().addAll(paymenttitle, searchBoxpaymenrt, paymentTable, h);
		vPayment.setSpacing(20);

		HBox vPurPayment = new HBox();
		vPurPayment.getChildren().addAll(vPurches, vPayment);
		vPurPayment.setSpacing(30);
		vPurPayment.setPadding(new Insets(40));
		vPurPayment.setAlignment(Pos.CENTER);
		root.setCenter(vPurPayment);
		root.setStyle("-fx-background-color: #FAE7E9");

		addNewPayment.setOnAction(e -> {
			if (purchaseTable.getSelectionModel().getSelectedItem() != null) {
				ObservableList<purchase> select = purchaseTable.getSelectionModel().getSelectedItems();
				ArrayList<purchase> selectrows = new ArrayList<>(select);

				double s = 0;
				if (Utils.stringToDouble(selectrows.get(0).getRemaining_amount()) == s) {
					Utils.showAlert("All Amount Has Been Paid");
				} else {
					try {
						createAddPayWindow(selectrows.get(0));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

			else {
				Utils.showAlert("Please Select A Purchase");
			}
		});

		searchPurchesButton.setOnAction(e -> {

			String s = searchchoicePurches.getSelectionModel().getSelectedItem().toString();

			ArrayList<purchase> purchaseSearch = new ArrayList<purchase>();

			if (s.equals(new String("Purchase ID"))) {
				try {
					purchaseSearch = purchase.searchPurchase("purchase_ID", searchPurchesText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Car ID"))) {
				try {
					purchaseSearch = purchase.searchPurchase("car_ID", searchPurchesText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Customer ID"))) {
				try {
					purchaseSearch = purchase.searchPurchase("customer_ID", searchPurchesText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Date"))) {
				try {
					purchaseSearch = purchase.searchPurchase("Date", searchPurchesText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Discount"))) {
				try {
					purchaseSearch = purchase.searchPurchase("Discount", searchPurchesText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Remaning Amount"))) {
				try {
					purchaseSearch = purchase.searchPurchase("Amount_Left_To_Pay", searchPurchesText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Agreed Price"))) {
				try {
					purchaseSearch = purchase.searchPurchase("Agreed_Price", searchPurchesText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			ObservableList<purchase> dataSearch = FXCollections.observableArrayList(purchaseSearch);
			purchaseTable.setItems(dataSearch);

		});

		refresh_purchase.setOnAction(e -> {

			try {
				dataPurchase = FXCollections.observableArrayList(getDataPurchase());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			purchaseTable.getSelectionModel().clearSelection();
			purchaseTable.setItems(dataPurchase);
			paymentTable.setItems(dataPayment);

		});

		purchaseTable.setOnMouseClicked(e -> {
			if (purchaseTable.getSelectionModel().getSelectedItem() != null) {

				ObservableList<purchase> select = purchaseTable.getSelectionModel().getSelectedItems();
				ArrayList<purchase> selectrows = new ArrayList<>(select);
				String pur_id = selectrows.get(0).getPurchase_id();

				ObservableList<payment> paymentSearch = null;
				try {
					paymentSearch = FXCollections.observableArrayList(getDataPayPur(pur_id));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				paymentTable.setItems(paymentSearch);
			}
		});

		paymentSearchButton.setOnAction(e -> {

			String s = searchchoicePayment.getSelectionModel().getSelectedItem().toString();

			ArrayList<payment> paymentSearch = new ArrayList<payment>();

			if (s.equals(new String("Date"))) {
				try {
					paymentSearch = payment.searchPayment("payment_date", searchpaymentText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Method"))) {
				try {
					paymentSearch = payment.searchPayment("payment_method", searchpaymentText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (s.equals(new String("Amount"))) {
				try {
					paymentSearch = payment.searchPayment("amount", searchpaymentText.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			ObservableList<payment> dataSearch = FXCollections.observableArrayList(paymentSearch);
			paymentTable.setItems(dataSearch);

		});

		refresh_payment.setOnAction(e -> {
			try {
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				dataPayment = FXCollections.observableArrayList(getDataPayment());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			paymentTable.setItems(dataPayment);
			purchaseTable.getSelectionModel().clearSelection();

		});

		return root;

	}

	// operation in purchase

	// add purchase
	public void createAddPayWindow(purchase select) throws Exception {

		customer customerInfo = customer.searchCustomer("customer_id", select.getCustomer_id()).get(0);

		Button add = new Button("Confirm Payment");

		BorderPane rootAddPay = new BorderPane();

		Text customer_info_1 = new Text();
		Text car_info_1 = new Text();
		Label paymentDateLabel = new Label("Date");
		Label paymentMethodLabel = new Label("Payment Method");
		Label RemaningAmountLabel = new Label("Remaning Amount");
		Label pricPaidLabel = new Label("Amount");

		Text customer_info_2 = new Text();
		Text car_info_2 = new Text();
		DatePicker datePicker = new DatePicker(LocalDate.of(2024, 1, 1));
		ComboBox PaymentMethodBox = new ComboBox();
		PaymentMethodBox.getItems().addAll("CASH", "CHECK");
		PaymentMethodBox.getSelectionModel().select(0);
		Text RemaningAmountText = new Text(select.getRemaining_amount());
		TextField paymentAmountText = new TextField();
		Label done = new Label();
		datePicker.getEditor().setDisable(true);
		datePicker.setConverter(Utils.dateFormatter());

		Separator s = new Separator();
		Label newPayment = new Label("New Payment");
		newPayment.setFont(Font.font("Elephant", FontWeight.BOLD, 30));
		Label details = new Label("Purchase Details");
		details.setFont(Font.font("Elephant", FontWeight.BOLD, 30));

		GridPane PaneAddPay = new GridPane();

		PaneAddPay.add(details, 0, 0);
		PaneAddPay.add(customer_info_1, 0, 1);
		PaneAddPay.add(car_info_1, 0, 2);
		PaneAddPay.add(s, 0, 4);
		PaneAddPay.add(RemaningAmountLabel, 0, 3);
		PaneAddPay.add(newPayment, 0, 5);
		PaneAddPay.add(paymentMethodLabel, 0, 6);
		PaneAddPay.add(pricPaidLabel, 0, 7);
		PaneAddPay.add(paymentDateLabel, 0, 8);
		PaneAddPay.add(add, 0, 9);

		GridPane.setColumnSpan(details, 2);
		PaneAddPay.add(customer_info_2, 1, 1);
		PaneAddPay.add(car_info_2, 1, 2);
		GridPane.setColumnSpan(s, 2);
		PaneAddPay.add(RemaningAmountText, 1, 3);
		GridPane.setColumnSpan(newPayment, 2);
		PaneAddPay.add(PaymentMethodBox, 1, 6);
		PaneAddPay.add(paymentAmountText, 1, 7);
		PaneAddPay.add(datePicker, 1, 8);
		GridPane.setColumnSpan(add, 2);

		add.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		details.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		newPayment.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		PaymentMethodBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		details.setAlignment(Pos.CENTER);
		newPayment.setAlignment(Pos.CENTER);

		PaneAddPay.setVgap(20);
		PaneAddPay.setHgap(35);
		PaneAddPay.setAlignment(Pos.CENTER);

		GridPane.setColumnSpan(s, 2);

		details.setStyle("-fx-background-color: #FAE7E9");
		PaneAddPay.setStyle("-fx-background-color: #FAE7E9");

		rootAddPay.setCenter(PaneAddPay);

		String c = select.getCar_id();
		ArrayList<Car> car = purchase.serchPurCar("car_id", c);
		Car carSelect = car.get(0);

		customer_info_1.setText("Customer ID" + "\nCustomer Name" + "\nCustomer Email" + "\nCustomer Phone"
				+ "\nCustomer Adress" + "\nCustomer Licence Number");

		customer_info_2.setText(customerInfo.getCustomer_id() + "\n" + customerInfo.customer_name + "\n"
				+ customerInfo.getCustomer__email() + "\n" + customerInfo.getCustomer__phone() + "\n"
				+ customerInfo.getCustomer_address() + "\n" + customerInfo.getCustomer_license_number());

		car_info_1.setText(
				"Company" + "\nCar ID" + "\nCar Model" + "\nCar Color" + "\nCar Year" + "\nCar VIN" + "\nCar Mileage");

		car_info_2.setText(carSelect.getCompany() + "\n" + carSelect.getCar_id() + "\n" + carSelect.getModel() + "\n"
				+ carSelect.getColor() + "\n" + carSelect.getYear() + "\n" + carSelect.getVIN() + "\n"
				+ carSelect.getMileage());

		add.setOnAction(e -> {
			double m = 0;
			if (Utils.stringToDouble(select.getRemaining_amount()) == m)
				Utils.showAlert("All Amount Has Been Paid");

			else if (Utils.stringToDouble(paymentAmountText.getText()) > Utils
					.stringToDouble(RemaningAmountText.getText())) {
				Utils.showAlert("Ths Amount Paid Is More Than The Remaning Amount");
			}

			else {
				payment p = null;
				try {
					p = new payment(Utils.maxId("payment"), datePicker.getValue().toString(),
							paymentAmountText.getText(), select.purchase_id, PaymentMethodBox.getValue().toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					p.addPayment();
					paymentTable.setItems(FXCollections.observableArrayList(getDataPayPur(select.purchase_id)));

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				double remain = Utils.stringToDouble(select.remaining_amount)
						- Utils.stringToDouble(paymentAmountText.getText());
				select.setRemaining_amount(Utils.doubleToString(remain));

				RemaningAmountText.setText(Utils.doubleToString(remain));
				try {
					select.updatePurchase();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					dataPurchase = FXCollections.observableArrayList(getDataPurchase());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				purchaseTable.setItems(dataPurchase);

			}

		});

		Stage stage = new Stage();
		Scene scene = new Scene(rootAddPay, 650, 750);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public ArrayList<purchase> getDataPurchase() throws Exception {

		ArrayList<purchase> purchase = new ArrayList<purchase>();
		con.connectDB();

		Statement stat = con.getCon().createStatement();
		String SQL = ("select * from purchase order by convert(purchase_ID,signed)");
		ResultSet rs = stat.executeQuery(SQL);

		while (rs.next()) {
			try {

				purchase.add(new purchase(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		stat.close();
		con.getCon().close();

		return purchase;
	}

	public ArrayList<payment> getDataPayment() throws Exception {
		ArrayList<payment> payments = new ArrayList<payment>();

		try {
			con.connectDB();

			Statement stat = con.getCon().createStatement();
			String SQL = "select * from payment order by convert(purchase_ID, signed)";
			ResultSet rs = stat.executeQuery(SQL);

			while (rs.next()) {
				try {
					payments.add(new payment(rs.getString(1), // payment_id
							rs.getString(2), // payment_date
							rs.getString(3), // amount
							rs.getString(4), // purchase_id
							rs.getString(5) // payment_method
					));

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			rs.close();
			stat.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (con != null && con.getCon() != null) {
				con.getCon().close();
			}
		}

		return payments;
	}

	public ArrayList<payment> getDataPayPur(String pur_id) throws Exception {

		ArrayList<payment> payment = new ArrayList<payment>();
		con.connectDB();

		Statement stat = con.getCon().createStatement();
		String SQL = ("select * from payment where ( purchase_ID = '" + pur_id
				+ "') order by convert(payment_date,signed) ;" + " ");
		ResultSet rs = stat.executeQuery(SQL);

		while (rs.next()) {
			try {

				payment.add(new payment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		rs.close();
		stat.close();
		con.getCon().close();
		return payment;
	}

}