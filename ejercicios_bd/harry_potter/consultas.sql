/*
SELECT 
	a.nombre AS 'nombre', 
	ROUND(AVG(nota),2) AS 'nota_media'
FROM 
	alumno a, curso c
WHERE
	a.id = c.id_alumno AND c.anio_curso = 1996
GROUP BY a.nombre
ORDER BY nota DESC
LIMIT 11
;
*/

-- 1. Alumno con mejor nota en cada materia de este año (NO ESTÁ BIEN DEL TODO, ALGUNA ASIGNATURA REPE)
SELECT  
	asig.nombre AS 'asignatura',
	anio_curso,
	MAX(nota) AS 'mejor nota', 
	(
		SELECT 
			nombre
		FROM
			alumno
		WHERE
			id = c.id_alumno
		ORDER BY nota DESC
		LIMIT 1
	) AS 'alumno'
FROM 
	alumno a, curso c, asignatura asig
WHERE
	a.id = c.id_alumno AND c.id_asignatura = asig.id AND c.anio_curso = 1996 
GROUP BY asig.nombre, a.nombre
-- HAVING COUNT(asig.nombre) = ( Select DISTINCT COUNT(asig.id) )
ORDER BY nota DESC
LIMIT 7
;


-- subconsulta (ok):
/*
SELECT 
	a.nombre AS 'alumno', 
	asig.nombre AS 'asignatura',
	nota, 
	anio_curso
FROM
	alumno a, curso c, asignatura asig
WHERE
	a.id = c.id_alumno AND c.id_asignatura = asig.id AND c.anio_curso = 1996 AND asig.nombre = 'Pociones'
ORDER BY nota DESC
LIMIT 1
;
*/


-- 2. Profesor que ha impartido una materia en un año concreto 
-- (por ejemplo profesor de defensa contra artes oscuras en 2017)
SELECT  
	a.nombre AS 'asignatura',
	p.nombre AS 'profesor',
	anio_curso
FROM
	profesor p, curso c, asignatura a
WHERE
	p.id = c.id_profesor AND c.id_asignatura = a.id
GROUP BY p.nombre
ORDER BY a.nombre ASC
LIMIT 15
;


-- 3. Listado de los 3 alumnos con más MVPs en los partidos de Quidditch. 
SELECT 
	a.nombre AS 'alumno',
	COUNT(1) AS 'num_veces_MVP'
FROM
	alumno a, quidditch_partido qp 
WHERE
	a.id = qp.id_alumno_mvp
GROUP BY a.nombre
-- HAVING COUNT(1) > 1
ORDER BY num_veces_MVP DESC
LIMIT 3
;


-- 4. Nota media de los alumnos para una materia y un año.
SELECT 
	a.nombre AS 'asignatura', 
	ROUND(AVG(nota),2) AS 'nota_media',
	anio_curso
FROM 
	asignatura a, curso c
WHERE
	a.id = c.id_asignatura 
GROUP BY a.nombre
ORDER BY nota DESC
LIMIT 11
;


-- 5. Nota media de los alumnos de cada una de las casas por materia. 

SELECT  
	ca.nombre AS 'casa', 
	ROUND(AVG(nota),2) AS 'nota_media',
	(
		SELECT 
			nombre
		FROM 
			asignatura
		WHERE
			id = cu.id_asignatura AND al.id = cu.id_alumno
		GROUP BY nombre
		ORDER BY nombre ASC
		LIMIT 1
	) AS 'asignatura'
FROM 
	asignatura asig, curso cu, casa ca, alumno al
WHERE
	asig.id = cu.id_asignatura AND al.id = cu.id_alumno AND al.id_casa = ca.id 
GROUP BY ca.nombre, asig.nombre
ORDER BY ca.nombre, asig.nombre ASC
LIMIT 10
;

-- subconsulta (ok):
SELECT 
	ca.nombre AS 'casa',
	asig.nombre AS 'asignatura', 
	ROUND(AVG(nota),2) AS 'nota_media'
FROM 
	asignatura asig, curso cu, casa ca, alumno al
WHERE
	asig.id = cu.id_asignatura AND al.id = cu.id_alumno AND al.id_casa = ca.id AND ca.nombre = 'Ravenclaw'
GROUP BY asig.nombre
ORDER BY ca.nombre, asig.nombre ASC
LIMIT 10
;
