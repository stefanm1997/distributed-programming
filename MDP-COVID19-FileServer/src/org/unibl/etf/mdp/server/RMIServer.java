package org.unibl.etf.mdp.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.unibl.etf.mdp.interfaces.RMIInterface;
import org.unibl.etf.mdp.util.Logger;
import org.unibl.etf.mdp.util.PropertyReader;

public class RMIServer implements RMIInterface {

	private Set<String> files;
	private File root;

	public RMIServer() {
		// root = new File("users");
	}

	@Override
	public Set<String> getAllFiles(String root, String username) throws RemoteException {
		files = new HashSet<String>();
		File userDir = new File(root, username);
		if (userDir.exists()) {
			for (File file : userDir.listFiles())
				files.add(file.getName());
		}
		return files;
	}

	@Override
	public byte[] getFile(String root, String username, String fileName) throws RemoteException {

		File userDir = new File(root, username);
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(userDir, fileName)))) {

			return Base64.getEncoder().encode(in.readAllBytes());

		} catch (FileNotFoundException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
		return null;
	}

	@Override
	public boolean sendFile(String rootName, String toUser, String fileName, byte[] content) throws RemoteException {

		root = new File(rootName);
		if (!root.exists())
			root.mkdir();
		File toUserDir = new File(root, toUser);
		if (!toUserDir.exists())
			toUserDir.mkdir();

		int count = toUserDir.list().length;
		if (count < 5) {
			try (FileOutputStream out = new FileOutputStream(new File(toUserDir, fileName))) {

				out.write(Base64.getDecoder().decode(content));
				return true;
			} catch (FileNotFoundException e) {
				Logger.log(Level.INFO, e.toString(), e);
			} catch (IOException e) {
				Logger.log(Level.INFO, e.toString(), e);
			}
		} else {
			JOptionPane optionPane = new JOptionPane("Prekrsili ste limit! Ne moze se poslati vise od 5 dokumenata!",
					JOptionPane.WARNING_MESSAGE);
			JDialog dialog = optionPane.createDialog("Upozorenje!!");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
		return false;
	}

	private static String PATH = "config" + File.separator + "server.policy";

	public static void main(String[] args) {
		try {
			System.setProperty("java.security.policy", PATH);
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			String name = "FileService";
			Registry registry = LocateRegistry
					.createRegistry(PropertyReader.readInt("config" + File.separator + "rmi.config", "RMI_PORT"));
			RMIServer server = new RMIServer();
			RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(server, 0);
			registry.rebind(name, stub);
			System.out.println("RMIServer pokrenut...");

		} catch (RemoteException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
	}
}
