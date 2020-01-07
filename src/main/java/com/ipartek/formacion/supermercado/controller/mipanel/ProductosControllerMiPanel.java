package com.ipartek.formacion.supermercado.controller.mipanel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.controller.Alerta;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoException;
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
		
	private static ProductoDAO daoProducto;
	
	private Usuario uLogeado;
	
	
	// acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";
	
	private boolean isRedirect;
	
	
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
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoProducto = ProductoDAO.getInstance();
		
		//para inicializar validaciones de parámetros:
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public void destroy() {
		super.destroy();
		daoProducto = null;
		
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
		
		isRedirect = false;

		// 1. recogemos parámetros:
		pAccion = request.getParameter("accion");

		pId = request.getParameter("id");
		pNombre = request.getParameter("nombre");
		pPrecio = request.getParameter("precio");
		pImagen = request.getParameter("imagen");
		pDescripcion = request.getParameter("descripcion");
		pDescuento = request.getParameter("descuento");
		
		//ahora no vamos a recibir el usuario por parámetro sino que lo cogemos de la sesión para evitar que se envíe el usuario desde el formulario:
		HttpSession sesion = request.getSession();
		uLogeado = (Usuario) sesion.getAttribute("usuarioLogeado"); //en 1 línea: uLogeado = (Usuario)request.getSession().getAttribute("usuarioLogeado");
		
		
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
			
		} catch (ProductoException e) {
			LOG.warn("Problema al visualizar el producto" + e);
			isRedirect = true;
						
		} catch (Exception e) {
			LOG.warn("Ha habido algún problema" + e);
			
		} finally {
			if(isRedirect) {
				////invalidamos la session del usuario => ProductoException
				response.sendRedirect(request.getContextPath() + "/logout");
			}else {
				// ir a JSP:
				request.getRequestDispatcher(vistaSeleccionda).forward(request, response);
			}
			
		}
	}

	
	// métodos:

	private void irFormulario(HttpServletRequest request, HttpServletResponse response) throws SQLException, ProductoException { 
		//este método sólo nos lleva al formulario, no es para rellenar los campos, para eso utilizamos "guardar"

		// recibimos parámetros:
		int id = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		Producto productoVisualizar = new Producto();
		
		if (id > 0) {

			productoVisualizar = daoProducto.getByIdByUser(id, uLogeado.getId()); // productoVisualizar = daoProducto.getById(id);

		}
	
		request.setAttribute("producto", productoVisualizar);
		vistaSeleccionda = VIEW_FORM;
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		// en función del id del producto:
		// 1. se modificará si el id > 0, significa que el producto está en la lista
		// 2. se creará un registro nuevo en caso contrario, puesto que si id = 0, significa que el producto todavía no está en la lista		
		
		
		// recibimos los datos del formulario:
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

		
		//recogemos el id del usuario DE LA SESIÓN para el producto seleccionado para mejorar la seguridad:
/*		String pIdUsuario = request.getParameter("idUsuario");
		Usuario u = new Usuario();
		u.setId(Integer.parseInt(pIdUsuario));
		producto.setUsuario(u);
*/
		Usuario u = new Usuario();
		u.setId(uLogeado.getId()); //así evitamos que se envíe el parámetro desde el formulario
		producto.setUsuario(u);
			
		
		//nombre más de 2 y menos de 150
		Set<ConstraintViolation<Producto>> validaciones = validator.validate(producto); 
		
		if (validaciones.size() > 0) {
			
			mensajeValidacion(request, validaciones);
			
		} else {
			try {
							
				if ( pId > 0 ) {  //modificar un producto existente
					
						LOG.trace("Modificar datos del producto");
						
						daoProducto.updateByUser(pId, uLogeado.getId(), producto);
						request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Los datos del producto se han modificado correctamente"));
												
					
				}else {  //crear nuevo producto

					LOG.trace("Crear un registro un producto nuevo");
					
					daoProducto.create(producto);
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Producto nuevo añadido"));
					
				}
							
			} catch (ProductoException e){
				LOG.error(e);
				
			} catch (Exception e){ //problemas al añadir el producto en la base de datos
				LOG.fatal(e);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "El producto no se puede añadir a la base de datos, su nombre ya existe. Elige otro, por favor"));	
			}
		}
			
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
	

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ProductoException{
		
		// recibimos parámetros:
		int pId = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		if (pId > 0) { 
			
			Producto producto = daoProducto.deleteByUser(pId, uLogeado.getId());
			
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Has comprado " + producto.getNombre() + " Gracias"));
			LOG.info("Se ha comprado " + producto.getNombre());
			
			listar(request, response);
		}
		
	}

	
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<Producto> productos = (ArrayList<Producto>) daoProducto.getAllByIdUsuario(uLogeado.getId());
		request.setAttribute("productos", productos );
		
		vistaSeleccionda = VIEW_TABLA; // vamos a la tabla

	}

}
