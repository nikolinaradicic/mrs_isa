insert into user(id, email, name, lastname, password, role) value (1,"admin","admin","admin","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_SYSTEM_MANAGER')
insert into system_manager(id) value(1)
insert into user(id, email, name, lastname, password, role) value (2,"gost1","Sandra","Todorovic","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_GUEST')
insert into user(id, email, name, lastname, password, role) value (3,"gost2","Uros","Jovanovic","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_GUEST')
insert into user(id, email, name, lastname, password, role) value (4,"gost3","Nikolina","Radicic","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_GUEST')
insert into restaurant(id, description, name) value (1,"Restoran na obali","Restoran#1")
insert into user(id, email, name, lastname, password, role) value (5, "menadzer1","Uros","Jovanovic","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_RESTAURANT_MANAGER')
insert into restaurant_manager(id, restaurant_id) value (5, 1)
insert into user(id, email, name, lastname, password, role) value (6, "waiter1","Waiter","Waiter","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_WAITER')
insert into employee(birthday,shoe_size,uniform_size,id,restaurant_id, enabled) value ('1995-02-12',43,31,6,1, true)
insert into user(id, email, name, lastname, password, role) value (7, "chef1","Chef","Chef","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_CHEF')
insert into employee(birthday,shoe_size,uniform_size,id,restaurant_id, enabled) value ('1995-02-12',43,31,7,1,true)
insert into user(id, email, name, lastname, password, role) value (8, "bartender1","Bartender","Bartender","$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi",'ROLE_BARTENDER')
insert into employee(birthday,shoe_size,uniform_size,id,restaurant_id, enabled) value ('1995-02-12',43,31,8,1, true)
insert into waiter(id) value(6)
insert into chef(id) value(7)
insert into bartender(id) value(8)
insert into guest(id) value(2)
insert into guest(id) value(3)
insert into guest(id) value(4)

insert into meal(id, description, name, price, restaurant_id) value (1,"Testenine","Spagete",380,1)
insert into meal(id, description, name, price, restaurant_id) value (2,"Supe i corbe","Teleca Corba",260,1)
insert into meal(id, description, name, price, restaurant_id) value (3,"Supe i corbe","Kokosja Supa",160,1)
insert into meal(id, description, name, price, restaurant_id) value (4,"Supe i corbe","Krem supa",220,1)
insert into meal(id, description, name, price, restaurant_id) value (5,"Salate","Grcka salata",430,1)
insert into meal(id, description, name, price, restaurant_id) value (6,"Testenine","Carbonara",500,1)
insert into drink(id, description, name, price, restaurant_id) value (1,"Viski","Jack Daniel's",220,1)
insert into drink(id, description, name, price, restaurant_id) value (2,"Viski","Johnny Walker",180,1)
insert into drink(id, description, name, price, restaurant_id) value (3,"Gazirani sokovi","Coca-Cola",150,1)
insert into drink(id, description, name, price, restaurant_id) value (4,"Gazirani sokovi","Sprite",150,1)
insert into drink(id, description, name, price, restaurant_id) value (5,"Kafa","Espresso",110,1)
insert into drink(id, description, name, price, restaurant_id) value (6,"Kafa","Cappuccino",150,1)
