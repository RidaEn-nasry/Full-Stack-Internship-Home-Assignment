package ma.dnaengineering.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import ma.dnaengineering.backend.services.CsvParserServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ma.dnaengineering.backend.dto.EmployeeUploadResponse;
import ma.dnaengineering.backend.models.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private CsvParserServiceImpl csvParserService;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private List<Employee> employees;

    @BeforeEach
    public void setUp() throws Exception {
        employees = new ArrayList<>();
        employees.add(new Employee(1L, "ahmed ben ali", "developer", 60000D));
        employees.add(new Employee(2L, "mohamed hamza", "manager", 80000D));
        employees.add(new Employee(3L, "salma taha", "tester", 50000D));
        employees.add(new Employee(4L, "fatima zahra", "developer", 70000D));

        when(csvParserService.parseCsv(file)).thenReturn(employees);
    }

    @Test
    public void shouldUploadAndProcessCsvFile() throws Exception {
        ResponseEntity<EmployeeUploadResponse> response = employeeService.uploadAndProcessCsvFile(file);
        assertNotNull(response);
        assertEquals(employees, response.getBody().getEmployees());
        assertEquals(3, response.getBody().getAverageSalaryByJobTitle().size());
        assertEquals(32500D, response.getBody().getAverageSalaryByJobTitle().get("developer"));
        assertEquals(20000D, response.getBody().getAverageSalaryByJobTitle().get("manager"));
    }
}