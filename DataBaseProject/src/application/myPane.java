package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class myPane extends BorderPane {

	private TableView<Car> table;
	private Button add, delete, clearAll;
	private TextField car_id, price, model, mileage, VIN, color, year, status;

	public myPane() {

		table = new TableView<Car>();
		add = new Button("Add");
		delete = new Button("Delete");
		clearAll = new Button("Clear All");
		car_id = new TextField();
		price = new TextField();
		model = new TextField();
		mileage = new TextField();
		VIN = new TextField();
		color = new TextField();
		year = new TextField();
		status = new TextField();

		GridPane grid = new GridPane();

		grid.add(new Label("Car ID:"), 0, 0);
		grid.add(new Label("Color:"), 1, 0);
		grid.add(new Label("Year:"), 2, 0);
		grid.add(new Label("VIN:"), 3, 0);
		grid.add(new Label("Model:"), 4, 0);
		grid.add(new Label("Status:"), 5, 0);
		grid.add(new Label("Price:"), 6, 0);
		grid.add(new Label("Mileage:"), 7, 0);

		grid.add(car_id, 0, 1);
		grid.add(color, 1, 1);
		grid.add(year, 2, 1);
		grid.add(VIN, 3, 1);
		grid.add(model, 4, 1);
		grid.add(status, 5, 1);
		grid.add(price, 6, 1);
		grid.add(mileage, 7, 1);

		grid.setHgap(10);
		grid.setVgap(10);

		HBox buttons = new HBox(20);
		buttons.getChildren().addAll(add, delete);
		buttons.setPadding(new Insets(10, 5, 5, 5));
		buttons.setAlignment(Pos.CENTER);

		setTop(grid);
		setCenter(table);
		setBottom(buttons);

	}

	public TableView getTable() {
		return table;
	}

	public Button getAdd() {
		return add;
	}

	public Button getDelete() {
		return delete;
	}

	public Button getClearAll() {
		return clearAll;
	}

	public TextField getCar_id() {
		return car_id;
	}

	public TextField getPrice() {
		return price;
	}

	public TextField getModel() {
		return model;
	}

	public TextField getMileage() {
		return mileage;
	}

	public TextField getVIN() {
		return VIN;
	}

	public TextField getColor() {
		return color;
	}

	public TextField getYear() {
		return year;
	}

	public TextField getStatus() {
		return status;
	}

}
