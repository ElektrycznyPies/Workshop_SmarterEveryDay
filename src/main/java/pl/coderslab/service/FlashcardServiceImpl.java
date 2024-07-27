package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.repository.FlashcardRepository;
import pl.coderslab.repository.PacketRepository;
import pl.coderslab.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private PacketRepository packetRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    @Override
    public Optional<Flashcard> getFlashcard(Long id) {
        return flashcardRepository.findById(id);
    }


    @Override
    @Transactional
    public void addFlashcard(Flashcard flashcard, Packet packet) {
        flashcard.setPack(packet);
        flashcardRepository.save(flashcard);
        packet.getFlashcards().add(flashcard);
        packetRepository.save(packet);
    }



    @Override
    @Transactional
    public void deleteFlashcard(Long id) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));

        Packet packet = flashcard.getPack();
        if (packet != null) {
            packet.getFlashcards().remove(flashcard);
            packetRepository.save(packet); // Zapisz zmiany w pakiecie przed usunięciem fiszki
        }

        flashcard.setPack(null); // Detach the flashcard from the packet
        flashcardRepository.delete(flashcard);

        System.out.println("]FISer Pak, Fid, listaF: " + packet.getName() + " , " + id + " , " + packet.getFlashcards());
    }


    //    @Override
//    public void updateFlashcard(Flashcard flashcard) {
//        if (!flashcardRepository.existsById(flashcard.getId())) {
//            throw new EntityNotFoundException("Flashcard not found");
//        }
//        Packet packet = flashcard.getPack();
//        flashcard.setPack(packet);
//        flashcardRepository.save(flashcard);
//    }
    @Override
    @Transactional
    public void updateFlashcard(Flashcard updatedFlashcard) {
        Flashcard existingFlashcard = flashcardRepository.findById(updatedFlashcard.getId())
                .orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));
        Packet newPacket = updatedFlashcard.getPack();

        existingFlashcard.setName(updatedFlashcard.getName());
        existingFlashcard.setWord(updatedFlashcard.getWord());
        existingFlashcard.setWord2(updatedFlashcard.getWord2());
        existingFlashcard.setAdditionalText(updatedFlashcard.getAdditionalText());
        existingFlashcard.setImageLink(updatedFlashcard.getImageLink());

        // Update the pack only if it has changed
        if (newPacket != null && !existingFlashcard.getPack().getId().equals(newPacket.getId())) {
            existingFlashcard.setPack(newPacket);
        }

        flashcardRepository.save(existingFlashcard);
    }



    @Override
    public List<Flashcard> getFlashcardsByPacketId(Long packetId) {
        return flashcardRepository.findByPackId(packetId);
    }
}