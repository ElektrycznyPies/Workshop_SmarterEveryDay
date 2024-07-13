package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Packet;

public interface PackageRepository extends JpaRepository<Packet, Long> {
}
