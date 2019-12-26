package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.modelo.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

	// variables y ctes:
	private final static Logger LOG = Logger.getLogger(UsuarioDAO.class);

	private static UsuarioDAO INSTANCE;

	private static final String SQL_EXIST = "SELECT id, nombre, contrasenia FROM usuario WHERE nombre = ? AND contrasenia = ?";

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario delete(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario update(Usuario pojo, int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario create(Usuario pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario exist(String nombre, String contrasenia) { // comprobamos que el usuario exista en la base de datos
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
					resul = new Usuario();
					resul.setId(rs.getInt("id"));
					resul.setNombre(rs.getString("nombre"));
					resul.setContrasenia(rs.getString("contrasenia"));
				}

			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return resul;
	}

}
