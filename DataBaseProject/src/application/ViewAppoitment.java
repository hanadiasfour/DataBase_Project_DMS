package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewAppoitment {

	private Label notes;
	private Stage stage;
	private TextArea textArea;

	public ViewAppoitment() {
		notes = new Label("Appoitment details");

		notes.setStyle(
				"-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");

		Image backgroundImage = new Image("bow2.png");

		ImageView backgroundImageView = new ImageView(backgroundImage);
		HBox h = new HBox();
		h.getChildren().add(backgroundImageView);
		h.setAlignment(Pos.CENTER);

		backgroundImageView.setFitWidth(200);
		backgroundImageView.setFitHeight(200);

		textArea = new TextArea();
		textArea.setPrefSize(600, 600);
		textArea.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightgrey; "
				+ "-fx-font-family: 'Times New Roman'; -fx-font-size: 30; -fx-text-fill: #212224; -fx-font-style: italic;");
		GridPane pane = new GridPane();
		pane.add(h, 0, 0);
		pane.add(textArea, 0, 1);

		pane.setStyle("-fx-background-color: #FAE7E9;");
		pane.setAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(pane, 600, 600);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setMaxWidth(800);
		stage.setMaxHeight(800);
		stage.show();

	}

	public String getAppointmentDetails(String appointmentId) {
		Connecter con = Main.con;
		StringBuilder result = new StringBuilder();

		try {
			con.connectDB();

			String employeeQuery = "SELECT Full_Name, Email, Phone FROM employee "
					+ "WHERE employee_id = (SELECT employee_id FROM appointment WHERE appointment_id = '"
					+ appointmentId + "')";
			try (PreparedStatement employeeStatement = con.getCon().prepareStatement(employeeQuery)) {
				ResultSet employeeResult = employeeStatement.executeQuery();

				if (employeeResult.next()) {
					String employeeName = employeeResult.getString("Full_Name");
					String employeeEmail = employeeResult.getString("Email");
					String employeePhone = employeeResult.getString("Phone");

					result.append("Employee Details:\n").append("Name: ").append(employeeName).append("\n")
							.append("Email: ").append(employeeEmail).append("\n").append("Phone: ")
							.append(employeePhone).append("\n\n");
				}
			}

			String carQuery = "SELECT * FROM car "
					+ "WHERE car_id IN (SELECT car_id FROM associated WHERE appointment_id = '" + appointmentId + "')";
			try (PreparedStatement carStatement = con.getCon().prepareStatement(carQuery)) {
				ResultSet carResult = carStatement.executeQuery();

				result.append("Cars Associated with the Appointment:\n");
				while (carResult.next()) {
					String carDetails = "Car ID: " + carResult.getString("car_id") + ", Company: "
							+ carResult.getString("company_name") + ", Model: " + carResult.getString("model")
							+ ", Color: " + carResult.getString("color") + "\n";

					result.append(carDetails);
				}
				result.append("\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// Customer details
			String customerQuery = "SELECT * FROM customer WHERE customer_id = "
					+ "(SELECT customer_id FROM appointment WHERE appointment_id = '" + appointmentId + "')";
			try (PreparedStatement customerStatement = con.getCon().prepareStatement(customerQuery)) {
				ResultSet customerResult = customerStatement.executeQuery();

				if (customerResult.next()) {
					String customerDetails = "Customer Details:\n" + "Name: " + customerResult.getString("Full_Name")
							+ ", Email: " + customerResult.getString("Email") + ", Phone: "
							+ customerResult.getString("Phone") + "\n";

					result.append(customerDetails);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con.getCon() != null) {
					con.getCon().close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result.toString();
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {

		this.textArea = textArea;
	}

	private void setTableViewStyles(TableView<?> tableView) {
		tableView.setMaxSize(800, 50);
		tableView.setMinSize(800, 50);

		tableView.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		for (TableColumn<?, ?> column : tableView.getColumns()) {
			column.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
