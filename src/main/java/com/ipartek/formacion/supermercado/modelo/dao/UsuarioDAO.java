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
import com.ipartek.formacion.supermercado.modelo.pojo.Rol;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

	// variables y ctes:
	private final static Logger LOG = Logger.getLogger(UsuarioDAO.class);

	private static UsuarioDAO INSTANCE;

	//private static final String SQL_EXIST = "SELECT id, nombre, contrasenia FROM usuario WHERE nombre = ? AND contrasenia = ?";
	private static final String SQL_EXIST = "SELECT u.id 'id_usuario', u.nombre 'nombre_usuario', contrasenia, r.id 'id_rol', r.nombre 'nombre_rol' " +
											" FROM usuario u, rol r " + 
											" WHERE u.id_rol = r.id AND " +
											" u.nombre = ? AND contrasenia = ?;";
	
	//private static final String SQL_GET_ALL = "SELECT id, nombre, contrasenia FROM usuario ORDER BY id DESC LIMIT 500;"; 
	private static final String SQL_GET_ALL = "SELECT u.id 'id_usuario', u.nombre 'nombre_usuario', contrasenia, r.id 'id_rol', r.nombre 'nombre_rol' " +
											" FROM usuario u, rol r " + 
											" WHERE u.id_rol = r.id;";
	
	private static final String SQL_INSERT = "INSERT INTO `usuario` (`nombre`, `contrasenia`, `id_rol`) VALUES (?, ?, ?);";
	
	//private static final String SQL_GET_BY_ID = "SELECT id, nombre, contrasenia FROM usuario WHERE id = ?;"; 
	private static final String SQL_GET_BY_ID = "SELECT u.id 'id_usuario', u.nombre 'nombre_usuario', contrasenia, r.id 'id_rol', r.nombre 'nombre_rol' " +
												" FROM usuario u, rol r AND id_usuario= ? " + 
												" WHERE u.id_rol = r.id;";
	
	private static final String SQL_DELETE = "DELETE FROM usuario WHERE id = ?;";
	private static final String SQL_UPDATE = "UPDATE `usuario` SET `nombre`=?, `contrasenia`=?, `id_rol`=? WHERE  `id`=?;";
		
	
	// constructor:
	public UsuarioDAO() {
		super();
	}

	
	// patrón singleton --> https://es.wikipedia.org/wiki/Singleton --> sólo va a
	// tener 1 único objeto en toda la clase, 1 objeto de tipo ArrayList<Producto>
	public synchronized static UsuarioDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}

		return INSTANCE;
	}

	
	// implementación métodos interfaz:
	@Override
	public List<Usuario> getAll() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);) {
			
			LOG.debug(pst);
			
			try (ResultSet rs = pst.executeQuery()){
				while (rs.next()) {
					/*
					Usuario u = new Usuario();
					u.setId( rs.getInt("id"));
					u.setNombre(rs.getString("nombre"));
					u.setContrasenia(rs.getString("contrasenia"));
					lista.add(u);
					*/
					
					lista.add(mapper(rs));
				}
			} //executeQuery

		} catch (SQLException e) {
			LOG.error(e); 
		}

		return lista;
		
	}

	
	@Override
	public Usuario getById(int id) {
		Usuario registro = null;  
				
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
					registro = new Usuario();
					registro.setId(rs.getInt("id"));
					registro.setNombre(rs.getString("nombre"));
					registro.setContrasenia(rs.getString("contrasenia"));
					*/
					registro = mapper(rs);
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		
		return registro; 
	}

	
	@Override
	public Usuario delete(int id) throws Exception {
		Usuario registro = null;
		
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_DELETE);) {

			pst.setInt(1, id);
			LOG.debug(pst);
			
			//obtenemos el id antes de elimianrlo:
			registro = this.getById(id);

			//borramos el usuario:
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
	public Usuario update(Usuario pojo, int id) throws Exception {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_UPDATE);) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getContrasenia());
			pst.setInt(3, pojo.getRol().getId() ); //recogemos id rol
			pst.setInt(4, id);
			LOG.debug(pst);

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				pojo.setId(id);
			} else {
				throw new Exception ("No se encontró registro para id = " + id);
			}

		} catch (Exception e) {
			LOG.error(e);
		}
		 
		return pojo;
	}

	
	@Override
	public Usuario create(Usuario pojo) throws Exception {
		//establecemos conexión:
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
			
			pst.setString(1, pojo.getNombre()); //1er interrogante con el nombre del registro que se quiere modificar; en ese caso, Nombre
			pst.setString(2, pojo.getContrasenia());
			pst.setInt(3, pojo.getRol().getId() ); //añadimos id rol
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
			LOG.error(e);
		}
		
		return pojo;	
	}

	
	@Override
	public Usuario exist(String nombre, String contrasenia) { // con este método comprobamos que el usuario exista en la base de datos
		Usuario resul = null;

		LOG.debug("usuario = " + nombre + " contrasenia = " + contrasenia);

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_EXIST);) {

			pst.setString(1, nombre);
			pst.setString(2, contrasenia);
			LOG.debug(pst);

			// ejecución de la sql:
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) { // lo ha encontrado
					// mapear del RS al POJO:
					// inicializamos el pojo y rellenamos sus datos:
					/*
					resul = new Usuario();
					resul.setId(rs.getInt("id"));
					resul.setNombre(rs.getString("nombre"));
					resul.setContrasenia(rs.getString("contrasenia"));
					*/
					resul = mapper(rs);
				}

			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return resul;
	}
	
	
	/**
	 * Utilidad para mapear un ResultSet a un pojo o a un Usuario
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Usuario mapper(ResultSet rs) throws SQLException{
		
		Usuario u = new Usuario();
		
		u.setId(rs.getInt("id_usuario"));
		u.setNombre(rs.getString("nombre_usuario"));
		u.setContrasenia(rs.getString("contrasenia"));
		
		Rol r = new Rol();
		r.setId(rs.getInt("id_rol"));
		r.setNombre(rs.getString("nombre_rol"));
		
		u.setRol(r);
		
		return u;
	}


}
