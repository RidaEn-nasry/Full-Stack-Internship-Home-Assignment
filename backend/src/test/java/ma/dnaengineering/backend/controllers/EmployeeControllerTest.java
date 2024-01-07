package ma.dnaengineering.backend.controllers;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import org.springframework.data.domain.PageImpl;

import ma.dnaengineering.backend.exceptions.CsvProcessingException;
import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;
import ma.dnaengineering.backend.models.Employee;
import ma.dnaengineering.backend.services.EmployeeService;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void shouldUploadCsvFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv",
                "id,employee_name,job_title,salary\n1,adil,developer,60000\n2,hamza,manager,80000\n3,salma,tester,50000\n4,fatima,developer,70000"
                        .getBytes());
        mockMvc.perform(multipart("/api/employees/upload").file(file)).andExpect(status().isOk());

    }

    @Test
    public void shouldThrowExceptionWhenFileIsEmpty() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", new byte[0]);

        doThrow(new CsvProcessingException("File is empty")).when(employeeService)
                .uploadAndProcessCsvFile(any(MultipartFile.class));

        mockMvc.perform(multipart("/api/employees/upload").file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowExceptionWhenFileIsNotCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain",
                "id,employee_name,job_title,salary\n1,adil,developer,60000\n2,hamza,manager,80000\n3,salma,tester,50000\n4,fatima,developer,70000"
                        .getBytes());

        doThrow(new CsvProcessingException("File is not a csv file")).when(employeeService)
                .uploadAndProcessCsvFile(any(MultipartFile.class));

        mockMvc.perform(multipart("/api/employees/upload").file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John Doe", "Developer", 60000D));
        employees.add(new Employee(2L, "Jane Smith", "Manager", 80000D));
        employees.add(new Employee(3L, "Jack Black", "Tester", 50000D));
        Page<Employee> page = new PageImpl<>(employees);
        given(employeeService.getEmployees(anyInt(), anyInt())).willReturn(page);
        mockMvc.perform(get("/api/employees")
                .param("page", "0")
                .param("size", "10")) // Make sure parameter name matches the one expected in the controller
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAverageSalaryByJobTitle() throws Exception {
        List<AverageSalaryByJobTitle> averageSalaries = new ArrayList<>();
        averageSalaries.add(new AverageSalaryByJobTitle("developer", 65000D, 0));
        averageSalaries.add(new AverageSalaryByJobTitle("manager", 80000D, 0));
        averageSalaries.add(new AverageSalaryByJobTitle("tester", 50000D, 0));
        given(this.employeeService.getAverageSalaryByJobTitles()).willReturn(averageSalaries);
        this.mockMvc.perform(get("/api/employees/average-salary"))
                .andExpect(status().isOk());
    }

}
