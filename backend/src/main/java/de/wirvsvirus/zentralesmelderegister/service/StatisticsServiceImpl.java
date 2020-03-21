package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CountByAge;
import de.wirvsvirus.zentralesmelderegister.model.CountByDay;
import de.wirvsvirus.zentralesmelderegister.model.CountByState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final DataSource dataSource;

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
        }

        return countByStates;
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
        }
        return countByStates;
    }
}
