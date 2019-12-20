package com.ipartek.formacion.supermercado.modelo.dao;

import java.util.List;

public interface IDAO<P> {

	/**
	 * Obtiene todos los datos 
	 * @return lista de pojos
	 * */
	List<P> getAll(); //List para que se puede implementar con el tipo de lista que se quiera
					  //<P>: tipo del objeto genérico con el que vamos a trabajar, POJO. Así podremos implementar objetos de tipo Perro, Producto... con esta interfaz
	/**
	 * Recupera un pojo por su identificador
	 * @param id identificador
	 * @return si lo encuentra, devuelve un pojo; si no, un null
	 * */
	P getById(int id);
	
	/**
	 * Elimina un pojo por su identificador
	 * @param id identificador
	 * @return devuelve el pojo que ha eliminado
	 * @throws Exception lanzada si no encuentra el identificador a eliminar o si no lo puede eliminar
	 * */
	P delete(int id) throws Exception;
	
	/**
	 * Modifica un pojo por su identificador
	 * @param pojo objeto que contiene los datos a modificar
	 * @param id identificador
	 * @return devuelve el pojo actualizado
	 * @throws Exception lanzada si no encuentra el identificador a modificar o no puede modificar el pojo
	 * */
	P update(P pojo, int id) throws Exception;
	
	/**
	 * Crea un pojo nuevo
	 * @param pojo objeto
	 * @return devuelve el pojo creado con el id nuevo
	 * @throws Exception lanzada si no ha podido crear el objeto
	 * */
	P create(P pojo) throws Exception;


}
