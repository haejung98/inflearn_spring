package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV3 {
	
	//hello.aop.order 패키지와 하위 패키지
	@Pointcut("execution(* hello.aop.order..*(..))") // 포인트컷
	private void allOrder(){} //pointcut signature
	
	//클래스 이름 패턴이 *Service
	@Pointcut("execution(* *..*Service.*(..))")
	private void allService(){}
	
	@Around("allOrder()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable { // advice
		log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처 -> 메서드 정보 찍힘
		return joinPoint.proceed(); // target 호출
	}
	
	//hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
	@Around("allOrder() && allService()")
	public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
			Object result = joinPoint.proceed();
			log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
			return result;
		} catch (Exception e) {
			log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
			throw e;
		} finally { // 무조건 호출
			log.info("[트랜잭션 릴리즈] {}", joinPoint.getSignature());
		}
	}
}