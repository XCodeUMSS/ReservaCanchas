--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.6
-- Dumped by pg_dump version 9.4.0
-- Started on 2015-04-29 15:34:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 176 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 176
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 171 (class 1259 OID 32771)
-- Name: id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE id OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 32785)
-- Name: CampoDeportivo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "CampoDeportivo" (
    "IdCampoDeportivo" integer DEFAULT nextval('id'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL,
    "PrecioMinimo" money NOT NULL,
    "Foto" bytea,
    "IdTipoCancha" integer NOT NULL,
    "IdTipoSuelo" integer NOT NULL
);


ALTER TABLE "CampoDeportivo" OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 32768)
-- Name: HorarioAtencion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "HorarioAtencion" (
    "IdHorario" integer DEFAULT nextval('id'::regclass) NOT NULL,
    "HoraInicio" time without time zone NOT NULL,
    "HoraFin" time without time zone NOT NULL,
    "Dia" character varying(2) NOT NULL,
    "IdCampoDeportivo" integer NOT NULL
);
ALTER TABLE ONLY "HorarioAtencion" ALTER COLUMN "Dia" SET STORAGE PLAIN;


ALTER TABLE "HorarioAtencion" OWNER TO postgres;

--
-- TOC entry 1981 (class 0 OID 0)
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
-- TOC entry 175 (class 1259 OID 32821)
-- Name: Reserva; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "Reserva" (
    "IdReserva" integer DEFAULT nextval('id'::regclass) NOT NULL,
    "IdUsuario" integer NOT NULL,
    "IdAdmin" integer NOT NULL,
    "Fecha" date NOT NULL,
    "IdCampoDeportivo" integer NOT NULL,
    "HoraInicio" time without time zone NOT NULL,
    "HoraFin" time without time zone NOT NULL,
    "Confirmado" boolean DEFAULT false NOT NULL
);


ALTER TABLE "Reserva" OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 32776)
-- Name: TipoCancha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoCancha" (
    "IdTipoCancha" integer DEFAULT nextval('id'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE "TipoCancha" OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 32806)
-- Name: TipoSuelo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TipoSuelo" (
    "IdTipoSuelo" integer DEFAULT nextval('id'::regclass) NOT NULL,
    "Nombre" character varying NOT NULL
);


ALTER TABLE "TipoSuelo" OWNER TO postgres;

--
-- TOC entry 1854 (class 2606 OID 32793)
-- Name: IdCampoDeportivo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "IdCampoDeportivo" PRIMARY KEY ("IdCampoDeportivo");


--
-- TOC entry 1860 (class 2606 OID 32826)
-- Name: IdReserva; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "Reserva"
    ADD CONSTRAINT "IdReserva" PRIMARY KEY ("IdReserva");


--
-- TOC entry 1852 (class 2606 OID 32784)
-- Name: IdTipoCancha; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TipoCancha"
    ADD CONSTRAINT "IdTipoCancha" PRIMARY KEY ("IdTipoCancha");


--
-- TOC entry 1858 (class 2606 OID 32814)
-- Name: IdTipoSuelo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TipoSuelo"
    ADD CONSTRAINT "IdTipoSuelo" PRIMARY KEY ("IdTipoSuelo");


--
-- TOC entry 1850 (class 2606 OID 32775)
-- Name: idHorario; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "HorarioAtencion"
    ADD CONSTRAINT "idHorario" PRIMARY KEY ("IdHorario");


--
-- TOC entry 1855 (class 1259 OID 32820)
-- Name: fki_IDTipoSuelo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IDTipoSuelo" ON "CampoDeportivo" USING btree ("IdTipoSuelo");


--
-- TOC entry 1848 (class 1259 OID 32805)
-- Name: fki_IdCampoDeportivo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IdCampoDeportivo" ON "HorarioAtencion" USING btree ("IdCampoDeportivo");


--
-- TOC entry 1856 (class 1259 OID 32799)
-- Name: fki_IdTipoCancha; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IdTipoCancha" ON "CampoDeportivo" USING btree ("IdTipoCancha");


--
-- TOC entry 1861 (class 1259 OID 32847)
-- Name: fki_Reserva_CampoDeportivo; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_Reserva_CampoDeportivo" ON "Reserva" USING btree ("IdCampoDeportivo");


--
-- TOC entry 1864 (class 2606 OID 32794)
-- Name: CampoDeportivo-TipoCancha; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "CampoDeportivo-TipoCancha" FOREIGN KEY ("IdTipoCancha") REFERENCES "TipoCancha"("IdTipoCancha") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1863 (class 2606 OID 32815)
-- Name: CampoDeportivo-TipoSuelo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "CampoDeportivo"
    ADD CONSTRAINT "CampoDeportivo-TipoSuelo" FOREIGN KEY ("IdTipoSuelo") REFERENCES "TipoSuelo"("IdTipoSuelo") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1862 (class 2606 OID 32800)
-- Name: HorarioAtencion-CampoDeportivo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "HorarioAtencion"
    ADD CONSTRAINT "HorarioAtencion-CampoDeportivo" FOREIGN KEY ("IdCampoDeportivo") REFERENCES "CampoDeportivo"("IdCampoDeportivo");


--
-- TOC entry 1865 (class 2606 OID 32842)
-- Name: Reserva-CampoDeportivo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Reserva"
    ADD CONSTRAINT "Reserva-CampoDeportivo" FOREIGN KEY ("IdCampoDeportivo") REFERENCES "CampoDeportivo"("IdCampoDeportivo");


--
-- TOC entry 1979 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-04-29 15:34:42

--
-- PostgreSQL database dump complete
--

