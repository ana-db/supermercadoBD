<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="/includes/header.jsp" %>

 
	<h1 class="text-center text-info">Alta y modificación de categorías</h1>
	
	<!-- FORMULARIO -->
	<form action="seguridad/categorias" method="post">
	
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
	               value = "${categoria.id}"
	               placeholder="Identificador de la categoría"
	               pattern="[0-9]"
	               min="0" max="100"
	               aria-describedby="idHelp">
	        <small id="idHelp" class="form-text text-muted">Identificador de la categoría</small>
		</div>


		<div class="form-group">
	        <label for="nombre">Categoría</label>
	        <input type="text" 
	               class="form-control" 
	               name="nombre" id="nombre" 
	               required
	               value = "${categoria.nombre}"
	               min="0" max="100"
	               placeholder="Mínimo 2 Máximo 100 caracteres"
	               aria-describedby="nombreHelp">
	        <small id="nombreHelp" class="form-text text-muted">Nombre de la categoria</small>
	    </div>	   
	    	
 
	    <input type="submit" class="btn btn-block btn-outline-primary" value="${(categoria.id>0)?"Modificar":"Crear" }">    <!-- para que el botón cambie de texto cuando queremos crear/modificar -->
	</form>
	




	<!-- VENTANA MODAL BOOTSTRAP PARA BORRAR -->
	<c:if test="${categoria.id > 0}">

		<!-- Button trigger modal -->
		<button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#exampleModal">Eliminar</button>
		
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Borrar categoría</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        ¿Seguro que quieres borrar la categoría ${categoria.nombre} de la base de datos?
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
		        <a class="btn btn-danger" href="seguridad/categorias?id=${categoria.id}&accion=eliminar">Eliminar</a>
		      </div>
		    </div>
		  </div>
		</div>

	</c:if>
	


<%@include file="/includes/footer.jsp" %>    