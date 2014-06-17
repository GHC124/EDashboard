-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 17, 2014 at 11:23 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `edashboard`
--

-- --------------------------------------------------------

--
-- Table structure for table `competency`
--

CREATE TABLE IF NOT EXISTS `competency` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `evaluator` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(3) NOT NULL,
  `type` int(1) NOT NULL,
  `competency_group_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `competency_group_id` (`competency_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `competency_group`
--

CREATE TABLE IF NOT EXISTS `competency_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `type` int(1) DEFAULT NULL,
  `user_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `file`
--

CREATE TABLE IF NOT EXISTS `file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `size` bigint(20) NOT NULL,
  `dateup` datetime NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `download_url` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `folder_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `folder_id` (`folder_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=17 ;

--
-- Dumping data for table `file`
--

INSERT INTO `file` (`id`, `name`, `size`, `dateup`, `description`, `download_url`, `content_type`, `folder_id`) VALUES
(1, 'text', 500, '2014-06-01 00:00:00', 'text description', NULL, NULL, 2),
(2, 'ebook', 700, '2014-06-01 00:00:00', 'ebook description', NULL, NULL, 1),
(3, 'Avatar', 11147, '2014-06-01 00:00:00', '', '2014\\5\\file_1401635143402.jpg', NULL, 2),
(4, 'Avatar1', 5953, '2014-06-01 00:00:00', '', '2014\\5\\file_1401635565295.jpg', NULL, 2),
(5, 'Avatar2', 11147, '2014-06-02 00:00:00', '', '2014\\5\\file_1401715199598.jpg', NULL, 1),
(6, 'avatar', 359570, '2014-06-12 00:00:00', 'HBQ', '2014\\5\\file_1402556910666.PNG', 'image/png', 4),
(7, 'avatar2', 11147, '2014-06-13 00:00:00', '', '2014\\5\\file_1402637758271.jpg', 'image/jpeg', 4),
(8, 'avatar3', 359570, '2014-06-13 00:00:00', '', '2014\\5\\file_1402638153831.PNG', 'image/png', 4),
(9, 'avatar4', 359570, '2014-06-13 00:00:00', '', '2014\\5\\file_1402639011210.PNG', 'image/png', 4),
(10, 'avatar5', 359570, '2014-06-13 00:00:00', '', '2014\\5\\file_1402647350402.PNG', 'image/png', 4),
(11, 'avatar6', 359570, '2014-06-13 00:00:00', '', '2014\\5\\file_1402647356687.PNG', 'image/png', 4),
(12, 'avatar7', 359570, '2014-06-13 00:00:00', '', '2014\\5\\file_1402647361484.PNG', 'image/png', 4),
(13, 'avatar7', 359570, '2014-06-13 00:00:00', '', '2014\\5\\file_1402647368107.PNG', 'image/png', 4),
(14, 'avatar6', 11147, '2014-06-16 00:00:00', '', '2014\\6\\admin_file_1402906654025.jpg', 'image/jpeg', 4),
(15, 'picture', 11147, '2014-06-16 00:00:00', '', '2014\\6\\admin_file_1402913563955.jpg', 'image/jpeg', 5),
(16, 'picture1', 11147, '2014-06-16 00:00:00', '', '2014\\6\\admin_file_1402913569347.jpg', 'image/jpeg', 5);

-- --------------------------------------------------------

--
-- Table structure for table `folder`
--

CREATE TABLE IF NOT EXISTS `folder` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file_folder_user_idx` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Dumping data for table `folder`
--

INSERT INTO `folder` (`id`, `name`, `description`, `user_id`) VALUES
(1, 'Public', 'public folder', 1),
(2, 'Private', 'private folder', 1),
(3, 'Public', 'public fodler', 2),
(4, 'Avatar', '', 1),
(5, 'Picture', '', 1),
(6, 'Ebook', '', 1),
(7, 'IT', '', 1),
(8, 'Spring', '', 1),
(9, 'Java', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
  `id` int(10) unsigned NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `icon_id` int(10) unsigned DEFAULT NULL,
  `introduction` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `icon_id` (`icon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `email`, `first_name`, `last_name`, `icon_id`, `introduction`) VALUES
(1, 'admin@gmail.com', 'van', 'chung', 14, '<p>\r\n	my name is Chung</p>\r\n'),
(2, 'chung.pv0795@gmail.com', '', '', 10, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) unsigned NOT NULL,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authority` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `authority`, `is_active`) VALUES
(1, 'admin', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'ROLE_ADMIN', 1),
(2, 'test', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'ROLE_USER', 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `competency`
--
ALTER TABLE `competency`
  ADD CONSTRAINT `competency_ibfk_1` FOREIGN KEY (`competency_group_id`) REFERENCES `competency_group` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `competency_group`
--
ALTER TABLE `competency_group`
  ADD CONSTRAINT `competency_group_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `file`
--
ALTER TABLE `file`
  ADD CONSTRAINT `file_ibfk_1` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `folder`
--
ALTER TABLE `folder`
  ADD CONSTRAINT `file_folder_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`icon_id`) REFERENCES `file` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `profile_user_fk` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
