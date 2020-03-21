package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CountByState;

import java.util.List;

public interface StatisticsService {

    List<CountByState> getCountByStateNow();

}
