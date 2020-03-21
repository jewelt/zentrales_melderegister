package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestDTO;

import java.util.List;

public interface TestService {

    TestDTO createTestDTO(final TestDTO testDTO);
    TestDTO getTestDTO(final Long testId);
    void deleteTestDTO(final Long testId);
    void updateTest(final TestDTO testDTO);

    List<TestDTO> getAllTests();
}
