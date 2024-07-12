package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Package;

public interface PackageRepository extends JpaRepository<Package, Long> {
}
