DROP TABLE IF EXISTS user;  

DROP TABLE IF EXISTS heroe;  

CREATE TABLE user (  
username VARCHAR(50) PRIMARY KEY,
password VARCHAR(50) NOT NULL 
); 

CREATE TABLE heroe (  
id INT AUTO_INCREMENT  PRIMARY KEY,
name VARCHAR(MAX) NOT NULL 
);
