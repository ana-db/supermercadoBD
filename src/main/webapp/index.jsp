<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="includes/header.jsp" %>
    
    	${categorias}
    	<!-- ${productos} -->

        <div class="row contenedor-productos">
        
        
        	<c:forEach items="${productos}" var = "p">
        		<div class="col">

	                <!-- producto -->
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
