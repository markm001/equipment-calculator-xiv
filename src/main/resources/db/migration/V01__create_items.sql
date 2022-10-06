create table items_tmp (
   id bigint not null,
    item_level integer not null,
    item_slot varchar(255),
    level integer not null,
    name varchar(255) not null,
    primary key (id)
);