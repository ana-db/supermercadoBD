package com.ipartek.formacion.supermercado.modelo.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Producto {
	
	public static final int DESCUENTO_MIN = 0;
	public static final int DESCUENTO_MAX = 100;
	
	private int id;
	
	@NotNull //validaciones a nivel de pojo
	@NotBlank
	@Size(min = 2, max = 50)
	private String nombre;
	
	private float precio;
	private String imagen;
	
	@Size(min = 2, max = 150)
	private String descripcion;
	
	@Min(0)
	@Max(100)
	private int descuento;
	 
	
	//constructores: 
	public Producto() {
		super();
		this.id = 0;
		this.nombre = "";
		this.precio = 0;
		this.imagen = "https://image.flaticon.com/icons/png/512/372/372627.png";
		this.descripcion = "";
		this.descuento = DESCUENTO_MIN;
	}
	
	public Producto(int id, String nombre, float precio, String imagen, String descripcion, int descuento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.descuento = descuento;
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

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	
	//métodos propios:
	public float getPrecioDescuento()
	{
		
		return(this.precio - (this.precio * this.descuento / 100) );
	}
		
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", imagen=" + imagen
				+ ", descripcion=" + descripcion + ", descuento=" + descuento + "]";
	}
	
	
	
}
