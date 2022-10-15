drop table if exists comments;
drop table if exists booking;
drop table if exists items;
drop table if exists users;

CREATE TABLE IF NOT EXISTS users ( id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, name VARCHAR(255) NOT NULL, email VARCHAR(512) NOT NULL, CONSTRAINT pk_user PRIMARY KEY (id), CONSTRAINT UQ_USER_EMAIL UNIQUE (email));
CREATE TABLE IF NOT EXISTS items ( id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, name VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, available BOOLEAN NOT NULL, owner_id BIGINT NOT NULL, request_id BIGINT NOT NULL, CONSTRAINT pk_item PRIMARY KEY (id) );
CREATE TABLE IF NOT EXISTS booking ( id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, start TIMESTAMP WITHOUT TIME ZONE, "end" TIMESTAMP WITHOUT TIME ZONE, item_id BIGINT NOT NULL , booker_id BIGINT NOT NULL, status VARCHAR(255) default 'WAITING' , CONSTRAINT pk_booking PRIMARY KEY (id), CONSTRAINT fk_booker FOREIGN KEY (booker_id) REFERENCES users (id) ON DELETE CASCADE, CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE);
CREATE TABLE IF NOT EXISTS comments ( id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, text VARCHAR(255) NOT NULL, item_id BIGINT NOT NULL, author_id BIGINT NOT NULL, CONSTRAINT pk_comment PRIMARY KEY (id), CONSTRAINT fk_item_comment FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE, CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE);