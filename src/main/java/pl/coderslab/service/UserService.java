package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUser(Long id);

    void addUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);
}

