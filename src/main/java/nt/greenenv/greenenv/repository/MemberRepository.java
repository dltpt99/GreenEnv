package nt.greenenv.greenenv.repository;

import nt.greenenv.greenenv.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNick(String nick);

    Optional<Member> findByUserId(String user_id);
}
