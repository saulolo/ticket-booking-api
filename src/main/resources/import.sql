--Saul Echeverri
-- 06-oct-2025
-- Se agregan permisos iniciales para la aplicacin "Airplane Ticket Booking".
-- ####################
-- ## permissions
-- ####################
INSERT INTO permissions (name) VALUES ('READ');
INSERT INTO permissions (name) VALUES ('CREATE');
INSERT INTO permissions (name) VALUES ('UPDATE');
INSERT INTO permissions (name) VALUES ('DELETE');
INSERT INTO permissions (name) VALUES ('REFACTOR');


-- Saul Echeverri
-- 06-oct-2025
-- Se agregan los roles definidos para la autenticacin y autorizacin.

-- ####################
-- ## roles
-- ####################
INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('USER');
INSERT INTO roles (role_name) VALUES ('DEVELOPER');
INSERT INTO roles (role_name) VALUES ('INVITED');


-- Saul Echeverri
-- 06-oct-2025
-- Se asignan permisos a los roles a traves de la tabla de enlace roles_permissions.
-- ####################
-- ## roles_permissions
-- ####################

-- ADMIN (ID 1) tiene CRUD completo
INSERT INTO roles_permissions (id_permission, id_role) VALUES (1, 1); -- READ  -> ADMIN
INSERT INTO roles_permissions (id_permission, id_role) VALUES (2, 1); -- CREATE -> ADMIN
INSERT INTO roles_permissions (id_permission, id_role) VALUES (3, 1); -- UPDATE -> ADMIN
INSERT INTO roles_permissions (id_permission, id_role) VALUES (4, 1); -- DELETE -> ADMIN

-- USER (ID 2) tiene READ, CREATE, UPDATE
INSERT INTO roles_permissions (id_permission, id_role) VALUES (1, 2); -- READ  -> USER
INSERT INTO roles_permissions (id_permission, id_role) VALUES (2, 2); -- CREATE -> USER
INSERT INTO roles_permissions (id_permission, id_role) VALUES (3, 2); -- UPDATE -> USER
-- Nota: el usuario estndar NO tiene DELETE (4) ni REFACTOR (5)

-- DEVELOPER (ID 3) tiene CRUD completo + REFACTOR
INSERT INTO roles_permissions (id_permission, id_role) VALUES (1, 3); -- READ  -> DEVELOPER
INSERT INTO roles_permissions (id_permission, id_role) VALUES (2, 3); -- CREATE -> DEVELOPER
INSERT INTO roles_permissions (id_permission, id_role) VALUES (3, 3); -- UPDATE -> DEVELOPER
INSERT INTO roles_permissions (id_permission, id_role) VALUES (4, 3); -- DELETE -> DEVELOPER
INSERT INTO roles_permissions (id_permission, id_role) VALUES (5, 3); -- REFACTOR -> DEVELOPER

-- INVITED (ID 4) tiene READ
INSERT INTO roles_permissions (id_permission, id_role) VALUES (1, 4); -- READ  -> INVITED


-- Saul Echeverri
-- 06-oct-2025
-- Se agregan 5 usuarios iniciales para pruebas de roles:
-- 1 ADMIN, 2 USER, 1 DEVELOPER y 1 INVITED.
-- ####################
-- ## users
-- ####################
-- Contraseña por el momento: 1234

-- 1. Usuario Administrador (ID 1): Felipe
INSERT INTO users (name, username, password, description, created_date, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES ('Felipe', 'pipe', '$2a$10$7a7Lvm1G3nCVvgwzeaT.H.j01GJ4VlPJs8HVhM1KkixrO.kYJole6','Medico.', NOW(), TRUE, TRUE, TRUE, TRUE);

-- 2. Usuario Estandar #1 (ID 2): Alejandra
INSERT INTO users (name, username, password, description, created_date, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES ('Alejandra', 'aleja', '$2a$10$7a7Lvm1G3nCVvgwzeaT.H.j01GJ4VlPJs8HVhM1KkixrO.kYJole6','Administradora de Empresas.', NOW(), TRUE, TRUE, TRUE, TRUE);

-- 3. Usuario Estandar #2 (ID 3): Leidy
INSERT INTO users (name, username, password, description, created_date, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES ('Leidy', 'zapata', '$2a$10$7a7Lvm1G3nCVvgwzeaT.H.j01GJ4VlPJs8HVhM1KkixrO.kYJole6','Administradora de Empresas.', NOW(), TRUE, TRUE, TRUE, TRUE);

-- 4. Usuario Desarrollador (ID 4): Saul
INSERT INTO users (name, username, password, description, created_date, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES ('Saul', 'saulolo', '$2a$10$7a7Lvm1G3nCVvgwzeaT.H.j01GJ4VlPJs8HVhM1KkixrO.kYJole6','Ingeniero de Software.', NOW(), TRUE, TRUE, TRUE, TRUE);

-- 5. Usuario Invitado (ID 5)
INSERT INTO users (name, username, password, description, created_date, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES ('Diego', 'pupilo', '$2a$10$7a7Lvm1G3nCVvgwzeaT.H.j01GJ4VlPJs8HVhM1KkixrO.kYJole6','Ingeniero de Biomédico.', NOW(), TRUE, TRUE, TRUE, TRUE);


-- Saul Echeverri
-- 06-oct-2025
--Asignacin de roles a los 5 usuarios creados:
-- ####################
-- ## users_roles
-- ####################

-- 1. Felipe (ID 1) -> ROL ADMIN (ID 1)
INSERT INTO users_roles (id_user, id_role) VALUES (1, 1);

-- 2. Alejandra (ID 2) -> ROL USER (ID 2)
INSERT INTO users_roles (id_user, id_role) VALUES (2, 2);

-- 3. Leidy (ID 3) -> ROL USER (ID 2)
INSERT INTO users_roles (id_user, id_role) VALUES (3, 2);

-- 4. Saul (ID 4) -> ROL DEVELOPER (ID 3)
INSERT INTO users_roles (id_user, id_role) VALUES (4, 3);

-- 5. Diego (ID 5) -> ROL INVITED (ID 4)
INSERT INTO users_roles (id_user, id_role) VALUES (5, 4);
