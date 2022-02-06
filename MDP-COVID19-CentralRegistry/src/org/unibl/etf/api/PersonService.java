package org.unibl.etf.api;

import java.util.Map;

import org.unibl.etf.mdp.PropertyReader;
import org.unibl.etf.model.Person;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PersonService {

	public static JedisPool pool = new JedisPool(PropertyReader.readString("config.properties", "JEDIS"));
	public int n = PropertyReader.readInt("N");
	public int p = PropertyReader.readInt("P");
	public int k = PropertyReader.readInt("K");

	public String config = "N=" + String.valueOf(n) + "#P=" + String.valueOf(p) + "#K=" + String.valueOf(k);

	public String getConfig() {
		return config;
	}

	public Person getPersonByToken(int token) {
		Person p = null;
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> map = jedis.hgetAll(token + "");
			if (!map.isEmpty()) {
				p = new Person(map.get("name"), map.get("surname"), Long.valueOf(map.get("jmbg")),
						Integer.valueOf(map.get("token")), map.get("password"), map.get("time"), map.get("map"));
			}

		}
		return p;
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

	public boolean setPosition(int token, String[][] maps) {

		try (Jedis jedis = pool.getResource()) {
			jedis.hset(token + "", "map", mapToString(maps));
			return true;
		}
	}

	public boolean setTime(int token, String time) {
		try (Jedis jedis = pool.getResource()) {
			String timeOld = jedis.hget(token + "", "time");
			if (!"".equals(timeOld))
				timeOld += "," + time;
			else {
				timeOld += time;
			}
			jedis.hset(token + "", "time", timeOld);
			return true;

		}
	}

	public String getTime(int token) {
		String time = "";
		try (Jedis jedis = pool.getResource()) {
			time = jedis.hget(token + "", "time");
		}
		return time;

	}

	public String[][] getPosition(int token) {
		String[][] position = null;
		try (Jedis jedis = pool.getResource()) {
			String result = jedis.hget(token + "", "map");
			position = stringToMap(result);

		}
		return position;

	}

	public boolean setMessage(String mess, int token) {
		try (Jedis jedis = pool.getResource()) {
			jedis.hset(token + "", "status", mess);
			return true;
		}
	}

	public String getMessage(int token) {
		String message = "";
		try (Jedis jedis = pool.getResource()) {
			message = jedis.hget(token + "", "status");
		}
		return message;
	}

}
