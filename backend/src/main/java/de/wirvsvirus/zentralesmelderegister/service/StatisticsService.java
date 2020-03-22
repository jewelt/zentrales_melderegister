package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.*;

import java.util.List;

public interface StatisticsService {

    List<CountByState> getCountByStateNow();

    List<CountByState> getGrowthByStateToday();

    List<CountByDay> getGrowthByDate();

    List<CountByDay> getCountByDate();

    List<CountByAge> getCountByAge();

    List<TestResultDistribution> getTestResultDistribution();

    TestCaseDistribution getTestCaseDistribution();

    List<DataByDateAndLocation> getDataByDateAndLocation(SearchRequestDTO searchRequestDTO);

    void importInfections();
}
