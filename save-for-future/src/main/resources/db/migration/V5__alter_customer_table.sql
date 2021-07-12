ALTER TABLE customer ADD CONSTRAINT customer_fk FOREIGN KEY (savingid) REFERENCES public.saving(savingid);
