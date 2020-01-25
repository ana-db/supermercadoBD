<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="includes/header.jsp" %>
    
    	<!-- ${categorias}
    	 ${productos} -->
    	 
    	 
    	 <!-- FILTRO/BUSCADOR POR CATEGORÍA Y/O NOMBRE PRODUCTO -->
    	 <div class="row">
			<div class="col-lg-12">
				<div class="search-wrapper">
					<form action="inicio" method="POST">
						<div class="row">
							<div class="col-10">
								<div class="form-group">
									<label for="category-select">Selecciona una categoría: </label>
									<select name="id" class="form-control" id="category-select">
										<option value="0">Todas las categorías</option>
										<c:forEach items="${categorias}" var="c">
											<option value="${c.id}" ${(c.id eq cId)?"selected":""}>${c.nombre}</option>
										</c:forEach>
									</select>
									
									<input name="nombre" class="form-control" type="text" placeholder="Nombre del producto" value="${pNombre}" aria-label="Search">
								</div>
							</div>
							<div class="col-2 text-right">
								<button type="submit" class="btn btn-primary"><span class="fas fa-search"></span></button>
							</div>
						</div>
					</form>
				</div> <!-- fin class="search-wrapper"  -->
			</div> <!-- fin class="col-lg-12"  -->
		</div> <!-- fin class="row"  -->
    	 
    	 
    	 

        <div class="row contenedor-productos">
        
        
        	<c:forEach items="${productos}" var = "p">
        		<div class="col">

	                <!-- PRODUCTO -->
	                <div class="producto">
	                    <span class="descuento">${p.descuento}%</span>
	                    <img src="${p.imagen}" alt="imagen del producto ${p.nombre}">
	
	                    <div class="body">
	                        <p>
	                            <span class="precio-descuento">
	                            	<fmt:formatNumber minFractionDigits="2" type="currency" currencySymbol="€" value="${p.precio}"/>
	                            </span> 
	                            <span class="precio-original">
	                            	<fmt:formatNumber minFractionDigits="2" type="currency" currencySymbol="€" value="${p.precioDescuento}"/>
	                            </span>    
	                        </p>
	                        <p class="text-muted precio-unidad ">${p.nombre}</p>
	                        <p class="descripcion">${p.descripcion}</p>
	                        <p class="descripcion">${p.categoria.nombre}</p>
	                        <p class="descripcion">${p.usuario.nombre}</p>
	                    </div>
	
	                    <div class="botones">
	                        <button class="minus">-</button>
	                        <input type="text" value="1">
	                        <button class="plus">+</button>
	                    </div>
	
	                    <button class="carro">Añadir al carro</button>
	
	                </div>
	                <!-- /.producto -->
	
	            </div>            
        	
        	</c:forEach>
        
        
        
<%@include file="includes/footer.jsp" %>    
