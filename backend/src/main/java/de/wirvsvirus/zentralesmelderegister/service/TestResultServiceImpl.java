package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestResultDTO;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.TestResult;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.TestResultRecord;
import de.wirvsvirus.zentralesmelderegister.web.errors.InternalServerErrorException;
import de.wirvsvirus.zentralesmelderegister.web.errors.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TestResultServiceImpl implements TestResultService {

    private final DSLContext dslContext;


    @Override
    public TestResultDTO createTestResult(TestResultDTO testResultDTO) {

        log.debug("Insert test result with description " + testResultDTO.getDescription());
        return this.dslContext.insertInto(Tables.TEST_RESULT)
                .set(Tables.TEST_RESULT.DESCRIPTION, testResultDTO.getDescription())
                .returning().fetchOptional().map(TestResultDTO::new)
                .orElseThrow(() -> new InternalServerErrorException(
                        "Could not insert test_result"));
    }

    @Override
    public TestResultDTO getTestResultDTO(Long testResultId) {
        log.debug("Get test result with id " + testResultId);
        return this.dslContext.selectFrom(Tables.TEST_RESULT)
                .where(Tables.TEST_RESULT.ID.eq(testResultId))
                .fetchOptional()
                .map(TestResultDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Test Result with id " + testResultId + " was not found."));
    }

    @Override
    public void deleteTestResultDTO(Long testResultId) {
        log.debug("Delete test result with id " + testResultId);
        final int affectedRows = this.dslContext.delete(Tables.TEST_RESULT)
                .where(Tables.TEST_RESULT.ID.eq(testResultId))
                .execute();
        if (affectedRows == 1) {
            log.debug("Successful delete. 1 row affected");
        } else {
            log.warn(
                    "Failure while delete test result with id " + testResultId);
            throw new InternalServerErrorException(
                    "Could not delete test result with id " + testResultId);
        }
    }


    @Override
    public void updateTestResult(TestResultDTO testResultDTO) {
        log.debug("update test result with description " + testResultDTO.getDescription());
        final int affectedRows = this.dslContext.update(Tables.TEST_RESULT)
                .set(Tables.TEST_RESULT.DESCRIPTION, testResultDTO.getDescription())
                .where(Tables.TEST_RESULT.ID.eq(testResultDTO.getId()))
                .execute();

        if (affectedRows == 1) {
            log.debug("Successful update. 1 row affected");
        } else {
            log.warn(
                    "Failure while update test resilt with id " + testResultDTO.getId());
            throw new InternalServerErrorException(
                    "Could not test result with id " + testResultDTO.getId());
        }
    }

    @Override
    public List<TestResultDTO> getAllTestResults() {
        log.debug("get all testresults");
        return this.dslContext.select().from(Tables.TEST_RESULT)
                .fetchInto(TestResultRecord.class)
                .stream().map(TestResultDTO::new)
                .collect(Collectors.toList());
    }
}
