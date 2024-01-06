package ma.dnaengineering.backend.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ma.dnaengineering.backend.dto.EmployeeUploadResponse;
import ma.dnaengineering.backend.exceptions.CsvProcessingException;
import ma.dnaengineering.backend.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ma.dnaengineering.backend.services.CsvParserService;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private CsvParserService csvParserService;

    @Override
    public ResponseEntity<EmployeeUploadResponse> uploadAndProcessCsvFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CsvProcessingException("File is empty");
        }
        if (!file.getContentType().equals("text/csv")) {
            throw new CsvProcessingException("File is not csv");
        }

        List<Employee> employees = null;
        try {
            employees = csvParserService.parseCsv(file);
        } catch (IOException | NumberFormatException e) {
            throw new CsvProcessingException("Error occurred while processing csv file: " + e.getMessage());
        }
        Map<String, Double> averageSalaryByJobTitle = getAverageSalaryByJobTitle(employees);
        return ResponseEntity.ok(new EmployeeUploadResponse(employees, averageSalaryByJobTitle));
    }

    private Map<String, Double> getAverageSalaryByJobTitle(List<Employee> employees) {
        Map<String, Double> averageSalaryByJobTitle = new HashMap<>();
        for (Employee employee : employees) {
            String jobTitle = employee.getJobTitle();
            Double salary = employee.getSalary();
            if (averageSalaryByJobTitle.containsKey(jobTitle)) {
                Double averageSalary = averageSalaryByJobTitle.get(jobTitle);
                averageSalaryByJobTitle.put(jobTitle, averageSalary + salary);
            } else {
                averageSalaryByJobTitle.put(jobTitle, salary);
            }
        }
        for (Map.Entry<String, Double> entry : averageSalaryByJobTitle.entrySet()) {
            Double averageSalary = entry.getValue() / employees.size();
            entry.setValue(averageSalary);
        }
        return averageSalaryByJobTitle;
    }

}
