package de.wirvsvirus.zentralesmelderegister.controller;

import de.wirvsvirus.zentralesmelderegister.model.TestDTO;
import de.wirvsvirus.zentralesmelderegister.model.TestResultDTO;
import de.wirvsvirus.zentralesmelderegister.service.TestResultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
@Slf4j
public class TestResultController {

    private final TestResultService testResultService;


    @GetMapping("/test-result/{test-result-id}")
    public TestResultDTO getTestResult(@PathVariable("test-result-id") long testResultId) {
        log.debug("request to get a test_result with id " + testResultId);
        return this.testResultService.getTestResultDTO(testResultId);
    }

    @PostMapping("/test-result")
    public TestResultDTO createTestResult(TestResultDTO testResultDTO) {
        log.debug("request to create a test_result: " + testResultDTO.toString());
        return this.testResultService.createTestResult(testResultDTO);
    }

    @PutMapping("/test-result")
    public void updateTestResult(TestResultDTO testResultDTO) {
        log.debug("request to update a test_result: " + testResultDTO.toString());
        this.testResultService.updateTestResult(testResultDTO);
    }


    @DeleteMapping("/test-result/{test-result-id}")
    public void deleteTestResult(@PathVariable("test-result-id") long testResultId) {
        log.debug("request to delete a test_result with id " + testResultId);
        this.testResultService.deleteTestResultDTO(testResultId);
    }

    @GetMapping("/test-result")
    public List<TestResultDTO> getAllTestResults() {
        log.debug("request to get all test results");
        return this.testResultService.getAllTestResults();
    }

}
