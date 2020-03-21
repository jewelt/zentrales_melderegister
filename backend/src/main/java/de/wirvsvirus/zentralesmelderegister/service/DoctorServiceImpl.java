package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.DoctorDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestResultDTO;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.DoctorRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.PatientRecord;
import de.wirvsvirus.zentralesmelderegister.web.errors.InternalServerErrorException;
import de.wirvsvirus.zentralesmelderegister.web.errors.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DSLContext dslContext;

    @Override
    public DoctorDTO createDoctorDTO(DoctorDTO doctorDTO) {

        log.debug("Insert doctor: " + doctorDTO.toString());
        return this.dslContext.insertInto(Tables.DOCTOR)
                .set(Tables.DOCTOR.NAME, doctorDTO.getName())
                .returning().fetchOptional().map(DoctorDTO::new)
                .orElseThrow(() -> new InternalServerErrorException(
                        "Could not insert doctor"));
    }

    @Override
    public DoctorDTO getDoctorDTO(Long doctorId) {
        log.debug("Get doctor with id " + doctorId);
        return this.dslContext.selectFrom(Tables.DOCTOR)
                .where(Tables.DOCTOR.ID.eq(doctorId))
                .fetchOptional()
                .map(DoctorDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Test Doctor with id " + doctorId + " was not found."));
    }

    @Override
    public void deleteDoctorDTO(Long doctorId) {
        log.debug("Delete doctor with id " + doctorId);
        final int affectedRows = this.dslContext.delete(Tables.DOCTOR)
                .where(Tables.DOCTOR.ID.eq(doctorId))
                .execute();
        if (affectedRows == 1) {
            log.debug("Successful delete. 1 row affected");
        } else {
            log.warn(
                    "Failure while delete doctor with id " + doctorId);
            throw new InternalServerErrorException(
                    "Could not delete doctor id " + doctorId);
        }
    }

    @Override
    public void updateDoctorDTO(DoctorDTO doctorDTO) {
        log.debug("Update test result with name " + doctorDTO.getName());
        final int affectedRows = this.dslContext.update(Tables.DOCTOR)
                .set(Tables.DOCTOR.NAME, doctorDTO.getName())
                .where(Tables.DOCTOR.ID.eq(doctorDTO.getId()))
                .execute();

        if (affectedRows == 1) {
            log.debug("Successful update. 1 row affected");
        } else {
            log.warn(
                    "Failure while update doctor with id " + doctorDTO.getId());
            throw new InternalServerErrorException(
                    "Could not test doctor with id " + doctorDTO.getId());
        }
    }

    @Override
    public List<PatientDTO> getPatientIdsFromDoctor(Long doctorId) {
        log.debug("get patients from doctorid: " + doctorId);

        return this.dslContext.select().from(Tables.PATIENT)
                .join(Tables.DOCTOR_PATIENT)
                .on(Tables.DOCTOR_PATIENT.PATIENT_ID.eq(Tables.PATIENT.ID))
                .where(Tables.DOCTOR_PATIENT.DOCTOR_ID.eq(doctorId))
                .fetchInto(PatientRecord.class)
                .stream().map(PatientDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        log.debug("get all doctors");
        return this.dslContext.select().from(Tables.DOCTOR)
                .fetchInto(DoctorRecord.class)
                .stream().map(DoctorDTO::new)
                .collect(Collectors.toList());
    }
}
