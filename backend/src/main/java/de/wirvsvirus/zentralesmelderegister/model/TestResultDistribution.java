package de.wirvsvirus.zentralesmelderegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDistribution {

    private String name;
    private BigDecimal count;

}
