-- examen uf2214 - apartado consultas

-- 1. listado de todos los proveedores ordenados alfabeticamente

SELECT id, nombre, direccion, telefono FROM proveedores ORDER BY nombre ASC LIMIT 500;


-- 2. listado de todos los productos organizados por categorias

SELECT 
	p.descripcion AS 'producto',
	c.descripcion AS 'categoria'
 FROM productos p, categorias c WHERE p.id_categoria=c.id ORDER BY c.descripcion asc
 LIMIT 500;

-- 3. las ventas del ultimo mes, mostrando: cliente, producto, categoria y cantidad

SELECT
	v.id AS 'id_factura',
	c.nombre,
	p.descripcion AS 'producto',
	ca.descripcion AS 'categoria',
	cantidad,
	f.fecha
FROM ventas v, facturas f, clientes c, productos p, categorias ca
WHERE
	v.id_factura=f.id
	and f.id_cliente=c.id
	AND v.id_producto=p.id
	AND p.id_categoria=ca.id
	AND f.fecha BETWEEN DATE_SUB(curdate(),INTERVAL 1 MONTH) AND curdate()
LIMIT 500;


-- 4. listado de los 5 productos más vendidos


SELECT p.descripcion AS 'producto', SUM(cantidad) AS 'cantidad'
FROM productos AS p, ventas AS v
WHERE 
	p.id = v.id_producto
GROUP BY id_producto
ORDER BY cantidad DESC
LIMIT 5;

SELECT
	p.descripcion AS 'producto',
	(SELECT sum(v.cantidad) FROM ventas v WHERE v.id_producto=p.id) AS 'cantidad_total'
FROM
	ventas v,
	productos p
WHERE
	v.id_producto=p.id
GROUP BY producto
ORDER BY cantidad_total desc
LIMIT 5;


-- 5. listado de los 5 clientes que más dinero han gastado

SELECT cl.nombre, ROUND(SUM(v.cantidad*p.precio),2) AS 'total_gastado'
FROM clientes AS cl, facturas AS f, ventas AS v, productos AS p
WHERE 
	cl.id = f.id_cliente AND
	f.id = v.id_factura AND
	v.id_producto = p.id
GROUP BY cl.nombre
ORDER BY total_gastado DESC
LIMIT 5;

SELECT
	c.nombre,
	(SELECT sum(p.precio) FROM ventas v, productos p, facturas f WHERE v.id_producto=p.id and f.id_cliente=c.id) AS 'dinero_gastado'
FROM
	ventas v,
	clientes c,
	facturas f,
	productos p
WHERE
	v.id_factura=f.id
	AND f.id_cliente=c.id
	AND v.id_producto=p.id
GROUP BY c.nombre
ORDER BY dinero_gastado desc
LIMIT 5;
