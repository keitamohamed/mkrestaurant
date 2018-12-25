DROP DATABASE IF EXISTS OnLineStore;

CREATE DATABASE OnLineStore;

USE OnLineStore;

CREATE TABLE UserTable(
  UserID INT NOT NULL,
  FirstName VARCHAR(60) NOT NULL,
  LastName VARCHAR(60) NOT NULL,
  UserName VARCHAR(60) NOT NULL,
  Password VARCHAR(60) NOT NULL,
  UserType VARCHAR(10) NOT NULL,

  PRIMARY KEY (UserID)
)ENGINE = innoDB;

CREATE TABLE Address (
  ID INT NOT NULL AUTO_INCREMENT,
  UserID INT NOT NULL,
  UserAddress VARCHAR(100) NOT NULL,
  City VARCHAR(20) NOT NULL,
  State VARCHAR(3) NOT NULL,
  ZipCode INT NOT NULL,

  PRIMARY KEY(ID)
)ENGINE = innoDB;

CREATE TABLE ShippingAddress (
  ShappingID INT NOT NULL AUTO_INCREMENT,
  UID INT NOT NULL,
  FullAddress VARCHAR (150) NOT NULL,

  PRIMARY KEY (ShappingID)
)ENGINE = innoDB;

CREATE TABLE ProductTable (
  ProductID INT NOT NULL,
  PName VARCHAR (50) NOT NULL,
  Quantity INT NOT NULL,
  Price DECIMAL (6, 2) NOT NULL,
  ImageName VARCHAR (50) NULL,

  PRIMARY KEY (ProductID)
)ENGINE = innoDB;

CREATE TABLE OrderTable (
  GenerateID INT NOT NULL AUTO_INCREMENT,
  OrderID INT NOT NULL,
  UserID INT NULL,
  PID INT NOT NULL,
  ProductName VARCHAR(150) NOT NULL,
  Quantity INT NOT NULL,
  Price DECIMAL (6, 2) NOT NULL,

  PRIMARY KEY (GenerateID)
)ENGINE = innoDB;

ALTER TABLE ShippingAddress AUTO_INCREMENT = 100;
ALTER TABLE Address AUTO_INCREMENT = 1011;
ALTER TABLE OrderTable AUTO_INCREMENT = 1120;

ALTER TABLE Address ADD CONSTRAINT UserID_FK FOREIGN KEY (UserID) REFERENCES UserTable (UserID);
ALTER TABLE ShippingAddress ADD CONSTRAINT UID_FK FOREIGN KEY (UID) REFERENCES UserTable (UserID);
ALTER TABLE OrderTable ADD CONSTRAINT PID_FK FOREIGN KEY (PID) REFERENCES ProductTable (ProductID);


INSERT INTO UserTable (UserID, FirstName, LastName, UserName, Password, UserType)
VALUE (256343, 'John', 'Smith', 'jSmith', '!2Smith', 'Employee'),
      (672341, 'Ashely', 'William', 'aWilliam', 'Ashely!23', 'Customer');
INSERT INTO Address(UserID, UserAddress, City, State, ZipCode)
VALUE (256343, '562 East Way DR Apt 15', 'Charlotte', 'NC', 28740),
      (672341, '892 Arthur Avenue Ave SE Apt 106', 'New York City', 'NY', 78352);
INSERT INTO ProductTable (ProductID, PName, Quantity, Price, ImageName)
VALUE (78232, 'Lays, Sun and Jalapeno Chip', 1, 3.78, 'chips'),
      (67235, 'Chips Ahoy Cookie', 15, 5.37, 'chips-ahoy-cookie'),
      (75062, 'Pure-Life Water', 7, 3.98, 'pure-life-water'),
      (99873, 'Grandmas Chocolate Cookie', 9, 1.89, 'grandmas-cookie'),
      (56221, 'Origin Water', 5, 2.56, 'origing-water'),
      (67342, 'Deer Park Water', 9, 2.89, 'deer-park'),
      (54674, 'Strawberry and Peach', 10, 5.47, 'fruit-one'),
      (55432, 'Apple, Banana and Pana', 10, 12.36, 'fruit-two'),
      (97783, 'Golden valley Water', 13, 1.50, 'golden-valley'),
      (33178, 'Orange', 89, 3.89, 'orange'),
      (45543, 'Orange drink', 23, 2.49, 'orange-drink'),
      (99701, 'Creamy Peanut Butter', 15, 4.89, 'cadia-peanut-butter'),
      (110021, 'Natural Peanut Butter', 4, 7.34, 'crunchy-peanut-butter'),
      (77563, 'Oreo Cookie', 6, 3.67, 'oreo'),
      (70172, 'KP Jumbo Salted Peanut', 9, 2.32, 'jumbo-salted-peanut'),
      (77007, 'KP Salted Peanut', 11, 2.98, 'original-salted-peanut');





