-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.18 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para uf2214
DROP DATABASE IF EXISTS `uf2214`;
CREATE DATABASE IF NOT EXISTS `uf2214` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `uf2214`;

-- Volcando estructura para tabla uf2214.categorias
DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `descripcion` (`descripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla uf2214.categorias: ~10 rows (aproximadamente)
DELETE FROM `categorias`;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` (`id`, `descripcion`) VALUES
	(5, 'alimentacion'),
	(7, 'calzado'),
	(4, 'electrodomesticos'),
	(3, 'informatica'),
	(1, 'juguetes'),
	(9, 'limpieza'),
	(2, 'musica'),
	(8, 'panaderia'),
	(10, 'papeleria'),
	(6, 'ropa');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;

-- Volcando estructura para tabla uf2214.clientes
DROP TABLE IF EXISTS `clientes`;
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL DEFAULT '',
  `direccion` varchar(250) NOT NULL DEFAULT '',
  `telefono` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla uf2214.clientes: ~10 rows (aproximadamente)
DELETE FROM `clientes`;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` (`id`, `nombre`, `direccion`, `telefono`) VALUES
	(1, 'Erlantz', 'Artazagane 28 4 B', '685777675'),
	(2, 'Joseba', 'El retiro 55 3 A', '678555678'),
	(3, 'Federico', 'Parque de los patos', '678888786'),
	(4, 'Musolini', 'Calle italiana numero 4', '94456666'),
	(5, 'Ander', 'Calle ponme un diez', '678777678'),
	(6, 'Cristian', 'Avenida del boxeo amateur numero 100', '654555333'),
	(7, 'Raul', 'Ciudad flotante imaginaria en la cima del amboto', '678678678'),
	(8, 'Ana', 'Calle mezo n 8 Astrabudua', '678889789'),
	(9, 'Harriet', 'Melham street Cheltenham', '44435678'),
	(10, 'Jon', 'Artazagane 28 4 B', '685728457');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;

-- Volcando estructura para tabla uf2214.facturas
DROP TABLE IF EXISTS `facturas`;
CREATE TABLE IF NOT EXISTS `facturas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_cliente` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_cliente` (`id_cliente`),
  CONSTRAINT `fk_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla uf2214.facturas: ~10 rows (aproximadamente)
DELETE FROM `facturas`;
/*!40000 ALTER TABLE `facturas` DISABLE KEYS */;
INSERT INTO `facturas` (`id`, `fecha`, `id_cliente`) VALUES
	(1, '2020-01-16 12:14:14', 9),
	(2, '2015-06-16 12:14:41', 9),
	(3, '2019-07-06 09:10:21', 1),
	(4, '2020-01-16 12:16:08', 10),
	(6, '2018-09-01 02:00:00', 5),
	(7, '2019-04-03 13:00:45', 4),
	(8, '2015-04-23 12:17:46', 3),
	(9, '2018-09-23 15:34:00', 6),
	(10, '2015-02-16 12:18:42', 5),
	(11, '2020-01-01 01:00:00', 5);
/*!40000 ALTER TABLE `facturas` ENABLE KEYS */;

-- Volcando estructura para tabla uf2214.productos
DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL DEFAULT '',
  `precio` float NOT NULL DEFAULT '0',
  `id_categoria` int(11) NOT NULL DEFAULT '0',
  `id_proveedor` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `descripcion` (`descripcion`),
  KEY `fk_categoria` (`id_categoria`),
  KEY `fk_proveedor` (`id_proveedor`),
  CONSTRAINT `fk_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`),
  CONSTRAINT `fk_proveedor` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla uf2214.productos: ~10 rows (aproximadamente)
DELETE FROM `productos`;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` (`id`, `descripcion`, `precio`, `id_categoria`, `id_proveedor`) VALUES
	(1, 'tornillos que no se caen', 9.99, 4, 1),
	(4, 'nutria asada', 24, 5, 5),
	(6, 'colonia o de tuaret', 50.49, 9, 6),
	(7, 'baguette', 0.99, 8, 10),
	(8, 'chancletas de cuero', 5.99, 7, 9),
	(9, 'piano de plastico', 500, 2, 4),
	(10, 'guitarra imitacion slash', 999, 2, 4),
	(12, 'violin stradivarius', 5055.99, 2, 4),
	(14, 'frutos secos variados', 2.6, 5, 7),
	(15, 'cola light', 1.2, 5, 2);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;

-- Volcando estructura para tabla uf2214.proveedores
DROP TABLE IF EXISTS `proveedores`;
CREATE TABLE IF NOT EXISTS `proveedores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL DEFAULT '',
  `direccion` varchar(250) NOT NULL DEFAULT '',
  `telefono` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla uf2214.proveedores: ~10 rows (aproximadamente)
DELETE FROM `proveedores`;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` (`id`, `nombre`, `direccion`, `telefono`) VALUES
	(1, 'aceros galvanizados', 'calle del acero numero 3', '94456787'),
	(2, 'alimentacion lola', 'el sitio mas adecuado para la comida', '876456345'),
	(3, 'juguetes evaristo roman', 'madrid en la plaza numero 5', '9445673241'),
	(4, 'musicas electricas de guitarra', 'poligono industrial 4 durango', '678555432'),
	(5, 'alimentacion cenutrio', 'parque nacional yellowstrone america', '5436785657'),
	(6, 'drogas maria', 'farmacia numero 78', '654678678'),
	(7, 'alimentacion frutos secos', 'calle la arboleda jamaica 89', '654789678'),
	(8, 'electrodomesticos chavi', 'barcelona, centro comercial las monas', '856789443'),
	(9, 'textiles gipsy king', 'debajo del puente', '678456342'),
	(10, 'panes panymas', 'artaza 34 leioa', '654789678');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;

-- Volcando estructura para tabla uf2214.ventas
DROP TABLE IF EXISTS `ventas`;
CREATE TABLE IF NOT EXISTS `ventas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_factura` int(11) NOT NULL DEFAULT '0',
  `id_producto` int(11) NOT NULL DEFAULT '0',
  `cantidad` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_factura` (`id_factura`),
  KEY `fk_producto` (`id_producto`),
  CONSTRAINT `fk_factura` FOREIGN KEY (`id_factura`) REFERENCES `facturas` (`id`),
  CONSTRAINT `fk_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla uf2214.ventas: ~0 rows (aproximadamente)
DELETE FROM `ventas`;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` (`id`, `id_factura`, `id_producto`, `cantidad`) VALUES
	(2, 8, 6, 1),
	(4, 1, 4, 2),
	(5, 1, 14, 5),
	(6, 6, 10, 1),
	(7, 6, 1, 10),
	(9, 6, 8, 1),
	(10, 10, 15, 3),
	(11, 10, 8, 1),
	(12, 10, 1, 5),
	(14, 11, 12, 1),
	(15, 2, 7, 4),
	(16, 2, 15, 2);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;

-- Volcando estructura para procedimiento uf2214.pa_facturas_buscar_entre_fechas
DROP PROCEDURE IF EXISTS `pa_facturas_buscar_entre_fechas`;
DELIMITER //
CREATE PROCEDURE `pa_facturas_buscar_entre_fechas`(
	IN `p_fecha_inicio` TIMESTAMP,
	IN `p_fecha_fin` TIMESTAMP
)
BEGIN

	SELECT id FROM facturas WHERE fecha between p_fecha_inicio and p_fecha_fin;

END//
DELIMITER ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
