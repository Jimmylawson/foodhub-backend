CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL NOT NULL,
                                     username VARCHAR(255),
                                     email VARCHAR(255),
                        created_at TIMESTAMP WITHOUT TIME ZONE,
                                     CONSTRAINT pk_users PRIMARY KEY (id)
);
