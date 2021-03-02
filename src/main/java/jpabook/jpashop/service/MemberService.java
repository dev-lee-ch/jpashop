package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA 성능 최적화 방법 중 하나 (더티 체킹X)
@RequiredArgsConstructor    // Lombok - final을 가진 필드만 생성자로 만들고,  최신버전 spring의 경우 하나의 생성자만 존재하는 경우 자동으로 injection을 해줌.
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired // 최신버전 spring의 경우 하나의 생성자만 존재하는 경우 자동으로 injection을 해줌.
//    public MemberService(MemberRepository memberRepository) {   // 생성자 injection
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복회원 체크

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원명 중복 체크 - 중복시 Exception 발생
     */
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원전체조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원한건조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
