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

    @Override
    public Optional<Packet> getPacket(Long id) {
        return packetRepository.findById(id);
    }

    @Transactional
    public Packet addPacket(Packet packet, User user) {
        if (packet.getUsers() == null) {
            packet.setUsers(new HashSet<>());
        }
        packet.getUsers().add(user);

        Packet savedPacket = packetRepository.save(packet);

        if (user.getPackets() == null) {
            user.setPackets(new HashSet<>());
        }
        user.getPackets().add(savedPacket);
        userRepository.save(user);
        return savedPacket;
    }
//    public void addPacket(Packet packet, User user) {
//        if (packet.getUsers() == null) {
//            packet.setUsers(new HashSet<>());
//        }
//        packet.getUsers().add(user);
//        packetRepository.save(packet);
//        // Aktualizacja użytkownika, aby dodać pakiet do jego zbioru pakietów
//        userRepository.save(user);
//        System.out.println("|||||||||||||||||||||||||||Packet ID after save: " + packet.getId());
//    }

    @Override
    public void deletePacket(Long id) {
        packetRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePacket(Packet updatedPacket) {
        Packet existingPacket = packetRepository.findById(updatedPacket.getId())
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));

        existingPacket.setName(updatedPacket.getName());
        existingPacket.setDescription(updatedPacket.getDescription());
        packetRepository.save(existingPacket);
    }
}
