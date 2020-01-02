package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.modelo.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

public class ProductoDAO implements IDAO<Producto>{
	
	private final static Logger LOG = Logger.getLogger(UsuarioDAO.class);

	private static ProductoDAO INSTANCE;

	//ctes para la consulta a la base de datos:
	//private static final String SQL_GET_ALL = "SELECT id, nombre, precio, imagen, descripcion, descuento FROM producto ORDER BY id DESC LIMIT 500;";
	private static final String SQL_GET_ALL = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario' " + 
												" FROM producto p, usuario u " + 
												" WHERE p.id_usuario = u.id " + 
												" ORDER BY p.id DESC LIMIT 500;";
	//usamos el alias 'id_producto' para p.id  para distinguirlo del campo id de la tabla usuario
	
	private static final String SQL_INSERT = "INSERT INTO `producto` (`nombre`, `precio`, `imagen`, `descripcion`, `descuento`, `id_usuario`) VALUES (?, ?, ?, ?, ?, ?);";
	
	//private static final String SQL_GET_BY_ID = "SELECT id, nombre, precio, imagen, descripcion, descuento FROM producto WHERE id = ?;";
	private static final String SQL_GET_BY_ID = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario' " + 
												" FROM producto p, usuario u " + 
												" WHERE p.id_usuario = u.id AND p.id= ? " + 
												" ORDER BY p.id DESC LIMIT 500;";
	
	private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?;";
	
	//private static final String SQL_UPDATE = "UPDATE producto SET nombre = ?, precio = ?, imagen = ?, descripcion = ?, descuento = ? WHERE id = ?;";
	private static final String SQL_UPDATE = "UPDATE `producto` SET `nombre`=?, `precio`=?, `imagen`=?, `descripcion`=?, `descuento`=?, `id_usuario`=? WHERE  `id`=?;";

	//private static final String SQL_GET_ALL_BY_ID_USUARIO = "SELECT * FROM producto WHERE id_usuario = ? ORDER BY id DESC LIMIT 500;";
	private static final String SQL_GET_ALL_BY_ID_USUARIO = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario' " + 
															" FROM producto p, usuario u " + 
															" WHERE p.id_usuario = u.id AND id_usuario= ? " + 
															" ORDER BY p.id DESC LIMIT 500;";
	
	
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
				/*
				Producto p = new Producto();
				p.setId( rs.getInt("id_producto"));
				p.setNombre(rs.getString("nombre_producto"));
				p.setPrecio(rs.getFloat("precio"));
				p.setImagen(rs.getString("imagen"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setDescuento(rs.getInt("descuento"));
				
				Usuario u = new Usuario();
				u.setId(rs.getInt("id_usuario"));
				u.setNombre(rs.getString("nombre_usuario"));
				p.setUsuario(u);
				
				lista.add(p);
				*/
				
				lista.add(mapper(rs));

			}

		} catch (SQLException e) {
			LOG.error(e); //e.printStackTrace();
		}

		return lista;
	}
	
	
	@Override
	public Producto create(Producto pojo) throws Exception {
		//establecemos conexión:
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			//"INSERT INTO `producto` (`nombre`, `precio`, `imagen`, `descripcion`, `descuento`, `id_usuario`) VALUES (?, ?, ?, ?, ?, ?);";
			
			pst.setString(1, pojo.getNombre()); //1er interrogante con el nombre del registro que se quiere modificar; en ese caso, Nombre
			pst.setFloat(2, pojo.getPrecio());
			pst.setString(3, pojo.getImagen());
			pst.setString(4, pojo.getDescripcion());
			pst.setInt(5, pojo.getDescuento());
			pst.setInt(6, pojo.getUsuario().getId() ); //añadimos usuario
			
			
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) { //queremos modificar un registro, así que afectará a 1 fila
				// conseguimos el ID que acabamos de crear
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					pojo.setId(rs.getInt(1));
				}
			}
				
		} catch (SQLException e) {
			LOG.error(e); //e.printStackTrace();
		}
		
		return pojo;		
	}

	
	@Override
	public Producto getById(int id) { 
		
		Producto p = null;  
		
		//obtenemos la conexión:
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			//sustituimos parámetros en la SQL, en este caso 1º ? por id:
			pst.setInt(1, id);

			//ejecutamos la consulta:
			try (ResultSet rs = pst.executeQuery()) {
				while(rs.next()) {
					/*
					p.setId( rs.getInt("id_producto"));
					p.setNombre(rs.getString("nombre_producto"));
					p.setPrecio(rs.getFloat("precio"));
					p.setImagen(rs.getString("imagen"));
					p.setDescripcion(rs.getString("descripcion"));
					p.setDescuento(rs.getInt("descuento"));
					
					Usuario u = new Usuario();
					u.setId(rs.getInt("id_usuario"));
					u.setNombre(rs.getString("nombre_usuario"));
					p.setUsuario(u);
					*/
					p = mapper(rs);
				
				}
			}
		} catch (Exception e) {
			LOG.error(e); //e.printStackTrace();
		}
		
		return p; 
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
			LOG.error(e); //e.printStackTrace();
		}
		
		return registro;
	}
	
	
	@Override
	public Producto update(Producto pojo, int id) throws Exception {
						
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_UPDATE);) {

			//mismo orden que en la sql: "UPDATE `producto` SET `nombre`=?, `precio`=?, `imagen`=?, `descripcion`=?, `descuento`=?, `id_usuario`=? WHERE  `id`=?;";
			pst.setString(1, pojo.getNombre());
			pst.setFloat(2, pojo.getPrecio());
			pst.setString(3, pojo.getImagen());
			pst.setString(4, pojo.getDescripcion());
			pst.setInt(5, pojo.getDescuento());
			pst.setInt(6, pojo.getUsuario().getId());
			pst.setInt(7, id);


			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				pojo.setId(id);
			} else {
				throw new Exception ("No se encontró registro para id = " + id);
			}
		}
		 
		return pojo;
	}
	
	
	
	public List<Producto> getAllByIdUsuario(int usuarioId) {
		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_ID_USUARIO);
				) {
			
			//sustituimos parámetros en la SQL, en este caso 1º ? por id:
			pst.setInt(1, usuarioId);
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				/*
				Producto p = new Producto();
				
				p.setId(rs.getInt("id_producto"));
				p.setNombre(rs.getString("nombre_producto"));
				p.setPrecio(rs.getFloat("precio"));
				p.setImagen(rs.getString("imagen"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setDescuento(rs.getInt("descuento"));
				*/
				
				lista.add(mapper(rs));

			}

		} catch (SQLException e) {
			LOG.error(e); //e.printStackTrace();
		}

		return lista;
	}
	
	
	/**
	 * Utilidad para mapear un ResultSet a un pojo o a un Producto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Producto mapper(ResultSet rs) throws SQLException{
		
		Producto p = new Producto();
		p.setId( rs.getInt("id_producto"));
		p.setNombre(rs.getString("nombre_producto"));
		p.setPrecio(rs.getFloat("precio"));
		p.setImagen(rs.getString("imagen"));
		p.setDescripcion(rs.getString("descripcion"));
		p.setDescuento(rs.getInt("descuento"));
		
		Usuario u = new Usuario();
		u.setId(rs.getInt("id_usuario"));
		u.setNombre(rs.getString("nombre_usuario"));
		p.setUsuario(u);
		
		return p;
	}

}
