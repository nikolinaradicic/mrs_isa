SET SQL_MODE = ''
alter table user add firstTime VARCHAR(60)
SET SQL_MODE = ''
ALTER TABLE user modify COLUMN firstTime VARCHAR(60) not null default 'notvisited'
insert into user(id, email, name, lastname, password, role, firstTime) value (1,"admin","admin","admin","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_SYSTEM_MANAGER',"notvisited")
insert into system_manager(id) value(1)