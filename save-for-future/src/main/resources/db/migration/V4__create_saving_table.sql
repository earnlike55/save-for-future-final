CREATE TABLE saving (
                               savingid varchar NOT NULL,
                               customerid varchar NOT NULL,
                               suggestamt numeric NOT NULL,
                               monthlysave numeric NOT NULL,
                               remainamt numeric NOT NULL,
                               createdatetime date NOT NULL,
                               CONSTRAINT saving_pk PRIMARY KEY (savingid),
                               CONSTRAINT saving_un UNIQUE (customerid),
                               CONSTRAINT saving_fk FOREIGN KEY (customerid) REFERENCES public.customer(customerid)
)