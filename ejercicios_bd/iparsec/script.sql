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


-- Volcando estructura de base de datos para iparcsec
DROP DATABASE IF EXISTS `iparcsec`;
CREATE DATABASE IF NOT EXISTS `iparcsec` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `iparcsec`;

-- Volcando estructura para tabla iparcsec.departamento
DROP TABLE IF EXISTS `departamento`;
CREATE TABLE IF NOT EXISTS `departamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla iparcsec.departamento: ~5 rows (aproximadamente)
DELETE FROM `departamento`;
/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
INSERT INTO `departamento` (`id`, `nombre`) VALUES
	(1, 'cobol'),
	(2, 'oficinas'),
	(3, 'java'),
	(4, 'sistemas'),
	(5, 'mantenimiento');
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;

-- Volcando estructura para tabla iparcsec.empleado
DROP TABLE IF EXISTS `empleado`;
CREATE TABLE IF NOT EXISTS `empleado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `edad` int(11) NOT NULL DEFAULT '0',
  `sueldo` int(11) NOT NULL DEFAULT '0',
  `primas` int(11) NOT NULL DEFAULT '0',
  `horas_extra` int(11) NOT NULL DEFAULT '0' COMMENT 'horas extra mensuales',
  `id_departamento` int(11) NOT NULL DEFAULT '0',
  `id_puesto` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_departamento` (`id_departamento`),
  KEY `FK_puesto` (`id_puesto`),
  CONSTRAINT `FK_departamento` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id`),
  CONSTRAINT `FK_puesto` FOREIGN KEY (`id_puesto`) REFERENCES `puesto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla iparcsec.empleado: ~14 rows (aproximadamente)
DELETE FROM `empleado`;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` (`id`, `nombre`, `edad`, `sueldo`, `primas`, `horas_extra`, `id_departamento`, `id_puesto`) VALUES
	(1, 'Raul', 36, 25000, 200, 2, 1, 1),
	(2, 'Erlantz', 30, 20000, 150, 10, 1, 1),
	(3, 'Alejandro', 23, 350, 0, 0, 2, 2),
	(4, 'Maria', 36, 30000, 0, 0, 1, 3),
	(5, 'Iker', 27, 28000, 500, 10, 3, 3),
	(6, 'Aitor', 24, 35000, 1000, 0, 2, 4),
	(7, 'Endika', 25, 24000, 150, 5, 3, 1),
	(8, 'Kiryl', 22, 40000, 500, 5, 4, 5),
	(9, 'Inigo', 23, 64000, 800, 40, 4, 3),
	(16, 'Ana', 30, 24000, 150, 5, 3, 1),
	(17, 'Cristian', 25, 12000, 0, 10, 5, 6),
	(18, 'Mikel', 29, 24000, 500, 10, 3, 7),
	(19, 'Joseba', 22, 14000, 0, 0, 5, 8),
	(20, 'Ander', 39, 6000, 10, 10, 3, 9);
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;

-- Volcando estructura para tabla iparcsec.puesto
DROP TABLE IF EXISTS `puesto`;
CREATE TABLE IF NOT EXISTS `puesto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla iparcsec.puesto: ~9 rows (aproximadamente)
DELETE FROM `puesto`;
/*!40000 ALTER TABLE `puesto` DISABLE KEYS */;
INSERT INTO `puesto` (`id`, `nombre`) VALUES
	(1, 'programador'),
	(2, 'administrador'),
	(3, 'analista'),
	(4, 'gerente'),
	(5, 'hacker'),
	(6, 'limpieza'),
	(7, 'diseniador'),
	(8, 'conserje'),
	(9, 'programdor junior');
/*!40000 ALTER TABLE `puesto` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
