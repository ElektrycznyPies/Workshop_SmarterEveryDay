package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.repository.FlashcardRepository;
import pl.coderslab.repository.PacketRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
    @Transactional
    public void deleteFlashcard(Long id) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));
        Packet packet = flashcard.getPack();
        packet.getFlashcards().remove(flashcard);
        packetRepository.save(packet);
        flashcardRepository.deleteById(id);
    }

    @Override
    public void updateFlashcard(Flashcard updatedFlashcard) {
        if (!flashcardRepository.existsById(updatedFlashcard.getId())) {
            throw new EntityNotFoundException("Flashcard not found");
        }
        flashcardRepository.save(updatedFlashcard);
    }

    @Override
    public List<Flashcard> getFlashcardsByPacketId(Long packetId) {
        return flashcardRepository.findByPackId(packetId);
    }
}
