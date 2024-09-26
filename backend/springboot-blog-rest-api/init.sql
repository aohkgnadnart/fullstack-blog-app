-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: myblog_v2
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `comment_dislikes`
--

DROP TABLE IF EXISTS `comment_dislikes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_dislikes` (
  `comment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`comment_id`,`user_id`),
  KEY `FKcrv6elcq6cwapedwllp9uvydr` (`user_id`),
  CONSTRAINT `FK3sf17i0imged70nb7ucged75t` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `FKcrv6elcq6cwapedwllp9uvydr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_dislikes`
--

LOCK TABLES `comment_dislikes` WRITE;
/*!40000 ALTER TABLE `comment_dislikes` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_dislikes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_likes`
--

DROP TABLE IF EXISTS `comment_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_likes` (
  `comment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`comment_id`,`user_id`),
  KEY `FK6h3lbneryl5pyb9ykaju7werx` (`user_id`),
  CONSTRAINT `FK3wa5u7bs1p1o9hmavtgdgk1go` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `FK6h3lbneryl5pyb9ykaju7werx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_likes`
--

LOCK TABLES `comment_likes` WRITE;
/*!40000 ALTER TABLE `comment_likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `dislike_count` int NOT NULL,
  `like_count` int NOT NULL,
  `parent_comment_id` bigint DEFAULT NULL,
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7h839m3lkvhbyv3bcdv7sm4fj` (`parent_comment_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK7h839m3lkvhbyv3bcdv7sm4fj` FOREIGN KEY (`parent_comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'hôm nay là một ngày đẹp trời','2024-07-31 06:22:19.512952',0,0,NULL,23,8),(2,'sài gòn hôm nay mưa','2024-07-31 07:08:15.846272',0,0,NULL,23,7),(3,'sà','2024-07-31 07:08:24.440275',0,0,NULL,23,7),(4,' a','2024-07-31 07:08:44.420123',0,0,NULL,23,7),(5,'thu xem nao','2024-07-31 07:09:28.354832',0,0,NULL,23,7),(6,'ko được thử','2024-07-31 07:09:45.276539',0,0,NULL,23,7),(7,'buoi sang','2024-07-31 07:10:18.413305',0,0,NULL,23,7),(8,'buoi sang','2024-07-31 07:11:50.098574',0,0,NULL,23,7),(9,'buoi sangs','2024-07-31 07:27:49.430477',0,0,NULL,23,7),(10,'buoi toi','2024-07-31 07:28:44.329122',0,0,NULL,23,7),(11,'buoi toi','2024-07-31 08:15:40.854249',0,0,6,23,7),(12,'buoi chieu toi','2024-07-31 08:16:09.443526',0,0,6,23,7),(13,'toigian ak ma','2024-07-31 08:17:15.480721',0,0,NULL,23,7),(14,'thu tuwj cos quan trong','2024-07-31 08:18:36.258230',0,0,6,23,7),(15,'con của con','2024-07-31 09:19:05.505722',0,0,12,23,7),(16,'con của con của con...','2024-07-31 09:21:57.184461',0,0,15,23,7),(17,'không có việc gì khó','2024-08-01 05:55:32.475384',0,0,6,23,16),(18,'buổi sáng thật trong lành','2024-08-01 06:05:07.745354',0,0,NULL,23,16),(19,'reply con của con','2024-08-01 06:58:51.325453',0,0,NULL,23,16),(20,'aaaaaa','2024-08-01 07:03:35.522778',0,0,NULL,23,16),(21,'adfsdfsdfs','2024-08-01 07:06:52.420996',0,0,6,23,16),(22,'adfsdfsdfs','2024-08-01 07:07:14.753503',0,0,15,23,16),(23,'www','2024-08-01 07:23:07.189540',0,0,NULL,23,16),(24,'ee','2024-08-01 07:30:55.054145',0,0,15,23,16),(25,'ff','2024-08-01 07:46:45.167696',0,0,NULL,23,16),(26,'adadad','2024-08-01 08:01:54.763848',0,0,NULL,23,16),(27,'fdsfdsfdfffffffffffffffffffffff f','2024-08-01 08:06:04.103546',0,0,15,23,16),(28,'vvv','2024-08-01 08:11:18.648168',0,0,NULL,23,16),(29,'vv','2024-08-01 08:11:48.381112',0,0,NULL,23,16),(30,'noi dung reply thu nhiem','2024-08-01 08:15:26.720794',0,0,NULL,23,16),(31,'noi dung reply thu nhiem','2024-08-01 08:21:30.174566',0,0,NULL,23,16),(32,'fdsfdsfdfffffffffffffffffffffff f','2024-08-01 08:22:08.105790',0,0,15,23,16),(33,'noi dung reply thu nhiem','2024-08-01 08:27:52.302609',0,0,NULL,23,16),(34,'noi dung reply thu nhiem','2024-08-01 08:28:19.177877',0,0,NULL,23,16),(35,'aa','2024-08-01 08:33:54.621438',0,0,NULL,23,16),(36,'ff','2024-08-01 08:34:43.737800',0,0,NULL,23,16),(37,'version 2','2024-08-01 08:53:59.442705',0,0,NULL,23,16),(38,'version 2 roi de nhes đây là comment con','2024-08-01 08:54:26.694642',0,0,15,23,16),(39,'fasfafffffffffffffffffffffffffeeeeeeeeeeeeeeeee','2024-08-01 09:01:42.260774',0,0,22,23,16),(40,'910fskfjsdl','2024-08-01 09:10:50.068190',0,0,39,23,16),(41,'911 fskfjsdklfsd','2024-08-01 09:11:08.441047',0,0,25,23,16),(42,'911 v2','2024-08-01 09:11:33.700317',0,0,20,23,16),(43,'fdsf 913','2024-08-01 09:13:28.880186',0,0,41,23,16),(44,'     fsf sdf s915','2024-08-01 09:15:05.817941',0,0,22,23,16),(45,'915v2','2024-08-01 09:16:01.891158',0,0,22,23,16),(46,'920','2024-08-01 09:21:02.797476',0,0,22,23,16),(47,'921 roi','2024-08-01 09:21:31.142461',0,0,46,23,16),(48,'thử nghiệm ahy','2024-08-01 09:22:11.133067',0,0,33,23,16),(49,'fsdfds 931','2024-08-01 09:32:01.698813',0,0,39,23,16),(50,'932','2024-08-01 09:32:17.005999',0,0,NULL,23,16),(51,'946','2024-08-01 09:46:05.167567',0,0,37,23,16),(52,'946 v2','2024-08-01 09:46:14.531939',0,0,NULL,23,16),(53,'fsdfsdfds','2024-08-01 09:47:10.464262',0,0,NULL,22,16),(54,'fsfsd','2024-08-01 09:47:28.760965',0,0,53,22,7),(55,'fsdfffffffffffff','2024-08-01 09:47:32.524906',0,0,53,22,7),(56,'947 rồi nha','2024-08-01 09:47:58.349052',0,0,39,23,7),(57,'fsdfsdfds','2024-08-01 10:32:26.151218',0,0,33,23,7),(58,'fsfsdf','2024-08-01 10:32:31.316832',0,0,57,23,7),(59,'fsdfdsf1033','2024-08-01 10:33:16.414349',0,0,NULL,23,7),(60,'hay đó','2024-08-01 11:49:09.756471',0,0,48,23,15),(61,'ổn không nhỉ','2024-08-01 17:04:45.332423',0,0,46,23,16),(62,'cũng được','2024-08-01 17:05:49.663691',0,0,46,23,16),(63,'lỗi gì rồi','2024-08-01 18:46:54.282311',0,0,22,23,16),(64,'không nên xem','2024-08-01 18:57:24.697560',0,0,NULL,24,16),(65,'một ngày mới lại bắt đầu','2024-08-02 07:10:24.133221',0,0,27,23,15),(66,'comment daoj','2024-08-02 22:40:00.685100',0,0,NULL,25,7),(67,'thoi thoi','2024-08-02 22:40:10.497756',0,0,66,25,7),(68,'ngay moi lai bat dau','2024-08-03 06:56:09.772417',0,0,NULL,4,15),(69,'hahaha','2024-08-04 12:06:28.369921',0,0,67,25,15),(70,'hihi','2024-08-04 12:07:34.682002',0,0,67,25,15),(71,'ngày đẹp trời','2024-08-04 12:17:37.989874',0,0,66,25,15),(72,'fasfsdfsd','2024-08-04 12:39:06.618028',0,0,69,25,15),(73,'thử xem nào','2024-08-04 22:56:52.035765',0,0,66,25,15),(74,'thử lại nào','2024-08-04 22:59:13.587057',0,0,66,25,15),(76,'hihi, lại là ngày đẹp trời','2024-08-05 09:20:12.778579',0,0,73,25,15),(77,'bug này mạnh đấy, nhưng tôi mạnh hơn','2024-08-05 09:20:41.699687',0,0,NULL,25,15),(79,'Comment mới nè','2024-08-05 14:14:02.137681',0,0,66,25,15),(81,'ngày ngắn','2024-08-05 14:34:34.187345',0,0,67,25,15),(82,'ngắn ngày','2024-08-05 14:34:46.070041',0,0,72,25,15),(84,'1435 rồi nè','2024-08-05 14:36:06.113111',0,0,NULL,25,15),(91,'hihihihi','2024-08-05 15:56:51.025249',0,0,69,25,15),(92,'1557 roi ne','2024-08-05 15:57:08.255533',0,0,NULL,25,15),(103,'fsdsdf ','2024-08-05 18:07:48.394194',0,0,NULL,28,15),(106,'đây đúng là một bài việt chất lượng','2024-08-06 19:05:59.768055',0,0,NULL,28,7),(108,'ngày mình đi với nhau đó là ngày tuyệt vời','2024-08-06 20:10:53.122112',0,0,NULL,25,7),(110,'ngày dài như năm','2024-08-09 06:00:56.436152',0,0,61,23,15),(112,'fdsfsd','2024-08-14 05:34:49.585496',0,0,91,25,8),(113,'f sdfsdfsd  dsf ds','2024-08-14 05:35:37.518680',0,0,69,25,8),(116,'ngày mới lại bắt đầu','2024-08-14 05:42:13.123431',0,0,NULL,23,15);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_requests`
--

DROP TABLE IF EXISTS `friend_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_requests` (
  `sender_id` bigint NOT NULL,
  `receiver_id` bigint NOT NULL,
  PRIMARY KEY (`sender_id`,`receiver_id`),
  KEY `FKtcmqalc5v4qdt1slgcsa544i5` (`receiver_id`),
  CONSTRAINT `FKcchlh48b4347amfvmke793bg7` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKtcmqalc5v4qdt1slgcsa544i5` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_requests`
--

LOCK TABLES `friend_requests` WRITE;
/*!40000 ALTER TABLE `friend_requests` DISABLE KEYS */;
INSERT INTO `friend_requests` VALUES (7,6),(7,16);
/*!40000 ALTER TABLE `friend_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `recipient_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sender` (`sender_id`),
  KEY `idx_recipient` (`recipient_id`),
  KEY `idx_sender_recipient` (`sender_id`,`recipient_id`),
  KEY `idx_recipient_sender` (`recipient_id`,`sender_id`),
  CONSTRAINT `FK4ui4nnwntodh6wjvck53dbk9m` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKhdkwfnspwb3s60j27vpg0rpg6` FOREIGN KEY (`recipient_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'test thu thoi','2024-07-30 15:10:15.719042',15,16),(2,'phan hoi ne','2024-06-30 15:10:15.719042',16,15),(3,'lam gi day','2024-08-03 12:20:10.105484',16,15),(4,'lam gi day','2024-08-03 12:26:49.707485',16,15),(5,'lam gi day','2024-08-03 12:30:25.382786',16,15),(6,'lam gi day','2024-08-03 12:33:10.410987',16,15),(7,'lam gi day','2024-08-03 12:36:48.279519',16,15),(8,'lam gi day','2024-08-03 12:36:55.047628',16,15),(9,'lam gi day','2024-08-03 12:37:43.094856',16,15),(10,'lam gi day','2024-08-03 12:38:48.191015',16,15),(11,'lam gi day','2024-08-03 12:40:54.258774',16,15),(12,'lam gi day','2024-08-03 12:41:20.913176',16,15),(13,'lam gi day','2024-08-03 12:42:03.635687',15,16),(14,'lam gi day','2024-08-03 12:42:30.958892',16,15),(15,'lam gi day','2024-08-03 12:47:09.838744',16,15),(16,'lam gi day','2024-08-03 12:48:04.639227',16,15),(17,'lam gi day','2024-08-03 12:50:42.907673',16,15),(18,'dang lam gì đó','2024-08-03 13:17:50.826492',15,16),(19,'tai sao','2024-08-05 19:41:20.144204',16,15),(20,'dang lam gi go','2024-08-05 19:42:20.662245',15,16),(21,'tyuty','2024-08-05 19:43:25.546475',16,15),(22,'hahah','2024-08-05 20:01:24.319837',16,15),(23,'2328, thanh cong roi','2024-08-05 23:28:20.836211',16,15),(24,'hello Hu truc','2024-08-06 00:02:00.977218',16,15),(25,'222','2024-08-06 00:02:46.416065',16,15),(26,'333','2024-08-06 00:12:03.818239',16,15),(27,'huhu','2024-08-06 00:12:38.468553',15,16),(28,'444','2024-08-06 00:22:59.869663',15,16),(29,'555','2024-08-06 00:31:32.310229',16,15),(30,'666','2024-08-06 00:48:23.152152',16,15),(31,'777','2024-08-06 00:51:18.315951',16,15),(32,'hello doan du','2024-08-06 01:08:26.821593',15,16),(33,'111','2024-08-06 01:08:35.332865',16,15),(34,'hihi','2024-08-06 01:09:39.264668',16,15),(35,'s','2024-08-06 01:12:14.300651',16,15),(36,'2','2024-08-06 01:19:18.220573',15,16),(37,'1','2024-08-06 05:51:24.355158',16,15),(38,'22','2024-08-06 06:01:53.296387',16,15),(39,'33','2024-08-06 06:10:36.998341',16,15),(40,'12','2024-08-06 06:27:22.053161',15,16),(41,'ngày đẹp trời','2024-08-06 06:35:54.278815',16,15),(42,'qq','2024-08-06 06:49:08.362721',16,15),(43,'fsdfsd','2024-08-06 06:51:21.325691',15,16),(44,'qưe','2024-08-06 06:52:56.380222',16,15),(45,'erưer','2024-08-06 06:53:16.642720',16,15),(46,'qưeqưe','2024-08-06 06:59:20.696227',15,16),(47,'2332','2024-08-06 07:04:07.021253',16,15),(48,'đang làm gì đó','2024-08-06 20:26:11.482678',15,7),(49,'hello','2024-08-08 16:00:40.554862',16,15),(50,'Hello lần 2 nè','2024-08-08 16:14:47.685958',16,15),(51,'Heello lần 3','2024-08-08 16:15:00.575568',16,15),(52,'Hello gì nhiều thế','2024-08-08 16:15:57.971927',15,16),(53,'ngày mình đi với nhau đó là ngày tuyệt vời','2024-08-08 16:53:35.404051',16,15),(54,'co do ko','2024-08-08 18:31:19.848265',15,16),(55,'ngày dài như đêm','2024-08-08 19:35:33.031962',15,16),(56,'âa','2024-08-08 19:40:18.426824',15,16),(57,'aaaa','2024-08-08 19:41:21.441460',15,16),(58,'w','2024-08-08 19:42:07.530910',15,16),(59,'q','2024-08-08 19:42:30.795636',16,15),(60,'q','2024-08-08 19:43:41.164074',16,15),(61,'s','2024-08-08 19:47:09.846817',15,16),(62,'ư','2024-08-08 19:49:57.538126',15,16),(63,'â','2024-08-08 19:58:35.557254',15,16),(64,'qưeqưeqư','2024-08-08 20:06:34.159855',16,15),(65,'qưeqưe','2024-08-08 20:11:44.981485',16,15),(66,'qưeqưe','2024-08-08 20:12:00.563699',16,15),(67,'qưeqưeqưeee','2024-08-08 20:12:31.767006',16,15),(68,'q','2024-08-08 20:13:56.309005',15,16),(69,'ư','2024-08-08 20:14:18.251114',16,15),(70,'ww','2024-08-08 20:16:50.117237',15,16),(71,'asdasd','2024-08-08 20:17:24.259148',15,16),(72,'z','2024-08-08 20:21:45.241435',15,16),(73,'e','2024-08-08 20:23:26.271603',15,16),(74,'r','2024-08-08 20:23:57.161670',15,16),(75,'q','2024-08-08 20:24:15.270548',16,15),(76,'2','2024-08-08 20:30:11.261576',16,15),(77,'2','2024-08-08 20:31:33.457216',16,15),(78,'hi','2024-08-08 20:34:37.944553',16,15),(79,'r','2024-08-08 20:37:36.645410',15,16),(80,'e','2024-08-08 20:38:27.593498',15,16),(81,'t','2024-08-08 20:38:42.376228',16,15),(82,'ee','2024-08-08 20:49:44.823687',15,16),(83,'ff','2024-08-08 20:50:01.674526',15,16),(84,'a','2024-08-08 20:51:24.902300',15,16),(85,'x','2024-08-08 20:53:40.007669',15,16),(86,'q','2024-08-08 20:53:50.850930',16,15),(87,'x','2024-08-08 20:55:51.054810',15,16),(88,'ád','2024-08-08 20:59:29.905019',15,16),(89,'x','2024-08-08 20:59:49.658184',16,15),(90,'x','2024-08-08 21:00:48.733614',16,15),(91,'g','2024-08-08 21:01:03.700899',16,15),(92,'d','2024-08-08 21:01:14.591569',16,15),(93,'3','2024-08-08 21:03:53.470506',15,16),(94,'4','2024-08-08 21:04:02.357471',16,15),(95,'5','2024-08-08 21:04:15.298476',15,16),(96,'6','2024-08-08 21:04:36.964542',15,16),(97,'1','2024-08-08 21:07:34.460692',16,15),(98,'2','2024-08-08 21:07:56.683862',16,15),(99,'3','2024-08-08 21:17:13.885872',16,15),(100,'4','2024-08-08 21:18:31.645970',15,16),(101,'chao buoi sang ','2024-08-09 05:42:47.377732',15,16),(102,'goodmorning','2024-08-09 05:48:22.113673',16,15),(103,'ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg dgfdgdf ','2024-08-09 05:50:05.354013',15,16),(104,'vừa ngủ dậy','2024-08-09 05:58:56.483762',7,15),(105,'?','2024-08-10 21:00:00.354106',16,15),(106,'chào buổi sáng','2024-08-14 05:38:49.911838',15,16),(107,'dậy sớm thế nhị ca','2024-08-14 05:39:15.173654',16,15),(108,'đệ cũng vậy','2024-08-14 05:40:12.751679',15,16),(109,'22','2024-08-14 08:15:32.430644',16,15),(110,'lau ngay nhi doan du','2024-09-23 13:48:32.550712',15,16),(111,'uk, lau ngay that','2024-09-23 13:48:45.382341',16,15),(112,'hú hú','2024-09-24 09:19:01.950270',15,16),(113,'có đó ko tiểu đệ','2024-09-24 09:44:46.321619',15,16);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment_count` int NOT NULL,
  `content` text NOT NULL,
  `dislike_count` int NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `like_count` int NOT NULL,
  `title` varchar(80) NOT NULL,
  `viewer_count` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmchce1gm7f6otpphxd6ixsdps` (`title`),
  KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
  CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,0,'stringstriaaaaaaa',0,'2024-07-28 10:13:15.410623',0,'stringaaaaaaaaaa',2,7),(2,0,'stringstriaaaaaaa22222',0,'2024-07-28 10:14:40.626089',0,'stringaaaaaaaaaa22222222222',0,7),(3,0,'Em vừa tham gia cộng đồng, mong được mọi người giúp đỡ ạ',0,'2024-08-14 05:56:30.954769',1,'TITLE TEST ĐỘ DÀI CỦA TITLE, NGÀY DÀI THÁNG RỘNG, MẶT TRỜI MỘC ĐẰNG ĐÔNG',2,7),(4,1,'stringstriqqqqq',0,'2024-07-28 10:18:04.711744',0,'stringqqqqq',1,8),(5,0,'stringstriqqqqq',0,'2024-07-28 10:18:40.614033',0,'stringqqqqq222222',1,8),(6,0,'stringstriqqqqq',0,'2024-07-28 10:19:18.493923',0,'stringqqqqq22222233333333',1,8),(7,0,'stringstriqqqqq',0,'2024-08-14 05:32:56.966291',1,'Kiểm tra xem một biến có phải là Null hay không bằng cách sử dụng Hamcrest',2,8),(8,0,'stringstriqqqqq',0,'2024-07-28 10:19:44.201092',0,'string3333zzzzz',1,8),(10,0,'fsdfsdáddddddddd',0,'2024-07-29 22:32:55.491291',0,'adfádfsafsdd',0,16),(11,0,'ffffffffffff',0,'2024-07-29 22:33:02.366439',0,'ffffffffffffff',0,16),(12,0,'aaaaaaaaaaaaaa',1,'2024-07-30 05:10:43.295898',0,'aaa',1,16),(13,0,'aaaaaaaaaaaaaaa',0,'2024-07-30 05:13:09.710871',0,'aaaa',1,16),(14,0,'aaaaaaaaaaaaaa',0,'2024-07-30 05:22:39.473491',0,'aaaaa',0,16),(15,0,'fsafsdfsdfsfsdf',0,'2024-07-30 05:23:20.537169',0,'as',0,16),(16,0,'asfsdfdsfsdfasfsdfdsfsdfasfsdfdsfsdfasfsdfdsfsdfasfsdfdsfsdfasfsdfdsfsdfasfsdfdsfsdfasfsdfdsfsdf',0,'2024-07-30 05:25:57.240406',0,'asfsdfdsfsdfasfsdfdsfsdfasfsdfdsfsdfasfsdfdsfs',1,16),(17,0,'fsfsdsdfsdfsd',0,'2024-07-30 05:44:57.804734',0,'ádfsd',2,16),(18,0,'fsdfsdsssssssss',0,'2024-07-30 06:12:18.270514',0,'sdfsdfsd',2,16),(20,0,'fasfsfsdfss',0,'2024-07-30 06:57:10.043995',1,'giải thưởng lớn',3,16),(21,0,'sdfsdf sdf sdfsdf sdf sdfdsf ds fdsfdsf ',0,'2024-07-30 06:59:17.757446',0,'chiến lược toàn diện',2,16),(22,3,'sdfsdfsdfsdfsdfsd',0,'2024-07-30 07:11:52.682127',1,'trên trời cao có muôn vì sao',2,16),(23,63,'# Giới thiệu về Machine Learning\n\nMachine Learning (ML) là một nhánh của trí tuệ nhân tạo (AI) tập trung vào việc phát triển các thuật toán và mô hình cho phép máy tính học từ dữ liệu và cải thiện hiệu suất theo thời gian mà không cần lập trình tường minh.\n\n## Lịch sử của Machine Learning\n\nMachine Learning không phải là một khái niệm mới; nó đã được phát triển từ những năm 1950. Một số mốc quan trọng trong lịch sử của Machine Learning bao gồm:\n\n- **1950:** Alan Turing giới thiệu \"Turing Test\" để kiểm tra khả năng của máy tính trong việc thể hiện hành vi thông minh tương đương con người.\n- **1952:** Arthur Samuel phát triển chương trình chơi cờ đam, một trong những chương trình đầu tiên sử dụng khái niệm học máy.\n- **1997:** Máy tính Deep Blue của IBM đánh bại nhà vô địch cờ vua thế giới Garry Kasparov.\n- **2016:** AlphaGo của Google DeepMind đánh bại nhà vô địch cờ vây thế giới Lee Sedol.\n\n## Các loại Machine Learning\n\nCó ba loại chính của Machine Learning:\n\n1. **Học có giám sát (Supervised Learning):**\n    - **Mô tả:** Máy học từ các dữ liệu được gắn nhãn để đưa ra dự đoán.\n    - **Ứng dụng:** Phân loại email spam, nhận dạng chữ viết tay, dự đoán giá nhà.\n    - **Ví dụ code:**\n    ```python\n    from sklearn.model_selection import train_test_split\n    from sklearn.linear_model import LinearRegression\n    from sklearn.datasets import load_boston\n\n    # Load dataset\n    boston = load_boston()\n    X = boston.data\n    y = boston.target\n\n    # Split data\n    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)\n\n    # Train model\n    model = LinearRegression()\n    model.fit(X_train, y_train)\n\n    # Predict\n    predictions = model.predict(X_test)\n    ```\n\n2. **Học không giám sát (Unsupervised Learning):**\n    - **Mô tả:** Máy học từ các dữ liệu không gắn nhãn để tìm ra cấu trúc ẩn.\n    - **Ứng dụng:** Phân nhóm khách hàng, giảm kích thước dữ liệu, phát hiện gian lận.\n    - **Ví dụ code:**\n    ```python\n    from sklearn.cluster import KMeans\n    from sklearn.datasets import load_iris\n\n    # Load dataset\n    iris = load_iris()\n    X = iris.data\n\n    # Cluster data\n    kmeans = KMeans(n_clusters=3, random_state=42)\n    kmeans.fit(X)\n\n    # Predict\n    clusters = kmeans.predict(X)\n    ```\n\n3. **Học tăng cường (Reinforcement Learning):**\n    - **Mô tả:** Máy học thông qua thử và sai, nhận phản hồi từ môi trường để tối ưu hóa hành động.\n    - **Ứng dụng:** Điều khiển robot, chơi game, quản lý tài nguyên.\n    - **Ví dụ code:**\n    ```python\n    import gym\n\n    # Load environment\n    env = gym.make(\'CartPole-v1\')\n\n    # Initialize variables\n    state = env.reset()\n    total_reward = 0\n    done = False\n\n    while not done:\n        action = env.action_space.sample()  # Random action\n        next_state, reward, done, info = env.step(action)\n        total_reward += reward\n        state = next_state\n\n    print(f\'Total reward: {total_reward}\')\n    ```\n\n## Các thuật toán phổ biến trong Machine Learning\n\nDưới đây là một số thuật toán phổ biến trong Machine Learning và ứng dụng của chúng:\n\n- **Hồi quy tuyến tính (Linear Regression):** Dự đoán giá trị liên tục.\n- **Cây quyết định (Decision Trees):** Phân loại và hồi quy.\n- **Rừng ngẫu nhiên (Random Forest):** Phân loại và hồi quy, xử lý overfitting.\n- **Hồi quy logistic (Logistic Regression):** Phân loại nhị phân.\n- **Mạng nơ-ron (Neural Networks):** Học sâu, xử lý dữ liệu hình ảnh và âm thanh.\n\n## Ứng dụng của Machine Learning\n\nMachine Learning đã được ứng dụng rộng rãi trong nhiều lĩnh vực:\n\n- **Y tế:** Dự đoán bệnh, phân tích hình ảnh y khoa.\n- **Tài chính:** Dự đoán thị trường chứng khoán, phát hiện gian lận.\n- **Giải trí:** Khuyến nghị phim, [âm nhạc](https://www.youtube.com/watch?v=ZyYmIiYEK7I&pp=ygUYYsOgaSB0w6xuaCBjYSBj4bunYSDEkcOh).\n- **Giao thông:** Xe tự lái, tối ưu hóa lộ trình.\n\n## Kết luận\n\nMachine Learning là một lĩnh vực đầy tiềm năng và không ngừng phát triển. Với sự phát triển của công nghệ và lượng dữ liệu ngày càng tăng, Machine Learning sẽ tiếp tục đóng vai trò quan trọng trong nhiều lĩnh vực khác nhau.\n\n## Hình ảnh minh họa\n\n![Machine Learning](https://i.pinimg.com/564x/b5/49/78/b54978334dd062cebc1dfa3194718257.jpg)\n\n',1,'2024-09-26 05:12:08.832632',2,'title test markdown và comment dạng cây',4,16),(24,1,'đông phương bất bại',0,'2024-08-01 18:56:05.685924',1,'Tiếu ngạo giang hồ',2,16),(25,19,'đây là bài viết đầu tay của tôi.',1,'2024-08-08 14:39:10.462728',2,'Chào mọi người,,',4,15),(28,2,'\n# 1. Mô hình Agile- Scrum\nLà phương pháp phát triển phầm mềm linh hoạt để làm sao đưa sản phẩm đến tay người tiêu dùng càng nhanh càng tốt, càng sớm càng tốt. Scrum là 1 dạng của mô hình Agile và là Framework phổ biến nhất khi thực hiện mô hình Agile Scrum là mô hình phát triển lặp đi lặp lại. Những khoảng lặp cố định thường kéo dài 1,2 tuần được gọi lại Sprint hoặc Iteration\n\n\n# 2. Mô hình Waterfall\nMô hình Waterfall- Mô hình thác nước là 1 mô hình quản lý dự án dễ nhất hiện nay. Mô hình thác nước là 1 phương pháp quản lý dự án dữa trên quy trình thiết kế tuần tự là liên tiếp. Trong mô hình Watefall, các giai đoạn của dự án được thực hiện lần lượt và nối tiếp nhau. Giai đoạn mới chĩ được bắt đầu khi giai đoạn trước nó đã hoàn thành\n\nMỗi bước sẽ có tài liệu mô tả từng bước',1,'2024-08-06 19:05:22.552674',0,'Một số mô hình phát triển phần mềm',4,15),(29,0,'Chào anh em, anh em hãy cùng tôi thảo luận về chủ nề này nhé',0,'2024-08-06 19:25:53.928223',0,'Dự kiến sài gòn sẽ mưa trong nhiều ngày tới',3,7),(31,0,'\n# tiêu đề\n\nfdsàdsfds',0,'2024-08-14 08:19:12.955486',0,'fdsjkfdsjk fjdskjfkldsjfkl dskljf kldsdsf',1,15),(32,0,'fsdfsdfsdfds',0,'2024-08-26 16:36:46.395862',0,'fsafsdf',2,15),(33,0,'fsdfsdfdsff',0,'2024-08-26 16:36:56.992389',0,'fdsfsfasdf',2,15);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts_dislikes`
--

DROP TABLE IF EXISTS `posts_dislikes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts_dislikes` (
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`user_id`),
  KEY `FK77i2b9jivo9279eix5ht7ecm6` (`user_id`),
  CONSTRAINT `FK77i2b9jivo9279eix5ht7ecm6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmcx3pxb1qr92cy7m6uj7brkkr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts_dislikes`
--

LOCK TABLES `posts_dislikes` WRITE;
/*!40000 ALTER TABLE `posts_dislikes` DISABLE KEYS */;
INSERT INTO `posts_dislikes` VALUES (25,7),(12,15),(23,15),(28,15);
/*!40000 ALTER TABLE `posts_dislikes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts_likes`
--

DROP TABLE IF EXISTS `posts_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts_likes` (
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`user_id`),
  KEY `FKt5kx9tu4bo443unk2n21dmshd` (`user_id`),
  CONSTRAINT `FKimxtd6dl39nmu9x0snqm6mu1g` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKt5kx9tu4bo443unk2n21dmshd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts_likes`
--

LOCK TABLES `posts_likes` WRITE;
/*!40000 ALTER TABLE `posts_likes` DISABLE KEYS */;
INSERT INTO `posts_likes` VALUES (20,8),(23,8),(24,8),(25,8),(3,15),(22,15),(25,15),(7,16),(23,16);
/*!40000 ALTER TABLE `posts_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts_tags`
--

DROP TABLE IF EXISTS `posts_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts_tags` (
  `post_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`tag_id`),
  KEY `FK4svsmj4juqu2l8yaw6whr1v4v` (`tag_id`),
  CONSTRAINT `FK4svsmj4juqu2l8yaw6whr1v4v` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`),
  CONSTRAINT `FKcreclgob71ibo58gsm6l5wp6` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts_tags`
--

LOCK TABLES `posts_tags` WRITE;
/*!40000 ALTER TABLE `posts_tags` DISABLE KEYS */;
INSERT INTO `posts_tags` VALUES (1,1),(2,1),(6,1),(2,2),(4,3),(5,3),(5,4),(6,4),(7,4),(8,4),(17,4),(7,5),(8,5),(18,5),(10,6),(11,6),(12,7),(17,7),(24,7),(13,8),(14,9),(17,9),(20,9),(15,10),(17,10),(16,11),(18,12),(21,12),(23,12),(25,12),(31,13),(20,15),(21,16),(22,17),(23,18),(23,19),(24,20),(24,21),(24,22),(24,23),(3,24),(25,24),(28,32),(28,33),(28,34),(31,34),(3,35),(29,36),(31,41),(31,42),(31,43),(32,44),(33,45);
/*!40000 ALTER TABLE `posts_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts_viewers`
--

DROP TABLE IF EXISTS `posts_viewers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts_viewers` (
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`user_id`),
  KEY `FKg87trk3hlwg3o90x6958i6gt1` (`user_id`),
  CONSTRAINT `FKg87trk3hlwg3o90x6958i6gt1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKl9clvtc5xlsfs3pvwvikwhmo9` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts_viewers`
--

LOCK TABLES `posts_viewers` WRITE;
/*!40000 ALTER TABLE `posts_viewers` DISABLE KEYS */;
INSERT INTO `posts_viewers` VALUES (3,7),(16,7),(17,7),(18,7),(20,7),(21,7),(22,7),(23,7),(25,7),(28,7),(29,7),(32,7),(33,7),(1,8),(6,8),(7,8),(17,8),(18,8),(20,8),(21,8),(23,8),(24,8),(25,8),(28,8),(3,15),(4,15),(5,15),(8,15),(12,15),(13,15),(20,15),(22,15),(23,15),(24,15),(25,15),(28,15),(29,15),(31,15),(32,15),(33,15),(1,16),(7,16),(23,16),(25,16),(28,16),(29,16);
/*!40000 ALTER TABLE `posts_viewers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt48xdq560gs3gap9g7jg36kgc` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (14,'..'),(34,'agile'),(43,'báo cáo'),(40,'basic'),(32,'developer'),(6,'eu'),(7,'euro'),(45,'fdfd'),(42,'fdsfds'),(41,'fdskfjk'),(44,'fsdf'),(28,'gaga'),(9,'gg'),(10,'gg dich'),(29,'haha'),(18,'hình đẹp'),(27,'huhu'),(13,'không'),(12,'không có'),(17,'không có việc gì khó'),(11,'khong sao'),(22,'kim dung'),(23,'lệnh hồ sung'),(39,'markdown'),(26,'mây'),(19,'mây xanh'),(24,'newbie'),(25,'ngày dài'),(21,'phim'),(30,'string'),(1,'stringaaaaaaa'),(5,'stringaaaazzzzz'),(2,'stringabbbbbbbb'),(3,'stringqqqq'),(4,'stringqqqq22222222'),(36,'thảo luận'),(16,'trần đăng khoa'),(20,'truyện'),(31,'tủ sách'),(38,'tutorial'),(8,'uno'),(33,'waterfall'),(35,'welcome'),(15,'y học');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `email` varchar(320) NOT NULL,
  `last_visit` datetime NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2024-07-28 06:01:37','admin@gmail.com','2024-08-14 00:05:19','ta la admin day','$2a$10$35WqtcXWAcdzg4Heotn.5uIyZtxIy2sTAWk9kHYvOEkdLNuDQZE/C','admin'),(2,'2024-07-28 06:09:00','aohk@gmail.com','2024-08-14 00:08:28','ta la aohk day','$2a$10$h.kBL5bWOpbHzV/SWrlo9ePiwPOgq18evp6R33AFitVOnBOnQDDSK','aohk'),(3,'2024-07-28 06:11:26','aofsdfsdfdsfsdfdfshk@gmail.com','2024-07-28 06:11:25','ta la aohk day','$2a$10$4PPrt3FSMv7bcfXQk50.U.wTIVJzhji18UEKJrr7rvByXB4uLyJH.','aohfdsfsdk'),(4,'2024-07-28 06:12:20','aofsdfsdfdssfsdfdfshk@gmail.com','2024-07-28 06:12:19','ta la aohk day','$2a$10$4vRX1F.lLBg/wFatv/iAzOJcdvy98v2Y9CsgjeEii8F4m9ymRLef2','aohfdsfsdsk'),(5,'2024-07-28 06:15:59','aofsdfsddfdssfsdfdfshk@gmail.com','2024-07-28 06:15:58','ta la aohk day','$2a$10$gycK6dbHhwwDRD79RzXUCuKpIJEk3FW2w0pmrimaX9jONC8auVoei','aohfddsfsdsk'),(6,'2024-07-28 06:19:18','uno@gmail.com','2024-08-14 00:08:47','ta la uno','$2a$10$QkgC5dIdHmGgdYqg5DhgoOXVBXVpWibeeE9ojU9/knyY./NKEDBKW','uno'),(7,'2024-07-28 09:53:47','a@gmail.com','2024-09-26 04:35:07','string1','$2a$10$lKptgSe3bBOvqswz7V6SGOKcAGNHzG0VjMiITNggvrxkqiV.t/fHG','author1'),(8,'2024-07-28 09:54:02','b@gmail.com','2024-08-14 05:37:45','string2','$2a$10$WF6m/.bvNc9jXyftECOiOuV/L4D.qGvwpsrm34qsAY5IF3t4PgIX6','author2'),(9,'2024-07-28 21:29:37','sa@gmail.com','2024-07-28 21:29:36','sa','$2a$10$fumggJk9ueU5CbW9ceMmCOkUo4rLguQBfOXbiPzVUQJgNHYuh5iMy','sa'),(10,'2024-07-28 21:33:42','s@s','2024-07-28 21:33:42','s','$2a$10$o7/OCT/qgX8SLF..1qQWTO4TDlF8gKqN0bDHz9yC2zLK4zRFztEy2','a'),(11,'2024-07-28 21:37:05','q@q','2024-07-28 21:37:05','q','$2a$10$3UcQ9MrzwBMAGPKPAq9/HOKC3VzVyNFOMPTY3H54LWU2YzaVYNfsW','q'),(12,'2024-07-28 21:40:15','e@gmail.com','2024-07-28 21:40:14','e','$2a$10$/Vb0cKnwMunoYH60kJXlCuCiJ67.EALhd3SQNYvQsgHyOeK8m1kxG','e'),(13,'2024-07-28 21:41:19','w@s','2024-07-28 21:41:19','ư','$2a$10$gRTQP6/fZmPkN/5UKNvgV.YQstWVbw/lFXpDw.wj9j6RdmiDOPJ7.','w'),(14,'2024-07-28 22:11:07','tieubangchu@gmail.com','2024-07-28 22:11:07','Kiều Phong','$2a$10$tHEqpWhUDuCOsN1I0ijV8u7plOGp7WlTmAPGuFP/JROHEHq/RfO9e','kieuphong'),(15,'2024-07-28 22:22:18','nhide@gmail.com','2024-09-26 05:08:28','Đoàn Dự','$2a$10$VVXPQMlLLNZXliz9KxKX3eltjBK.09JYSESO7/wKu8R5tvFRGd0yi','doandu'),(16,'2024-07-28 22:31:28','tamde@gmail.com','2024-09-26 05:13:35','Hư Trúc','$2a$10$EXyi20x2A8D9hQbHkJMvD.CDiuy2TTgmxWfSD7xIYKFxCtROmtfCO','hutruc'),(17,'2024-07-30 22:25:06','ss@ssss','2024-08-02 19:29:25','sssss','$2a$10$M/sX3f3SeD0CEmPFD.Xdz.iE1IpDMPkgCQh6whjQcE64hkwXh0oiu','sssss'),(18,'2024-08-06 17:56:21','thandieudaihiep@gmail.com','2024-08-06 17:56:21','Dương Quá','$2a$10$MKZOpVg3rwFiqHuABvNRlO8GPGWOOb82p1KLitJojQkg87jAct7me','duongqua');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_following`
--

DROP TABLE IF EXISTS `users_following`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_following` (
  `user_id` bigint NOT NULL,
  `following_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`following_id`),
  KEY `FKrfqb0kmfo2jv9xa0mw7e9euwg` (`following_id`),
  CONSTRAINT `FK7p0n81rhro2hd136vp15id8k7` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKrfqb0kmfo2jv9xa0mw7e9euwg` FOREIGN KEY (`following_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_following`
--

LOCK TABLES `users_following` WRITE;
/*!40000 ALTER TABLE `users_following` DISABLE KEYS */;
INSERT INTO `users_following` VALUES (7,1),(7,2),(7,6),(17,7),(7,8),(16,8),(7,15),(7,16),(15,16);
/*!40000 ALTER TABLE `users_following` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_friends`
--

DROP TABLE IF EXISTS `users_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_friends` (
  `user_id` bigint NOT NULL,
  `friend_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`friend_id`),
  KEY `FKetin2ga6w0oln69xfef2wwjqw` (`friend_id`),
  CONSTRAINT `FKetin2ga6w0oln69xfef2wwjqw` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKry5pun2eg852sbl2l50p236bo` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_friends`
--

LOCK TABLES `users_friends` WRITE;
/*!40000 ALTER TABLE `users_friends` DISABLE KEYS */;
INSERT INTO `users_friends` VALUES (8,7),(15,7),(17,7),(7,8),(7,15),(16,15),(15,16),(7,17);
/*!40000 ALTER TABLE `users_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(7,1),(8,1),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-26 12:14:34