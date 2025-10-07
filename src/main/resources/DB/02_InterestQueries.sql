-- Saul Echeverri
-- 06-oct-2025
-- Consultas SQL de inters para el monitoreo y desarrollo del sistema "Airplane Ticket Booking".

-- ####################
-- ## DDL
-- ####################

-- Consulta para crear la base de datos (Ejecucin manual fuera de Spring/Hibernate)
-- Esta sentencia solo se usa si la BD no existe.
CREATE DATABASE bd_ticket_booking_ias;


-- ####################
-- ## TABLAS DE SEGURIDAD
-- ####################

-- 1. Consultar todos los Permisos definidos
SELECT * FROM permissions;

-- 2. Consultar todos los Roles definidos
SELECT * FROM roles;

-- 3. Consultar la tabla de enlace de Roles y Permisos (ID y Permisos asociados)
SELECT * FROM roles_permissions;

-- 4. Consultar Roles y Permisos asociados con sus nombres (Vista detallada)
SELECT
    r.id_role,
    r.role_name AS rol,
    p.name AS permiso_otorgado
FROM
    roles r
        INNER JOIN
    roles_permissions rp ON r.id_role = rp.id_role
        INNER JOIN
    permissions p ON rp.id_permission = p.id_permission
ORDER BY
    r.id_role, p.id_permission;


-- ####################
-- ## TABLAS DE USUARIOS
-- ####################

-- 5. Consultar todos los Usuarios (users)
SELECT * FROM users;

-- 6. Consultar la tabla de enlace de Usuarios y Roles (users_roles)
SELECT * FROM users_roles;

-- 7. Consultar Usuarios, sus Roles asignados y todos los Permisos que dicho Rol otorga.
--    Utiliza STRING_AGG para concatenar los permisos en una sola columna.
SELECT
    u.id_user,
    u.name AS user_name,
    u.username,
    u.password, -- Campo de contrasea (hash) incluido aqu
    r.role_name,
    STRING_AGG(p.name, ', ' ORDER BY p.name) AS permissions_granted
FROM
    users u
        INNER JOIN
    users_roles ur ON u.id_user = ur.id_user
        INNER JOIN
    roles r ON ur.id_role = r.id_role
        INNER JOIN
    roles_permissions rp ON r.id_role = rp.id_role
        INNER JOIN
    permissions p ON rp.id_permission = p.id_permission
GROUP BY
    u.id_user, u.name, u.username, u.password, r.role_name -- Agregamos u.password al GROUP BY
ORDER BY
    u.id_user;


-- ####################
-- ## TABLAS DE NEGOCIO PRINCIPALES
-- ####################

-- 8. Consultar todos los Vuelos (flights)
SELECT * FROM flights;

-- 9. Consultar todos los Tiquetes (tickets)
SELECT * FROM tickets;

-- 10. Consultar todas las Reservas (reservations)
SELECT * FROM reservations;


-- ####################
-- ## MANTENIMIENTO (Ejecucin CUIDADOSA)
-- ####################

-- Consultar para reiniciar la tabla de usuarios (ej: en entornos de prueba)
-- TRUNCATE TABLE users RESTART IDENTITY CASCADE;

-- Consultar para eliminar la tabla de usuarios (Ejecucin muy CUIDADOSA)
-- DROP TABLE IF EXISTS users;