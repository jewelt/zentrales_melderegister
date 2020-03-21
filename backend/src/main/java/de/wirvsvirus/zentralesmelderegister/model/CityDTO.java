package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

    private Long id;
    private BigDecimal coordinatesLongitude;
    private BigDecimal coordinatesLatitude;
    private String name;
    private Long countryId;


    public CityDTO(CityRecord cityRecord) {
        this.id = cityRecord.getId();
        this.coordinatesLatitude = cityRecord.getCoordinatesLatitude();
        this.coordinatesLongitude = cityRecord.getCoordinatesLongitude();
        this.name = cityRecord.getName();
        this.countryId = cityRecord.getCountryId();
    }
}
