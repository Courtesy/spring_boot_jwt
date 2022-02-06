BEGIN;

CREATE TABLE users (
    id SERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_dtime timestamp default LOCALTIMESTAMP,
    modified_dtime timestamp default LOCALTIMESTAMP
);

CREATE table refresh_token (
    id varchar(64) UNIQUE NOT NULL,
    username varchar(64) NOT NULL,
    token varchar(128) UNIQUE NOT NULL,
    expiry_date timestamp NOT NULL,
    created_dtime timestamp default LOCALTIMESTAMP,
    modified_dtime timestamp default LOCALTIMESTAMP
);

COMMIT;