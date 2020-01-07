package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.SQLException;
import java.util.List;

import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

public interface IProductoDAO extends IDAO<Producto>{
	
	/**
	 * Lista de los productos de un usuario
	 * @param idUsuario int identificador del usuario
	 * @return List<Producto>, lista inicializada en caso de que no tenga productos
	 */
	List<Producto> getAllByIdUsuario(int idUsuario);
	
	
	/**
	 * Recupera un producto de un usuario concreto
	 * @param idProducto
	 * @param idUsuario
	 * @return Producto si lo encuentra, null si no lo encuentra
	 * @throws ProductoException, lanza esta excepción cuando intenta recuperar un producto que no pertenece al usuario
	 */
	Producto getByIdByUser(int idProducto, int idUsuario) throws ProductoException;
	
	
	/**
	 * Modifica un producto de un Usuario concreto
	 * @param idProducto
	 * @param idUsuario
	 * @param pojo
	 * @return
	 * @throws ProductoException, lanza esta excepción cuando intenta modificar un producto que no pertenece al usuario
	 */
	public Producto updateByUser(int idProducto, int idUsuario, Producto pojo) throws SQLException, ProductoException;
	
	
	/**
	 * Elimina un producto de un @class Usuario concreto
	 * @param idProducto
	 * @param idUsuario
	 * @return el @class Producto eliminado si lo encuentra, si no lo encuentra lanza ProductoException
	 * @throws ProductoException, lanza esta excepción
	 * 			<ol>
	 * 				<li> cuando intenta eliminar un producto que no pertenece al usuario </li>
	 * 				<li> cuando no encuentra el producto por su id para ese usuario idUsuario</li>
	 * 			</ol>
	 */
	public Producto deleteByUser(int idProducto, int idUsuario) throws SQLException, ProductoException;

}
