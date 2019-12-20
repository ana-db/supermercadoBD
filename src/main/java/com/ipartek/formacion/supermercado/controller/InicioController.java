package com.ipartek.formacion.supermercado.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

/**
 * Servlet implementation class InicioController
 */
@WebServlet("/inicio")
public class InicioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(InicioController.class);
	private static ProductoDAO dao;
      
	
	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		dao = ProductoDAO.getInstance();
		
		//public Producto(int id, String nombre, float precio, String imagen, String descripcion, int descuento)
		try {
			dao.create(new Producto(1, "Turrón", 2, "https://supermercado.eroski.es/images/17930009.jpg", "Turrón duro EROSKI, caja 250 g", 0));
			dao.create(new Producto(2, "Gulas", 8.65f, "https://supermercado.eroski.es/images/19780345.jpg", "Gulas del norte congeladas LA GULA DEL NORTE, bandeja 430 g", 20));
			dao.create(new Producto(3, "Turrón", 4, "https://supermercado.eroski.es/images/22615017.jpg", "Turrón crujiente de chocolate negro EL ALMENDRO, tableta 280 g", 15));
			dao.create(new Producto(4, "Zumo de naranja", 2.75f, "https://supermercado.eroski.es/images/13899539.jpg", "Zumo de naranja exprimido sin pulpa ZÜ PREMIUM, brik 2 litros", 10));
			dao.create(new Producto(5, "Leche", 0.70f, "https://supermercado.eroski.es/images/18672311.jpg", "Leche semidesnatada del PaÍs Vasco EROSKI, brik 1 litro", 0));
			dao.create(new Producto(6, "Mermelada", 1.89f, "https://supermercado.eroski.es/images/330456.jpg", "Mermelada de albaricoque BEBÉ, frasco 340 g", 0));
			dao.create(new Producto(7, "Helado", 5, "https://supermercado.eroski.es/images/20260006.jpg", "Bombón mini clásico-almendrado-blanco MAGNUN, caja 266 g", 50));
			dao.create(new Producto(8, "Queso", 11.40f, "https://supermercado.eroski.es/images/15962624.jpg", "Queso natural D.O. Idiazabal BAGA, cuña 550 g", 20));
			dao.create(new Producto(9, "Huevos", 2.45f, "https://supermercado.eroski.es/images/21176490.jpg", "Huevo suelo M/L Pais Vasco EROSKI, cartón 18 uds.", 0));
			dao.create(new Producto(10, "Tomates", 2.5f, "https://supermercado.eroski.es/images/7039522.jpg", "Tomate EUSKO LABEL, al peso, compra mínima 500 g", 10));
			
		} catch (Exception e) {
			LOG.warn(e);		
		}

	}
	
	
	@Override
	public void destroy() {
		
		super.destroy();
		dao = null;
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1 producto para hacer la prueba:
		//request.setAttribute("producto", new Producto() );
		
		//lista de productos:
		//llamamos al DAO capa modelo
		ArrayList<Producto> productos = (ArrayList<Producto>) dao.getAll();
		request.setAttribute("productos", productos );
		request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Últimos productos destacados") ); //mensaje fijo al entrar en la web
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
