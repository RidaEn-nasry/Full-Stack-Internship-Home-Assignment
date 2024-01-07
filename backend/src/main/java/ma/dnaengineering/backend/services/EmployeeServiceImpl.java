package ma.dnaengineering.backend.services;

import java.util.List;
import ma.dnaengineering.backend.exceptions.CsvProcessingException;
import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;
import ma.dnaengineering.backend.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ma.dnaengineering.backend.repositories.EmployeeRepository;
import ma.dnaengineering.backend.repositories.AverageSalaryByJobTitleRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final CsvParserService csvParserService;
    private final AverageSalaryByJobTitleRepository averageSalaryByJobTitleRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(CsvParserService csvParserService,
            AverageSalaryByJobTitleRepository averageSalaryByJobTitleRepository,
            EmployeeRepository employeeRepository) {
        this.csvParserService = csvParserService;
        this.averageSalaryByJobTitleRepository = averageSalaryByJobTitleRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Page<Employee> getEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return employeeRepository.findAll(pageable);
    }

    private void validateCsvFile(MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("File is empty");
            throw new CsvProcessingException("File is empty");

        }
        if (!file.getContentType().equals("text/csv")) {
            throw new CsvProcessingException("File is not a csv file");
        }

    }

    @Transactional
    @Override
    public void uploadAndProcessCsvFile(MultipartFile file) throws CsvProcessingException {
        validateCsvFile(file);
        List<Employee> newEmployees = csvParserService.parseCsv(file);
        employeeRepository.saveAll(newEmployees);
        updateAverageSalaries(newEmployees);
    }

    @Transactional
    @Override
    public List<AverageSalaryByJobTitle> getAverageSalaryByJobTitles() {
        List<AverageSalaryByJobTitle> averageSalaries = averageSalaryByJobTitleRepository.findAll();
        return averageSalaries;
    }

    private void updateAverageSalaries(List<Employee> newEmployees) {
        for (Employee newEmployee : newEmployees) {
            String jobTitle = newEmployee.getJobTitle();
            double newSalary = newEmployee.getSalary();
            AverageSalaryByJobTitle averageSalaryByJobTitle = averageSalaryByJobTitleRepository
                    .findByJobTitle(jobTitle)
                    .orElse(new AverageSalaryByJobTitle(jobTitle, 0.0, 0));

            double totalSalary = averageSalaryByJobTitle.getAverageSalary()
                    * averageSalaryByJobTitle.getEmployeeCount();
            int employeeCount = averageSalaryByJobTitle.getEmployeeCount();

            totalSalary += newSalary;
            employeeCount++;

            double newAverageSalary = totalSalary / employeeCount;

            averageSalaryByJobTitle.setAverageSalary(newAverageSalary);
            averageSalaryByJobTitle.setEmployeeCount(employeeCount);
            averageSalaryByJobTitleRepository.save(averageSalaryByJobTitle);
        }
    }

}
