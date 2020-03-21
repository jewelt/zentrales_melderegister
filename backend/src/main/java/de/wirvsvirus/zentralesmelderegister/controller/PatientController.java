package de.wirvsvirus.zentralesmelderegister.controller;

import de.wirvsvirus.zentralesmelderegister.model.DoctorDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
@Slf4j
public class PatientController {

    private final PatientService patientService;


    @GetMapping("/patient/{patient-id}")
    public PatientDTO getPatient(@PathVariable("patient-id") long patientId) {
        log.debug("request to get a patient with id " + patientId);
        return this.patientService.getPatientDTO(patientId);
    }

    @GetMapping("/patient/{patient-id}/patient")
    public List<DoctorDTO> getAllPatientsByPatient(@PathVariable("patient-id") long patientId) {
        log.debug("request to get all patients with patient id " + patientId);
        return this.patientService.getDoctorsFromPatient(patientId);
    }

    @PostMapping("/patient")
    public PatientDTO createPatient(PatientDTO patientDTO) {
        log.debug("request to create a patient: " + patientDTO.toString());
        return this.patientService.createPatientDTO(patientDTO);
    }

    @PutMapping("/patient")
    public void updatePatient(PatientDTO patientDTO) {
        log.debug("request to update a patient: " + patientDTO.toString());
        this.patientService.updatePatientDTO(patientDTO);
    }


    @DeleteMapping("/patient/{patient-id}")
    public void deletePatient(@PathVariable("patient-id") long patientId) {
        log.debug("request to delete a patient with id " + patientId);
        this.patientService.deletePatientDTO(patientId);
    }

    @PutMapping("/patient/{patient-id}/doctors")
    public void createPatient(@PathVariable("patient-id") long patientId, @RequestBody List<Long> doctorIds) {
        log.debug("request to update doctors to a patient: " + patientId);
        this.patientService.updateDoctorIds(doctorIds, patientId);
    }

    @GetMapping("/patient")
    public List<PatientDTO> getAllPatients() {
        log.debug("request to get all patients");
        return this.patientService.getAllPatients();
    }

}
