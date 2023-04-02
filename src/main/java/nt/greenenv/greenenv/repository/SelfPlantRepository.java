package nt.greenenv.greenenv.repository;

import nt.greenenv.greenenv.domain.plant.SelfPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfPlantRepository extends JpaRepository<SelfPlant, Long> {
    List<SelfPlant> findAllById(long Id);

    List<SelfPlant> findAllByOwnerId(long ownerId);
}
