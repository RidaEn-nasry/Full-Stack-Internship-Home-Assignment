package ma.dnaengineering.backend.controllers;

import org.springframework.data.domain.Page;
import java.io.IOException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ma.dnaengineering.backend.services.EmployeeService;
import ma.dnaengineering.backend.exceptions.CsvProcessingException;
import org.springframework.http.HttpStatus;
import ma.dnaengineering.backend.dto.EmployeeUploadResponse;
import ma.dnaengineering.backend.dto.ErrorResponse;
import ma.dnaengineering.backend.models.Employee;
import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "${client.url}")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int offset) {
        Page<Employee> employees = employeeService.getEmployees(page, offset);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/upload")
    public ResponseEntity<EmployeeUploadResponse> uploadAndProcessCsvFile(@RequestParam("file") MultipartFile file)
            throws IOException, CsvProcessingException {
        employeeService.uploadAndProcessCsvFile(file);
        Page<Employee> employees = employeeService.getEmployees(0, 10);
        return ResponseEntity
                .ok(new EmployeeUploadResponse(employees, employeeService.getAverageSalaryByJobTitles()));
    }

    @GetMapping("/average-salary")
    public ResponseEntity<List<AverageSalaryByJobTitle>> getAverageSalaryByJobTitles() {
        List<AverageSalaryByJobTitle> averageSalaries = employeeService.getAverageSalaryByJobTitles();
        return ResponseEntity.ok(averageSalaries);
    }

    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<?> handleCsvProcessingException(CsvProcessingException ex) {
        // Log err for debugging purposes
        // Logger.error("Error occurred while processing csv file: " + ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
