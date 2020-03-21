alter table patient
    add user_account_id bigint not null;

alter table patient
    add constraint patient_user_account_id_fk
        foreign key (user_account_id) references user_account;

