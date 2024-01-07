
package ma.dnaengineering.backend.dto;

import java.util.List;
import org.springframework.data.domain.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;
import ma.dnaengineering.backend.models.Employee;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class EmployeeUploadResponse {
    private Page<Employee> employees;
    private List<AverageSalaryByJobTitle> averageSalaryByJobTitles;
}
