package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.*;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j


public class StatisticsServiceImpl implements StatisticsService {



    private final DataSource dataSource;
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
                    "group by s.id, s.name, p.id;").executeQuery();

            while (resultSet.next()) {
                countByStates.add(new CountByState(resultSet.getString(2), resultSet.getBigDecimal(3)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
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
                    "group by to_char(t.result_date, 'yyyy-mm-dd'), p.id\n" +
                    "order by 1 desc;").executeQuery();

            while (resultSet.next()) {
                growthByDates.add(new CountByDay(LocalDate.parse(resultSet.getString(1)), resultSet.getBigDecimal(2)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
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
                    "group by to_char(t.result_date, 'yyyy-mm-dd'), p.id\n" +
                    "order by 1 asc;").executeQuery();
            BigDecimal sumCounter = BigDecimal.ZERO;
            while (resultSet.next()) {
                final BigDecimal growthAtDay = resultSet.getBigDecimal(2);
                sumCounter = sumCounter.add(growthAtDay);
                growthByDates.add(new CountByDay(LocalDate.parse(resultSet.getString(1)), sumCounter));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
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
                    "group by extract(year FROM age(p.birthday)), p.id\n" +
                    "order by 1 desc;").executeQuery();

            while (resultSet.next()) {
                countByStates.add(new CountByAge(resultSet.getLong(1), resultSet.getBigDecimal(2)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
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
                    "group by s.id, s.name, p.id");

            preparedStatement.setString(1, dateParam);

            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                countByStates.add(new CountByState(resultSet.getString(2), resultSet.getBigDecimal(3)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return countByStates;
    }




}
