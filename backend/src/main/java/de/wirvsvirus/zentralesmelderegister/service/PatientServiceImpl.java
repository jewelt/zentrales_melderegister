package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.DoctorDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.DoctorRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.PatientRecord;
import de.wirvsvirus.zentralesmelderegister.web.errors.InternalServerErrorException;
import de.wirvsvirus.zentralesmelderegister.web.errors.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final DSLContext dslContext;
    private final UserAccountService userAccountService;

    @Override
    public PatientDTO createPatientDTO(PatientDTO patientDTO) {

        log.debug("Insert patient: " + patientDTO.toString());
        return this.dslContext.insertInto(Tables.PATIENT)
                .set(Tables.PATIENT.BIRTHDAY, OffsetDateTime.of(patientDTO.getBirthday(), LocalTime.MIDNIGHT, ZoneOffset.UTC))
                .set(Tables.PATIENT.CITY_ID, patientDTO.getCityId())
                .set(Tables.PATIENT.USER_ACCOUNT_ID, userAccountService.getCurrentUserId())
                .returning().fetchOptional().map(PatientDTO::new)
                .orElseThrow(() -> new InternalServerErrorException(
                        "Could not insert patient"));
    }

    @Override
    public PatientDTO getPatientDTO(Long patientId) {
        log.debug("Get patient with id " + patientId);
        return this.dslContext.selectFrom(Tables.PATIENT)
                .where(Tables.PATIENT.ID.eq(patientId))
                .and(Tables.PATIENT.USER_ACCOUNT_ID.eq(userAccountService.getCurrentUserId()))
                .fetchOptional()
                .map(PatientDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Test Patient with id " + patientId + " was not found."));
    }

    @Override
    public void deletePatientDTO(Long patientId) {
        log.debug("Delete patient with id " + patientId);
        final int affectedRows = this.dslContext.delete(Tables.PATIENT)
                .where(Tables.PATIENT.ID.eq(patientId))
                .and(Tables.PATIENT.USER_ACCOUNT_ID.eq(userAccountService.getCurrentUserId()))
                .execute();
        if (affectedRows == 1) {
            log.debug("Successful delete. 1 row affected");
        } else {
            log.warn(
                    "Failure while delete patient with id " + patientId);
            throw new InternalServerErrorException(
                    "Could not delete patient id " + patientId);
        }
    }

    @Override
    public void updatePatientDTO(PatientDTO patientDTO) {
        log.debug("Update patient: " + patientDTO.toString());
        final int affectedRows = this.dslContext.update(Tables.PATIENT)
                .set(Tables.PATIENT.BIRTHDAY, OffsetDateTime.of(patientDTO.getBirthday(), LocalTime.MIDNIGHT, ZoneOffset.UTC))
                .set(Tables.PATIENT.CITY_ID, patientDTO.getCityId())
                .where(Tables.PATIENT.ID.eq(patientDTO.getId()))
                .and(Tables.PATIENT.USER_ACCOUNT_ID.eq(userAccountService.getCurrentUserId()))
                .execute();

        if (affectedRows == 1) {
            log.debug("Successful update. 1 row affected");
        } else {
            log.warn(
                    "Failure while update patient with id " + patientDTO.getId());
            throw new InternalServerErrorException(
                    "Could not test patient with id " + patientDTO.getId());
        }
    }

    @Override
    public List<DoctorDTO> getDoctorsFromPatient(Long patientId) {
        log.debug("get doctors from patientid: " + patientId);

        return this.dslContext.select().from(Tables.DOCTOR)
                .join(Tables.DOCTOR_PATIENT)
                .on(Tables.DOCTOR_PATIENT.DOCTOR_ID.eq(Tables.DOCTOR.ID))
                .join(Tables.PATIENT)
                .on(Tables.PATIENT.ID.eq(Tables.DOCTOR_PATIENT.ID))
                .where(Tables.DOCTOR_PATIENT.PATIENT_ID.eq(patientId))
                .and(Tables.PATIENT.USER_ACCOUNT_ID.eq(userAccountService.getCurrentUserId()))
                .fetchInto(DoctorRecord.class)
                .stream().map(DoctorDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public void updateDoctorIds(List<Long> doctorIds, long patientId) {
        this.dslContext.delete(Tables.DOCTOR_PATIENT)
                .where(Tables.DOCTOR_PATIENT.ID.eq(patientId))
                .execute();

        if (doctorIds != null) {
            doctorIds.forEach(doctorId -> {
                this.dslContext.insertInto(Tables.DOCTOR_PATIENT)
                        .set(Tables.DOCTOR_PATIENT.PATIENT_ID, patientId)
                        .set(Tables.DOCTOR_PATIENT.DOCTOR_ID, doctorId)
                        .returning();
            });
        }
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        log.debug("get all patients");
        return this.dslContext.select().from(Tables.PATIENT)
                .where(Tables.PATIENT.USER_ACCOUNT_ID.eq(userAccountService.getCurrentUserId()))
                .fetchInto(PatientRecord.class)
                .stream().map(PatientDTO::new)
                .collect(Collectors.toList());
    }
}
