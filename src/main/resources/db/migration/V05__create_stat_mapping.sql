create table stat_value_mapping (
       item_id bigint not null,
       stat_value integer not null,
       stat_name varchar(255) not null,
       primary key (item_id, stat_name)
);

alter table stat_value_mapping
       add constraint stat_value_mapping_item_id_fk
       foreign key (item_id)
       references items_tmp (id);