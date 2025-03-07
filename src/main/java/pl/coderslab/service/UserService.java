package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUser(Long id);
    void addUser(User user);
    void deleteUser(Long id);
    void updateUser(User user);
    Optional<User> authorize(String email);
    void registerUser(User user);
    boolean checkPassword(String plainPassword, String hashedPassword);
    @Transactional
    List<Packet> getUserPackets(Long userId);
    Optional<User> findById(Long id);

}

