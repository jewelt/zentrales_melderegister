package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.StateDTO;

public interface StateService {

    StateDTO createStateDTO(final StateDTO stateDTO);
    StateDTO getStateDTO(final Long stateId);
    void deleteStateDTO(final Long stateId);
    void updateStateDTO(final StateDTO stateDTO);
}
