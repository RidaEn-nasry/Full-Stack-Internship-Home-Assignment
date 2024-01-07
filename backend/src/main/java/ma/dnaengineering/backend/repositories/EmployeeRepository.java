
package ma.dnaengineering.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import ma.dnaengineering.backend.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);
}
