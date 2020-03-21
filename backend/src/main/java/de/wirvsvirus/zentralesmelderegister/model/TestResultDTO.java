package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.TestResultRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDTO {

    private Long id;
    private String description;

    public TestResultDTO(final TestResultRecord testResultRecord) {
        this.id = testResultRecord.getId();
        this.description = testResultRecord.getDescription();
    }
}
