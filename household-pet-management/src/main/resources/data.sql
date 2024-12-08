INSERT INTO households (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) 
VALUES ('EIR001', 3, 5, TRUE),
       ('EIR002', 0, 4, FALSE),
       ('EIR003', 2, 2, TRUE);

INSERT INTO pets (name, animal_type, breed, age, eircode) 
VALUES ('Fido', 'Dog', 'Labrador', 4, 'EIR001'),
       ('Whiskers', 'Cat', 'Siamese', 2, 'EIR001'),
       ('Bella', 'Dog', 'German Shepherd', 5, 'EIR003');


INSERT INTO users (username, password, role, unlocked, email, first_name, last_name, county)
VALUES ('adminUser', '$2y$10$sPrbT7GKWFKWBC.1fLbA7eT1RQP09A/hGnVb9N5vy3EyYgGnR5OMa', 'ADMIN', true, 'admin@example.com', 'Admin', 'User', 'Cork');

INSERT INTO users (username, password, role, unlocked, email, first_name, last_name, county)
VALUES ('regularUser', '$2y$10$sPrbT7GKWFKWBC.1fLbA7eT1RQP09A/hGnVb9N5vy3EyYgGnR5OMa', 'USER', true, 'user@example.com', 'Regular', 'User', 'Kerry');