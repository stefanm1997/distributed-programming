package org.unibl.etf.mdp.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

import javax.net.ssl.SSLServerSocketFactory;

import org.unibl.etf.mdp.util.Logger;

public class Server {
	
	static ConcurrentLinkedQueue<InetAddress> doctors=new ConcurrentLinkedQueue<InetAddress>();
	static int PORT_LISTENING;
	public static void main(String[] args) {

		try (InputStream in = new FileInputStream(new File("resources" + File.separator + "server.config"))) {
			Properties prop = new Properties();
			prop.load(in);
			int port = Integer.valueOf(prop.getProperty("PORT"));
			PORT_LISTENING=Integer.valueOf(prop.getProperty("PORT_LISTENING"));
			String pathToJKS = prop.getProperty("PATH_TO_JKS");
			String passJKS = prop.getProperty("JKS_PASSWORD");
			System.setProperty("javax.net.ssl.trustStore", pathToJKS);
			System.setProperty("javax.net.ssl.trustStorePassword", passJKS);
			System.setProperty("javax.net.ssl.keyStore", pathToJKS);
			System.setProperty("javax.net.ssl.keyStorePassword", passJKS);
			new Thread() {
				public void run() {
					SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
					ServerSocket ss;
					try {
						ss = factory.createServerSocket(Integer.valueOf(prop.getProperty("DOCTOR_PORT")));
						System.out.println("Server pokrenut...");
						while (true) {
							try {
								new DoctorThread(ss.accept()).start();
							} catch (IOException e) {
								Logger.log(Level.INFO, e.toString(), e);
							}
						}
					} catch (IOException e1) {
						Logger.log(Level.INFO, e1.toString(), e1);
					}
				};
			}.start();
			
			new Thread() {
				public void run() {
					SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
					ServerSocket ss;
					try {
						ss = factory.createServerSocket(port);
						System.out.println("Server pokrenut...");
						while (true) {
							try {
								new ServerThread(ss.accept()).start();
							} catch (IOException e) {
								Logger.log(Level.INFO, e.toString(), e);
							}
						}
					} catch (IOException e1) {
						Logger.log(Level.INFO, e1.toString(), e1);
					}
				};
			}.start();

		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
	}

}
