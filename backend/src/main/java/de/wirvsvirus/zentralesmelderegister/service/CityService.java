package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;

public interface CityService {

    CityDTO createCityDTO(final CityDTO cityDTO);
    CityDTO getCityDTO(final Long cityId);
    void deleteCityDTO(final Long cityId);
    void updateCity(final CityDTO cityDTO);


}
