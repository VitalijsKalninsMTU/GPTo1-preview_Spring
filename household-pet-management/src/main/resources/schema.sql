CREATE TABLE households (
    eircode VARCHAR(10) NOT NULL,
    number_of_occupants INT NOT NULL,
    max_number_of_occupants INT NOT NULL,
    owner_occupied BOOLEAN NOT NULL,
    PRIMARY KEY (eircode)
);

CREATE TABLE pets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    animal_type VARCHAR(50) NOT NULL,
    breed VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    eircode VARCHAR(10) NOT NULL,
    CONSTRAINT fk_household_eircode FOREIGN KEY (eircode) REFERENCES households(eircode)
);

CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    unlocked BOOLEAN NOT NULL,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    county VARCHAR(100) NOT NULL
);