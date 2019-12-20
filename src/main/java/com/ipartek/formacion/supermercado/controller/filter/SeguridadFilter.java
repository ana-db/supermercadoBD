package com.ipartek.formacion.supermercado.controller.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.controller.seguridad.ProductosController;



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
		if (session.getAttribute("usuarioLogeado") == null) {
			LOG.warn("Intentan entrar sin loggearse");
			
			//sacamos unas trazas con info:
			LOG.debug("RequestURL: " + req.getRequestURL());
			LOG.debug("RequestURL: " + req.getRequestURI());
			LOG.debug("Protocolo HTTP: " + req.getProtocol());
			LOG.debug("HTTP RemoteAddress, IP: " + req.getRemoteAddr());
			LOG.debug("HTTP RemoteHost: " + req.getRemoteHost());
			LOG.debug("Navegador: " + req.getHeader("User-Agent"));
			
			Map parametrosMap = req.getParameterMap(); //recogemos parámetros enviados
			//visualizamos los parámetros enviados:
			/*
			for (Map key : parametrosMap.keySet()) {
			    String[] strArr = (String[]) parametrosMap.get(key);
			    for (String val : strArr) {
			        System.out.println("Str Array= " + val);
			    }
			}	
			*/		
			
			
			/*
			 * Vamos a calcular el números de usuarios que acceden indebidamente
			 * Se inicializa la variable en este listener
			 * @see com.ipartek.formacion.controller.listener.AppListener
			 */
			ServletContext sc = req.getServletContext(); //AplicationContext en la JSP
			//actualizamos numeroAccesosIndebidos:
			int numeroAccesosIndebidos = (int)sc.getAttribute("numeroAccesosIndebidos");
			numeroAccesosIndebidos++;
			sc.setAttribute("numeroAccesosIndebidos", numeroAccesosIndebidos);
			
			//guardamos ip en la colección:
			HashSet<String> ips = (HashSet<String>)sc.getAttribute("ips");
			String ipCliente = req.getRemoteHost();
			ips.add(ipCliente);
			sc.setAttribute("ips", ips);
			
		}
		else {
			//dejamos pasar al filtro y continuar:
			// pass the request along the filter chain
			LOG.trace("logeado con exito");
			chain.doFilter(request, response);
		}
	}

}
