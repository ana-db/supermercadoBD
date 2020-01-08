package com.ipartek.formacion.supermercado.modelo.pojo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Categoria {
	
	private int id;
	
	@NotEmpty
	@Size(min=1, max=100)
	private String nombre;
	
	
	//constructores:
	public Categoria(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Categoria() {
		super();
		this.id = 0;
		this.nombre = "";
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


	//toString
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
	
}
