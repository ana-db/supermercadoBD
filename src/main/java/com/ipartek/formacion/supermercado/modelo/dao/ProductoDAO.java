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
import com.ipartek.formacion.supermercado.modelo.pojo.Categoria;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

public class ProductoDAO implements IProductoDAO{
	
	private final static Logger LOG = Logger.getLogger(ProductoDAO.class);

	private static ProductoDAO INSTANCE;

	//ctes para la consulta a la base de datos:
/*	private static final String SQL_GET_ALL = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario' " + 
												" FROM producto p, usuario u " + 
												" WHERE p.id_usuario = u.id " + 
												" ORDER BY p.id DESC LIMIT 500;";	*/
	//usamos el alias 'id_producto' para p.id  para distinguirlo del campo id de la tabla usuario
	
	private static final String SQL_GET_ALL = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario', c.id 'id_categoria', c.nombre 'nombre_categoria' " + 
												" FROM producto p, usuario u, categoria c " + 
												" WHERE p.id_usuario = u.id AND p.id_categoria = c.id" + 
												" ORDER BY p.id DESC LIMIT 500;";
	
//	private static final String SQL_INSERT = "INSERT INTO `producto` (`nombre`, `precio`, `imagen`, `descripcion`, `descuento`, `id_usuario`) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SQL_INSERT = "INSERT INTO `producto` (`nombre`, `precio`, `imagen`, `descripcion`, `descuento`, `id_usuario`, `id_categoria`) VALUES (?, ?, ?, ?, ?, ?, ?);";
	
/*	private static final String SQL_GET_BY_ID = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario' " + 
												" FROM producto p, usuario u " + 
												" WHERE p.id_usuario = u.id AND p.id= ? " + 
												" ORDER BY p.id DESC LIMIT 500;";	*/
	private static final String SQL_GET_BY_ID = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario', c.id 'id_categoria', c.nombre 'nombre_categoria' " + 
												" FROM producto p, usuario u, categoria c " + 
												" WHERE p.id_usuario = u.id AND p.id_categoria = c.id AND p.id= ? " + 
												" ORDER BY p.id DESC LIMIT 500;";
	
	private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?;";
	
	private static final String SQL_UPDATE = "UPDATE `producto` SET `nombre`=?, `precio`=?, `imagen`=?, `descripcion`=?, `descuento`=?, `id_usuario`=? WHERE  `id`=?;";

	//private static final String SQL_GET_ALL_BY_ID_USUARIO = "SELECT * FROM producto WHERE id_usuario = ? ORDER BY id DESC LIMIT 500;";
/*	private static final String SQL_GET_ALL_BY_ID_USUARIO = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario' " + 
															" FROM producto p, usuario u " + 
															" WHERE p.id_usuario = u.id AND id_usuario= ? " + 
															" ORDER BY p.id DESC LIMIT 500;";	*/
	private static final String SQL_GET_ALL_BY_ID_USUARIO = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario', c.id 'id_categoria', c.nombre 'nombre_categoria' " + 
															" FROM producto p, usuario u, categoria c " + 
															" WHERE p.id_usuario = u.id AND p.id_categoria = c.id AND id_usuario= ? " + 
															" ORDER BY p.id DESC LIMIT 500;";
	
	
	// 07/01/2020: SQLs para utilizar con la interfaz nueva para ProductoDAO, IProductoDAO:
	private static final String SQL_UPDATE_BY_USER = "UPDATE `producto` SET `nombre`=?, `precio`=?, `imagen`=?, `descripcion`=?, `descuento`=? WHERE `id`=? AND `id_usuario`=?;";
	private static final String SQL_DELETE_BY_USER = "DELETE FROM producto WHERE id = ? AND id_usuario = ?;";
/*	private static final String SQL_GET_BY_ID_BY_USER = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario' " + 
														" FROM producto p, usuario u " + 
														" WHERE p.id_usuario = u.id AND p.id= ? AND u.id= ?" + 
														" ORDER BY p.id DESC LIMIT 500;"; 	*/
	private static final String SQL_GET_BY_ID_BY_USER = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', p.descripcion, p.imagen, p.precio, p.descuento, u.id 'id_usuario', u.nombre 'nombre_usuario', c.id 'id_categoria', c.nombre 'nombre_categoria' " + 
														" FROM producto p, usuario u, categoria c " + 
														" WHERE p.id_usuario = u.id AND p.id_categoria = c.id AND p.id= ? AND u.id= ?" + 
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
			pst.setInt(7, pojo.getCategoria().getId() ); //añadimos categoria
			LOG.debug(pst);
			
			
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
			LOG.debug(pst);

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
			LOG.debug(pst);
			
			//obtenemos el id antes de elimianrlo:
			registro = this.getById(id);

			//eliminamos el prodcuto:
			int affetedRows = pst.executeUpdate();
			if (affetedRows != 1) {
				registro = null; //eliminamos
				throw new Exception("No se puede eliminar " + registro);
			}

		} catch (Exception e) {
			LOG.error(e); 
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
			LOG.debug(pst);

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				pojo.setId(id);
			} else {
				throw new Exception ("No se encontró registro para id = " + id);
			}
		}
		 
		return pojo;
	}
	
	
	public List<Producto> getAllByIdUsuario(int idUsuario) {
		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_ID_USUARIO);
				) {
			
			//sustituimos parámetros en la SQL, en este caso 1º ? por id:
			pst.setInt(1, idUsuario);
			LOG.debug(pst);
			
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
		p.setId(rs.getInt("id_producto"));
		p.setNombre(rs.getString("nombre_producto"));
		p.setPrecio(rs.getFloat("precio"));
		p.setImagen(rs.getString("imagen"));
		p.setDescripcion(rs.getString("descripcion"));
		p.setDescuento(rs.getInt("descuento"));
		
		Usuario u = new Usuario();
		u.setId(rs.getInt("id_usuario"));
		u.setNombre(rs.getString("nombre_usuario"));
		p.setUsuario(u);
		
		Categoria c = new Categoria();
		c.setId(rs.getInt("id_categoria"));
		c.setNombre(rs.getString("nombre_categoria"));
		p.setCategoria(c);
		
		return p;
	}

	
	// 07/01/2020: implementación métodos de la interfaz nueva para ProductoDAO, IProductoDAO:
	@Override
	public Producto getByIdByUser(int id, int id_usuario) throws ProductoException {
		Producto p = null;  
		
		//obtenemos la conexión:
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID_BY_USER)) {

			//sustituimos parámetros en la SQL, en este caso 1º ? es el id del producto y el 2º ? es el id del usuario:
			pst.setInt(1, id);
			pst.setInt(2, id_usuario);
			LOG.debug(pst);

			//ejecutamos la consulta:
			try (ResultSet rs = pst.executeQuery()) { //se ejecuta la consulta
				if(rs.next()) { //si tiene resultado, llamamos a la función mapper para guardar los parámetros
					
					p = mapper(rs);
				
				}else {
					LOG.warn("No se ha encontrado el producto");;
					throw new ProductoException(ProductoException.EXCEPTION_UNAUTORIZED);
				}
			}//try 2 
		}catch (SQLException e) {
			throw new ProductoException(ProductoException.EXCEPTION_UNAUTORIZED);
		}
		
		return p; 
	}


	@Override
	public Producto updateByUser(int id, int id_usuario, Producto pojo) throws SQLException, ProductoException {
		
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_UPDATE_BY_USER);) {

			//mismo orden que en la sql: "UPDATE `producto` SET `nombre`=?, `precio`=?, `imagen`=?, `descripcion`=?, `descuento`=? WHERE `id`=? AND `id_usuario`=?;";
			pst.setString(1, pojo.getNombre());
			pst.setFloat(2, pojo.getPrecio());
			pst.setString(3, pojo.getImagen());
			pst.setString(4, pojo.getDescripcion());
			pst.setInt(5, pojo.getDescuento());
			pst.setInt(6, id);
			pst.setInt(7, id_usuario);
			LOG.debug(pst);
			
			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				LOG.debug("Producto modificado correctamente");
				pojo.setId(id);
				
			} else {
				LOG.debug("No se encontró registro para id = " + id + " con id_usuario = " + id_usuario + ". El objeto no pertenece a ese usuario");
				throw new ProductoException(ProductoException.EXCEPTION_UNAUTORIZED); //le pasamos el mensaje que hemos definido como una cte
			}
			
/*		} catch (Exception e) {
			LOG.error(e); 
		}
*/		 
		}catch (SQLException e) {
			
			LOG.debug("El nombre del producto ya existe en la base de datos, elige otro");
			throw e;
		}	
			
		return pojo;
	}


	@Override
	public Producto deleteByUser(int id, int id_usuario) throws ProductoException {
		
		Producto registro = null;
		
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_USER);) {

			pst.setInt(1, id);
			pst.setInt(2, id_usuario);
			LOG.debug(pst);
			
			//obtenemos el id antes de eliminarlo:
			registro = this.getById(id);
	
			//eliminamos el producto:
			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				
				LOG.debug("Registro eliminado");
				
			}else {
	
				LOG.warn("Un usuario ha intentado comprar un producto que no le corresponde");
				throw new ProductoException(ProductoException.EXCEPTION_UNAUTORIZED); //le pasamos el mensaje que hemos definido como una cte
			}
		}catch (SQLException e) {
			throw new ProductoException(ProductoException.EXCEPTION_UNAUTORIZED);
		}
		
		return registro;
		
	}

}
