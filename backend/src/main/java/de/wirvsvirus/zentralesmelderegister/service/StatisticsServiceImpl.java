package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CountByState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
