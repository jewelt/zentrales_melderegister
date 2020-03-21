package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;

import java.util.List;

public interface CityService {

    CityDTO createCityDTO(final CityDTO cityDTO);
    CityDTO getCityDTO(final Long cityId);
    void deleteCityDTO(final Long cityId);
    void updateCity(final CityDTO cityDTO);

    List<CityDTO> getAllCities();


    List<CityDTO> getAllCitiesByCountryId(long countryId);
}
