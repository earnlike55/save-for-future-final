ALTER TABLE saving DROP CONSTRAINT saving_pk;
ALTER TABLE saving ALTER COLUMN savingid TYPE int USING savingid::int;
ALTER TABLE saving ALTER COLUMN customerid TYPE int USING customerid::int;
