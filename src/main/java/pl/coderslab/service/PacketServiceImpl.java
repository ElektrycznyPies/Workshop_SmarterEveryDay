package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.repository.PacketRepository;
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

    @Override
    public List<Packet> getAllPackets() {
        return packetRepository.findAll();
    }

//    @Override
//    public Optional<Packet> getPacket(Long id) {
//        return packetRepository.findById(id);
//    }
@Override
@Transactional
public Optional<Packet> getPacket(Long id) {
    Optional<Packet> packet = packetRepository.findById(id);
    packet.ifPresent(p -> p.getShowFields().size()); // Access the collection to initialize it
    return packet;
}

//    @Transactional
//    public Packet addPacket(Packet packet, User user) {
//        // Ensure users set is initialized
//        if (packet.getUsers() == null) {
//            packet.setUsers(new HashSet<>());
//        }
//
//        // Check if the user is already associated with the packet
//        if (!packet.getUsers().contains(user)) {
//            packet.getUsers().add(user);
//        }
//
//        System.out.println("\033[36;1;44m}}}}}}}}}}}}}}}Before saving: Packet ID = " + packet.getId() + ", User ID = " + user.getId()+"\033[0m");
//        System.out.println("\033[36;1;44m}}}}}}}}}}}}}}}Packet users before saving: " + packet.getUsers().stream().map(User::getId).collect(Collectors.toSet()) + "\033[0m");
//
//        // Save the packet
//        Packet savedPacket = packetRepository.save(packet);
//
//        System.out.println("\033[36;1;44m}}}}}}}}}}}}}}}After saving: Packet ID = " + packet.getId() + ", User ID = " + user.getId()+"\033[0m");
//        System.out.println("\033[36;1;44m}}}}}}}}}}}}}}}Packet users after saving: " + packet.getUsers().stream().map(User::getId).collect(Collectors.toSet()) + "\033[0m");
//
//        // Ensure user's packets set is initialized
//        if (user.getPackets() == null) {
//            user.setPackets(new HashSet<>());
//        }
//
//        // Check if the packet is already associated with the user
//        if (!user.getPackets().contains(savedPacket)) {
//            user.getPackets().add(savedPacket);
//            user = userRepository.save(user);
//        }
//
//        return savedPacket;
//    }

    @Transactional
    public Packet addPacket(Packet packet, User user) {
        // Fetch the user from the database to ensure it's in a managed state
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Ensure users set is initialized
        if (packet.getUsers() == null) {
            packet.setUsers(new HashSet<>());
        }

        // Check if the user is already associated with the packet
        if (!packet.getUsers().contains(managedUser)) {
            packet.getUsers().add(managedUser);
        }

        // Save the packet
        Packet savedPacket = packetRepository.save(packet);

        // Ensure user's packets set is initialized
        if (managedUser.getPackets() == null) {
            managedUser.setPackets(new HashSet<>());
        }

        // Check if the packet is already associated with the user
        if (!managedUser.getPackets().contains(savedPacket)) {
            managedUser.getPackets().add(savedPacket);
            userRepository.save(managedUser);
        }
        return savedPacket;
    }

    @Override
    public void deletePacket(Long id) {
        packetRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePacket(Packet packet) {
        Packet existingPacket = packetRepository.findById(packet.getId())
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        existingPacket.setName(packet.getName());
        existingPacket.setDescription(packet.getDescription());
        existingPacket.setAuthor(packet.getAuthor());
        // Aktualizuj inne pola, jeśli są
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

}
