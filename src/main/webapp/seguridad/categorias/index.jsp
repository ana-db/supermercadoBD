<%@ page contentType="text/html; charset=UTF-8"%>

<%@include file="/includes/header.jsp"%>


<h1 class="text-center text-info" id="top">Categorías</h1>


<a href="seguridad/categorias?accion=formulario">Nueva Categoría</a>


	<style>
	.foto-perfil-producto {
		width: 3.5em;
	}
	</style>
	
	<!-- ${categorias} -->
	
	
	<!-- DATATABLES -->
	<link rel="stylesheet" type="text/css"
		href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" />
	<link rel="stylesheet" type="text/css"
		href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" />
	
	<!-- JAVASCRIPT los incluimos en el pie-->
	
	<!-- TABLA -->
	<table class="tabla reponsive display">
		<thead>
			<tr>
				<th>ID</th>
				<th>Categoría</th>
				<th>Acción</th>
			</tr>
		</thead>
	
		<tbody>
	
			<c:forEach items="${categorias}" var="c">
				<tr>
					<td>${c.id}</td>
					<td>${c.nombre}</td>
					<td><a href="seguridad/categorias?accion=formulario&id=${c.id}">Editar</a>, <a href="seguridad/categorias?accion=eliminar&id=${c.id}">Borrar</a></td>
				</tr>
			</c:forEach>
	
		</tbody>
	
		<tfoot>
			<tr>
				<th>ID</th>
				<th>Categoría</th>
				<th>Acción</th>
			</tr>
		</tfoot>
	</table>




<!-- ------------------------------------------- -->



<a id="btn-top" href="#top" class="btn btn-primary">top</a>

<%@include file="/includes/footer.jsp"%>
