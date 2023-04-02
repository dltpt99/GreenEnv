package nt.greenenv.greenenv.service;

import nt.greenenv.greenenv.domain.plant.Plant;
import nt.greenenv.greenenv.domain.plant.PlantFilter;
import nt.greenenv.greenenv.domain.plant.SelfPlant;
import nt.greenenv.greenenv.domain.plant.SelfPlantNewForm;
import nt.greenenv.greenenv.repository.PlantsRepository;
import nt.greenenv.greenenv.repository.SelfPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PlantService {
    private PlantsRepository plantsRepository;
    private SelfPlantRepository selfRepository;

    @Autowired
    public PlantService(PlantsRepository plantsRepository, SelfPlantRepository selfRepository) {
        this.plantsRepository = plantsRepository;
        this.selfRepository = selfRepository;
    }

    public List<Plant> getAllPlants() {
        return plantsRepository.findAll();
    }

    public Set<Plant> filteredPlantsAndOp(PlantFilter filter_ori) {

//        PlantFilter filter = (PlantFilter) filter_ori.clone();
        PlantFilter filter = new PlantFilter(filter_ori);
/*        filter.setBug(filter_ori.getBug());
        filter.setHeight(filter_ori.getHeight());
        filter.setSeason(filter_ori.getSeason());
        filter.setPlantType(filter_ori.getPlantType());
        filter.setSpeed(filter_ori.getSpeed());
        filter.setTemp(filter_ori.getTemp());
        filter.setDifficulty(filter_ori.getDifficulty());*/

        if(filter.getSpeed().isEmpty()) {
            List<String> levels = filter.getSpeed();
            levels.add("매우 느림");
            levels.add("느림");
            levels.add("보통");
            levels.add("빠름");
            levels.add("매우 빠름");
            filter.setSpeed(levels);
        }

        if (filter.getHeight().isEmpty()) {
            List<String> levels = filter.getHeight();
            levels.add("매우 낮음");
            levels.add("낮음");
            levels.add("보통");
            levels.add("높음");
            levels.add("매우 높음");
            filter.setHeight(levels);
        }

        if (filter.getSeason().isEmpty()) {
            List<String> levels = filter.getSeason();
            levels.add("초봄");
            levels.add("봄");
            levels.add("늦봄");
            levels.add("초여름");
            levels.add("여름");
            levels.add("가을");
            levels.add("늦가을");
            levels.add("겨울");
            filter.setSeason(levels);
        }

        if (filter.getTemp().isEmpty()) {
            List<String> levels = filter.getTemp();
            levels.add("매우 낮음");
            levels.add("낮음");
            levels.add("보통");
            levels.add("높음");
            levels.add("매우 높음");
            filter.setTemp(levels);
        }

        if (filter.getPlantType().isEmpty()) {
            List<String> levels = filter.getPlantType();
            levels.add("공기정화");
            filter.setPlantType(levels);
        }

        if (filter.getDifficulty().isEmpty()) {
            List<String> levels = filter.getDifficulty();
            levels.add("쉬움");
            levels.add("보통");
            levels.add("어려움");
            filter.setDifficulty(levels);
        }

        if (filter.getBug().isEmpty()) {
            List<String> levels = filter.getBug();
            levels.add("응애");
            levels.add("깍지벌레");
            levels.add("탄저병");
            levels.add("개각충");
            levels.add("진딧물");
            levels.add("곰팡이병");
            levels.add("온실가루이");
            levels.add("백수병");
            levels.add("팁번");
            levels.add("흰가루병");
            levels.add("총채벌레");
            levels.add("복숭아혹");
            filter.setBug(levels);
        }

        Set<Plant> plantList = new HashSet<>(plantsRepository.findAllByGrowSpeedInAndHeightInAndSeasonInAndGrowTempInAndPlantTypeInAndDifficultyIn(
                filter.getSpeed(),
                filter.getHeight(),
                filter.getSeason(),
                filter.getTemp(),
                filter.getPlantType(),
                filter.getDifficulty()));

        Set<Plant> plantbugList = new HashSet<>();
        for (String bug : filter.getBug()) {
            plantbugList.addAll(plantsRepository.findAllByBugContains(bug));
        }

        Set<Plant> needDelete = new HashSet<>();
        for (Plant plant : plantList) {
            if (!plantbugList.contains(plant)) {
                needDelete.add(plant);
            }
        }

        plantList.removeAll(needDelete);

        return plantList;
    }

    public Set<Plant> filteredPlantsOrOp(PlantFilter filter) {
        Set<Plant> plantList = new HashSet<>(plantsRepository.findAllByGrowSpeedInOrHeightInOrSeasonInOrGrowTempInOrPlantTypeInOrDifficultyIn(
                filter.getSpeed(),
                filter.getHeight(),
                filter.getSeason(),
                filter.getTemp(),
                filter.getPlantType(),
                filter.getDifficulty()
        ));
        for (String bug : filter.getBug()) {
            plantList.addAll(new HashSet<>(plantsRepository.findAllByBugContains(bug)));
        }
        return plantList;
    }

    public Plant getPlantById(long id) {
        return plantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식물입니다."));
    }

    public SelfPlant addNewSelfPlant(SelfPlantNewForm form, long id) {
        SelfPlant selfPlant = new SelfPlant(form, id);
        return selfRepository.save(selfPlant);
    }

    public SelfPlant waterCheck(long id, LocalDate lastWaterDate) {
        Optional<SelfPlant> selfPlant = selfRepository.findById(id);
        selfPlant.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식물관리입니다."));

        SelfPlant plant_checked = selfPlant.get();
        plant_checked.setLastWaterDate(lastWaterDate);
        return selfRepository.save(plant_checked);
    }

    public List<SelfPlant> getMemberSelfPlants(long id) {
        List<SelfPlant> plantList = selfRepository.findAllByOwnerId(id);
        for(SelfPlant plant : plantList) {

            Plant findPlant = plantsRepository.findByPlantName(plant.getPlantName());
            plant.nextWaterUpdate(findPlant.getWaterCycleWinter());
            plant.checkNeedWater();
        }
        return plantList;
    }
}
