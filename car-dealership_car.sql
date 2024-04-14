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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `car_id` char(10) NOT NULL,
  `year` int DEFAULT NULL,
  `color` char(10) DEFAULT NULL,
  `model` char(30) DEFAULT NULL,
  `status` char(20) DEFAULT NULL,
  `VIN` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `mileage` double DEFAULT NULL,
  `company_name` char(20) NOT NULL,
  PRIMARY KEY (`car_id`),
  UNIQUE KEY `VIN_UNIQUE` (`VIN`),
  KEY `company_name_idx` (`company_name`),
  KEY `company_namek_idx` (`company_name`),
  CONSTRAINT `company_name` FOREIGN KEY (`company_name`) REFERENCES `company` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES ('1',2019,'SILVER','K*LL','Exist',3258410,18000.75,18000,'Ford'),('10',2003,'RED','F20G','Exist',1210200,200000.5,9000,'Skoda'),('11',2013,'WHITE','56EK','Exist',5793335,90000.1,20000,'BMW'),('12',2020,'BLACK','$GHF','OutOfStock',1191441,35346,87008,'Honda'),('13',2022,'BLACK','B$GA','Exist',8710215,250000,40305,'Subaru'),('14',2017,'WHITE','SHB@','Exist',5120180,30200.5,97797,'Ford'),('15',2020,'RED','FL99','Exist',120984,40300,14092,'Toyota'),('2',2014,'BROWN','P@Q9','Exist',5473687,280000.2,60000,'Volkswagen'),('3',2016,'BLUE','XS@8','Exist',5120188,32000.5,68400,'BMW'),('4',2011,'RED','Z&$Q','OutOfStock',7890102,180000,55000,'Toyota'),('5',2021,'GOLD','D&K9','Exist',6234898,25000,69000,'Ford'),('6',2018,'GRAY','B$GA','Exist',4785632,35000.25,50700,'Nissan'),('7',2013,'BLACK','SHB@','Exist',6578970,250000,45000,'Ford'),('9',2019,'BLUE','R7@L','Exist',8950794,200000.5,9000,'Volvo'),('99',1998,'Black','CB%F','Exist',1987,309000,90990,'Honda');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
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
