package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;

public class Utils {
	public static String pattern = "yyyy-MM-dd";
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	public static Date convertStringToDate(String dateString) throws ParseException {
		return simpleDateFormat.parse(dateString);
	}

	public static String convertDateToString(Date date) throws ParseException {
		return simpleDateFormat.format(date);
	}

	public static String convertIntegerToString(Integer value) {
		return value.toString();
	}

	public static Integer convertStringToInteger(String value) {
		return Integer.parseInt(value);
	}

	public static Double stringToDouble(String value) {
		return Double.parseDouble(value);
	}

	public static String doubleToString(Double value) {
		return value.toString();
	}

	public static String pattern2 = "d-M-Y";

	public static StringConverter dateFormatter() {
		StringConverter converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern2);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				}
				return "";
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				}
				return null;
			}
		};
		return converter;
	}

	public static String maxId(String table) throws Exception {
		Main.con.connectDB();
		Statement stat = Main.con.getCon().createStatement();
		String SQL = ("select max(convert (" + table + "_id,signed)) from " + table + ";");
		ResultSet rs = stat.executeQuery(SQL);
		String s = null;

		while (rs.next()) {
			try {
				s = rs.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		int max = Integer.parseInt(s) + 1;
		return String.valueOf(max);
	}

	public static void showAlert(String message) {

		Alert informationAlert = new Alert(Alert.AlertType.ERROR);
		informationAlert.setTitle("Information");
		informationAlert.setHeaderText(null);
		informationAlert.setContentText(message);
		informationAlert.showAndWait();

	}

}