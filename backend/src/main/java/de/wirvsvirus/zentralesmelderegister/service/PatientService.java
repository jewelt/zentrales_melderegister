package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.DoctorDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;

import java.util.List;

public interface PatientService {
    PatientDTO createPatientDTO(final PatientDTO patientDTO);
    PatientDTO getPatientDTO(final Long patientId);
    void deletePatientDTO(final Long patientId);
    void updatePatientDTO(final PatientDTO patientDTO);

    List<DoctorDTO> getDoctorsFromPatient(final Long patientId);

    void updateDoctorIds(List<Long> doctorIds, long patientId);

    List<PatientDTO> getAllPatients();
}
