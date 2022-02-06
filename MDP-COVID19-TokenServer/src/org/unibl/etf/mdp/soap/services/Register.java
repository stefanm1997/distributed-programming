package org.unibl.etf.mdp.soap.services;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.unibl.etf.mdp.model.MedicalStaff;
import org.unibl.etf.mdp.model.Person;
import org.unibl.etf.mdp.util.PropertyReader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Register {

	public static JedisPool pool = new JedisPool(PropertyReader.readString("config.properties", "JEDIS"));
	public static boolean flagJMBG = true, isFirst = true;
	public static ArrayList<MedicalStaff> list = new ArrayList<MedicalStaff>();

	public int createToken(String name, String surname, long JMBG) {
		boolean flag = true, flag2 = true;
		int token = 0;
		while (flag) {
			try (Jedis jedis = pool.getResource()) {
				// provjera da li vec postoji covjek sa tokenom,ako postoji onda ostaje taj
				// token(provjera na osnovu jmbg)
				Set<String> tokens = jedis.keys("*"); // izlistaj sve tokene
				/*
				 * Object[] tokenArray = tokens.toArray(); String[] stringArray =
				 * Arrays.copyOf(tokenArray, tokenArray.length, String[].class);// pretvori u
				 * string// radi lakseg rada
				 */
				for (String tok : tokens) {
					String jmbg = jedis.hget(tok, "jmbg");// uzima jmbg na osnovu tokena
					if (jmbg != null && JMBG == Long.parseLong(jmbg)) {// ako imaju iste jmbg, znaci da je vec
																		// registrovan
						token = Integer.valueOf(tok);
						Person p = getPersonByToken(token);
						// provjera da li je iskoristen jmbg, tj. da li je jedinstven jmbg
						if (name.equals(p.getName()) && surname.equals(p.getSurname())) {// ako je isto ime i prezime
																							// koje se unosi i ime i
																							// prezime dobijeno iz
																							// jmbg-a, to je dobro,
																							// prekini posao
							System.out.println("Token kad su jmbgovi jednaki: " + token);
							flag2 = false;
							flag = false;
						} else {// ako nije znaci da neko pokusava unijeti drugacije ime ili prezime na isti
								// jmbg koji je vec iskoriste, sto nije moguce,vrati token kao -1 i ponovo unesi
							System.out.println("JMBG vec iskoristen, ali pod drugim imenom");
							token = -1;
							flag2 = false;
							flag = false;
						}
					}
				}
				if (flag2) {
					Random rand = new Random();
					token = rand.nextInt();
					Person p = new Person(name, surname, JMBG, token, "test");
					Map<String, String> map = jedis.hgetAll(token + "");
					if (map.isEmpty()) {
						flag = false;
						jedis.hmset(token + "", p.toMap());
					}
				}
			}
		}
		return token;
	}

	public String listAllTokens() {
		String token = "";
		try (Jedis jedis = pool.getResource()) {
			Set<String> tokens = jedis.keys("*");
			for (String tok : tokens) {
				String pom = jedis.hget(tok, "token");
				if (pom != null) {
					token += jedis.hget(tok, "token") + " ";
				}
			}
		}
		return token;
	}

	public boolean checkOneToken(int token) {
		try (Jedis jedis = pool.getResource()) {
			String result = jedis.hget(token + "", "token");
			if (result == null)
				return false;
			return true;
		}
	}

	// brisanje tokena
	public void deleteToken(int token) {
		try (Jedis jedis = pool.getResource()) {
			String result = jedis.hget(token + "", "token");
			System.out.println("Izbrisan token" + result);
			jedis.del(result);
		}
	}

	// dobijanje osobe na osnovu tokena,treba za logovanje da se provjeri lozinka
	public Person getPersonByToken(int token) {
		Person p = null;
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> map = jedis.hgetAll(token + "");
			if (!map.isEmpty()) {
				p = new Person(map.get("name"), map.get("surname"), Long.valueOf(map.get("jmbg")),
						Integer.valueOf(map.get("token")), map.get("password"));
			}

		}
		return p;
	}

	public void changePassword(int token, String password) {
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> map = jedis.hgetAll(token + "");
			if (!map.isEmpty()) {
				map.put("password", password);
				System.out.println("Ovo je lozinka posle odradjene promjene: " + map.get("password"));
				jedis.hmset(token + "", map);
			}

		}

	}

	public String getMedicalStaff() {
		MedicalStaff ms = null;
		String result = "";
		try (Jedis jedis = pool.getResource()) {
			Set<String> id = jedis.keys("*");
			for (String i : id) {
				Map<String, String> map = jedis.hgetAll(i + "");
				if (!map.isEmpty()) {
					if (map.get("token") == null) {
						ms = new MedicalStaff(map.get("name"), map.get("surname"), Boolean.valueOf(map.get("active")),
								Integer.valueOf(map.get("id")));
						result += ms + "#\n";
					}
				}

			}
		}
		return result;
	}

	public void setMedicalNotActive(int id) {
		boolean flag = false;
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> map = jedis.hgetAll(id + "");
			if (!map.isEmpty()) {
				map.put("active", flag + "");
				jedis.hmset(id + "", map);
			}
		}
	}

	public void setMedicalActive(int id) {
		boolean flag = true;
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> map = jedis.hgetAll(id + "");
			if (!map.isEmpty()) {
				map.put("active", flag + "");
				jedis.hmset(id + "", map);
			}
		}
	}

	public static void main(String[] args) {

	}

}
