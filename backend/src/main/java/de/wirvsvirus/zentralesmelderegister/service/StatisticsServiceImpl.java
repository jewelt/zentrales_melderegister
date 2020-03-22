package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.*;
import de.wirvsvirus.zentralesmelderegister.model.*;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
@Slf4j


public class StatisticsServiceImpl implements StatisticsService {



    private final DataSource dataSource;
    private final PatientService patientService;
    private final TestService testService;
    private final DSLContext dslContext;

    @Override
    public List<CountByState> getCountByStateNow() {
        final List<CountByState> countByStates = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            final ResultSet resultSet = connection.prepareCall("select s.id, s.name, count(*)\n" +
                    "from test t\n" +
                    "join test_result tr on tr.id = t.test_result_id\n" +
                    "join \"patient\" p on t.\"patient_id\" = p.\"id\"\n" +
                    "join \"city\" c2 on p.\"city_id\" = c2.\"id\"\n" +
                    "join \"country\" c3 on c2.\"country_id\" = c3.\"id\"\n" +
                    "join \"state\" s on c3.\"state_id\" = s.\"id\"\n" +
                    "where tr.description = 'positiv'\n" +
                    "group by s.id, s.name;").executeQuery();

            while (resultSet.next()) {
                countByStates.add(new CountByState(resultSet.getString(2), resultSet.getBigDecimal(3)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return countByStates;
    }

    @Override
    public List<CountByState> getGrowthByStateToday() {
        final String dateParam = LocalDate.now().toString();

        return getGrowthByStateToday(dateParam);
    }

    @Override
    public List<CountByDay> getGrowthByDate() {
        final List<CountByDay> growthByDates = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            final ResultSet resultSet = connection.prepareCall("select to_char(t.result_date, 'yyyy-mm-dd'),  count(*)\n" +
                    "from test t\n" +
                    "         join test_result tr on tr.id = t.test_result_id\n" +
                    "         join \"patient\" p on t.\"patient_id\" = p.\"id\"\n" +
                    "         join \"city\" c2 on p.\"city_id\" = c2.\"id\"\n" +
                    "         join \"country\" c3 on c2.\"country_id\" = c3.\"id\"\n" +
                    "         join \"state\" s on c3.\"state_id\" = s.\"id\"\n" +
                    "where tr.description = 'positiv'\n" +
                    "group by to_char(t.result_date, 'yyyy-mm-dd')\n" +
                    "order by 1 desc;").executeQuery();

            while (resultSet.next()) {
                growthByDates.add(new CountByDay(LocalDate.parse(resultSet.getString(1)), resultSet.getBigDecimal(2)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return growthByDates;
    }

    @Override
    public List<CountByDay> getCountByDate() {
        final List<CountByDay> growthByDates = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            final ResultSet resultSet = connection.prepareCall("select to_char(t.result_date, 'yyyy-mm-dd'),  count(*)\n" +
                    "from test t\n" +
                    "         join test_result tr on tr.id = t.test_result_id\n" +
                    "         join \"patient\" p on t.\"patient_id\" = p.\"id\"\n" +
                    "         join \"city\" c2 on p.\"city_id\" = c2.\"id\"\n" +
                    "         join \"country\" c3 on c2.\"country_id\" = c3.\"id\"\n" +
                    "         join \"state\" s on c3.\"state_id\" = s.\"id\"\n" +
                    "where tr.description = 'positiv'\n" +
                    "group by to_char(t.result_date, 'yyyy-mm-dd')\n" +
                    "order by 1 asc;").executeQuery();
            BigDecimal sumCounter = BigDecimal.ZERO;
            while (resultSet.next()) {
                final BigDecimal growthAtDay = resultSet.getBigDecimal(2);
                sumCounter = sumCounter.add(growthAtDay);
                growthByDates.add(new CountByDay(LocalDate.parse(resultSet.getString(1)), sumCounter));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return growthByDates;
    }

    @Override
    public List<CountByAge> getCountByAge() {
        final List<CountByAge> countByStates = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            final ResultSet resultSet = connection.prepareCall("select extract(year FROM age(p.birthday)),  count(*)\n" +
                    "from test t\n" +
                    "         join test_result tr on tr.id = t.test_result_id\n" +
                    "         join \"patient\" p on t.\"patient_id\" = p.\"id\"\n" +
                    "         join \"city\" c2 on p.\"city_id\" = c2.\"id\"\n" +
                    "         join \"country\" c3 on c2.\"country_id\" = c3.\"id\"\n" +
                    "         join \"state\" s on c3.\"state_id\" = s.\"id\"\n" +
                    "where tr.description = 'positiv'\n" +
                    "group by extract(year FROM age(p.birthday))\n" +
                    "order by 1 desc;").executeQuery();

            while (resultSet.next()) {
                countByStates.add(new CountByAge(resultSet.getLong(1), resultSet.getBigDecimal(2)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return countByStates;
    }

    @Override
    public List<TestResultDistribution> getTestResultDistribution() {
        final List<TestResultDistribution> testResultDistribution = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            final ResultSet resultSet = connection.prepareCall("select tr.description,  count(*)\n" +
                    "from test t\n" +
                    "         join test_result tr on tr.id = t.test_result_id\n" +
                    "         join \"patient\" p on t.\"patient_id\" = p.\"id\"\n" +
                    "         join \"city\" c2 on p.\"city_id\" = c2.\"id\"\n" +
                    "         join \"country\" c3 on c2.\"country_id\" = c3.\"id\"\n" +
                    "         join \"state\" s on c3.\"state_id\" = s.\"id\"\n" +
                    "group by tr.description\n" +
                    "order by 1 desc;").executeQuery();

            while (resultSet.next()) {
                testResultDistribution.add(new TestResultDistribution(resultSet.getString(1), resultSet.getBigDecimal(2)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return testResultDistribution;
    }

    @Override
    public TestCaseDistribution getTestCaseDistribution() {
        final TestCaseDistribution testCaseDistribution = new TestCaseDistribution();



         testCaseDistribution.setPositiv(BigDecimal.valueOf(this.dslContext.selectCount().from(Tables.TEST_RESULT)
                  .where(Tables.TEST_RESULT.DESCRIPTION.lower().trim().eq("positiv"))
                 .fetchOne(0,Long.class)));

         testCaseDistribution.setNegativ(BigDecimal.valueOf(this.dslContext.selectCount().from(Tables.TEST_RESULT)
                    .where(Tables.TEST_RESULT.DESCRIPTION.lower().trim().eq("negativ"))
                 .fetchOne(0,Long.class)));


         testCaseDistribution.setTests(testCaseDistribution.getNegativ().add(testCaseDistribution.getPositiv()));

         return testCaseDistribution;
    }

    @Override
    public List<DataByDateAndLocation> getDataByDateAndLocation(SearchRequestDTO searchRequestDTO) {
        final List<DataByDateAndLocation> dataByDateAndLocation = new ArrayList<>();
        final long days = ChronoUnit.DAYS.between(searchRequestDTO.getStartDate(), searchRequestDTO.getEndDate());


        for(long i =0; i<=days; i++){
                DataByDateAndLocation today = new DataByDateAndLocation();
                today.setDate(searchRequestDTO.getStartDate().plusDays(i));

                today.setGrowth(BigDecimal.valueOf(dslContext.selectCount().from(Tables.TEST)
                        .join(Tables.PATIENT)
                        .on(Tables.PATIENT.ID.eq(Tables.TEST.PATIENT_ID))
                        .join(Tables.CITY)
                        .on(Tables.CITY.ID.eq(Tables.PATIENT.CITY_ID))
                        .join(Tables.COUNTRY)
                        .on(Tables.COUNTRY.ID.eq(Tables.CITY.COUNTRY_ID))
                        .where(Tables.COUNTRY.ID.eq(searchRequestDTO.getCountryId()))
                        .and(Tables.TEST.RESULT_DATE.eq(today.getDate()))
                        .fetchOne(0,Long.class)));

            today.setTotal(BigDecimal.valueOf(dslContext.selectCount().from(Tables.TEST)
                    .join(Tables.PATIENT)
                    .on(Tables.PATIENT.ID.eq(Tables.TEST.PATIENT_ID))
                    .join(Tables.CITY)
                    .on(Tables.CITY.ID.eq(Tables.PATIENT.CITY_ID))
                    .join(Tables.COUNTRY)
                    .on(Tables.COUNTRY.ID.eq(Tables.CITY.COUNTRY_ID))
                    .where(Tables.COUNTRY.ID.eq(searchRequestDTO.getCountryId()))
                    .and(Tables.TEST.RESULT_DATE.lessOrEqual(today.getDate()))
                    .fetchOne(0,Long.class)));

                dataByDateAndLocation.add(today);






        }
        return dataByDateAndLocation;

    }


    @Override
    public void importInfections() {
        try (final Connection connection = dataSource.getConnection()) {
            final ResultSet resultSet = connection.prepareCall("select ci.infections, city.id, test_result.id\n" +
                    "from current_infections ci\n" +
                    "join country c on trim(lower(c.name)) = trim(lower(ci.gen))\n" +
                    "    join city on c.\"id\" = \"city\".\"country_id\"\n" +
                    "join test_result on test_result.description = 'positiv'").executeQuery();


            while (resultSet.next()) {

                final long infectionsInCity = resultSet.getLong(1);
                final long cityId = resultSet.getLong(2);
                final long testResultId = resultSet.getLong(3);

                log.debug("Inserting " + infectionsInCity + " tests in city " + cityId);

                int summeInCity = 0;
                for (int i = 2; i <= 31; i++) {
                    final int infectionsAtDay = (int) ((0.01 * Math.pow(1.16591, i) - 0.01 * Math.pow(1.16591, i - 1)) * infectionsInCity);

//                    log.debug("infections per day: " + i + ": " + infectionsAtDay);

                    summeInCity = summeInCity + infectionsAtDay;

                    for (int j = 0; j < infectionsAtDay; j++) {
                        final LocalDate randomBirthday = randomDate(LocalDate.parse("1930-03-22").atStartOfDay().toInstant(ZoneOffset.UTC),
                                LocalDate.parse("2020-03-21").atStartOfDay().toInstant(ZoneOffset.UTC));

                        final LocalDate testDate = LocalDate.now().minusDays(31 - i);

                        final PatientDTO patientDTO = new PatientDTO();
                        patientDTO.setBirthday(randomBirthday);
                        patientDTO.setCityId(cityId);

                        final PatientDTO createdPatient = patientService.createPatientDTO(patientDTO);


                        final TestDTO testDTO = new TestDTO();
                        testDTO.setEntryDate(testDate.atTime(OffsetTime.now()));
                        testDTO.setResultDate(testDate.atTime(OffsetTime.now()));
                        testDTO.setTestDate(testDate.atTime(OffsetTime.now()));
                        testDTO.setPatientId(createdPatient.getId());
                        testDTO.setTestResultId(testResultId);

                        testService.createTestDTO(testDTO);

                    }
                }
                log.debug("Verlgeich " + summeInCity);


            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private LocalDate randomDate(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private List<CountByState> getGrowthByStateToday(String dateParam) {
        final List<CountByState> countByStates = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("select s.id, s.name, count(*)\n" +
                    "from test t\n" +
                    "join test_result tr on tr.id = t.test_result_id\n" +
                    "join \"patient\" p on t.\"patient_id\" = p.\"id\"\n" +
                    "join \"city\" c2 on p.\"city_id\" = c2.\"id\"\n" +
                    "join \"country\" c3 on c2.\"country_id\" = c3.\"id\"\n" +
                    "join \"state\" s on c3.\"state_id\" = s.\"id\"\n" +
                    "where tr.description = 'positiv'\n" +
                    "and to_char(t.result_date, 'yyyy-mm-dd') = ?\n" +
                    "group by s.id, s.name");

            preparedStatement.setString(1, dateParam);

            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                countByStates.add(new CountByState(resultSet.getString(2), resultSet.getBigDecimal(3)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return countByStates;
    }




}
