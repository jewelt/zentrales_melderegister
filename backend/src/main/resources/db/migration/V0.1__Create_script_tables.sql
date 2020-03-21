create table test_result
(
    id bigserial not null,
    description text not null
);

create unique index test_result_id_uindex
    on test_result (id);

alter table test_result
    add constraint test_result_pk
        primary key (id);



create table test
(
    id bigserial not null,
    entry_date timestamp with time zone not null,
    test_date timestamp with time zone not null,
    result_date timestamp with time zone not null
);

create unique index test_id_uindex
    on test (id);

alter table test
    add constraint test_pk
        primary key (id);

create table doctor
(
    id bigserial not null,
    name text not null
);

create unique index doctor_id_uindex
    on doctor (id);

alter table doctor
    add constraint doctor_pk
        primary key (id);


create table patient
(
    id bigserial not null,
    birthday timestamp with time zone not null not null
);

create unique index patient_id_uindex
    on patient (id);

alter table patient
    add constraint patient_pk
        primary key (id);


create table city
(
    id bigserial not null,
    coordinates_longitude decimal,
    coordinates_latitude decimal,
    name text not null
);

create unique index city_id_uindex
    on city (id);

alter table city
    add constraint city_pk
        primary key (id);

create table county
(
    id bigserial not null,
    coordinates_longitude decimal,
    coordinates_latitude decimal,
    name text not null
);

create unique index county_id_uindex
    on county (id);

alter table county
    add constraint county_pk
        primary key (id);

create table state
(
    id bigserial not null,
    coordinates_longitude decimal,
    coordinates_latitude decimal,
    name text not null
);

create unique index state_id_uindex
    on state (id);

alter table state
    add constraint district_pk
        primary key (id);
