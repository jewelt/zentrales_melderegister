/*
 * This file is generated by jOOQ.
 */
package de.wirvsvirus.zentralesmelderegister.model.jooq;


import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.City;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.Country;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.Doctor;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.DoctorPatient;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.FlywaySchemaHistory;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.Patient;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.State;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.Test;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.TestResult;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.UserAccount;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index CITY_ID_UINDEX = Indexes0.CITY_ID_UINDEX;
    public static final Index CITY_PK = Indexes0.CITY_PK;
    public static final Index COUNTY_ID_UINDEX = Indexes0.COUNTY_ID_UINDEX;
    public static final Index COUNTY_PK = Indexes0.COUNTY_PK;
    public static final Index DOCTOR_ID_UINDEX = Indexes0.DOCTOR_ID_UINDEX;
    public static final Index DOCTOR_PK = Indexes0.DOCTOR_PK;
    public static final Index DOCTOR_PATIENT_ID_UINDEX = Indexes0.DOCTOR_PATIENT_ID_UINDEX;
    public static final Index DOCTOR_PATIENT_PK = Indexes0.DOCTOR_PATIENT_PK;
    public static final Index FLYWAY_SCHEMA_HISTORY_PK = Indexes0.FLYWAY_SCHEMA_HISTORY_PK;
    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Indexes0.FLYWAY_SCHEMA_HISTORY_S_IDX;
    public static final Index PATIENT_ID_UINDEX = Indexes0.PATIENT_ID_UINDEX;
    public static final Index PATIENT_PK = Indexes0.PATIENT_PK;
    public static final Index DISTRICT_PK = Indexes0.DISTRICT_PK;
    public static final Index STATE_ID_UINDEX = Indexes0.STATE_ID_UINDEX;
    public static final Index TEST_ID_UINDEX = Indexes0.TEST_ID_UINDEX;
    public static final Index TEST_PK = Indexes0.TEST_PK;
    public static final Index TEST_RESULT_ID_UINDEX = Indexes0.TEST_RESULT_ID_UINDEX;
    public static final Index TEST_RESULT_PK = Indexes0.TEST_RESULT_PK;
    public static final Index USER_ACCOUNT_ID_UINDEX = Indexes0.USER_ACCOUNT_ID_UINDEX;
    public static final Index USER_ACCOUNT_PK = Indexes0.USER_ACCOUNT_PK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index CITY_ID_UINDEX = Internal.createIndex("city_id_uindex", City.CITY, new OrderField[] { City.CITY.ID }, true);
        public static Index CITY_PK = Internal.createIndex("city_pk", City.CITY, new OrderField[] { City.CITY.ID }, true);
        public static Index COUNTY_ID_UINDEX = Internal.createIndex("county_id_uindex", Country.COUNTRY, new OrderField[] { Country.COUNTRY.ID }, true);
        public static Index COUNTY_PK = Internal.createIndex("county_pk", Country.COUNTRY, new OrderField[] { Country.COUNTRY.ID }, true);
        public static Index DOCTOR_ID_UINDEX = Internal.createIndex("doctor_id_uindex", Doctor.DOCTOR, new OrderField[] { Doctor.DOCTOR.ID }, true);
        public static Index DOCTOR_PK = Internal.createIndex("doctor_pk", Doctor.DOCTOR, new OrderField[] { Doctor.DOCTOR.ID }, true);
        public static Index DOCTOR_PATIENT_ID_UINDEX = Internal.createIndex("doctor_patient_id_uindex", DoctorPatient.DOCTOR_PATIENT, new OrderField[] { DoctorPatient.DOCTOR_PATIENT.ID }, true);
        public static Index DOCTOR_PATIENT_PK = Internal.createIndex("doctor_patient_pk", DoctorPatient.DOCTOR_PATIENT, new OrderField[] { DoctorPatient.DOCTOR_PATIENT.ID }, true);
        public static Index FLYWAY_SCHEMA_HISTORY_PK = Internal.createIndex("flyway_schema_history_pk", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
        public static Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex("flyway_schema_history_s_idx", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS }, false);
        public static Index PATIENT_ID_UINDEX = Internal.createIndex("patient_id_uindex", Patient.PATIENT, new OrderField[] { Patient.PATIENT.ID }, true);
        public static Index PATIENT_PK = Internal.createIndex("patient_pk", Patient.PATIENT, new OrderField[] { Patient.PATIENT.ID }, true);
        public static Index DISTRICT_PK = Internal.createIndex("district_pk", State.STATE, new OrderField[] { State.STATE.ID }, true);
        public static Index STATE_ID_UINDEX = Internal.createIndex("state_id_uindex", State.STATE, new OrderField[] { State.STATE.ID }, true);
        public static Index TEST_ID_UINDEX = Internal.createIndex("test_id_uindex", Test.TEST, new OrderField[] { Test.TEST.ID }, true);
        public static Index TEST_PK = Internal.createIndex("test_pk", Test.TEST, new OrderField[] { Test.TEST.ID }, true);
        public static Index TEST_RESULT_ID_UINDEX = Internal.createIndex("test_result_id_uindex", TestResult.TEST_RESULT, new OrderField[] { TestResult.TEST_RESULT.ID }, true);
        public static Index TEST_RESULT_PK = Internal.createIndex("test_result_pk", TestResult.TEST_RESULT, new OrderField[] { TestResult.TEST_RESULT.ID }, true);
        public static Index USER_ACCOUNT_ID_UINDEX = Internal.createIndex("user_account_id_uindex", UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.ID }, true);
        public static Index USER_ACCOUNT_PK = Internal.createIndex("user_account_pk", UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.ID }, true);
    }
}
