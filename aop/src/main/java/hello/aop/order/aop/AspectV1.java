package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV1 {
	
	//hello.aop.order 패키지와 하위 패키지
	@Around("execution(* hello.aop.order..*(..))") // 포인트컷
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable { // advice
		log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처 -> 메서드 정보 찍힘
		return joinPoint.proceed(); // target 호출
	}
}
