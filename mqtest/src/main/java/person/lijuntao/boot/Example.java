package person.lijuntao.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@EnableAutoConfiguration  
public class Example {  
      
    @RequestMapping("/")  
    public String home() {  
        return "Hello World!";  
    }  
      
    @RequestMapping("/hello/{myName}")  
    public String index(@PathVariable String myName) {  
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
}  