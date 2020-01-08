package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.modelo.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Categoria;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

public class CategoriaDAO implements ICategoriaDAO {

	private final static Logger LOG = Logger.getLogger(CategoriaDAO.class);

	private static CategoriaDAO INSTANCE;

	// singleton --> https://es.wikipedia.org/wiki/Singleton --> sólo va a tener 1
	// único objeto en toda la clase, 1 objeto de tipo ArrayList<Categoria>
	public synchronized static CategoriaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CategoriaDAO();
		}

		return INSTANCE;
	}

	@Override
	public List<Categoria> getAll() {
		LOG.trace("Recuperar todas las categorías");
		List<Categoria> registros = new ArrayList<Categoria>();

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento
		// almacenado (prepareCall):
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_getall }")) {
			LOG.debug(cs);

			// ejecutamos dentro de un try para que se cierre después de ejecutarse:
			try (ResultSet rs = cs.executeQuery()) {
				// TODO mapper

				while (rs.next()) {
					Categoria c = new Categoria();
					c.setId(rs.getInt("id"));
					c.setNombre(rs.getString("nombre"));
					registros.add(c);
				}

			}

		} catch (SQLException e) {
			LOG.error(e);
		}

		return registros;
	}

	@Override
	public Categoria getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria delete(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria update(Categoria pojo, int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria create(Categoria pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}