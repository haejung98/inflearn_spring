package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan ( // 자동으로 스프링 빈을 등록해줌
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // @Configuration ������ �������� -> ���� ������ �ڵ����� ��ϵǱ� ������
)
public class AutoAppConfig {

}
