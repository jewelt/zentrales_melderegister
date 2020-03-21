package de.wirvsvirus.zentralesmelderegister.service;


import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestResultDTO;

import java.util.List;

public interface TestResultService {

    TestResultDTO createTestResult(final TestResultDTO testResultDTO);
    TestResultDTO getTestResultDTO(final Long testResultId);
    void deleteTestResultDTO(final Long testResultId);
    void updateTestResult(final TestResultDTO testResultDTO);

    List<TestResultDTO> getAllTestResults();

}
