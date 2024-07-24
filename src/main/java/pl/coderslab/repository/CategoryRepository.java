package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Category;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
