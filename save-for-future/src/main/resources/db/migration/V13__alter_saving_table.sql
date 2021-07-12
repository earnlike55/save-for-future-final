ALTER TABLE public.saving ALTER COLUMN createdatetime TYPE timestamp(0) USING createdatetime::timestamp;
