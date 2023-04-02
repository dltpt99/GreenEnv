package nt.greenenv.greenenv.repository;


import nt.greenenv.greenenv.domain.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantsRepository extends JpaRepository<Plant, Long> {
    List<Plant> findAllByGrowSpeedInOrHeightInOrSeasonInOrGrowTempInOrPlantTypeInOrDifficultyIn(
            List<String> growSpeed,
            List<String> height,
            List<String> season,
            List<String> growTemp,
            List<String> plantType,
            List<String> difficulty);

    List<Plant> findAllByGrowSpeedInAndHeightInAndSeasonInAndGrowTempInAndPlantTypeInAndDifficultyIn(
            List<String> growSpeed,
            List<String> height,
            List<String> season,
            List<String> growTemp,
            List<String> plantType,
            List<String> difficulty);

    Plant findByPlantName(String plantName);

    List<Plant> findAllByGrowSpeedInAndHeightInAndSeasonIn(List<String> growSpeed, List<String> height, List<String> season);

    List<Plant> findAllByBugContains(String bug);
    List<Plant> findAllByPlantNameContaining(String plantName);
}
