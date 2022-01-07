package hello.core.autowired;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import hello.core.member.Member;

//강의: 옵션처리
public class AutowiredTest {
	
	@Test
	void AutowiredOption() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
	}
	
	static class TestBean {
		
		@Autowired(required = false) // (required = true)가 기본인데 Member는 스프링빈이 아니기 때문에 오류남
		public void setNoBean1(Member noBean1) {
			System.out.println("noBean1 = " + noBean1);
		}
		
		@Autowired
		public void setNoBean2(@Nullable Member noBean2) { // 호출은 되지만 null로 들어옴
			System.out.println("noBean2 = " + noBean2);
		}
		
		@Autowired
		public void setNoBean3(Optional<Member> noBean3) { // 스프링빈이 없으면 Optional.empty가 입력됨
			System.out.println("noBean3 = " + noBean3);
		}
	}
}
