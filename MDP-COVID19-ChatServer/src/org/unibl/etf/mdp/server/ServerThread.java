package org.unibl.etf.mdp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import javax.net.ssl.SSLSocketFactory;

import org.unibl.etf.mdp.util.Logger;

public class ServerThread extends Thread {

	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Socket doctorSocket;
	PrintWriter socketOutDoctor;
	BufferedReader socketInDoctor;
	InetAddress ia;
	boolean doctorEnd = false;
	boolean clientEnd = false;

	public ServerThread(Socket s) {
		this.socket = s;
		ia = Server.doctors.remove();
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try {
			socketOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			doctorSocket = factory.createSocket(ia, Server.PORT_LISTENING);
			socketOutDoctor = new PrintWriter(new OutputStreamWriter(doctorSocket.getOutputStream()), true);
			socketInDoctor = new BufferedReader(new InputStreamReader(doctorSocket.getInputStream()));
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}

	}

	@Override
	public void run() {

		Thread threadClient = new Thread() {
			@Override
			public void run() {
				String client;
				try {
					client = socketIn.readLine();
					while (!"END".equals(client)) {

						socketOutDoctor.println(client);
						socketOutDoctor.flush();
						client = socketIn.readLine();

					}
				} catch (IOException e) {
					Logger.log(Level.INFO, e.toString(), e);
				}
				// socketOutDoctor.println("NEXT");
				clientEnd = true;
			}

		};
		threadClient.start();
		Thread threadDoctor = new Thread() {
			@Override
			public void run() {
				String doctor = "";
				while (!"END".equals(doctor)) {
					try {
						doctor = socketInDoctor.readLine();
						socketOut.println(doctor);
						socketOut.flush();
						// socketOut.println("NEXT");
					} catch (IOException e) {
						Logger.log(Level.INFO, e.toString(), e);
					}

				}
				doctorEnd = true;

			}
		};
		threadDoctor.start();

		while (!doctorEnd && !clientEnd)
			;
		try {
			socketIn.close();
			socketOut.close();
			socketInDoctor.close();
			socketOutDoctor.close();
			socket.close();
			doctorSocket.close();
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}

		Server.doctors.add(ia);

	}

}
