--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.6
-- Dumped by pg_dump version 9.4.0
-- Started on 2015-04-29 10:36:25

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE "ReservaCanchasDB";
--
-- TOC entry 1957 (class 1262 OID 24576)
-- Name: ReservaCanchasDB; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "ReservaCanchasDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Bolivia.1252' LC_CTYPE = 'Spanish_Bolivia.1252';


ALTER DATABASE "ReservaCanchasDB" OWNER TO "postgres";

\connect "ReservaCanchasDB"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA "public";


ALTER SCHEMA "public" OWNER TO "postgres";

--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA "public"; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA "public" IS 'standard public schema';


--
-- TOC entry 174 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "plpgsql" WITH SCHEMA "pg_catalog";


--
-- TOC entry 1960 (class 0 OID 0)
-- Dependencies: 174
-- Name: EXTENSION "plpgsql"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "plpgsql" IS 'PL/pgSQL procedural language';


SET search_path = "public", pg_catalog;

--
-- TOC entry 171 (class 1259 OID 32771)
-- Name: id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "id"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "id" OWNER TO "postgres";

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 32785)
-- Name: CampoDeportivo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "CampoDeportivo" (
    "IdCampoDeportivo" integer DEFAULT "nextval"('"id"'::"regclass") NOT NULL,
    "Nombre" character varying NOT NULL,
    "PrecioMinimo" "money" NOT NULL,
    "Foto" "bytea",
    "IdTipoCancha" integer NOT NULL
);


ALTER TABLE "CampoDeportivo" OWNER TO "postgres";

--
-- TOC entry 170 (class 1259 OID 32768)
-- Name: HorarioAtencion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "HorarioAtencion" (
    "IdHorario" integer DEFAULT "nextval"('"id"'::"regclass") NOT NULL,
    "HoraInicio" time without time zone NOT NULL,
    "HoraFin" time without time zone NOT NULL,
    "Dia" character varying(2) NOT NULL,
    "IdCampoDeportivo" integer NOT NULL
);
ALTER TABLE ONLY "HorarioAtencion" ALTER COLUMN "Dia" SET STORAGE PLAIN;


ALTER TABLE "HorarioAtencion" OWNER TO "postgres";

--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 170
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
-- TOC entry 172 (class 1259 OID 32776)
-- Name: TipoCancha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoCancha" (
    "IdTipoCancha" integer DEFAULT "nextval"('"id"'::"regclass") NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE "TipoCancha" OWNER TO "postgres";

--
-- TOC entry 1842 (class 2606 OID 32793)
-- Name: IdCampoDeportivo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "IdCampoDeportivo" PRIMARY KEY ("IdCampoDeportivo");


--
-- TOC entry 1840 (class 2606 OID 32784)
-- Name: IdTipoCancha; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TipoCancha"
    ADD CONSTRAINT "IdTipoCancha" PRIMARY KEY ("IdTipoCancha");


--
-- TOC entry 1838 (class 2606 OID 32775)
-- Name: idHorario; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "HorarioAtencion"
    ADD CONSTRAINT "idHorario" PRIMARY KEY ("IdHorario");


--
-- TOC entry 1836 (class 1259 OID 32805)
-- Name: fki_IdCampoDeportivo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IdCampoDeportivo" ON "HorarioAtencion" USING "btree" ("IdCampoDeportivo");


--
-- TOC entry 1843 (class 1259 OID 32799)
-- Name: fki_IdTipoCancha; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IdTipoCancha" ON "CampoDeportivo" USING "btree" ("IdTipoCancha");


--
-- TOC entry 1844 (class 2606 OID 32800)
-- Name: IdCampoDeportivo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "HorarioAtencion"
    ADD CONSTRAINT "IdCampoDeportivo" FOREIGN KEY ("IdCampoDeportivo") REFERENCES "CampoDeportivo"("IdCampoDeportivo");


--
-- TOC entry 1845 (class 2606 OID 32794)
-- Name: IdTipoCancha; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "IdTipoCancha" FOREIGN KEY ("IdTipoCancha") REFERENCES "TipoCancha"("IdTipoCancha") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1959 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA "public" FROM PUBLIC;
REVOKE ALL ON SCHEMA "public" FROM "postgres";
GRANT ALL ON SCHEMA "public" TO "postgres";
GRANT ALL ON SCHEMA "public" TO PUBLIC;


-- Completed on 2015-04-29 10:36:26

--
-- PostgreSQL database dump complete
--

