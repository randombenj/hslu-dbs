DROP PROCEDURE IF EXISTS median_semester;

DELIMITER $$

CREATE PROCEDURE median_semester () BEGIN

  SELECT AVG(dd.Semester) as median_semester
  FROM (
    SELECT s.Semester, @rownum:=@rownum+1 as row_number, @total_rows:=@rownum
      FROM Studenten s, (SELECT @rownum:=0) r
      WHERE s.Semester is NOT NULL
      -- put some where clause here
      ORDER BY s.Semester
  ) as dd
  WHERE dd.row_number IN ( FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2) );

END$$

DELIMITER ;

CALL median_semester();