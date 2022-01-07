package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import hello.core.scope.SingletonTest.SingletonBean;

public class SingletonWithPrototypeTest1 {
	
	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		prototypeBean1.addCount();
		assertThat(prototypeBean1.getCount()).isEqualTo(1);
		
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		prototypeBean2.addCount();
		assertThat(prototypeBean2.getCount()).isEqualTo(1);
	}
	
	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = 
				new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class); // 자동빈 등록
		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int count1 = clientBean1.logic();
		assertThat(count1).isEqualTo(1);
		
		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		int count2 = clientBean2.logic(); // 생성시점에 주입된 prototypeBean을 그대로 씀
		assertThat(count2).isEqualTo(1);
	}
	
	@Scope("singleton")
	static class ClientBean {
//		private final PrototypeBean prototypeBean; // 생성시점에 주입
		
//		@Autowired // 생성자주입
//		public ClientBean(PrototypeBean prototypeBean) { // 의존관계 주입받음(스프링 컨테이너한테 프로토타입빈 내놔라고 요청), @Autowired생략가능, 생성자 하나이기때문에
//			this.prototypeBean = prototypeBean;
//		}
		
		@Autowired // 필드주입
		private Provider<PrototypeBean> prototypeBeanProvider;
	
		
		public int logic() {
			PrototypeBean prototypeBean = prototypeBeanProvider.get();
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
	
	@Scope("prototype")
	static class PrototypeBean {
		private int count = 0;
		
		public void addCount() {
			count++;
		}
		
		public int getCount() {
			return count;
		}
		
		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init" + this);
		}
		
		@PreDestroy // 호출안됨 -> prototype이기 때문에
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}
	}

}
