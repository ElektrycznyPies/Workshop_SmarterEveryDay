package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Packet;

public interface PacketRepository extends JpaRepository<Packet, Long> {
}
