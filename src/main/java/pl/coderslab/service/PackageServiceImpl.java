package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.repository.PackageRepository;
import pl.coderslab.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Packet> getAllPackages() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<Packet> getPackage(Long id) {
        return packageRepository.findById(id);
    }

    @Override
    public void addPackage(Packet pack, User user) {
        pack.getUsers().add(user);
        packageRepository.save(pack);
    }

    @Override
    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }

    @Override
    public void updatePackage(Packet pack) {
        packageRepository.save(pack);
    }
}
