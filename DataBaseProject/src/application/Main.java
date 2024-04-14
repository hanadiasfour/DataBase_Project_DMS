package application;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Connecter con = new Connecter();
	private CompanyPane companyPane = new CompanyPane();
	private CarPane carPane = new CarPane();
	private TabPane rootPane = new TabPane();
	private StatPane statPane = new StatPane();
	private employeePane employeePane = new employeePane();
	private customerPane customerPane = new customerPane();
	private paymentPane paymentPane = new paymentPane();
	private PurchesPane purchesPane = new PurchesPane();
	private AppointmentPane appoitmentPane = new AppointmentPane();

	@Override
	public void start(Stage primaryStage) throws SQLException {

		Tab statTab = new Tab("STATISTICS");
		Tab companyTab = new Tab("COMPANIES");
		Tab carTab = new Tab("CARS");
		Tab empTab = new Tab("EMPLOYEES");
		Tab customerTab = new Tab("CUSTOMERS");
		Tab purchaseTab = new Tab("PURCHASE");
		Tab paymentTab = new Tab("PAYMENTS");
		Tab appTab = new Tab("APPOINTMENTS");

		ImageView companyimg = new ImageView(new Image("company.png"));
		ImageView carimg = new ImageView(new Image("car.png"));
		ImageView statimg = new ImageView(new Image("stat.png"));
		ImageView empimg = new ImageView(new Image("employeeTab.png"));
		ImageView cusimg = new ImageView(new Image("customerTab.png"));
		ImageView purimg = new ImageView(new Image("purchaseTab.png"));
		ImageView payimg = new ImageView(new Image("paymentTab.png"));
		ImageView appimg = new ImageView(new Image("appointmentTab.png"));

		statimg.setFitHeight(25);
		statimg.setFitWidth(25);
		companyimg.setFitHeight(20);
		companyimg.setFitWidth(20);
		carimg.setFitHeight(25);
		carimg.setFitWidth(25);
		empimg.setFitHeight(25);
		empimg.setFitWidth(25);
		cusimg.setFitHeight(20);
		cusimg.setFitWidth(20);
		purimg.setFitHeight(25);
		purimg.setFitWidth(25);
		payimg.setFitHeight(25);
		payimg.setFitWidth(25);
		appimg.setFitHeight(20);
		appimg.setFitWidth(20);
		carTab.setGraphic(carimg);
		carTab.setContent(carPane);
		purchaseTab.setGraphic(purimg);
		purchaseTab.setContent(purchesPane);
		empTab.setGraphic(empimg);
		empTab.setContent(paymentPane);
		paymentTab.setGraphic(payimg);
		paymentTab.setContent(employeePane);
		customerTab.setGraphic(cusimg);
		customerTab.setContent(customerPane);
		companyTab.setGraphic(companyimg);
		companyTab.setContent(companyPane);
		appTab.setContent(appoitmentPane);
		appTab.setGraphic(appimg);
		statTab.setGraphic(statimg);
		statTab.setContent(statPane);
		try {
			empTab.setContent(employeePane.employeeclass());
			customerTab.setContent(customerPane.customerclass());
			paymentTab.setContent(paymentPane.paymentclass());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rootPane.getTabs().addAll(statTab, carTab, companyTab, empTab, customerTab, purchaseTab, paymentTab, appTab);

		// filling the observable list in the car class with database information
		try {
			Car.getData();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Company.getData();
		statPane.setInfo();
		companyPane.getTable().setItems(Company.companyList);
		carPane.getCompany().setItems(Company.nameList);
		carPane.getCompanyInAdd().setItems(Company.nameList);
		carPane.getTable().setItems(Car.carList);
		carPane.getColor().setItems(Car.colorList);
		carPane.getPrice().setItems(Car.priceList);
		carPane.getStatus().setItems(Car.statusList);
		carPane.getYear().setItems(Car.yearList);

		carPane.getPurchase().setOnAction(e -> {

			ObservableList<Car> selectedRows = carPane.getTable().getSelectionModel().getSelectedItems();
			if (selectedRows.isEmpty()) {

				showAlert("Select a Car to Purchase");

			} else {
				rootPane.getSelectionModel().select(purchaseTab);
				purchesPane.getCarIdBox().setText(carPane.getTable().getSelectionModel().getSelectedItem().getCar_id());
				purchesPane.getCarIdBox().fireEvent(e);
			}

		});

		paymentTab.setOnSelectionChanged((EventHandler<Event>) t -> {
			if (paymentTab.isSelected()) {
				try {
					paymentPane.purchaseTable.getSelectionModel().clearSelection();
					paymentPane.paymentTable.getSelectionModel().clearSelection();
					paymentPane.dataPurchase = FXCollections.observableArrayList(paymentPane.getDataPurchase());
					paymentPane.dataPayment = FXCollections.observableArrayList(paymentPane.getDataPayment());
					paymentPane.purchaseTable.setItems(paymentPane.dataPurchase);
					paymentPane.paymentTable.setItems(paymentPane.dataPayment);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		appoitmentPane.getAddAppoitment().setOnAction(e -> {
			AddAppointment add = new AddAppointment();
			Scene Scene = new Scene(add);
			Stage stage = new Stage();
			stage.setScene(Scene);
			Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setMaxHeight(500);
			stage.setMaxWidth(800);
			stage.show();
		});

		companyPane.getAdd().setOnAction(e -> {
			Scene addingScene = new Scene(companyPane.getCompanyAdder(), 600, 500);
			addingScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage addingStage = new Stage();
			addingStage.setScene(addingScene);
			addingStage.show();
			addingStage.setTitle("NEW COMPANY");
		});

		carPane.getAdd().setOnAction(e -> {
			Scene addingScene = new Scene(carPane.getCarAdder(), 800, 600);
			addingScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage addingStage = new Stage();
			addingStage.setScene(addingScene);
			addingStage.show();
			addingStage.setTitle("NEW CAR");

		});

		carPane.getApply().setOnAction(e -> {// applying selected filters to cars in the SLL O(n)

			try {

				carPane.getTable()
						.setItems(Car.getSpecificData(carPane.getColor().getValue(), carPane.getStatus().getValue(),
								carPane.getPrice().getValue(), carPane.getYear().getValue(),
								carPane.getCompany().getValue()));

			} catch (SQLException | ClassNotFoundException g) {

			}
			// getting the selected values form the combo box

		});

		carPane.getClear().setOnAction(e -> {

			Car.carList = FXCollections.observableArrayList();
			try {
				Car.getData();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			carPane.getTable().setItems(Car.carList);

			carPane.getColor().setValue(null);
			carPane.getPrice().setValue(null);
			carPane.getStatus().setValue(null);
			carPane.getYear().setValue(null);
			carPane.getCompany().setValue(null);

			carPane.getTable().refresh();

		});

		carPane.getRemove().setOnAction(e -> {

			ObservableList<Car> selectedRows = carPane.getTable().getSelectionModel().getSelectedItems();

			if (selectedRows.isEmpty()) {

				showAlert("Select a Car to Delete");

			} else {

				ArrayList<Car> rows = new ArrayList<>(selectedRows);

				rows.forEach(row -> {
					Car.carList.remove(row);
					Car.deleteRow(row);
					carPane.getTable().refresh();
				});
			}

		});

		carPane.getAddCar().setOnAction(e -> {

			String company = carPane.getCompanyInAdd().getSelectionModel().getSelectedItem();
			String color = carPane.getColortxf().getText();
			String year = carPane.getYeartxf().getText().trim();
			String vin = carPane.getVINtxf().getText();
			String mile = carPane.getMileagetxf().getText();
			String price = carPane.getPricetxf().getText();
			String model = carPane.getModeltxf().getText();
			String carID = carPane.getIDtxf().getText();

			if (company == null || carID.isBlank()) {
				showAlert("Cannot leave Car-ID or Company empty.");
			} else {
				try {

					if (!year.isBlank() && !isPositiveInt(year)) {
						showAlert("Invalid Year Input.");
						throw new NumberFormatException();
					} else if (year.isBlank())
						year = null;

					if (!vin.isBlank() && !isPositiveInt(vin)) {
						showAlert("Invalid VIN Input: Must consist of numbers only");
						throw new NumberFormatException();
					} else if (vin.isBlank())
						vin = null;

					if (!mile.isBlank() && !isPositiveInt(mile)) {
						showAlert("Invalid Mileage Input: Must consist of numbers only");
						throw new NumberFormatException();
					} else if (mile.isBlank())
						mile = null;

					if (!price.isBlank() && !isPositiveInt(price)) {
						showAlert("Invalid Price Input: Must consist of numbers only");
						throw new NumberFormatException();
					} else if (price.isBlank())
						price = null;

					if (color.isBlank())
						color = null;

					if (model.isBlank())
						model = null;

					Car.addCar(company, color, year, vin, mile, price, model, carID);

					if (!Car.colorList.contains(color)) {

						Car.colorList.add(color);

					}

				} catch (NumberFormatException k) {
					System.out.println("caught");

				}

			}

		});

		companyPane.getSearch().setOnAction(e -> {

			try {

				companyPane.getTable().setItems(Company.getSpecificData(companyPane.getSearchtxf().getText()));

			} catch (SQLException | ClassNotFoundException g) {

			}

		});

		companyPane.getRefresh().setOnAction(e -> {

			companyPane.getSearchtxf().setText("");
			Company.companyList = FXCollections.observableArrayList();

			Company.getData();
			companyPane.getTable().setItems(Company.companyList);

			companyPane.getTable().refresh();

		});

		companyPane.getRemove().setOnAction(e -> {

			ObservableList<Company> selectedRows = companyPane.getTable().getSelectionModel().getSelectedItems();

			if (selectedRows.isEmpty()) {

				showAlert("Select a Company to Delete");

			} else {

				ArrayList<Company> rows = new ArrayList<>(selectedRows);

				rows.forEach(row -> {
					Company.nameList.remove(row.getName());
					Company.companyList.remove(row);
					Company.deleteRow(row);
					carPane.getTable().refresh();
				});
			}

		});

		companyPane.getAddComp().setOnAction(e -> {

			String name = companyPane.getNametxf().getText();
			String country = companyPane.getCountrytxf().getText();

			if (name.isBlank()) {
				showAlert("Cannot leave Company name empty.");
			} else {
				try {
					if (country.isBlank())
						country = null;

					Company.addCompany(name, country);

				} catch (NumberFormatException k) {
					System.out.println("caught");

				}

			}

		});
		statPane.getRefresh().setOnAction(e -> {

			statPane.setInfo();

		});

		// opening the front page
		BorderPane root = new BorderPane();
		root.setCenter(rootPane);
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);

	}

	public static void main(String[] args) {
		launch(args);
	}

	// checks if the given string contains a positive integer
	public boolean isPositiveInt(String s) {

		try {

			int i = Integer.parseInt(s);
			if (i <= 0)
				throw new NumberFormatException();

			return true;

		} catch (NumberFormatException e) {
			return false;

		}

	}

	// alert used to warn users
	public void showAlert(String message) {

		Alert informationAlert = new Alert(Alert.AlertType.ERROR);
		informationAlert.setTitle("Information");
		informationAlert.setHeaderText(null);
		informationAlert.setContentText(message);
		informationAlert.showAndWait();

	}

}
