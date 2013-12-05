source realmdb-schema.sql;

insert into users values('alicia', MD5('alicia'), 'Alicia', 'alicia@acme.com');
insert into user_roles values ('alicia', 'registered');

insert into users values('blas', MD5('blas'), 'Blas', 'blas@acme.com');
insert into user_roles values ('blas', 'registered');

insert into users values('Administrador', MD5('Administrador'), 'Administrador', 'admin@admin.com');
insert into user_roles values ('Administrador', 'admin');