CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authority` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `edashboard`.`profile` (
  `id` INT UNSIGNED NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `icon_id` INT NULL,
  `introduction` TEXT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  CONSTRAINT `profile_user_fk`
    FOREIGN KEY (`id`)
    REFERENCES `edashboard`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
    
CREATE TABLE `edashboard`.`file_folder` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `file_folder_user_idx` (`user_id` ASC),
  CONSTRAINT `file_folder_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `edashboard`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `edashboard`.`file` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `size` BIGINT NOT NULL,
  `dateup` DATETIME NOT NULL,
  `description` TEXT NULL,
  `folder_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `file_folder_fk_idx` (`folder_id` ASC),
  CONSTRAINT `file_folder_fk`
    FOREIGN KEY (`folder_id`)
    REFERENCES `edashboard`.`file_folder` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);