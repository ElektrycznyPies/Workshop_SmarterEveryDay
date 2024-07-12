package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Package;
import pl.coderslab.repository.FlashcardRepository;
import pl.coderslab.repository.PackageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Override
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    @Override
    public Optional<Flashcard> getFlashcard(Long id) {
        return flashcardRepository.findById(id);
    }

    @Override
    public void addFlashcard(Flashcard flashcard, Package pack) {
        flashcard.setPack(pack);
        flashcardRepository.save(flashcard);
    }

    @Override
    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }

    @Override
    public void updateFlashcard(Flashcard flashcard) {
        flashcardRepository.save(flashcard);
    }
}
