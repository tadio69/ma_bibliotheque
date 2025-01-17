--
-- PostgreSQL database dump
--

-- Dumped from database version 14.15 (Ubuntu 14.15-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.15 (Ubuntu 14.15-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: emprunt; Type: TABLE; Schema: public; Owner: chijou
--

CREATE TABLE public.emprunt (
    id integer NOT NULL,
    membre_id integer NOT NULL,
    livre_id integer NOT NULL,
    date_emprunt date NOT NULL,
    date_retour_prevue date NOT NULL,
    date_retour_effective date
);


ALTER TABLE public.emprunt OWNER TO chijou;

--
-- Name: emprunt_id_seq; Type: SEQUENCE; Schema: public; Owner: chijou
--

CREATE SEQUENCE public.emprunt_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.emprunt_id_seq OWNER TO chijou;

--
-- Name: emprunt_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chijou
--

ALTER SEQUENCE public.emprunt_id_seq OWNED BY public.emprunt.id;


--
-- Name: livre; Type: TABLE; Schema: public; Owner: chijou
--

CREATE TABLE public.livre (
    id integer NOT NULL,
    titre character varying(255) NOT NULL,
    auteur character varying(255) NOT NULL,
    categorie character varying(200) NOT NULL,
    nbexemplaires integer NOT NULL
);


ALTER TABLE public.livre OWNER TO chijou;

--
-- Name: livre_id_seq; Type: SEQUENCE; Schema: public; Owner: chijou
--

CREATE SEQUENCE public.livre_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.livre_id_seq OWNER TO chijou;

--
-- Name: livre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chijou
--

ALTER SEQUENCE public.livre_id_seq OWNED BY public.livre.id;


--
-- Name: membre; Type: TABLE; Schema: public; Owner: chijou
--

CREATE TABLE public.membre (
    id integer NOT NULL,
    nom character varying(255) NOT NULL,
    prenom character varying(255) NOT NULL,
    email character varying(200) NOT NULL,
    adhesiondate date NOT NULL
);


ALTER TABLE public.membre OWNER TO chijou;

--
-- Name: membre_id_seq; Type: SEQUENCE; Schema: public; Owner: chijou
--

CREATE SEQUENCE public.membre_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.membre_id_seq OWNER TO chijou;

--
-- Name: membre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chijou
--

ALTER SEQUENCE public.membre_id_seq OWNED BY public.membre.id;


--
-- Name: emprunt id; Type: DEFAULT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.emprunt ALTER COLUMN id SET DEFAULT nextval('public.emprunt_id_seq'::regclass);


--
-- Name: livre id; Type: DEFAULT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.livre ALTER COLUMN id SET DEFAULT nextval('public.livre_id_seq'::regclass);


--
-- Name: membre id; Type: DEFAULT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.membre ALTER COLUMN id SET DEFAULT nextval('public.membre_id_seq'::regclass);


--
-- Data for Name: emprunt; Type: TABLE DATA; Schema: public; Owner: chijou
--

COPY public.emprunt (id, membre_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective) FROM stdin;
1	1	1	2021-06-12	2021-08-19	2023-04-07
5	7	9	2021-09-20	2021-12-12	2022-11-18
7	9	4	2020-08-21	2020-08-22	2020-08-22
6	8	1	2023-03-10	2024-03-10	2024-12-14
\.


--
-- Data for Name: livre; Type: TABLE DATA; Schema: public; Owner: chijou
--

COPY public.livre (id, titre, auteur, categorie, nbexemplaires) FROM stdin;
1	UML2 en action	Chijou Roger	Livre de poche	8
4	TP info	Tadio roger	Poche	6
5	Apprendre Postman	Jules Pat	Classique	8
9	Livre de maths	Un groupe d'enseignants	Classique	12
11	Spécificités de JavaScript	Hubert Top	Développement	30
10	Exercices de Mathématiques	Dubois Roger	Classique	40
\.


--
-- Data for Name: membre; Type: TABLE DATA; Schema: public; Owner: chijou
--

COPY public.membre (id, nom, prenom, email, adhesiondate) FROM stdin;
1	Chijou	Roger	chijou@gmail.com	2023-11-22
3	Nengou	John	nengo@yahoo.fr	2021-10-03
5	Nassi	Riche	nassi@yahoo.fr	2022-12-07
7	Megne	Joyce	joyceme@yahoo.fr	2025-01-12
8	Kue	Job	kuejo@gmail.com	2019-02-24
9	Tsemo	Ines	ines@yahoo.fr	2017-06-06
\.


--
-- Name: emprunt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chijou
--

SELECT pg_catalog.setval('public.emprunt_id_seq', 7, true);


--
-- Name: livre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chijou
--

SELECT pg_catalog.setval('public.livre_id_seq', 12, true);


--
-- Name: membre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chijou
--

SELECT pg_catalog.setval('public.membre_id_seq', 9, true);


--
-- Name: emprunt emprunt_pkey; Type: CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_pkey PRIMARY KEY (id);


--
-- Name: livre livre_pkey; Type: CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT livre_pkey PRIMARY KEY (id);


--
-- Name: membre membre_pkey; Type: CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.membre
    ADD CONSTRAINT membre_pkey PRIMARY KEY (id);


--
-- Name: livre titre_unique; Type: CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT titre_unique UNIQUE (titre);


--
-- Name: emprunt cste_extce_livre; Type: FK CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT cste_extce_livre FOREIGN KEY (livre_id) REFERENCES public.livre(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: emprunt cste_extce_mbre; Type: FK CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT cste_extce_mbre FOREIGN KEY (membre_id) REFERENCES public.membre(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: emprunt emprunt_livre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_livre_id_fkey FOREIGN KEY (livre_id) REFERENCES public.livre(id);


--
-- Name: emprunt emprunt_membre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: chijou
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_membre_id_fkey FOREIGN KEY (membre_id) REFERENCES public.membre(id);


--
-- PostgreSQL database dump complete
--

