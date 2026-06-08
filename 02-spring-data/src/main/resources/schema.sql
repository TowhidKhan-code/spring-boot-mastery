-- This file runs automatically on startup
-- because of spring.sql.init.mode=always

-- Create table if it doesn't exist
CREATE TABLE IF NOT EXISTS students (
                                        id          INT PRIMARY KEY AUTO_INCREMENT,
    -- auto_increment = MySQL gives next number automatically

                                        name        VARCHAR(100) NOT NULL,
    -- string max 100 chars, cannot be null

    email       VARCHAR(150) UNIQUE NOT NULL,
    -- unique = no two students same email

    age         INT,
    -- student age

    course      VARCHAR(100),
    -- which course they are in

    grade       DOUBLE,
    -- their grade (0.0 to 10.0)

    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    -- automatically saves when record was created
    );

CREATE TABLE IF NOT EXISTS courses (
                         id   INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100),
                         duration_weeks INT
);