CREATE DATABASE  IF NOT EXISTS `empadronamiento_2.0` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `empadronamiento_2.0`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: empadronamiento_2.0
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `municipio`
--

DROP TABLE IF EXISTS `municipio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `municipio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(15) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipio`
--

LOCK TABLES `municipio` WRITE;
/*!40000 ALTER TABLE `municipio` DISABLE KEYS */;
INSERT INTO `municipio` VALUES (1,'013','Barakaldo'),(2,'080','Trapagaran'),(3,'044','Getxo');
/*!40000 ALTER TABLE `municipio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `persona` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (2,'16897163Y','Armando'),(3,'77561777G','Lola'),(4,'13914860K','Dolores'),(9,'16955217P','Luis'),(12,'65881837Q','Armanda'),(20,'03534009J','Pepe'),(22,'67020729V','Manolo'),(25,'98321947Y','Rodrigo'),(30,'74182666F','Espe');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vivienda`
--

DROP TABLE IF EXISTS `vivienda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vivienda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(15) NOT NULL,
  `calle` varchar(45) NOT NULL,
  `numero` int(11) DEFAULT NULL,
  `id_municipio` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`),
  KEY `fk_vivienda_municipio_idx` (`id_municipio`),
  CONSTRAINT `fk_vivienda_municipio` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vivienda`
--

LOCK TABLES `vivienda` WRITE;
/*!40000 ALTER TABLE `vivienda` DISABLE KEYS */;
INSERT INTO `vivienda` VALUES (20,'C12','Esperanza',5,1),(21,'C13','Ibaizabal',NULL,1),(22,'C14','Juan de Garay',8,1),(23,'TP1','Minerias',1,2),(24,'TP2','1ºMayo',5,2),(25,'GTX1','Gobelaurre',4,3),(29,'GTX2','Calle Mayor',34,3);
/*!40000 ALTER TABLE `vivienda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vivienda_has_persona`
--

DROP TABLE IF EXISTS `vivienda_has_persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vivienda_has_persona` (
  `id_persona` int(11) NOT NULL,
  `id_vivienda` int(11) NOT NULL,
  `propietario` tinyint(4) DEFAULT '0' COMMENT '0: No propietario\n1: Propietario',
  `empadronado` tinyint(4) DEFAULT '0' COMMENT '0: No empadronado\n1: Empadronado',
  `alquilado` tinyint(4) DEFAULT '0' COMMENT '0: No alquilado\n1:Aquilado',
  PRIMARY KEY (`id_persona`,`id_vivienda`),
  KEY `vivienda_has_persona_idx` (`id_vivienda`) /*!80000 INVISIBLE */,
  CONSTRAINT `persona_has_vivienda` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`),
  CONSTRAINT `vivienda_has_persona` FOREIGN KEY (`id_vivienda`) REFERENCES `vivienda` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vivienda_has_persona`
--

LOCK TABLES `vivienda_has_persona` WRITE;
/*!40000 ALTER TABLE `vivienda_has_persona` DISABLE KEYS */;
INSERT INTO `vivienda_has_persona` VALUES (2,22,0,1,1),(3,20,1,1,0),(3,21,1,0,0),(3,22,1,0,0),(4,20,1,1,0),(4,21,1,0,0),(4,22,1,0,0),(9,29,1,0,0),(12,22,0,0,1),(20,23,0,1,1),(22,25,0,1,1),(25,24,0,1,1),(30,23,1,0,0),(30,24,1,0,0),(30,25,1,0,0),(30,29,0,1,1);
/*!40000 ALTER TABLE `vivienda_has_persona` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-18 11:37:45
