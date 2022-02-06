package org.unibl.etf.model;

import java.io.File;

import org.unibl.etf.mdp.util.PropertyReader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class BaseFill {

	public static void main(String[] args) {
		
		JedisPool pool = new JedisPool(PropertyReader.readString("resources"+File.separator+"doctor.config", "JEDIS"));
		MedicalStaff med1=new MedicalStaff("Doktor1", "Doktor1", true,1);
		MedicalStaff med2=new MedicalStaff("Doktor2", "Doktor2", true,2);
		MedicalStaff med3=new MedicalStaff("Doktor3", "Doktor3", true,3);
		MedicalStaff med4=new MedicalStaff("Doktor4", "Doktor4", true,4);
		try (Jedis jedis = pool.getResource()) {
			jedis.hmset(med1.getId()+"", med1.toMap());
			jedis.hmset(med2.getId()+"", med2.toMap());
			jedis.hmset(med3.getId()+"", med3.toMap());
			jedis.hmset(med4.getId()+"", med4.toMap());
		}
		System.out.println("Baza je uspjesno popunjena sa 4 medicinska radnika!");
		pool.close();
	}

}
