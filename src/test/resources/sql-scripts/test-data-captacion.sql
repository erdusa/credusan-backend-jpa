DELETE FROM captacion;
DELETE FROM asociado;
DELETE FROM tipodocumento;
DELETE FROM tipocaptacion;
DELETE FROM tipoestadocaptacion;

insert into tipodocumento (tipdocid, tipdocabreviatura, tipdocdescripcion) values
    (1, 'RC', 'REGISTRO CIVIL'),
    (2, 'TI', 'TARJETA DE IDENTIDAD'),
    (3, 'CC', 'CEDULA DE CIUDADANIA');

INSERT INTO public.tipocaptacion (tipcapid, tipcapnombre) VALUES
    (1, 'APORTES'),
    (2, 'AHORROS');

INSERT INTO public.tipoestadocaptacion (tiescaid, tiescanombre) VALUES
    (1, 'ACTIVA'),
    (2, 'SALDADA');

