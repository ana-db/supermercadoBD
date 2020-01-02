package com.ipartek.formacion.supermercado.controller.seguridad;

import java.io.IOException;
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
import com.ipartek.formacion.supermercado.modelo.dao.UsuarioDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

/**
 * Servlet implementation class UsuariosController
 */
@WebServlet("/seguridad/usuarios")
public class UsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(ProductosController.class);

	private static final String VIEW_TABLA_USUARIOS = "usuarios/index.jsp";
	private static final String VIEW_FORM_USUARIOS = "usuarios/formulario.jsp";
	private static String vistaSeleccionda = VIEW_TABLA_USUARIOS;
	
	private static UsuarioDAO dao;
	
	// acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";
	
	
	//Crear Factoria y Validador:
	ValidatorFactory factory;
	Validator validator;
	 

	// variables parámetros:
	String uAccion;
	String uId;
	String uNombre;
	String uContrasenia;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = UsuarioDAO.getInstance();
		
		//para inicializar validaciones de parámetros:
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public void destroy() {
		super.destroy();
		dao = null;
		
		factory = null;
		validator = null;
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. recogemos parámetros:
		uAccion = request.getParameter("accion");

		uId = request.getParameter("id");
		uNombre = request.getParameter("nombre");
		uContrasenia = request.getParameter("contrasenia");
		
		
		try {
			// lógica de negocio

			switch (uAccion) {
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

	
	//métodos:
	private void irFormulario(HttpServletRequest request, HttpServletResponse response) {
		//este método sólo nos lleva al formulario, no es para rellenar los campos, para eso utilizamos "guardar"

		// recibimos parámetros:
		int id = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		Usuario usuarioVisualizar = new Usuario();
		
		if (id > 0) {

			usuarioVisualizar = dao.getById(id); // getById() en IDAO

		}
	
		request.setAttribute("usuario", usuarioVisualizar);
		vistaSeleccionda = VIEW_FORM_USUARIOS;
		
	}
	
	
	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		// en función del id del producto:
		// 1. se modificará si el id > 0, significa que el usuario está en la base de datos
		// 2. se creará un registro nuevo en caso contrario, puesto que si id = 0, significa que el usuario todavía no está en la base de datos		
		
		// recibir datos del formulario
		int uId = Integer.parseInt(request.getParameter("id"));
		
		Usuario usuario = new Usuario();
		usuario.setId(uId);
		usuario.setNombre(uNombre);
		usuario.setContrasenia(uContrasenia);
		
		//nombre entre 2 y 50 caracteres
		Set<ConstraintViolation<Usuario>> validaciones = validator.validate(usuario); //if (uNombre != null && uNombre.length() >= 2 && uNombre.length() <= 50)
		
		if (validaciones.size() > 0) {
			
			mensajeValidacion(request, validaciones);
			
		} else {
			try {
							
				if ( uId > 0 ) {  //modificar un producto existente
					LOG.trace("Modificar datos del usuario");
										
					dao.update(usuario, uId);		
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Los datos del usuario se han modificado correctamente"));
					
				}else {  //crear nuevo producto
					LOG.trace("Crear un registro para un usuario nuevo");
										
					dao.create(usuario);
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Nuevo usuario añadido"));
				}
							
			} catch (Exception e){
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "El usuario no se puede añadir a la base de datos, su nombre ya existe. Elige otro, por favor"));
			}
		}
			
		
		request.setAttribute("usuarios", dao.getAll()); // devuelve el dao con todos sus parámetros
		
		vistaSeleccionda = VIEW_FORM_USUARIOS;		
	}
	
	
	private void mensajeValidacion(HttpServletRequest request, Set<ConstraintViolation<Usuario>> validaciones) {
		
		StringBuilder mensaje = new StringBuilder();
		for (ConstraintViolation<Usuario> cv : validaciones) {
			
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
		int uId = (request.getParameter("id") == null) ? 0 : Integer.parseInt(request.getParameter("id"));

		Usuario usuario = dao.getById(uId);

		if (uId > 0) {

			try {
				dao.delete(uId);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "El usuario " + usuario.getNombre() + " se ha borrado"));
				LOG.info("Se ha comprado " + usuario.getNombre());
			} catch (Exception e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede borrar el usuario " + usuario.getNombre()));
			}
		}

		request.setAttribute("productos", dao.getAll()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA_USUARIOS;
		
	}
	

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("usuarios", dao.getAll()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA_USUARIOS; // vamos a la tabla
		
	}

	
	
	
	

}
