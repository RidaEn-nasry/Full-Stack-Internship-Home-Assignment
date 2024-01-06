
package ma.dnaengineering.backend.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ma.dnaengineering.backend.models.Employee;

@Service
public class CsvParserServiceImpl implements CsvParserService {

    public List<Employee> parseCsv(MultipartFile file) throws IOException, NumberFormatException {
        List<Employee> employees = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        // skip first line (metadata)
        br.readLine();
        while ((line = br.readLine()) != null) {
            if (line.isEmpty() || line.isBlank()) {
                continue;
            }
            String[] values = line.split(",");
            Long id = Long.parseLong(values[0]);
            Double salary = Double.parseDouble(values[3]);
            Employee employee = new Employee(id, values[1], values[2], salary);
            employees.add(employee);
        }
        return employees;
    }
}
