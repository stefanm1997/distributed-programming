package org.unibl.etf.mdp;

import java.util.HashMap;

public class Person {

	private String name, surname, password;
	private int token;
	private long JMBG;
	private String map[][] = new String[10][10], status = "nije zarazena", time = "";
	private boolean isBlocked = false;

	public Person() {
		super();
	}

	public Person(String name, String surname, long jMBG, int token, String password) {
		super();
		this.name = name;
		this.surname = surname;
		JMBG = jMBG;
		this.token = token;
		this.password = password;

	}

	public Person(String name, String surname, long jMBG, int token, String password, String time, String map) {
		super();
		this.name = name;
		this.surname = surname;
		JMBG = jMBG;
		this.token = token;
		this.password = password;
		this.time = time;
		this.map = stringToMap(map);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getJMBG() {
		return JMBG;
	}

	public void setJMBG(long jMBG) {
		JMBG = jMBG;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[][] getMap() {
		return map;
	}

	public void setMap(String map[][]) {
		this.map = map;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getVrijeme() {
		return time;
	}

	public void setVrijeme(String time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (JMBG != other.JMBG)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", surname=" + surname + ", password=" + password + ", token=" + token
				+ ", JMBG=" + JMBG + ", map=" + map + ", status=" + status + ", time=" + time + ", isBlocked="
				+ isBlocked + "]";
	}

	public String mapToString(String[][] map) {
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
			int col = xy.length;
			map[i] = new String[col];
			for (int j = 0; j < col; ++j) {
				map[i][j] = xy[j];
			}
		}
		return map;
	}

	public HashMap<String, String> toMap() {
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("jmbg", JMBG + "");
		map1.put("name", name);
		map1.put("surname", surname);
		map1.put("token", token + "");
		map1.put("password", password);
		map1.put("map", mapToString(map));
		map1.put("status", status);
		map1.put("time", time);
		map1.put("blocked", isBlocked + "");
		return map1;

	}

}
