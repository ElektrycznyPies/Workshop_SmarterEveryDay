package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface PackageService {
    List<Packet> getAllPackages();
    Optional<Packet> getPackage(Long id);
    void addPackage(Packet pack, User user);
    void deletePackage(Long id);
    void updatePackage(Packet pack);
}
