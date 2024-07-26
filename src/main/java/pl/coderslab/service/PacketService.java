package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface PacketService {
    List<Packet> getAllPackets();
    Optional<Packet> getPacket(Long id);
    @Transactional
    Packet addPacket(Packet packet, User user);
    Packet updatePacket(Packet packet, Long userId);
    void deletePacket(Long id);
    @Transactional
    List<Packet> getBazaarPackets();
    List<Packet> getBazaarPacketsByCategories(List<Long> categoryIds);
    void updateStudySettings(Long packetId, Set<String> showFields, String compareField);
    void destroyPacket(Long id);
    void removePacketFromUser(Long packetId, Long userId);
    void sendPacketToBazaar (Long packetId, Long userId);
    void addPacketToUser(Long packetId, Long userId);

    }

