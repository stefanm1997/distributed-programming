package application;

import java.io.File;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginForm {

	public void openLoginForm(String fileName) {
		try {
			// BorderPane root = new BorderPane();
			Stage primaryStage = new Stage();
			URL url = new File("src" + File.separator + "views" + File.separator + fileName).toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root, 636, 427);
			primaryStage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Forma za logovanje korisnika");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
