CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authority` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `profile` (
  `id` int(11) unsigned NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `icon_id` int(10) unsigned DEFAULT NULL,
  `introduction` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `icon_id` (`icon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`icon_id`) REFERENCES `file` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `profile_user_fk` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

    
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

CREATE TABLE IF NOT EXISTS `file` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `size` bigint(20) NOT NULL,
  `dateup` datetime NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `download_url` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content_type` VARCHAR( 255 ) NULL DEFAULT NULL,
  `folder_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `folder_id` (`folder_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;
ALTER TABLE `file`
  ADD CONSTRAINT `file_ibfk_1` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE CASCADE;

CREATE TABLE `edashboard`.`competency` (
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `evaluator` VARCHAR(255) NULL,
  `level` INT(3) NOT NULL,
  `type` INT(1) NOT NULL,
  `competency_group_id` INT unsigned NOT NULL,
PRIMARY KEY (`id`));
ALTER TABLE  `competency` ADD FOREIGN KEY (  `competency_group_id` ) REFERENCES  `edashboard`.`competency_group` (
`id`
) ON DELETE CASCADE ON UPDATE RESTRICT ;

CREATE TABLE `edashboard`.`competency_group` (
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `type` INT(1) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`));
ALTER TABLE  `competency_group` ADD FOREIGN KEY (  `user_id` ) REFERENCES  `edashboard`.`user` (
`id`
) ON DELETE CASCADE ON UPDATE RESTRICT ;
 