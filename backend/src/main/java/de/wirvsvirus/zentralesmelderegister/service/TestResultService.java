package de.wirvsvirus.zentralesmelderegister.service;


import de.wirvsvirus.zentralesmelderegister.model.TestResultDTO;

public interface TestResultService {

    TestResultDTO createTestResult(final TestResultDTO testResultDTO);
    TestResultDTO getTestResultDTO(final Long testResultId);
    void deleteTestResultDTO(final Long testResultId);
    void updateTestResult(final TestResultDTO testResultDTO);

}
