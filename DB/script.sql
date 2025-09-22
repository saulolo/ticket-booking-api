--Insertar registros a la tabla flights
INSERT INTO flights (flight_number, origin, destination, departure_time, arrival_time, created_date)
VALUES
    ('AB123', 'Madrid', 'Bogotá', '2025-09-21 15:00:00', '2025-09-22 01:00:00', NOW()),
    ('CD456', 'Medellín', 'Miami', '2025-09-22 08:30:00', '2025-09-22 13:00:00', NOW()),
    ('EF789', 'Bogotá', 'Ciudad de México', '2025-09-23 20:00:00', '2025-09-23 23:00:00', NOW());

--Insertar registros a la tabla tickets
INSERT INTO tickets (seat_number, price, is_available, description, created_date, id_flight)
VALUES
    ('1A', 250.00, TRUE, 'Asiento de pasillo en clase económica', NOW(), 1),
    ('2B', 450.00, TRUE, 'Asiento con ventanilla en clase de negocios', NOW(), 1),
    ('3C', 300.00, TRUE, 'Asiento en clase económica', NOW(), 2),
    ('4D', 500.00, TRUE, 'Asiento con ventanilla en primera clase', NOW(), 2),
    ('5E', 280.00, TRUE, 'Asiento de pasillo en clase económica', NOW(), 3);