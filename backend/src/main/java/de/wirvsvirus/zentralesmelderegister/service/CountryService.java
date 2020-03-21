package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CountryDTO;

public interface CountryService {

    CountryDTO createCountryDTO(final CountryDTO countryDTO);
    CountryDTO getCountryDTO(final Long countryId);
    void deleteCountryDTO(final Long countryId);
    void updateCountry(final CountryDTO countryDTO);
}
