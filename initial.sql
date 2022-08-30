CREATE TABLE users
(
    id      Serial NOT NULL,
    brand    VARCHAR(255),
    producingCountry   VARCHAR(255),
    model VARCHAR(255),
    PRIMARY KEY (id)
)