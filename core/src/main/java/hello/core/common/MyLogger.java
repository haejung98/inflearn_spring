package hello.core.common;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

// 강의: requestscope
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // 프록시
public class MyLogger {
	
	private String uuid;
	private String requestURL;
	
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	
	public void log(String message) {
		System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
	}
	
	@PostConstruct // 요청이 들어올 때 최초로 스프링 달라할 때 호출됨
	public void init() {
		uuid = UUID.randomUUID().toString();
		System.out.println("[" + uuid + "] request scope bean create:" + this);
	}
	
	@PreDestroy // 요청이 빠져나갈 때 호출됨
	public void close() {
		System.out.println("[" + uuid + "] request scope bean close:" + this);
	}

}
