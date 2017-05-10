SET SQL_MODE = ''
alter table user add firstTime VARCHAR(60)
SET SQL_MODE = ''
ALTER TABLE user modify COLUMN firstTime VARCHAR(60) not null default 'notvisited'
insert into user(id, email, name, lastname, password, role, firstTime) value (1,"admin","admin","admin","admin",1,"notvisited")
insert into system_manager(id) value(1)