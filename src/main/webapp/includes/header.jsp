<!-- taglibs necesarias para leer notación JSTL (c:forEach...) : -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Idioma en castellano: -->
<fmt:setLocale value="es_ES"/> 

<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="com.ipartek.formacion.supermercado.modelo.pojo.Producto"%>
<%@page import="java.util.ArrayList"%>
		
		
<!doctype html>
<%@page import="com.ipartek.formacion.supermercado.modelo.pojo.Producto"%>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="Ana">
		<title>Supermercado</title>
		
		<!-- la base para construir todas las rutas de esta página -->
	    <base href="${pageContext.request.contextPath}/" /> <!-- coge directamente el servidor, el puerto y el nombre del proyecto. Añadimos la barra -->
	    <!-- <p>ContextPath = <b>${pageContext.request.contextPath}</b></p> <!-- coge directamente el servidor, el puerto y el nombre del proyecto -->
	    		
		<!-- Bootstrap core CSS -->
		<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- DATATABLES CSS -->
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
	    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css"/>
		
		<!-- nuestro css -->
		<link rel="stylesheet" href="css/custom.css">

	</head>
	
  <body id="top">
    <nav class="site-header sticky-top py-1">
        <div class="container d-flex flex-column flex-md-row justify-content-between">
            <a class="py-2" href="/supermercado/">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="d-block mx-auto" role="img" viewBox="0 0 24 24" focusable="false"><title>Product</title><circle cx="12" cy="12" r="10"/><path d="M14.31 8l5.74 9.94M9.69 8h11.48M7.38 12l5.74-9.94M9.69 16L3.95 6.06M14.31 16H2.83m13.79-4l-5.74 9.94"/></svg>
            </a>
            
            <c:if test="${empty usuarioLogeado}" >
            	<a class="py-2 d-none d-md-inline-block" href="login.jsp">Login</a>
            </c:if>
            
            <c:if test="${not empty usuarioLogeado}" >
            	<a class="py-2 d-none d-md-inline-block" href="seguridad/index.jsp">Dashboard</a>
	            <a class="py-2 d-none d-md-inline-block" href="seguridad/productos?accion=listar">Tabla</a>
	            <a class="py-2 d-none d-md-inline-block" href="seguridad/productos?accion=formulario">Formulario</a> 
	            <a class="py-2 d-none d-md-inline-block" href="logout">Cerrar Sesión</a>  
            </c:if>
            
        </div>
    </nav>

    <main class="container">
    
    	<c:if test="${not empty mensajeAlerta}">
    	
	    	<!-- https://getbootstrap.com/docs/4.4/components/alerts/#dismissing -->
	    	 <div class="alert alert-${mensajeAlerta.tipo} alert-dismissible fade show mt-3" role="alert">
				${mensajeAlerta.texto}
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			
		</c:if>	
		
