alter table test
    add test_result_id bigint;

alter table test
    add constraint test_test_result_id_fk
        foreign key (test_result_id) references test_result;

alter table test
    add patient_id bigint;

alter table test
    add constraint test_patient_id_fk
        foreign key (patient_id) references patient;

alter table patient
    add city_id bigint;

alter table patient
    add constraint patient_city_id_fk
        foreign key (city_id) references city;

alter table city
    add country_id bigint;

alter table city
    add constraint city_county_id_fk
        foreign key (country_id) references county;

alter table county
    add state_id bigint;

alter table county
    add constraint county_state_id_fk
        foreign key (state_id) references state;

create table doctor_patient
(
    id bigserial not null,
    patient_id bigint not null
        constraint doctor_patient_patient_id_fk
            references patient,
    doctor_id bigint not null
        constraint doctor_patient_doctor_id_fk
            references doctor
);

create unique index doctor_patient_id_uindex
    on doctor_patient (id);

alter table doctor_patient
    add constraint doctor_patient_pk
        primary key (id);

-- auto-generated definition
create table user_account
(
    id               bigserial    not null
        constraint user_account_pk
            primary key,
    password_hash    varchar(1024),
    mail             varchar(255) not null,
    activated        boolean      not null,
    activation_key   varchar(1024),
    reset_key        varchar(1024),
    reset_date       timestamp with time zone,
    create_date_time timestamp with time zone,
    first_name       varchar(255),
    last_name        varchar(255)
);


create unique index user_account_id_uindex
    on user_account (id);

