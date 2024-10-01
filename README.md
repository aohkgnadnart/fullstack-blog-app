# Blog App - Fullstack Project
## Project Description
This blog application is designed to handle a large number of users efficiently by utilizing RabbitMQ to queue and manage concurrent requests. It also features real-time messaging between users using Socket.IO, along with many other exciting functionalities.
## Features
### Admin:
- Role management form:
  - Promote a user to admin.
  - Demote an admin back to user.
- View the list of users who liked a specific post.
### User:
- Login and change password form.
- Manage personal profile.
- Add, delete, and edit posts.
- Add, delete, and edit comments.
- Like posts, comments, and authors.
- Follow favorite authors.
- View news feed (contains posts from followed authors).
- View comments and like counts in posts.
- Send friend requests to other users.
- Messaging form with friends (other users).
- Search and view posts by author, category, or title.
## Tech Stack
- Backend: Spring Boot, Spring Security, Spring Data JPA, MySQL, RabbitMQ, SocketIO
- Frontend: ReactJS
- Deployment: Docker, AWS
## Installation
### Local Environment
1. Clone the repository
2. At the location of the docker-compose.yml file, run the following command to build and run the application (Docker Compose needs to be installed beforehand):
```bash
docker-compose up --build -d
```
3. To stop and remove containers, networks, and volumes, run:
```bash
docker-compose down -v
```
### AWS Environment (EC2 Instance)
1. Create an EC2 instance on AWS.
2. SSH into the instance:
```bash
ssh -i your-key.pem ec2-user@your-ec2-public-ip

// Example: ssh -i "my-ec2-key.pem" ubuntu@13.236.91.56
```
3. Clone the repository:
4. At the location of the docker-compose.single-ec2-instance.yml file, run the following command to build and run the application (Docker Compose needs to be installed beforehand):
```bash
docker-compose -f docker-compose.single-ec2-instance.yml up --build -d
```
**Note:** In the docker-compose.single-ec2-instance.yml file, make sure to update the public ip to match the public IP of the EC2 instance you created on AWS.

5. To stop and remove containers, networks, and volumes, run:
```bash
docker-compose down -v
```
## Usage
- Visit the homepage at `http://localhost:3000` (for local development) or `http://<your-public-ip>:3000` (if deployed on AWS EC2).
- You can register an account or log in with the following default demo accounts:
   - **admin / admin**
   - **author1 / author1**
   - **author2 / author2**
   - **doandu / doandu**
   - **hutruc / hutruc**

   *(Note: The password is the same as the username for these accounts)*
- Create, edit, or delete blog posts.
- Interact with other users by commenting, liking, or messaging them.
## Contributing
Contributions are welcome! Feel free to open a pull request or issue on GitHub.
## Contact
For any inquiries, you can reach me at [aohkgnadnart](mailto:aohkgnadnart@gmail.com).

