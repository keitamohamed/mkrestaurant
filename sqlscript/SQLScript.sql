DROP DATABASE IF EXISTS OnLineStore;
CREATE DATABASE OnLineStore;
USE OnLineStore;

CREATE TABLE User (
  UserID INT NOT NULL,
  FirstName VARCHAR(60) NOT NULL,
  LastName VARCHAR(60) NOT NULL,
  UserName VARCHAR(60) NOT NULL,
  Password VARCHAR(60) NOT NULL,
  UserType VARCHAR(10) NOT NULL,

  PRIMARY KEY (UserID)
)ENGINE = innoDB;

CREATE TABLE UserInfo(
  UserID INT NOT NULL,
  UserAddress VARCHAR(100) NOT NULL,
  City VARCHAR(20) NOT NULL,
  State VARCHAR(3) NOT NULL,
  Zipcode INT NOT NULL,

  CONSTRAINT UserID_FK FOREIGN KEY (UserID) REFERENCES User (UserID)
)ENGINE = innoDB;

CREATE TABLE ShippingAddress (
  SID INT NOT NULL AUTO_INCREMENT,
  UID INT NOT NULL,
  SAddress VARCHAR (150) NOT NULL,

  PRIMARY KEY (SID),
  CONSTRAINT UID_FK FOREIGN KEY (UID) REFERENCES User (UserID)
)ENGINE = innoDB;

CREATE TABLE Product (
  ProductID INT NOT NULL,
  PName VARCHAR (50) NOT NULL,
  Quantity INT NOT NULL,
  Price DECIMAL (6, 2) NOT NULL,
  ImageName VARCHAR (50) NULL,

  PRIMARY KEY (ProductID)
)ENGINE = innoDB;

CREATE TABLE POrder (
  POrderID INT NOT NULL AUTO_INCREMENT,
  OrderID INT NOT NULL,
  UserID INT NOT NULL,
  PID INT NOT NULL,
  Quantity INT NOT NULL,
  Price DECIMAL (6, 2) NOT NULL,
  ImageName VARCHAR (50) NULL,

  PRIMARY KEY (POrderID),
  CONSTRAINT PID_FK FOREIGN KEY (PID) REFERENCES Product (ProductID)
)ENGINE = innoDB;

INSERT INTO User (UserID, FirstName, LastName, UserName, Password, UserType)
VALUE (25634, 'John', 'Smith', 'jSmith', '!2Smith', 'Admin'),
      (67234, 'Ashely', 'William', 'aWilliam', 'Ashely!23', 'Cust');
INSERT INTO Product (ProductID, PName, Quantity, Price, ImageName)
VALUE (7823, 'Lays, Sun and Jalapeno Chip', 1, 3.78, 'chips'),
      (67231, 'Chips Ahoy Cookie', 15, 5.37, 'chips-ahoy-cookie'),
      (75662, 'Pure-Life Water', 7, 3.98, 'pure-life-water'),
      (99873, 'Grandmas Chocolate Cookie', 9, 1.89, 'grandmas-cookie'),
      (56221, 'Origin Water', 5, 2.56, 'origing-water'),
      (67342, 'Deer Park Water', 9, 2.89, 'deer-park'),
      (54674, 'Strawberry and Peach', 10, 5.47, 'fruit-one'),
      (55432, 'Apple, Banana and Pana', 10, 12.36, 'fruit-two'),
      (97783, 'Golden valley Water', 13, 1.50, 'golden-valley'),
      (33178, 'Orange', 89, 3.89, 'orange'),
      (45543, 'Orange drink', 23, 2.49, 'orange-drink'),
      (77563, 'Oreo Cookie', 6, 3.67, 'oreo');

