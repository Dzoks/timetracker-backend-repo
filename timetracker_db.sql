-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.26-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for timetracker_db
CREATE DATABASE IF NOT EXISTS `timetracker_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `timetracker_db`;

-- Dumping structure for table timetracker_db.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `start_date` date NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `estimated_end_date` date DEFAULT NULL,
  `estimated_work_hours` int(11) DEFAULT NULL,
  `estimated_budget` decimal(10,2) DEFAULT NULL,
  `finished` tinyint(4) NOT NULL DEFAULT '0',
  `project_manager_id` int(11) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_project_user1_idx` (`project_manager_id`),
  CONSTRAINT `fk_project_user1` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.project: ~3 rows (approximately)
DELETE FROM `project`;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `name`, `start_date`, `description`, `estimated_end_date`, `estimated_work_hours`, `estimated_budget`, `finished`, `project_manager_id`, `active`) VALUES
	(9, 'Asset Management', '2019-04-01', NULL, NULL, NULL, NULL, 0, 5, 1),
	(10, 'Tracker Project', '2019-05-28', 'Opis projekta', '2019-06-11', 60, 10000.00, 0, 5, 1),
	(11, 'Izgradnja autobuskog stajališta', '2019-06-02', NULL, '2019-06-13', 80, 150000.00, 0, 1, 1),
	(12, 'Novi projekat', '2019-05-26', NULL, NULL, NULL, NULL, 1, 5, 1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.setting
CREATE TABLE IF NOT EXISTS `setting` (
  `id` int(11) NOT NULL,
  `key` varchar(45) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.setting: ~0 rows (approximately)
DELETE FROM `setting`;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.timesheet
CREATE TABLE IF NOT EXISTS `timesheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `hours` int(11) NOT NULL,
  `turnover` decimal(10,2) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_has_project_id` int(11) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_timesheet_user_has_project1_idx` (`user_has_project_id`),
  CONSTRAINT `fk_timesheet_user_has_project1` FOREIGN KEY (`user_has_project_id`) REFERENCES `user_has_project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.timesheet: ~3 rows (approximately)
DELETE FROM `timesheet`;
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
INSERT INTO `timesheet` (`id`, `date`, `description`, `hours`, `turnover`, `created`, `user_has_project_id`, `active`) VALUES
	(41, '2019-06-03', NULL, 2, 40.00, '2019-06-03 22:03:13', 5, 1),
	(42, '2019-06-03', 'Opis', 3, 60.00, '2019-06-03 22:04:09', 6, 1),
	(43, '2019-06-02', NULL, 3, 60.00, '2019-06-03 22:04:53', 6, 1),
	(44, '2019-06-04', NULL, 4, 16.00, '2019-06-04 00:23:58', 8, 1);
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(128) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `user_group_id` int(11) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_user_user_group_idx` (`user_group_id`),
  CONSTRAINT `fk_user_user_group` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.user: ~4 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `password`, `first_name`, `last_name`, `user_group_id`, `active`) VALUES
	(1, 'rukovodilac2@mailinator.com', '6f71b029671859cdcefe4237492f0a405ff559b7f2ecd1a5fb22fa839d73751b5a93bf1a2fb5b58163f55c37ccdd283b43abeaa0d3c326d01c84e8de3e02abb7', 'Aleksandar', 'Lukić', 1, 1),
	(2, 'korisnik1@mailinator.com', '0664ae3f3a52ec0a12d9c77436c419647f0e32d24d09829fa6b7cd2fcaf3bbb5897a15a4f0dbbd5ed44ab0e5001aff97fb7de692da2842729701e4791855d4be', 'Marko', 'Marković', 2, 1),
	(4, 'korisnik2@mailinator.com', '5d5023553fe89252bcd1a18558fbaa968748731a9561618f3d474e887b58908c12ef6070c78a7a30d40a131db80b4762eac8737ff34e32de5bbb4a4998e4535f', 'Jovan', 'Stefanović', 2, 1),
	(5, 'rukovodilac1@mailinator.com', '8a6ca5c04d89b41f29eb79325e05cd9ab9ad08f970aaa04c857636d31467c26441690709a7d41cfdf9e9b6712361473b0c1dd5c5ef108d8ba17a2028aef4844f', 'Slobodan', 'Mitošević', 1, 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.user_group
CREATE TABLE IF NOT EXISTS `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.user_group: ~2 rows (approximately)
DELETE FROM `user_group`;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` (`id`, `key`, `name`) VALUES
	(1, 'projectManager', 'Projekt menadžer'),
	(2, 'user', 'Korisnik');
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.user_has_project
CREATE TABLE IF NOT EXISTS `user_has_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `hour_rate` decimal(10,2) NOT NULL,
  `blocked` tinyint(4) NOT NULL DEFAULT '0',
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_user_has_project_project1_idx` (`project_id`),
  KEY `fk_user_has_project_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_project_project1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_project_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.user_has_project: ~3 rows (approximately)
DELETE FROM `user_has_project`;
/*!40000 ALTER TABLE `user_has_project` DISABLE KEYS */;
INSERT INTO `user_has_project` (`id`, `user_id`, `project_id`, `hour_rate`, `blocked`, `active`) VALUES
	(5, 5, 9, 20.00, 0, 1),
	(6, 5, 10, 20.00, 0, 1),
	(7, 1, 11, 25.00, 0, 1),
	(8, 1, 9, 4.00, 1, 1),
	(9, 2, 9, 1.00, 0, 1),
	(10, 4, 9, 8.00, 0, 1),
	(11, 2, 10, 12.00, 0, 1),
	(12, 5, 12, 10.00, 0, 1),
	(13, 2, 12, 2.00, 0, 1);
/*!40000 ALTER TABLE `user_has_project` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
