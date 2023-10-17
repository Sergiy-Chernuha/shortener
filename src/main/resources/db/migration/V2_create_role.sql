CREATE TABLE ROLES (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT,
  NAME VARCHAR(50) NOT NULL
);

INSERT INTO roles (name) VALUES
('ROLE_USER'), ('ROLE_ADMIN');

CREATE TABLE users_roles (
  user_id               bigint not null,
  role_id               bigint not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, password, email)
values
    ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
    ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);


-- {
--     "username":"user",
--     "email":"user@gmail.com",
--     "password":"oioi123",
--     "role":["user"]
-- }