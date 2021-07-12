ALTER TABLE customer ALTER COLUMN memberno TYPE int USING memberno::int;
ALTER TABLE customer ALTER COLUMN monthlyincome TYPE numeric(10) USING monthlyincome::numeric;
ALTER TABLE customer ALTER COLUMN dob TYPE date USING dob::date;
ALTER TABLE customer ALTER COLUMN monthlyexpense TYPE numeric(10) USING monthlyexpense::numeric;
