package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.supermercado.modelo.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

public class ProductoDAO implements IDAO<Producto>{

	private static ProductoDAO INSTANCE;

	//ctes para la consulta a la base de datos:
	private static final String SQL_GET_ALL = "SELECT id, nombre, precio, imagen, descripcion, descuento FROM producto ORDER BY id DESC LIMIT 500;";
	private static final String SQL_INSERT = "INSERT INTO producto (nombre, precio, imagen, descripcion, descuento) VALUES (?, ?, ?, ?, ?);";
	private static final String SQL_GET_BY_ID = "SELECT id, nombre, precio, imagen, descripcion, descuento FROM producto WHERE id = ?;";
	private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?;";
	private static final String SQL_UPDATE = "UPDATE producto SET nombre = ?, precio = ?, imagen = ?, descripcion = ?, descuento = ? WHERE id = ?;";

	
	private ProductoDAO() {
		super();
	}
	
	
	//singleton --> https://es.wikipedia.org/wiki/Singleton --> sólo va a tener 1 único objeto en toda la clase, 1 objeto de tipo ArrayList<Producto>
	public synchronized static ProductoDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ProductoDAO();
		}

		return INSTANCE;
	}

	
	@Override
	public List<Producto> getAll() {
		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				
				Producto p = new Producto();
				p.setId( rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setPrecio( rs.getFloat("precio"));
				p.setImagen(rs.getString("imagen"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setDescuento( rs.getInt("descuento"));
				lista.add(p);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	
	@Override
	public Producto create(Producto pojo) throws Exception {
		//establecemos conexión:
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			pst.setString(1, pojo.getNombre()); //1er interrogante con el nombre del registro que se quiere modificar; en ese caso, Nombre
			pst.setFloat(2, pojo.getPrecio());
			pst.setString(3, pojo.getImagen());
			pst.setString(4, pojo.getDescripcion());
			pst.setInt(5, pojo.getDescuento());
			
			
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) { //queremos modificar un registro, así que afectará a 1 fila
				// conseguimos el ID que acabamos de crear
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					pojo.setId(rs.getInt(1));
				}
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pojo;		
	}

	
	@Override
	public Producto getById(int id) { 
		
		Producto registro = null;  
		
		//obtenemos la conexión:
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			//sustituimos parámetros en la SQL, en este caso 1º ? por id:
			pst.setInt(1, id);

			//ejecutamos la consulta:
			try (ResultSet rs = pst.executeQuery()) {
				while(rs.next()) {
					registro = new Producto();
					registro.setId(rs.getInt("id"));
					registro.setNombre(rs.getString("nombre"));
					registro.setPrecio(rs.getFloat("precio"));
					registro.setImagen(rs.getString("imagen"));
					registro.setDescripcion(rs.getString("descripcion"));
					registro.setDescuento( rs.getInt("descuento"));
				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return registro; 
	}
	
	
	@Override
	public Producto delete(int id) throws Exception {
		Producto registro = null;
		
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_DELETE);) {

			pst.setInt(1, id);
			
			//obtenemos el id antes de elimianrlo:
			registro = this.getById(id);

			//eliminamos el prodcuto:
			int affetedRows = pst.executeUpdate();
			if (affetedRows != 1) {
				registro = null; //eliminamos
				throw new Exception("No se puede eliminar " + registro);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return registro;
	}
	
	
	@Override
	public Producto update(Producto pojo, int id) throws Exception {
				
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_UPDATE);) {

			pst.setInt(1, id);
			pst.setString(2, pojo.getNombre());
			pst.setFloat(3, pojo.getPrecio());
			pst.setString(4, pojo.getImagen());
			pst.setString(5, pojo.getDescripcion());
			pst.setInt(6, pojo.getDescuento());

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				pojo.setId(id);
			} else {
				throw new Exception ("No se encontró registro para id = " + id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return pojo;
	}

}
