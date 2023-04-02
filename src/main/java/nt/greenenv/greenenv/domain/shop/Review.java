package nt.greenenv.greenenv.domain.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nt.greenenv.greenenv.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long orderId;
    private long productId;
    private long writerId;
    private short score;
    private String text;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime uploadDate;
    @Transient
    private String writerNick;

    public Review(ReviewForm form, long writerId) {
        this.productId = form.getProductId();
        this.orderId = form.getOrderId();
        this.writerId = writerId;
        this.score = form.getScore();
        this.text = form.getText();
        this.uploadDate = LocalDateTime.now();
    }

    public Review() {
    }
}
