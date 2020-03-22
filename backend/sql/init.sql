INSERT INTO public.doctor (id, name) VALUES (1, 'Dr. Mustermann');

INSERT INTO public.state (id, coordinates_longitude, coordinates_latitude, name) VALUES (1, 9.845077, 52.636704, 'Niedersachsen');

INSERT INTO public.country (id, coordinates_longitude, coordinates_latitude, name, state_id) VALUES (1, 8.2145521, 53.1434501, 'Landkreis Oldenburg', 1);

INSERT INTO public.user_account (id, password_hash, mail, activated, activation_key, reset_key, reset_date, create_date_time, first_name, last_name) VALUES (1, '$2a$10$gzIGjjKIh6sNws32WMuWRusWw/4Ahbpr.tOvqcaNBCcFbC4XbXgbC', 'max.mustermann.admin@test.test', true, '15068304250619803969', null, null, '2020-03-21 14:01:09.622000', 'Max', 'Mustermann');
INSERT INTO public.user_account (id, password_hash, mail, activated, activation_key, reset_key, reset_date, create_date_time, first_name, last_name) VALUES (2, '$2a$10$gzIGjjKIh6sNws32WMuWRusWw/4Ahbpr.tOvqcaNBCcFbC4XbXgbC', 'max.mustermann.user@test.test', true, '67151937589181788017', null, null, '2020-03-21 14:09:14.458000', 'Max', 'Mustermann');

INSERT INTO public.user_authority (authority, user_account_id) VALUES ('ADMIN', 1);
INSERT INTO public.user_authority (authority, user_account_id) VALUES ('USER', 2);

INSERT INTO public.city (id, coordinates_longitude, coordinates_latitude, name, country_id, user_account_id) VALUES (2, 8.2524538, 52.9434787, 'Großenkneten', 1, 2);

INSERT INTO public.test_result (description) values ('positiv');
INSERT INTO public.test_result (description) values ('negativ');
INSERT INTO public.test_result (description) values ('nicht auswertbar');


commit;
