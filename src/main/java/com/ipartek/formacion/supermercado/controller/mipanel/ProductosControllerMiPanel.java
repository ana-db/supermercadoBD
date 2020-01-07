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
		
	private static ProductoDAO daoProducto;
	private static UsuarioDAO daoUsuario;
	
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
			LOG.warn("Ha habido algún problema");
			
		} finally {
			if(isRedirect) {
				//invalidamos:
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

			productoVisualizar = daoProducto.getByIdByUser(id, uLogeado.getId()); // getById() en IDAO

		}
	
		//request.setAttribute("usuarios", daoUsuario.getAll() ); //mostramos los usuarios
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

		
		//recogemos el id del usuario para el producto seleccionado para garantizar la seguridad entre usuarios:
		String pIdUsuario = request.getParameter("idUsuario");
		Usuario u = new Usuario();
		u.setId(Integer.parseInt(pIdUsuario));
		producto.setUsuario(u);
		
				
		//nombre más de 2 y menos de 150
		Set<ConstraintViolation<Producto>> validaciones = validator.validate(producto); //if (pNombre != null && pNombre.length() >= 2 && pNombre.length() <= 50)
		
		if (validaciones.size() > 0) {
			
			mensajeValidacion(request, validaciones);
			
		} else {
			try {
							
				if ( pId > 0 ) {  //modificar un producto existente
					
/*					if (producto.getUsuario().getId() == uLogeado.getId()) { //Seguridad: sólo dejamos que modifique el producto si está en su lista	
						LOG.trace("Modificar datos del producto");
											
						daoProducto.update(producto, pId);		
						request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Los datos del producto se han modificado correctamente"));
					
					}else { //si el usuario que ha iniciado sesión intenta modificar un producto de otro usuario mediante la url, se le invalida la sesión y se le envía al login
						
						request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "Ese objeto no lo puedes modificar porque no es tuyo"));
						request.getSession().invalidate();
						LOG.warn("Un usuario ha intentado modificar un producto que no le corresponde");
						
						vistaSeleccionda = "/login.jsp";	
*/
						LOG.trace("Modificar datos del producto");
						
						daoProducto.updateByUser(pId, uLogeado.getId(), producto);
						
						if (pId == uLogeado.getId()){
							request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Los datos del producto se han modificado correctamente"));
						}else {
							request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "Este producto no es tuyo, no puedes modificarlo"));
							request.getSession().invalidate();
							LOG.warn("Un usuario ha intentado modificar un producto que no le corresponde");
							
							vistaSeleccionda = "/login.jsp";	
						}
						
					
				}else {  //crear nuevo producto
/*					LOG.trace("Crear un registro un producto nuevo");
					
					//recogemos el id del usuario para el producto seleccionado. Utilizamos la variable uLogeado que cogemos de su sesión:
					//Usuario u = new Usuario();
					u.setId(uLogeado.getId()); //u.setId(Integer.parseInt(pUsuarioId)); 
					producto.setUsuario(u); //guardamos el usuario de la sesión en el producto
										
					daoProducto.create(producto);
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Producto nuevo añadido"));
*/
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
	

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ProductoException{
		
		// recibimos parámetros:
		int pId = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));
/*
		Producto producto = daoProducto.getById(pId);

		if (pId > 0 && producto.getUsuario().getId() == uLogeado.getId()) { //Seguridad: sólo dejamos que lo elimine si el producto es suyo

			try {
				daoProducto.delete(pId);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Has comprado " + producto.getNombre() + " Gracias"));
				LOG.info("Se ha comprado " + producto.getNombre());
			} catch (Exception e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede comprar este producto"));
				LOG.error("Ha habido un problema y no se ha podido comprar " + producto.getNombre());
			}
			
			listar(request, response);
			
		}else { //si el usuario que ha iniciado sesión intenta eliminar un producto de otro usuario mediante la url, se le invalida la sesión y se le envía al login
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "ese objeto no lo puedes eliminar porque no es tuyo"));
			request.getSession().invalidate();
			LOG.warn("Un usuario ha intentado comprar un producto que no le corresponde");
			
			vistaSeleccionda = "/login.jsp";
		}
*/
		
		Producto producto = new Producto();

		if (pId > 0) { 

			try {
				//producto = daoProducto.getByIdByUser(pId, uLogeado.getId());
				
				producto = daoProducto.deleteByUser(pId, uLogeado.getId());
				
				if (pId == uLogeado.getId()){
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Has comprado " + producto.getNombre() + " Gracias"));
					LOG.info("Se ha comprado " + producto.getNombre());
					
				}else { //si el usuario que ha iniciado sesión intenta eliminar un producto de otro usuario mediante la url, se le invalida la sesión y se le envía al login
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "Este producto no es tuyo, no lo puedes eliminar"));
					request.getSession().invalidate();
					LOG.warn("Un usuario ha intentado comprar un producto que no le corresponde");
					
					vistaSeleccionda = "/login.jsp";	
				}
				
			} catch (ProductoException e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede comprar este producto"));
				LOG.error("Ha habido un problema y no se ha podido comprar " + producto.getNombre());
			}
			
			listar(request, response);
		}
		
	}

	
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<Producto> productos = (ArrayList<Producto>) daoProducto.getAllByIdUsuario(uLogeado.getId());
		request.setAttribute("productos", productos );
		
		vistaSeleccionda = VIEW_TABLA; // vamos a la tabla

	}

}
