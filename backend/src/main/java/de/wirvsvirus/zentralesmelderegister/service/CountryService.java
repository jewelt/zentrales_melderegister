package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CityDTO;
import de.wirvsvirus.zentralesmelderegister.model.CountryDTO;

import java.util.List;

public interface CountryService {

    CountryDTO createCountryDTO(final CountryDTO countryDTO);
    CountryDTO getCountryDTO(final Long countryId);
    void deleteCountryDTO(final Long countryId);
    void updateCountry(final CountryDTO countryDTO);

    List<CountryDTO> getAllCountries();

    List<CountryDTO> getAllCountriesByStateId(long stateId);
}
