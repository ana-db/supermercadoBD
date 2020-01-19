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


-- Volcando estructura de base de datos para hogwarts
DROP DATABASE IF EXISTS `hogwarts`;
CREATE DATABASE IF NOT EXISTS `hogwarts` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hogwarts`;

-- Volcando estructura para tabla hogwarts.alumno
DROP TABLE IF EXISTS `alumno`;
CREATE TABLE IF NOT EXISTS `alumno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `num_mago` varchar(10) NOT NULL,
  `anio_nacimiento` int(4) NOT NULL DEFAULT '0',
  `id_casa` int(11) NOT NULL DEFAULT '0',
  `id_habitacion` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `num_mago` (`num_mago`),
  KEY `FK_casa` (`id_casa`),
  KEY `FK_habitacion` (`id_habitacion`),
  CONSTRAINT `FK_casa` FOREIGN KEY (`id_casa`) REFERENCES `casa` (`id`),
  CONSTRAINT `FK_habitacion` FOREIGN KEY (`id_habitacion`) REFERENCES `habitacion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.alumno: ~11 rows (aproximadamente)
DELETE FROM `alumno`;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` (`id`, `nombre`, `apellido`, `num_mago`, `anio_nacimiento`, `id_casa`, `id_habitacion`) VALUES
	(1, 'Harry', 'Potter', '7777777', 1980, 1, 1),
	(2, 'Ron', 'Weasley', '1111111', 1980, 1, 1),
	(3, 'Hermione', 'Granger', '5555555', 1979, 1, 5),
	(4, 'Ginny', 'Weasley', '2222222', 1981, 1, 5),
	(5, 'Luna', 'Lovegood', '4444444', 1981, 3, 3),
	(6, 'Neville', 'Longbottom', '3333333', 1980, 1, 1),
	(7, 'Draco', 'Malfoy', '6666666', 1980, 4, 4),
	(8, 'Cho', 'Chang', '8888888', 1979, 3, 3),
	(9, 'Cedric', 'Diggory', '9999999', 1977, 2, 2),
	(10, 'Ernie', 'Macmillan', '9090909', 1977, 2, 2),
	(11, 'Gregory', 'Goyle', '6060606', 1980, 4, 4);
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;

-- Volcando estructura para tabla hogwarts.asignatura
DROP TABLE IF EXISTS `asignatura`;
CREATE TABLE IF NOT EXISTS `asignatura` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.asignatura: ~7 rows (aproximadamente)
DELETE FROM `asignatura`;
/*!40000 ALTER TABLE `asignatura` DISABLE KEYS */;
INSERT INTO `asignatura` (`id`, `nombre`) VALUES
	(6, 'Astronomía'),
	(5, 'Defensa Contra las Artes Oscuras'),
	(2, 'Encantamientos'),
	(7, 'Herbología'),
	(4, 'Historia de la Magia'),
	(3, 'Pociones'),
	(1, 'Transformaciones');
/*!40000 ALTER TABLE `asignatura` ENABLE KEYS */;

-- Volcando estructura para tabla hogwarts.casa
DROP TABLE IF EXISTS `casa`;
CREATE TABLE IF NOT EXISTS `casa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `fundador` varchar(50) NOT NULL,
  `id_profesor_jefe` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  UNIQUE KEY `fundador` (`fundador`),
  KEY `FK_profesor_jefe` (`id_profesor_jefe`),
  CONSTRAINT `FK_profesor_jefe` FOREIGN KEY (`id_profesor_jefe`) REFERENCES `profesor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.casa: ~4 rows (aproximadamente)
DELETE FROM `casa`;
/*!40000 ALTER TABLE `casa` DISABLE KEYS */;
INSERT INTO `casa` (`id`, `nombre`, `fundador`, `id_profesor_jefe`) VALUES
	(1, 'Gryffindor', 'Godric Gryffindor', 2),
	(2, 'Hufflepuff', 'Helga Hufflepuff', 5),
	(3, 'Ravenclaw', 'Rowena Ravenclaw', 4),
	(4, 'Slytherin', 'Salazar Slytherin', 3);
/*!40000 ALTER TABLE `casa` ENABLE KEYS */;

-- Volcando estructura para tabla hogwarts.curso
DROP TABLE IF EXISTS `curso`;
CREATE TABLE IF NOT EXISTS `curso` (
  `id_alumno` int(11) NOT NULL DEFAULT '0',
  `id_asignatura` int(11) NOT NULL DEFAULT '0',
  `id_profesor` int(11) NOT NULL DEFAULT '0',
  `nota` float NOT NULL DEFAULT '0',
  `anio_curso` int(11) NOT NULL DEFAULT '0',
  KEY `FK_alumno` (`id_alumno`),
  KEY `FK_asignatura` (`id_asignatura`),
  KEY `FK_profesor` (`id_profesor`),
  CONSTRAINT `FK_alumno` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`),
  CONSTRAINT `FK_asignatura` FOREIGN KEY (`id_asignatura`) REFERENCES `asignatura` (`id`),
  CONSTRAINT `FK_profesor` FOREIGN KEY (`id_profesor`) REFERENCES `profesor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.curso: ~0 rows (aproximadamente)
DELETE FROM `curso`;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` (`id_alumno`, `id_asignatura`, `id_profesor`, `nota`, `anio_curso`) VALUES
	(1, 2, 4, 8, 1996),
	(1, 1, 2, 8, 1996),
	(2, 1, 2, 7, 1996),
	(2, 2, 4, 7, 1996),
	(1, 3, 7, 10, 1996),
	(1, 5, 3, 9, 1996),
	(2, 3, 7, 8, 1996),
	(2, 5, 3, 6.5, 1996),
	(3, 1, 2, 10, 1996),
	(3, 2, 4, 10, 1996),
	(3, 3, 7, 9.5, 1996),
	(3, 5, 3, 8.5, 1996),
	(5, 1, 2, 8.5, 1996),
	(5, 2, 4, 8.5, 1996),
	(5, 6, 6, 9, 1996),
	(8, 1, 2, 8, 1996),
	(8, 5, 3, 8, 1996),
	(8, 6, 6, 9, 1996);
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;

-- Volcando estructura para tabla hogwarts.habitacion
DROP TABLE IF EXISTS `habitacion`;
CREATE TABLE IF NOT EXISTS `habitacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num_camas` int(11) NOT NULL DEFAULT '0',
  `ubicacion` varchar(75) NOT NULL,
  `id_casa` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_casa_habitacion` (`id_casa`),
  CONSTRAINT `FK_casa_habitacion` FOREIGN KEY (`id_casa`) REFERENCES `casa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.habitacion: ~5 rows (aproximadamente)
DELETE FROM `habitacion`;
/*!40000 ALTER TABLE `habitacion` DISABLE KEYS */;
INSERT INTO `habitacion` (`id`, `num_camas`, `ubicacion`, `id_casa`) VALUES
	(1, 5, 'Torre septimo piso, chicos', 1),
	(2, 4, 'Sotano', 2),
	(3, 5, 'Torre de astronomia', 3),
	(4, 4, 'Mazmorras', 4),
	(5, 5, 'Torre septimo piso, chicas', 1);
/*!40000 ALTER TABLE `habitacion` ENABLE KEYS */;

-- Volcando estructura para tabla hogwarts.profesor
DROP TABLE IF EXISTS `profesor`;
CREATE TABLE IF NOT EXISTS `profesor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_casa` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_casa_profesor` (`id_casa`),
  CONSTRAINT `FK_casa_profesor` FOREIGN KEY (`id_casa`) REFERENCES `casa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.profesor: ~11 rows (aproximadamente)
DELETE FROM `profesor`;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` (`id`, `nombre`, `id_casa`) VALUES
	(1, 'Albus Dumbledore', 1),
	(2, 'Minerva McGonagall', 1),
	(3, 'Severus Snape', 4),
	(4, 'Filius Flitwick', 3),
	(5, 'Pomona Sprout', 2),
	(6, 'Sybill Trelawney', 3),
	(7, 'Horace Slughorn', 4),
	(8, 'Cuthbert Binns', 2),
	(9, 'Gilderoy Lockhart', 3),
	(10, 'Remus Lupin', 1),
	(11, 'Quirinus Quirrell', 4);
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;

-- Volcando estructura para tabla hogwarts.quidditch_equipo
DROP TABLE IF EXISTS `quidditch_equipo`;
CREATE TABLE IF NOT EXISTS `quidditch_equipo` (
  `id_alumno_partido` int(11) NOT NULL DEFAULT '0',
  `id_partido` int(11) NOT NULL DEFAULT '0',
  KEY `FK_alumno_partido` (`id_alumno_partido`),
  KEY `FK_partido` (`id_partido`),
  CONSTRAINT `FK_alumno_partido` FOREIGN KEY (`id_alumno_partido`) REFERENCES `alumno` (`id`),
  CONSTRAINT `FK_partido` FOREIGN KEY (`id_partido`) REFERENCES `quidditch_partido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.quidditch_equipo: ~0 rows (aproximadamente)
DELETE FROM `quidditch_equipo`;
/*!40000 ALTER TABLE `quidditch_equipo` DISABLE KEYS */;
INSERT INTO `quidditch_equipo` (`id_alumno_partido`, `id_partido`) VALUES
	(1, 1),
	(2, 1),
	(8, 1),
	(5, 1),
	(1, 2),
	(4, 2),
	(7, 2),
	(11, 2),
	(5, 4),
	(8, 4),
	(9, 4),
	(10, 4);
/*!40000 ALTER TABLE `quidditch_equipo` ENABLE KEYS */;

-- Volcando estructura para tabla hogwarts.quidditch_partido
DROP TABLE IF EXISTS `quidditch_partido`;
CREATE TABLE IF NOT EXISTS `quidditch_partido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `casa1` varchar(50) NOT NULL,
  `casa2` varchar(50) NOT NULL,
  `fecha` datetime NOT NULL,
  `resultado` int(11) NOT NULL DEFAULT '0',
  `id_alumno_mvp` int(11) NOT NULL DEFAULT '0',
  `id_profesor_arbitro` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_alumno_mvp` (`id_alumno_mvp`),
  KEY `FK_profesor_arbitro` (`id_profesor_arbitro`),
  CONSTRAINT `FK_alumno_mvp` FOREIGN KEY (`id_alumno_mvp`) REFERENCES `alumno` (`id`),
  CONSTRAINT `FK_profesor_arbitro` FOREIGN KEY (`id_profesor_arbitro`) REFERENCES `profesor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hogwarts.quidditch_partido: ~0 rows (aproximadamente)
DELETE FROM `quidditch_partido`;
/*!40000 ALTER TABLE `quidditch_partido` DISABLE KEYS */;
INSERT INTO `quidditch_partido` (`id`, `casa1`, `casa2`, `fecha`, `resultado`, `id_alumno_mvp`, `id_profesor_arbitro`) VALUES
	(1, 'Gryffindor', 'Ravenclaw', '2020-01-18 11:00:00', 180, 1, 4),
	(2, 'Gryffindor', 'Slytherin', '2019-12-18 11:00:00', 160, 1, 3),
	(3, 'Gryffindor', 'Hufflepuff', '2020-11-10 11:00:00', 200, 1, 5),
	(4, 'Ravenclaw', 'Hufflepuff', '2019-12-03 11:00:00', 170, 8, 2),
	(5, 'Hufflepuff', 'Slytherin', '2020-01-03 11:00:00', 200, 7, 7),
	(6, 'Slytherin', 'Ravenclaw', '2019-11-28 11:00:00', 150, 7, 6);
/*!40000 ALTER TABLE `quidditch_partido` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
