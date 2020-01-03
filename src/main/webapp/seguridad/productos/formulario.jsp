<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="/includes/header.jsp" %>

 
	<h1 class="text-info">Alta y modificación de productos</h1>
	
	
	<!-- ${producto}  -->	
	<!-- ------------------------------------------------------------------------------- -->
	
	
	<!-- FORMULARIO -->
	<form action="seguridad/productos" method="post">
	<!-- <form action="seguridad/productos?accion=guardar" method="post"> -->
	
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
	               value = "${producto.id}"
	               placeholder="Identificador del producto"
	               pattern="[0-9]"
	               min="0" max="100"
	               aria-describedby="idHelp">
	        <small id="idHelp" class="form-text text-muted">Identificador del producto</small>
		</div>


		<div class="form-group">
	        <label for="nombre">Producto</label>
	        <input type="text" 
	               class="form-control" 
	               name="nombre" id="nombre" 
	               required
	               value = "${producto.nombre}"
	               placeholder="Mínimo 2 Máximo 150 caracteres"
	               aria-describedby="nombreHelp">
	        <small id="nombreHelp" class="form-text text-muted">Nombre del producto</small>
	    </div>	   
	    <!-- 
	    	quitamos pattern=".{2,150}" para controlarlo en el controlador con las funciones de validación
	     -->


		<div class="form-group">
	        <label for="precio">Precio</label>
	        <input type="number" 
	               class="form-control" 
	               name="precio" id="precio" 
	               required
	               value = "${producto.precio}"
	               placeholder="Precio en euros sin descuento"
	               pattern="[0-9]"
	               min="0" max="100"
	               step="0.1"
	               aria-describedby="precioHelp">
	        <small id="precioHelp" class="form-text text-muted">Precio en euros sin descuento</small>
		</div>
		
		<div class="form-group">
	        <label for="imagen">Imagen</label>
	        <input type="text" 
	               class="form-control" 
	               name="imagen" id="imagen" 
	               required
	               value = "${producto.imagen}"
	               placeholder="Escribe aquí la url completa de la imagen"
	               aria-describedby="imagenHelp">
	    </div>
	    
	    <div class="form-group">
	        <label for="descripcion">Descripcion</label>
	        <input type="text" 
	               class="form-control" 
	               name="descripcion" id="descripcion" 
	               required
	               value = "${producto.descripcion}"
	               placeholder="Mínimo 2 Máximo 150 caracteres"
	               pattern=".{2,150}"
	               aria-describedby="descripcionHelp">
	    </div>
	    
	    <div class="form-group">
	        <label for="descuento">Descuento %</label>
	        <input type="number" 
	               class="form-control" 
	               name="descuento" id="descuento" 
	               value = "${producto.descuento}"
	               placeholder="Descuento en %"
	               pattern="[0-9]"
	               min="0" max="100"
	               aria-describedby="descuentoHelp">
		</div>
		
		
		<!--  
	    <div class="form-group">
	        <label for="usuario">Usuario del producto</label>
	        ${producto.usuario}
	        
	    	<hr>Todos los usuarios<hr>
	        ${usuarios}
	        <br>
	    </div>
	    -->
	    
	   <div class="form-group">		
			<label>Usuario del producto</label>
			<select name="usuarioId" class="custom-select">
				<c:forEach items="${usuarios}" var="u">
					<option value="${u.id}"  ${(u.id eq producto.usuario.id)?"selected":""} >${u.nombre}</option>	
				</c:forEach>
			</select>
		</div>
		
 
 		<!--   <button type="submit" class="btn btn-block btn-outline-primary">Crear</button>   -->
	    <input type="submit" class="btn btn-block btn-outline-primary" value="${(producto.id>0)?"Modificar":"Crear" }">    <!-- para que el botón cambie de texto cuando queremos crear/modificar -->
	</form>
	




	<!-- VENTANA MODAL BOOTSTRAP PARA ELIMINAR -->
	<c:if test="${producto.id > 0}">

		<!-- Button trigger modal -->
		<button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#exampleModal">Eliminar</button>
		
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Eliminar producto</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        ¿Seguro que quieres comprar ${producto.nombre}?
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
		        <a class="btn btn-danger" href="seguridad/productos?id=${producto.id}&accion=eliminar">Eliminar</a>
		      </div>
		    </div>
		  </div>
		</div>

	</c:if>
	


<%@include file="/includes/footer.jsp" %>    