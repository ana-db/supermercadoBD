package com.ipartek.formacion.supermercado.controller.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.controller.seguridad.ProductosController;
import com.ipartek.formacion.supermercado.modelo.pojo.Rol;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;



/**
 * Servlet Filter implementation class SeguridadFilter
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/seguridad/*" })
public class SeguridadFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(ProductosController.class);

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		LOG.trace("init");
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		LOG.trace("destroy");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//hacemos cast de request y response de tipo ServletRequest y ServletResponse a HttpServletRequest y HttpServletResponse:
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//hacemos el filtro de seguridad:
		HttpSession session = req.getSession();
		Usuario uLogeado = (Usuario) session.getAttribute("usuarioLogeado");
		
		if (uLogeado != null && uLogeado.getRol().getId() == Rol.ROL_ADMIN ) {
			
			// pass the request along the filter chain
			chain.doFilter(request, response);
			
		}
		else {
			
			LOG.warn("Acceso denegado por seguridad " + uLogeado);
			session.invalidate();
			res.sendRedirect( req.getContextPath() +  "/login.jsp");
		}
	}


}
