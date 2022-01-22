--INITIALISATION TABLE ROLE
INSERT INTO ROLES(ROLE_ID, ROLE_NAME) VALUES (1,'admin');
INSERT INTO ROLES(ROLE_ID, ROLE_NAME) VALUES (2,'user');

--INITIALISATION TABLE UTILISATEURS
INSERT INTO USER_API(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, IS_ACTIVE) values (1, 'dorine', 'berton', 'admin@admin.com', '$2a$10$ISnv6T5sqpr5YeRKP01xEOLAr/ZWviCp73BC07hMK54GNgQHMemxm', 1);
INSERT INTO USER_API(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, IS_ACTIVE) values (2, 'franck', 'lecoche', 'franck@gmail.com', '$2a$10$5TLZBQgB/FOSkccGjKCRDerrD6YzsFznyNURwNHZG8tEwAumfFw1C', 1);
INSERT INTO USER_API(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, IS_ACTIVE) values (3, 'tatie', 'nathalie', 'tatie@gmail.com', '$2a$10$U2NARRA6lp0CSfDF2JEmtOaAbf3bVGx9zGqDbLkW/T59.QdGnlguO', 0);

-- TABLE DE JOINTURE
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (1,1);
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (1,2);
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (2,2);
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (3,2);

COMMIT;
