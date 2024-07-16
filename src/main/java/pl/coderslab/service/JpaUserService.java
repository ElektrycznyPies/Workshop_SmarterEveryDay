package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.model.User;
import pl.coderslab.model.Packet;
import pl.coderslab.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JpaUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // osierocone pakiety -- przypisuje do defaultUser o id 0
        User defaultUser = userRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("Default user not found"));

        for (Packet packet : user.getPackets()) {
            packet.getUsers().remove(user);
            packet.getUsers().add(defaultUser);
        }
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