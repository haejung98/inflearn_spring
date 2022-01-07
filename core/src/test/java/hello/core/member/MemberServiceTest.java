package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.core.AppConfig;

public class MemberServiceTest {
	//MemberService memberService = new MemberServiceImpl();
	
	MemberService memberService;
	
	@BeforeEach // test 실행전에 무조건 실행
	public void beforeEach() {
		AppConfig appconfig = new AppConfig();
		memberService = appconfig.memberService();	
	}
	
	@Test
	void join() {
		//given - 주어졌을떄
		Member member = new Member(1L, "memberA", Grade.VIP);
		
		//when - 이렇게 했을떄
		memberService.join(member);
		Member findMember = memberService.findMember(1L);
		
		//then - 이렇게 된다
		Assertions.assertThat(member).isEqualTo(findMember);
	}
}
