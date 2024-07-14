package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface PacketService {
    List<Packet> getAllPackets();
    Optional<Packet> getPacket(Long id);
    Packet addPacket(Packet pack, User user);
    void deletePacket(Long id);
    void updatePacket(Packet pack);


        }

