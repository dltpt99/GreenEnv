package nt.greenenv.greenenv.service;

import nt.greenenv.greenenv.domain.member.Member;
import nt.greenenv.greenenv.domain.member.MemberNewForm;
import nt.greenenv.greenenv.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    private MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Member signup(MemberNewForm form) {
        if (repository.findByUserId(form.getUser_id()).isPresent()) {
            throw new IllegalStateException("ID가 이미 존재합니다");
        }

        if (repository.findByNick(form.getNick()).isPresent()) {
            throw new IllegalStateException("닉네임이 이미 존재합니다.");
        }

        Member member = new Member(form, LocalDateTime.now());
        member.setPw(new BCryptPasswordEncoder().encode(form.getPw()));

        return repository.save(member);
    }
/*
    public Optional<Session> login(LoginForm form) {
        Optional<Member> member = repository.findByUserId(form.getUser_id());
        if (member.isEmpty()) {
            return Optional.empty();
        }
        Session session = new Session();
        session.setUser_id(member.get().getUserId());
        session.setNick(member.get().getNick());
        session.setBirthday(member.get().getBirthday());
        return Optional.ofNullable(session);
    }*/

    public Member getMemberById(long id) {
        return repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 일치하지 않습니다")
        );
    }

    public List<Member> getAllMember() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다."));
        return member;
    }

    public Member getNickById(long Id) {
        Optional<Member> member = repository.findById(Id);
        member.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        return member.get();
    }
}
