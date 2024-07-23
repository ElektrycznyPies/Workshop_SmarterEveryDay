package pl.coderslab.service;

import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.coderslab.model.Packet;
import pl.coderslab.model.StudySession;
import pl.coderslab.model.User;
import pl.coderslab.repository.StudySessionRepository;
import pl.coderslab.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudySessionService {

    @Autowired
    private StudySessionRepository studySessionRepository;
    private UserRepository userRepository;

    @Transactional
    public StudySession startSession(User user, Packet packet) {
        StudySession studySession = new StudySession();
        studySession.setUser(user);
        studySession.setPacket(packet);
        studySession.setStartTime(new Timestamp(System.currentTimeMillis()));
        return studySessionRepository.save(studySession);
    }

//    public StudySession endSession(Long sessionId) {
//        StudySession session = studySessionRepository.findById(sessionId)
//                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
//        session.setEndTime(new Timestamp(System.currentTimeMillis()));
//        session.setDuration((session.getEndTime().getTime() - session.getStartTime().getTime()) / 1000);
//        return studySessionRepository.save(session);
//    }

    public StudySession endSession(StudySession studySession) {
        studySession.setEndTime(new Timestamp(System.currentTimeMillis()));
        studySession.setDuration((studySession.getEndTime().getTime() - studySession.getStartTime().getTime()) / 1000);
        return studySessionRepository.save(studySession);
    }

    public List<StudySession> getSessionsPerPacket(Long userId) {
        return studySessionRepository.findByUserId(userId); // zwraca sesje dla użytkownika
    }

    public StudySession findSessionById(Long sessionId) {
        return studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }
    public void deleteStudySession(Long sessionId){
        studySessionRepository.deleteById(sessionId);
    }

    // other methods for getting and analyzing study sessions
}
