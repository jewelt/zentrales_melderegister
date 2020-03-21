package de.wirvsvirus.zentralesmelderegister.controller;

import de.wirvsvirus.zentralesmelderegister.model.PatientDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestDTO;
import de.wirvsvirus.zentralesmelderegister.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
@Slf4j
public class TestController {

    private final TestService testService;


    @GetMapping("/test/{test-id}")
    public TestDTO getTest(@PathVariable("test-id") long testId) {
        log.debug("request to get a test with id " + testId);
        return this.testService.getTestDTO(testId);
    }

    @PostMapping("/test")
    public TestDTO createTest(TestDTO testDTO) {
        log.debug("request to create a test: " + testDTO.toString());
        return this.testService.createTestDTO(testDTO);
    }

    @PutMapping("/test")
    public void updateTest(TestDTO testDTO) {
        log.debug("request to update a test: " + testDTO.toString());
        this.testService.updateTest(testDTO);
    }


    @DeleteMapping("/test/{test-id}")
    public void deleteTest(@PathVariable("test-id") long testId) {
        log.debug("request to delete a test with id " + testId);
        this.testService.deleteTestDTO(testId);
    }

    @GetMapping("/test")
    public List<TestDTO> getAllTests() {
        log.debug("request to get all tests");
        return this.testService.getAllTests();
    }

}
