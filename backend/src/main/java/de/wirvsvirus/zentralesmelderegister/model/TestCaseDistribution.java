package de.wirvsvirus.zentralesmelderegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDistribution {

    private BigDecimal tests;
    private BigDecimal positiv;
    private BigDecimal negativ;

}
