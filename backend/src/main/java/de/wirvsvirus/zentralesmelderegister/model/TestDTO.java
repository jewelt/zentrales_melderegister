package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.TestRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    private Long id;
    private OffsetDateTime entryDate;
    private OffsetDateTime testDate;
    private OffsetDateTime resultDate;
    private Long testResultId;
    private Long patientId;

    public TestDTO(TestRecord testRecord) {
        this.id = testRecord.getId();
        this.entryDate = testRecord.getEntryDate();
        this.testDate = testRecord.getTestDate();
        this.resultDate = testRecord.getResultDate();
        this.testResultId = testRecord.getTestResultId();
        this.patientId = testRecord.getPatientId();
    }
}
