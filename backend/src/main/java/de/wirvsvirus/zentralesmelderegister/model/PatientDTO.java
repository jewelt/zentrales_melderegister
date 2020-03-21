package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.PatientRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;
    private OffsetDateTime birthday;

    public PatientDTO(PatientRecord patientRecord) {
        this.id = patientRecord.getId();
        if (patientRecord.getBirthday() != null) {
            this.birthday = patientRecord.getBirthday();
        }

    }
}
