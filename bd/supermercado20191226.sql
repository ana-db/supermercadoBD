
-- consulta de LOGIN
SELECT id, nombre, contrasenia FROM usuario WHERE nombre ='admin' AND contrasenia ='123456';

-- INSERT INTO usuario (nombre, contrasenia) VALUES ('admin', '123456'); 
-- INSERT INTO usuario (nombre, contrasenia) VALUES ('pepe', '123456'), ('pepa', '123456'); 

-- DELETE FROM usuario WHERE id = 3; 

-- UPDATE usuario SET nombre = 'Dolores', contrasenia = '56789' WHERE id = 2;

SELECT id, nombre, contrasenia FROM usuario ORDER BY id DESC LIMIT 500;
usuario
