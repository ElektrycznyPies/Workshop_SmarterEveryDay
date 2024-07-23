package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.StudySession;
import java.util.List;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findByUserId(Long userId);
    List<StudySession> findByPacketId(Long packetId);

}