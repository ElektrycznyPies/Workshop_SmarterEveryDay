package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}
