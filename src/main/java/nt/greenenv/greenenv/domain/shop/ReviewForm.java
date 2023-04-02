package nt.greenenv.greenenv.domain.shop;

import lombok.Data;

@Data
public class ReviewForm {
    private long productId;
    private long orderId;
    private short score;
    private String text;
}
