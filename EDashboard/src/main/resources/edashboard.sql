-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2014 at 04:48 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `edashboard`
--

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- Dumping data for table `file`
--

INSERT INTO `file` (`id`, `name`, `size`, `dateup`, `description`, `download_url`) VALUES
(1, 'text', 500, '2014-06-01 00:00:00', 'text description', NULL),
(2, 'ebook', 700, '2014-06-01 00:00:00', 'ebook description', NULL),
(3, 'Avatar', 11147, '2014-06-01 00:00:00', '', '2014\\5\\file_1401635143402.jpg'),
(4, 'Avatar1', 5953, '2014-06-01 00:00:00', '', '2014\\5\\file_1401635565295.jpg'),
(5, 'Avatar2', 11147, '2014-06-02 00:00:00', '', '2014\\5\\file_1401715199598.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `file_folder`
--

CREATE TABLE IF NOT EXISTS `file_folder` (
  `file_id` int(10) unsigned NOT NULL,
  `folder_id` int(10) unsigned NOT NULL,
  KEY `folder_fk` (`folder_id`),
  KEY `file_fk` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `file_folder`
--

INSERT INTO `file_folder` (`file_id`, `folder_id`) VALUES
(1, 1),
(2, 2),
(3, 1),
(3, 4);

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
  `icon_id` int(11) DEFAULT NULL,
  `introduction` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `email`, `first_name`, `last_name`, `icon_id`, `introduction`) VALUES
(1, 'admin@gmail.com', 'van', 'chung', NULL, '<p>\r\n	my name is Chung</p>\r\n'),
(2, 'chung.pv0795@gmail.com', '', '', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authority` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

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
-- Constraints for table `file_folder`
--
ALTER TABLE `file_folder`
  ADD CONSTRAINT `file_folder_ibfk_1` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `file_folder_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `folder`
--
ALTER TABLE `folder`
  ADD CONSTRAINT `file_folder_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_user_fk` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
