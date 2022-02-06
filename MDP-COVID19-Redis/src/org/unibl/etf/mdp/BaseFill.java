package org.unibl.etf.mdp;

import java.io.File;

import org.unibl.etf.mdp.util.PropertyReader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class BaseFill {

	public static void main(String[] args) {

		JedisPool pool = new JedisPool(
				PropertyReader.readString("resources" + File.separator + "redis.config", "JEDIS"));
		Person p1 = new Person("Marko", "Markovic", 9865912854871l, 288751225, "test");
		Person p2 = new Person("Darko", "Darkovic", 8712500217651l, 941123512, "test");
		Person p3 = new Person("Stanko", "Stankovic", 4321251258751l, 764900122, "test");
		Person p4 = new Person("Mirko", "Mirkovic", 5215155299014l, 100024156, "test");
		try (Jedis jedis = pool.getResource()) {
			jedis.hmset(p1.getToken() + "", p1.toMap());
			jedis.hmset(p2.getToken() + "", p2.toMap());
			jedis.hmset(p3.getToken() + "", p3.toMap());
			jedis.hmset(p4.getToken() + "", p4.toMap());
		}
		System.out.println("Baza je uspjesno popunjena sa 4 osobe!");
		pool.close();
	}

}
