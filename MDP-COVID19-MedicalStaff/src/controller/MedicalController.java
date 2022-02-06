package controller;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;
import java.util.logging.Level;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
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
import org.unibl.etf.mdp.interfaces.RMIInterface;
import org.unibl.etf.mdp.soap.services.Register;
import org.unibl.etf.mdp.soap.services.RegisterServiceLocator;
import org.unibl.etf.mdp.util.Logger;
import org.unibl.etf.mdp.util.PropertyReader;
import org.unibl.etf.model.MedicalStaff;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.Gson;

import application.MapForm;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class MedicalController {

	public static int token, id, N, P, K;
	public String contentArea = "";
	public boolean notEnd = true;

	@FXML
	private ComboBox<String> tokenChooser;

	@FXML
	private RadioButton not;

	@FXML
	private RadioButton maybe;

	@FXML
	private RadioButton yes;

	@FXML
	private TextField medical;

	@FXML
	private ComboBox<String> time;

	@FXML
	private TextArea areaContact;

	@FXML
	private TextArea areaChat;

	@FXML
	private TextField input;

	@FXML
	private ComboBox<String> chooseDoc;

	@FXML
	private ComboBox<String> choosePerson;

	@FXML
	private TextField inputMedical;

	@FXML
	private TextArea areaChatMedical;

	private RMIInterface stub;
	private int rmiPort;
	private String nameDoctor = "";

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

	public static void createAlert(String header, String context) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Upozorenje!");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}

	@FXML
	private void initialize() {
		System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "securemdp");
		System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "securemdp");
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		int port = PropertyReader.readInt("resources" + File.separator + "doctor.config", "PORT");
		String address = PropertyReader.readString("resources" + File.separator + "doctor.config", "ADDRESS");
		try (Socket s = factory.createSocket(InetAddress.getByName(address), port);
				PrintWriter socketOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
			socketOut.println("ADD");
			socketOut.flush();
		} catch (IOException e) {
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
					areaContact.setText(contentArea);

				}
			};

		};
		thread.setDaemon(true);
		thread.start();

		RegisterServiceLocator locator = new RegisterServiceLocator();
		try {
			Register reg = locator.getRegister();
			String tokens = reg.listAllTokens();
			String[] split = tokens.split(" ");
			for (int i = 0; i < split.length; i++) {
				tokenChooser.getItems().add(split[i]);
				choosePerson.getItems().add(split[i]);
			}

			ArrayList<MedicalStaff> list = getAllStaff();
			boolean isFirst = true;
			for (MedicalStaff med : list) {
				if (med.isActive() && isFirst) {
					reg.setMedicalNotActive(med.getId());
					nameDoctor = med.getName();
					medical.setText(med.getName() + " " + med.getSurname());
					id = med.getId();
					isFirst = false;
				}
			}

			if ("".equals(medical.getText())) {
				createAlert("Molimo sacekajte", "Trenutno nema slobodnih medicinskih radnika!!!");
				Platform.exit();
			}

		} catch (ServiceException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (RemoteException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
		ToggleGroup group = new ToggleGroup();
		not.setToggleGroup(group);
		not.setSelected(true);
		maybe.setToggleGroup(group);
		yes.setToggleGroup(group);

		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target(UriBuilder.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
		Invocation.Builder inBuilderConfig = webTarget.path("config").request(MediaType.TEXT_PLAIN);
		Response resConfig = inBuilderConfig.get();
		String config = resConfig.readEntity(String.class);
		String[] split1 = config.split("#");
		String[] split2 = split1[0].split("=");
		N = Integer.parseInt(split2[1]);
		String[] split3 = split1[1].split("=");
		P = Integer.parseInt(split3[1]);
		String[] split4 = split1[2].split("=");
		K = Integer.parseInt(split4[1]);

		startMessageThread();

		int mPort = PropertyReader.readInt("resources" + File.separator + "doctor.config", "MULTICAST_PORT");
		String mAddress = PropertyReader.readString("resources" + File.separator + "doctor.config",
				"MULTICAST_ADDRESS");
		Thread informations = new Thread() {
			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					Logger.log(Level.INFO, e1.toString(), e1);
				}

				try (MulticastSocket mSocket = new MulticastSocket(mPort)) {
					mSocket.joinGroup(InetAddress.getByName(mAddress));
					while (true) {
						byte[] bytes = new byte[1024];
						DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
						mSocket.receive(packet);

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								String message = "";
								message = new String(packet.getData());
								areaChatMedical.setText(message);
							}
						});

						File userDir = new File(System.getProperty("user.dir"), nameDoctor);
						File informationsDir;
						File userFile;
						if (!userDir.exists()) {
							userDir.mkdir();
							informationsDir = new File(userDir, "informations");
							informationsDir.mkdir();
						} else {
							informationsDir = new File(userDir, "informations");
							if (!informationsDir.exists())
								informationsDir.mkdir();
						}

						userFile = new File(informationsDir, "" + System.currentTimeMillis());

						int count = informationsDir.listFiles().length % 4;

						if (count == 0)
							serializeWithJava(areaChatMedical.getText(), userFile);
						else if (count == 1)
							serializeWithGson(areaChatMedical.getText(), userFile);
						else if (count == 2)
							serializeWithXML(areaChatMedical.getText(), userFile);
						else
							serializeWithKryo(areaChatMedical.getText(), userFile);
					}
				} catch (IOException e) {
					Logger.log(Level.INFO, e.toString(), e);
				}
			}
		};
		informations.setDaemon(true);
		informations.start();
	}

	@FXML
	void downloadDoc(ActionEvent event) {

		String chooserContent = chooseDoc.getValue();
		String choosePersonContent = choosePerson.getValue();
		if (choosePersonContent != null) {
			if (chooserContent != null) {
				File toUserDir = new File(
						System.getProperty("user.dir") + File.separator + "downloads" + File.separator + nameDoctor);
				if (!toUserDir.exists()) {
					toUserDir.mkdir();
				}
				File userDir = new File(toUserDir + File.separator + choosePersonContent);
				if (!userDir.exists()) {
					userDir.mkdir();
				}
				File fileLoc = new File(userDir + File.separator + chooserContent);
				Thread download = new Thread() {
					@Override
					public void run() {
						try {
							byte[] bytes = stub.getFile(choosePersonContent, nameDoctor, chooserContent);

							try (FileOutputStream out = new FileOutputStream(fileLoc)) {
								out.write(Base64.getDecoder().decode(bytes));
							} catch (FileNotFoundException e) {
								Logger.log(Level.INFO, e.toString(), e);
							} catch (IOException e) {
								Logger.log(Level.INFO, e.toString(), e);
							}
						} catch (RemoteException e) {
							Logger.log(Level.INFO, e.toString(), e);
						}
					};
				};
				download.setDaemon(true);
				download.start();

			} else {
				createAlert("Nije izabran dokument koji se preuzima.",
						"Molimo Vas da izabere dokument koji zelite preuzeti kako bi preuzimanje bilo uspjesno!");
			}
		} else {
			createAlert("Nije izabrana osoba od koje se preuzima.",
					"Molimo Vas da izabere osobu od koje zelite preuzeti dokument kako bi preuzimanje bilo uspjesno!");
		}

	}

	private SSLServerSocket socket;
	private PrintWriter print;
	private BufferedReader in;
	private SSLServerSocketFactory sf;

	String text = "";

	private void startMessageThread() {

		Thread read = new Thread() {
			@Override
			public void run() {

				int port = PropertyReader.readInt("resources" + File.separator + "doctor.config", "PORT_LISTENING");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						areaChat.getScene().getWindow().setOnCloseRequest((e) -> notEnd = false);
					}
				});

				try {
					sf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
					socket = (SSLServerSocket) sf.createServerSocket(port);
					while (notEnd) {
						Socket ss = socket.accept();
						print = new PrintWriter(new OutputStreamWriter(ss.getOutputStream()), true);
						in = new BufferedReader(new InputStreamReader(ss.getInputStream()));

						text = "";
						String line = in.readLine();
						while (!"END".equals(line)) {
							text = "";
							while (!"NEXT".equals(line) && line != null && !"null".equals(line)) {
								text += line + "\n";
								areaChat.setText(text);
								line = in.readLine();
							}

							areaChat.appendText("Ovo je u vanjskom while\n");
							line = in.readLine();

						}
					}

				} catch (IOException e) {
					Logger.log(Level.INFO, e.toString(), e);
				}
			}
		};
		read.setDaemon(true);
		read.start();
	}

	@FXML
	void onChoose(ActionEvent event) {

		String name = "FileService";
		rmiPort = PropertyReader.readInt("resources" + File.separator + "doctor.config", "RMI_PORT");
		try {
			Registry registry = LocateRegistry.getRegistry(
					PropertyReader.readString("resources" + File.separator + "doctor.config", "RMI_SERVER"), rmiPort);
			stub = (RMIInterface) registry.lookup(name);
			Set<String> files = stub.getAllFiles(choosePerson.getValue(), nameDoctor);
			chooseDoc.getItems().clear();
			for (String file : files) {
				chooseDoc.getItems().add(file);
			}
		} catch (RemoteException e1) {
			Logger.log(Level.INFO, e1.toString(), e1);
		} catch (NotBoundException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}

	}

	@FXML
	void onSelect(ActionEvent event) {
		token = Integer.parseInt(tokenChooser.getValue());
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target(UriBuilder.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
		Invocation.Builder inBuilderMap = webTarget.path("time/" + token).request(MediaType.APPLICATION_JSON);
		Response resMap2 = inBuilderMap.get();
		String timeRest = resMap2.readEntity(String.class);
		String[] split2 = timeRest.split(",");
		time.getItems().clear();
		for (int i = 0; i < split2.length; i++) {
			if (!"".equals(split2[i]))
				time.getItems().add(split2[i]);
		}
	}

	@FXML
	void viewMap(ActionEvent event) {
		if (tokenChooser.getValue() != null) {
			token = Integer.parseInt(tokenChooser.getValue());
			MapForm map = new MapForm();
			map.openMapForm("MapView.fxml");
		}
	}

	public static String mapToString(String[][] map) {
		String tmp = "";
		for (int i = 0; i < map.length; tmp += "\n", ++i)
			for (int j = 0; j < map[i].length; tmp += " ", ++j) {
				tmp += map[i][j];
			}
		return tmp;
	}

	public String[][] stringToMap(String str) {
		String[] tmp = str.split("\\n");
		int row = tmp.length;
		String[][] map = new String[row][];
		for (int i = 0; i < row; ++i) {
			String[] xy = tmp[i].split(" ");
			map[i] = new String[10];
			for (int j = 1; j < 10; ++j) {
				map[i][j] = xy[j];
			}
		}
		return map;
	}

	public String[][] stringToMap2(String str) {
		String[][] map = new String[10][10];
		String[] xy = str.split("\\n");
		for (int i = 0; i < 10; ++i) {
			String[] xy2 = xy[i].split(" ");
			for (int j = 0; j < 10; ++j) {
				map[i][j] = xy2[j];
				if (j == 9) {
					map[i][j] += "\n";
				}
			}
		}
		return map;
	}

	@FXML
	void detectCovid(ActionEvent event) {

		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target(UriBuilder.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
		Invocation.Builder inBuilderTime = webTarget.path("message/" + token).request(MediaType.TEXT_PLAIN);
		if ((maybe.isSelected() || yes.isSelected()) && time.getValue() != null) {
			String status = "";
			if (maybe.isSelected())
				status = "potencijalno zarazena";
			else
				status = "zarazena";
			String box = time.getValue() + "*" + status;
			Response resTime = inBuilderTime.post(Entity.text(box));
			String response = resTime.readEntity(String.class);
			System.out.println(response);
			String[] split1 = time.getValue().split("#");
			String date = split1[0];
			String[] split2 = split1[1].split(":");
			String hour = split2[0];
			String min = split2[1];
			int minutes = Integer.parseInt(min);
			String[] map = potencionalContact();

			String input = "";
			for (int i = 0; i < map.length; i++) {
				if (map[i].contains(date + "#" + hour) && !map[i].contains(tokenChooser.getValue())) {
					ObservableList<String> list = tokenChooser.getItems();
					String t = "";
					for (String l : list) {
						if (map[i].contains(l)) {
							t = l;
						}
					}
					Invocation.Builder inBuilderTime2 = webTarget.path("time/" + t).request(MediaType.APPLICATION_JSON);
					Response resTime2 = inBuilderTime2.get();
					String time = resTime2.readEntity(String.class);
					String[] split = time.split(",");
					String timeDiffer = "";
					if (split.length != 1) {
						for (int j = 0; j < split.length; j++) {
							if (split[j].contains(date + "#" + hour)) {
								timeDiffer = split[j];
							}
						}
					} else {
						timeDiffer = time;
					}
					String[] splitTime = timeDiffer.split(":");
					int minutesOther = Integer.parseInt(splitTime[1]);
					int differ = minutes - minutesOther;
					int differAbs = Math.abs(differ);
					Invocation.Builder inBuilderMap = webTarget.path("map/" + t).request(MediaType.APPLICATION_JSON);
					Response resMap2 = inBuilderMap.get();
					String[][] mapMatrix = resMap2.readEntity(String[][].class);
					boolean flag = false;
					for (int k = 0; k < 10; k++) {
						for (int y = 0; y < 10; y++) {
							if (mapMatrix[k][y].contains(timeDiffer)) {
								for (int z = 0; z < K; z++) {
									if ((!"null".equals(mapMatrix[k + z][y]) && k + z < 10)
											|| (!"null".equals(mapMatrix[k - z][y]) && k - z > 0)
											|| (!"null".equals(mapMatrix[k][y + z]) && y + z < 10)
											|| (!"null".equals(mapMatrix[k][y - z]) && y - z > 0)) {
										flag = true;
									}
								}
							}
						}
					}
					Invocation.Builder inBuilderTime3 = webTarget.path("message/" + t).request(MediaType.TEXT_PLAIN);
					Response resMessage = inBuilderTime3.get();
					String message = resMessage.readEntity(String.class);
					if (differAbs <= P && flag && ("nije zarazena".equals(message) || "".equals(message))) {

						input += "Osoba sa tokenom: " + t + " je imala potencijalni kontakt u " + timeDiffer + "\n";
						Response resTimeNew = inBuilderTime3.post(Entity
								.text("Osoba sa tokenom: " + t + " je imala potencijalni kontakt u+" + timeDiffer));
					} else {
						input += message + "\n";

					}
				}
			}

			contentArea = !contentArea.equals(input) ? input : contentArea;
		} else if (not.isSelected() && (time.getValue() != null || time.getValue() == null)) {
			Response resTime = inBuilderTime.post(Entity.text(""));
			String response = resTime.readEntity(String.class);
			Response resMess = inBuilderTime.post(Entity.text(""));
			String mess = resMess.readEntity(String.class);
			String test = contentArea;
			if (contentArea.contains(token + "")) {
				contentArea = test.replaceAll("Osoba sa tokenom: " + token + " je imala potencijalni kontakt u .*", "");
			}
		}
	}

	public String[] potencionalContact() {
		String[] maps = null;
		RegisterServiceLocator locator = new RegisterServiceLocator();
		try {
			Register reg = locator.getRegister();
			String tokens = reg.listAllTokens();
			String[] split = tokens.split(" ");
			maps = new String[split.length];
			for (int i = 0; i < split.length; i++) {
				ClientConfig clientConfig = new ClientConfig();
				Client client = ClientBuilder.newClient(clientConfig);
				WebTarget webTarget = client.target(
						UriBuilder.fromUri("http://localhost:8080/MDP-COVID19-CentralRegistry/api/person/").build());
				Invocation.Builder inBuilderMap = webTarget.path("map/" + split[i]).request(MediaType.APPLICATION_JSON);
				Response resMap2 = inBuilderMap.get();
				String map[][] = resMap2.readEntity(String[][].class);
				String content = mapToString(map);
				String content2 = content.replaceAll("null", " ");
				maps[i] = content2;
			}
		} catch (ServiceException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (RemoteException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
		return maps;

	}

	@FXML
	void sendMessage(ActionEvent event) {
		System.out.println(input.getText());
		print.println(input.getText());
		input.setText("");
	}

	@FXML
	void sendMulticast(ActionEvent event) {

		Thread sendMulti = new Thread() {
			@Override
			public void run() {
				int port = PropertyReader.readInt("resources" + File.separator + "doctor.config", "MULTICAST_PORT");
				String address = PropertyReader.readString("resources" + File.separator + "doctor.config",
						"MULTICAST_ADDRESS");
				String msg = inputMedical.getText();
				if (msg.length() <= 1024) {
					byte[] bytes = msg.getBytes();
					try (DatagramSocket socket = new DatagramSocket()) {
						DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(address),
								port);
						socket.send(packet);
					} catch (UnknownHostException e) {
						Logger.log(Level.INFO, e.toString(), e);
					} catch (SocketException e) {
						Logger.log(Level.INFO, e.toString(), e);
					} catch (IOException e) {
						Logger.log(Level.INFO, e.toString(), e);
					}
				} else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							createAlert("Prevelika duzina poruke", "Maksimalna duzina poruke je 1024 karaktera.");
						}
					});
				}
			}

		};
		sendMulti.setDaemon(true);
		sendMulti.start();
	}

	private void serializeWithJava(String message, File userFile) {

		new Thread() {
			@Override
			public void run() {

				try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userFile))) {

					out.writeObject(message);
					out.flush();
				} catch (FileNotFoundException e) {
					Logger.log(Level.INFO, e.toString(), e);
				} catch (IOException e) {
					Logger.log(Level.INFO, e.toString(), e);
				}
			}
		}.start();

	}

	private void serializeWithGson(String message, File userFile) {

		new Thread() {
			@Override
			public void run() {

				Gson gson = new Gson();
				try (PrintStream out = new PrintStream(new FileOutputStream(userFile))) {

					out.println(gson.toJson(message));
					out.flush();
				} catch (FileNotFoundException e) {
					Logger.log(Level.INFO, e.toString(), e);
				}
			}
		}.start();
	}

	private void serializeWithXML(String message, File userFile) {

		new Thread() {
			@Override
			public void run() {

				try (XMLEncoder encoder = new XMLEncoder((new FileOutputStream(userFile)))) {
					encoder.writeObject(message);
					encoder.flush();
				} catch (FileNotFoundException e) {
					Logger.log(Level.INFO, e.toString(), e);
				}
			}
		}.start();
	}

	private void serializeWithKryo(String message, File userFile) {

		Kryo kryo = new Kryo();
		kryo.register(String.class);

		try (Output out = new Output(new FileOutputStream(userFile))) {

			kryo.writeClassAndObject(out, message);
			out.flush();
		} catch (FileNotFoundException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}

	}

}
