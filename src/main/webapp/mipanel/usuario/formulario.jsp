<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="/includes/header.jsp" %>

 
	<h1 class="text-center text-info" >Editar contraseña</h1>
	
	
	<!-- FORMULARIO -->
	<form action="mipanel/usuario" method="post">
	
		<div class="form-group">
			<!-- necesitamos enviar la acción para que el controlador sepa a qué case del switch tiene que entrar -->	
			<input type="hidden" 
					name=accion
					value="guardar">
		</div>
		
		
		<div class="form-group">
	        <label for="id">ID</label>
	        <input type="number" 
	               class="form-control" 
	               name="id" id="id" 
	               required
	               readonly
	               value = "${usuario.id}"
	               placeholder="Identificador del usuario"
	               pattern="[0-9]"
	               min="0" max="100"
	               aria-describedby="idHelp">
	        <small id="idHelp" class="form-text text-muted">Identificador del usuario</small>
		</div>
		
		
		<div class="form-group">
	        <label for="nombre">Usuario</label>
	        <input type="text" 
	               class="form-control" 
	               name="nombre" id="nombre" 
	               required
	               readonly
	               value = "${usuario.nombre}"
	               placeholder="Mínimo 2 Máximo 50 caracteres"
	               aria-describedby="nombreHelp">
	    </div>
		

		<div class="form-group">
	        <label for="contrasenia">Nueva contraseña</label>
	        <input type="password" 
	               class="form-control" 
	               name="contrasenia" id="contrasenia" 
	               required
	               value = "${usuario.contrasenia}"
	               placeholder="Mínimo 2 Máximo 50 caracteres"
	               aria-describedby="contraseniaHelp">
	    </div>	
		
 
 		<!--   <button type="submit" class="btn btn-block btn-outline-primary">Crear</button>   -->
	    <input type="submit" class="btn btn-block btn-outline-primary" value="Modificar"}">   
	</form>



<%@include file="/includes/footer.jsp" %>    