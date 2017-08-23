package person.lijuntao.boot;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  
public class Example2 {  
      
    @RequestMapping("/hello22/{myName}")  
    public String index(@PathVariable String myName) {  
        return "Hello "+myName+"!!!";  
    }  
    
    @RequestMapping("/hello23/{myName}")  
    public String index2(@PathVariable String myName) {  
    	return "Hello "+myName+"!!!";  
    }  
   
}  