package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.repository.PacketRepository;
import pl.coderslab.repository.UserRepository;

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

    @Override
    public void addPacket(Packet pack, User user) {
        pack.getUsers().add(user);
        packetRepository.save(pack);
    }

    @Override
    public void deletePacket(Long id) {
        packetRepository.deleteById(id);
    }

    @Override
    public void updatePacket(Packet pack) {
        packetRepository.save(pack);
    }
}
