
package ma.dnaengineering.backend.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private List<Employee> employees;
    private Map<String, Double> averageSalaryByJobTitle;
}
