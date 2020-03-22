package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.CountryDTO;
import de.wirvsvirus.zentralesmelderegister.model.StateDTO;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Tables;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CountryRecord;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.StateRecord;
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
public class StateServiceImpl implements StateService{

    private final DSLContext dslContext;

    @Override
    public StateDTO createStateDTO(StateDTO stateDTO) {

        log.debug("Insert State "+ stateDTO.toString());
        return this.dslContext.insertInto(Tables.STATE)

                .set(Tables.STATE.NAME, stateDTO.getName())
                .set(Tables.STATE.COORDINATES_LATITUDE, stateDTO.getCoordinatesLatitude())
                .set(Tables.STATE.COORDINATES_LONGITUDE, stateDTO.getCoordinatesLongitude())
                .returning().fetchOptional().map(StateDTO::new)
                .orElseThrow(()-> new InternalServerErrorException(
                        "Could not insert State"
                ));
    }

    @Override
    public StateDTO getStateDTO(Long stateId) {
        log.debug("Get State with id " +stateId);
        return this.dslContext.selectFrom(Tables.STATE)
                .where(Tables.STATE.ID.eq(stateId))
                .fetchOptional()
                .map(StateDTO::new)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Test State with id "+stateId+ " was not found."
                ));
    }

    @Override
    public void deleteStateDTO(Long stateId) {
        log.debug("Remove State with id " + stateId);
        final int affectedRows = this.dslContext.delete(Tables.STATE)
                .where(Tables.STATE.ID.eq(stateId))
                .execute();
        if(affectedRows == 1){
            log.debug("Successful deleted. 1 row affected");
        }else{
            log.warn("Failure while deleting State with id " +stateId);
            throw new InternalServerErrorException(
                    "Could not delete State with id  " + stateId);

        }
    }

    @Override
    public void updateStateDTO(StateDTO stateDTO) {
        log.debug("Insert State " + stateDTO.toString());
        final int affactedRows = this.dslContext.update(Tables.STATE)
                .set(Tables.STATE.ID, stateDTO.getId())
                .set(Tables.STATE.NAME, stateDTO.getName())
                .set(Tables.STATE.COORDINATES_LONGITUDE,stateDTO.getCoordinatesLongitude())
                .set(Tables.STATE.COORDINATES_LATITUDE, stateDTO.getCoordinatesLatitude())
                .where(Tables.STATE.ID.eq(stateDTO.getId()))
                .execute();
        if(affactedRows == 1){
            log.debug("Successful deleted. 1 row affected");

        }else{
            log.warn("Failure while updating State with id " + stateDTO.getId());

            throw new InternalServerErrorException(
                    "Could not update State with id "+stateDTO.getId()
            );
        }
    }

    @Override
    public List<StateDTO> getAllStates() {
        log.debug("get all states");
        return this.dslContext.select().from(Tables.STATE)
                .fetchInto(StateRecord.class)
                .stream().map(StateDTO::new)
                .collect(Collectors.toList());
    }
}
