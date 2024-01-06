package ma.dnaengineering.backend.services;

import java.util.List;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import ma.dnaengineering.backend.models.Employee;

public interface CsvParserService {

    public List<Employee> parseCsv(MultipartFile file) throws IOException;

}
