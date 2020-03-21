package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.TestRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime entryDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime testDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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
