package nt.greenenv.greenenv.domain.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productId;
    private long sellerId;
    private String sellerName;
    private long buyerId;
    private String buyerName;
    private int amount;
    private String deliveryAddress;
    private String buyerMsgToSeller;
    private String buyerMsgToDeliver;
    private boolean isDelivery;
    private boolean isChecked;
    private boolean isFinished;
    private boolean isReviewed;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime orderDate;

//    Order order = new Order(form, buyerId, ordered_product.get().getSellerId(), productId, LocalDateTime.now());
    public Order(OrderNewForm form, long buyerId, long sellerId, long productId, String buyerName, String sellerName,LocalDateTime orderDate ) {
        this.deliveryAddress =  form.getAddress();
        this.buyerMsgToSeller = form.getMsgToSeller();
        this.buyerMsgToDeliver = form.getMsgToDeliver();
        this.buyerId = buyerId;
        this.amount = form.getAmount();
        this.productId = productId;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.orderDate = orderDate;
    }

    public Order() {

    }
}
