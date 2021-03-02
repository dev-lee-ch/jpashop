package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // JPA 어노테이션 JAP 엔티티 매니저 자동주입
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 영속화
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)   // JPQL 쿼리N
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)  // 조건설정 조건명 :{name}
                .setParameter("name", name) // 파라미터 설정
                .getResultList();
    }

}