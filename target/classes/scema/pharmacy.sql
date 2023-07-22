drop database pharmacy;
CREATE DATABASE pharmacy;
USE pharmacy;

CREATE TABLE Employee(
                         empID VARCHAR(10),
                         firstName VARCHAR(30) NOT NULL,
                         lastName VARCHAR(30) NOT NULL,
                         street VARCHAR(40),
                         city VARCHAR(30) NOT NULL,
                         lane VARCHAR(30),
                         contact VARCHAR(30) UNIQUE NOT NULL,
                         CONSTRAINT PRIMARY KEY (empID)
);

CREATE TABLE salary(
                       salID VARCHAR(10),
                       empID VARCHAR(10),
                       basic DECIMAL(10,2) NOT NULL,
                       bonus DECIMAL(10,2) ,
                       CONSTRAINT PRIMARY KEY (salID),
                       CONSTRAINT FOREIGN KEY(empID) REFERENCES Employee(empID)
                           ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE attendance(
                           attendanceID VARCHAR(10),
                           empID VARCHAR(10),
                           workingHourse Int(10) NOT NULL,
                           date  DATE,
                           CONSTRAINT PRIMARY KEY (attendanceID),
                           CONSTRAINT FOREIGN KEY(empID) REFERENCES Employee(empID)
                               ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Customer(
                         custID VARCHAR(10) ,
                         firstName VARCHAR(30) NOT NULL,
                         lastName VARCHAR(30) NOT NULL,
                         street VARCHAR(30) NOT NULL ,
                         city VARCHAR(30) NOT NULL,
                         lane VARCHAR(30),
                         contact VARCHAR(30) UNIQUE NOT NULL,
                         CONSTRAINT PRIMARY KEY (custID)
);

CREATE TABLE Orders(
                       orderID VARCHAR(10),
                       custID VARCHAR(10),
                       empID VARCHAR(10),
                       date DATE,
                       orderPayment DECIMAL(8,2),
                       CONSTRAINT PRIMARY KEY (orderID),
                       CONSTRAINT FOREIGN KEY(custID) REFERENCES Customer(custID) ON UPDATE CASCADE ON DELETE CASCADE,
                       CONSTRAINT FOREIGN KEY(empID) REFERENCES Employee(empID) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE Bill(
                     billID VARCHAR(10) ,
                     orderID VARCHAR(10) ,
                     totalAmt DECIMAL(10,2) NOT NULL,
                     custPay DECIMAL(10,2) NOT NULL,
                     discount DECIMAL(10,2),
                     orderDate DATE,
                     CONSTRAINT PRIMARY KEY (billID),
                     CONSTRAINT FOREIGN KEY(orderID) REFERENCES Orders(orderID)
                         ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Medicine(
                         medID VARCHAR(10) ,
                         medName VARCHAR(30) NOT NULL,
                         medUnitPrice DECIMAL(8,2) not null ,
                         type VARCHAR(30) NOT NULL,
                         mfgDate DATE,
                         expDate DATE,
                         qtyOnHand INT(10) NOT NULL,
                         CONSTRAINT PRIMARY KEY (medID)
);

CREATE TABLE orderDetail(
                            medID VARCHAR(10) ,
                            orderID VARCHAR(10) ,
                            qty INT(10),
                            CONSTRAINT FOREIGN KEY(medID) REFERENCES Medicine(medID),
                            CONSTRAINT FOREIGN KEY(orderID) REFERENCES Orders(orderID)
                                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Supplier(
                         supID VARCHAR(10),
                         firstName VARCHAR(30) NOT NULL,
                         lastName VARCHAR(30) NOT NULL,
                         street VARCHAR(40) NOT NULL ,
                         city VARCHAR(30) NOT NULL,
                         lane VARCHAR(30) ,
                         contact VARCHAR(30) UNIQUE NOT NULL ,
                         CONSTRAINT PRIMARY KEY (supID)
);

CREATE TABLE SupplierOrder(
                              supOrderID VARCHAR(10),
                              supID VARCHAR(10),
                              CONSTRAINT PRIMARY KEY (supOrderID),
                              CONSTRAINT FOREIGN KEY(supID) REFERENCES Supplier(supID)
                                  ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE Supply(
                       medID VARCHAR(10) ,
                       supOrderID VARCHAR(10) ,
                       CONSTRAINT FOREIGN KEY(medID) REFERENCES Medicine(medID),
                       CONSTRAINT FOREIGN KEY(supOrderID) REFERENCES SupplierOrder (supOrderID)
                           ON UPDATE CASCADE ON DELETE CASCADE
);

create table if not exists User(
                                   Username varchar(30),
                                   Password varchar(30) NOT NULL ,
                                   Email varchar(50) unique not null,
                                   constraint primary key (Username)


);




