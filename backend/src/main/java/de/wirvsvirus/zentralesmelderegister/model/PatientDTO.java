package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.PatientRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    private Long cityId;

    public PatientDTO(PatientRecord patientRecord) {
        this.id = patientRecord.getId();
        if (patientRecord.getBirthday() != null) {
            this.birthday = patientRecord.getBirthday().toLocalDate();
        }
        this.cityId = patientRecord.getCityId();
    }
}
