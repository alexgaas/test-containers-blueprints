CREATE TABLE BLUEPRINT (
  `ID` varchar(255) NOT NULL PRIMARY KEY,
  `NAME` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO BLUEPRINT (ID, NAME)
VALUES
  ('1', 'Name 1'),
  ('2', 'Name 2'),
  ('3', 'Name 3'),
  ('4', 'Name 4');
