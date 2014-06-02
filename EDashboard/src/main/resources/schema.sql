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
    
CREATE TABLE `edashboard`.`folder` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `folder_user_idx` (`user_id` ASC),
  CONSTRAINT `folder_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `edashboard`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `size` bigint(20) NOT NULL,
  `dateup` datetime NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `download_url` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
    
CREATE TABLE `file_folder` (
  `file_id` int(10) unsigned NOT NULL,
  `folder_id` int(10) unsigned NOT NULL,
  KEY `folder_fk` (`folder_id`),
  KEY `file_fk` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
ALTER TABLE `file_folder`
  ADD CONSTRAINT `file_folder_ibfk_1` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `file_folder_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE CASCADE;
 