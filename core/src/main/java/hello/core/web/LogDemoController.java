package hello.core.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
	
	// 의존관계주입
	private final LogDemoService logDemoService;
	private final MyLogger myLogger; // request scope : httprequest가 들어오고 나갈때까지 쓸 수 있음
//	private final ObjectProvider<MyLogger> myLoggerProvider; // Provider 사용법
	
	@RequestMapping("log-demo")
	@ResponseBody // 문자를 그대로 응답으로 보냄
	public String logDemo(HttpServletRequest request) throws InterruptedException {
		String requestURL = request.getRequestURL().toString();
		
//		MyLogger myLogger = myLoggerProvider.getObject(); // 필요한 시점에 myLogger 받음 -> Provider 특징
		
		System.out.println("myLogger = " + myLogger.getClass());
		myLogger.setRequestURL(requestURL); // url 정보를 myLogger에 저장해둠
		
		myLogger.log("controller test");
		logDemoService.logic("testId");
		return "OK";
	}
}
