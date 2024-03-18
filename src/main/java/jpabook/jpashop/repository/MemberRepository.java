package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    // @PersistenceContext
    // @Autowired // SpringBoot JPA가 지원하는 기능이다. @Autowired로도 @PersistenceContext를 대체할 수 있다.
    // 그렇다면 @Autowired없이 @RequiredArgsConstructor로도 대체가 가능하다.
    private final EntityManager em;


    public void save(Member member) {
        em.persist(member); // JPA가 Member를 저장하는 로직
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
