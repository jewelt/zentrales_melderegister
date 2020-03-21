package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestResultDTO;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.TestRecord;
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
public class TestServiceImpl implements TestService {

    private final DSLContext dslContext;

    @Override
    public TestDTO createTestDTO(TestDTO testDTO) {

        log.debug("Insert test: " + testDTO.toString());
        return this.dslContext.insertInto(Tables.TEST)
                .set(Tables.TEST.PATIENT_ID, testDTO.getPatientId())
                .set(Tables.TEST.TEST_RESULT_ID, testDTO.getTestResultId())
                .set(Tables.TEST.ENTRY_DATE, testDTO.getEntryDate())
                .set(Tables.TEST.RESULT_DATE, testDTO.getResultDate())
                .set(Tables.TEST.TEST_DATE, testDTO.getTestDate())
                .returning().fetchOptional().map(TestDTO::new)
                .orElseThrow(() -> new InternalServerErrorException(
                        "Could not insert test_result"));
    }

    @Override
    public TestDTO getTestDTO(Long testId) {
        log.debug("Get test  with id " + testId);
        return this.dslContext.selectFrom(Tables.TEST)
                .where(Tables.TEST.ID.eq(testId))
                .fetchOptional()
                .map(TestDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Test Test with id " + testId + " was not found."));
    }

    @Override
    public void deleteTestDTO(Long testId) {
        log.debug("Delete test  with id " + testId);
        final int affectedRows = this.dslContext.delete(Tables.TEST)
                .where(Tables.TEST.ID.eq(testId))
                .execute();
        if (affectedRows == 1) {
            log.debug("Successful delete. 1 row affected");
        } else {
            log.warn(
                    "Failure while delete test with id " + testId);
            throw new InternalServerErrorException(
                    "Could not delete test test id " + testId);
        }
    }

    @Override
    public void updateTest(TestDTO testDTO) {
        log.debug("update test: " + testDTO.toString());
        final int affectedRows = this.dslContext.update(Tables.TEST)
                .set(Tables.TEST.PATIENT_ID, testDTO.getPatientId())
                .set(Tables.TEST.TEST_RESULT_ID, testDTO.getTestResultId())
                .set(Tables.TEST.ENTRY_DATE, testDTO.getEntryDate())
                .set(Tables.TEST.RESULT_DATE, testDTO.getResultDate())
                .set(Tables.TEST.TEST_DATE, testDTO.getTestDate())
                .where(Tables.TEST.ID.eq(testDTO.getId()))
                .execute();

        if (affectedRows == 1) {
            log.debug("Successful update. 1 row affected");
        } else {
            log.warn(
                    "Failure while update test with id " + testDTO.getId());
            throw new InternalServerErrorException(
                    "Could not test with id " + testDTO.getId());
        }
    }

    @Override
    public List<TestDTO> getAllTests() {
        log.debug("get all tests");
        return this.dslContext.select().from(Tables.TEST)
                .fetchInto(TestRecord.class)
                .stream().map(TestDTO::new)
                .collect(Collectors.toList());
    }
}
