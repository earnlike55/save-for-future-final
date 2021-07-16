ALTER TABLE public.saving ADD customerid serial NOT NULL;
ALTER TABLE public.saving ADD CONSTRAINT saving_un UNIQUE (customerid);
ALTER TABLE public.saving ADD CONSTRAINT saving_fk FOREIGN KEY (customerid) REFERENCES public.customer(customerid);
