package ma.dnaengineering.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import ma.dnaengineering.backend.services.CsvParserServiceImpl;
import org.springframework.web.multipart.MultipartFile;
import ma.dnaengineering.backend.models.Employee;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CsvParserServiceImplTest {

    private CsvParserServiceImpl csvParserService;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() throws IOException {
        csvParserService = new CsvParserServiceImpl();
        when(file.getInputStream()).thenReturn(mockInputStreamWithValidCsv());
    }

    @Test
    public void shouldParseValidCsv() throws IOException, NumberFormatException {
        List<Employee> employees = csvParserService.parseCsv(file);
        assertNotNull(employees);
        assertEquals(6, employees.size());
        assertEquals(1, employees.get(0).getId());
        assertEquals("John Doe", employees.get(0).getEmployeeName());
        assertEquals("Developer", employees.get(0).getJobTitle());
        assertEquals(60000, employees.get(0).getSalary());
    }

    @Test
    public void shouldThrowExceptionWhenParsingInvalidCsv() throws IOException {
        when(file.getInputStream()).thenReturn(mockInputStreamWithInvalidCsv());
        assertThrows(NumberFormatException.class, () -> {
            csvParserService.parseCsv(file);
        });
    }

    @Test
    public void shouldSkipEmptyLines() throws IOException {
        when(file.getInputStream()).thenReturn(mockInputStreamWithEmptyLines());
        List<Employee> employees = csvParserService.parseCsv(file);
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    private InputStream mockInputStreamWithValidCsv() {
        String csvData = "id,employee_name,job_title,salary\n1,John Doe,Developer,60000\n2,Jane Smith,Manager,80000\n3,Jack Black,Tester,50000\n4,Hillary Klint,Developer,70000\n5,Donald Trump,Manager,90000\n6,Barack Obama,Tester,40000";
        return new ByteArrayInputStream(csvData.getBytes());
    }

    private InputStream mockInputStreamWithInvalidCsv() {
        String csvData = "id,employee_name,job_title,salary\na,123,Developer\n2,10000,9000,hello\n";
        return new ByteArrayInputStream(csvData.getBytes());
    }

    private InputStream mockInputStreamWithEmptyLines() {
        String csvData = "id,employee_name,job_title,salary\n\n\n\n1,John Doe,Developer,60000\t\n\n\t\n\r2,Jane Smith,Manager,80000\n\n\n\n";
        return new ByteArrayInputStream(csvData.getBytes());
    }

}
