-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.1.72-community - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para supermercado
DROP DATABASE IF EXISTS `supermercado`;
CREATE DATABASE IF NOT EXISTS `supermercado` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `supermercado`;

-- Volcando estructura para tabla supermercado.producto
DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `precio` float NOT NULL DEFAULT '0',
  `imagen` varchar(150) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `descuento` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla supermercado.producto: ~40 rows (aproximadamente)
DELETE FROM `producto`;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id`, `nombre`, `precio`, `imagen`, `descripcion`, `descuento`) VALUES
	(1, 'leche', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(2, 'Cafe', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(4, 'Turrón', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(5, 'Gulas', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(6, 'Turrón', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(7, 'Zumo de naranja', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(8, 'Leche', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(9, 'Mermelada', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(10, 'Helado', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(11, 'Queso', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(12, 'Huevos', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(13, 'Tomates', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(14, 'lechuga', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(15, 'Turrón', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(16, 'Gulas', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(17, 'Turrón', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(18, 'Zumo de naranja', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(19, 'Leche', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(20, 'Mermelada', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(21, 'Helado', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(22, 'Queso', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(23, 'Huevos', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(24, 'Tomates', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(25, 'Turrón', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(26, 'Gulas', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(27, 'Turrón', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(28, 'Zumo de naranja', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(30, 'Mermelada', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(32, 'Queso', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(33, 'Huevos', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(34, 'Tomates', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(35, 'Tortilla de patata', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(36, 'Cafe con leche', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(37, 'Tortilla', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(38, 'Donetes', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(40, 'te', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(41, 'Azucar', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(42, 'azucar', 0, 'https://image.flaticon.com/icons/png/512/372/372627.png', '0', 0),
	(43, 'mock', 10, 'aaa', 'lorem', 100),
	(44, 'Sal', 1, 'https://supermercado.eroski.es/images/917372.jpg', 'Sal marina fina, paquete 1 kg', 0);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla supermercado.usuario
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `contrasenia` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla supermercado.usuario: ~2 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nombre`, `contrasenia`) VALUES
	(1, 'admin', '123456'),
	(2, 'Dolores', '56789');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
