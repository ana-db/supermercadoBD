<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="/includes/header.jsp" %>

 
	<h1>Formulario</h1>
	
	usar este atributo para rellenar los values del formulario
	
	${producto}
	
	<!-- ------------------------------------------------------------------------------- -->
	
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
	               pattern=".{2,150}"
	               aria-describedby="nombreHelp">
	        <small id="nombreHelp" class="form-text text-muted">Nombre del producto</small>
	    </div>
	    
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
	               step="0.5"
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

	    <button type="submit" class="btn btn-block btn-outline-primary">Crear</button>
	</form>
	



<%@include file="/includes/footer.jsp" %>    