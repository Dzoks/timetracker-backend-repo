-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.26-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for timetracker_db
DROP DATABASE IF EXISTS `timetracker_db`;
CREATE DATABASE IF NOT EXISTS `timetracker_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `timetracker_db`;

-- Dumping structure for table timetracker_db.project
DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `start_date` date NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `finished` tinyint(4) NOT NULL DEFAULT '0',
  `project_manager_id` int(11) NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_project_user1_idx` (`project_manager_id`),
  CONSTRAINT `fk_project_user1` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.project: ~4 rows (approximately)
DELETE FROM `project`;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `name`, `start_date`, `description`, `finished`, `project_manager_id`, `active`) VALUES
	(16, 'Timetracker', '2019-04-01', 'Softver za praćenje i evidenciju vremena provedenog na projektu', 1, 5, 1),
	(17, 'Softver za prepoznavanje lica', '2019-09-01', 'Izrada softvera za prepoznavanje lica na osnovu video snimaka', 0, 8, 1),
	(18, 'React edukacija', '2019-08-04', 'Predavanja vezana za React biblioteku', 0, 8, 1),
	(19, 'Amazon Integracija', '2019-09-06', 'Integracija postojećih aplikacija sa Amazon Web servisima', 0, 5, 1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.timesheet
DROP TABLE IF EXISTS `timesheet`;
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
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.timesheet: ~86 rows (approximately)
DELETE FROM `timesheet`;
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
INSERT INTO `timesheet` (`id`, `date`, `description`, `hours`, `turnover`, `created`, `user_has_project_id`, `active`) VALUES
	(72, '2019-04-03', NULL, 6, 150.00, '2019-10-01 20:59:46', 42, 1),
	(73, '2019-04-04', NULL, 8, 200.00, '2019-10-01 20:59:56', 42, 1),
	(74, '2019-04-05', 'Sastanak', 5, 125.00, '2019-10-01 21:00:54', 42, 1),
	(75, '2019-04-12', NULL, 5, 125.00, '2019-10-01 21:01:38', 42, 1),
	(76, '2019-04-19', 'Sastanak pred tehnički prijem', 5, 125.00, '2019-10-01 21:02:04', 42, 1),
	(77, '2019-04-22', 'Tehnički prijem', 8, 200.00, '2019-10-01 21:02:17', 42, 1),
	(78, '2019-04-21', NULL, 2, 50.00, '2019-10-01 21:02:24', 42, 1),
	(79, '2019-04-07', 'Izrada serverskog dijela.', 4, 32.00, '2019-10-01 21:03:54', 44, 1),
	(80, '2019-04-08', NULL, 4, 32.00, '2019-10-01 21:03:59', 44, 1),
	(81, '2019-04-09', NULL, 2, 16.00, '2019-10-01 21:04:04', 44, 1),
	(82, '2019-04-10', NULL, 3, 24.00, '2019-10-01 21:04:12', 44, 1),
	(83, '2019-04-12', 'Sastanak', 5, 40.00, '2019-10-01 21:04:21', 44, 1),
	(84, '2019-04-19', 'Sastanak', 5, 40.00, '2019-10-01 21:04:32', 44, 1),
	(85, '2019-04-18', 'Bugfixing', 8, 64.00, '2019-10-01 21:04:50', 44, 1),
	(86, '2019-04-14', 'Integracija sa frontend dijelom', 6, 48.00, '2019-10-01 21:05:05', 44, 1),
	(87, '2019-04-05', 'Inicijalni sastanak', 5, 60.00, '2019-10-01 21:09:59', 43, 1),
	(88, '2019-04-22', 'Tehnički prijem', 8, 96.00, '2019-10-01 21:11:08', 43, 1),
	(89, '2019-04-21', 'Testiranje', 8, 96.00, '2019-10-01 21:11:15', 43, 1),
	(90, '2019-04-19', 'Sastanak pred tehnički prijem', 5, 60.00, '2019-10-01 21:11:27', 43, 1),
	(91, '2019-04-12', NULL, 5, 60.00, '2019-10-01 21:11:32', 43, 1),
	(92, '2019-04-07', 'Frontend dio aplikacije', 6, 72.00, '2019-10-01 21:11:43', 43, 1),
	(93, '2019-04-08', NULL, 4, 48.00, '2019-10-01 21:11:48', 43, 1),
	(94, '2019-04-09', NULL, 3, 36.00, '2019-10-01 21:11:53', 43, 1),
	(95, '2019-04-16', NULL, 2, 24.00, '2019-10-01 21:11:57', 43, 1),
	(96, '2019-04-17', NULL, 6, 72.00, '2019-10-01 21:12:01', 43, 1),
	(97, '2019-09-03', NULL, 6, 180.00, '2019-10-01 21:16:21', 45, 1),
	(98, '2019-09-04', NULL, 5, 150.00, '2019-10-01 21:16:27', 45, 1),
	(99, '2019-09-06', 'Redovni sastanak', 2, 60.00, '2019-10-01 21:16:46', 45, 1),
	(100, '2019-09-13', 'Sastanak', 4, 120.00, '2019-10-01 21:16:58', 45, 1),
	(101, '2019-09-20', NULL, 5, 150.00, '2019-10-01 21:17:06', 45, 1),
	(102, '2019-09-09', NULL, 4, 120.00, '2019-10-01 21:17:14', 45, 1),
	(103, '2019-09-11', NULL, 2, 60.00, '2019-10-01 21:17:19', 45, 1),
	(104, '2019-10-01', NULL, 3, 90.00, '2019-10-01 21:17:29', 45, 1),
	(105, '2019-10-01', NULL, 4, 48.00, '2019-10-01 21:21:23', 48, 1),
	(106, '2019-09-03', NULL, 2, 24.00, '2019-10-01 21:21:59', 48, 1),
	(107, '2019-09-10', NULL, 2, 24.00, '2019-10-01 21:22:04', 48, 1),
	(108, '2019-09-17', '2', 2, 24.00, '2019-10-01 21:22:12', 48, 0),
	(109, '2019-09-17', NULL, 2, 24.00, '2019-10-01 21:22:23', 48, 1),
	(110, '2019-09-24', NULL, 4, 48.00, '2019-10-01 21:22:36', 48, 1),
	(111, '2019-09-13', 'Case Study', 8, 200.00, '2019-10-01 21:29:13', 53, 1),
	(112, '2019-09-20', 'Sastanak', 3, 75.00, '2019-10-01 21:29:27', 53, 1),
	(113, '2019-09-27', 'Sastanak', 3, 75.00, '2019-10-01 21:29:43', 53, 1),
	(114, '2019-09-24', NULL, 4, 100.00, '2019-10-01 21:29:48', 53, 1),
	(115, '2019-09-06', NULL, 4, 100.00, '2019-10-01 21:30:08', 53, 1),
	(116, '2019-09-09', NULL, 6, 150.00, '2019-10-01 21:30:13', 53, 1),
	(117, '2019-09-18', NULL, 2, 50.00, '2019-10-01 21:30:19', 53, 1),
	(118, '2019-09-20', 'Sastanak', 3, 36.00, '2019-10-01 21:31:25', 55, 1),
	(119, '2019-09-27', 'Sastanak', 3, 36.00, '2019-10-01 21:31:38', 55, 1),
	(120, '2019-09-06', NULL, 3, 36.00, '2019-10-01 21:31:47', 55, 1),
	(121, '2019-09-24', NULL, 4, 32.00, '2019-10-01 21:31:57', 51, 1),
	(122, '2019-10-01', NULL, 4, 32.00, '2019-10-01 21:32:03', 51, 1),
	(123, '2019-09-17', NULL, 2, 16.00, '2019-10-01 21:32:14', 51, 1),
	(124, '2019-09-03', NULL, 2, 16.00, '2019-10-01 21:32:23', 51, 1),
	(125, '2019-09-10', NULL, 2, 16.00, '2019-10-01 21:32:31', 51, 1),
	(126, '2019-09-10', NULL, 3, 45.00, '2019-10-01 21:32:40', 46, 1),
	(127, '2019-09-10', NULL, 3, 36.00, '2019-10-01 21:32:48', 55, 1),
	(128, '2019-09-18', NULL, 4, 60.00, '2019-10-01 21:33:00', 46, 1),
	(129, '2019-09-18', NULL, 4, 48.00, '2019-10-01 21:33:07', 55, 1),
	(130, '2019-09-12', NULL, 3, 36.00, '2019-10-01 21:33:13', 55, 1),
	(131, '2019-09-23', NULL, 8, 120.00, '2019-10-01 21:33:21', 46, 1),
	(132, '2019-09-25', NULL, 6, 72.00, '2019-10-01 21:33:33', 55, 1),
	(133, '2019-10-01', NULL, 4, 32.00, '2019-10-01 21:34:10', 50, 1),
	(134, '2019-09-24', NULL, 4, 32.00, '2019-10-01 21:34:18', 50, 1),
	(135, '2019-09-17', NULL, 2, 16.00, '2019-10-01 21:34:22', 50, 1),
	(136, '2019-09-10', NULL, 2, 16.00, '2019-10-01 21:34:26', 50, 1),
	(137, '2019-09-03', NULL, 2, 16.00, '2019-10-01 21:34:34', 50, 1),
	(138, '2019-10-01', NULL, 2, 16.00, '2019-10-01 21:35:22', 52, 0),
	(139, '2019-10-18', NULL, 3, 48.00, '2019-10-01 21:35:27', 54, 0),
	(140, '2019-10-25', NULL, 3, 48.00, '2019-10-01 21:35:31', 54, 0),
	(141, '2019-10-11', NULL, 8, 128.00, '2019-10-01 21:35:37', 54, 0),
	(142, '2019-10-08', NULL, 2, 16.00, '2019-10-01 21:35:41', 52, 0),
	(143, '2019-10-15', NULL, 2, 16.00, '2019-10-01 21:35:45', 52, 0),
	(144, '2019-10-22', NULL, 4, 32.00, '2019-10-01 21:35:49', 52, 0),
	(145, '2019-10-01', NULL, 4, 32.00, '2019-10-01 21:36:17', 52, 1),
	(146, '2019-09-03', NULL, 2, 16.00, '2019-10-01 21:36:25', 52, 1),
	(147, '2019-09-10', NULL, 2, 16.00, '2019-10-01 21:36:29', 52, 1),
	(148, '2019-09-17', NULL, 2, 16.00, '2019-10-01 21:36:33', 52, 1),
	(149, '2019-09-24', NULL, 4, 32.00, '2019-10-01 21:36:37', 52, 1),
	(150, '2019-09-13', 'Case Study', 8, 128.00, '2019-10-01 21:36:46', 54, 1),
	(151, '2019-09-20', NULL, 3, 48.00, '2019-10-01 21:36:52', 54, 1),
	(152, '2019-09-27', NULL, 3, 48.00, '2019-10-01 21:36:56', 54, 1),
	(153, '2019-09-20', NULL, 2, 40.00, '2019-10-01 21:37:54', 47, 1),
	(154, '2019-09-06', NULL, 2, 40.00, '2019-10-01 21:38:06', 47, 1),
	(155, '2019-09-03', NULL, 4, 80.00, '2019-10-01 21:38:18', 47, 1),
	(156, '2019-09-08', NULL, 4, 80.00, '2019-10-01 21:38:26', 47, 1),
	(157, '2019-09-18', NULL, 4, 64.00, '2019-10-01 21:38:30', 54, 1);
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.user
DROP TABLE IF EXISTS `user`;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.user: ~5 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `password`, `first_name`, `last_name`, `user_group_id`, `active`) VALUES
	(2, 'korisnik1@mailinator.com', '0664ae3f3a52ec0a12d9c77436c419647f0e32d24d09829fa6b7cd2fcaf3bbb5897a15a4f0dbbd5ed44ab0e5001aff97fb7de692da2842729701e4791855d4be', 'Marko', 'Marković', 2, 1),
	(4, 'korisnik2@mailinator.com', '5d5023553fe89252bcd1a18558fbaa968748731a9561618f3d474e887b58908c12ef6070c78a7a30d40a131db80b4762eac8737ff34e32de5bbb4a4998e4535f', 'Jovan', 'Stefanović', 2, 1),
	(5, 'rukovodilac1@mailinator.com', '8a6ca5c04d89b41f29eb79325e05cd9ab9ad08f970aaa04c857636d31467c26441690709a7d41cfdf9e9b6712361473b0c1dd5c5ef108d8ba17a2028aef4844f', 'Slobodan', 'Mitošević', 1, 1),
	(7, 'korisnik3@mailinator.com', '5ed7f08ae9e68cf7f04c00b1dda8b34e394c75eaa7a203a742e7ba8690b95fe33f4e3cb615b8ea80c94f1a4c26a21febdbc461f74f40b45fb4544d9919081fdf', 'Jovan', 'Lukić', 2, 1),
	(8, 'rukovodilac2@mailinator.com', '6f71b029671859cdcefe4237492f0a405ff559b7f2ecd1a5fb22fa839d73751b5a93bf1a2fb5b58163f55c37ccdd283b43abeaa0d3c326d01c84e8de3e02abb7', 'Djordje', 'Turjacanin', 1, 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.user_group
DROP TABLE IF EXISTS `user_group`;
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
	(1, 'projectManager', 'Rukovodilac projekta'),
	(2, 'user', 'Korisnik');
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;

-- Dumping structure for table timetracker_db.user_has_project
DROP TABLE IF EXISTS `user_has_project`;
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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table timetracker_db.user_has_project: ~13 rows (approximately)
DELETE FROM `user_has_project`;
/*!40000 ALTER TABLE `user_has_project` DISABLE KEYS */;
INSERT INTO `user_has_project` (`id`, `user_id`, `project_id`, `hour_rate`, `blocked`, `active`) VALUES
	(42, 5, 16, 25.00, 0, 1),
	(43, 8, 16, 12.00, 0, 1),
	(44, 2, 16, 8.00, 0, 1),
	(45, 8, 17, 30.00, 0, 1),
	(46, 2, 17, 15.00, 0, 1),
	(47, 7, 17, 20.00, 0, 1),
	(48, 8, 18, 12.00, 0, 1),
	(50, 4, 18, 8.00, 0, 1),
	(51, 2, 18, 8.00, 0, 1),
	(52, 7, 18, 8.00, 0, 1),
	(53, 5, 19, 25.00, 0, 1),
	(54, 7, 19, 16.00, 0, 1),
	(55, 2, 19, 12.00, 0, 1);
/*!40000 ALTER TABLE `user_has_project` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
