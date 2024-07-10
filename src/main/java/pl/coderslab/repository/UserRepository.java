package pl.coderslab.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

// TE 4 METODY są dziedziczone z JpaRepo i nie trzeba ich deklarować:
    //    List<User> findAll();
    //    Optional<User> findById(Long id);
    //    User save(User user);
    //    void deleteById(Long id);

}
