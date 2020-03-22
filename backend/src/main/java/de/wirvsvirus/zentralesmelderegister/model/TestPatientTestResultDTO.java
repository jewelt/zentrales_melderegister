package de.wirvsvirus.zentralesmelderegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TestPatientTestResultDTO extends TestDTO {

    private PatientDTO patientDTO;
    private TestResultDTO testResultDTO;

    public TestPatientTestResultDTO(TestDTO testDTO) {
        super.setId(testDTO.getId());
        this.setEntryDate(testDTO.getEntryDate());
        this.setTestDate(testDTO.getTestDate());
        this.setResultDate(testDTO.getResultDate());
        this.setTestResultId(testDTO.getTestResultId());
        this.setPatientId(testDTO.getPatientId());
    }

}
