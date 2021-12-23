DELIMITER $$

CREATE FUNCTION `ExtractNumber`(in_string VARCHAR(50))
    RETURNS INT
    NO SQL
BEGIN
    DECLARE ctrNumber VARCHAR(50);
    DECLARE finNumber VARCHAR(50) DEFAULT '';
    DECLARE sChar VARCHAR(1);
    DECLARE inti INTEGER DEFAULT 1;

    IF LENGTH(in_string) > 0 THEN
        WHILE(inti <= LENGTH(in_string)) DO
            SET sChar = SUBSTRING(in_string, inti, 1);
            SET ctrNumber = FIND_IN_SET(sChar, '0,1,2,3,4,5,6,7,8,9');
            IF ctrNumber > 0 THEN
                SET finNumber = CONCAT(finNumber, sChar);
END IF;
            SET inti = inti + 1;
END WHILE;
RETURN CAST(finNumber AS UNSIGNED);
ELSE
        RETURN 0;
END IF;
END$$

DELIMITER ;





DELIMITER $$

CREATE FUNCTION find_num_str(search_word VARCHAR(255),find_char VARCHAR(255))
    RETURNS VARCHAR(255)
    NOT DETERMINISTIC
    CONTAINS SQL
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE s INT DEFAULT 0;
SELECT  LOCATE(search_word,find_char) INTO s;
REPEAT
SET i = i+1;
	        UNTIL (SUBSTR(find_char,s+i,1)=',' OR SUBSTR(find_char,s+i,1)='分' OR i>= LENGTH(find_char))
END REPEAT;
RETURN  (SELECT SUBSTR(find_char,s,i));

END$$

DELIMITER ;