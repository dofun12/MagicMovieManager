-- MySQL Script generated by MySQL Workbench
-- Ter 24 Nov 2015 16:24:33 BRST
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema magicmoviemanager
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `magicmoviemanager` ;

-- -----------------------------------------------------
-- Schema magicmoviemanager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `magicmoviemanager` DEFAULT CHARACTER SET utf8 ;
USE `magicmoviemanager` ;

-- -----------------------------------------------------
-- Table `magicmoviemanager`.`serie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `magicmoviemanager`.`serie` ;

CREATE TABLE IF NOT EXISTS `magicmoviemanager`.`serie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `visible` TINYINT(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `magicmoviemanager`.`tipomedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `magicmoviemanager`.`tipomedia` ;

CREATE TABLE IF NOT EXISTS `magicmoviemanager`.`tipomedia` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `magicmoviemanager`.`mediafile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `magicmoviemanager`.`mediafile` ;

CREATE TABLE IF NOT EXISTS `magicmoviemanager`.`mediafile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `path` TEXT NULL,
  `size` MEDIUMTEXT NULL,
  `duration` MEDIUMTEXT NULL,
  `dateAdded` DATETIME NULL,
  `id_tipomedia` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_mediafile_tipomedia_idx` (`id_tipomedia` ASC),
  CONSTRAINT `fk_mediafile_tipomedia`
    FOREIGN KEY (`id_tipomedia`)
    REFERENCES `magicmoviemanager`.`tipomedia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `magicmoviemanager`.`episodio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `magicmoviemanager`.`episodio` ;

CREATE TABLE IF NOT EXISTS `magicmoviemanager`.`episodio` (
  `id_serie` INT NOT NULL,
  `id_mediafile` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `temporada` INT NULL,
  PRIMARY KEY (`id_serie`, `id_mediafile`),
  INDEX `fk_episodio_mediafile_idx` (`id_mediafile` ASC),
  CONSTRAINT `fk_episodio_serie`
    FOREIGN KEY (`id_serie`)
    REFERENCES `magicmoviemanager`.`serie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_episodio_mediafile`
    FOREIGN KEY (`id_mediafile`)
    REFERENCES `magicmoviemanager`.`mediafile` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `magicmoviemanager`.`historico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `magicmoviemanager`.`historico` ;

CREATE TABLE IF NOT EXISTS `magicmoviemanager`.`historico` (
  `id_mediafile` INT NOT NULL,
  `lastTimeWatched` DATETIME NULL,
  `lastPosition` MEDIUMTEXT NULL,
  PRIMARY KEY (`id_mediafile`),
  CONSTRAINT `fk_historico_mediafile`
    FOREIGN KEY (`id_mediafile`)
    REFERENCES `magicmoviemanager`.`mediafile` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `magicmoviemanager`.`config`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `magicmoviemanager`.`config` ;

CREATE TABLE IF NOT EXISTS `magicmoviemanager`.`config` (
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NULL,
  PRIMARY KEY (`key`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
