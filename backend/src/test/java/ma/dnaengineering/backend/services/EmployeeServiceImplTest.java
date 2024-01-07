
package ma.dnaengineering.backend.services;

import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;
import ma.dnaengineering.backend.models.Employee;
import ma.dnaengineering.backend.repositories.AverageSalaryByJobTitleRepository;
import ma.dnaengineering.backend.repositories.EmployeeRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private CsvParserService csvParserService;

    @Mock
    private AverageSalaryByJobTitleRepository averageSalaryByJobTitleRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void whenGetEmployees_thenReturnsPageOfEmployees() {
        // Prepare expected result
        List<Employee> employeeList = new ArrayList<>();
        Page<Employee> expectedPage = new PageImpl<>(employeeList, PageRequest.of(0, 10, Sort.by("id")),
                employeeList.size());
        given(employeeRepository.findAll(any(Pageable.class))).willReturn(expectedPage);
        Page<Employee> actualPage = employeeService.getEmployees(0, 10);
        assertThat(actualPage).isEqualTo(expectedPage);
        verify(employeeRepository).findAll(any(Pageable.class));
    }

    @Test
    public void whenUploadAndProcessCsvFile_thenValidatesAndProcesses() {
        MockMultipartFile mockCsvFile = new MockMultipartFile("data", "filename.csv", "text/csv",
                "id,employee_name,job_title,salary\n1,John Doe,Developer,60000\n2,John Doe,Developer,60000\n3,John Doe,Developer,60000\n4,John Doe,Developer,60000\n5,John Doe,Developer,60000\n6,John Doe,Developer,60000\n"
                        .getBytes());
        List<Employee> newEmployees = Arrays.asList(new Employee(1L, "John Doe", "Developer", 60000D),
                new Employee(2L, "John Doe", "Developer", 60000D), new Employee(3L, "John Doe", "Developer", 60000D),
                new Employee(4L, "John Doe", "Developer", 60000D), new Employee(5L, "John Doe", "Developer", 60000D),
                new Employee(6L, "John Doe", "Developer", 60000D));
        given(csvParserService.parseCsv(mockCsvFile)).willReturn(newEmployees);
        employeeService.uploadAndProcessCsvFile(mockCsvFile);
        verify(csvParserService).parseCsv(mockCsvFile);
        verify(employeeRepository).saveAll(newEmployees);
    }

    @Test
    public void whenGetAverageSalaryByJobTitles_thenReturnsList() {
        // Prepare expected result
        List<AverageSalaryByJobTitle> averageSalaryByJobTitleList = Arrays.asList(
                new AverageSalaryByJobTitle("developer", 65000D, 0), new AverageSalaryByJobTitle("manager", 80000D, 0),
                new AverageSalaryByJobTitle("tester", 50000D, 0));
        given(averageSalaryByJobTitleRepository.findAll()).willReturn(averageSalaryByJobTitleList);
        List<AverageSalaryByJobTitle> actualList = employeeService.getAverageSalaryByJobTitles();
        assertThat(actualList).isEqualTo(averageSalaryByJobTitleList);
        verify(averageSalaryByJobTitleRepository).findAll();
    }

}