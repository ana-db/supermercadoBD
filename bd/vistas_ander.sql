-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         10.1.38-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para supermercado
CREATE DATABASE IF NOT EXISTS `supermercado_vistas` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `supermercado_vistas`;

-- Volcando estructura para tabla supermercado.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla supermercado.categoria: ~8 rows (aproximadamente)
DELETE FROM `categoria`;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`id`, `nombre`) VALUES
	(5, 'bebida'),
	(3, 'electrodomesticos'),
	(14, 'embutidos'),
	(8, 'fruteria'),
	(1, 'lacteos'),
	(2, 'musica'),
	(7, 'oferta'),
	(6, 'otra'),
	(4, 'panaderia');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

-- Volcando estructura para tabla supermercado.historico
CREATE TABLE IF NOT EXISTS `historico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `precio` float NOT NULL DEFAULT '0',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_producto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_producto` (`id_producto`),
  CONSTRAINT `FK_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla supermercado.historico: ~8 rows (aproximadamente)
DELETE FROM `historico`;
/*!40000 ALTER TABLE `historico` DISABLE KEYS */;
INSERT INTO `historico` (`id`, `precio`, `fecha`, `id_producto`) VALUES
	(1, 12, '2020-01-10 08:21:25', 8),
	(2, 12, '2020-01-10 08:21:34', 8),
	(3, 56, '2020-01-10 08:21:40', 8),
	(4, 100, '2020-01-10 08:21:52', 8),
	(5, 400, '2020-01-10 08:22:15', 8),
	(6, 400, '2020-01-10 08:22:23', 8),
	(7, 400, '2020-01-10 08:22:25', 8),
	(8, 400, '2020-01-10 08:24:02', 8),
	(9, 0, '2020-01-10 08:24:06', 8),
	(10, 1, '2020-01-10 08:24:12', 8),
	(11, 2, '2020-01-15 08:20:00', 8),
	(12, 0, '2020-01-15 08:20:05', 12),
	(13, 0, '2020-01-15 08:20:11', 22),
	(14, 0, '2020-01-15 08:20:29', 29),
	(15, 0, '2020-01-15 08:20:42', 30),
	(16, 0, '2020-01-15 08:20:53', 31),
	(17, 0, '2020-01-15 08:21:01', 36),
	(18, 0, '2020-01-15 08:21:12', 21);
/*!40000 ALTER TABLE `historico` ENABLE KEYS */;

-- Volcando estructura para tabla supermercado.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `precio` float NOT NULL DEFAULT '0',
  `descuento` int(11) DEFAULT '0' COMMENT 'porcentaje descuento de 0 a 100',
  `fecha_modificacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_baja` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_usuario` (`id_usuario`),
  KEY `FK_categoria` (`id_categoria`),
  CONSTRAINT `FK_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FK_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla supermercado.producto: ~8 rows (aproximadamente)
DELETE FROM `producto`;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id`, `nombre`, `id_categoria`, `id_usuario`, `precio`, `descuento`, `fecha_modificacion`, `fecha_baja`) VALUES
	(8, 'queso', 1, 1, 30, 0, '2020-01-15 08:20:00', NULL),
	(12, 'chorizo', 14, 1, 12, 10, '2020-01-15 08:22:16', NULL),
	(21, 'panto tumaca', 7, 4, 1.6, 10, '2020-01-15 08:22:43', '2020-01-15 08:21:23'),
	(22, 'pan', 4, 1, 1.2, 0, '2020-01-15 08:22:20', NULL),
	(29, 'vino', 5, 1, 12, 30, '2020-01-15 08:22:27', NULL),
	(30, 'jamon', 14, 1, 500, 10, '2020-01-15 08:22:29', NULL),
	(31, 'queso manchego', 1, 1, 8, 0, '2020-01-15 08:20:53', NULL),
	(36, 'tomate', 8, 1, 0.4, 0, '2020-01-15 08:22:35', NULL);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla supermercado.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '1: Usuario normal   2: Administrador',
  `nombre` varchar(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla supermercado.rol: ~2 rows (aproximadamente)
DELETE FROM `rol`;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`id`, `nombre`) VALUES
	(2, 'administrador'),
	(1, 'usuario');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

-- Volcando estructura para tabla supermercado.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `contrasenia` varchar(50) NOT NULL DEFAULT '0',
  `id_rol` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_rol` (`id_rol`),
  CONSTRAINT `FK_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla supermercado.usuario: ~2 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nombre`, `contrasenia`, `id_rol`) VALUES
	(1, 'admin', 'admin', 2),
	(4, 'Dolores', '123456', 1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Volcando estructura para procedimiento supermercado.pa_categoria_delete
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_categoria_delete`(
	IN `pId` INT
)
BEGIN

	DELETE FROM categoria WHERE id = pId;

END//
DELIMITER ;

-- Volcando estructura para procedimiento supermercado.pa_categoria_getall
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_categoria_getall`()
BEGIN

   	-- nuestro primer PA
   	/*  tiene pinta que tambien comentarios de bloque */
    SELECT id, nombre FROM categoria ORDER BY nombre ASC LIMIT 500;
    
    -- desde java executeQuery
    -- retorna ResultSet

END//
DELIMITER ;

-- Volcando estructura para procedimiento supermercado.pa_categoria_get_by_id
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_categoria_get_by_id`(
	IN `pId` INT
)
BEGIN

	SELECT id, nombre FROM categoria WHERE id = pId;

END//
DELIMITER ;

-- Volcando estructura para procedimiento supermercado.pa_categoria_insert
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_categoria_insert`(
	IN `p_nombre` VARCHAR(100),
	OUT `p_id` INT
)
BEGIN

	-- crear nuevo registro
	INSERT INTO categoria ( nombre ) VALUES ( p_nombre );
	
	-- obtener el ID generado y SETearlo al parametro salida p_id
	SET p_id = LAST_INSERT_ID();
	

END//
DELIMITER ;

-- Volcando estructura para procedimiento supermercado.pa_categoria_update
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_categoria_update`(
	IN `pId` INT,
	IN `pNombre` VARCHAR(100)
)
BEGIN


	UPDATE categoria SET nombre = pNombre WHERE id = pId;
	
	-- desde java executeUpdate, retorna affectedRows int

END//
DELIMITER ;

-- Volcando estructura para función supermercado.HELLO_WORLD
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `HELLO_WORLD`() RETURNS varchar(100) CHARSET utf8
BEGIN

	RETURN "hola mundo";

END//
DELIMITER ;

-- Volcando estructura para función supermercado.HELLO_WORLD2
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `HELLO_WORLD2`(
	`pNombre` VARCHAR(50)
) RETURNS varchar(100) CHARSET utf8
BEGIN

	DECLARE nombre VARCHAR(100) DEFAULT 'anonimo';

   IF( TRIM(pNombre) != '' ) THEN
   	SET nombre = TRIM(pNombre);
   END IF;

	--	RETURN "Hello" +  pNombre;
	RETURN CONCAT("hello"," ",nombre);

END//
DELIMITER ;

-- Volcando estructura para disparador supermercado.tau_producto_historico
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tau_producto_historico` AFTER UPDATE ON `producto` FOR EACH ROW BEGIN

	-- meter un registro en la tabla historico con el precio del producto cambiado
	
	-- comprobar que inserte solo si cambia el precio
	
	IF ( NEW.precio <> OLD.precio ) THEN
	
		INSERT INTO historico (precio, id_producto ) VALUES ( OLD.precio , OLD.id );
		
	END IF;
		

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador supermercado.tbi_producto
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tbi_producto` BEFORE INSERT ON `producto` FOR EACH ROW BEGIN

	/* 
		comprobar que el descuento sea entre 0 y 100 
		si descuento < 0 = 0
		si descuento > 100 = 100
	*/

	IF NEW.descuento < 0 THEN 
		SET NEW.descuento = 0; 
	END IF;
	
	IF NEW.descuento > 100 THEN 
		SET NEW.descuento = 100; 
	END IF;	

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador supermercado.tbu_producto
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tbu_producto` BEFORE UPDATE ON `producto` FOR EACH ROW BEGIN

	IF NEW.descuento < 0 THEN 
		SET NEW.descuento = 0; 
	END IF;
	

	IF NEW.descuento > 100 THEN 
		SET NEW.descuento = 100; 
	END IF;	

	-- INSERT INTO categoria (nombre) VALUE ( CONCAT(NEW.descuento, 'categoria'));

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
