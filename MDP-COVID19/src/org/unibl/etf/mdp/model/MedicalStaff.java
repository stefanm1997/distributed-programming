package org.unibl.etf.mdp.model;

import java.util.HashMap;

public class MedicalStaff {

	private String name, surname;
	private boolean active;
	private int id;

	public MedicalStaff() {
		super();
	}

	public MedicalStaff(String name, String surname, boolean active, int id) {
		super();
		this.name = name;
		this.surname = surname;
		this.active = active;
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalStaff other = (MedicalStaff) obj;
		if (active != other.active)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedicalStaff [name=" + name + ", surname=" + surname + ", active=" + active + ", id=" + id + "]";
	}

	public HashMap<String, String> toMap() {
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("id", id + "");
		map1.put("name", name);
		map1.put("surname", surname);
		map1.put("active", active + "");
		return map1;

	}
}
