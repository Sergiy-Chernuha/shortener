create table users
(
    id       bigint generated always as identity PRIMARY KEY,
    username varchar(30) not null unique,
    password varchar(80) not null,
    email    varchar(50) unique
);

CREATE TABLE URL
(
    short_url    VARCHAR(200) PRIMARY KEY,
    long_url     VARCHAR(1024) NOT NULL,
    create_date  TIMESTAMP(6)  NOT NULL,
    expiry_date  TIMESTAMP(6)  NOT NULL,
    clicks_count INTEGER       NOT NULL,
    user_id      bigint        not null,
    foreign key (user_id) references users (id) ON DELETE CASCADE
);

create table roles
(
    ID   bigint generated always as identity PRIMARY KEY,
    name varchar(50) not null
);

CREATE TABLE users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_MODERATOR'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);
