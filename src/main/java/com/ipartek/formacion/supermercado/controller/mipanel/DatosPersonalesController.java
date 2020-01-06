package com.ipartek.formacion.supermercado.controller.mipanel;

import java.io.IOException;
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
import com.ipartek.formacion.supermercado.modelo.dao.UsuarioDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

/**
 * Servlet implementation class DatosPersonalesController
 */
@WebServlet("/mipanel/usuario")
public class DatosPersonalesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(DatosPersonalesController.class);

	private static final String VIEW_TABLA = "usuario/index.jsp";
	private static final String VIEW_FORM = "usuario/formulario.jsp";
	private static String vistaSeleccionda = VIEW_TABLA;
	
	private static UsuarioDAO dao;
	
	private Usuario uLogeado;

	
	// acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // modificar
	
	
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
		
		HttpSession sesion = request.getSession();
		uLogeado = (Usuario) sesion.getAttribute("usuarioLogeado");
		
		try {
			// lógica de negocio

			switch (uAccion) {
			case ACCION_LISTAR:
				listar(request, response);
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
		vistaSeleccionda = VIEW_FORM;
		
	}
	
	
	private void guardar(HttpServletRequest request, HttpServletResponse response) {		
		// recibimos los datos del formulario y los guardamos en el usuario que ha iniciado sesión:
		
		int uId = Integer.parseInt(request.getParameter("id"));
				
		//recogemos el usuario de la sesión y guardamos la nueva contraseña:
		HttpSession sesion = request.getSession();
		uLogeado = (Usuario) sesion.getAttribute("usuarioLogeado");
		
		uLogeado.setId(uId);
		uLogeado.setNombre(uNombre);
		uLogeado.setContrasenia(uContrasenia);
		
		
		//nombre entre 2 y 50 caracteres
		Set<ConstraintViolation<Usuario>> validaciones = validator.validate(uLogeado); 
		
		if (validaciones.size() > 0) {
			
			mensajeValidacion(request, validaciones);
			
		} else {
			try {
							
				//modificar un producto existente
				LOG.trace("Modificar datos del usuario");
									
				dao.update(uLogeado, uId);		
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "La contraseña del usuario se han modificado correctamente"));
								
			} catch (Exception e){
				LOG.error(e);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "La constraseña no se puede añadir a la base de datos"));
			}
		}
			
		request.setAttribute("usuario", uLogeado);
		
		vistaSeleccionda = VIEW_FORM;		
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
	

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession sesion = request.getSession();
		uLogeado = (Usuario) sesion.getAttribute("usuarioLogeado");
		dao.getById(uLogeado.getId());
		
		request.setAttribute("usuario", uLogeado);
		vistaSeleccionda = VIEW_TABLA; // vamos a la tabla
		
	}
	
	

}
