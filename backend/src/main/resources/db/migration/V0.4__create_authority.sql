create table user_authority
(
    authority       varchar(255) not null,
    user_account_id bigint       not null
        constraint user_authority___fk___user_accocunt
            references user_account
);

alter table city
    add user_account_id bigint not null;

alter table city
    add constraint city_user_account_id_fk
        foreign key (user_account_id) references user_account;


