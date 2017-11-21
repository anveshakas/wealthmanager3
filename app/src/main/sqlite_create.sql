CREATE TABLE Customer_Table (
	ID integer PRIMARY KEY AUTOINCREMENT,
	Customer_ID varchar,
	Transaction_DateTime datetime,
	Merchant_Details varchar,
	Debit decimal,
	Credit decimal,
	Balance decimal
);

CREATE TABLE Score_Table (
	ID integer PRIMARY KEY AUTOINCREMENT,
	Customer_ID varchar,
	Type_of_Expence varchar,
	Month varchar,
	Score integer
);

CREATE TABLE Score_Reference (
	ID integer PRIMARY KEY AUTOINCREMENT,
	Type_of_Expence varchar,
	Start_Percentage integer,
	End_Percentage integer,
	Score integer
);

CREATE TABLE Loan_Table (
	ID integer PRIMARY KEY AUTOINCREMENT,
	Customer_ID varchar,
	Pending_EMI integer
);

CREATE TABLE "Loan_Table copy" (
	ID integer PRIMARY KEY AUTOINCREMENT,
	Score integer,
	Investment_Options varchar
);

