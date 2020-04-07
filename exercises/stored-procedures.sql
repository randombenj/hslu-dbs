-- Execute by running:
--  mysql -u uniuser -p"password" uni4 < exercises/stored-procedures.sql

-- Cleanup
DROP TABLE IF EXISTS weekends;
DROP PROCEDURE IF EXISTS mark_weekends;

-- Erstellen Sie auf Ihrer lokalen MySQL-Installation eine Tabelle:
CREATE TABLE IF NOT EXISTS weekends(datum date primary key, yay boolean);

-- Dann erstellen Sie eine Stored Procedure `f`:
DELIMITER $$

CREATE PROCEDURE mark_weekends (startdate date, nr int) BEGIN

  DECLARE i int DEFAULT 1;
  DECLARE next_day date DEFAULT startdate;

  WHILE (nr >= i) DO

    -- insert weekdays and mark them as weekends
    INSERT INTO weekends(datum, yay)
    VALUES (next_day, (dayofweek(next_day) in (7, 1)));

    SET i := i + 1;
    SET next_day := DATE_ADD(next_day, INTERVAL 1 DAY);

  END WHILE;
END$$

DELIMITER ;

-- Anschliessend können Sie die die Prozedur wie folgt aufrufen:
CALL mark_weekends('2020-11-11', 111);

SELECT * FROM weekends;
