CREATE TABLE public."customer" (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
	"name" varchar NOT NULL,
	email varchar NOT NULL,
	phone varchar NOT NULL
);
