<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="/includes/header.jsp" %>

 
	<h1 class="text-center text-info" id="top">Usuarios</h1>
	
	
	<a href="seguridad/usuarios?accion=formulario">Nuevo Usuario</a>
		
	
	<style>

		 .foto-perfil-producto {
		 	width: 3.5em;
		 }
	 
	</style>
	
	<!-- ${usuarios} -->
	
	
	<%
		//ArrayList<Usuario> usuarios = (ArrayList<Usuario>)request.getAttribute("usuarios");
	%>
	
	
	<!-- DATATABLES -->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css"/>
        
	<!-- JAVASCRIPT los incluimos en el pie-->
	
    <!-- TABLA -->
    <table class="tabla reponsive display">
	    <thead>
	        <tr>
	            <th>ID</th>
	            <th>Nombre</th>
	            <th>Contraseña</th>
	            <th>Rol</th>
	            <th>Acción</th>
	        </tr>
	    </thead>
	    
	    <tbody>
	     						
			<c:forEach items="${usuarios}" var="u">
				<tr>
					<td>${u.id}</td>
					<td>${u.nombre}</td>
					<td>${u.contrasenia}</td>
					<td>${u.rol.nombre}</td>
					<td><a href="seguridad/usuarios?accion=formulario&id=${u.id}">Editar</a>, <a href="seguridad/usuarios?accion=eliminar&id=${u.id}">Borrar</a></td>
				</tr>
			</c:forEach>
								
		</tbody>
		          
		<tfoot>
	          <tr>
	              <th>ID</th>
	              <th>Nombre</th>
	              <th>Contraseña</th>
	              <th>Rol</th>
	              <th>Acción</th>
	          </tr>
		</tfoot>
 	</table>
	

	
	
	<!-- ------------------------------------------- -->            
	
	

	<a id="btn-top" href="#top" class="btn btn-primary">top</a>

<%@include file="/includes/footer.jsp" %>    