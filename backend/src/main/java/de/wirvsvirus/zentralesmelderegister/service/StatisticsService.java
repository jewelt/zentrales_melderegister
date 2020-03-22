package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CountByAge;
import de.wirvsvirus.zentralesmelderegister.model.CountByDay;
import de.wirvsvirus.zentralesmelderegister.model.CountByState;
import de.wirvsvirus.zentralesmelderegister.model.TestResultDistribution;

import java.util.List;

public interface StatisticsService {

    List<CountByState> getCountByStateNow();

    List<CountByState> getGrowthByStateToday();

    List<CountByDay> getGrowthByDate();

    List<CountByDay> getCountByDate();

    List<CountByAge> getCountByAge();

    List<TestResultDistribution> getTestResultDistribution();
}
