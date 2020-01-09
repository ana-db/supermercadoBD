package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
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

				while (rs.next()) {
					/*
					Categoria c = new Categoria();
					c.setId(rs.getInt("id"));
					c.setNombre(rs.getString("nombre"));
					registros.add(c);
					*/
					registros.add(mapper(rs));
				}

			}

		} catch (SQLException e) {
			LOG.error(e);
		}

		return registros;
		
	}

	@Override
	public Categoria getById(int id) {
		
		LOG.trace("Recuperar la categoría según su id" + id);
		
		Categoria registro = new Categoria();

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_getbyid(?) }")) {
			
			//parámetro de entrada (1era y única ?):
			cs.setInt(1, id);
			LOG.debug(cs);

			// ejecutamos dentro de un try para que se cierre después de ejecutarse:
			try (ResultSet rs = cs.executeQuery()) {

				if (rs.next()) { //como sólo va a haber un resultado podríamos poner un while o un if
					
					registro = mapper(rs);
					
				}else {
					registro = null; //no podemos lanzar excepción porque no lo definimos así en IDAO
				}
					
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return registro;
		
	}

	@Override
	public Categoria delete(int id) throws Exception {    //eliminando categoría

		LOG.trace("Eliminar categoría por id" + id);
		
		//recuperamos la categoría antes de eliminarla:
		Categoria registro = getById(id);
		if(registro == null) {
			throw new Exception("Registro no encontrado " + id);
		}

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):	
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_delete(?) }")) {

			//parámetro de entrada (1era y única ?):
			cs.setInt(1, id);
			LOG.debug(cs);

			//eliminamos el producto:
			cs.executeUpdate();

		} /* catch (Exception e) {
			LOG.error(e); 
		} //quitamos este catch para que no se capture la excepción si se quiere eliminar una categoría con porductos asociados */ 
		
		return registro;
	
	}

	@Override
	public Categoria update(Categoria pojo, int id) throws Exception {
		
		LOG.trace("Modificar categoría por id " + id + " " + pojo);
		
		Categoria registro = pojo; //IMPORTANTE: 
								   //toda variable primitiva si se hace una copia se copia el valor. 
								   //Toda variable que no sea primitiva, se pasa una copia de una dirección de memoria cuando se iguala, por eso, resgistro y pojo tienen el mismo valor
								   //Usaremos el método clone si queremos copiar el contenido de 2 variables no primitivas sin que apunten a la misma dir de memoria
								   //cualquier variable primitiva se pasa una copia por valor, por lo que fuera de esa función no ve alterado su valor

		// conseguimos la conexión (getConnection) y hacemos la llamada al procedimiento almacenado (prepareCall):	
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_update(?, ?) }")) {

			//parámetros de entrada (1ª y 2ª ?):
			//OJO con el orden de los parámetros: en la SQL tenemos p_id como parámetro 1 y p_nombre como parámetro 2, hay que ponerlos igual
			cs.setInt(1, id);
			cs.setString(2, pojo.getNombre());
			
			LOG.debug(cs);			
			
			//ejecutamos el procedimiento almacenado executeUpdate, CUIDADO porque NO es una SELECT --> executeQuery:
			if(cs.executeUpdate() == 1) {
				pojo.setId(id);
			}else {
				throw new Exception("No se puede modificar el registro " + pojo + " por id " + id);
			}

		}
		 
		return registro;
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
	
	
	//mapper:
	private Categoria mapper(ResultSet rs) throws SQLException{
		
		Categoria c = new Categoria();
		c.setId(rs.getInt("id"));
		c.setNombre(rs.getString("nombre"));
		
		return c;
	}

}
