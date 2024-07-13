package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Packet;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PacketRepository extends JpaRepository<Packet, Long> {
}
