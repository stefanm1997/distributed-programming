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

public class MapInfectedController {

	@FXML
	private TextField token;

	@FXML
	private Button close;

	@FXML
	private TextArea area;

	@FXML
	private void initialize() {
		token.setText(RegisterController.token + "");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target(UriBuilder.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
		Invocation.Builder inBuilderMap = webTarget.path("map/" + RegisterController.token + "")
				.request(MediaType.APPLICATION_JSON);
		Response resMap2 = inBuilderMap.get();
		String map[][] = resMap2.readEntity(String[][].class);
		String content = MenuController.mapToString(map);
		String content2 = content.replaceAll("null", " ");

		Invocation.Builder inBuilderTime = webTarget.path("message/" + RegisterController.token)
				.request(MediaType.TEXT_PLAIN);
		Response resTime = inBuilderTime.get();
		String result = resTime.readEntity(String.class);
		String[] split = result.split("\\*");
		String[] split2 = result.split("\\+");
		String token = String.valueOf(RegisterController.token);
		String tokenAfter, regex, content3, content4;
		if (split.length != 1) {
			if (token.startsWith("-")) {
				tokenAfter = token.replace("-", "");
				regex = "\\b(?!" + tokenAfter + "#" + split[0] + ")\\b\\S+";
				content3 = content2.replaceAll(regex, "");
				content4 = content3.replace(RegisterController.token + "", RegisterController.token + split[0]);
				area.setText(content4);
			} else {
				regex = "\\b(?!" + RegisterController.token + "#" + split[0] + ")\\b\\S+";
				content3 = content2.replaceAll(regex, "");
				content4 = content3.replace(RegisterController.token + "", RegisterController.token + split[0]);
				area.setText(content4);
			}
		}

		if (split2.length != 1) {
			if (token.startsWith("-")) {
				tokenAfter = token.replace("-", "");
				regex = "\\b(?!" + tokenAfter + "#" + split2[1] + ")\\b\\S+";
				content3 = content2.replaceAll(regex, "");
				content4 = content3.replace(RegisterController.token + "", RegisterController.token + split2[1]);
				area.setText(content4);
			} else {
				regex = "\\b(?!" + RegisterController.token + "#" + split2[1] + ")\\b\\S+";
				content3 = content2.replaceAll(regex, "");
				content4 = content3.replace(RegisterController.token + "", RegisterController.token + split2[1]);
				area.setText(content4);
			}
		}
	}

	@FXML
	void closeForm(ActionEvent event) {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}

}
