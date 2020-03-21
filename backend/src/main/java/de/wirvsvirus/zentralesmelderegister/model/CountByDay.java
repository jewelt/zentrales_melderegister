package de.wirvsvirus.zentralesmelderegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CountByDay {

    private LocalDate date;
    private BigDecimal value;

}
