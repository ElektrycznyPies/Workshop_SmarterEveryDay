package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.repository.PacketRepository;
import pl.coderslab.repository.UserRepository;

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

    public void addPacket(Packet packet, User user) {
        if (packet.getUsers() == null) {
            packet.setUsers(new HashSet<>());
        }
        packet.getUsers().add(user);

        packetRepository.save(packet);

        // Aktualizacja użytkownika, aby dodać pakiet do jego zbioru pakietów
        if (user.getPackets() == null) {
            user.setPackets(new HashSet<>());
        }
        user.getPackets().add(packet);
        userRepository.save(user);
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
    public void updatePacket(Packet pack) {
        packetRepository.save(pack);
    }
}
