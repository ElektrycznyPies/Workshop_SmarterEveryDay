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
import javax.transaction.Transactional;
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

//    @Override
//    @Transactional
//    public void addFlashcard(Flashcard flashcard, Packet packet) {
////        flashcard = flashcardRepository.findById(flashcard.getId())
////                .orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));
////        packet = flashcard.getPack();
//        flashcard.setPack(packet);
//        flashcardRepository.save(flashcard);
//    }

    @Override
    @Transactional
    public void addFlashcard(Flashcard flashcard, Packet packet) {
        flashcard.setPack(packet);
        flashcardRepository.save(flashcard);
        packet.getFlashcards().add(flashcard);
        packetRepository.save(packet);
    }

//    @Override
//    @Transactional
//    public void deleteFlashcard(Long id) {
//        Flashcard flashcard = flashcardRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));
//
//        Packet packet = flashcard.getPack();
//        packet.getFlashcards().remove(flashcard);
//
//        packetRepository.save(packet);
//            System.out.println("]FISer Pak, Fid, listaF: " + packet.getName() + " , " + flashcard.getId() + " , " + packet.getFlashcards());
//        flashcardRepository.delete(flashcard);
//    }
@Override
@Transactional
public void deleteFlashcard(Long id) {
    Flashcard flashcard = flashcardRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));

    Packet packet = flashcard.getPack();
    packet.getFlashcards().remove(flashcard);

    flashcard.setPack(null); // Detach the flashcard from the packet
    flashcardRepository.delete(flashcard);
    packetRepository.save(packet);

    System.out.println("]FISer Pak, Fid, listaF: " + packet.getName() + " , " + id + " , " + packet.getFlashcards());
}






    @Override
    public void updateFlashcard(Flashcard flashcard) {
        if (!flashcardRepository.existsById(flashcard.getId())) {
            throw new EntityNotFoundException("Flashcard not found");
        }
        Packet packet = flashcard.getPack();
        flashcard.setPack(packet);
        flashcardRepository.save(flashcard);
    }

    @Override
    public List<Flashcard> getFlashcardsByPacketId(Long packetId) {
        return flashcardRepository.findByPackId(packetId);
    }
}
