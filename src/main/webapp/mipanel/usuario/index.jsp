<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="/includes/header.jsp" %>

 
	<h1 class="text-center text-info" id="top">Mis datos</h1>
		
	
	<style>

		 .foto-perfil-producto {
		 	width: 3.5em;
		 }
	 
	</style>
	
	
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
	     						
			<tr>
				<td>${usuario.id}</td>
				<td>${usuario.nombre}</td>
				<td>${usuario.contrasenia}</td>
				<td>${usuario.rol.nombre}</td>
				<td><a href="mipanel/usuario?accion=formulario&id=${usuario.id}">Editar contraseña</a></td>
			</tr>
								
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
		

	<a id="btn-top" href="#top" class="btn btn-primary">top</a>

<%@include file="/includes/footer.jsp" %>    