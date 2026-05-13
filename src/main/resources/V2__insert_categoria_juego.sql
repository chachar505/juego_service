INSERT INTO categoria (nombre, descripcion)
VALUES
    ('Acción',     'Juegos de acción y aventura'),
    ('RPG',        'Juegos de rol y estrategia'),
    ('Deportes',   'Juegos deportivos y simulación'),
    ('Terror',     'Juegos de terror y suspenso'),
    ('Estrategia', 'Juegos de estrategia y puzzle');

INSERT INTO juego (nombre_juego, descripcion, precio, stock, activo, categoria_id)
VALUES
    ('God of War',       'Aventura épica nórdica',             29990.00, 50,  TRUE, 1),
    ('Elden Ring',       'RPG de mundo abierto',               39990.00, 30,  TRUE, 2),
    ('FIFA 25',          'Simulador de fútbol',                34990.00, 100, TRUE, 3),
    ('Resident Evil 4',  'Horror y acción en tercera persona', 24990.00, 40,  TRUE, 4),
    ('Civilization VII', 'Estrategia por turnos',              44990.00, 20,  TRUE, 5);