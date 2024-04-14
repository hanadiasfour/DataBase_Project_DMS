package application;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CompanyPane extends GridPane {

	private Button add, remove, refresh, search, addComp;
	private TableView<Company> table;
	private TextField searchtxf, nametxf, countrytxf;
	private Connecter con = Main.con;

	public CompanyPane() {

		ImageView img = new ImageView(new Image("Search.png"));

		add = new Button("Add Company");
		remove = new Button("Remove Company");
		refresh = new Button("Refresh");
		search = new Button("", img);
		searchtxf = new TextField();
		table = new TableView<Company>();
		nametxf = new TextField();
		countrytxf = new TextField();
		addComp = new Button("Add Company");

		search.setStyle("-fx-background-color:white; -fx-border-color:black;");
		search.setPrefSize(45, 45);
		searchtxf.setPromptText("Search Companies");
		img.setFitHeight(20);
		img.setFitWidth(20);
		table.setPrefSize(800, 500);

		add.getStyleClass().add("my-button");
		remove.getStyleClass().add("my-button");
		refresh.getStyleClass().add("my-button");

		HBox searching = new HBox();
		searching.getChildren().addAll(searchtxf, search);

		HBox control = new HBox(15);
		control.getChildren().addAll(add, remove);

		add(searching, 0, 0);
		add(table, 0, 1);
		add(control, 0, 2);
		add(refresh, 1, 0);
		setHgap(-80);
		setVgap(15);
		setPadding(new Insets(50, 10, 10, 50));
		setStyle("-fx-background-color:#FAE7E9;");
		setColumns();

	}

	public BorderPane getCompanyAdder() {

		Label heading = new Label("New Company");
		Label namelbl = new Label("Company Name:");
		Label companylbl = new Label("Country:");

		namelbl.getStyleClass().add("my-label");
		companylbl.getStyleClass().add("my-label");
		heading.setFont(Font.font("Elephant", FontWeight.BOLD, 30));
		addComp.getStyleClass().add("my-button");
		countrytxf.setPromptText("Country");
		nametxf.setPromptText("Name");

		ImageView img = new ImageView(new Image("addCompany.png"));
		img.setFitHeight(40);
		img.setFitWidth(40);

		HBox top = new HBox(15);
		top.getChildren().addAll(img, heading);
		top.setAlignment(Pos.CENTER);

		GridPane middle = new GridPane();
		middle.add(namelbl, 0, 0);
		middle.add(nametxf, 0, 1);
		middle.add(companylbl, 0, 2);
		middle.add(countrytxf, 0, 3);
		middle.setAlignment(Pos.CENTER);
		middle.setVgap(10);

		BorderPane root = new BorderPane();
		root.setTop(top);
		root.setBottom(addComp);
		root.setCenter(middle);
		root.setAlignment(top, Pos.CENTER);
		root.setAlignment(addComp, Pos.CENTER);
		root.setPadding(new Insets(50, 10, 50, 10));
		root.setStyle("-fx-background-color:#FAE7E9;");
		return root;

	}

	private void setColumns() {

		// setting columns
		TableColumn<Company, String> namec = new TableColumn<Company, String>("Company/Brand");
		TableColumn<Company, String> countryc = new TableColumn<Company, String>("Country");

		namec.setCellValueFactory(new PropertyValueFactory<Company, String>("name"));
		countryc.setCellValueFactory(new PropertyValueFactory<Company, String>("country"));
		namec.setCellFactory(TextFieldTableCell.<Company>forTableColumn());
		countryc.setCellFactory(TextFieldTableCell.<Company>forTableColumn());
		namec.setEditable(true);
		countryc.setEditable(true);
		namec.setPrefWidth(400);
		countryc.setPrefWidth(398);
		namec.setStyle("-fx-alignment: CENTER;");
		countryc.setStyle("-fx-alignment: CENTER;");

		table.setEditable(true);

		table.getColumns().addAll(namec, countryc);

		table.refresh();

		namec.setOnEditCommit((CellEditEvent<Company, String> t) -> {

			update(t.getRowValue().getName(), t.getNewValue(), "name");

			if (isGood)
				((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
		});

		countryc.setOnEditCommit((CellEditEvent<Company, String> t) -> {
			((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCountry(t.getNewValue());
			update(t.getRowValue().getName(), t.getNewValue(), "country");
		});

	}

	private boolean isGood = true;

	public void update(String cnum, String newValue, String column) {

		try {
			isGood = true;
			con.connectDB();
			con.ExecuteStatement("update company set " + column + "= '" + newValue + "' where name = '" + cnum + "';");
			con.getCon().close();

		} catch (SQLException e) {
			showAlert("Update Failed: Company Already Exists.");
			isGood = false;
			table.refresh();
		} catch (ClassNotFoundException e) {
			System.out.println("here from company pane update");
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

	public Button getAdd() {
		return add;
	}

	public Button getRemove() {
		return remove;
	}

	public Button getRefresh() {
		return refresh;
	}

	public Button getSearch() {
		return search;
	}

	public TableView<Company> getTable() {
		return table;
	}

	public TextField getSearchtxf() {
		return searchtxf;
	}

	public Button getAddComp() {
		return addComp;
	}

	public TextField getNametxf() {
		return nametxf;
	}

	public TextField getCountrytxf() {
		return countrytxf;
	}

}
