--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.6
-- Dumped by pg_dump version 9.3.6
-- Started on 2015-09-03 23:16:00

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2007 (class 0 OID 17597)
-- Dependencies: 177
-- Data for Name: TipoCancha; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (1, 'Tenis', 50);
INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (2, 'Futbol de Salon', 50);
INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (3, 'Futbol', 10);
INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (4, 'Basquet', 10);


--
-- TOC entry 2013 (class 0 OID 17624)
-- Dependencies: 183
-- Data for Name: TipoSuelo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (1, 'Cesped');
INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (2, 'Pavimento');
INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (3, 'Madera');


--
-- TOC entry 2001 (class 0 OID 17572)
-- Dependencies: 171
-- Data for Name: CampoDeportivo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (1, 'El Balon', 20, './assets/img/El Balon.jpg', 4, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (2, 'FastBall', 20, './assets/img/FastBall.jpg', 4, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (3, 'Los Balones', 20, './assets/img/Los Balones.jpg', 4, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (4, 'Ositos', 20, './assets/img/Ositos.jpg', 4, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (5, 'Panchito', 20, './assets/img/Panchito.jpg', 4, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (6, 'Pentachampion', 20, './assets/img/Pentachampion.jpg', 4, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (7, 'Pies y Tacos', 20, './assets/img/Pies y Tacos.jpg', 4, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (8, 'Pollitos', 10, './assets/img/Pollitos.jpg', 4, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (9, 'Ronaldinho', 10, './assets/img/Ronaldinho.jpg', 4, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (10, 'Sierra Nevada', 10, './assets/img/Sierra Nevada.jpg', 4, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (11, 'SpeedBall', 10, './assets/img/SpeedBall.jpg', 4, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (12, 'UEFA', 10, './assets/img/UEFA.jpg', 4, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (13, 'Zinedine', 10, './assets/img/Zinedine.jpg', 4, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (14, 'Gol Presente', 15, './assets/img/Gol Presente.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (15, 'Goles de Fuego', 15, './assets/img/Goles de Fuego.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (16, 'Gran Penalty', 15, './assets/img/Gran Penalty.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (17, 'Hambre y Goles', 15, './assets/img/Hambre y Goles.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (18, 'Heidelbergen Sokker', 15, './assets/img/Heidelbergen Sokker.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (19, 'Joga Futebol', 15, './assets/img/Joga Futebol.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (20, 'Penalty', 15, './assets/img/Penalty.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (21, 'Porteria Grande', 10, './assets/img/Porteria Grande.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (22, 'Real Madrid Soccer', 10, './assets/img/Real Madrid Soccer.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (23, 'Soccer y Taco', 10, './assets/img/Soccer y Taco.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (24, 'SoccerFive', 10, './assets/img/SoccerFive.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (25, 'Soccer Match', 10, './assets/img/Soccer Match.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (26, 'Tiro de Esquina', 10, './assets/img/Tiro de Esquina.jpg', 3, 1);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (27, 'Balon de Oro', 55, './assets/img/Balon de Oro.jpg', 2, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (28, 'Campo Futbolero', 55, './assets/img/Campo Futbolero.jpg', 2, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (29, 'Diversoccer', 55, './assets/img/Diversoccer.jpg', 2, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (30, 'Eat and Run Soccer', 55, './assets/img/Eat and Run Soccer.jpg', 2, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (31, 'El Rey del Gol', 55, './assets/img/El Rey del Gol.jpg', 2, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (32, 'FIFA Soccer', 55, './assets/img/FIFA Soccer.jpg', 2, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (33, 'Firesoccer', 75, './assets/img/Firesoccer.jpg', 2, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (34, 'Futbol de a Varo', 75, './assets/img/Futbol de a Varo.jpg', 2, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (35, 'Futbol y Taco', 75, './assets/img/Futbol y Taco.jpg', 2, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (36, 'Futboleros Comelones', 75, './assets/img/Futboleros Comelones.jpg', 2, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (37, 'FutbolFive', 75, './assets/img/FutbolFive.jpg', 2, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (38, 'Futbolorama 2000', 75, './assets/img/Futbolorama 2000.jpg', 2, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (39, 'Gol de Oro', 75, './assets/img/Gol de Oro.jpg', 2, 3);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (40, 'America 4 Ever', 65, './assets/img/America 4 Ever.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (41, 'Balones Ponchados', 65, './assets/img/Balones Ponchados.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (42, 'Campo del Balon', 65, './assets/img/Campo del Balon.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (43, 'Contrapuntos y Balones', 65, './assets/img/Contrapuntos y Balones.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (44, 'Diverball', 65, './assets/img/Diverball.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (45, 'El balonsito', 50, './assets/img/El balonsito.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (46, 'El rinconsito del balon', 50, './assets/img/El rinconsito del balon.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (47, 'Fireball', 50, './assets/img/Fireball.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (48, 'General Zaragoza', 55, './assets/img/General Zaragoza.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (49, 'Joga Bonito', 55, './assets/img/Joga Bonito.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (50, 'Joga Feito', 55, './assets/img/Joga Feito.jpg', 1, 2);
INSERT INTO "CampoDeportivo" ("IdCampoDeportivo", "Nombre", "PrecioPorHora", "RutaFoto", "IdTipoCancha", "IdTipoSuelo") VALUES (51, 'Miguel Hidalgo y Costilla', 70, './assets/img/Miguel Hidalgo y Costilla.jpg', 1, 2);


--
-- TOC entry 2003 (class 0 OID 17581)
-- Dependencies: 173
-- Data for Name: HorarioAtencion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (1, '07:00:00', '22:00:00', 'TS', 1);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (2, '07:00:00', '22:00:00', 'TS', 2);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (3, '07:00:00', '22:00:00', 'TS', 3);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (4, '07:00:00', '22:00:00', 'TS', 4);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (5, '07:00:00', '22:00:00', 'TS', 5);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (6, '07:00:00', '22:00:00', 'TS', 6);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (7, '07:00:00', '22:00:00', 'TS', 7);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (8, '08:00:00', '22:00:00', 'TS', 8);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (9, '08:00:00', '22:00:00', 'TS', 9);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (10, '08:00:00', '22:00:00', 'TS', 10);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (11, '08:00:00', '22:00:00', 'TS', 11);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (12, '08:00:00', '22:00:00', 'TS', 12);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (13, '08:00:00', '22:00:00', 'TS', 13);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (14, '08:00:00', '18:00:00', 'TS', 14);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (15, '08:00:00', '18:00:00', 'TS', 15);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (16, '08:00:00', '18:00:00', 'TS', 16);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (17, '08:00:00', '18:00:00', 'TS', 17);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (18, '08:00:00', '18:00:00', 'TS', 18);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (19, '08:00:00', '18:00:00', 'TS', 19);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (20, '08:00:00', '18:00:00', 'TS', 20);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (21, '08:00:00', '18:00:00', 'TS', 21);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (22, '08:00:00', '18:00:00', 'TS', 22);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (23, '08:00:00', '18:00:00', 'TS', 23);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (24, '08:00:00', '18:00:00', 'TS', 24);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (25, '08:00:00', '18:00:00', 'TS', 25);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (26, '08:00:00', '18:00:00', 'TS', 26);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (27, '08:00:00', '22:00:00', 'TS', 27);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (28, '08:00:00', '22:00:00', 'TS', 28);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (29, '08:00:00', '22:00:00', 'TS', 29);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (30, '08:00:00', '22:00:00', 'TS', 30);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (31, '08:00:00', '22:00:00', 'TS', 31);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (32, '08:00:00', '22:00:00', 'TS', 32);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (33, '08:00:00', '22:00:00', 'TS', 33);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (34, '08:00:00', '22:00:00', 'TS', 34);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (35, '08:00:00', '22:00:00', 'TS', 35);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (36, '08:00:00', '22:00:00', 'TS', 36);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (37, '08:00:00', '22:00:00', 'TS', 37);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (38, '08:00:00', '22:00:00', 'TS', 38);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (39, '08:00:00', '22:00:00', 'TS', 39);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (40, '09:00:00', '18:00:00', 'TS', 40);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (41, '09:00:00', '18:00:00', 'TS', 41);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (42, '09:00:00', '18:00:00', 'TS', 42);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (43, '09:00:00', '18:00:00', 'TS', 43);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (44, '09:00:00', '18:00:00', 'TS', 44);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (45, '09:00:00', '18:00:00', 'TS', 45);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (46, '09:00:00', '18:00:00', 'TS', 46);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (47, '09:00:00', '18:00:00', 'TS', 47);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (48, '09:00:00', '18:00:00', 'TS', 48);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (49, '09:00:00', '18:00:00', 'TS', 49);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (50, '09:00:00', '18:00:00', 'TS', 50);
INSERT INTO "HorarioAtencion" ("IdHorario", "HoraInicio", "HoraFin", "Dia", "IdCampoDeportivo") VALUES (51, '09:00:00', '18:00:00', 'TS', 51);


--
-- TOC entry 2005 (class 0 OID 17587)
-- Dependencies: 175
-- Data for Name: Reserva; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2009 (class 0 OID 17606)
-- Dependencies: 179
-- Data for Name: TipoEvento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (1, 'Mantenimiento de Cancha');
INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (2, 'Evento Especial');
INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (3, 'Feriado');
INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (4, 'Paro');


--
-- TOC entry 2011 (class 0 OID 17615)
-- Dependencies: 181
-- Data for Name: TipoRepeticion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (1, 'Ninguno');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (2, 'Diario');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (3, 'Semanal');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (4, 'Mensual');


--
-- TOC entry 2018 (class 0 OID 0)
-- Dependencies: 170
-- Name: seqidcampodeportivo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidcampodeportivo', 51, true);


--
-- TOC entry 2019 (class 0 OID 0)
-- Dependencies: 172
-- Name: seqidhorarioatencion; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidhorarioatencion', 51, true);


--
-- TOC entry 2020 (class 0 OID 0)
-- Dependencies: 174
-- Name: seqidreserva; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidreserva', 0, true);


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 176
-- Name: seqidtipocancha; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtipocancha', 4, true);


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 178
-- Name: seqidtipoevento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtipoevento', 4, true);


--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 180
-- Name: seqidtiporepeticion; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtiporepeticion', 1, false);


--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 182
-- Name: seqidtiposuelo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtiposuelo', 3, true);


-- Completed on 2015-09-03 23:16:01

--
-- PostgreSQL database dump complete
--

