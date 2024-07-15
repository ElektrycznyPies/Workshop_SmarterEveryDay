package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.repository.FlashcardRepository;
import pl.coderslab.repository.PacketRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private PacketRepository packetRepository;

    @Override
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    @Override
    public Optional<Flashcard> getFlashcard(Long id) {
        return flashcardRepository.findById(id);
    }

    @Override
    public void addFlashcard(Flashcard flashcard, Packet pack) {
        flashcard.setPack(pack);
        flashcardRepository.save(flashcard);
    }

    @Override
    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }

//    @Override
//    public void updateFlashcard(Flashcard flashcard) {
//        flashcardRepository.save(flashcard);
//    }

    @Override
    public void updateFlashcard(Flashcard updatedFlashcard) {
        Flashcard existingFlashcard = flashcardRepository.findById(updatedFlashcard.getId())
                .orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));

        // updatuję poszczególne pola, żeby w razie zostawienia pustych nie zapisał jako null
        existingFlashcard.setName(updatedFlashcard.getName());
        existingFlashcard.setWord(updatedFlashcard.getWord());
        existingFlashcard.setImageLink(updatedFlashcard.getImageLink());
        existingFlashcard.setSoundLink(updatedFlashcard.getSoundLink());
        existingFlashcard.setAdditionalText(updatedFlashcard.getAdditionalText());

        flashcardRepository.save(existingFlashcard);
    }
}
