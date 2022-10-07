create table item_job_categories (
       item_id bigint not null,
       job_categories varchar(255)not null
);

alter table item_job_categories
       add constraint item_job_categories_item_id_fk
       foreign key (item_id)
       references items_tmp (id);