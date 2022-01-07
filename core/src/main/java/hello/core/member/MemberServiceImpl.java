package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{
	//private final MemberRepository memberRepository = new MemoryMemberRepository(); // 구현객체 선택해주기(new 구현객체), 인터페이스와 구현객체 둘다 의존(잘못된방법)
	private final MemberRepository memberRepository; // 인터페이스에만 의존
	
	@Autowired // 자동 의존관계 주입
	// 생성자를 통해서 memberRepository에 구현체가 뭐가 들어갈지 결정한다.
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public void join(Member member) {
		memberRepository.save(member);	// 다형성에 의해서 MemoryMemberRepository에 있는 override한게 호출된다.
	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}
	
	// 테스트 용도, 강의:@Configuration과 싱글톤
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
	

}
