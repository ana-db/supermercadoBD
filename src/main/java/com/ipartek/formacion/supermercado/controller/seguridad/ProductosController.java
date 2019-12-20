package com.ipartek.formacion.supermercado.controller.seguridad;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.controller.Alerta;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

/**
 * Servlet implementation class ProductosController
 */
@WebServlet("/seguridad/productos")
public class ProductosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ProductosController.class);

	private static final String VIEW_TABLA = "productos/index.jsp";
	private static final String VIEW_FORM = "productos/formulario.jsp";
	private static String vistaSeleccionda = VIEW_TABLA;
	boolean isRedirect = false;
	
	private static ProductoDAO dao;
	

	// acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";

	// variables:
	String pAccion;
	String pId;
	String pNombre;
	String pPrecio;
	String pImagen;
	String pDescripcion;
	String pDescuento;
	
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = ProductoDAO.getInstance();
	}

	@Override
	public void destroy() {
		super.destroy();
		dao = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. recogemos parámetros:
		pAccion = request.getParameter("accion");

		pId = request.getParameter("id");
		pNombre = request.getParameter("nombre");
		pPrecio = request.getParameter("precio");
		pImagen = request.getParameter("imagen");
		pDescripcion = request.getParameter("descripcion");
		pDescuento = request.getParameter("descuento");

		isRedirect = false;

		try {
			// lógica de negocio

			switch (pAccion) {
			case ACCION_LISTAR:
				listar(request, response);
				break;

			case ACCION_ELIMINAR:
				eliminar(request, response);
				break;

			case ACCION_GUARDAR:
				guardar(request, response);
				break;

			case ACCION_IR_FORMULARIO:
				irFormulario(request, response);
				break;

			default:
				listar(request, response);
				break;
			}

			// request.setAttribute("productos", dao.getAll());

		} catch (Exception e) {
			// TODO log
			LOG.warn("Ha habido algún problema");
			e.printStackTrace();
		} finally {
			// ir a JSP:
			

			//if (isRedirect) {
		//		response.sendRedirect("/seguridad/productos?accion=listar");
		//	} else {
				request.getRequestDispatcher(vistaSeleccionda).forward(request, response);
		//	}
		}
	}

	// métodos:

	private void irFormulario(HttpServletRequest request, HttpServletResponse response) { 
		//este método sólo nos lleva al formulario, no es para rellenar los campos, para eso utilizamos "guardar"

		// TODO pregutar por pID > 0 recuperar del DAO
		// si no New Producto()

		// dao.getById(id) => implementar

		//////////////////////////////////////////

		// recibimos parámetros:
		int id = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		Producto productoVisualizar = new Producto();
		
		if (id > 0) {

			productoVisualizar = dao.getById(id); // getById() en IDAO

		}
	
		request.setAttribute("producto", productoVisualizar);
		vistaSeleccionda = VIEW_FORM;
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		isRedirect = true;
		
		// recibir datos del formulario
		int pId = Integer.parseInt(request.getParameter("id"));
		float pPrecioFloat = Float.parseFloat(pPrecio);
		int pDescuentoInt = Integer.parseInt(pDescuento);

		// en función del id del producto:
		// 1. se modificará si el id > 0, significa que el producto está en la lista
		// 2. se creará un registro nuevo en caso contrario, puesto que si id = 0,
		// significa que el producto todavía no está en la lista
		if (pId > 0) {
			LOG.trace("Modificar datos del producto");
			
			Producto producto = new Producto();
			
			// modificamos los datos:
			producto.setId(pId);
			producto.setNombre(pNombre);
			producto.setPrecio(pPrecioFloat);
			producto.setImagen(pImagen);
			producto.setDescripcion(pDescripcion);
			producto.setDescuento(pDescuentoInt);

			try {
				dao.update(producto, pId);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Los datos del producto se han modificado correctamente"));
			} catch (Exception e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "Los datos del producto no se han podido modificar"));
			}

		} else {
			LOG.trace("Crear un registro un producto nuevo");

			// crear registro para un producto nuevo
			Producto producto = new Producto();
			producto.setNombre(pNombre);
			producto.setPrecio(pPrecioFloat);
			producto.setImagen(pImagen);
			producto.setDescripcion(pDescripcion);
			producto.setDescuento(pDescuentoInt);

			// lo guardamos en la lista
			try {
				dao.create(producto);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Producto nuevo añadido"));

			} catch (Exception e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede añadir"));
			}
		}

		request.setAttribute("productos", dao.getAll()); // devuelve el dao con todos sus parámetros
		// vistaSeleccionda = VIEW_TABLA;
		vistaSeleccionda = VIEW_FORM;
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		// recibimos parámetros:
		int pId = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		Producto producto = dao.getById(pId);

		if (pId > 0) {

			try {
				dao.delete(pId);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Has comprado " + producto.getNombre() + " Gracias"));
				LOG.info(producto.getNombre() + " ha sido comprado");
			} catch (Exception e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede comprar este producto"));
			}
		}

		request.setAttribute("productos", dao.getAll()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA;

	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("productos", dao.getAll()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA; // vamos a la tabla

	}

}
