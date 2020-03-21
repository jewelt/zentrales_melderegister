package de.wirvsvirus.zentralesmelderegister.model;

import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.StateRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
    private Long id;
    private BigDecimal coordinatesLongitude;
    private BigDecimal coordinatesLatitude;
    private String name;

    public StateDTO(StateRecord stateRecord){
        this.coordinatesLatitude = stateRecord.getCoordinatesLatitude();
        this.coordinatesLongitude = stateRecord.getCoordinatesLongitude();
        this.name = stateRecord.getName();
        this.id = stateRecord.getId();
    }
}
