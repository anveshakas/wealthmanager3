CREATE TABLE `Customer_Table` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`Customer_ID` varchar(20) NOT NULL,
	`Transaction_DateTime` DATETIME NOT NULL,
	`Merchant_Details` varchar(300) NOT NULL,
	`Debit` DECIMAL NOT NULL,
	`Credit` DECIMAL NOT NULL,
	`Balance` DECIMAL NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `Score_Table` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`Customer_ID` varchar(20) NOT NULL,
	`Type_of_Expence` varchar(50) NOT NULL,
	`Month` varchar(20) NOT NULL,
	`Score` INT NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `Score_Reference` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`Type_of_Expence` varchar(50) NOT NULL,
	`Start_Percentage` INT NOT NULL,
	`End_Percentage` INT NOT NULL,
	`Score` INT NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `Loan_Table` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`Customer_ID` varchar(20) NOT NULL,
	`Pending_EMI` INT NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `Loan_Table copy` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`Score` INT NOT NULL,
	`Investment_Options` varchar(500) NOT NULL,
	PRIMARY KEY (`ID`)
);

ALTER TABLE `Score_Table` ADD CONSTRAINT `Score_Table_fk0` FOREIGN KEY (`Customer_ID`) REFERENCES `Customer_Table`(`Customer_ID`);

ALTER TABLE `Score_Reference` ADD CONSTRAINT `Score_Reference_fk0` FOREIGN KEY (`Type_of_Expence`) REFERENCES `Score_Table`(`Type_of_Expence`);

ALTER TABLE `Loan_Table` ADD CONSTRAINT `Loan_Table_fk0` FOREIGN KEY (`Customer_ID`) REFERENCES `Customer_Table`(`Transaction_DateTime`);

