-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.26-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for time_tracker_db
CREATE DATABASE IF NOT EXISTS `time_tracker_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `time_tracker_db`;

-- Dumping structure for table time_tracker_db.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `start_date` date NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `estimated_end_date` date DEFAULT NULL,
  `estimateed_work_hours` int(11) DEFAULT NULL,
  `estimated_budget` decimal(10,0) DEFAULT NULL,
  `finished` tinyint(4) NOT NULL DEFAULT '0',
  `project_manager_id` int(11) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_project_user1_idx` (`project_manager_id`),
  CONSTRAINT `fk_project_user1` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table time_tracker_db.project: ~0 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Dumping structure for table time_tracker_db.setting
CREATE TABLE IF NOT EXISTS `setting` (
  `id` int(11) NOT NULL,
  `key` varchar(45) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table time_tracker_db.setting: ~0 rows (approximately)
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;

-- Dumping structure for table time_tracker_db.timesheet
CREATE TABLE IF NOT EXISTS `timesheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `hours` int(11) NOT NULL,
  `turnover` decimal(10,0) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_has_project_id` int(11) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_timesheet_user_has_project1_idx` (`user_has_project_id`),
  CONSTRAINT `fk_timesheet_user_has_project1` FOREIGN KEY (`user_has_project_id`) REFERENCES `user_has_project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table time_tracker_db.timesheet: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;

-- Dumping structure for table time_tracker_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `user_group_id` int(11) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_user_user_group_idx` (`user_group_id`),
  CONSTRAINT `fk_user_user_group` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table time_tracker_db.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`id`, `email`, `password`, `first_name`, `last_name`, `user_group_id`, `active`) VALUES
	(1, 'dzoks', 'dzoks', 'djordje', 'turjacanin', 1, 1),
	(2, 'marko', 'marko', 'marko', 'markovic', 2, 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table time_tracker_db.user_group
CREATE TABLE IF NOT EXISTS `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table time_tracker_db.user_group: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT IGNORE INTO `user_group` (`id`, `name`) VALUES
	(1, 'projectManager'),
	(2, 'user');
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;

-- Dumping structure for table time_tracker_db.user_has_project
CREATE TABLE IF NOT EXISTS `user_has_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `hour_rate` decimal(10,0) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_user_has_project_project1_idx` (`project_id`),
  KEY `fk_user_has_project_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_project_project1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_project_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table time_tracker_db.user_has_project: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_has_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_project` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
