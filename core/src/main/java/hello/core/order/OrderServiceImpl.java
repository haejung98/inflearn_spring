package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
//클라이언트
@Component
//@RequiredArgsConstructor // final이 붙은 필드를 파라미터로 받는 생성자를 만들어줌
public class OrderServiceImpl implements OrderService{
	
	// 인터페이스, 구현체 둘다 의존(잘못된 방법)
	//private final MemberRepository memberRepository = new MemoryMemberRepository();
	//private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 고정할인금액, DIP 위반(구현체에도 의존하존하고있음), OCP 위반(코드를 변경해야함)
	//private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // OCP 위반(코드를 변경해야함)
	
	
	// 인터페이스에만 의존하도록 코드 변경
	// final은 생성자에서만 값을 바꿀 수 있음
	// 생성자를 통해서만 의존관계 주입받는다
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;
	
	@Autowired
	// 생성자
	public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
		super();
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}
	
/*  수정자 주입
	@Component
	public class OrderServiceImpl implements OrderService{
	    private  MemberRepository memberRepository;
	    private  DiscountPolicy discountPolicy;

	    @Autowired
	    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
	        this.discountPolicy = discountPolicy;
	    }

	    @Autowired(required = false) // 선택적
	    public void setMemberRepository(MemberRepository memberRepository){
	        this.memberRepository = memberRepository;
	    }   
	}
	
	필드 주입
	@Component
	public class OrderServiceImpl implements OrderService{
	    @Autowired
	    private  MemberRepository memberRepository;
	    @Autowired
	    private  DiscountPolicy discountPolicy;
	    
	    setter를 따로 만들어줘서 해결해야한다.
	    public void setMemberRepository(MemberRepository memberRepository) {
	    	this.memberRepository = memberRepository;
	    }
	}
	
	일반 메서드 주입
	@Component
	public class OrderServiceImpl implements OrderService {
	    private MemberRepository memberRepository;
	    private DiscountPolicy discountPolicy;
	
	    @Autowired
	    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
	        this.memberRepository = memberRepository;
	        this.discountPolicy = discountPolicy;
	    }
	}
*/

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId); // 회원정보 조회
		int discountPrice = discountPolicy.discount(member, itemPrice);
		
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}
	
	// 테스트 용도, 강의:@Configuration과 싱글톤
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}

}
