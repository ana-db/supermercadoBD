package com.ipartek.formacion.supermercado.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

public class ProductoDAO implements IDAO<Producto>{

	private static ProductoDAO INSTANCE;
	private ArrayList<Producto> registros;
	private static int indice = 0; //por seguridad se suele indicar que empiece por cualquier núm excpeto el 1
	
	
	
	//capar para que no se pueda hacer new de esta clase
	private ProductoDAO() {
		super();
		registros = new ArrayList<Producto>();
		
		//TODO 10 productos 1 poco más elaborados (con distintos nombres, imagenes, descuentos...)
		// metemos 10 productos:
		/* lo vamos a hcer desde el init del controlador
		for(int i=0; i<=10; i++) {
			registros.add(new Producto());
			indice++;
		}
		*/
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
		return registros;
	}

	@Override
	public Producto getById(int id) { 
		Producto resul = null; 
		
		for(Producto producto : registros) {
			if (id == producto.getId()) {
				resul = producto;
				break;
			}
		}
		
		return resul; //los métodos sólo devuelven 1 resultado
	}

	@Override
	public Producto delete(int id) throws Exception {
		
		Producto resul = null; 
		
		for(Producto producto : registros) {
			if (id == producto.getId()) {
				resul = producto;
				registros.remove(producto);
				break;
			}
		}
		
		if (resul == null) {
			throw new Exception("Producto no encontrado por su id " + id);
		}
		
		return resul;
	}

	@Override
	public Producto update(Producto pojo, int id) throws Exception {
		Producto resul = null; 
		
		for (int i = 0; i < registros.size(); i++) {
			if (id == registros.get(i).getId()) { //registros.get(i) nos devuelve un objeto perro completo
				registros.remove(i);
				registros.add(pojo);
				break;
			}
		}
		
		if (resul == null) {
			throw new Exception("El producto no se ha encontrado" + id);
		}
		
		return resul;
	}

	@Override
	public Producto create(Producto pojo) throws Exception {
		Producto resul = pojo; 
		
		//TODO comprobar campos del pojo
		
		if (pojo != null) {
			pojo.setId(++indice);
			registros.add(pojo);
		}
		else {
			throw new Exception("No se ha podido crear: Producto NULL sin datos");
		}
		
		return resul;
	}
	
}
