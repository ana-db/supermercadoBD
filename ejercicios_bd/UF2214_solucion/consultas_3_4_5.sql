-- 3. Las ventas del último mes, mostrando: cliente, producto, categoría y cantidad 

/*
SELECT MONTH(NOW()); -- nos da el mes actual
SELECT MONTH('2020-12-16'); -- nos da el mes correspondiente a esa fecha
*/

SELECT 
	v.id AS 'venta_id',
	f.fecha,
	p.descripcion 'producto',
	c.descripcion 'categoria',
	cli.nombre 'cliente',
	cantidad
FROM
	ventas v, productos p, categorias c, facturas f, clientes cli
WHERE 
	v.id_producto = p.id AND
	p.id_categoria = c.id AND
	v.id_factura = f.id AND
	f.id_cliente = cli.id AND
	
	-- conseguimos el último mes:
	YEAR(NOW())=YEAR(f.fecha) AND 
	MONTH(NOW())=MONTH(f.fecha) 
	
ORDER BY f.fecha DESC;


-- 4. listado de los 5 productos más vendidos
SELECT 
	p.id,
	p.descripcion AS 'producto',
	SUM(cantidad) AS 'cantidad_total'
FROM
	ventas v, productos p 
WHERE
	v.id_producto = p.id
GROUP BY v.id_producto
ORDER BY cantidad_total DESC
LIMIT 5
;


-- 5. listado de los 5 clientes que más dinero han gastado
SELECT 
	cli.nombre,
	ROUND(SUM(p.precio * v.cantidad),2) AS 'gastado'
FROM
	clientes cli, facturas f, ventas v, productos p
WHERE
	cli.id = f.id_cliente AND
	f.id = v.id_factura AND
	v.id_producto = p.id	
GROUP BY cli.id
ORDER BY gastado DESC
LIMIT 5
;

