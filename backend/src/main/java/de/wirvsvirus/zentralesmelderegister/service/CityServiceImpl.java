package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.web.errors.InternalServerErrorException;
import de.wirvsvirus.zentralesmelderegister.web.errors.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final DSLContext dslContext;

    @Override
    public CityDTO createCityDTO(CityDTO cityDTO) {

        log.debug("Insert city: " + cityDTO.toString());
        return this.dslContext.insertInto(Tables.CITY)
                .set(Tables.CITY.COUNTRY_ID, cityDTO.getCountryId())
                .set(Tables.CITY.COORDINATES_LATITUDE, cityDTO.getCoordinatesLatitude())
                .set(Tables.CITY.COORDINATES_LONGITUDE, cityDTO.getCoordinatesLongitude())
                .set(Tables.CITY.NAME, cityDTO.getName())
                .returning().fetchOptional().map(CityDTO::new)
                .orElseThrow(() -> new InternalServerErrorException(
                        "Could not insert test_result"));
    }

    @Override
    public CityDTO getCityDTO(Long cityId) {
        log.debug("Get test result with id " + cityId);
        return this.dslContext.selectFrom(Tables.CITY)
                .where(Tables.CITY.ID.eq(cityId))
                .fetchOptional()
                .map(CityDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Test City with id " + cityId + " was not found."));
    }

    @Override
    public void deleteCityDTO(Long cityId) {
        log.debug("Delete test result with id " + cityId);
        final int affectedRows = this.dslContext.delete(Tables.CITY)
                .where(Tables.CITY.ID.eq(cityId))
                .execute();
        if (affectedRows == 1) {
            log.debug("Successful delete. 1 row affected");
        } else {
            log.warn(
                    "Failure while delete city with id " + cityId);
            throw new InternalServerErrorException(
                    "Could not delete test city id " + cityId);
        }
    }

    @Override
    public void updateCity(CityDTO cityDTO) {
        log.debug("Insert city: " + cityDTO.toString());
        final int affectedRows = this.dslContext.update(Tables.TEST_RESULT)
                .set(Tables.CITY.COUNTRY_ID, cityDTO.getCountryId())
                .set(Tables.CITY.COORDINATES_LATITUDE, cityDTO.getCoordinatesLatitude())
                .set(Tables.CITY.COORDINATES_LONGITUDE, cityDTO.getCoordinatesLongitude())
                .set(Tables.CITY.NAME, cityDTO.getName())
                .where(Tables.CITY.ID.eq(cityDTO.getId()))
                .execute();

        if (affectedRows == 1) {
            log.debug("Successful update. 1 row affected");
        } else {
            log.warn(
                    "Failure while update city with id " + cityDTO.getId());
            throw new InternalServerErrorException(
                    "Could not city with id " + cityDTO.getId());
        }
    }
}
