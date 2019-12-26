package com.ipartek.formacion.supermercado.Listener;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.controller.seguridad.ProductosController;
import com.ipartek.formacion.supermercado.modelo.ConnectionManager;

/**
 * Application Lifecycle Listener implementation class ConexionListener
 *
 */
@WebListener
public class ConexionListener implements ServletContextListener {
	
	//listener que compruebe si la cx a la bd es satisfactoria
	
	private static final Logger LOG = Logger.getLogger(ProductosController.class);

    /**
     * Default constructor. 
     */
    public ConexionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         LOG.info("Paramos la App");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	LOG.info("Arranca la App");
    	
    	Connection con = ConnectionManager.getConnection();
    	if (con == null) {
    		
    		ServletContext sc = sce.getServletContext();
    		sc.getRequestDispatcher("/error.jsp");
    	}
    }
	
}
