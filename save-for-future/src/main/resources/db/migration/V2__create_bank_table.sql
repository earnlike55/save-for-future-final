CREATE TABLE bank (
                             bankid serial NOT NULL,
                             customerid serial NOT NULL,
                             accounttype varchar(20) NOT NULL,
                             balance numeric NOT NULL,
                             interest numeric NOT NULL,
                             CONSTRAINT bank_pk PRIMARY KEY (bankid),
                             CONSTRAINT bank_un UNIQUE (customerid)
);