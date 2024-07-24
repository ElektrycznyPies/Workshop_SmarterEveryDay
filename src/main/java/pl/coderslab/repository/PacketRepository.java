package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Packet;
import pl.coderslab.model.StudySession;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PacketRepository extends JpaRepository<Packet, Long> {

//    @Query("SELECT p FROM Packet p JOIN p.users u WHERE u.id = :userId")
//    List<Packet> findPacketsByUserId(@Param("userId") Long userId);
//    List<Packet> getPacketsByUserId(Long userId);
    List<Packet> findByIsOnBazaar(boolean is);
    List<Packet> findByIsOnBazaarAndCategoriesIn(boolean is, List<Long> categoryIds);
}
