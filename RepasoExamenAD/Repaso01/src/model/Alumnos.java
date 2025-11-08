package model;

import java.io.Serializable;

public class Alumnos implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int age;

	public Alumnos(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Alumnos [name=" + name + ", age=" + age + "]";
	}
}
