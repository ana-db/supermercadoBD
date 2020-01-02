package com.ipartek.formacion.supermercado.controller.mipanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.controller.Alerta;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.dao.UsuarioDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

/**
 * Servlet implementation class ProductosController
 */
@WebServlet("/mipanel/productos")
public class ProductosControllerMiPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ProductosControllerMiPanel.class);

	private static final String VIEW_TABLA = "productos/index.jsp";
	private static final String VIEW_FORM = "productos/formulario.jsp";
	private static String vistaSeleccionda = VIEW_TABLA;
	boolean isRedirect = false;
	
	private static ProductoDAO daoProducto;
	private static UsuarioDAO daoUsuario;
	

	// acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";
	
	
	//Crear Factoria y Validador:
	ValidatorFactory factory;
	Validator validator;
	 

	// variables parámetros:
	String pAccion;
	String pId;
	String pNombre;
	String pPrecio;
	String pImagen;
	String pDescripcion;
	String pDescuento;
		
	String pUsuarioId;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoProducto = ProductoDAO.getInstance();
		daoUsuario = UsuarioDAO.getInstance();
		
		//para inicializar validaciones de parámetros:
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public void destroy() {
		super.destroy();
		daoProducto = null;
		daoUsuario = null;
		
		factory = null;
		validator = null;
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
		
		pUsuarioId = request.getParameter("usuarioId");
		
		
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

		} catch (Exception e) {
			LOG.warn("Ha habido algún problema");
			e.printStackTrace();
		} finally {
			// ir a JSP:
			request.getRequestDispatcher(vistaSeleccionda).forward(request, response);
		}
	}

	
	// métodos:

	private void irFormulario(HttpServletRequest request, HttpServletResponse response) { 
		//este método sólo nos lleva al formulario, no es para rellenar los campos, para eso utilizamos "guardar"

		// recibimos parámetros:
		int id = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		Producto productoVisualizar = new Producto();
		
		if (id > 0) {

			productoVisualizar = daoProducto.getById(id); // getById() en IDAO

		}
	
		request.setAttribute("usuarios", daoUsuario.getAll() ); //mostramos los usuarios
		request.setAttribute("producto", productoVisualizar);
		vistaSeleccionda = VIEW_FORM;
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		// en función del id del producto:
		// 1. se modificará si el id > 0, significa que el producto está en la lista
		// 2. se creará un registro nuevo en caso contrario, puesto que si id = 0, significa que el producto todavía no está en la lista		
		
		
		// recibir datos del formulario
		int pId = Integer.parseInt(request.getParameter("id"));
		float pPrecioFloat = Float.parseFloat(pPrecio);
		int pDescuentoInt = Integer.parseInt(pDescuento);
		
		Producto producto = new Producto();
		producto.setId(pId);
		producto.setNombre(pNombre);
		producto.setPrecio(pPrecioFloat);
		producto.setImagen(pImagen);
		producto.setDescripcion(pDescripcion);
		producto.setDescuento(pDescuentoInt);
		
		//recogemos el id del usuario para el producto seleccionado:
		Usuario u = new Usuario();
		u.setId(Integer.parseInt(pUsuarioId));
		producto.setUsuario(u);
		
		
		//nombre más de 2 y menos de 150
		Set<ConstraintViolation<Producto>> validaciones = validator.validate(producto); //if (pNombre != null && pNombre.length() >= 2 && pNombre.length() <= 50)
		
		if (validaciones.size() > 0) {
			
			mensajeValidacion(request, validaciones);
			
		} else {
			try {
							
				if ( pId > 0 ) {  //modificar un producto existente
					LOG.trace("Modificar datos del producto");
										
					daoProducto.update(producto, pId);		
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Los datos del producto se han modificado correctamente"));
					
				}else {  //crear nuevo producto
					LOG.trace("Crear un registro un producto nuevo");
										
					daoProducto.create(producto);
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Producto nuevo añadido"));
				}
							
			} catch (Exception e){
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "El producto no se puede añadir a la base de datos, su nombre ya existe. Elige otro, por favor"));
			}
		}
			
		request.setAttribute("usuarios", daoUsuario.getAll() ); //devolvemos el dao de usuario para montar el select al modificar el producto y que muetsre todos los posibles usuarios
		request.setAttribute("productos", producto); // devuelve el dao con todos sus parámetros
		
		vistaSeleccionda = VIEW_FORM;			
	}

	
	private void mensajeValidacion(HttpServletRequest request, Set<ConstraintViolation<Producto>> validaciones) {
		
		StringBuilder mensaje = new StringBuilder();
		for (ConstraintViolation<Producto> cv : validaciones) {
			
			mensaje.append("<p>");
			mensaje.append(cv.getPropertyPath()).append(": ");
			mensaje.append(cv.getMessage()); //nos devuelve el mensaje
			mensaje.append("</p>");
		}
		// validación de campos del formulario incorrectos
		request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, mensaje.toString()));
		
	}
	

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		
		// recibimos parámetros:
		int pId = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		Producto producto = daoProducto.getById(pId);

		if (pId > 0) {

			try {
				daoProducto.delete(pId);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Has comprado " + producto.getNombre() + " Gracias"));
				LOG.info("Se ha comprado " + producto.getNombre());
			} catch (Exception e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede comprar este producto"));
			}
		}

		request.setAttribute("productos", daoProducto.getAllByIdUsuario()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA;

	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("productos", daoProducto.getAllByIdUsuario()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA; // vamos a la tabla

	}

}
