CREATE TABLE city (
   id INT NOT NULL auto_increment,
   name VARCHAR(50) NOT NULL,
   principal VARCHAR(50) NOT NULL
);

CREATE TABLE district (
   id INT NOT NULL auto_increment,
   name VARCHAR(50) NOT NULL,
   city_id INT
);

CREATE TABLE measurement (
   id INT NOT NULL auto_increment,
   co2level DOUBLE,
   district_id INT,
   created TIMESTAMP
);