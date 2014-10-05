-- update times on insert and update
CREATE OR REPLACE FUNCTION update_times()
  RETURNS TRIGGER AS $$
BEGIN
  IF (TG_OP = 'INSERT')
  THEN
    NEW.CREATION_DATE := current_timestamp;
  ELSIF (TG_OP = 'UPDATE')
    THEN
      NEW.LAST_MODIFICATION_DATE := current_timestamp;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- users table
CREATE TABLE USERS (
  ID                      BIGSERIAL    NOT NULL,
  AUTHORITY               INT4         NOT NULL,
  CREATION_DATE           TIMESTAMP,
  CREDENTIALS_NON_EXPIRED BOOLEAN      NOT NULL,
  EMAIL                   VARCHAR(128),
  FIRST_NAME              VARCHAR(64),
  LAST_MODIFICATION_DATE  TIMESTAMP,
  LAST_NAME               VARCHAR(32),
  PASSWORD                VARCHAR(512),
  THEME                   VARCHAR(64)  NOT NULL,
  LANGUAGE                VARCHAR(8)   NOT NULL,
  STATUS                  INT4         NOT NULL,
  USERNAME                VARCHAR(128) NOT NULL,
  PRIMARY KEY (ID)
);
-- index on username
ALTER TABLE USERS ADD CONSTRAINT IDX1_USERS UNIQUE (USERNAME);

-- trigger on update times
CREATE TRIGGER TG1_USERS BEFORE INSERT OR UPDATE ON users FOR EACH ROW EXECUTE PROCEDURE update_times();

-- admin/admin
INSERT INTO USERS (AUTHORITY, CREDENTIALS_NON_EXPIRED, PASSWORD, STATUS, USERNAME, THEME, LANGUAGE)
VALUES (7, TRUE, 'a4a88c0872bf652bb9ed803ece5fd6e82354838a9bf59ab4babb1dab322154e1', 0, 'admin', 'default', 'en');
