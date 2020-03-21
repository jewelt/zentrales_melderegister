package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.DoctorDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTO createDoctorDTO(final DoctorDTO doctorDTO);
    DoctorDTO getDoctorDTO(final Long doctorId);
    void deleteDoctorDTO(final Long doctorId);
    void updateDoctorDTO(final DoctorDTO doctorDTO);

    List<PatientDTO> getPatientIdsFromDoctor(final Long doctorId);

    List<DoctorDTO> getAllDoctors();
}
