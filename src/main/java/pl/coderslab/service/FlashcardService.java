package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;

import java.util.List;
import java.util.Optional;

@Service
public interface FlashcardService {
    List<Flashcard> getAllFlashcards();
    Optional<Flashcard> getFlashcard(Long id);
    void addFlashcard(Flashcard flashcard, Packet pack);
    void deleteFlashcard(Long id);
    void updateFlashcard(Flashcard flashcard);
}
