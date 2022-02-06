package controller;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import org.unibl.etf.mdp.model.Person;
import org.unibl.etf.mdp.soap.services.Register;
import org.unibl.etf.mdp.soap.services.RegisterServiceLocator;
import org.unibl.etf.mdp.util.Logger;

import application.MenuForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField token;

	@FXML
	private Button log;

	@FXML
	private PasswordField password;

	@FXML
	private void initialize() {
		token.setText(RegisterController.token + "");
	}

	@FXML
	void login(ActionEvent event) {

		if ("".equals(password.getText())) {
			RegisterController.createAlert("Nije unesen nikakav sadrzaj",
					"Popunite polje za lozinku kako bi se uspjesno prijavili!");
		} else {
			RegisterServiceLocator locator = new RegisterServiceLocator();
			try {
				Register reg = locator.getRegister();
				Person p = reg.getPersonByToken(RegisterController.token);
				if (p != null) {
					if (password.getText().equals(p.getPassword())) {
						MenuForm menu = new MenuForm();
						menu.openMenuForm("MenuView.fxml");
						String pattern = " HH:mm:ss";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						MenuController.start2 = simpleDateFormat.format(new Date());
						Stage stage = (Stage) log.getScene().getWindow();
						stage.close();
					} else {
						RegisterController.createAlert("Pogresna lozinka",
								"Unesite odgovarajucu lozinku kako bi se uspjesno logovali!");
					}
				} else {
					RegisterController.createAlert("Greska prilikom logovanja", "Ne postoji trazeni korisnik!");
				}
			} catch (ServiceException e) {
				Logger.log(Level.INFO, e.toString(), e);
			} catch (RemoteException e) {
				Logger.log(Level.INFO, e.toString(), e);
			}
		}
	}
}
