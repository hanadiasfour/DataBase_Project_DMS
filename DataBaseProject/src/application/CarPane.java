package application;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class CarPane extends GridPane {

	private ComboBox<String> company, color, year, price, status, companyInAdd;
	private Button add, remove, clear, purchase, addCar, apply;
	private TextField IDtxf, colortxf, yeartxf, mileagetxf, VINtxf, modeltxf, pricetxf;
	private TableView<Car> table;
	private Connecter con = Main.con;

	public CarPane() {

		company = new ComboBox<String>();
		color = new ComboBox<String>();
		year = new ComboBox<String>();
		price = new ComboBox<String>();
		status = new ComboBox<String>();
		companyInAdd = new ComboBox<String>();
		add = new Button("Add New Car");
		remove = new Button("Remove Selected Car");
		clear = new Button("Refresh");
		apply = new Button("Apply");

		IDtxf = new TextField();
		colortxf = new TextField();
		yeartxf = new TextField();
		mileagetxf = new TextField();
		VINtxf = new TextField();
		modeltxf = new TextField();
		pricetxf = new TextField();
		addCar = new Button("Add Car");

		purchase = new Button("Make Purchase");
		table = new TableView<Car>();
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		apply.getStyleClass().add("my-button");
		add.getStyleClass().add("my-button");
		remove.getStyleClass().add("my-button");
		clear.getStyleClass().add("my-button");
		purchase.getStyleClass().add("my-button");

		table.setPrefSize(1350, 500);
		company.setPrefSize(150, 30);
		color.setPrefSize(150, 30);
		year.setPrefSize(150, 30);
		price.setPrefSize(150, 30);
		status.setPrefSize(150, 30);

		Label companylbl = new Label("Company");
		Label colorlbl = new Label("Color   ");
		Label yearlbl = new Label("Max Year");
		Label pricelbl = new Label("Budget ");
		Label statuslbl = new Label("Status");

		companylbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		yearlbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		colorlbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		statuslbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		pricelbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

		HBox filterTitles = new HBox(90);
		filterTitles.getChildren().addAll(companylbl, yearlbl, colorlbl, statuslbl, pricelbl);

		HBox filters = new HBox(15);
		filters.getChildren().addAll(company, year, color, status, price, apply);

		HBox control = new HBox(25);
		control.getChildren().addAll(add, remove);

		add(filterTitles, 0, 0);
		add(filters, 0, 1);
		add(table, 0, 2);
		add(control, 0, 3);
		add(clear, 1, 1);
		add(purchase, 1, 3);
		setHgap(-150);
		setVgap(20);
		setPadding(new Insets(50, 10, 10, 50));
		setStyle("-fx-background-color:#FAE7E9;");

		setColumns();

	}

	public BorderPane getCarAdder() {

		Label id = new Label("Car ID");
		Label color = new Label("Color");
		Label year = new Label("Year");
		Label mileage = new Label("Mileage");
		Label vin = new Label("VIN");
		Label model = new Label("Model");
		Label price = new Label("Price");
		Label company = new Label("Company");
		Label heading = new Label("New Car");

		id.getStyleClass().add("my-label");
		color.getStyleClass().add("my-label");
		year.getStyleClass().add("my-label");
		mileage.getStyleClass().add("my-label");
		vin.getStyleClass().add("my-label");
		model.getStyleClass().add("my-label");
		price.getStyleClass().add("my-label");
		company.getStyleClass().add("my-label");
		addCar.getStyleClass().add("my-button");
		heading.setFont(Font.font("Elephant", FontWeight.BOLD, 35));

		companyInAdd.setPrefSize(255, 30);
		companyInAdd.setStyle("-fx-background-color:white;");
		IDtxf.setPromptText("Car ID");
		colortxf.setPromptText("Color");
		yeartxf.setPromptText("Year");
		mileagetxf.setPromptText("Mileage");
		VINtxf.setPromptText("VIN");
		modeltxf.setPromptText("Model");
		pricetxf.setPromptText("Price");

		GridPane controls = new GridPane();
		controls.add(company, 0, 0);
		controls.add(companyInAdd, 1, 0);
		controls.add(id, 3, 0);
		controls.add(IDtxf, 4, 0);

		controls.add(color, 0, 1);
		controls.add(colortxf, 1, 1);
		controls.add(year, 3, 1);
		controls.add(yeartxf, 4, 1);

		controls.add(mileage, 0, 2);
		controls.add(mileagetxf, 1, 2);
		controls.add(vin, 3, 2);
		controls.add(VINtxf, 4, 2);

		controls.add(price, 0, 3);
		controls.add(pricetxf, 1, 3);
		controls.add(model, 3, 3);
		controls.add(modeltxf, 4, 3);

		controls.add(new Text("           "), 2, 0);
		controls.add(new Text("           "), 2, 1);
		controls.add(new Text("           "), 2, 2);
		controls.add(new Text("           "), 2, 3);

		controls.setHgap(15);
		controls.setVgap(15);
		controls.setAlignment(Pos.CENTER);

		ImageView img = new ImageView(new Image("addCar.png"));
		img.setFitHeight(40);
		img.setFitWidth(40);

		HBox top = new HBox(15);
		top.getChildren().addAll(img, heading);
		top.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setTop(top);
		root.setCenter(controls);
		root.setBottom(addCar);
		root.setAlignment(top, Pos.CENTER);
		root.setAlignment(addCar, Pos.CENTER);
		root.setPadding(new Insets(50, 10, 50, 10));
		root.setStyle("-fx-background-color:#FAE7E9;");
		return root;

	}

	private void setColumns() {

		// setting columns
		TableColumn<Car, String> car_idc = new TableColumn<Car, String>("Car_ID");
		TableColumn<Car, String> colorc = new TableColumn<Car, String>("Color");
		TableColumn<Car, String> yearc = new TableColumn<Car, String>("Year");
		TableColumn<Car, String> vinc = new TableColumn<Car, String>("VIN");
		TableColumn<Car, String> modelc = new TableColumn<Car, String>("Model");
		TableColumn<Car, String> statusc = new TableColumn<Car, String>("Status");
		TableColumn<Car, String> pricec = new TableColumn<Car, String>("Price");
		TableColumn<Car, String> mileagec = new TableColumn<Car, String>("Mileage");
		TableColumn<Car, String> companyc = new TableColumn<Car, String>("Company");

		car_idc.setCellValueFactory(new PropertyValueFactory<Car, String>("car_id"));
		colorc.setCellValueFactory(new PropertyValueFactory<Car, String>("color"));
		yearc.setCellValueFactory(new PropertyValueFactory<Car, String>("year"));
		vinc.setCellValueFactory(new PropertyValueFactory<Car, String>("VIN"));
		modelc.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
		statusc.setCellValueFactory(new PropertyValueFactory<Car, String>("status"));
		pricec.setCellValueFactory(new PropertyValueFactory<Car, String>("price"));
		mileagec.setCellValueFactory(new PropertyValueFactory<Car, String>("mileage"));
		companyc.setCellValueFactory(new PropertyValueFactory<Car, String>("company"));

		colorc.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
		modelc.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
		statusc.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
		yearc.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
		vinc.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
		pricec.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
		mileagec.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
		companyc.setCellFactory(TextFieldTableCell.<Car>forTableColumn());

		colorc.setEditable(true);
		yearc.setEditable(true);
		vinc.setEditable(true);
		modelc.setEditable(true);
		statusc.setEditable(true);
		pricec.setEditable(true);
		mileagec.setEditable(true);
		companyc.setEditable(true);

		car_idc.setMinWidth(150);
		colorc.setMinWidth(160);
		yearc.setMinWidth(100);
		vinc.setMinWidth(150);
		modelc.setMinWidth(150);
		statusc.setMinWidth(155);
		pricec.setMinWidth(160);
		mileagec.setMinWidth(160);
		companyc.setMinWidth(160);

		car_idc.setStyle("-fx-alignment: CENTER;");
		colorc.setStyle("-fx-alignment: CENTER;");
		yearc.setStyle("-fx-alignment: CENTER;");
		vinc.setStyle("-fx-alignment: CENTER;");
		modelc.setStyle("-fx-alignment: CENTER;");
		statusc.setStyle("-fx-alignment: CENTER;");
		pricec.setStyle("-fx-alignment: CENTER;");
		mileagec.setStyle("-fx-alignment: CENTER;");
		companyc.setStyle("-fx-alignment: CENTER;");

		table.setStyle("-fx-text-size:25;");
		table.setEditable(true);

		table.getColumns().addAll(car_idc, yearc, colorc, vinc, modelc, statusc, pricec, mileagec, companyc);

		table.refresh();

		companyc.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			update(Integer.parseInt(t.getRowValue().getCar_id()), "'" + t.getNewValue() + "'", "company_name");
			if (isGood)
				((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCompany(t.getNewValue());

		});

		colorc.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColor(t.getNewValue());
			update(Integer.parseInt(t.getRowValue().getCar_id()), "'" + t.getNewValue() + "'", "color");
		});

		yearc.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			String newValue = t.getNewValue();// store string

			try {
				int newYear = Integer.parseInt(newValue);
				if (newYear < 1900 || newYear > 2024)
					throw new NumberFormatException();

				((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setYear(newYear + "");
				update(Integer.parseInt(t.getRowValue().getCar_id()), newYear + "", "year");

			} catch (NumberFormatException r) {
				// Display an information alert
				showAlert("Invalid input for Year");
				table.refresh();
			}
		});

		vinc.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			String newValue = t.getNewValue();// store string

			try {
				int newVIN = Integer.parseInt(newValue);

				if (newVIN <= 0)
					throw new NumberFormatException();

				((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setVIN(newVIN + "");
				update(Integer.parseInt(t.getRowValue().getCar_id()), newVIN + "", "VIN");

			} catch (NumberFormatException r) {
				// Display an information alert
				showAlert("Invalid input for VIN");
				table.refresh();
			}

		});

		modelc.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModel(t.getNewValue());
			update(Integer.parseInt(t.getRowValue().getCar_id()), "'" + t.getNewValue() + "'", "model");
		});

		statusc.setOnEditCommit((CellEditEvent<Car, String> t) -> {

			String value = t.getNewValue();
			boolean flag = false;

			if (value.equalsIgnoreCase("Exist")) {
				value = "Exist";
				flag = true;
			} else if (value.equalsIgnoreCase("OutOfStock")) {
				value = "OutOfStock";
				flag = true;

			} else {

				showAlert("Invalid input for status");
				table.refresh();

			}

			if (flag) {
				((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStatus(value);
				update(Integer.parseInt(t.getRowValue().getCar_id()), "'" + value + "'", "status");
			}
		});

		pricec.setOnEditCommit((CellEditEvent<Car, String> t) -> {

			String newValue = t.getNewValue();// store string

			try {
				double newPrice = Double.parseDouble(newValue);

				if (newPrice <= 0)
					throw new NumberFormatException();

				((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPrice(newPrice + "");
				update(Integer.parseInt(t.getRowValue().getCar_id()), newPrice + "", "price");

			} catch (NumberFormatException r) {
				// Display an information alert
				showAlert("Invalid input for Price");
				table.refresh();
			}

		});

		mileagec.setOnEditCommit((CellEditEvent<Car, String> t) -> {

			String newValue = t.getNewValue();// store string

			try {
				double newMile = Double.parseDouble(newValue);

				if (newMile <= 0)
					throw new NumberFormatException();

				((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMileage(newMile + "");
				update(Integer.parseInt(t.getRowValue().getCar_id()), newMile + "", "mileage");

			} catch (NumberFormatException r) {
				// Display an information alert
				showAlert("Invalid input for Mileage");
				table.refresh();
			}

		});

	}

	private boolean isGood = true;

	public void update(int cnum, String newValue, String column) {

		try {
			isGood = true;
			con.connectDB();
			Car.ExecuteStatement("update car set " + column + "= " + newValue + " where car_id = " + cnum + ";");
			con.getCon().close();

		} catch (SQLException e) {
			showAlert("Update Failed: Company Does Not Exist.");
			isGood = false;
			table.refresh();

		} catch (ClassNotFoundException e) {
			System.out.println("here from cap pane update");
			isGood = false;

			e.printStackTrace();
		}
	}

	public void showAlert(String message) {

		Alert informationAlert = new Alert(Alert.AlertType.ERROR);
		informationAlert.setTitle("Information");
		informationAlert.setHeaderText(null);
		informationAlert.setContentText(message);
		informationAlert.showAndWait();

	}

	public void makeColumnsEditible() {

	}

	public ComboBox<String> getCompany() {
		return company;
	}

	public ComboBox<String> getColor() {
		return color;
	}

	public ComboBox<String> getYear() {
		return year;
	}

	public ComboBox<String> getPrice() {
		return price;
	}

	public ComboBox<String> getStatus() {
		return status;
	}

	public ComboBox<String> getCompanyInAdd() {
		return companyInAdd;
	}

	public Button getAdd() {
		return add;
	}

	public Button getRemove() {
		return remove;
	}

	public Button getClear() {
		return clear;
	}

	public Button getPurchase() {
		return purchase;
	}

	public Button getAddCar() {
		return addCar;
	}

	public TextField getIDtxf() {
		return IDtxf;
	}

	public TextField getColortxf() {
		return colortxf;
	}

	public TextField getYeartxf() {
		return yeartxf;
	}

	public TextField getMileagetxf() {
		return mileagetxf;
	}

	public TextField getVINtxf() {
		return VINtxf;
	}

	public TextField getModeltxf() {
		return modeltxf;
	}

	public TextField getPricetxf() {
		return pricetxf;
	}

	public TableView<Car> getTable() {
		return table;
	}

	public Button getApply() {
		return apply;
	}

}
