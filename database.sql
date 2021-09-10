/*
SQLyog Community Edition- MySQL GUI v8.03 
MySQL - 5.6.12-log : Database - social_network_assisted_worker_recruitment
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`social_network_assisted_worker_recruitment` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `social_network_assisted_worker_recruitment`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `postid` int(11) DEFAULT NULL,
  `ulid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `comment` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `comment` */

insert  into `comment`(`comment_id`,`postid`,`ulid`,`date`,`comment`) values (1,14,3,'2021-06-19','good one'),(2,14,2,'2021-06-20','thank you'),(3,14,2,'2021-06-21','thanku'),(4,14,2,'2021-06-21','thanks'),(5,14,3,'2021-06-28','keep doing'),(6,14,2,'2021-06-28','thanku'),(7,14,2,'2021-06-28','thanku'),(8,14,3,'2021-07-13','oke'),(9,20,3,'2021-07-13','beautiful place');

/*Table structure for table `complain_view` */

DROP TABLE IF EXISTS `complain_view`;

CREATE TABLE `complain_view` (
  `cid` int(30) NOT NULL AUTO_INCREMENT,
  `ulid` int(30) DEFAULT NULL,
  `complaint` varchar(40) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `reply` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `complain_view` */

insert  into `complain_view`(`cid`,`ulid`,`complaint`,`date`,`reply`) values (1,2,'bhb','0002-03-21','gjjkk'),(2,2,'complaint send','2021-05-27','gjlbnn'),(3,3,'mohan is not working properly','2021-07-13','will consider');

/*Table structure for table `friend_request` */

DROP TABLE IF EXISTS `friend_request`;

CREATE TABLE `friend_request` (
  `reqid` int(30) NOT NULL AUTO_INCREMENT,
  `fromid` int(30) DEFAULT NULL,
  `toid` int(30) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`reqid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/*Data for the table `friend_request` */

insert  into `friend_request`(`reqid`,`fromid`,`toid`,`status`,`date`) values (11,2,3,'friend','2021-06-03'),(13,3,5,'pending','2021-06-04'),(14,3,5,'pending','2021-06-04'),(15,3,5,'pending','2021-06-04'),(16,3,6,'pending','2021-06-26'),(17,4,4,'pending','2021-06-28'),(19,4,3,'reject','2021-06-28'),(20,4,2,'friend','2021-06-28'),(21,6,4,'pending','2021-06-28'),(22,6,3,'reject','2021-06-28'),(23,6,2,'friend','2021-06-28'),(24,6,5,'pending','2021-06-28'),(25,2,13,'pending','2021-07-13');

/*Table structure for table `likes` */

DROP TABLE IF EXISTS `likes`;

CREATE TABLE `likes` (
  `likeid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `likeno` int(11) DEFAULT NULL,
  `postid` int(11) DEFAULT NULL,
  PRIMARY KEY (`likeid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `likes` */

insert  into `likes`(`likeid`,`userid`,`likeno`,`postid`) values (1,2,1,14),(2,3,1,14);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `loginid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`loginid`,`uname`,`password`,`type`) values (1,'admin','admin','admin'),(2,'mohan@gmail.com','1111','user'),(3,'azra629@gmail.com','1234','user'),(4,'anees@gmail.com','123','user'),(5,'mina@gmail.com','12345','user'),(6,'effa@gmail.com','1212','user'),(7,'rayan@gmail.com','123','user'),(8,'amree@gmail.com','1234','user'),(9,'dalia@gmail.com','123','user'),(13,'vsa1200@gmail.com','25111964','user');

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `postid` int(30) NOT NULL AUTO_INCREMENT,
  `ulid` int(30) DEFAULT NULL,
  `filepath` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`postid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

/*Data for the table `post` */

insert  into `post`(`postid`,`ulid`,`filepath`,`description`,`date`) values (14,2,'/static/post/2021_06_13_12_45_06.547049.jpg','farming','2021-06-13'),(20,3,'/static/post/2021_06_27_17_24_46.079159.jpg','adfg','2021-06-27');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rate_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(50) DEFAULT NULL,
  `from_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`rate_id`,`date`,`from_id`,`to_id`,`rate`) values (1,'2021-06-28',4,3,'3.5'),(2,'2021-06-28',2,3,'4.0'),(3,'2021-07-14',5,3,'4.0'),(4,'2021-07-14',3,2,'4.0');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(50) DEFAULT NULL,
  `from_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`rid`,`date`,`from_id`,`to_id`,`status`) values (2,'2021-06-27',3,2,'accepted'),(3,'2021-06-28',4,3,'accepted'),(4,'2021-06-28',4,2,'completed'),(5,'2021-06-28',6,4,'pending'),(7,'2021-06-28',6,2,'rejected'),(8,'2021-06-28',5,4,'pending'),(9,'2021-06-28',5,3,'rejected'),(10,'2021-06-28',2,3,'completed'),(12,'2021-06-28',3,4,'pending'),(13,'2021-07-14',6,3,'pending'),(14,'2021-07-14',5,3,'pending');

/*Table structure for table `skill` */

DROP TABLE IF EXISTS `skill`;

CREATE TABLE `skill` (
  `skill_id` int(30) NOT NULL AUTO_INCREMENT,
  `skill` varchar(50) DEFAULT NULL,
  `description` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`skill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `skill` */

insert  into `skill`(`skill_id`,`skill`,`description`) values (2,'capenter','a person who makes and repairs wooden objects '),(3,'driver','those who have lisence'),(4,'cook','those who know cooking well'),(5,'teacher','4 year experience'),(6,'unskilled','not a worker');

/*Table structure for table `user_location` */

DROP TABLE IF EXISTS `user_location`;

CREATE TABLE `user_location` (
  `locid` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `place` varchar(250) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `lid` int(11) DEFAULT NULL,
  PRIMARY KEY (`locid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `user_location` */

insert  into `user_location`(`locid`,`latitude`,`longitude`,`place`,`date`,`lid`) values (1,10.9791,76.2227,'Mehafil','2021-07-14',3),(2,10.9793,76.2258,'OOTTY ROAD','2021-07-14',2),(3,10.9792,76.2231,'Mehafil','2021-06-28',4),(4,10.9839,76.2209,'Tharayil trade complex','2021-07-14',6),(5,10.9839,76.2209,'Tharayil trade complex','2021-07-14',5);

/*Table structure for table `view_user` */

DROP TABLE IF EXISTS `view_user`;

CREATE TABLE `view_user` (
  `sno` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `phone_no` varchar(50) DEFAULT NULL,
  `place` varchar(40) DEFAULT NULL,
  `post` varchar(40) DEFAULT NULL,
  `image` varchar(150) DEFAULT NULL,
  `occupation` varchar(40) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `loginid` int(11) DEFAULT NULL,
  `skill_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `view_user` */

insert  into `view_user`(`sno`,`name`,`phone_no`,`place`,`post`,`image`,`occupation`,`email`,`loginid`,`skill_id`) values (0,'azra','1234567890','calicut','Calicut','/static/user_pic/2021_06_02_09_44_33.915111.jpg','driver','azra629@gmail.com',3,3),(2,'mohan','2546389','feroke','679355','/static/user_pic/2021_06_02_09_44_33.915111.jpg','driver','mohan627@gmail.com',2,3),(3,'anees','12345','calicut','6543','/static/user_pic/2021_06_02_09_44_33.915111.jpg','carpenter','anees@gmail',4,2),(6,'mina','123456','pmna','123','/static/user_pic/2021_06_04_09_07_18.329094.jpg','teacher','mina@gmail.com',5,5),(7,'effa','1234567','melattur','12345','/static/user_pic/2021_06_05_00_03_32.504158.jpg','cook','effa@gmail.com',6,4),(8,'rayan','123456','edapal','456','/static/user_pic/2021_06_05_08_39_52.597891.jpg','cook','rayan@gmail.com',7,4),(9,'amre','12344','pmna','pmna','/static/user_pic/2021_06_28_16_18_07.941940.jpg','driver','amree@gmail.com',8,8),(10,'dalia','12345','pmna','pmna','/static/user_pic/2021_06_28_16_21_42.167063.jpg','house wife','dalia@gmail.com',9,6),(11,'dalia','12345','pmna','pmna','/static/user_pic/2021_06_28_16_21_42.323326.jpg','house wife','dalia@gmail.com',10,6),(12,'dalia','12345','pmna','pmna','/static/user_pic/2021_06_28_16_21_43.554986.jpg','house wife','dalia@gmail.com',11,6),(13,'dalia','12345','pmna','pmna','/static/user_pic/2021_06_28_16_21_43.708884.jpg','house wife','dalia@gmail.com',12,6),(14,'subaida','9995018048','Calicut','Calicut','/static/user_pic/2021_07_13_20_21_52.288747.jpg','cook','vsa1200@gmail.com',13,4);

/*Table structure for table `get_rating` */

DROP TABLE IF EXISTS `get_rating`;

/*!50001 DROP VIEW IF EXISTS `get_rating` */;
/*!50001 DROP TABLE IF EXISTS `get_rating` */;

/*!50001 CREATE TABLE `get_rating` (
  `sno` int(11) NOT NULL DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `phone_no` varchar(50) DEFAULT NULL,
  `place` varchar(40) DEFAULT NULL,
  `post` varchar(40) DEFAULT NULL,
  `image` varchar(150) DEFAULT NULL,
  `occupation` varchar(40) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `loginid` int(11) DEFAULT NULL,
  `skill_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*View structure for view get_rating */

/*!50001 DROP TABLE IF EXISTS `get_rating` */;
/*!50001 DROP VIEW IF EXISTS `get_rating` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `get_rating` AS (select `view_user`.`sno` AS `sno`,`view_user`.`name` AS `name`,`view_user`.`phone_no` AS `phone_no`,`view_user`.`place` AS `place`,`view_user`.`post` AS `post`,`view_user`.`image` AS `image`,`view_user`.`occupation` AS `occupation`,`view_user`.`email` AS `email`,`view_user`.`loginid` AS `loginid`,`view_user`.`skill_id` AS `skill_id`,`rating`.`to_id` AS `to_id`,`rating`.`rate` AS `rate` from (`view_user` left join `rating` on((`rating`.`to_id` = `view_user`.`loginid`)))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
