-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: myblog_v2
-- ------------------------------------------------------
-- Server version	8.0.41

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
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (119,'Sử dụng --force-with-lease khi push để đảm bảo rằng không có sự thay đổi ở remote so với lần push trước đó.','2025-03-19 08:56:18.774609',0,0,NULL,36,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (34,0,'# Problem\nkhi có nhiều tài khoản github, việc push commit đúng tài khoản là vấn đề.\n# Solution\n## GitHub CLI\nDưới đây là hướng dẫn cơ bản để sử dụng GitHub CLI (gh) nhằm đăng nhập, kiểm tra trạng thái, và chuyển đổi giữa các tài khoản GitHub:\n\n---\n\n### 1. Cài đặt GitHub CLI\n\n- **Tải và cài đặt:**  \n  Truy cập trang chính thức của GitHub CLI tại [cli.github.com](https://cli.github.com/) để tải và cài đặt phiên bản phù hợp với hệ điều hành của bạn.\n\n---\n\n### 2. Đăng nhập vào tài khoản GitHub\n\n- **Sử dụng lệnh đăng nhập:**  \n  Mở terminal và chạy lệnh:\n  ```bash\n  gh auth login\n  ```\n  Lệnh này sẽ hướng dẫn bạn chọn giữa đăng nhập qua web hoặc nhập token. Bạn có thể chọn đăng nhập qua web để tiện lợi:\n  - Chọn “GitHub.com” khi được hỏi.\n  - Chọn “Web browser” để mở trình duyệt và xác thực.\n  - Sau khi xác thực thành công, terminal sẽ thông báo bạn đã đăng nhập thành công.\n\n- **Ví dụ đăng nhập:**\n  ```bash\n  $ gh auth login\n  ? What account do you want to log into? GitHub.com\n  ? What is your preferred protocol for Git operations? HTTPS\n  ? Authenticate Git with your GitHub credentials? Yes\n  - Opening https://github.com/login/device in your browser...\n  ```\n  \n---\n\n### 3. Kiểm tra trạng thái đăng nhập\n\n- **Xem tài khoản hiện tại:**  \n  Sử dụng lệnh:\n  ```bash\n  gh auth status\n  ```\n  Lệnh này sẽ hiển thị thông tin tài khoản hiện đang đăng nhập cùng với các scopes được cấp phép.\n\n---\n\n### 4. Chuyển đổi giữa các tài khoản\n\nNếu bạn có nhiều tài khoản GitHub, bạn có thể chuyển đổi theo các bước sau:\n\n- **Đăng xuất khỏi tài khoản hiện tại:**  \n  ```bash\n  gh auth logout\n  ```\n  Lệnh này sẽ đăng xuất bạn khỏi tài khoản đang đăng nhập. Bạn sẽ được hỏi xác nhận đăng xuất.\n\n- **Đăng nhập vào tài khoản khác:**  \n  Sau khi đăng xuất, chạy lại lệnh:\n  ```bash\n  gh auth login\n  ```\n  Và thực hiện quá trình đăng nhập như hướng dẫn ở bước 2 với tài khoản mong muốn.\n\n\n---\n\n### Tóm lại\n\n- **Đăng nhập:** Sử dụng `gh auth login` để đăng nhập.\n- **Kiểm tra trạng thái:** Dùng `gh auth status` để xem tài khoản hiện tại.\n- **Chuyển đổi tài khoản:** Đăng xuất bằng `gh auth logout` rồi đăng nhập lại với tài khoản khác.\n\nVới GitHub CLI, bạn có thể dễ dàng quản lý và chuyển đổi giữa các tài khoản GitHub mà không cần phải thay đổi nhiều cấu hình thủ công.\n\n---\n\nTham khảo thêm tài liệu chính thức của GitHub CLI tại [docs.github.com/en/cli](https://docs.github.com/en/cli) để biết thêm chi tiết.\n',0,'2025-03-19 08:46:10.483662',0,'Cách chuyển đổi tài khoản github nhanh chóng để push commit',1,1),(35,0,'Cả hai tùy chọn đều dùng để ép buộc ghi đè lịch sử commit trên remote, nhưng chúng khác nhau ở chỗ:\n\n- **--force:**  \n  - Khi sử dụng, Git sẽ ghi đè trực tiếp lên remote mà không kiểm tra sự thay đổi của người khác.  \n  - Nếu có người khác đã đẩy commit mới lên remote, bạn có thể vô tình ghi đè và làm mất dữ liệu của họ.\n\n- **--force-with-lease:**  \n  - Tùy chọn này an toàn hơn vì Git sẽ kiểm tra trước xem remote có thay đổi nào không so với lần đẩy cuối cùng của bạn.  \n  - Nếu remote đã được cập nhật (ví dụ: có commit của người khác), lệnh sẽ từ chối push để bảo vệ dữ liệu.  \n  - Chỉ khi remote vẫn giữ nguyên trạng thái như lần bạn lấy về (fetch) thì push sẽ được thực hiện.\n\nTóm lại, **--force-with-lease** là lựa chọn an toàn hơn khi cần ghi đè, giúp tránh việc vô tình ghi đè commit của người khác trên remote.',0,'2025-03-19 08:48:57.542496',0,'Cẩn thận với --force trong git',1,1),(36,1,'Để xóa hoàn toàn một commit khỏi lịch sử (biến mất không dấu vết), bạn sẽ cần phải **viết lại lịch sử commit** của nhánh bằng cách sử dụng lệnh **git reset** hoặc **git rebase -i** rồi **force push** lên remote. Dưới đây là các bước cụ thể:\n\n### 1. Sử dụng **git reset** (nếu commit cần loại bỏ là commit cuối cùng hoặc gần cuối)\n- **Bước 1:** Xác định commit trước commit bạn muốn loại bỏ. Giả sử bạn muốn xóa commit cuối cùng, bạn có thể đưa nhánh về commit trước đó:\n  ```bash\n  git reset --hard HEAD~1\n  ```\n- **Bước 2:** Sau đó, force push thay đổi lên remote:\n  ```bash\n  git push origin <branch-name> --force\n  ```\n  *Lưu ý:* Việc sử dụng `--force` sẽ ghi đè lịch sử trên remote, vì vậy hãy chắc chắn rằng không có ai khác đang làm việc trên nhánh đó.\n\n### 2. Sử dụng **git rebase -i** (nếu commit cần xóa nằm giữa lịch sử commit)\n- **Bước 1:** Chạy rebase tương tác bắt đầu từ commit ngay trước commit cần loại bỏ:\n  ```bash\n  git rebase -i <commit-hash>^\n  ```\n  Trong đó, `<commit-hash>` là mã của commit bạn muốn loại bỏ.\n- **Bước 2:** Trong trình soạn thảo, tìm dòng chứa commit đó và thay đổi từ `pick` thành `drop` (hoặc xóa dòng đó).\n- **Bước 3:** Lưu và thoát trình soạn thảo để hoàn thành rebase.\n- **Bước 4:** Sau đó, force push lên remote:\n  ```bash\n  git push origin <branch-name> --force\n  ```\n\n### Lưu ý quan trọng\n- **Cảnh báo:** Việc force push sẽ ghi đè lịch sử commit trên remote, do đó cần thận trọng và thông báo cho các thành viên khác cùng làm việc trên nhánh nếu cần.\n- **Backup:** Hãy đảm bảo rằng bạn đã sao lưu hoặc kiểm tra kỹ lưỡng trước khi thực hiện thao tác này để tránh mất mát dữ liệu không mong muốn.\n\nVới cách này, commit bạn muốn loại bỏ sẽ không còn xuất hiện trong lịch sử của nhánh nữa.',0,'2025-03-19 08:52:12.703210',0,'Cách để xóa hoàn toàn một commit khỏi lịch sử',1,1),(37,0,'Trong câu lệnh `git push -u origin master`, option `-u` (viết tắt của `--set-upstream`) có tác dụng thiết lập branch từ xa (remote branch) làm \"upstream\" cho branch hiện tại của bạn. Điều này giúp:\n\n- **Đồng bộ dễ dàng hơn:** Sau khi đã thiết lập upstream, bạn chỉ cần sử dụng `git push` hoặc `git pull` mà không cần phải chỉ định lại remote và branch.\n- **Theo dõi trạng thái:** Git sẽ tự động biết branch nào đang theo dõi branch từ xa, giúp hiển thị thông tin khác biệt giữa chúng một cách rõ ràng.\n\nVậy, `-u` giúp bạn tiết kiệm công việc gõ lại thông tin remote/branch và hỗ trợ quản lý các thay đổi giữa branch cục bộ và branch từ xa hiệu quả hơn.\n\n**Tóm lại:** Không nên dùng, dùng câu lệnh dài một chút nhưng tường mình, ví dụ như:\n```\ngit push origin <local_branch>:<remote_branch>\n```',0,'2025-03-19 09:05:37.025259',0,'Sự vô dụng của git push -u',1,1);
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
INSERT INTO `posts_tags` VALUES (34,46),(34,47),(34,48),(34,49),(35,49),(36,49),(37,49),(35,50);
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
INSERT INTO `posts_viewers` VALUES (34,1),(35,1),(36,1),(37,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (14,'..'),(34,'agile'),(43,'báo cáo'),(40,'basic'),(32,'developer'),(6,'eu'),(7,'euro'),(45,'fdfd'),(42,'fdsfds'),(41,'fdskfjk'),(44,'fsdf'),(28,'gaga'),(9,'gg'),(10,'gg dich'),(48,'gh'),(49,'git'),(50,'git push'),(46,'github'),(47,'github cli'),(29,'haha'),(18,'hình đẹp'),(27,'huhu'),(13,'không'),(12,'không có'),(17,'không có việc gì khó'),(11,'khong sao'),(22,'kim dung'),(23,'lệnh hồ sung'),(39,'markdown'),(26,'mây'),(19,'mây xanh'),(24,'newbie'),(25,'ngày dài'),(21,'phim'),(30,'string'),(1,'stringaaaaaaa'),(5,'stringaaaazzzzz'),(2,'stringabbbbbbbb'),(3,'stringqqqq'),(4,'stringqqqq22222222'),(36,'thảo luận'),(16,'trần đăng khoa'),(20,'truyện'),(31,'tủ sách'),(38,'tutorial'),(8,'uno'),(33,'waterfall'),(35,'welcome'),(15,'y học');
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
INSERT INTO `users` VALUES (1,'2024-07-28 06:01:37','admin@gmail.com','2025-03-19 10:11:43','ta la admin day','$2a$10$35WqtcXWAcdzg4Heotn.5uIyZtxIy2sTAWk9kHYvOEkdLNuDQZE/C','admin'),(2,'2024-07-28 06:09:00','aohk@gmail.com','2024-08-14 00:08:28','ta la aohk day','$2a$10$h.kBL5bWOpbHzV/SWrlo9ePiwPOgq18evp6R33AFitVOnBOnQDDSK','aohk'),(3,'2024-07-28 06:11:26','aofsdfsdfdsfsdfdfshk@gmail.com','2024-07-28 06:11:25','ta la aohk day','$2a$10$4PPrt3FSMv7bcfXQk50.U.wTIVJzhji18UEKJrr7rvByXB4uLyJH.','aohfdsfsdk'),(4,'2024-07-28 06:12:20','aofsdfsdfdssfsdfdfshk@gmail.com','2024-07-28 06:12:19','ta la aohk day','$2a$10$4vRX1F.lLBg/wFatv/iAzOJcdvy98v2Y9CsgjeEii8F4m9ymRLef2','aohfdsfsdsk'),(5,'2024-07-28 06:15:59','aofsdfsddfdssfsdfdfshk@gmail.com','2024-07-28 06:15:58','ta la aohk day','$2a$10$gycK6dbHhwwDRD79RzXUCuKpIJEk3FW2w0pmrimaX9jONC8auVoei','aohfddsfsdsk'),(6,'2024-07-28 06:19:18','uno@gmail.com','2024-08-14 00:08:47','ta la uno','$2a$10$QkgC5dIdHmGgdYqg5DhgoOXVBXVpWibeeE9ojU9/knyY./NKEDBKW','uno'),(7,'2024-07-28 09:53:47','a@gmail.com','2024-08-14 07:32:42','string1','$2a$10$lKptgSe3bBOvqswz7V6SGOKcAGNHzG0VjMiITNggvrxkqiV.t/fHG','author1'),(8,'2024-07-28 09:54:02','b@gmail.com','2024-08-14 05:37:45','string2','$2a$10$WF6m/.bvNc9jXyftECOiOuV/L4D.qGvwpsrm34qsAY5IF3t4PgIX6','author2'),(9,'2024-07-28 21:29:37','sa@gmail.com','2024-07-28 21:29:36','sa','$2a$10$fumggJk9ueU5CbW9ceMmCOkUo4rLguQBfOXbiPzVUQJgNHYuh5iMy','sa'),(10,'2024-07-28 21:33:42','s@s','2024-07-28 21:33:42','s','$2a$10$o7/OCT/qgX8SLF..1qQWTO4TDlF8gKqN0bDHz9yC2zLK4zRFztEy2','a'),(11,'2024-07-28 21:37:05','q@q','2024-07-28 21:37:05','q','$2a$10$3UcQ9MrzwBMAGPKPAq9/HOKC3VzVyNFOMPTY3H54LWU2YzaVYNfsW','q'),(12,'2024-07-28 21:40:15','e@gmail.com','2024-07-28 21:40:14','e','$2a$10$/Vb0cKnwMunoYH60kJXlCuCiJ67.EALhd3SQNYvQsgHyOeK8m1kxG','e'),(13,'2024-07-28 21:41:19','w@s','2024-07-28 21:41:19','ư','$2a$10$gRTQP6/fZmPkN/5UKNvgV.YQstWVbw/lFXpDw.wj9j6RdmiDOPJ7.','w'),(14,'2024-07-28 22:11:07','tieubangchu@gmail.com','2024-07-28 22:11:07','Kiều Phong','$2a$10$tHEqpWhUDuCOsN1I0ijV8u7plOGp7WlTmAPGuFP/JROHEHq/RfO9e','kieuphong'),(15,'2024-07-28 22:22:18','nhide@gmail.com','2025-03-19 04:01:56','Đoàn Dự','$2a$10$VVXPQMlLLNZXliz9KxKX3eltjBK.09JYSESO7/wKu8R5tvFRGd0yi','doandu'),(16,'2024-07-28 22:31:28','tamde@gmail.com','2024-10-01 10:31:01','Hư Trúc','$2a$10$EXyi20x2A8D9hQbHkJMvD.CDiuy2TTgmxWfSD7xIYKFxCtROmtfCO','hutruc'),(17,'2024-07-30 22:25:06','ss@ssss','2024-08-02 19:29:25','sssss','$2a$10$M/sX3f3SeD0CEmPFD.Xdz.iE1IpDMPkgCQh6whjQcE64hkwXh0oiu','sssss'),(18,'2024-08-06 17:56:21','thandieudaihiep@gmail.com','2024-08-06 17:56:21','Dương Quá','$2a$10$MKZOpVg3rwFiqHuABvNRlO8GPGWOOb82p1KLitJojQkg87jAct7me','duongqua');
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
INSERT INTO `users_following` VALUES (7,1),(7,2),(7,6),(15,7),(17,7),(7,8),(16,8),(7,15),(7,16),(15,16);
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
INSERT INTO `users_roles` VALUES (1,1),(7,1),(8,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2);
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

-- Dump completed on 2025-03-19 17:19:47
