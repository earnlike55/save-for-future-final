ALTER TABLE bank ADD CONSTRAINT bank_fk FOREIGN KEY (customerid) REFERENCES public.customer(customerid);
