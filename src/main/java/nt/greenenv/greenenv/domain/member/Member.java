package nt.greenenv.greenenv.domain.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String nick;
    private String pw;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createTime;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate birthday;
    // seller
    // member
    // admin
    private String Role;
    @Transient
    private boolean todayIsBirthday;

    public Member(MemberNewForm form,LocalDateTime createTime) {
        this.userId = form.getUser_id();
        this.birthday = form.getBirthday();
        this.nick = form.getNick();
        this.pw = form.getPw();
        this.createTime = createTime.withNano(0);
        Role = "user";
    }

    public Member checkBirthday() {
        //today보다 작으면 -1
        //today와 같으면 0
        //today보다 크면 1
        if ((LocalDate.now().getMonthValue() == birthday.getMonthValue())  && (LocalDate.now().getDayOfMonth() == birthday.getDayOfMonth())) {
            todayIsBirthday = true;
        }
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auth =  new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(this.Role));
        return auth;
    }

    @Override
    public String getPassword() {
        return getPw();
    }

    @Override
    public String getUsername() {
        return getUserId();
    }

    public String getNick() {
        return nick;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
