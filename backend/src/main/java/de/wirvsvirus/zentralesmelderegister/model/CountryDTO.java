package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CountryRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private Long id;
    private BigDecimal coordinatesLongitude;
    private BigDecimal coordinatesLatitude;
    private String name;
    private Long StateId;


    public CountryDTO(CountryRecord countryRecord) {
        this.id = countryRecord.getId();
        this.coordinatesLatitude = countryRecord.getCoordinatesLatitude();
        this.coordinatesLongitude = countryRecord.getCoordinatesLongitude();
        this.name = countryRecord.getName();
        this.StateId = countryRecord.getStateId();
    }
}
