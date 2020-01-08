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

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):
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
		// TODO tipo getAll pero con 1 parámetro
		
		//Categoria registro = null;
		Categoria registro = new Categoria();

		LOG.trace("Recuperar la categoría según su id");

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_getbyid(?) }")) {
			
			//parámetro de entrada (1era y única ?):
			cs.setInt(1, id);
			LOG.debug(cs);

			// ejecutamos dentro de un try para que se cierre después de ejecutarse:
			try (ResultSet rs = cs.executeQuery()) {
				// TODO mapper

				while (rs.next()) {
					
					registro.setId(rs.getInt("id"));
					registro.setNombre(rs.getString("nombre"));
					
				}

			}

		} catch (SQLException e) {
			LOG.error(e);
		}

		return registro;
		
	}

	@Override
	public Categoria delete(int id) throws Exception {    //eliminando categoría

		Categoria registro = null;
		
		LOG.trace("Eliminar categoría");

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):	
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_delete(?) }")) {

			//parámetro de entrada (1era y única ?):
			cs.setInt(1, id);
			LOG.debug(cs);
			
			//obtenemos el id antes de eliminarlo:
			registro = this.getById(id);

			//eliminamos el producto:
			int affetedRows = cs.executeUpdate();
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
	public Categoria update(Categoria pojo, int id) throws Exception {
		LOG.trace("Modificar categoría");

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):	
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_update(?, ?) }")) {

			//parámetros de entrada (1ª y 2ª ?):
			cs.setString(1, pojo.getNombre());
			cs.setInt(2, id);
			
			//parámetro de salida (2ª ?), será el id de la categoría:
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			
			LOG.debug(cs);
			
			//ejecutamos el procedimiento almacenado executeUpdate, CUIDADO porque NO es una SELECT --> executeQuery:
			cs.executeUpdate();
			
			//una vez ejecutado, podemos recuperar el parámetro de salida (2ª ?):
			pojo.setId(cs.getInt(2));
			

		}
		 
		return pojo;
	}

	@Override
	public Categoria create(Categoria pojo) throws Exception {
		
		Categoria registro = pojo; //devolvemos el mismo parámetro que hemos recibido
		
		LOG.trace("Insertar nueva categorías" + pojo);

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):	
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_insert(?, ?) }")) {
			
			//parámetro de entrada (1ª ?):
			cs.setString(1, pojo.getNombre());
			
			//parámetro de salida (2ª ?):
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			
			LOG.debug(cs);
			
			//ejecutamos el procedimiento almacenado executeUpdate, CUIDADO porque NO es una SELECT --> executeQuery:
			cs.executeUpdate();
			
			//una vez ejecutado, podemos recuperar el parámetro de salida (2ª ?):
			pojo.setId(cs.getInt(2));
		}	
			
		return registro;
	}

}
