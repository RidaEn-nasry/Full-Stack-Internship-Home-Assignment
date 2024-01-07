package ma.dnaengineering.backend.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ma.dnaengineering.backend.models.Employee;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindAll_thenReturnPageOfEmployees() {
        // to avoid conflicts with the ids of the employees already in the database
        long id1 = (long) (Math.random() * (9999999 - 1000000)) + 1000000;
        long id2 = (long) (Math.random() * (9999999 - 1000000)) + 1000000;

        Employee employee1 = new Employee(id1, "John Doe", "Developer", 60000.00);
        Employee employee2 = new Employee(id2, "Jane Doe", "Manager", 80000.00);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(0, 5, Sort.by("id")));
        assertThat(employeePage.getTotalElements()).isGreaterThanOrEqualTo(2L);
    }

    @Test
    public void whenSaveEmployee_thenReturnEmployee() {

        long id1 = (long) (Math.random() * (9999999 - 1000000)) + 1000000;
        Employee employee1 = new Employee(id1, "John Doe", "Developer", 60000.00);
        Employee savedEmployee = employeeRepository.save(employee1);
        assertThat(savedEmployee.getId()).isEqualTo(employee1.getId());
    }

    @Test
    public void whenDeleteEmployee_thenEmployeeShouldNotExist() {

        long id1 = (long) (Math.random() * (9999999 - 1000000)) + 1000000;
        Employee employee1 = new Employee(id1, "John Doe", "Developer", 60000.00);
        employeeRepository.save(employee1);
        employeeRepository.delete(employee1);
        assertThat(employeeRepository.findById(id1)).isEmpty();
    }

    @Test
    public void whenUpdateEmployee_thenEmployeeShouldBeUpdated() {

        long id1 = (long) (Math.random() * (9999999 - 1000000)) + 1000000;
        Employee employee1 = new Employee(id1, "John Doe", "Developer", 60000.00);
        employeeRepository.save(employee1);
        employee1.setEmployeeName("Jane Doe");
        employeeRepository.save(employee1);
        assertThat(employeeRepository.findById(id1).get().getEmployeeName()).isEqualTo("Jane Doe");
    }
    
}