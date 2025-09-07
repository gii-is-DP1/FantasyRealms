-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Ten player users, named player1 with passwor 0wn3r
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');
INSERT INTO appusers(id,username,password,authority,email) VALUES (4,'user1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2,'player1@gmail.com');
INSERT INTO appusers(id,username,password,authority) VALUES (5,'user2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'user3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'user4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'user5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'user6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'user7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'user8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'user9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'user10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (14,'WYP8859','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (15,'pabolimor','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (16,'juavarver','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (17,'PDJ6975','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (18,'marruivaz1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);

INSERT INTO friendships (user_id, friend_id, status, sender_id, receiver_id) VALUES (6, 16, 'PENDING', 6, 16);

INSERT INTO appusers(id,username,password,authority) VALUES (19,'VXN8675','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);

-- Hay que insertar un solo deck con todas las cartas del juego -> deck universal

-- Hay que cambiar nombre de cartas a español

INSERT INTO cards(id, base_value, final_value, name, card_type, image) VALUES
-- Ejército
(1, 5, 5, 'Exploradores', 'EJERCITO', 'exploradores.png'),
(2, 10, 10, 'Arqueros Elficos', 'EJERCITO', 'arqueros_elficos.png'),
(3, 15, 15, 'Infanteria Enana', 'EJERCITO', 'infanteria_enana.png'),
(4, 17, 17, 'Caballeria Ligera', 'EJERCITO', 'caballeria_ligera.png'),
(5, 20, 20, 'Caballeros Celestiales', 'EJERCITO', 'caballeros_celestiales.png'),

-- Artefacto
(6, 1, 1, 'Runa de Proteccion', 'ARTEFACTO', 'runa_proteccion.png'),
(7, 2, 2, 'Arbol del Mundo', 'ARTEFACTO', 'arbol_mundo.png'),
(8, 3, 3, 'Libro de los Cambios', 'ARTEFACTO', 'libro_cambios.png'),
(9, 4, 4, 'Escudo de Keth', 'ARTEFACTO', 'escudo_keth.png'),
(10, 5, 5, 'Gema del Orden', 'ARTEFACTO', 'gema_orden.png'),

-- Bestia
(11, 6, 6, 'Caballo de Guerra', 'BESTIA', 'caballo_guerra.png'),
(12, 9, 9, 'Unicornio', 'BESTIA', 'unicornio.png'),
(13, 12, 12, 'Hidra', 'BESTIA', 'hidra.png'),
(14, 30, 30, 'Dragon', 'BESTIA', 'dragon.png'),
(15, 35, 35, 'Basilisco', 'BESTIA', 'basilisco.png'),

-- Llama
(16, 2, 2, 'Vela', 'LLAMA', 'vela.png'),
(17, 4, 4, 'Elemental de Fuego', 'LLAMA', 'elemental_fuego.png'),
(18, 9, 9, 'Forja', 'LLAMA', 'forja.png'),
(19, 11, 11, 'Relampago', 'LLAMA', 'relampago.png'),
(20, 40, 40, 'Fuego Salvaje', 'LLAMA', 'fuego_salvaje.png'),

-- Inundación
(21, 1, 1, 'Fuente de la Vida', 'INUNDACION', 'fuente_vida.png'),
(22, 4, 4, 'Elemental de Agua', 'INUNDACION', 'elemental_agua.png'),
(23, 14, 14, 'Isla', 'INUNDACION', 'isla.png'),
(24, 18, 18, 'Pantano', 'INUNDACION', 'pantano.png'),
(25, 32, 32, 'Gran Inundacion', 'INUNDACION', 'gran_inundacion.png'),

-- Tierra
(26, 4, 4, 'Elemental de Tierra', 'TIERRA', 'elemental_tierra.png'),
(27, 6, 6, 'Cavernas Subterraneas', 'TIERRA', 'cavernas_subterraneas.png'),
(28, 7, 7, 'Bosque', 'TIERRA', 'bosque.png'),
(29, 8, 8, 'Campanario', 'TIERRA', 'campanario.png'),
(30, 9, 9, 'Montana', 'TIERRA', 'montana.png'),

-- Lider
(31, 2, 2, 'Princesa', 'LIDER', 'princesa.png'),
(32, 4, 4, 'Jefe militar', 'LIDER', 'jefe_militar.png'),
(33, 6, 6, 'Reina', 'LIDER', 'reina.png'),
(34, 8, 8, 'Rey', 'LIDER', 'rey.png'),
(35, 15, 15, 'Emperatriz', 'LIDER', 'emperatriz.png'),

-- Arma
(36, 1, 1, 'Varita magica', 'ARMA', 'varita_magica.png'),
(37, 3, 3, 'Arco elfico largo', 'ARMA', 'arco_elfico_largo.png'),
(38, 7, 7, 'Espada de Keth', 'ARMA', 'espada_keth.png'),
(39, 23, 23, 'Buque de guerra', 'ARMA', 'buque_guerra.png'),
(40, 35, 35, 'Dirigible de guerra', 'ARMA', 'dirigible_guerra.png'),

-- Tiempo
(41, 4, 4, 'Elemental de aire', 'TIEMPO', 'elemental_aire.png'),
(42, 8, 8, 'Tormenta', 'TIEMPO', 'tormenta.png'),
(43, 13, 13, 'Torbellino', 'TIEMPO', 'torbellino.png'),
(44, 27, 27, 'Humo', 'TIEMPO', 'humo.png'),
(45, 30, 30, 'Ventisca', 'TIEMPO', 'ventisca.png'),

-- Salvaje
(46, 0, 0, 'Cambiaformas', 'SALVAJE', 'cambiaformas.png'),
(47, 0, 0, 'Espejismo', 'SALVAJE', 'espejismo.png'),
(48, 0, 0, 'Doppelganger', 'SALVAJE', 'doppelganger.png'),

-- Mago
(49, 3, 3, 'Necromancer', 'MAGO', 'necromancer.png'),
(50, 5, 5, 'Hechicera elemental', 'MAGO', 'hechicera_elemental.png'),
(51, 7, 7, 'Coleccionista', 'MAGO', 'coleccionista.png'),
(52, 9, 9, 'Maestro de bestias', 'MAGO', 'maestro_bestias.png'),
(53, 25, 25, 'Senor brujo', 'MAGO', 'senor_brujo.png');

INSERT INTO mods(id, origin_card_id, primary_value, secondary_value, mod_main_type, mod_type, description) VALUES
-- Exploradores
(1, 1, 10, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +10 por cada Tierra'),
(2, 1, NULL, NULL, 'CLEAR', 'CLEAR_ARMY_PENALTY_HAND', 'Elimina la palabra Ejercito de la seccion de penalizaciones de todas las cartas en la mano'),

-- Arqueros Élficos
(3, 2, 5, NULL, 'BONUS', 'BONUS_PENALTY_NO_TYPE', 'Bonus: +5 si no hay Tiempo en la mano'),

-- Infantería Enana
(4, 3, -2, NULL, 'PENALTY', 'BONUS_PENALTY_EACH_TYPE', 'Penalizacion: -2 por cada otro Ejercito'),

-- Caballería Ligera
(5, 4, -2, NULL, 'PENALTY', 'BONUS_PENALTY_EACH_TYPE', 'Penalizacion: -2 por cada Tierra'),

-- Caballeros Celestiales
(6, 5, -8, NULL, 'PENALTY', 'BONUS_PENALTY_NO_TYPE', 'Penalizacion: -8 a menos que haya al menos un Lider'),

-- Runa de Protección
(7, 6, NULL, NULL, 'CLEAR', 'CLEAR_ALL_PENALTIES', 'Elimina las secciones de penalizacion de todas las cartas en la mano'),

-- Árbol del Mundo
(8, 7, 50, NULL, 'ALLHAND', 'BONUS_DIFFERENT_SUIT_HAND', 'Bonus: +50 si cada carta activa en la mano tiene un tipo diferente'),

-- Libro de los Cambios
(9, 8, NULL, NULL, 'STATE', 'CHANGE_TYPE_CARD', 'Bonus: puedes cambiar el tipo de una carta. Su nombre, bonificaciones y penalizaciones permanecen iguales'),

-- Escudo de Keth

(10, 9, 15, 40, 'BONUS', 'BONUS_LEADER_SHIELD_KETH', 'Bonus: +15 con cualquier Lider, +40 con Lider y Espada de Keth'),

-- Gema del Orden
(11, 10, NULL, NULL, 'ALLHAND', 'BONUS_CARD_RUN_HAND', 'Bonus: +10 por combinacion de 3 cartas, +30 por combinacion de 4 cartas, +60 por combinacion de 5 cartas, +100 por combinacion de 6 cartas, +150 por combinacion de 7 cartas'),

-- Caballo de Guerra
(12, 11, 14, NULL, 'BONUS', 'BONUS_LEADER_OR_WIZARD', 'Bonus: +14 con cualquier Lider o Mago'),

-- Unicornio
(13, 12, 15, 30, 'BONUS', 'BONUS_PRINCESS_QUEEN_EMPRESS', 'Bonus: +30 con Princesa, +15 con Emperatriz, Reina o Encantadora Elemental'),

-- Hidra
(14, 13, 28, NULL, 'BONUS', 'BONUS_SWAMP', 'Bonus: +28 con Pantano'),

-- Dragón
(15, 14, -40, NULL, 'PENALTY', 'BONUS_PENALTY_NO_TYPE', 'Penalizacion: -40 a menos que haya al menos un Mago'),

-- Basilisco
(16, 15, NULL, NULL, 'BLANK', 'BLANK_TYPES', 'Penalizacion: Anula todos los Ejercitos, Lideres y otras Bestias'),

-- Vela
(17, 16, 100, NULL, 'BONUS', 'BONUS_BOOK_BELL_WIZARD', 'Bonus: +100 con Libro de los Cambios, Campanario y cualquier Mago'),

-- Elemental de Fuego
(18, 17, 15, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +15 por cada otra carta de tipo Llama'),

-- Forja
(19, 18, 9, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +9 por cada Arma y Artefacto'),

-- Relámpago
(20, 19, 30, NULL, 'BONUS', 'BONUS_RAINSTORM', 'Bonus: +30 con Tormenta'),

-- Fuego Salvaje
(21, 20, NULL, NULL, 'BLANK', 'BLANK_EXCEPT_NAME', 'Anula todas las cartas excepto Llamas, Clima, Magos, Armas, Artefactos, Gran Inundacion, Isla, Montaña, Unicornio y Dragon'),

-- Fuente de la Vida
(22, 21, NULL, NULL, 'STATE', 'CHANGE_BASE_VALE', 'Bonus: Añade la fuerza base de cualquier Arma, Inundacion, Llama, Tierra o Clima en tu mano'),

-- Elemental de Agua
(23, 22, 15, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +15 por cada otra Inundacion'),

-- Isla
(24, 23, NULL, NULL, 'CLEAR', 'CLEAR_PENALTY_ISLAND', 'Elimina la penalizacion de una Inundacion o Llama'),

-- Pantano
(25, 24, -3, NULL, 'PENALTY', 'BONUS_PENALTY_EACH_TYPE', 'Penalizacion: -3 por cada Ejercito y Llama'),

-- Gran Inundación
(26, 25, NULL, NULL, 'PENALTY_AND_BLANK', 'BLANK_EXCEPT_NAME', 'Penalizacion: Anula todos los Ejercitos, toda Tierra excepto Montaña, y todas las Llamas excepto Relampago'),

-- Elemental de Tierra
(27, 26, 15, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +15 por cada otra Tierra'),

-- Cavernas Subterráneas
(28, 27, 25, NULL, 'BONUS', 'BONUS_DWARVISH_DRAGON', 'Bonus: +25 con Infanteria Enana o Dragon'),
(29, 27, NULL, NULL, 'CLEAR', 'CLEAR_PENALTY_FOR_TYPE', 'Elimina la penalizacion de todas las cartas de Tiempo'),

-- Bosque
(30, 28, 12, NULL, 'BONUS', 'BONUS_FOREST', 'Bonus: +12 por cada Bestia y Arqueros Elficos'),

-- Campanario
(31, 29, 15, NULL, 'BONUS', 'BASIC_BONUS_SINGLE_WIZARD', 'Bonus: +15 con cualquier Mago'),

-- Montaña
(32, 30, 50, NULL, 'BONUS', 'BONUS_SMOKE_WILDFIRE', 'Bonus: +50 con Humo y Fuego Salvaje'),
(33, 30, NULL, NULL, 'CLEAR', 'CLEAR_PENALTY_FOR_TYPE', 'Elimina la penalización de todas las cartas de tipo Inundacion'),

-- Princesa
(34, 31, 8, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +8 por cada Ejercito, Mago y cualquier otro Lider'),

-- Jefe militar
(35, 32, NULL, NULL, 'STATE', 'EQUAL_BASE_STRENGTHS', 'Bonus: La suma de las fuerzas base de todas las armas de tu mazo'),

-- Reina
(36, 33, 5, 20, 'BONUS', 'BONUS_QUEEN', 'Bonus: +5 por cada Ejercito o +20 por cada Ejercito si también tienes al Rey'),

-- Rey
(37, 34, 5, 20, 'BONUS', 'BONUS_KING', 'Bonus: +5 por cada Ejercito o +20 por cada Ejercito si también tienes a la Reina'),

-- Emperatriz
(38, 35, 10, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +10 por cada Ejercito'),
(39, 35, -5, NULL, 'PENALTY', 'BONUS_PENALTY_EACH_TYPE', 'Penalizacion: -5 por cada otro lider'),

-- Varita magica
(40, 36, 25, NULL, 'BONUS', 'BASIC_BONUS_SINGLE_WIZARD', 'Bonus: +25 con cualquier Mago'),

-- Arco elfico largo
(41, 37, 30, NULL, 'BONUS', 'BONUS_ELVEN_WARLORD_BEASTMASTER', 'Bonus: + 30 con Arqueros Elficos, Señor de la Guerra o Señor de las Bestias'),

-- Espada de Keth
(42, 38, 10, 40, 'BONUS', 'BONUS_LEADER_SWORD_KETH', 'Bonus: +10 con cualquier Lider o +40 con Lider y Escudo de Keth'),

-- Buque de guerra
(43, 39, NULL, NULL, 'PENALTY_AND_BLANK', 'BLANK_UNLESS_TYPE', 'Penalización: anulada a menos que este con al menos una carta de Inundacion'),
(44, 39, NULL, NULL, 'CLEAR', 'CLEAR_ARMY_PENALTY_FLOOD','Elimina la palabra Ejercito de la seccion de penalizacion de todos las Inundaciones'),

-- Dirigible de guerra
(45, 40, NULL, NULL, 'PENALTY_AND_BLANK', 'BLANK_UNLESS_ARMY_OR_WEATHER', 'Penalización: anulada a menos que este con al menos un Ejercito, anulada si la mano contiene cualquier carta de Tiempo'),

-- Elemental de aire
(46, 41, 15, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +15 por cada otra carta de tipo Tiempo'),

-- Tormenta
(47, 42, 10, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +10 por cada Inundacion'),
(48, 42, NULL, NULL, 'PENALTY_AND_BLANK', 'BLANK_EXCEPT_NAME', 'Penalizacion: Anula todas las cartas de tipo Llama excepto Relampago'),

-- Torbellino
(49, 43, 40, NULL, 'BONUS', 'BONUS_WHIRLWIND', 'Bonus: +40 si tienes Tormenta y Ventisca o +40 si tienes Tormenta y Gran Inundacion'),

-- Humo
(50, 44, NULL, NULL, 'PENALTY_AND_BLANK', 'BLANK_UNLESS_TYPE', 'Penalizacion: anulada a menos que este acompañada por al menos una carta de tipo Fuego'),

-- Ventisca
(51, 45, NULL, NULL, 'PENALTY_AND_BLANK', 'BLANK_TYPES', 'Penalizacion: anula todas las cartas de tipo Inundacion'),
(52, 45, -5, NULL, 'PENALTY', 'BONUS_PENALTY_EACH_TYPE', 'Penalizacion: -5 por cada Ejercito, Lider, Bestia y Llama'),

-- Cambiaformas
(53, 46, NULL, NULL, 'STATE', 'CHANGE_NAME_TYPE_SHAPESHIFTER', 'Puede adoptar el nombre y el tipo de cualquier Artefacto, Lider, Mago, Arma o Bestia de todo el juego. No obtiene bonus, penalizaciones o fuerza base de la carta copiada'),

-- Espejismo
(54, 47, NULL, NULL, 'STATE', 'CHANGE_NAME_AND_TYPE_MIRAGE', 'Puede adoptar el nombre y el tipo de cualquier Ejercito, Tierra, Tiempo, Inundacion o Llama de todo el juego. No obtiene bonus, penalizaciones o fuerza base de la carta copiada'),

-- Doppelganger
(55, 48, NULL, NULL, 'STATE', 'CHANGE_ALL_EXCEPT_BONUS', 'Puede duplicar el nombre, tipo, fuerza base y penalizacion, pero no la bonificacion, de cualquier otra carta en tu mano'),

-- Necromancer
(56, 49, NULL, NULL, 'NECROMANCER', 'NECROMANCER_MOD', 'Bonus: al final del juego, puedes tomar un Ejercito, Lider, Mago o Bestia del descarte y ponerla en tu mano como una octava carta'),

-- Hechicera elemental
(57, 50, 5, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +5 por cada Tierra, Tiempo, Inundacion y Llama'),

-- Coleccionista
(58, 51, 10, 40, 'ALLHAND', 'BONUS_SAME_SUIT_HAND', 'Bonus: +10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco'),

-- Maestro de bestias
(59, 52, 9, NULL, 'BONUS', 'BONUS_PENALTY_EACH_TYPE', 'Bonus: +9 por cada Bestia'),
(60, 52, NULL, NULL, 'CLEAR', 'CLEAR_PENALTY_FOR_TYPE', 'Borra la penalizacion de todas las Bestias'),

-- Señor brujo
(61, 53, -10, NULL, 'PENALTY', 'BONUS_PENALTY_EACH_TYPE', 'Penalizacion: -10 por cada Lider y otro Mago que tengas');

INSERT INTO decks(id) VALUES (1);

INSERT INTO decks_cards(deck_id,cards_id) VALUES 
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(1, 23),
(1, 24),
(1, 25),
(1, 26),
(1, 27),
(1, 28),
(1, 29),
(1, 30),
(1, 31),
(1, 32),
(1, 33),
(1, 34),
(1, 35),
(1, 36),
(1, 37),
(1, 38),
(1, 39),
(1, 40),
(1, 41),
(1, 42),
(1, 43),
(1, 44),
(1, 45),
(1, 46),
(1, 47),
(1, 48),
(1, 49),
(1, 50),
(1, 51),
(1, 52),
(1, 53);

INSERT INTO discards(id) VALUES (1);
INSERT INTO discards(id) VALUES (2);
INSERT INTO discards(id) VALUES (3);

INSERT INTO matches(name, id, deck_id, discard_id, start_date, end_date, in_scoring_phase) VALUES ('De Chill',2, 1, 1, '2025-01-08T10:15:00', '2025-01-08T18:45:00', false);

-- Exploradores
INSERT INTO mods_target(mod_id, target_id) VALUES (1, 26);
INSERT INTO mods_target(mod_id, target_id) VALUES (1, 27);
INSERT INTO mods_target(mod_id, target_id) VALUES (1, 28);
INSERT INTO mods_target(mod_id, target_id) VALUES (1, 29);
INSERT INTO mods_target(mod_id, target_id) VALUES (1, 30);

-- Arqueros Élficos
INSERT INTO mods_target(mod_id, target_id) VALUES (3, 41);
INSERT INTO mods_target(mod_id, target_id) VALUES (3, 42);
INSERT INTO mods_target(mod_id, target_id) VALUES (3, 43);
INSERT INTO mods_target(mod_id, target_id) VALUES (3, 44);
INSERT INTO mods_target(mod_id, target_id) VALUES (3, 45);

-- Infantería Enana
INSERT INTO mods_target(mod_id, target_id) VALUES (4, 1);
INSERT INTO mods_target(mod_id, target_id) VALUES (4, 2);
INSERT INTO mods_target(mod_id, target_id) VALUES (4, 3);
INSERT INTO mods_target(mod_id, target_id) VALUES (4, 5);

-- Caballería Ligera
INSERT INTO mods_target(mod_id, target_id) VALUES (5, 26);
INSERT INTO mods_target(mod_id, target_id) VALUES (5, 27);
INSERT INTO mods_target(mod_id, target_id) VALUES (5, 28);
INSERT INTO mods_target(mod_id, target_id) VALUES (5, 29);
INSERT INTO mods_target(mod_id, target_id) VALUES (5, 30);

-- Caballeros Celestiales
INSERT INTO mods_target(mod_id, target_id) VALUES (6, 31);
INSERT INTO mods_target(mod_id, target_id) VALUES (6, 32);
INSERT INTO mods_target(mod_id, target_id) VALUES (6, 33);
INSERT INTO mods_target(mod_id, target_id) VALUES (6, 34);
INSERT INTO mods_target(mod_id, target_id) VALUES (6, 35);

-- Dragón
INSERT INTO mods_target(mod_id, target_id) VALUES (15, 49);
INSERT INTO mods_target(mod_id, target_id) VALUES (15, 50);
INSERT INTO mods_target(mod_id, target_id) VALUES (15, 51);
INSERT INTO mods_target(mod_id, target_id) VALUES (15, 52);
INSERT INTO mods_target(mod_id, target_id) VALUES (15, 53);

-- Basilisco
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 1);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 2);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 3);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 4);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 5);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 31);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 32);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 33);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 34);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 35);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 11);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 12);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 13);
INSERT INTO mods_target(mod_id, target_id) VALUES (16, 14);

-- Vela
-- Sin target_id especifico

-- Elemental de Fuego
INSERT INTO mods_target(mod_id, target_id) VALUES (18, 16);
INSERT INTO mods_target(mod_id, target_id) VALUES (18, 18);
INSERT INTO mods_target(mod_id, target_id) VALUES (18, 19);
INSERT INTO mods_target(mod_id, target_id) VALUES (18, 20);

-- Forja
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 36);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 37);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 38);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 39);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 40);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 6);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 7);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 8);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 9);
INSERT INTO mods_target(mod_id, target_id) VALUES (19, 10);

-- Fuego Salvaje
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 1);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 2);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 3);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 4);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 5);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 11);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 13);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 15);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 21);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 22);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 24);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 26);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 27);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 28);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 29);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 31);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 32);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 33);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 34);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 35);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 46);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 47);
INSERT INTO mods_target(mod_id, target_id) VALUES (21, 48);

-- Elemental de Agua
INSERT INTO mods_target(mod_id, target_id) VALUES (23, 21);
INSERT INTO mods_target(mod_id, target_id) VALUES (23, 23);
INSERT INTO mods_target(mod_id, target_id) VALUES (23, 24);
INSERT INTO mods_target(mod_id, target_id) VALUES (23, 25);

-- Pantano
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 1);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 2);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 3);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 4);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 5);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 16);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 17);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 18);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 19);
INSERT INTO mods_target(mod_id, target_id) VALUES (25, 20);

-- Gran Inundación
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 1);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 2);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 3);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 4);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 5);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 26);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 27);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 28);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 29);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 16);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 17);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 18);
INSERT INTO mods_target(mod_id, target_id) VALUES (26, 20);

-- Elemental de Tierra
INSERT INTO mods_target(mod_id, target_id) VALUES (27, 27);
INSERT INTO mods_target(mod_id, target_id) VALUES (27, 28);
INSERT INTO mods_target(mod_id, target_id) VALUES (27, 29);
INSERT INTO mods_target(mod_id, target_id) VALUES (27, 30);

-- Cavernas Subterráneas
INSERT INTO mods_target(mod_id, target_id) VALUES (29, 41);  
INSERT INTO mods_target(mod_id, target_id) VALUES (29, 42);  
INSERT INTO mods_target(mod_id, target_id) VALUES (29, 43); 
INSERT INTO mods_target(mod_id, target_id) VALUES (29, 44);  
INSERT INTO mods_target(mod_id, target_id) VALUES (29, 45);

-- Montaña
INSERT INTO mods_target(mod_id, target_id) VALUES (33, 21);  
INSERT INTO mods_target(mod_id, target_id) VALUES (33, 22);  
INSERT INTO mods_target(mod_id, target_id) VALUES (33, 23); 
INSERT INTO mods_target(mod_id, target_id) VALUES (33, 24);  
INSERT INTO mods_target(mod_id, target_id) VALUES (33, 25); 

-- Princesa
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 1);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 2);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 3); 
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 4);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 5); 
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 49);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 50);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 51); 
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 52);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 53); 
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 32);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 33);  
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 34); 
INSERT INTO mods_target(mod_id, target_id) VALUES (34, 35);  

-- Emperatriz
INSERT INTO mods_target(mod_id, target_id) VALUES (38, 1);  
INSERT INTO mods_target(mod_id, target_id) VALUES (38, 2);  
INSERT INTO mods_target(mod_id, target_id) VALUES (38, 3); 
INSERT INTO mods_target(mod_id, target_id) VALUES (38, 4);  
INSERT INTO mods_target(mod_id, target_id) VALUES (38, 5);
INSERT INTO mods_target(mod_id, target_id) VALUES (39, 31);  
INSERT INTO mods_target(mod_id, target_id) VALUES (39, 32);  
INSERT INTO mods_target(mod_id, target_id) VALUES (39, 33); 
INSERT INTO mods_target(mod_id, target_id) VALUES (39, 34);  

-- Buque de guerra
INSERT INTO mods_target(mod_id, target_id) VALUES (43, 21);  
INSERT INTO mods_target(mod_id, target_id) VALUES (43, 22);  
INSERT INTO mods_target(mod_id, target_id) VALUES (43, 23); 
INSERT INTO mods_target(mod_id, target_id) VALUES (43, 24);  
INSERT INTO mods_target(mod_id, target_id) VALUES (43, 25);

-- Dirigible de guerra
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 1);  
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 2);  
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 3); 
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 4);  
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 5);
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 41);  
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 42);  
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 43); 
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 44);  
INSERT INTO mods_target(mod_id, target_id) VALUES (45, 45);

-- Elemental de aire
INSERT INTO mods_target(mod_id, target_id) VALUES (46, 42);  
INSERT INTO mods_target(mod_id, target_id) VALUES (46, 43); 
INSERT INTO mods_target(mod_id, target_id) VALUES (46, 44);  
INSERT INTO mods_target(mod_id, target_id) VALUES (46, 45);

-- Tormenta
INSERT INTO mods_target(mod_id, target_id) VALUES (47, 21);  
INSERT INTO mods_target(mod_id, target_id) VALUES (47, 22);  
INSERT INTO mods_target(mod_id, target_id) VALUES (47, 23); 
INSERT INTO mods_target(mod_id, target_id) VALUES (47, 24);  
INSERT INTO mods_target(mod_id, target_id) VALUES (47, 25);
INSERT INTO mods_target(mod_id, target_id) VALUES (48, 16);  
INSERT INTO mods_target(mod_id, target_id) VALUES (48, 17);  
INSERT INTO mods_target(mod_id, target_id) VALUES (48, 18); 
INSERT INTO mods_target(mod_id, target_id) VALUES (48, 20); 

-- Humo
INSERT INTO mods_target(mod_id, target_id) VALUES (50, 16);  
INSERT INTO mods_target(mod_id, target_id) VALUES (50, 17);  
INSERT INTO mods_target(mod_id, target_id) VALUES (50, 18); 
INSERT INTO mods_target(mod_id, target_id) VALUES (50, 19);  
INSERT INTO mods_target(mod_id, target_id) VALUES (50, 20);

-- Ventisca
INSERT INTO mods_target(mod_id, target_id) VALUES (51, 21);  
INSERT INTO mods_target(mod_id, target_id) VALUES (51, 22);  
INSERT INTO mods_target(mod_id, target_id) VALUES (51, 23); 
INSERT INTO mods_target(mod_id, target_id) VALUES (51, 24);  
INSERT INTO mods_target(mod_id, target_id) VALUES (51, 25);
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 1);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 2);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 3); 
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 4);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 5);
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 31);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 32);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 33); 
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 34);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 35);
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 11);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 12);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 13); 
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 14);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 15);
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 16);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 17);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 18); 
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 19);  
INSERT INTO mods_target(mod_id, target_id) VALUES (52, 20);

-- Hechicera elemental
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 16);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 17);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 18); 
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 19);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 20);
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 21);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 22);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 23); 
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 24);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 25);
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 26);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 27);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 28); 
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 29);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 30);
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 41);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 42);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 43); 
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 44);  
INSERT INTO mods_target(mod_id, target_id) VALUES (57, 45);

-- Maestro de bestias
INSERT INTO mods_target(mod_id, target_id) VALUES (59, 11);  
INSERT INTO mods_target(mod_id, target_id) VALUES (59, 12);  
INSERT INTO mods_target(mod_id, target_id) VALUES (59, 13); 
INSERT INTO mods_target(mod_id, target_id) VALUES (59, 14);  
INSERT INTO mods_target(mod_id, target_id) VALUES (59, 15);
INSERT INTO mods_target(mod_id, target_id) VALUES (60, 11);  
INSERT INTO mods_target(mod_id, target_id) VALUES (60, 12);  
INSERT INTO mods_target(mod_id, target_id) VALUES (60, 13); 
INSERT INTO mods_target(mod_id, target_id) VALUES (60, 14);  
INSERT INTO mods_target(mod_id, target_id) VALUES (60, 15);

-- Senyor brujo
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 31);  
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 32);  
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 33); 
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 34);  
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 35);
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 49);  
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 50);  
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 51); 
INSERT INTO mods_target(mod_id, target_id) VALUES (61, 52);


INSERT INTO players(id,score,user_id,role,match_played_id) VALUES (3,127,16,'PARTICIPANTE',2);
INSERT INTO players(id,score,user_id,role,match_played_id) VALUES (7,123,17,'PARTICIPANTE',2);
INSERT INTO players(id,score,user_id,role,match_played_id) VALUES (6,94,5,'CREADOR',2);

-- Logros

INSERT INTO achievements(id, required_value, tier, description, extra_data, icon, condition, type) VALUES
(1, 10, 'INTERMEDIO', 'Gana 10 partidas', NULL, NULL, 'WIN_N_GAMES', 'HABILIDAD'),
(2, 1, 'FACIL', 'Juega 1 partida', NULL, NULL, 'PLAY_N_GAMES', 'PROGRESO'),
(3, 10, 'FACIL', 'Juega 10 partida', NULL, NULL, 'PLAY_N_GAMES', 'PROGRESO'),
(4, 30, 'INTERMEDIO', 'Juega 30 partida', NULL, NULL, 'PLAY_N_GAMES', 'PROGRESO'),
(5, 3, 'INTERMEDIO', 'Obten una racha de 3 victorias', NULL, NULL, 'WIN_STREAK', 'HABILIDAD'),
(6, 180, 'INTERMEDIO', 'Gana una partida con 180 puntos o mas', NULL, NULL, 'WIN_WITH_MIN_POINTS', 'HABILIDAD'),
(7, NULL, 'FACIL', 'Gana una partida con una mano que no tenga ninguna carta especial', NULL, NULL, 'WIN_WITH_NO_RARE_CARDS', 'PROGRESO'),
(8, NULL, 'FACIL', 'Gana una partida despues de quedar ultimo', NULL, NULL, 'WIN_AFTER_LAST_PLACE', 'PROGRESO'),
(9, NULL, 'INTERMEDIO', 'Gana una partida con Rey y Reina', 'KING,QUEEN', NULL, 'WIN_WITH_SPECIFIC_CARDS', 'HABILIDAD'),
(10, NULL, 'DIFICIL', 'Gana una partida con el Maestro de las Bestias y 3 Bestias en la mano', 'BEASTMASTER,3_BEASTS', NULL, 'WIN_WITH_SPECIFIC_CARDS', 'HABILIDAD'),
(11, NULL, 'INTERMEDIO', 'Gana una partida con el Escudo y la Espada de keth', 'SWORD_KETH,SHIELD_KETH', NULL, 'WIN_WITH_SPECIFIC_CARDS', 'HABILIDAD'),
(12, NULL, 'DIFICIL', 'Gana una partida con las 4 cartas elementales', '4_ELEMENTALS', NULL, 'WIN_WITH_SPECIFIC_CARDS', 'HABILIDAD'),
(13, 100, 'DIFICIL', 'Juega 100 cartas de tipo Llama', 'LLAMA', NULL, 'PLAY_N_CARDS_OF_TYPE', 'PROGRESO'),
(14, 100, 'DIFICIL', 'Juega 100 cartas de tipo Inundacion', 'INUNDACION', NULL, 'PLAY_N_CARDS_OF_TYPE', 'PROGRESO');

