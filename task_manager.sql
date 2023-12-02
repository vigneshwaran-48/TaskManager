-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: task_manager
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `list`
--

CREATE DATABASE task_manager;

USE task_manager;

DROP TABLE IF EXISTS `list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `list` (
  `list_id` bigint NOT NULL AUTO_INCREMENT,
  `list_color` varchar(255) DEFAULT NULL,
  `list_name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list`
--

LOCK TABLES `list` WRITE;
/*!40000 ALTER TABLE `list` DISABLE KEYS */;
INSERT INTO `list` VALUES (6,'#fe6a6b','Personal','-1'),(7,'#db77f3','Work','-1'),(33,'#5d7dfa','Bug','dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(34,'#fe6a6b','Test','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(35,'#fe6a6b','Testing','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(36,'#8ce99a','Testing 123','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(37,'#fe6a6b','ljclkejwev','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(38,'#fe6a6b','mne m wnb','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(48,'#9675fb','mt test','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9');
/*!40000 ALTER TABLE `list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `tag_id` bigint NOT NULL AUTO_INCREMENT,
  `tag_color` varchar(255) DEFAULT NULL,
  `tag_name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `is_completed` bit(1) NOT NULL,
  `parent_task` bigint DEFAULT NULL,
  `task_name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (101,NULL,'2023-10-14',_binary '',NULL,'This task is created from vicky account','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(102,NULL,'2023-10-14',_binary '\0',NULL,'Task created from basith account','76a03ea0-e261-4386-9b40-af2ff2883fff','2023-11-25 23:15:02.000000'),(104,NULL,'2023-10-16',_binary '',NULL,'test task','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(105,'Need to ask dimple what should I ask to Nani AppX team to know where SendMailDetails are given as response','2023-10-17',_binary '',NULL,'SendMailDetails Mobile response doubt','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(144,'Found this bug, when placing the gun in hand make sure the gun or object didn&#39;t have a name that the character bones have, or the StreamLine something error will come while playing the game','2023-10-29',_binary '',NULL,'IK gun bug','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(160,'In All Comp when we check a task as completed it in a Yet2Finish filter we need to hide that still didn&#39;t implemted that','2023-11-02',_binary '\0',NULL,'Completed tasks in YetToStart','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(172,'Finish this feature in Chatbox, Let the AppManager handle the Group creation and then create a client connection to that group and manage both','2023-11-06',_binary '\0',NULL,'Group creation and Join','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(177,'For learning this there is tutorial video in freecodecamp yt','2023-11-09',_binary '\0',NULL,'Learn Linux','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(178,'Done all missed changes in KERNEL_RESPONSE_CONSTANTS branch','2023-11-09',_binary '',NULL,'SendMaildetails and Signature constants','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(195,NULL,'2023-11-11',_binary '\0',NULL,'Dashboard page in TaskManager','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(206,NULL,'2023-11-11',_binary '\0',NULL,'debfeen','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(207,NULL,'2023-11-11',_binary '\0',NULL,'teshfgh787','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(208,NULL,'2023-11-11',_binary '\0',NULL,'ewqvr372837','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(237,NULL,'2023-11-11',_binary '\0',NULL,'vne','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(239,NULL,'2023-11-11',_binary '\0',NULL,'cnev','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(240,NULL,'2023-11-11',_binary '\0',NULL,'mdnv','f7b2eb31-7f7c-445c-9a03-bdbac797cdf9','2023-11-25 23:15:02.000000'),(241,'Check websocket is secure in TaskManager app','2023-11-11',_binary '\0',NULL,'Check websocket is secure','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(276,'Need to test it by taking a build','2023-11-14',_binary '\0',NULL,'Vacation Response constants','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(281,'Need to implement this feature','2023-11-15',_binary '\0',NULL,'Import Export Task Data','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(282,'Settings page header name not changing when navigating to Settings page from other route','2023-11-15',_binary '\0',NULL,'Settings page header Name','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(283,NULL,'2023-11-16',_binary '',NULL,'Test Vacation Request Params','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(287,'Gun Aim UI element and actual gun shooting point is different','2023-11-19',_binary '\0',NULL,'Gun Aim position','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(290,'something','2023-11-20',_binary '',NULL,'something to do in my life','559be0b9-cdc7-43c4-b9a8-ec36f2bc2d73','2023-11-25 23:15:02.000000'),(297,NULL,'2023-11-23',_binary '',NULL,'Check about SvelteJs','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(298,'Need to ask prawin bro, Should I do unification for these constants in Vacation sample class =&gt; VacationAPIServerAPIImpl&#39;s exportVacation method','2023-11-23',_binary '',NULL,'DateFormat and TimeZone','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(300,'Asked prawin bro need to use it only vacation request params and for others other vacation response use we as agent','2023-11-24',_binary '',NULL,'Ask Agent Mode valid','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(301,'Take notes for the explanation that prawin bro gave for how notification works','2023-11-24',_binary '',NULL,'Take notes of NotificationBuilder','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(302,'Change this class when pushing Vacation changes to milestone branch and give this class prawin bro will push the class to the mailserver repo','2023-11-24',_binary '\0',NULL,'MailServer AccountRESTClientTransformer','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-25 23:15:02.000000'),(305,'Finish Game StateMachine in Endless Runner game project','2023-11-26',_binary '\0',NULL,'Finish Game StateMachine','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-26 23:14:37.684262'),(306,'Finish listing with sortby in settings like in All tasks component','2023-11-26',_binary '',NULL,'Sortby settings in listing','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-26 23:15:51.507974'),(307,NULL,'2023-11-27',_binary '',NULL,'Handle AutoExpunge for int values','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-27 12:02:06.105148'),(321,NULL,'2023-11-28',_binary '',NULL,'Csadmin Signature changes','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-28 12:47:44.048860'),(322,'Remove your slipper and work','2023-11-28',_binary '',NULL,'Remove your Slipper','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-28 21:37:48.145547'),(323,'Handle onboarding name input in csadmin and render that extra column in tha result table','2023-11-29',_binary '\0',NULL,'Onboarding name input','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-29 11:59:13.390798'),(324,'Need to change the &quot;fromDateTime&quot; param to &quot;from&quot; param','2023-11-29',_binary '\0',NULL,'Vacation addZohoAccount from param','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-29 19:06:25.679830'),(331,NULL,'2023-11-29',_binary '\0',NULL,'Testing','12','2023-11-29 22:00:42.467091'),(332,NULL,'2023-11-29',_binary '\0',NULL,'ATest','12','2023-11-29 22:00:45.303145'),(333,NULL,'2023-11-29',_binary '',NULL,'CTest','12','2023-11-29 22:00:47.185837'),(334,NULL,'2023-11-29',_binary '',NULL,'BTest','12','2023-11-29 22:00:48.993249'),(335,NULL,'2023-11-29',_binary '\0',NULL,'ZTest','12','2023-11-29 22:00:51.135289'),(336,NULL,'2023-11-30',_binary '\0',NULL,'HTest','12','2023-11-29 22:00:52.877644'),(337,'Need to discuss about the problem in VacationClientAPIImpl where we are using getValue() directly but need to set the clienttype for it.','2023-11-30',_binary '\0',NULL,'Problem in VacationAPIClientImpl','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-30 10:20:51.291314'),(338,NULL,'2023-11-30',_binary '\0',NULL,'Local Hbase in Localbuild','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-30 15:37:14.104735'),(339,NULL,'2023-11-30',_binary '\0',NULL,'Local Redis in Localbuild','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-30 15:37:22.066166'),(340,'Need ask prawin bro can I add an extra catch clause for handle that exception for password encoding','2023-11-30',_binary '\0',NULL,'Exception handling in SendMailDetails','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-30 17:10:00.119634'),(341,'We can handle client type in json construction but we can&#39;t do it if the constants has been directly used. Need to discuss this with prawin bro','2023-11-30',_binary '\0',NULL,'Vacation directlt getting the constants','dd3353ac-aeab-417c-bf4f-bc0f825acb03','2023-11-30 18:39:17.017650');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_list`
--

DROP TABLE IF EXISTS `task_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_list` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `list_id` bigint NOT NULL,
  `task_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKti4ygsaic5wyrw3oxlrspygsa` (`list_id`),
  KEY `FK4crgxc9q674vsymqprx6qty7q` (`task_id`),
  CONSTRAINT `FK4crgxc9q674vsymqprx6qty7q` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FKti4ygsaic5wyrw3oxlrspygsa` FOREIGN KEY (`list_id`) REFERENCES `list` (`list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=348 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_list`
--

LOCK TABLES `task_list` WRITE;
/*!40000 ALTER TABLE `task_list` DISABLE KEYS */;
INSERT INTO `task_list` VALUES (75,6,101,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(76,6,102,'76a03ea0-e261-4386-9b40-af2ff2883fff'),(81,6,104,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(134,33,144,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(152,6,160,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(164,6,172,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(169,6,177,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(170,7,178,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(187,6,195,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(198,6,206,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(199,6,207,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(200,6,208,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(229,6,237,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(231,6,239,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(232,6,240,'f7b2eb31-7f7c-445c-9a03-bdbac797cdf9'),(233,6,241,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(268,7,276,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(284,6,281,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(285,33,282,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(292,33,287,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(301,6,297,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(302,7,298,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(305,7,300,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(306,7,301,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(307,7,302,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(310,6,305,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(311,6,306,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(312,7,307,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(313,7,283,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(327,7,321,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(328,7,322,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(329,7,323,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(330,7,324,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(337,6,331,'12'),(338,6,332,'12'),(339,6,333,'12'),(340,6,334,'12'),(341,6,335,'12'),(342,6,336,'12'),(343,7,337,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(344,7,338,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(345,7,339,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(346,7,340,'dd3353ac-aeab-417c-bf4f-bc0f825acb03'),(347,7,341,'dd3353ac-aeab-417c-bf4f-bc0f825acb03');
/*!40000 ALTER TABLE `task_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_tag`
--

DROP TABLE IF EXISTS `task_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_id` bigint NOT NULL,
  `task_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9mp6455j6w7duvlo9cwok7s6j` (`tag_id`),
  KEY `FKmnb6mkxwtvkg1utqig0ps56ne` (`task_id`),
  CONSTRAINT `FK9mp6455j6w7duvlo9cwok7s6j` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`),
  CONSTRAINT `FKmnb6mkxwtvkg1utqig0ps56ne` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_tag`
--

LOCK TABLES `task_tag` WRITE;
/*!40000 ALTER TABLE `task_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-01  9:36:57
