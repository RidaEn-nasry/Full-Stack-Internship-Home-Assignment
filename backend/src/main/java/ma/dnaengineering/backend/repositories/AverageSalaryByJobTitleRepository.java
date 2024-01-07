package ma.dnaengineering.backend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;

public interface AverageSalaryByJobTitleRepository extends JpaRepository<AverageSalaryByJobTitle, String> {
    Optional<AverageSalaryByJobTitle> findByJobTitle(String jobTitle);

}
