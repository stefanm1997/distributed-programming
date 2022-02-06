package controller;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.rpc.ServiceException;

import org.unibl.etf.mdp.soap.services.Register;
import org.unibl.etf.mdp.soap.services.RegisterServiceLocator;
import org.unibl.etf.mdp.util.Logger;

import application.LoginForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {

	@FXML
	private Button register;

	@FXML
	private TextField name;

	@FXML
	private TextField surname;

	@FXML
	private TextField umcn;

	public static boolean flagJMBG = false;

	public static void createAlert(String header, String context) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Upozorenje!");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}

	public static int token;

	@FXML
	void registerClient(ActionEvent event) {

		if ("".equals(name.getText()) || "".equals(surname.getText()) || "".equals(umcn.getText())) {
			createAlert("Nije unesen nikakav sadrzaj", "Popunite sva polja kako bi se uspjesno registrovali!");
		} else {

			boolean flag = true;
			String contentUMCN = umcn.getText();
			String contentName = name.getText();
			String contentSurname = surname.getText();
			try {
				Long.valueOf(contentUMCN);
				Pattern pattern = Pattern.compile("^\\p{Alpha}+$");
				Matcher matcher1 = pattern.matcher(contentName);
				Matcher matcher2 = pattern.matcher(contentSurname);
				if (!matcher1.matches() || !matcher2.matches()) {
					flag = false;
					createAlert("Unesen je pogresan sadrzaj",
							"Za ime i prezime mogu biti unesena samo slova, u suprotnom necete moci izvrsiti registraciju!");
				}
			} catch (NumberFormatException e) {
				flag = false;
				createAlert("Unesen je pogresan sadrzaj",
						"Unesite JMBG u obliku brojeva, inace necete uspjeti registraciju!");
			}
			RegisterServiceLocator locator = new RegisterServiceLocator();
			try {
				Register reg = locator.getRegister();
				if (flag) {
					long pom = Long.parseLong(umcn.getText());
					token = reg.createToken(name.getText(), surname.getText(), pom);
					if (token == -1) {
						// ovo je da se ne mogu unijeti razlicita imena i prezimena za isti jmbg, tj. da
						// jmbg uvijek bude jedinstven za svaku osobu
						createAlert("Unesen je JMBG koji vec neko ima, sto nije moguce",
								"Unesite pravi JMBG osobe, inace necete uspjeti registraciju!");
					} else {
						LoginForm login = new LoginForm();
						login.openLoginForm("LoginView.fxml");
						Stage stage = (Stage) register.getScene().getWindow();
						stage.close();
					}
				}
			} catch (ServiceException e) {
				Logger.log(Level.INFO, e.toString(), e);
			} catch (RemoteException e) {
				Logger.log(Level.INFO, e.toString(), e);
			}
		}

	}

}
