package nt.greenenv.greenenv.domain.shop;

import lombok.Data;

@Data
public class OrderNewForm {
    private String address;
    private int amount;
    private String msgToSeller;
    private String msgToDeliver;
}
