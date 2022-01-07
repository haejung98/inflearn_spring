package hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

@Configuration // 애플리케이션 설정정보(구성정보)
public class AppConfig {
	
	// 생성자를 통해서 객체가 들어간다 = 생성자 주입
//	public MemberService memberService() {
//		return new MemberServiceImpl(new MemoryMemberRepository()); // MemberServiceImpl을 만들고 내가 만든 MemberServiceImpl은 MemoryMemberRepository()를 사용할거야 => 주입시킴
//	}
//	
//	public OrderService orderService() {
//		return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
//	}
	
	
	// @Bean memberService -> new MemoryMemberRepository()
	// @Bean orderService -> new MemoryMemberRepository()
	// new MemoryMemberRepository() 두번 호출? 싱글톤 깨진거 아닌가? NO! 싱글톤 안깨짐
	
/*	 예상 호출 순서
	 call AppConfig.memberService
	 call AppConfig.memberRepository
	 call AppConfig.memberRepository
	 call AppConfig.orderService
	 call AppConfig.memberRepository
	 
	 진짜 호출 순서
	 call AppConfig.memberService
	 call AppConfig.memberRepository
	 call AppConfig.orderService  
*/
	 
	
	
	// 리팩토링 = new MemoryMemberRepository가 반복된다
	// MemberService 역할
	@Bean
	public MemberService memberService() {
		System.out.println("call AppConfig.memberService");
		return new MemberServiceImpl(memberRepository()); // MemberServiceImpl을 만들고 내가 만든 MemberServiceImpl은 MemoryMemberRepository()를 사용할거야 => 주입시킴
	}
	
	// MemberRepository 역할
	@Bean
	public MemberRepository memberRepository() {
		System.out.println("call AppConfig.memberRepository");
		return new MemoryMemberRepository(); // 구현 클래스
	}
	
	// OrderService 역할
	@Bean
	public OrderService orderService() {
		System.out.println("call AppConfig.orderService");
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	
	// DiscountPolicy 역할
	@Bean
	public DiscountPolicy discountPolicy() {
		// return new FixDiscountPolicy(); // 구현 클래스
		return new RateDiscountPolicy(); // 할인 정책 변경
	}
	
}
