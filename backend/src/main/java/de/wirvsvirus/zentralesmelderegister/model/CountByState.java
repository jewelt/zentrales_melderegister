package de.wirvsvirus.zentralesmelderegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CountByState {

    private String name;
    private BigDecimal value;

}
