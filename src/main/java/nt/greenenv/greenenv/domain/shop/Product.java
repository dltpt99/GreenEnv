package nt.greenenv.greenenv.domain.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long sellerId;
    private String sellerName;
    private String plantName;
    private int cost;
    private String img;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime upload;
    private String descript;

    public Product() { }

    public Product(ProductNewForm form, long sellerId, String sellerName, LocalDateTime upload) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.upload = upload;

        this.img = null;

        this.plantName = form.getPlantName();
        this.cost = form.getCost();
        this.descript = form.getDesc();
    }
}
