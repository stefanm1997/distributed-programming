package application;

import java.io.File;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapInfectedForm {

	public void openMapForm(String fileName) {
		try {
			// BorderPane root = new BorderPane();
			Stage primaryStage = new Stage();
			URL url = new File("src" + File.separator + "views" + File.separator + fileName).toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root, 600, 500);
			primaryStage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Forma za mapu");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
