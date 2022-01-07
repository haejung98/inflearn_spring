package hello.core.discount;

import org.springframework.stereotype.Component;

import hello.core.member.Grade;
import hello.core.member.Member;

@Component
public class FixDiscountPolicy implements DiscountPolicy{

	private int discountFixAmount = 1000; // 1000원 할인
	
	@Override
	public int discount(Member member, int price) {
		if(member.getGrade() == Grade.VIP) { // vip일때만 1000원 할인, enum은 == 사용
			return discountFixAmount;
		} else {
			return 0;
		}
	}
}
