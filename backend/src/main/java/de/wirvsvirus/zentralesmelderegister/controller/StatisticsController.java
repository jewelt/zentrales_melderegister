package de.wirvsvirus.zentralesmelderegister.controller;

import de.wirvsvirus.zentralesmelderegister.model.CountByState;
import de.wirvsvirus.zentralesmelderegister.service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistic/state-now")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CountByState> getCountByStateNow() {
        return statisticsService.getCountByStateNow();
    }

}
