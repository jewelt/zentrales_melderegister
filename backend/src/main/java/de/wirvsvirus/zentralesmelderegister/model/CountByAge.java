package de.wirvsvirus.zentralesmelderegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CountByAge {

    private Long age;
    private BigDecimal value;

}
