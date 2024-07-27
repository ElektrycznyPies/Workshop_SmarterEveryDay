package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.model.StudySession;
import pl.coderslab.model.User;
import pl.coderslab.repository.FlashcardRepository;
import pl.coderslab.repository.PacketRepository;
import pl.coderslab.repository.StudySessionRepository;
import pl.coderslab.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PacketServiceImpl implements PacketService {

    @Autowired
    private PacketRepository packetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudySessionRepository studySessionRepository;
    @Autowired
    private FlashcardRepository flashcardRepository;

    @Override
    public List<Packet> getAllPackets() {
        return packetRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Packet> getPacket(Long id) {
        Optional<Packet> packet = packetRepository.findById(id);
        packet.ifPresent(p -> p.getShowFields().size());
        return packet;
    }

    @Override
    @Transactional
    public Packet addPacket(Packet packet, User user) {
        // user zarządzany przez persistence context
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // wszyscy userzy zarz. przez persistence context
        Set<User> managedUsers = packet.getUsers().stream()
                .map(u -> userRepository.findById(u.getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found")))
                .collect(Collectors.toSet());

        packet.setUsers(managedUsers);

        // czy już przypisany
        boolean associationExists = managedUser.getPackets().stream()
                .anyMatch(p -> p.getId().equals(packet.getId()));

        if (!associationExists) {
            packetRepository.save(packet);
            managedUser.getPackets().add(packet);
            userRepository.save(managedUser);
        }

        return packet;
    }


    @Override
    @Transactional
    public void deletePacket(Long id) {
        Packet packet = packetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        if (packet.getUsers().size() <= 1) {
            List<StudySession> studySessions = studySessionRepository.findByPacketId(id);
            studySessionRepository.deleteAll(studySessions);
            packetRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Cannot delete packet assigned to multiple users");
        }
    }


    // całkowicie usuwa pakiet (Admin)
    @Override
    @Transactional
    public void destroyPacket(Long id) {
        Packet packet = packetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        List<StudySession> studySessions = studySessionRepository.findByPacketId(id);
        studySessionRepository.deleteAll(studySessions);
        packetRepository.delete(packet);
    }

    // jeśli więcej niż 1 user posiada pakiet, odłącza go od usera. Jeśli tylko bieżący user, kasuje (User)
    @Override
    @Transactional
    public void removePacketFromUser(Long packetId, Long userId) {
        Packet packet = packetRepository.findById(packetId)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        packet.getUsers().remove(user);
        user.getPackets().remove(packet);

        if (packet.getUsers().isEmpty()) {
            List<StudySession> studySessions = studySessionRepository.findByPacketId(packetId);
            studySessionRepository.deleteAll(studySessions);
            packetRepository.delete(packet);
        } else {
            packetRepository.save(packet);
        }
        userRepository.save(user);
    }



    @Override
    @Transactional
    public Packet updatePacket(Packet packet, Long userId) {
        Packet existingPacket = packetRepository.findById(packet.getId())
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        System.out.println("]0Upd currentUser2: " + currentUser);
        System.out.println("]]]1Upd packet: " + packet);
        System.out.println("]]]2Upd currentUser: " + currentUser);
        System.out.println("]]]2Upd packet users: " + packet.getUsers());

        if (existingPacket.getUsers().size() > 1) {
            // Tworzenie nowego pakietu
            Packet newPacket = new Packet();
            newPacket.setName(packet.getName());
            newPacket.setDescription(packet.getDescription());
            newPacket.setAuthor(packet.getAuthor());
            newPacket.setOnBazaar(false);
            newPacket.setShowFields(new HashSet<>(packet.getShowFields())); // Ensure showFields is copied
            newPacket.setCompareField(packet.getCompareField());

            // tylko 1 user nowego pak.
            newPacket.setUsers(new HashSet<>(Collections.singletonList(currentUser)));
            System.out.println("]]]2.2Upd newpacket users: " + newPacket.getUsers());

            // Kopiowanie fiszek
            newPacket.setFlashcards(new HashSet<>());
            for (Flashcard flashcard : existingPacket.getFlashcards()) {
                Flashcard newFlashcard = new Flashcard();
                newFlashcard.setName(flashcard.getName());
                newFlashcard.setWord(flashcard.getWord());
                newFlashcard.setWord2(flashcard.getWord2());
                newFlashcard.setAdditionalText(flashcard.getAdditionalText());
                newFlashcard.setImageLink(flashcard.getImageLink());
                newFlashcard.setPack(newPacket);
                newPacket.getFlashcards().add(newFlashcard);
            }

            System.out.println("]6Upd Flashcards copied to new packet: " + newPacket.getFlashcards());

            // Remove the current user from the existing packet
            existingPacket.getUsers().remove(currentUser);
            packetRepository.save(existingPacket);

            // Save the new packet
            packetRepository.save(newPacket);

            // Update user's packet list
            currentUser.getPackets().remove(existingPacket);
            currentUser.getPackets().add(newPacket);
            userRepository.save(currentUser);

            System.out.println("]8Upd newPacket and user updated. Users: " + newPacket.getUsers());

            return newPacket;
        } else {
            // Aktualizacja istniejącego pakietu
            existingPacket.setName(packet.getName());
            existingPacket.setDescription(packet.getDescription());
            existingPacket.setShowFields(packet.getShowFields());
            existingPacket.setCompareField(packet.getCompareField());
            existingPacket.setOnBazaar(packet.isOnBazaar());
            existingPacket.setAuthor(packet.getAuthor());

            System.out.println("]8Upd exiPacket users: " + existingPacket.getUsers());

            // Save the updated existing packet
            packetRepository.save(existingPacket);

            System.out.println("]7 zaktualizowany stary pakiet. Users: " + existingPacket.getUsers());

            return existingPacket;
        }
    }



    @Override
    @Transactional
    public void updateStudySettings(Long packetId, Set<String> showFields, String compareField) {
        Packet packet = packetRepository.findById(packetId)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        packet.setShowFields(showFields);
        packet.setCompareField(compareField);
        packetRepository.save(packet);
    }

    @Override
    @Transactional
    public void sendPacketToBazaar(Long packetId, Long userId) {
        Packet packet = packetRepository.findById(packetId)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!packet.getUsers().contains(user)) {
            throw new IllegalStateException("User does not own this packet");
        }

        if (packet.getFlashcards() == null || packet.getFlashcards().isEmpty()) {
            throw new IllegalStateException("Packet must have flashcards to be sent to the bazaar");
        }

        packet.setOnBazaar(true);
        packetRepository.save(packet);
    }

    @Override
    @Transactional
    public List<Packet> getBazaarPackets() {
        return packetRepository.findByIsOnBazaar(true);
    }


    @Override
    @Transactional
    public void addPacketToUser(Long packetId, Long userId) {
        Packet packet = packetRepository.findById(packetId)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!packet.getUsers().contains(user)) {
            packet.getUsers().add(user);
            user.getPackets().add(packet);
            packetRepository.save(packet);
            userRepository.save(user);
            System.out.println("]5Getbaz user, packet: " + user.getFullName() + packet.getName());
        }
    }
    @Override
    @Transactional
    public List<Packet> getBazaarPacketsByCategories(List<Long> categoryIds) {
        return packetRepository.findByIsOnBazaarAndCategoriesIn(true, categoryIds);
    }
}