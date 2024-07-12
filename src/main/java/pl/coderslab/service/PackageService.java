package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Package;
import pl.coderslab.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface PackageService {
    List<Package> getAllPackages();
    Optional<Package> getPackage(Long id);
    void addPackage(Package pack, User user);
    void deletePackage(Long id);
    void updatePackage(Package pack);
}
