package nt.greenenv.greenenv.domain.member;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
public class MemberNewForm {
    private String user_id;
    private String nick;
    private String pw;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
