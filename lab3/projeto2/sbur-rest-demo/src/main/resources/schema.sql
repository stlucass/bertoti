DROP TABLE IF EXISTS moto;

CREATE TABLE moto (
    id VARCHAR(50) PRIMARY KEY,
    brand VARCHAR(100),
    model VARCHAR(100),
    year INT,
    displacement INT,
    color VARCHAR(50),
    price DOUBLE,
    category VARCHAR(50)
);
