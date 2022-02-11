package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
@RestController
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass()); // 내 클래스(LogTestController) 지정, @Slf4j 적으면 생략 가능
    
    @RequestMapping("/log-test")
    public String logTest() {
    	String name = "Spring";
    	
    	log.trace("trace log={}", name); 
    	log.debug("debug log={}", name); // 개발서버에서 봄
    	log.info(" info log={}", name); // 중요한 정보
    	log.warn(" warn log={}", name); // 경고
    	log.error("error log={}", name); // 에러
    	
    	return "ok";
    }
    
}
