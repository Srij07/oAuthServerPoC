-- The encrypted client_secret it `secret`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity)
  VALUES ('clientId', '{bcrypt}$2a$10$bipYn4NI/W0GcllocWZmbuH83qWBpHU4Eeq0gnxsexu.yjA2aZ1qq', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);

-- The encrypted password is `pass`
INSERT INTO users (id, username, password, enabled) VALUES (1, 'user', '{bcrypt}$2a$10$bipYn4NI/W0GcllocWZmbuH83qWBpHU4Eeq0gnxsexu.yjA2aZ1qq', 1);
INSERT INTO users (id, username, password, enabled) VALUES (2, 'guest', '{bcrypt}$2a$10$bipYn4NI/W0GcllocWZmbuH83qWBpHU4Eeq0gnxsexu.yjA2aZ1qq', 1);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('guest', 'ROLE_GUEST');
