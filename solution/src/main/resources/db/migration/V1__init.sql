CREATE TABLE expense (
  id     SERIAL,
  date   DATE         NOT NULL,
  amount DOUBLE       NOT NULL,
  reason VARCHAR(255) NOT NULL,

  PRIMARY KEY (id)
);