package ma.dnaengineering.backend.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import ma.dnaengineering.backend.models.Employee;
import ma.dnaengineering.backend.exceptions.CsvProcessingException;

public interface CsvParserService {

    public List<Employee> parseCsv(MultipartFile file) throws CsvProcessingException;

}
