package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import hello.core.AppConfig;

public class statefulServiceTest {
	
	@Test
	void statefulServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);

	/*
		// 문제점: B사용자 주문금액이 나옴
		// ThreadA: A사용자 10000원 주문
		statefulService1.order("userA", 10000);
		// ThreadB: B사용자 20000원 주문
		statefulService1.order("userB", 20000);
		
		// ThreadA: 사용자A 주문 금액 조회
		int price = statefulService1.getPrice();
		System.out.println("price = " + price); // 20000원
		
		Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
	*/
		
		// 해결 방법 - 지역변수
		int userAPrice = statefulService1.order("userA", 10000);
		int userBPrice = statefulService1.order("userB", 20000);
		System.out.println("price = " + userAPrice);
		
		
	}
	
	static class TestConfig {
		
		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
		
	}

}
