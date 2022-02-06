package controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MapController {

	@FXML
	private TextField token;

	@FXML
	private Button close;

	@FXML
	private TextArea area;

	@FXML
	private void initialize() {
		token.setText(MedicalController.token + "");

		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target(UriBuilder.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
		Invocation.Builder inBuilderMap = webTarget.path("map/" + MedicalController.token + "")
				.request(MediaType.APPLICATION_JSON);
		Response resMap2 = inBuilderMap.get();
		String map[][] = resMap2.readEntity(String[][].class);
		String content = MedicalController.mapToString(map);

		String content2 = content.replaceAll("null", " ");
		area.setText(content2);
	}

	@FXML
	void closeForm(ActionEvent event) {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}

}
