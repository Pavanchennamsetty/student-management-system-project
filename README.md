# Student Management System (Java, JDBC, MySQL, Swing)

**What this is**
A beginner-friendly Java project demonstrating JDBC connectivity with MySQL and a simple Swing GUI for managing students (add, update, delete, list).

**Requirements**
- Java 8 or later
- MySQL server
- MySQL Connector/J (JDBC driver) — add the JAR to the project's classpath when compiling/running

**Database setup**
1. Create the database and table:
   ```sql
   -- run create_db.sql or execute these commands
   CREATE DATABASE studentdb;
   USE studentdb;
   CREATE TABLE students (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     age INT,
     email VARCHAR(150)
   );
   
2. Update database credentials in `src/com/example/sms/DBUtil.java` if needed (default: user `root`, password `password`).

**Compile & Run (command line)**
1. Place the MySQL Connector/J jar in a folder, e.g. `lib/mysql-connector-java.jar`.
2. From the project root:
   ```bash
   mkdir -p bin
   javac -d bin -cp "lib/mysql-connector-java.jar" src/com/example/sms/*.java
   java -cp "bin:lib/mysql-connector-java.jar" com.example.sms.Main
   ```
   (On Windows use `;` instead of `:` in classpath.)

**Notes**
- This project is intentionally simple so you can read and extend the code.