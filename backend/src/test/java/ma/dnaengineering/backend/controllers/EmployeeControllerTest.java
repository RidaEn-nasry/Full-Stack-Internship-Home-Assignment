package ma.dnaengineering.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ma.dnaengineering.backend.services.EmployeeService;
import ma.dnaengineering.backend.controllers.EmployeeController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
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
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());
        mockMvc.perform(multipart("/api/employees/upload").file(file)).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowExceptionWhenFileIsNotCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain",
                "id,employee_name,job_title,salary\n1,adil,developer,60000\n2,hamza,manager,80000\n3,salma,tester,50000\n4,fatima,developer,70000"
                        .getBytes());
        mockMvc.perform(multipart("/api/employees/upload").file(file)).andExpect(status().isBadRequest());
    }

}
