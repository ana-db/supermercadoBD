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
import com.ipartek.formacion.supermercado.modelo.dao.CategoriaDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Categoria;


/**
 * Servlet implementation class CategoriasController
 */
@WebServlet("/seguridad/categorias")
public class CategoriasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(CategoriasController.class);
       
	private static final String VIEW_TABLA = "categorias/index.jsp";
	private static final String VIEW_FORM = "categorias/formulario.jsp";
	private static String vistaSeleccionda = VIEW_TABLA;
	
	private static CategoriaDAO daoCategoria;

	
	// acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";
	
	
	//Crear Factoria y Validador:
	ValidatorFactory factory;
	Validator validator;
	 

	// variables parámetros:
	String cAccion;
	String cId;
	String cNombre;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoCategoria = CategoriaDAO.getInstance();
		
		//para inicializar validaciones de parámetros:
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public void destroy() {
		super.destroy();
		daoCategoria = null;
		
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
		cAccion = request.getParameter("accion");

		cId = request.getParameter("id");
		cNombre = request.getParameter("nombre");
		 
		
		try {
			// lógica de negocio

			switch (cAccion) {
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

		Categoria categoriaVisualizar = new Categoria();
		
		if (id > 0) {

			categoriaVisualizar = daoCategoria.getById(id); // getById() en IDAO

		}

		request.setAttribute("categoria", categoriaVisualizar);
		vistaSeleccionda = VIEW_FORM;
		
	}
	
	
	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		// en función del id del producto:
		// 1. se modificará si el id > 0, significa que el usuario está en la base de datos
		// 2. se creará un registro nuevo en caso contrario, puesto que si id = 0, significa que el usuario todavía no está en la base de datos		
		
		// recibir datos del formulario
		int cId = Integer.parseInt(request.getParameter("id"));
		
		Categoria categoria = new Categoria();
		categoria.setId(cId);
		categoria.setNombre(cNombre);

		
		//nombre entre 2 y 50 caracteres
		Set<ConstraintViolation<Categoria>> validaciones = validator.validate(categoria); //if (uNombre != null && uNombre.length() >= 2 && uNombre.length() <= 50)
		
		if (validaciones.size() > 0) {
			
			mensajeValidacion(request, validaciones);
			
		} else {
			try {
							
				if ( cId > 0 ) {  //modificar una categoria existente
					LOG.trace("Modificar datos de la categoria");
										
					daoCategoria.update(categoria, cId);		
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Los datos de la categoria se han modificado correctamente"));
					
				}else {  //crear una nueva categoria
					LOG.trace("Crear un registro para una categoria nueva");
										
					daoCategoria.create(categoria);
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Nueva categoria añadida"));
				}
							
			} catch (Exception e){
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "La categoria no se puede añadir a la base de datos, su nombre ya existe. Elige otro, por favor"));
			}
		}
			
		
		request.setAttribute("categorias", daoCategoria.getAll()); // devuelve el dao con todos sus parámetros
		
		vistaSeleccionda = VIEW_FORM;		
	}
	
	
	private void mensajeValidacion(HttpServletRequest request, Set<ConstraintViolation<Categoria>> validaciones) {
		
		StringBuilder mensaje = new StringBuilder();
		for (ConstraintViolation<Categoria> cv : validaciones) {
			
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

		Categoria categoria = daoCategoria.getById(uId);

		if (uId > 0) {

			try {
				daoCategoria.delete(uId);
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "La categoria " + categoria.getNombre() + " se ha borrado"));
				LOG.info("Se ha borrado la categoria " + categoria.getNombre());
			} catch (Exception e) {
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede borrar la categoria " + categoria.getNombre()));
			}
		}

		request.setAttribute("categorias", daoCategoria.getAll()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA;
		
	}
	

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("categorias", daoCategoria.getAll()); // devuelve el dao con todos sus parámetros
		vistaSeleccionda = VIEW_TABLA; // vamos a la tabla
		
	}

}
