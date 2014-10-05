-- barcode generator
CREATE FUNCTION GET_BARCODE(CODE VARCHAR, LANGUAGE VARCHAR, LOT VARCHAR, SERIAL_NUMBER VARCHAR)
  RETURNS VARCHAR AS $$
SELECT
  CODE || LANGUAGE || '00' || LOT || SERIAL_NUMBER;
$$ LANGUAGE SQL;

-- unit stickers for print (by external program)
CREATE OR REPLACE VIEW V_UNIT_STICKERS(ID, DESIGNATION, LOT, SERIAL_NUMBER, BARCODE) AS
  SELECT
    U.ID                                                    AS ID,
    U.DESIGNATION                                           AS DESIGNATION,
    U.LOT                                                   AS LOT,
    U.SERIAL_NUMBER                                         AS SERIAL_NUMBER,
    GET_BARCODE(U.CODE, U.LANGUAGE, U.LOT, U.SERIAL_NUMBER) AS BARCODE
  FROM UNITS U
  WHERE U.STICKER_STATUS = 1;