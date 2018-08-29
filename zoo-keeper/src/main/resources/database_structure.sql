CREATE TABLE `zoo_keeper`.`animals_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
PRIMARY KEY (`id`));

CREATE TABLE `zoo_keeper`.`animals` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `age` INT NULL,
  `type_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `animal_type_id`
  FOREIGN KEY (`type_id`)
  REFERENCES `zoo_keeper`.`animals_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
