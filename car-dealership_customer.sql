-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: car-dealership
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` char(10) NOT NULL,
  `Full_Name` char(20) DEFAULT NULL,
  `Email` char(30) DEFAULT NULL,
  `Phone` char(10) DEFAULT NULL,
  `Age` int DEFAULT NULL,
  `Adress` char(50) DEFAULT NULL,
  `Date_Of_Birth` date DEFAULT NULL,
  `License_Number` char(20) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('1','Amani Sheikh','amani@gmail.com','0594374527',21,'Ramallah-Baladna ST-4891','2002-07-15','24565'),('10','Lucy Hale','songsAreRad@mail.com','0592583401',40,'Hebron- Badpeople ST- 1990','1983-09-08','39458'),('11','Fatiima Balwa','fatima@gmail.com','0598983211',39,'Jenin-yarmouk ST-8844','1984-03-21','56743'),('12','Zach Waterson','BlueKitten@gmail.com','0595129845',19,'Hebron - Jabalia ST - 2092','2004-05-03','68954'),('13','Yunis Ramadan','yunisRamadan@gmail.com','0598006688',67,'Ramallah-Jilbab St-2008','1956-07-09','52323'),('14','Waleen Miqdad','sisterJana@gmail.com','0591111011',25,'Jericho-Center ST-7000','1998-11-20','43223'),('15','Zurayda Ismail','zurayda@gmail.com','0598996849',58,'Gaza-Salah Al Deen ST-2001','1965-01-30','53413'),('2','Hanadi Asfour','hanadi@gmail.com','0595742736',20,'Ramallah-Alquds ST-9043','2003-09-02','76587'),('3','Falasteen Abu Ali','falasteen@gmail.com','0591122354',20,'Ramallah-Alquds St-5490','2003-11-28','12345'),('4','Amir Ganim','amir@gmail.com','0592855613',30,'Ramallah-Jilbab St-2178','1993-04-12','98623'),('5','Margo Thalji','greatnes4ever@mail.com','0595021781',52,'Nablus-Shayifhali ST-3250','1971-08-23','11920'),('6','Baheya Asfour','meowKitty@gmail.com','0590274967',22,'Al Quds- Better ST-6743','2001-02-05','98892'),('7','Samih Kawaliya','im2cool4u@gmail.com','0595345703',31,'Nablus- Om Badi ST-2341','1992-10-17','23500'),('8','Hussien Fajir','hussien28andbeyond@mail.com','0592478230',29,'Ramallah-Alquds ST-4343','1994-06-30','01002'),('9','Mohammad Alkalam','mohammadAZ4@gmail.com','0592469320',28,'Al Quds- Better ST-6711','1995-12-18','02358');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-15  0:01:49
