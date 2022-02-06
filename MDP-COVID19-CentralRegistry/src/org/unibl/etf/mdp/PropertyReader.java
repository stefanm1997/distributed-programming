package org.unibl.etf.mdp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;

import org.unibl.etf.mdp.Logger;

public class PropertyReader {

	public static int readInt(String property) {
		int result = 0;
		Properties prop = new Properties();
		try {
			InputStreamReader input = new InputStreamReader(new FileInputStream("config.properties"));
			prop.load(input);
			result = Integer.parseInt(prop.getProperty(property));
		} catch (FileNotFoundException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
		return result;
	}

	public static String readString(String file, String property) {
		String result = "";
		Properties prop = new Properties();
		try {
			InputStreamReader input = new InputStreamReader(new FileInputStream(file));
			prop.load(input);
			result = (prop.getProperty(property));
		} catch (FileNotFoundException e) {
			Logger.log(Level.INFO, e.toString(), e);
		} catch (IOException e) {
			Logger.log(Level.INFO, e.toString(), e);
		}
		return result;
	}

	public static void main(String[] args) {

	}

}
