CREATE TABLE customer (
                                 customerid varchar(30) NOT NULL,
                                 email varchar(30) NOT NULL,
                                 dob date NOT NULL,
                                 age int NOT NULL,
                                 memberno int NOT NULL,
                                 monthlyincome int NOT NULL,
                                 monthlyexpense int NOT NULL,
                                 tax int NOT NULL,
                                 ageofretirement int NOT NULL,
                                 "password" varchar(100) NOT NULL,
                                 gender varchar(10) NOT NULL,
                                 savingid varchar NOT NULL,
                                 CONSTRAINT customer_pk PRIMARY KEY (customerid),
                                 CONSTRAINT customer_un UNIQUE (email)
);
