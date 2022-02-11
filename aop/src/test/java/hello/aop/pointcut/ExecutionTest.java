package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutionTest {
	
	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(); // 포인트컷 표현식 처리해주는 클래스
	Method helloMethod;
	
	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}
	
	@Test
	// helloMethod = public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
	void printMethod() {
		log.info("helloMethod={}", helloMethod);
	}
	
	@Test
	void exactMatch() {
		// public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
		pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void allMatch() {
		pointcut.setExpression("execution(* *(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void nameMatch() {
		pointcut.setExpression("execution(* hello(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void nameMatchStar1() {
		pointcut.setExpression("execution(* hel*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void nameMatchStar2() {
		pointcut.setExpression("execution(* *el*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void nameMatchFalse() { // 매칭실패
		pointcut.setExpression("execution(* nono(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}
	
	// 패키지 매칭
	@Test
	void packageExactMatch1() { // 매칭실패
		pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void packageExactMatch2() { // 매칭실패
		pointcut.setExpression("execution(* hello.aop.member.*.*(..))"); // hello.aop.member 안에 있는건 다 매칭
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void packageExactFalse() { // 매칭실패
		pointcut.setExpression("execution(* hello.aop.*.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}
	
	@Test
	void packageMatchSubPackage1() { // member랑 member하위에 있는 모든 패키지 포함
		pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void packageMatchSubPackage2() { // hello.aop랑 하위에 있는 모든 패키지 포함
		pointcut.setExpression("execution(* hello.aop..*.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	// 타입 매칭
	@Test
	void typeExactMatch() { 
		pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void typeMatchSuperType() {
		pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))"); // 인터페이스(부모) -> 부모타입으로해도 매칭된다.
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void typeMatchInternal() throws NoSuchMethodException { 
		pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
		
		Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class); // 자식한테 있는 메소드
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
		pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))"); // 인터페이스(부모) -> 부모타입으로해도 매칭된다.
		
		Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class); // 자식한테 있는 메소드
		assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse(); // 부모타입에 선언한 메서드까지만 매칭된다 -> 부모엔 internal 메소드가 없음
	}
	
	//String 타입의 파라미터 허용
	//(String)
	@Test
	void argsMatch() {
		pointcut.setExpression("execution(* *(String))"); 
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	//파라미터가 없어야 함
	//()
	@Test
	void argsMatchNoArgs() {
		pointcut.setExpression("execution(* *())"); 
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}
	
	//정확히 하나의 파라미터 허용, 모든 타입 허용
	//(Xxx)
	@Test
	void argsMatchStar() {
		pointcut.setExpression("execution(* *(*))"); 
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	//숫자와 무관하게 모든 파라미터, 모든 타입 허용
	//(), (Xxx), (Xxx, Xxx)
	@Test
	void argsMatchAll() {
		pointcut.setExpression("execution(* *(..))"); 
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	//String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
	//(String), (String, Xxx), (String, Xxx, Xxx)
	@Test
	void argsMatchComplex() {
		pointcut.setExpression("execution(* *(String, ..))"); 
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
}
