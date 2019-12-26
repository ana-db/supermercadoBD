package com.ipartek.formacion.supermercado.modelo.pojo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Usuario {

	//variables:
	private int id;
	
	@NotBlank 
	@Size(min = 2, max = 50)
	private String nombre;
	
	@NotBlank 
	@Size(min = 2, max = 50)
	private String contrasenia;
	
	
	//constructores:
	public Usuario(int id, String nombre, String contrasenia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
	}
	
	public Usuario() {
		super();
		this.id = 0;
		this.nombre = "";
		this.contrasenia = "";
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

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	
	//toString
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + "]";
	}
	
	
}
