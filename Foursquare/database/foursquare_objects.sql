USE foursquare;

DROP VIEW IF EXISTS lugares;

CREATE VIEW lugares AS
SELECT calleTransversal, codigoPostal, parroquia_nom parroquia, ciudad_nom ciudad, pais_nom pais
FROM ubicacion JOIN parroquia USING (id_parroquia) JOIN ciudad USING (id_ciudad) JOIN pais USING (id_pais);

DROP PROCEDURE IF EXISTS research;

DELIMITER ?
CREATE PROCEDURE research(IN busqueda TEXT)
BEGIN
SET busqueda = CONCAT('%',busqueda,'%');

IF (SELECT COUNT(*) FROM Tipo WHERE nombre LIKE busqueda) > 0 THEN
	SELECT nombre FROM Tipo WHERE nombre LIKE busqueda;
END IF;

IF (SELECT COUNT(*) FROM lugares WHERE calleTransversal LIKE busqueda OR codigoPostal LIKE busqueda
    OR parroquia LIKE busqueda OR ciudad LIKE busqueda OR pais LIKE busqueda) > 0 THEN
    SELECT * FROM lugares 
    WHERE calleTransversal LIKE busqueda
    OR codigoPostal LIKE busqueda
    OR parroquia LIKE busqueda
    OR ciudad LIKE busqueda
    OR pais LIKE busqueda;
END IF;

IF (SELECT COUNT(parroquia_nom) FROM parroquia WHERE parroquia_nom LIKE busqueda) > 0 THEN
    SELECT parroquia_nom FROM parroquia WHERE parroquia_nom LIKE busqueda;
END IF;

IF (SELECT COUNT(ciudad_nom) FROM ciudad WHERE ciudad_nom LIKE busqueda) > 0 THEN
    SELECT ciudad_nom FROM ciudad WHERE ciudad_nom LIKE busqueda;
END IF;

IF (SELECT COUNT(pais_nom) FROM pais WHERE pais_nom LIKE busqueda) > 0 THEN
    SELECT pais_nom FROM pais WHERE pais_nom LIKE busqueda;
END IF;

END ?

DELIMITER ;