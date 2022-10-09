create table gearset_items (
   gearset_id bigint not null,
    id bigint not null,
    item_id bigint not null,
    item_slot integer not null
);
create table gearsets (
   id bigint not null,
    gear_class varchar(50) not null,
    profile_id bigint not null,
    primary key (id)
);

alter table gearset_items
   add constraint gearset_items_gearset_id_fk
   foreign key (gearset_id)
   references gearsets (id);

alter table gearsets
   add constraint gearsets_profile_id_fk
   foreign key (profile_id)
   references profiles (id);






alter table gearset_items
   add constraint gearset_items_item_id_fk
   foreign key (item_id)
   references items_tmp (id);