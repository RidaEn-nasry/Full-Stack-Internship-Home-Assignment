package ma.dnaengineering.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "average_salary_by_job_title")
public class AverageSalaryByJobTitle {
    @Id
    @Column(unique = true, nullable = false)
    private String jobTitle;
    private Double averageSalary;
    private Integer employeeCount;
}
