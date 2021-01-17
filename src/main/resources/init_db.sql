CREATE SCHEMA `internet_taxi` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet_taxi`.`manufacturers` (
  `manufacturer_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `manufacturer_name` VARCHAR(225) NOT NULL,
  `manufacturer_country` VARCHAR(225) NOT NULL,
  `manufacturer_deleted` VARCHAR(225) NOT NULL DEFAULT 'FALSE',
  PRIMARY KEY (`manufacturer_id`));

CREATE TABLE `internet_taxi`.`cars` (
  `car_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `manufacturer_id` BIGINT(11) NOT NULL,
  `car_model` VARCHAR(225) NOT NULL,
  `deleted` BIT(1) NOT NULL DEFAULT false,
  PRIMARY KEY (`car_id`));

ALTER TABLE `internet_taxi`.`cars`
ADD INDEX `cars_manufacturers_fk_idx` (`manufacturer_id` ASC) VISIBLE;
;
ALTER TABLE `internet_taxi`.`cars`
ADD CONSTRAINT `cars_manufacturers_fk`
  FOREIGN KEY (`manufacturer_id`)
  REFERENCES `internet_taxi`.`manufacturers` (`manufacturer_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `internet_taxi`.`drivers` (
  `driver_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `driver_name` VARCHAR(225) NOT NULL,
  `driver_license_number` VARCHAR(225) NOT NULL,
  `deleted` BIT(1) NOT NULL DEFAULT false,
PRIMARY KEY (`driver_id`));

CREATE TABLE `internet_taxi`.`cars_drivers` (
  `driver_id` BIGINT(11) NOT NULL,
  `car_id` BIGINT(11) NOT NULL,
  INDEX `driver_fk_idx` (`driver_id` ASC) VISIBLE,
  INDEX `cars_fk_idx` (`car_id` ASC) VISIBLE,
  CONSTRAINT `drivers_fk`
    FOREIGN KEY (`driver_id`)
    REFERENCES `internet_taxi`.`drivers` (`driver_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cars_fk`
    FOREIGN KEY (`car_id`)
    REFERENCES `internet_taxi`.`cars` (`car_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
