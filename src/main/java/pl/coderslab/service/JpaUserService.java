package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.model.StudySession;
import pl.coderslab.model.User;
import pl.coderslab.model.Packet;
import pl.coderslab.repository.PacketRepository;
import pl.coderslab.repository.StudySessionRepository;
import pl.coderslab.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Primary
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PacketRepository packetRepository;
    private final StudySessionRepository studySessionRepository;


    @Autowired
    public JpaUserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          PacketRepository packetRepository, StudySessionRepository studySessionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.packetRepository = packetRepository;
        this.studySessionRepository = studySessionRepository;
    }

    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(0L); // default role
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }


    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // przy kasowaniu usera jego pakiety zostają przypisane userowi id = 0 (defaultUser)

        // znajdź użytkownika o id = 0 (defaultUser)
        User defaultUser = userRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("Default user not found"));

        // przypisz pakiety do defaultUser
        Set<Packet> userPackets = new HashSet<>(user.getPackets());
        for (Packet packet : userPackets) {
            packet.getUsers().remove(user);
            packet.getUsers().add(defaultUser);
            defaultUser.getPackets().add(packet);
            packetRepository.save(packet);
        }

        // wyczyść pakiety kasowanego usera
        user.getPackets().clear();

        // usuń jego sesje nauki
        List<StudySession> studySessions = studySessionRepository.findByUserId(id);
        studySessionRepository.deleteAll(studySessions);

        // zapisz zmiany dla defaultUser
        userRepository.save(defaultUser);

        // usuń kasowanego użytkownika
        userRepository.delete(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> authorize(String email){
        return userRepository.findUserByEmail(email);
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    @Override
    @Transactional
    public List<Packet> getUserPackets(Long userId) {
        return userRepository.findPacketsByUserId(userId);
    }
}