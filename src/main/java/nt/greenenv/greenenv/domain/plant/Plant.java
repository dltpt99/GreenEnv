package nt.greenenv.greenenv.domain.plant;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "plant")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String plantName; // 식물이름
    private String season; //계절
    private String growSpeed; //생장속도
    private String growTemp; //생장온도
    private String height; // 식물 높이
    private String feature; // 특징
    private String plantType; // 관상용, 공기정화용 등
    private String difficulty; // 생육 난이도
    private int fertilizerCycle; // 비료 주기 (0은 비료필요없음)
    private int waterCycleSpring; // 물 주기 봄
    private int waterCycleSummer; // 물 주기 여름
    private int waterCycleFall; // 물 주기 가을
    private int waterCycleWinter; // 물 주기 겨울
    private String bug; // 병충해
}