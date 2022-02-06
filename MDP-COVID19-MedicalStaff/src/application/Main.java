package application;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import org.unibl.etf.mdp.soap.services.Register;
import org.unibl.etf.mdp.soap.services.RegisterServiceLocator;
import org.unibl.etf.mdp.util.Logger;

import controller.MedicalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// BorderPane root = new BorderPane();
			URL url = new File("src" + File.separator + "views" + File.separator + "MedicalMenu.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Dobro dosli na formu za medicinsko osoblje!");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		RegisterServiceLocator locator = new RegisterServiceLocator();
		try {
			Register reg = locator.getRegister();
			reg.setMedicalActive(MedicalController.id);
			System.out.println("IDDDD: " + MedicalController.id);
		} catch (ServiceException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (RemoteException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
