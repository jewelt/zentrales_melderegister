package de.wirvsvirus.zentralesmelderegister.controller;

import de.wirvsvirus.zentralesmelderegister.model.CountryDTO;
import de.wirvsvirus.zentralesmelderegister.model.DoctorDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.service.DoctorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;


    @GetMapping("/doctor/{doctor-id}")
    public DoctorDTO getDoctor(@PathVariable("doctor-id") long doctorId) {
        log.debug("request to get a doctor with id " + doctorId);
        return this.doctorService.getDoctorDTO(doctorId);
    }

    @GetMapping("/doctor/{doctor-id}/patient")
    public List<PatientDTO> getAllPatientsByDoctor(@PathVariable("doctor-id") long doctorId) {
        log.debug("request to get all patients with doctor id " + doctorId);
        return this.doctorService.getPatientIdsFromDoctor(doctorId);
    }

    @PostMapping("/doctor")
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        log.debug("request to create a doctor: " + doctorDTO.toString());
        return this.doctorService.createDoctorDTO(doctorDTO);
    }

    @PutMapping("/doctor")
    public void updateDoctor(DoctorDTO doctorDTO) {
        log.debug("request to update a doctor: " + doctorDTO.toString());
        this.doctorService.updateDoctorDTO(doctorDTO);
    }


    @DeleteMapping("/doctor/{doctor-id}")
    public void deleteDoctor(@PathVariable("doctor-id") long doctorId) {
        log.debug("request to delete a doctor with id " + doctorId);
        this.doctorService.deleteDoctorDTO(doctorId);
    }

    @GetMapping("/doctor")
    public List<DoctorDTO> getAllDoctors() {
        log.debug("request to get all doctors");
        return this.doctorService.getAllDoctors();
    }

}
