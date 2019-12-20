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
	private static final String SQL_GET_ALL = "SELECT id, nombre FROM producto ORDER BY id DESC LIMIT 500;";
	private static final String SQL_INSERT = "INSERT INTO producto (nombre) VALUES (?);";
	private static final String SQL_GET_BY_ID = "SELECT p.id, p.nombre FROM producto as p WHERE p.id = ?;";
	private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?;";
	private static final String SQL_UPDATE = "UPDATE producto SET nombre= ? WHERE id = ?;";

	
	private ProductoDAO() {
		super();
	}
	
	
	//singleton --> https://es.wikipedia.org/wiki/Singleton --> sólo va a tener 1 único objeto en toda la clase, 1 objeto de tipo ArrayProductoDAO
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
				lista.add(p);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	
	@Override
	public Producto create(Producto pojo) throws Exception {
		
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			pst.setString(1, pojo.getNombre());
			
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
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
		Producto resul = new Producto();
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					resul = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resul;
	}
	
	
	@Override
	public Producto delete(int id) throws Exception {
		Producto resul = new Producto();
		
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_DELETE);) {

			pst.setInt(1, id);

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				resul = null; //¿?  
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resul;
	}
	
	
	@Override
	public Producto update(Producto pojo, int id) throws Exception {
		Producto resul = new Producto();
		
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_UPDATE);) {

			pst.setString(1, pojo.getNombre());
			pst.setInt(2, pojo.getId());

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				resul = null; //¿?  
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resul;
	}

		
	private Producto mapper(ResultSet rs) throws SQLException {
		
		Producto p = new Producto();
		p.setId(rs.getInt("id"));
		p.setNombre(rs.getString("nombre"));
			
		/*
		Rol rol = new Rol();
		rol.setId( rs.getInt("id_rol"));
		rol.setNombre( rs.getString("nombre_rol"));
		p.setRol(rol);
		*/
		
		return p;
	}

}
