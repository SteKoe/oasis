-- MySQL dump 10.13  Distrib 5.5.25, for osx10.6 (i386)
--
-- Host: localhost    Database: idss
-- ------------------------------------------------------
-- Server version	5.5.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `projectName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectMember`
--

DROP TABLE IF EXISTS `ProjectMember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectMember` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hob0o9p9rxv0li9m6ktspb5ve` (`user_id`),
  CONSTRAINT `FK_hob0o9p9rxv0li9m6ktspb5ve` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectMember`
--

LOCK TABLES `ProjectMember` WRITE;
/*!40000 ALTER TABLE `ProjectMember` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProjectMember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectMemberToProjectRole`
--

DROP TABLE IF EXISTS `ProjectMemberToProjectRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectMemberToProjectRole` (
  `ProjectMember_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `projectRoles_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ProjectMember_id`,`projectRoles_id`),
  KEY `FK_frgdfwfoif38eql6adiq7kcgd` (`projectRoles_id`),
  KEY `FK_qxw20xxn5l98tal4qq0a9pgh8` (`ProjectMember_id`),
  CONSTRAINT `FK_frgdfwfoif38eql6adiq7kcgd` FOREIGN KEY (`projectRoles_id`) REFERENCES `ProjectRole` (`id`),
  CONSTRAINT `FK_qxw20xxn5l98tal4qq0a9pgh8` FOREIGN KEY (`ProjectMember_id`) REFERENCES `ProjectMember` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectMemberToProjectRole`
--

LOCK TABLES `ProjectMemberToProjectRole` WRITE;
/*!40000 ALTER TABLE `ProjectMemberToProjectRole` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProjectMemberToProjectRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectRole`
--

DROP TABLE IF EXISTS `ProjectRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectRole` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2cci75odd79frrgfkkv94xm7f` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectRole`
--

LOCK TABLES `ProjectRole` WRITE;
/*!40000 ALTER TABLE `ProjectRole` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProjectRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectToProjectMember`
--

DROP TABLE IF EXISTS `ProjectToProjectMember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectToProjectMember` (
  `Project_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `projectTeam_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Project_id`,`projectTeam_id`),
  KEY `FK_iwwkpn5j2yvydgu75j4ww4nqo` (`projectTeam_id`),
  KEY `FK_r3gfmp9kpf62soa97kjkisayk` (`Project_id`),
  CONSTRAINT `FK_iwwkpn5j2yvydgu75j4ww4nqo` FOREIGN KEY (`projectTeam_id`) REFERENCES `ProjectMember` (`id`),
  CONSTRAINT `FK_r3gfmp9kpf62soa97kjkisayk` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectToProjectMember`
--

LOCK TABLES `ProjectToProjectMember` WRITE;
/*!40000 ALTER TABLE `ProjectToProjectMember` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProjectToProjectMember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SystemRole`
--

DROP TABLE IF EXISTS `SystemRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SystemRole` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SystemRole`
--

LOCK TABLES `SystemRole` WRITE;
/*!40000 ALTER TABLE `SystemRole` DISABLE KEYS */;
INSERT INTO `SystemRole` VALUES ('e4d00da9-0efc-4a78-8f2e-f1ea85ae1566','USER'),('fead0981-1280-48a7-ac3c-538df87e3340','ADMIN');
/*!40000 ALTER TABLE `SystemRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `activationKey` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `profile_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jreodf78a7pl5qidfh43axdfb` (`username`),
  KEY `FK_oakeryhiiqn2d8plp1scdlt5r` (`profile_id`),
  CONSTRAINT `FK_oakeryhiiqn2d8plp1scdlt5r` FOREIGN KEY (`profile_id`) REFERENCES `UserProfile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('fead0981-1280-48a7-ac3c-538df87e3340',NULL,'test@example.com','$2a$10$IJb9d/WeOAREJ81OPMs8hOkDYxtn7OUxLZQHzhi4rukLZAyItQO1O','Test User','5d0e3267-af22-4a28-a8da-9414a8df8763');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserProfile`
--

DROP TABLE IF EXISTS `UserProfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserProfile` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `birthdate` date DEFAULT NULL,
  `firstname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserProfile`
--

LOCK TABLES `UserProfile` WRITE;
/*!40000 ALTER TABLE `UserProfile` DISABLE KEYS */;
INSERT INTO `UserProfile` VALUES ('5d0e3267-af22-4a28-a8da-9414a8df8763',NULL,NULL,NULL);
/*!40000 ALTER TABLE `UserProfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserToSystemRole`
--

DROP TABLE IF EXISTS `UserToSystemRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserToSystemRole` (
  `user_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  KEY `FK_km8u90x0hpenqj0jh83o892sl` (`user_id`),
  KEY `FK_hsey0jj3iojpi1hcjuuif3hb4` (`role_id`),
  CONSTRAINT `FK_hsey0jj3iojpi1hcjuuif3hb4` FOREIGN KEY (`role_id`) REFERENCES `SystemRole` (`id`),
  CONSTRAINT `FK_km8u90x0hpenqj0jh83o892sl` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserToSystemRole`
--

LOCK TABLES `UserToSystemRole` WRITE;
/*!40000 ALTER TABLE `UserToSystemRole` DISABLE KEYS */;
INSERT INTO `UserToSystemRole` VALUES ('fead0981-1280-48a7-ac3c-538df87e3340','e4d00da9-0efc-4a78-8f2e-f1ea85ae1566');
/*!40000 ALTER TABLE `UserToSystemRole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-10-08  7:58:46
