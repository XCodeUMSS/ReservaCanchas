--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.6
-- Dumped by pg_dump version 9.3.6
-- Started on 2015-09-11 10:02:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE "ReservaCanchasDB";
--
-- TOC entry 2059 (class 1262 OID 42239)
-- Name: ReservaCanchasDB; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "ReservaCanchasDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Bolivia.1252' LC_CTYPE = 'Spanish_Bolivia.1252';


ALTER DATABASE "ReservaCanchasDB" OWNER TO postgres;

\connect "ReservaCanchasDB"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2060 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 190 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2062 (class 0 OID 0)
-- Dependencies: 190
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 170 (class 1259 OID 42240)
-- Name: seqidcampodeportivo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidcampodeportivo
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidcampodeportivo OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 42242)
-- Name: CampoDeportivo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "CampoDeportivo" (
    "IdCampoDeportivo" integer DEFAULT nextval('seqidcampodeportivo'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL,
    "PrecioPorHora" smallint NOT NULL,
    "RutaFoto" character varying,
    "IdTipoCancha" integer NOT NULL,
    "IdTipoSuelo" integer NOT NULL
);


ALTER TABLE public."CampoDeportivo" OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 42249)
-- Name: seqidhorarioatencion; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidhorarioatencion
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidhorarioatencion OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 42251)
-- Name: HorarioAtencion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "HorarioAtencion" (
    "IdHorario" integer DEFAULT nextval('seqidhorarioatencion'::regclass) NOT NULL,
    "HoraInicio" time without time zone NOT NULL,
    "HoraFin" time without time zone NOT NULL,
    "Dia" character varying(2) NOT NULL,
    "IdCampoDeportivo" integer NOT NULL
);
ALTER TABLE ONLY "HorarioAtencion" ALTER COLUMN "Dia" SET STORAGE PLAIN;


ALTER TABLE public."HorarioAtencion" OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 42302)
-- Name: seqidmenu; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidmenu
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidmenu OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 42304)
-- Name: Menu; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Menu" (
    "IdMenu" integer DEFAULT nextval('seqidmenu'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL,
    "Url" character varying NOT NULL,
    "IdRol" integer NOT NULL
);


ALTER TABLE public."Menu" OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 42255)
-- Name: seqidreserva; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidreserva
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidreserva OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 42257)
-- Name: Reserva; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Reserva" (
    "IdReserva" integer DEFAULT nextval('seqidreserva'::regclass) NOT NULL,
    "Fecha" date NOT NULL,
    "IdCampoDeportivo" integer NOT NULL,
    "HoraInicio" time without time zone NOT NULL,
    "HoraFin" time without time zone NOT NULL,
    "Precio" integer NOT NULL,
    "NombreCliente" character varying NOT NULL,
    "TelefonoReferencia" integer NOT NULL,
    "ReservaEspecial" boolean DEFAULT false NOT NULL,
    "FechaExpiracion" date,
    "HoraExpiracion" time without time zone,
    "Confirmado" boolean DEFAULT true NOT NULL
);


ALTER TABLE public."Reserva" OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 42311)
-- Name: seqidrol; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidrol
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidrol OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 42313)
-- Name: Rol; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Rol" (
    "IdRol" integer DEFAULT nextval('seqidrol'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE public."Rol" OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 42266)
-- Name: seqidtipocancha; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidtipocancha
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidtipocancha OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 42268)
-- Name: TipoCancha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoCancha" (
    "IdTipoCancha" integer DEFAULT nextval('seqidtipocancha'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL,
    "PrecioMinimo" smallint NOT NULL
);


ALTER TABLE public."TipoCancha" OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 42275)
-- Name: seqidtipoevento; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidtipoevento
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidtipoevento OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 42277)
-- Name: TipoEvento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoEvento" (
    "IdEvento" integer DEFAULT nextval('seqidtipoevento'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE public."TipoEvento" OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 42284)
-- Name: seqidtiporepeticion; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidtiporepeticion
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidtiporepeticion OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 42286)
-- Name: TipoRepeticion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoRepeticion" (
    "IdRepeticion" integer DEFAULT nextval('seqidtiporepeticion'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE public."TipoRepeticion" OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 42293)
-- Name: seqidtiposuelo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidtiposuelo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidtiposuelo OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 42295)
-- Name: TipoSuelo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoSuelo" (
    "IdTipoSuelo" integer DEFAULT nextval('seqidtiposuelo'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE public."TipoSuelo" OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 42320)
-- Name: seqidusuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidusuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidusuario OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 42322)
-- Name: Usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Usuario" (
    "IdUsuario" integer DEFAULT nextval('seqidusuario'::regclass) NOT NULL,
    "NombreUsuario" character varying NOT NULL,
    "Nombre" character varying NOT NULL,
    "Apellidos" character varying NOT NULL,
    "TelefonoReferencia" integer NOT NULL,
    "CarnetIdentidad" integer NOT NULL,
    "Rol" integer NOT NULL
);


ALTER TABLE public."Usuario" OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 42320)
-- Name: seqidnotificacion; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seqidnotificacion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seqidnotificacion OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 42322)
-- Name: Notificacion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Notificacion" (
    "IdNotificacion" integer DEFAULT nextval('seqidnotificacion'::regclass) NOT NULL,
    "Visto" boolean NOT NULL,
    "IdReserva" integer NOT NULL
);


ALTER TABLE public."Notificacion" OWNER TO postgres;

CREATE SEQUENCE seqidrecibo
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.seqidrecibo OWNER TO postgres;

CREATE TABLE "Recibo" (
    "IdRecibo" integer DEFAULT nextval('seqidrecibo'::regclass) NOT NULL,
    "Fecha" date NOT NULL,
    "Precio" integer NOT NULL,
    "CantidadReserva" integer NOT NULL,
    "IdReserva" integer NOT NULL,
    "Administrador" character varying NOT NULL
);


ALTER TABLE public."Recibo" OWNER TO postgres;

ALTER TABLE ONLY "Recibo"
    ADD CONSTRAINT "idRecibo" PRIMARY KEY ("IdRecibo");

    ALTER TABLE ONLY "Recibo"
    ADD CONSTRAINT "Recibo-Reserva" FOREIGN KEY ("IdReserva") REFERENCES "Reserva"("IdReserva") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2050 (class 0 OID 42304)
-- Dependencies: 185
-- Data for Name: Menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (1, 'Campos Deportivos', 'http://localhost/ReservaCanchas/index.php/ControladorCanchas/index', 1);
INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (2, 'Realizar Reserva', 'http://localhost/ReservaCanchas/index.php/ControladorReserva/index', 1);
INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (3, 'Realizar Reserva Especial', 'http://localhost/ReservaCanchas/index.php/ControladorReservaEspecial/index', 1);
INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (4, 'Confirmar Reservas', 'http://localhost/ReservaCanchas/index.php/ControladorConfirmarPrereserva/mostrarVistaConfirmacion', 1);
INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (5, 'Realizar Reserva', 'http://localhost/ReservaCanchas/index.php/ControladorPrereservas/mostrarDetallesCanchas', 2);
INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (6, 'Registrar Administrador', 'http://localhost/ReservaCanchas/index.php/ControladorAdministradores/index', 1);


--
-- TOC entry 2040 (class 0 OID 42257)
-- Dependencies: 175
-- Data for Name: Reserva; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2052 (class 0 OID 42313)
-- Dependencies: 187
-- Data for Name: Rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "Rol" ("IdRol", "Nombre") VALUES (1, 'Administrador');
INSERT INTO "Rol" ("IdRol", "Nombre") VALUES (2, 'Cliente');


--
-- TOC entry 2042 (class 0 OID 42268)
-- Dependencies: 177
-- Data for Name: TipoCancha; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (1, 'Tenis', 50);
INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (2, 'Futbol de Salon', 50);
INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (3, 'Futbol', 10);
INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (4, 'Basquet', 10);


--
-- TOC entry 2044 (class 0 OID 42277)
-- Dependencies: 179
-- Data for Name: TipoEvento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (1, 'Mantenimiento de Cancha');
INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (2, 'Evento Especial');
INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (3, 'Feriado');
INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (4, 'Paro');


--
-- TOC entry 2046 (class 0 OID 42286)
-- Dependencies: 181
-- Data for Name: TipoRepeticion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (1, 'Ninguno');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (2, 'Diario');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (3, 'Semanal');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (4, 'Mensual');


--
-- TOC entry 2048 (class 0 OID 42295)
-- Dependencies: 183
-- Data for Name: TipoSuelo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (1, 'Cesped');
INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (2, 'Pavimento');
INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (3, 'Madera');


--
-- TOC entry 2054 (class 0 OID 42322)
-- Dependencies: 189
-- Data for Name: Usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "Usuario" ("IdUsuario", "NombreUsuario", "Nombre", "Apellidos", "TelefonoReferencia", "CarnetIdentidad", "Rol") VALUES (1, 'Admi', 'XCode', 'Agil', 4472104, 123456, 1);


--
-- TOC entry 2063 (class 0 OID 0)
-- Dependencies: 170
-- Name: seqidcampodeportivo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidcampodeportivo', 51, true);


--
-- TOC entry 2064 (class 0 OID 0)
-- Dependencies: 172
-- Name: seqidhorarioatencion; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidhorarioatencion', 51, true);


--
-- TOC entry 2065 (class 0 OID 0)
-- Dependencies: 184
-- Name: seqidmenu; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidmenu', 5, true);


--
-- TOC entry 2066 (class 0 OID 0)
-- Dependencies: 174
-- Name: seqidreserva; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidreserva', 0, true);


--
-- TOC entry 2067 (class 0 OID 0)
-- Dependencies: 186
-- Name: seqidrol; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidrol', 2, true);


--
-- TOC entry 2068 (class 0 OID 0)
-- Dependencies: 176
-- Name: seqidtipocancha; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtipocancha', 4, true);


--
-- TOC entry 2069 (class 0 OID 0)
-- Dependencies: 178
-- Name: seqidtipoevento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtipoevento', 4, true);


--
-- TOC entry 2070 (class 0 OID 0)
-- Dependencies: 180
-- Name: seqidtiporepeticion; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtiporepeticion', 1, false);


--
-- TOC entry 2071 (class 0 OID 0)
-- Dependencies: 182
-- Name: seqidtiposuelo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtiposuelo', 3, true);


--
-- TOC entry 2072 (class 0 OID 0)
-- Dependencies: 188
-- Name: seqidusuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidusuario', 1, true);

--
-- TOC entry 2072 (class 0 OID 0)
-- Dependencies: 188
-- Name: seqidnotificacion; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidnotificacion', 1, true);


--
-- TOC entry 1899 (class 2606 OID 42330)
-- Name: IdCampoDeportivo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "IdCampoDeportivo" PRIMARY KEY ("IdCampoDeportivo");


--
-- TOC entry 1911 (class 2606 OID 42332)
-- Name: IdEvento; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TipoEvento"
    ADD CONSTRAINT "IdEvento" PRIMARY KEY ("IdEvento");


--
-- TOC entry 1917 (class 2606 OID 42336)
-- Name: IdMenu; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Menu"
    ADD CONSTRAINT "IdMenu" PRIMARY KEY ("IdMenu");


--
-- TOC entry 1913 (class 2606 OID 42334)
-- Name: IdRepeticion; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TipoRepeticion"
    ADD CONSTRAINT "IdRepeticion" PRIMARY KEY ("IdRepeticion");


--
-- TOC entry 1906 (class 2606 OID 42338)
-- Name: IdReserva; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Reserva"
    ADD CONSTRAINT "IdReserva" PRIMARY KEY ("IdReserva");


--
-- TOC entry 1909 (class 2606 OID 42340)
-- Name: IdTipoCancha; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TipoCancha"
    ADD CONSTRAINT "IdTipoCancha" PRIMARY KEY ("IdTipoCancha");


--
-- TOC entry 1915 (class 2606 OID 42342)
-- Name: IdTipoSuelo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TipoSuelo"
    ADD CONSTRAINT "IdTipoSuelo" PRIMARY KEY ("IdTipoSuelo");


--
-- TOC entry 1904 (class 2606 OID 42344)
-- Name: idHorario; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "HorarioAtencion"
    ADD CONSTRAINT "idHorario" PRIMARY KEY ("IdHorario");


--
-- TOC entry 1919 (class 2606 OID 42346)
-- Name: idRol; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Rol"
    ADD CONSTRAINT "idRol" PRIMARY KEY ("IdRol");


--
-- TOC entry 1921 (class 2606 OID 42348)
-- Name: idUsuario; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Usuario"
    ADD CONSTRAINT "idUsuario" PRIMARY KEY ("IdUsuario");


-- TOC entry 1921 (class 2606 OID 42348)
-- Name: idNotificacion; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Notificacion"
    ADD CONSTRAINT "idNotificacion" PRIMARY KEY ("IdNotificacion");

--
-- TOC entry 1900 (class 1259 OID 42359)
-- Name: fki_IDTipoSuelo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IDTipoSuelo" ON "CampoDeportivo" USING btree ("IdTipoSuelo");


--
-- TOC entry 1902 (class 1259 OID 42360)
-- Name: fki_IdCampoDeportivo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IdCampoDeportivo" ON "HorarioAtencion" USING btree ("IdCampoDeportivo");


--
-- TOC entry 1901 (class 1259 OID 42361)
-- Name: fki_IdTipoCancha; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IdTipoCancha" ON "CampoDeportivo" USING btree ("IdTipoCancha");


--
-- TOC entry 1907 (class 1259 OID 42362)
-- Name: fki_Reserva_CampoDeportivo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_Reserva_CampoDeportivo" ON "Reserva" USING btree ("IdCampoDeportivo");


--
-- TOC entry 1922 (class 2606 OID 42363)
-- Name: CampoDeportivo-TipoCancha; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "CampoDeportivo-TipoCancha" FOREIGN KEY ("IdTipoCancha") REFERENCES "TipoCancha"("IdTipoCancha") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1923 (class 2606 OID 42368)
-- Name: CampoDeportivo-TipoSuelo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "CampoDeportivo-TipoSuelo" FOREIGN KEY ("IdTipoSuelo") REFERENCES "TipoSuelo"("IdTipoSuelo") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1924 (class 2606 OID 42373)
-- Name: HorarioAtencion-CampoDeportivo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "HorarioAtencion"
    ADD CONSTRAINT "HorarioAtencion-CampoDeportivo" FOREIGN KEY ("IdCampoDeportivo") REFERENCES "CampoDeportivo"("IdCampoDeportivo");


--
-- TOC entry 1926 (class 2606 OID 42349)
-- Name: Menu-Rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Menu"
    ADD CONSTRAINT "Menu-Rol" FOREIGN KEY ("IdRol") REFERENCES "Rol"("IdRol") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1925 (class 2606 OID 42378)
-- Name: Reserva-CampoDeportivo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Reserva"
    ADD CONSTRAINT "Reserva-CampoDeportivo" FOREIGN KEY ("IdCampoDeportivo") REFERENCES "CampoDeportivo"("IdCampoDeportivo");


--
-- TOC entry 1927 (class 2606 OID 42354)
-- Name: Usuario-Rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Usuario"
    ADD CONSTRAINT "Usuario-Rol" FOREIGN KEY ("Rol") REFERENCES "Rol"("IdRol");

--
-- TOC entry 1927 (class 2606 OID 42354)
-- Name: Notificacion-Reserva; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Notificacion"
    ADD CONSTRAINT "Notificacion-Reserva" FOREIGN KEY ("IdReserva") REFERENCES "Reserva"("IdReserva") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2061 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-09-11 10:02:16

--
-- PostgreSQL database dump complete
--