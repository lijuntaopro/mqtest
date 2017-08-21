package person.lijuntao.boot;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController  
@EnableAutoConfiguration
public class Example extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{  
      
    @RequestMapping("/")  
    public String home() {  
        return "Hello World!";  
    }  
      
    @RequestMapping("/hello/{myName}")  
    public String index(@PathVariable String myName) {  
    	RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    	ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    	HttpServletRequest request = sra.getRequest();
    	Enumeration<String> headerNames = request.getHeaderNames();
    	while(headerNames.hasMoreElements()){
    		System.out.println(headerNames.nextElement() + ":" + request.getHeader(headerNames.nextElement()));
    	}
        return "Hello "+myName+"!!!";  
    }  
    
    @RequestMapping("/hello2/{myName}")  
    public String index2(@PathVariable String myName) {  
    	return "Hello "+myName+"!!!";  
    }  
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }
    
    @Bean()
    public Example2 get(){
    	System.out.println("1222");
    	return new Example2();
    }
    
    public class Example2{
    	
    }

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8084);  
	}
}  