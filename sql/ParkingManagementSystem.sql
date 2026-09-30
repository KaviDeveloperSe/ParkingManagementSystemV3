DROP DATABASE IF EXISTS parking_management_system;

CREATE DATABASE parking_management_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE parking_management_system;

CREATE TABLE customers (
    customer_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(120) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (customer_id),
    CONSTRAINT uq_customer_email UNIQUE (email)
);

CREATE TABLE vehicles (
    vehicle_id INT NOT NULL AUTO_INCREMENT,
    registration_number VARCHAR(20) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    vehicle_type ENUM('CAR', 'MOTORCYCLE', 'VAN') NOT NULL,
    customer_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (vehicle_id),
    CONSTRAINT uq_vehicle_registration UNIQUE (registration_number),
    CONSTRAINT fk_vehicle_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE parking_spaces (
    space_id INT NOT NULL AUTO_INCREMENT,
    space_number VARCHAR(12) NOT NULL,
    space_type ENUM('CAR', 'MOTORCYCLE', 'VAN') NOT NULL,
    status ENUM('AVAILABLE', 'OCCUPIED') NOT NULL DEFAULT 'AVAILABLE',

    PRIMARY KEY (space_id),
    CONSTRAINT uq_parking_space_number UNIQUE (space_number)
);

CREATE TABLE parking_sessions (
    session_id INT NOT NULL AUTO_INCREMENT,
    vehicle_id INT NOT NULL,
    space_id INT NOT NULL,
    entry_time DATETIME NOT NULL,
    exit_time DATETIME NULL,
    hourly_rate DECIMAL(10,2) NOT NULL,
    parking_fee DECIMAL(10,2) NULL,
    status ENUM('ACTIVE', 'COMPLETED') NOT NULL DEFAULT 'ACTIVE',
    
    PRIMARY KEY (session_id),
    CONSTRAINT fk_session_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_session_space FOREIGN KEY (space_id) REFERENCES parking_spaces(space_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_hourly_rate CHECK (hourly_rate >= 0),
    CONSTRAINT chk_parking_fee CHECK (parking_fee IS NULL OR parking_fee >= 0),
    CONSTRAINT chk_parking_session_state CHECK ((status = 'ACTIVE' AND exit_time IS NULL AND parking_fee IS NULL) OR (status = 'COMPLETED' AND exit_time IS NOT NULL AND parking_fee IS NOT NULL))
);

CREATE INDEX idx_vehicle_customer
ON vehicles(customer_id);

CREATE INDEX idx_vehicle_type
ON vehicles(vehicle_type);

CREATE INDEX idx_space_status
ON parking_spaces(status);

CREATE INDEX idx_space_type_status
ON parking_spaces(space_type, status);

CREATE INDEX idx_session_vehicle_status
ON parking_sessions(vehicle_id, status);

CREATE INDEX idx_session_space_status
ON parking_sessions(space_id, status);

CREATE INDEX idx_session_status
ON parking_sessions(status);

CREATE INDEX idx_session_entry
ON parking_sessions(entry_time);

CREATE INDEX idx_session_exit
ON parking_sessions(exit_time);