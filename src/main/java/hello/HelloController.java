package hello;

import org.springframework.web.bind.annotation.RestController;

import nz.co.login.Response;
import nz.co.login.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
    
	private static Map<String,User> userMap = new HashMap<>();
	
	static {
    	User user = new User();
    	user.setId("1");
    	user.setName("Shaohai");
    	user.setPassword("123");
    	
    	userMap.put(user.getName(), user);
	}
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Response login(@RequestParam("password") String password, @RequestParam("username") String username) {
    	Response rs = new Response();
    	User user = userMap.get(username);
    	if(user != null && user.getPassword().equals(password)) {
    		rs.setResult("Success");
    		return rs;
    	}
    	rs.setResult("Failure");
        return rs;
    }
    
}
