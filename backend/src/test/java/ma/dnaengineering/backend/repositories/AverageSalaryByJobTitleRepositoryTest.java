package ma.dnaengineering.backend.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ma.dnaengineering.backend.models.AverageSalaryByJobTitle;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AverageSalaryByJobTitleRepositoryTest {

    @Autowired
    private AverageSalaryByJobTitleRepository averageSalaryByJobTitleRepository;

    @Test
    public void whenFindAll_thenReturnsListOfAverageSalaryByJobTitle() {
        averageSalaryByJobTitleRepository.save(new AverageSalaryByJobTitle("Developer", 60000.00, 0));
        averageSalaryByJobTitleRepository.save(new AverageSalaryByJobTitle("Manager", 80000.00, 0));

        List<AverageSalaryByJobTitle> averageSalaryByJobTitleList = averageSalaryByJobTitleRepository.findAll();
        int size = averageSalaryByJobTitleList.size();
        assert (size >= 2);
    }

    @Test
    public void whenSaveAverageSalaryByJobTitle_thenReturnsAverageSalaryByJobTitle() {
        AverageSalaryByJobTitle averageSalaryByJobTitle = new AverageSalaryByJobTitle("Developer", 60000.00, 0);
        AverageSalaryByJobTitle savedAverageSalaryByJobTitle = averageSalaryByJobTitleRepository
                .save(averageSalaryByJobTitle);
        assert (savedAverageSalaryByJobTitle.getJobTitle().equals(averageSalaryByJobTitle.getJobTitle()));
    }

    @Test
    public void whenDeleteAverageSalaryByJobTitle_thenAverageSalaryByJobTitleShouldNotExist() {
        AverageSalaryByJobTitle averageSalaryByJobTitle = new AverageSalaryByJobTitle("Developer", 60000.00, 0);
        averageSalaryByJobTitleRepository.save(averageSalaryByJobTitle);
        averageSalaryByJobTitleRepository.delete(averageSalaryByJobTitle);
        assert (averageSalaryByJobTitleRepository.findById(averageSalaryByJobTitle.getJobTitle()).isEmpty());
    }

    @Test
    public void whenUpdateAverageSalaryByJobTitle_thenAverageSalaryByJobTitleShouldBeUpdated() {
        AverageSalaryByJobTitle averageSalaryByJobTitle = new AverageSalaryByJobTitle("Developer", 60000.00, 0);
        averageSalaryByJobTitleRepository.save(averageSalaryByJobTitle);
        averageSalaryByJobTitle.setAverageSalary(70000.00);
        averageSalaryByJobTitleRepository.save(averageSalaryByJobTitle);
        assert (averageSalaryByJobTitleRepository.findById(averageSalaryByJobTitle.getJobTitle()).get()
                .getAverageSalary() == 70000.00);

    }

}
