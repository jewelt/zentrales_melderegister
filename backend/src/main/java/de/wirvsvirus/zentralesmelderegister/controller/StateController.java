package de.wirvsvirus.zentralesmelderegister.controller;

import de.wirvsvirus.zentralesmelderegister.model.StateDTO;
import de.wirvsvirus.zentralesmelderegister.service.StateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("vi")
@Slf4j
public class StateController {

    private final StateService stateService;

    @GetMapping("/state/{state-id}")
    public StateDTO getStateDTO(@PathVariable("state-id") long stateId){
        log.debug("request to get state with id " + stateId);
        return this.stateService.getStateDTO(stateId);

    }
    @PostMapping("/state")
    public StateDTO createState(@RequestBody StateDTO stateDTO){
        log.debug("request to create a state " +stateDTO.toString());
        return this.stateService.createStateDTO(stateDTO);
    }
    @PutMapping("/state")
    public void updateState(@RequestBody StateDTO stateDTO){
        log.debug("request to update state" + stateDTO.toString());
        this.stateService.updateStateDTO(stateDTO);
    }

    @DeleteMapping("/state/{state-id}")
    public void deleteState(@PathVariable("state-id") long stateId){
        log.debug("request to delete state with id " + stateId);
        this.stateService.deleteStateDTO(stateId);
    }
}
