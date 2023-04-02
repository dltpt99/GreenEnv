package nt.greenenv.greenenv.domain.shop;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductNewForm {
    private String plantName;
    private int cost;
    private String desc;

}
