<%@ page contentType="text/html; charset=UTF-8" %>


</main>

    <div class="footer-wrapper">
        <footer class="container py-5">
            <div class="row">
                <div class="col-12 col-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="d-block mb-2" role="img" viewBox="0 0 24 24" focusable="false"><title>Product</title><circle cx="12" cy="12" r="10"/><path d="M14.31 8l5.74 9.94M9.69 8h11.48M7.38 12l5.74-9.94M9.69 16L3.95 6.06M14.31 16H2.83m13.79-4l-5.74 9.94"/></svg>
                    <small class="d-block mb-3 text-muted">&copy; 2019</small>
                </div>
    
                <div class="col-6 col-md offset-6">
                    <h5>Enlaces de Interes</h5>
                    <ul class="list-unstyled text-small">
                        <li><a class="text-muted" href="politica-privacidad.html">Politica Privacidad</a></li>
                        <li><a class="text-muted" href="localizacion.html">LocalizaciÃ³n</a></li>
                        <li><a class="text-muted" href="contacto.html">Contacto</a></li>                    
                    </ul>
                </div>

            </div>    
        </footer>
    </div>    

    <a id="btn-top" href="#top" class="btn btn-primary">top</a>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

	<!-- JAVASCRIPT DATATABLES -->
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
	    
	<script>
       	//nuestro script para seleccionar la tabla y ejecutar plugin
       	//con este script conseguimos que la tabla se adapte a las distintas posiciones del dispositivo y sea visible siempre (https://datatables.net/reference/option/responsive - https://datatables.net/extensions/responsive/examples/styling/compact) 
        $(document).ready(function() { //espera que esté todo el DOM (Document Object Mode, toda la págia web) cargado y listo. Podría cascar si cuando empieza a ejecutar, todavía no ha cargado la tabla
        	$('.tabla').DataTable( { 
        	 	responsive: true, 
        	 	language: 
        	 		{
		    			"sProcessing":     "Procesando...",
		                "sLengthMenu":     "Mostrar _MENU_ registros",
		                "sZeroRecords":    "No se encontraron resultados",
		                "sEmptyTable":     "Ningún dato disponible en esta tabla =(",
		                "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
		                "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
		                "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
		                "sInfoPostFix":    "",
		                "sSearch":         "Buscar:",
		                "sUrl":            "",
		                "sInfoThousands":  ",",
		                "sLoadingRecords": "Cargando...",
		                "oPaginate": {
		                    "sFirst":    "Primero",
		                    "sLast":     "Último",
		                    "sNext":     "Siguiente",
		                    "sPrevious": "Anterior"
		                },
		                "oAria": {
		                    "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
		                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
		                },
		                "buttons": {
		                    "copy": "Copiar",
		                    "colvis": "Visibilidad"
		                }	
	         	 	} //cerramos language
				} );
	         } );
         </script>
         
    </body>

</html>