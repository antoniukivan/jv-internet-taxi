CREATE SCHEMA `internet_taxi` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet_taxi`.`manufacturers` (
  `manufacturer_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `manufacturer_name` VARCHAR(225) NOT NULL,
  `manufacturer_country` VARCHAR(225) NOT NULL,
  `manufacturer_deleted` VARCHAR(225) NOT NULL DEFAULT 'FALSE',
  PRIMARY KEY (`manufacturer_id`));
INSERT INTO `internet_taxi`.`manufacturers` (`manufacturer_name`, `manufacturer_country`) VALUES ('Tesla', 'USA');
INSERT INTO `internet_taxi`.`manufacturers` (`manufacturer_name`, `manufacturer_country`) VALUES ('Toyota', 'Japan');
INSERT INTO `internet_taxi`.`manufacturers` (`manufacturer_name`, `manufacturer_country`) VALUES ('Ford', 'USA');
