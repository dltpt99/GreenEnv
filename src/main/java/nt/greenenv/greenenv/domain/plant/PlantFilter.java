package nt.greenenv.greenenv.domain.plant;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlantFilter {
    private List<String> speed;
    private List<String> height;
    private List<String> season;
    private List<String> temp;
    private List<String> difficulty; // 생육 난이도
    private List<String> plantType;
    private List<Integer> fertilizerCycle; // 비료 주기 (0은 비료필요없음)
    private List<Integer> waterCycleSpring; // 물 주기 봄
    private List<Integer> waterCycleSummer; // 물 주기 여름
    private List<Integer> waterCycleFall; // 물 주기 가을
    private List<Integer> waterCycleWinter; // 물 주기 겨울
    private List<String> bug; // 병충해
    private String operation;

    public PlantFilter() {
    }

    public PlantFilter(PlantFilter filter) {
//        this.speed = filter.getSpeed();
        this.speed = new ArrayList<>();
        for (String s : filter.getSpeed()) {
            this.speed.add(s);
        }
//        Collections.copy(this.speed, filter.getSpeed());
//        this.height = filter.getHeight();
        this.height = new ArrayList<>();
        for (String s : filter.getHeight()) {
            this.height.add(s);
        }
//        Collections.copy(this.height, filter.getHeight());
//        this.season = filter.getSeason();
        this.season = new ArrayList<>();
        for (String s : filter.getSeason()) {
            this.season.add(s);
        }
//        Collections.copy(this.season, filter.getSeason());
//        this.temp = filter.temp;
        this.temp = new ArrayList<>();
        for (String s : filter.getTemp()) {
            this.temp.add(s);
        }
//        Collections.copy(this.temp, filter.getTemp());
//        this.difficulty = filter.difficulty;
        this.difficulty = new ArrayList<>();
        for (String s : filter.getDifficulty()) {
            this.difficulty.add(s);
        }
//        Collections.copy(this.difficulty, filter.getDifficulty());
//        this.plantType = filter.plantType;
        this.plantType = new ArrayList<>();
        for (String s : filter.getPlantType()) {
            this.plantType.add(s);
        }
//        Collections.copy(this.plantType, filter.getPlantType());
//        this.bug = filter.bug;
        this.bug = new ArrayList<>();
        for (String s : filter.getBug()) {
            this.bug.add(s);
        }
//        Collections.copy(this.bug, filter.getBug());
    }

}
