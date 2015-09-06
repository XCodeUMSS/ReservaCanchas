
 -- PostgreSQL database dump
 --
 
 -- Dumped from database version 9.3.6
 -- Dumped by pg_dump version 9.3.6
 -- Started on 2015-08-12 10:23:32
 
 SET statement_timeout = 0;
 SET lock_timeout = 0;
 SET client_encoding = 'UTF8';
 SET standard_conforming_strings = on;
 SET check_function_bodies = false;
 SET client_min_messages = warning;
 
 DROP DATABASE "ReservaCanchasDB";
 --
 -- TOC entry 2008 (class 1262 OID 49491)
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
 -- TOC entry 2009 (class 0 OID 0)
 -- Dependencies: 6
 -- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
 --
 
 COMMENT ON SCHEMA public IS 'standard public schema';
 
 
 --
 -- TOC entry 182 (class 3079 OID 11750)
 -- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
 --
 
 CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
 
 
 --
 -- TOC entry 2011 (class 0 OID 0)
 -- Dependencies: 182
 -- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
 --
 
 COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
 
 
 SET search_path = public, pg_catalog;
 
 --
 -- TOC entry 170 (class 1259 OID 49492)
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
 -- TOC entry 171 (class 1259 OID 49494)
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
 -- TOC entry 172 (class 1259 OID 49501)
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
 -- TOC entry 173 (class 1259 OID 49503)
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
 -- TOC entry 2012 (class 0 OID 0)
 -- Dependencies: 173
 -- Name: COLUMN "HorarioAtencion"."Dia"; Type: COMMENT; Schema: public; Owner: postgres
 --
 
 COMMENT ON COLUMN "HorarioAtencion"."Dia" IS 'Podría ser:
 - LU
 - MA
 - MI
 - JU
 - VI
 - SA
 - DO';
 
 
 --
 -- TOC entry 174 (class 1259 OID 49507)
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
 -- TOC entry 175 (class 1259 OID 49509)
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
    "HoraExpiracion" time with time zone,
    "Confirmado" boolean DEFAULT true NOT NULL
);
 
 
 ALTER TABLE public."Reserva" OWNER TO postgres;
 
 --
 -- TOC entry 176 (class 1259 OID 49516)
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
 -- TOC entry 177 (class 1259 OID 49518)
 -- Name: TipoCancha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
 --
 
 CREATE TABLE "TipoCancha" (
     "IdTipoCancha" integer DEFAULT nextval('seqidtipocancha'::regclass) NOT NULL,
     "Nombre" character varying NOT NULL,
     "PrecioMinimo" smallint NOT NULL
 );
 
 
 ALTER TABLE public."TipoCancha" OWNER TO postgres;
 
 --
 -- TOC entry 180 (class 1259 OID 49571)
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
 -- TOC entry 181 (class 1259 OID 49573)
 -- Name: TipoEvento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
 --
 
 CREATE TABLE "TipoEvento" (
     "IdEvento" integer DEFAULT nextval('seqidtipoevento'::regclass) NOT NULL,
     "Nombre" character varying NOT NULL
 );
 
 
 ALTER TABLE public."TipoEvento" OWNER TO postgres;
 
--
-- TOC entry 182 (class 1259 OID 49525)
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
-- TOC entry 183 (class 1259 OID 49527)
-- Name: TipoRepeticion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoRepeticion" (
    "IdRepeticion" integer DEFAULT nextval('seqidtiporepeticion'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE public."TipoRepeticion" OWNER TO postgres;


 --
 -- TOC entry 178 (class 1259 OID 49525)
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
 -- TOC entry 179 (class 1259 OID 49527)
 -- Name: TipoSuelo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
 --
 
 CREATE TABLE "TipoSuelo" (
     "IdTipoSuelo" integer DEFAULT nextval('seqidtiposuelo'::regclass) NOT NULL,
     "Nombre" character varying NOT NULL
 );
 
 
 ALTER TABLE public."TipoSuelo" OWNER TO postgres;

--
 -- TOC entry 178 (class 1259 OID 49525)
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
-- TOC entry 189 (class 1259 OID 65693)
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
 -- TOC entry 178 (class 1259 OID 49525)
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
-- TOC entry 185 (class 1259 OID 65659)
-- Name: Rol; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Rol" (
    "IdRol" integer DEFAULT nextval('seqidrol'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE public."Rol" OWNER TO postgres;


--
 -- TOC entry 178 (class 1259 OID 49525)
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
-- TOC entry 187 (class 1259 OID 65668)
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
 -- TOC entry 1993 (class 0 OID 49494)
 -- Dependencies: 171
 -- Data for Name: CampoDeportivo; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 
 
 --
 -- TOC entry 1995 (class 0 OID 49503)
 -- Dependencies: 173
 -- Data for Name: HorarioAtencion; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 
 
 --
 -- TOC entry 1997 (class 0 OID 49509)
 -- Dependencies: 175
 -- Data for Name: Reserva; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 
 
 --
 -- TOC entry 1999 (class 0 OID 49518)
 -- Dependencies: 177
 -- Data for Name: TipoCancha; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (1, 'Tenis', 50);
 INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (2, 'Futbol de Salon', 50);
 INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (3, 'Futbol', 10);
 INSERT INTO "TipoCancha" ("IdTipoCancha", "Nombre", "PrecioMinimo") VALUES (4, 'Basquet', 10);
 
 
 --
 -- TOC entry 2003 (class 0 OID 49573)
 -- Dependencies: 181
 -- Data for Name: TipoEvento; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (1, 'Mantenimiento de Cancha');
 INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (2, 'Evento Especial');
 INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (3, 'Feriado');
 INSERT INTO "TipoEvento" ("IdEvento", "Nombre") VALUES (4, 'Paro');

--
 -- TOC entry 2003 (class 0 OID 49573)
 -- Dependencies: 181
 -- Data for Name: TipoRepeticion; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (1, 'Ninguno');
 INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (2, 'Diario');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (3, 'Semanal');
INSERT INTO "TipoRepeticion" ("IdRepeticion", "Nombre") VALUES (4, 'Mensual');
 

 --
 -- TOC entry 2001 (class 0 OID 49527)
 -- Dependencies: 179
 -- Data for Name: TipoSuelo; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (1, 'Cesped');
 INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (2, 'Pavimento');
 INSERT INTO "TipoSuelo" ("IdTipoSuelo", "Nombre") VALUES (3, 'Madera');

--
 -- TOC entry 2001 (class 0 OID 49527)
 -- Dependencies: 179
 -- Data for Name: Menu; Type: TABLE DATA; Schema: public; Owner: postgres
 --
 
 INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (1, 'Campos Deportivos', 'http://localhost/ReservaCanchas/index.php/ControladorCanchas/index', 1);

 INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (2, 'Realizar Reserva', 'http://localhost/ReservaCanchas/index.php/ControladorReserva/index', 1);

 INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (3, 'Realizar Reserva Especial', 'http://localhost/ReservaCanchas/index.php/ControladorReservaEspecial/index', 1);

INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (4, 'Confirmar Reservas', 'http://localhost/ReservaCanchas/index.php/ControladorConfirmarPrereserva/mostrarVistaConfirmacion', 1);

INSERT INTO "Menu" ("IdMenu", "Nombre", "Url", "IdRol") VALUES (5, 'Realizar Reserva', 'http://localhost/ReservaCanchas/index.php/ControladorPrereservas/mostrarDetallesCanchas', 2);

--
-- TOC entry 1994 (class 0 OID 65659)
-- Dependencies: 185
-- Data for Name: Rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "Rol" ("IdRol", "Nombre") VALUES (1, 'Administrador'); 

INSERT INTO "Rol" ("IdRol", "Nombre") VALUES (2, 'Cliente');

--
-- TOC entry 1995 (class 0 OID 65668)
-- Dependencies: 187
-- Data for Name: Usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO "Usuario" ("IdUsuario", "NombreUsuario", "Nombre", "Apellidos", "TelefonoReferencia", "CarnetIdentidad", "Rol") VALUES (1, 'Admi', 'XCode', 'Agil', 4472104, 123456, 1); 
 
 --
 -- TOC entry 2013 (class 0 OID 0)
 -- Dependencies: 170
 -- Name: seqidcampodeportivo; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidcampodeportivo', 0, true);
 
 
 --
 -- TOC entry 2014 (class 0 OID 0)
 -- Dependencies: 172
 -- Name: seqidhorarioatencion; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidhorarioatencion', 0, true);
 
 
 --
 -- TOC entry 2015 (class 0 OID 0)
 -- Dependencies: 174
 -- Name: seqidreserva; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidreserva', 0, true);
 
 
 --
 -- TOC entry 2016 (class 0 OID 0)
 -- Dependencies: 176
 -- Name: seqidtipocancha; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidtipocancha', 4, true);
 
 
 --
 -- TOC entry 2017 (class 0 OID 0)
 -- Dependencies: 180
 -- Name: seqidtipoevento; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidtipoevento', 4, true);

--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 182
-- Name: seqidtiporepeticion; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seqidtiporepeticion', 1, false);
 

 --
 -- TOC entry 2018 (class 0 OID 0)
 -- Dependencies: 178
 -- Name: seqidtiposuelo; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidtiposuelo', 3, true);
 
 --
 -- TOC entry 2018 (class 0 OID 0)
 -- Dependencies: 178
 -- Name: seqidmenu; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidmenu', 5, true);
 
 --
 -- TOC entry 2018 (class 0 OID 0)
 -- Dependencies: 178
 -- Name: seqidrol; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidrol', 2, true);
 
--
 -- TOC entry 2018 (class 0 OID 0)
 -- Dependencies: 178
 -- Name: seqidusuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
 --
 
 SELECT pg_catalog.setval('seqidusuario', 1, true);


 --
 -- TOC entry 1866 (class 2606 OID 49535)
 -- Name: IdCampoDeportivo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
 --
 
 ALTER TABLE ONLY "CampoDeportivo"
     ADD CONSTRAINT "IdCampoDeportivo" PRIMARY KEY ("IdCampoDeportivo");
 
 
 --
 -- TOC entry 1880 (class 2606 OID 49581)
 -- Name: IdEvento; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
 --
 
 ALTER TABLE ONLY "TipoEvento"
     ADD CONSTRAINT "IdEvento" PRIMARY KEY ("IdEvento");
 
 
 --
 -- TOC entry 1873 (class 2606 OID 49537)
 -- Name: IdReserva; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
 --
 
 ALTER TABLE ONLY "Reserva"
     ADD CONSTRAINT "IdReserva" PRIMARY KEY ("IdReserva");
 
 
 --
 -- TOC entry 1876 (class 2606 OID 49539)
 -- Name: IdTipoCancha; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
 --
 
 ALTER TABLE ONLY "TipoCancha"
     ADD CONSTRAINT "IdTipoCancha" PRIMARY KEY ("IdTipoCancha");
 
 
 --
 -- TOC entry 1878 (class 2606 OID 49541)
 -- Name: IdTipoSuelo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
 --
 
 ALTER TABLE ONLY "TipoSuelo"
     ADD CONSTRAINT "IdTipoSuelo" PRIMARY KEY ("IdTipoSuelo");
 
 
 --
 -- TOC entry 1871 (class 2606 OID 49543)
 -- Name: idHorario; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
 --
 
 ALTER TABLE ONLY "HorarioAtencion"
     ADD CONSTRAINT "idHorario" PRIMARY KEY ("IdHorario");
 
--
-- TOC entry 1880 (class 2606 OID 65676)
-- Name: idRol; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Rol"
    ADD CONSTRAINT "idRol" PRIMARY KEY ("IdRol");


--
-- TOC entry 1882 (class 2606 OID 65678)
-- Name: idUsuario; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Usuario"
    ADD CONSTRAINT "idUsuario" PRIMARY KEY ("IdUsuario");


--
-- TOC entry 1886 (class 2606 OID 65702)
-- Name: Menu-Rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Menu"
    ADD CONSTRAINT "Menu-Rol" FOREIGN KEY ("IdRol") REFERENCES "Rol"("IdRol") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1885 (class 2606 OID 65679)
-- Name: Usuario-Rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Usuario"
    ADD CONSTRAINT "Usuario-Rol" FOREIGN KEY ("Rol") REFERENCES "Rol"("IdRol");
 

 --
 -- TOC entry 1867 (class 1259 OID 49544)
 -- Name: fki_IDTipoSuelo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
 --
 
 CREATE INDEX "fki_IDTipoSuelo" ON "CampoDeportivo" USING btree ("IdTipoSuelo");
 
 
 --
 -- TOC entry 1869 (class 1259 OID 49545)
 -- Name: fki_IdCampoDeportivo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
 --
 
 CREATE INDEX "fki_IdCampoDeportivo" ON "HorarioAtencion" USING btree ("IdCampoDeportivo");
 
 
 --
 -- TOC entry 1868 (class 1259 OID 49546)
 -- Name: fki_IdTipoCancha; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
 --
 
 CREATE INDEX "fki_IdTipoCancha" ON "CampoDeportivo" USING btree ("IdTipoCancha");
 
 
 --
 -- TOC entry 1874 (class 1259 OID 49547)
 -- Name: fki_Reserva_CampoDeportivo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
 --
 
 CREATE INDEX "fki_Reserva_CampoDeportivo" ON "Reserva" USING btree ("IdCampoDeportivo");
 
 
 --
 -- TOC entry 1881 (class 2606 OID 49548)
 -- Name: CampoDeportivo-TipoCancha; Type: FK CONSTRAINT; Schema: public; Owner: postgres
 --
 
 ALTER TABLE ONLY "CampoDeportivo"
     ADD CONSTRAINT "CampoDeportivo-TipoCancha" FOREIGN KEY ("IdTipoCancha") REFERENCES "TipoCancha"("IdTipoCancha") ON UPDATE CASCADE ON DELETE RESTRICT;
 
 
 --
 -- TOC entry 1882 (class 2606 OID 49553)
 -- Name: CampoDeportivo-TipoSuelo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
 --
 
 ALTER TABLE ONLY "CampoDeportivo"
     ADD CONSTRAINT "CampoDeportivo-TipoSuelo" FOREIGN KEY ("IdTipoSuelo") REFERENCES "TipoSuelo"("IdTipoSuelo") ON UPDATE CASCADE ON DELETE RESTRICT;
 
 
 --
 -- TOC entry 1883 (class 2606 OID 49558)
 -- Name: HorarioAtencion-CampoDeportivo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
 --
 
 ALTER TABLE ONLY "HorarioAtencion"
     ADD CONSTRAINT "HorarioAtencion-CampoDeportivo" FOREIGN KEY ("IdCampoDeportivo") REFERENCES "CampoDeportivo"("IdCampoDeportivo");
 
 
 --
 -- TOC entry 1884 (class 2606 OID 49563)
 -- Name: Reserva-CampoDeportivo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
 --
 
 ALTER TABLE ONLY "Reserva"
     ADD CONSTRAINT "Reserva-CampoDeportivo" FOREIGN KEY ("IdCampoDeportivo") REFERENCES "CampoDeportivo"("IdCampoDeportivo");
 
 --
 -- TOC entry 2010 (class 0 OID 0)
 -- Dependencies: 6
 -- Name: public; Type: ACL; Schema: -; Owner: postgres
 --
 
 REVOKE ALL ON SCHEMA public FROM PUBLIC;
 REVOKE ALL ON SCHEMA public FROM postgres;
 GRANT ALL ON SCHEMA public TO postgres;
 GRANT ALL ON SCHEMA public TO PUBLIC;
 
 
 -- Completed on 2015-08-12 10:23:32
 
 --
 -- PostgreSQL database dump complete
 --