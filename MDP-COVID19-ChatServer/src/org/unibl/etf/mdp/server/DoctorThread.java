package org.unibl.etf.mdp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

import org.unibl.etf.mdp.util.Logger;

public class DoctorThread extends Thread {

	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;

	public DoctorThread(Socket s) {
		this.socket = s;
		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
	}

	@Override
	public void run() {
		try {
			String option = socketIn.readLine();
			if ("ADD".equals(option) && !Server.doctors.contains(socket.getInetAddress()))
				Server.doctors.add(socket.getInetAddress());
			else if ("REMOVE".equals(option))
				Server.doctors.remove(socket.getInetAddress());
			System.out.println(Server.doctors.size());
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
	}

}
