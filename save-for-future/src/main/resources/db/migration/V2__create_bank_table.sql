CREATE TABLE bank (
                             bankid varchar(30) NOT NULL,
                             customerid varchar(30) NOT NULL,
                             accounttype varchar(20) NOT NULL,
                             balance numeric NOT NULL,
                             interest numeric NOT NULL,
                             CONSTRAINT bank_pk PRIMARY KEY (bankid),
                             CONSTRAINT bank_un UNIQUE (customerid)
);