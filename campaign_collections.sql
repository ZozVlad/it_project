DROP DATABASE `campaign_collections`;

CREATE DATABASE IF NOT EXISTS `campaign_collections` DEFAULT CHARACTER SET utf8 ;
USE `campaign_collections` ;

CREATE TABLE IF NOT EXISTS `campaign_collections`.`Account` (
`id_account` INT AUTO_INCREMENT NOT NULL,
`login` VARCHAR(40) NOT NULL CHECK(login !=''),
`password` VARCHAR(50) NOT NULL CHECK(password !=''),
PRIMARY KEY (`id_account`))
ENGINE = InnoDB
DEFAULT CHARSET utf8;

CREATE TABLE IF NOT EXISTS `campaign_collections`.`Campaign` (
`id_campaign` INT NOT NULL,
`account_id` INT NOT NULL,
`name` VARCHAR (50) NULL,
`status` VARCHAR (20) NULL,
`user_email` VARCHAR (50) NOT NULL,
`user_password` VARCHAR(50) NOT NULL,
`day_limit` INT NULL DEFAULT 0,
`toSend` INT NULL DEFAULT 0,
`lastRecipient` INT NULL DEFAULT 0,
`nextDate` VARCHAR (20),
PRIMARY KEY (`id_campaign`),
CONSTRAINT campaign_account_fk
FOREIGN KEY (`account_id`)  REFERENCES `campaign_collections`.`Account` (`id_account`) ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARSET utf8;

CREATE TABLE IF NOT EXISTS `campaign_collections`.`Prospects` (
`id_prospect` INT UNSIGNED NOT NULL CHECK (id_prospect > 0),
`campaign_id` INT NOT NULL,
`email` VARCHAR(50) NOT NULL CHECK(email !=''),
 `first_name` VARCHAR(30) NULL,
 `last_name` VARCHAR(30) NULL,
 `full_name` VARCHAR(75) NULL,
 `company` VARCHAR(45) NULL,
 `phone` VARCHAR(20) NULL,
 `address` VARCHAR(70) NULL,
 `city` VARCHAR(45) NULL,
 `snippet_1` VARCHAR(50) NULL,
 `snippet_2` VARCHAR(50) NULL,
 `snippet_3` VARCHAR(50) NULL,
 `snippet_4` VARCHAR(50) NULL,
 `snippet_5` VARCHAR(50) NULL,
 `lastLetterSent` INT NULL DEFAULT 0,
 `emailInfo` VARCHAR(50) NULL,
 PRIMARY KEY (id_prospect),
 CONSTRAINT prospects_campaign_fk
FOREIGN KEY (`campaign_id`)  REFERENCES `campaign_collections`.`Campaign` (`id_campaign`) ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARSET utf8;

CREATE TABLE IF NOT EXISTS `campaign_collections`.`Letter` (
`id_letter` INT UNSIGNED NOT NULL CHECK (id_letter > 0),
`campaign_id` INT NOT NULL,
`subject` VARCHAR (70) NULL,
`HtmlText` TEXT NULL,
PRIMARY KEY (`id_letter`),
 CONSTRAINT letter_campaign_fk
FOREIGN KEY (`campaign_id`)  REFERENCES `campaign_collections`.`Campaign` (`id_campaign`) ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARSET utf8;
