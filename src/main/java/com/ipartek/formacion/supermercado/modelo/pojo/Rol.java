package com.ipartek.formacion.supermercado.modelo.pojo;

public class Rol {
	
	//variables y ctes:
	public static final int ROL_USUARIO = 1;
	public static final int ROL_ADMIN = 2;
	
	private int id;
	private String nombre;
	
	
	//constructores:
	public Rol(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Rol() {
		super();
		this.id = ROL_USUARIO;
		this.nombre = "usuario";
	}
	
	
	//getters y setters:
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	//toString:
	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
	

}
