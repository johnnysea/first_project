package hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nz.co.login.Response;
import nz.co.login.User;

@Controller
public class HelloController {
    
	private static Map<String,User> userMap = new HashMap<>();
	
	static {
    	User user = new User();
    	user.setId("1");
    	user.setName("Shaohai");
    	user.setPassword("123");
    	
    	userMap.put(user.getName(), user);
	}
	
	@GetMapping("/")
    public String index() {
    	return "index";
    }	
	
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Response login(@RequestParam("password") String password, @RequestParam("username") String username) {
    	System.out.println(username);
    	System.out.println(password);
    	Response rs = new Response();
    	User user = userMap.get(username);
    	if(user != null && user.getPassword().equals(password)) {
    		rs.setResult("Success");
    		rs.setLoginUser(user);
    		return rs;
    	}
    	rs.setResult("Failure");
        return rs;
    }
    
}
