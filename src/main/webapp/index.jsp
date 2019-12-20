<%@ page contentType="text/html; charset=UTF-8" %>

<%@include file="includes/header.jsp" %>
    
    	
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
	                        <p class="descripcion">"${p.descripcion}"</p>
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
        	
        	
        	
            
        <!-- ----------------------------------------------- -->
        
        <%
			ArrayList<Producto> productos = (ArrayList<Producto>)request.getAttribute("productos");
		%>
    
    	<ul>
			<% for ( Producto p : productos ){ %>
			
				<li>
					<%=p.getId()%> - <%=p.getNombre()%> - <%=p.getPrecio()%> 
					<img src="<%=p.getImagen()%>" style="width:100px; height: 100px;" alt="foto del producto">
					<%=p.getDescripcion()%> - <%=p.getDescuento()%> 
				</li>
				
			<% } %>
		</ul>
        
        
        
<%@include file="includes/footer.jsp" %>    
