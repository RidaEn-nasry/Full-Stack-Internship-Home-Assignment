package ma.dnaengineering.backend.services;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import ma.dnaengineering.backend.exceptions.CsvProcessingException;
import ma.dnaengineering.backend.models.Employee;
import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;

public interface EmployeeService {

    public void uploadAndProcessCsvFile(MultipartFile file) throws CsvProcessingException;

    public Page<Employee> getEmployees(int page, int size);

    public List<AverageSalaryByJobTitle> getAverageSalaryByJobTitles();
}