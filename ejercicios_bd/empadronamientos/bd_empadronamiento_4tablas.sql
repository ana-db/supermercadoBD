-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema empadronamiento
-- -----------------------------------------------------
-- base de datos para gestionar empadronamiento de Bizkaia

-- -----------------------------------------------------
-- Schema empadronamiento
--
-- base de datos para gestionar empadronamiento de Bizkaia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `empadronamiento` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `empadronamiento` ;

-- -----------------------------------------------------
-- Table `empadronamiento`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empadronamiento`.`persona` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dni` VARCHAR(9) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC),
  UNIQUE INDEX `apellidos_UNIQUE` (`apellidos` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empadronamiento`.`municipio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empadronamiento`.`municipio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `id_persona` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC),
  INDEX `fk_municipio_persona1_idx` (`id_persona` ASC),
  CONSTRAINT `fk_municipio_persona1`
    FOREIGN KEY (`id_persona`)
    REFERENCES `empadronamiento`.`persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empadronamiento`.`vivienda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empadronamiento`.`vivienda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(45) NOT NULL,
  `calle` VARCHAR(100) NOT NULL,
  `numero` VARCHAR(45) NULL,
  `id_municipio` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC),
  INDEX `fk_vivienda_municipio_idx` (`id_municipio` ASC),
  CONSTRAINT `fk_vivienda_municipio`
    FOREIGN KEY (`id_municipio`)
    REFERENCES `empadronamiento`.`municipio` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empadronamiento`.`propiedad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empadronamiento`.`propiedad` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_persona` INT NOT NULL,
  `id_vivienda` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_propiedad_persona1_idx` (`id_persona` ASC),
  INDEX `fk_propiedad_vivienda1_idx` (`id_vivienda` ASC),
  CONSTRAINT `fk_propiedad_persona1`
    FOREIGN KEY (`id_persona`)
    REFERENCES `empadronamiento`.`persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_propiedad_vivienda1`
    FOREIGN KEY (`id_vivienda`)
    REFERENCES `empadronamiento`.`vivienda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
