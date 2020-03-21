package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.CountryDTO;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CountryRecord;
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
public class CountryServiceImpl implements CountryService {

    private final DSLContext dslContext;

    @Override
    public CountryDTO createCountryDTO(CountryDTO countryDTO) {

        log.debug("Insert country: " + countryDTO.toString());
        return this.dslContext.insertInto(Tables.COUNTRY)
                .set(Tables.COUNTRY.STATE_ID, countryDTO.getStateId())
                .set(Tables.COUNTRY.COORDINATES_LATITUDE, countryDTO.getCoordinatesLatitude())
                .set(Tables.COUNTRY.COORDINATES_LONGITUDE, countryDTO.getCoordinatesLongitude())
                .set(Tables.COUNTRY.NAME, countryDTO.getName())
                .returning().fetchOptional().map(CountryDTO::new)
                .orElseThrow(() -> new InternalServerErrorException(
                        "Could not insert country"));
    }

    @Override
    public CountryDTO getCountryDTO(Long countryId) {
        log.debug("Get test result with id " + countryId);
        return this.dslContext.selectFrom(Tables.COUNTRY)
                .where(Tables.COUNTRY.ID.eq(countryId))
                .fetchOptional()
                .map(CountryDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Test Country with id " + countryId + " was not found."));
    }

    @Override
    public void deleteCountryDTO(Long countryId) {
        log.debug("Delete test result with id " + countryId);
        final int affectedRows = this.dslContext.delete(Tables.COUNTRY)
                .where(Tables.COUNTRY.ID.eq(countryId))
                .execute();
        if (affectedRows == 1) {
            log.debug("Successful delete. 1 row affected");
        } else {
            log.warn(
                    "Failure while delete country with id " + countryId);
            throw new InternalServerErrorException(
                    "Could not delete test country id " + countryId);
        }
    }

    @Override
    public void updateCountry(CountryDTO countryDTO) {
        log.debug("update country: " + countryDTO.toString());
        final int affectedRows = this.dslContext.update(Tables.TEST_RESULT)
                .set(Tables.COUNTRY.STATE_ID, countryDTO.getStateId())
                .set(Tables.COUNTRY.COORDINATES_LATITUDE, countryDTO.getCoordinatesLatitude())
                .set(Tables.COUNTRY.COORDINATES_LONGITUDE, countryDTO.getCoordinatesLongitude())
                .set(Tables.COUNTRY.NAME, countryDTO.getName())
                .where(Tables.COUNTRY.ID.eq(countryDTO.getId()))
                .execute();

        if (affectedRows == 1) {
            log.debug("Successful update. 1 row affected");
        } else {
            log.warn(
                    "Failure while update country with id " + countryDTO.getId());
            throw new InternalServerErrorException(
                    "Could not country with id " + countryDTO.getId());
        }
    }

    @Override
    public List<CountryDTO> getAllCountries() {
        log.debug("get all countries");
        return this.dslContext.select().from(Tables.COUNTRY)
                .fetchInto(CountryRecord.class)
                .stream().map(CountryDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CountryDTO> getAllCountriesByStateId(long stateId) {

        log.debug("get all countries by state id: " + stateId);
        return this.dslContext.select().from(Tables.COUNTRY)
                .where(Tables.COUNTRY.STATE_ID.eq(stateId))
                .fetchInto(CountryRecord.class)
                .stream().map(CountryDTO::new)
                .collect(Collectors.toList());
    }
}
