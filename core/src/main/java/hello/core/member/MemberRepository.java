package hello.core.member;

public interface MemberRepository {
	// 역할
	void save(Member member);
	
	Member findById(Long memberId);
}
