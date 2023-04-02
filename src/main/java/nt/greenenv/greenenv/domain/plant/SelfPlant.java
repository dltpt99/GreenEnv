package nt.greenenv.greenenv.domain.plant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "selfplant")
public class SelfPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long ownerId;
    private String dirtType;
    private String plantName;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate startDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate lastWaterDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime uploadDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    @Transient
    private LocalDate nextWaterDate;
    @Transient
    private boolean isNeedWater;

    public SelfPlant(SelfPlantNewForm form, long ownerId) {
        this.ownerId = ownerId;
        this.plantName = form.getPlantName();
        this.startDate = form.getStartDate();
        this.lastWaterDate = form.getLastWaterDate();
        this.uploadDate = LocalDateTime.now();
    }

    public SelfPlant() {
    }

    public SelfPlant nextWaterUpdate(int day) {
        this.nextWaterDate = lastWaterDate.plusDays(day);

        return this;
    }

    public SelfPlant checkNeedWater() {
        //today보다 작으면 -1
        //today와 같으면 0
        //today보다 크면 1
        if (nextWaterDate.compareTo(LocalDate.now()) <= 0) {
            setNeedWater(true);
        } else {
            setNeedWater(false);
        }

        return this;
    }
}
