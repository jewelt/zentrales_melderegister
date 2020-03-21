package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.DoctorRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private Long id;
    private String name;

    public DoctorDTO(DoctorRecord doctorRecord) {
        this.id = doctorRecord.getId();
        this.name = doctorRecord.getName();
    }
}
