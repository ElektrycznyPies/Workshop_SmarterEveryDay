package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.StudySession;
import pl.coderslab.model.User;
import pl.coderslab.repository.PacketRepository;
import pl.coderslab.repository.StudySessionRepository;
import pl.coderslab.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PacketServiceImpl implements PacketService {

    @Autowired
    private PacketRepository packetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudySessionRepository studySessionRepository;

    @Override
    public List<Packet> getAllPackets() {
        return packetRepository.findAll();
    }

@Override
@Transactional
public Optional<Packet> getPacket(Long id) {
    Optional<Packet> packet = packetRepository.findById(id);
    packet.ifPresent(p -> p.getShowFields().size()); // Access the collection to initialize it
    return packet;
}

//    @Transactional
//    public Packet addPacket(Packet packet, User user) {
//        // pobiera usera do zarządzania
//        User managedUser = userRepository.findById(user.getId())
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        // set userów, inicjalizacja
//        if (packet.getUsers() == null) {
//            packet.setUsers(new HashSet<>());
//        }
//
//        // czy user jest już przypisany do pakietu?
//        if (!packet.getUsers().contains(managedUser)) {
//            packet.getUsers().add(managedUser);
//        }
//
//        // zapisuje pak.
//        Packet savedPacket = packetRepository.save(packet);
//
//        // Ensure user's packets set is initialized
//        if (managedUser.getPackets() == null) {
//            managedUser.setPackets(new HashSet<>());
//        }
//
//        // Check if the packet is already associated with the user
//        if (!managedUser.getPackets().contains(savedPacket)) {
//            managedUser.getPackets().add(savedPacket);
//            userRepository.save(managedUser);
//        }
//        return savedPacket;
//    }


    @Transactional
    public Packet addPacket(Packet packet, User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // First, save the packet
        Packet savedPacket = packetRepository.save(packet);

        // Check if the association already exists
        boolean associationExists = managedUser.getPackets().stream()
                .anyMatch(p -> p.getId().equals(savedPacket.getId()));

        if (!associationExists) {
            // Add the association only if it doesn't exist
            managedUser.getPackets().add(savedPacket);
            savedPacket.getUsers().add(managedUser);

            // Save both entities
            userRepository.save(managedUser);
            packetRepository.save(savedPacket);
        }

        return savedPacket;
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
    public void updatePacket(Packet packet) {
        Packet existingPacket = packetRepository.findById(packet.getId())
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        existingPacket.setName(packet.getName());
        existingPacket.setDescription(packet.getDescription());
        existingPacket.setAuthor(packet.getAuthor());
        existingPacket.setOnBazaar(packet.isOnBazaar());
        existingPacket.setUsers(packet.getUsers());
        // + inne pola, jeśli są
        packetRepository.save(existingPacket);
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
    public List<Packet> getBazaarPackets() {
        return packetRepository.findByIsOnBazaar(true);
    }

    @Override
    @Transactional
    public List<Packet> getBazaarPacketsByCategories(List<Long> categoryIds) {
        return packetRepository.findByIsOnBazaarAndCategoriesIn(true, categoryIds);
    }

}
