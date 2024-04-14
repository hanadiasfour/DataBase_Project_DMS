package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Warning {

	private Image image;
	private ImageView imageV;
	private Label text;
	private Button bt;
	private VBox vbox;
	private Scene scene;
	private Stage stage;

	public Warning() {
		image = new Image("warningmessage.jpg");
		imageV = new ImageView(image);
		imageV.setFitWidth(50);
		imageV.setFitHeight(50);

		text = new Label();
		text.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
		bt = new Button("ok");
		bt.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #212224;");
	       bt.setMinWidth(100);
	        bt.setMinHeight(30);

		vbox = new VBox();
		vbox.getChildren().addAll(imageV, text, bt);
		vbox.setAlignment(Pos.CENTER);
		scene = new Scene(vbox, 300, 100);
		stage = new Stage();
		stage.setWidth(500);
		stage.setHeight(500);
		BackgroundFill backgroundFill = new BackgroundFill(Color.web("#F0CDD1"), null, null);
        Background background = new Background(backgroundFill);
        ((Region) scene.getRoot()).setBackground(background);

        
		stage.setScene(scene);

		bt.setOnAction(e->{
			stage.close();
		});
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Label getText() {
		return text;
	}

	public void setText(Label text) {
		this.text = text;
	}
}