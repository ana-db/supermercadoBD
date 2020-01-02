package com.ipartek.formacion.supermercado.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.modelo.dao.UsuarioDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Rol;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(LoginController.class);
	
	//comentamos las líneas de usuario y password porque lo vamos a hacer con la base de datos y el usuarioDAO (admin, 123456):
	//private static final String USUARIO = "admin";
	//private static final String PASSWORD = "admin";
	private static UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
   

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
		//1. recibir parámetros:
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("contrasenya");
		
		String vista = "login.jsp";
				
		try {
			
			Usuario usuario = usuarioDAO.exist(nombre, password);
			
			//2. lógica de negocio:
			if (usuario != null) {
				
				LOG.info("login correcto " + usuario);
				
				//recuperar sesión del usuario == browser
				HttpSession session = request.getSession();
				session.setAttribute("usuarioLogeado", nombre); //guarda 1 atributo  de la sesión
				session.setMaxInactiveInterval(60*3); //3 mins
				
				if(usuario.getRol().getId() == Rol.ROL_ADMIN) {
					
					vista = "seguridad/index.jsp"; //acceso al backoffice --> para el administrador
					
				}else {
					
					vista = "mipanel/index.jsp"; //acceso al frontoffice --> para usuarios normales
				}
			
				
				LOG.info("Logging correcto");
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Login realizado con éxito") ); 

			}
			else {
				vista = "login.jsp";
				
				LOG.warn("Las credenciales no son correctas");
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "Las credenciales no son correctas, vuelve a intentarlo") ); 

			}
		}	
		catch (Exception e){
			LOG.error(e);
		}
		finally{
			//ir a JSP:
			request.getRequestDispatcher(vista).forward(request, response);
		}

	}

}
