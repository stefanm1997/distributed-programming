package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import java.util.logging.Level;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.rpc.ServiceException;

import org.glassfish.jersey.client.ClientConfig;
import org.unibl.etf.mdp.soap.services.Register;
import org.unibl.etf.mdp.soap.services.RegisterServiceLocator;
import org.unibl.etf.mdp.util.Logger;
import org.unibl.etf.mdp.util.PropertyReader;
import org.unibl.etf.mdp.interfaces.RMIInterface;
import org.unibl.etf.mdp.model.MedicalStaff;

import application.LoginForm;
import application.Main;
import application.MapForm;
import application.MapInfectedForm;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController {

	@FXML
	private Menu out;

	@FXML
	private MenuItem outSys;

	@FXML
	private MenuItem outReg;

	@FXML
	private MenuBar menu;

	public static String start2;

	@FXML
	private DatePicker picker;

	@FXML
	private TextField hours;

	@FXML
	private TextField minutes;

	@FXML
	private TextField x;

	@FXML
	private TextField y;

	@FXML
	private TextField info;

	@FXML
	private TextField input;

	@FXML
	private ComboBox<String> comboChooser;

	@FXML
	private TextField fileLocation;

	private SSLSocketFactory sf;
	private boolean isFirst = true;
	private SSLSocket socket;
	private PrintWriter print;
	private BufferedReader in;
	private File file;
	private long maxSize = 524288000L;
	private RMIInterface stub;
	private int rmiPort;

	@FXML
	private TextArea areaChat;

	public ArrayList<MedicalStaff> getAllStaff() throws ServiceException, RemoteException {
		RegisterServiceLocator locator2 = new RegisterServiceLocator();
		Register reg2 = locator2.getRegister();
		ArrayList<MedicalStaff> list = new ArrayList<MedicalStaff>();
		String result = reg2.getMedicalStaff();
		String[] split1 = result.split("#");
		for (int i = 0; i < split1.length - 1; i++) {
			String[] split2 = split1[i].split(",");
			String[] split3 = split2[0].split("=");
			String name = split3[1];
			String[] split4 = split2[1].split("=");
			String surname = split4[1];
			String[] split5 = split2[2].split("=");
			String active = split5[1];
			String[] split6 = split2[3].split("=");
			String pom = split6[1];
			String[] split7 = pom.split("]");
			String id = split7[0];
			MedicalStaff medstaff = new MedicalStaff(name, surname, Boolean.valueOf(active), Integer.valueOf(id));
			list.add(medstaff);

		}
		return list;
	}

	@FXML
	private void initialize() {

		System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "securemdp");

		ArrayList<MedicalStaff> list;
		try {
			list = getAllStaff();
			for (MedicalStaff med : list) {
				String cont = med.getId() + "-" + med.getName();
				comboChooser.getItems().add(cont);
			}
		} catch (RemoteException e1) {
			Logger.log(Level.INFO, e1.toString(), e1);
		} catch (ServiceException e1) {
			Logger.log(Level.INFO, e1.toString(), e1);
		}

		String name = "FileService";
		rmiPort = PropertyReader.readInt("resources" + File.separator + "client.config", "RMI_PORT");
		try {
			Registry registry = LocateRegistry.getRegistry(
					PropertyReader.readString("resources" + File.separator + "client.config", "RMI_SERVER"), rmiPort);
			stub = (RMIInterface) registry.lookup(name);
		} catch (RemoteException e1) {
			Logger.log(Level.INFO, e1.toString(), e1);
		} catch (NotBoundException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}

		Thread thread = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						Logger.log(Level.INFO, e.toString(), e);
					}
					ClientConfig clientConfig = new ClientConfig();
					Client client = ClientBuilder.newClient(clientConfig);
					WebTarget webTarget = client.target(UriBuilder
							.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
					Invocation.Builder inBuilderTime = webTarget.path("message/" + RegisterController.token)
							.request(MediaType.TEXT_PLAIN);
					Response resTime = inBuilderTime.get();
					String result = resTime.readEntity(String.class);
					String[] split = result.split("\\*");
					info.clear();
					if (split.length != 1) {
						info.setText(
								"Osoba sa tokenom: " + RegisterController.token + " je " + split[1] + " u " + split[0]);
					} else {
						if (!"nije zarazena".equals(result))
							info.setText(result);
					}
				}
			};
		};
		thread.setDaemon(true);
		thread.start();

	}

	@FXML
	void chooseFile(ActionEvent event) {

		FileChooser chooser = new FileChooser();
		chooser.setTitle("Izaberi fajl za slanje");
		file = chooser.showOpenDialog(null);
		if (file != null)
			fileLocation.setText(file.getName() + "-" + file.getAbsolutePath());
	}

	@FXML
	void sendFile(ActionEvent event) {

		String chooserContent = comboChooser.getValue();
		if (chooserContent != null) {
			String[] split = chooserContent.split("-");
			String toUser = split[1];
			if (file != null) {
				Thread send = new Thread() {
					@Override
					public void run() {

						try {
							long size = Files.size(Paths.get(file.getAbsolutePath()));
							if (size <= maxSize) {
								byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
								if (stub.sendFile(RegisterController.token + "", toUser, file.getName(),
										Base64.getEncoder().encode(bytes))) {
									System.out.println("Uspjesno slanje!!!");
								}

							} else {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										RegisterController.createAlert("Prevelika datoteka",
												"Datoteka je prevelika. Maksimalna velicina 500 MB");
									}
								});
							}
						} catch (IOException e) {
							Logger.log(Level.INFO, e.toString(), e);
						}

					};
				};
				send.setDaemon(true);
				send.start();

			} else {
				RegisterController.createAlert("Nije izabran dokument koji se salje.",
						"Molimo Vas da izabere dokument koji zelite poslati kako bi slanje bilo uspjesno!");
			}
		} else {
			RegisterController.createAlert("Nije izabrana osoba kojoj se salje dokument.",
					"Molimo Vas da izabere osobu kojoj zelite poslati dokument kako bi slanje bilo uspjesno!");
		}
	}

	private void startMessageThread() {

		try {
			sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			int port = PropertyReader.readInt("resources" + File.separator + "client.config", "MSG_PORT");
			String address = PropertyReader.readString("resources" + File.separator + "client.config", "MSG_SERVER");
			socket = (SSLSocket) sf.createSocket(address, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			print = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

			Thread read = new Thread() {
				@Override
				public void run() {
					String line = "";
					try {
						line = in.readLine();
						String text = "";
						while (!"END".equals(line)) {
							while (!"NEXT".equals(line) && line != null && !"null".equals(line)) {
								text += line + "\n";
								areaChat.setText(text);
								line = in.readLine();
							}
							line = in.readLine();
						}
					} catch (IOException e) {
						Logger.log(Level.INFO, e.toString(), e);
					}
				}
			};
			read.setDaemon(true);
			read.start();
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}

	}

	@FXML
	void detailView(ActionEvent event) {
		if (!"".equals(info.getText())) {
			MapInfectedForm map = new MapInfectedForm();
			map.openMapForm("MapInfectedView.fxml");
		}
	}

	@FXML
	void logout(ActionEvent event) {
		LoginForm login = new LoginForm();
		login.openLoginForm("LoginView.fxml");
		Stage stage = (Stage) menu.getScene().getWindow();
		stage.close();
	}

	@FXML
	void unregister(ActionEvent event) {

		RegisterServiceLocator locator = new RegisterServiceLocator();
		try {
			Register reg = locator.getRegister();
			reg.deleteToken(RegisterController.token);
			Main main = new Main();
			Stage primaryStage = new Stage();
			main.start(primaryStage);
			Stage stage = (Stage) menu.getScene().getWindow();
			stage.close();
		} catch (ServiceException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (RemoteException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
	}

	@FXML
	void overview(ActionEvent event) {
		String pattern = " HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String now = simpleDateFormat.format(new Date());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacije vezane za pregled koriscenja");
		alert.setHeaderText("Pregled koriscenja aplikacije");
		alert.setContentText("Vrijeme pokretanja aplikacije: " + start2 + "\nTrenutno vrijeme: " + now + "\n"
				+ differenceTime(start2, now));
		alert.showAndWait();
	}

	private String differenceTime(String start, String end) {
		String pattern = " HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date dateObj1 = sdf.parse(start);
			Date dateObj2 = sdf.parse(end);
			long diff = dateObj2.getTime() - dateObj1.getTime();
			int diffsec = (int) (diff / (1000));
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date differ = new Date(diffsec * 1000L);
			String result = sdf.format(differ);
			return "Vrijeme koriscenja aplikacije je: " + result;
		} catch (ParseException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
		return "";
	}

	public static String mapToString(String[][] map) {
		String tmp = "";
		for (int i = 0; i < map.length; tmp += "\n", ++i)
			for (int j = 0; j < map[i].length; tmp += " ", ++j) {
				tmp += map[i][j];
			}
		return tmp;
	}

	@FXML
	void addLocation(ActionEvent event) {

		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target(UriBuilder.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
		Invocation.Builder inBuilderTime = webTarget.path("time/" + RegisterController.token + "")
				.request(MediaType.APPLICATION_JSON);
		Invocation.Builder inBuilderMap = webTarget.path("map/" + RegisterController.token + "")
				.request(MediaType.APPLICATION_JSON);
		Response resMap2 = inBuilderMap.get();

		LocalDate date = picker.getValue();
		String hoursTime = hours.getText();
		String minutesTime = minutes.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd#HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			int xCoo = Integer.parseInt(x.getText());
			int yCoo = Integer.parseInt(y.getText());
			int hoursInt = Integer.parseInt(hoursTime);
			int minutesInt = Integer.parseInt(minutesTime);
			if (date != null) {
				if (hoursInt >= 0 && hoursInt <= 23 && minutesInt >= 0 && minutesInt <= 59) {
					if (xCoo >= 0 && xCoo <= 9 && yCoo >= 0 && yCoo <= 9) {
						Date dateObj = sdf.parse(date.toString() + "#" + hoursTime + ":" + minutesTime);
						String result = sdf.format(dateObj);
						Response resTime = inBuilderTime.post(Entity.text(result));
						String contentTime = resTime.readEntity(String.class);
						System.out.println(contentTime);
						String map[][] = resMap2.readEntity(String[][].class);
						if (!"null".equals(map[xCoo - 1][yCoo - 1])) {
							map[xCoo - 1][yCoo - 1] += "," + result;
						} else
							map[xCoo - 1][yCoo - 1] = RegisterController.token + "#" + result;
						Response resMap = inBuilderMap.post(Entity.json(map));
						String[][] contentMap = resMap.readEntity(String[][].class);
						String result2 = mapToString(contentMap);
						System.out.println(result2);
					} else {
						RegisterController.createAlert("Nisu izabrane koordinate X i Y u odgovarajucem intervalu. ",
								"Molimo Vas da izabere odgovarajuce koordinate X i Y na kojoj se osoba nalazila kako bi dodavanje bilo uspjesno! ");
					}
				} else {
					RegisterController.createAlert(
							"Nisu izabrani sati i minute u odgovarajucem intervalu kada se osoba nalazila na odredjenoj lokaciji. ",
							"Molimo Vas da izabere odgovarajuci sat i minute kada se osoba nalazila na datoj lokaciji kako bi dodavanje bilo uspjesno! ");
				}
			} else {
				RegisterController.createAlert("Nije izabran datum kada se osoba nalazila na odredjenoj lokaciji. ",
						"Molimo Vas da izabere datum kada se osoba nalazila na datoj lokaciji kako bi dodavanje bilo uspjesno! ");
			}
		} catch (ParseException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (NumberFormatException e) {
			RegisterController.createAlert(" Nije unesen odgovarajuci format za input polje ili je input polje prazno ",
					"Molimo Vas da unesete odgovarajuci unos kako bi dodavanje bilo uspjesno! ");
		}

	}

	@FXML
	void changePassword(ActionEvent event) {

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Prozor za promjenu lozinke");
		dialog.setHeaderText("Unesite novu lozinku u polje za unos");
		dialog.setContentText("Nova lozinka za osobu sa tokenom: " + RegisterController.token);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String content = result.get();
			if (!"".equals(content)) {
				RegisterServiceLocator locator = new RegisterServiceLocator();
				try {
					Register reg = locator.getRegister();
					reg.changePassword(RegisterController.token, content);
				} catch (ServiceException e) {
					Logger.log(Level.INFO, e.toString(), e);
				} catch (RemoteException e) {
					Logger.log(Level.INFO, e.toString(), e);
				}

			}
		}

	}

	@FXML
	void viewMap(ActionEvent event) {
		MapForm map = new MapForm();
		map.openMapForm("MapView.fxml");

	}

	@FXML
	void sendMessage(ActionEvent event) {
		if (isFirst) {

			startMessageThread();
			isFirst = false;
		}
		print.println(input.getText());
		input.setText("");

	}

}
