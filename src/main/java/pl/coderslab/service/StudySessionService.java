package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.StudySession;
import pl.coderslab.model.User;
import pl.coderslab.repository.StudySessionRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
@Service
public class StudySessionService {

    @Autowired
    private StudySessionRepository studySessionRepository;

    @Transactional
    public StudySession startSession(User user, Packet packet) {
        StudySession session = new StudySession();
        session.setUser(user);
        session.setPacket(packet);
        session.setStartTime(new Timestamp(System.currentTimeMillis()));
        return studySessionRepository.save(session);
    }

    public StudySession endSession(Long sessionId) {
        StudySession session = studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
        session.setEndTime(new Timestamp(System.currentTimeMillis()));
        session.setDuration((session.getEndTime().getTime() - session.getStartTime().getTime()) / 1000);
        return studySessionRepository.save(session);
    }


    // other methods for getting and analyzing study sessions
}