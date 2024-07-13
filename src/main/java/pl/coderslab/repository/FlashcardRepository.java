package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Flashcard;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}
