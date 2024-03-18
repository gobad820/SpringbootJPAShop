package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 자동으로 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository;

    // @Autowired // 어노테이션이 없어도 스프링이 자동으로 주입시켜준다.
    // public MemberService(MemberRepository memberRepository) {
    //     this.memberRepository = memberRepository;
    // }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional // default는 readOnly = false
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // 항상 ID값은 들어가있다.
    }

    /**
     * 중복 회원 검증 로직
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 한 건만 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
