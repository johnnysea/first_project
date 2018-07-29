package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    	
    	User user = findUser(username,password);
    	if(user != null) {
    		rs.setResult("Success");
    		rs.setLoginUser(user);
    		return rs;
    	}else {
    		rs.setResult("Failure");
    		return rs;
    	}
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response register(@RequestParam("password") String password, @RequestParam("username") String username, 
    		@RequestParam(value = "gender", required = false) String gender, @RequestParam(value = "age", required = false) Integer age, 
    		@RequestParam(value = "major", required = false) String major) {
    	Response rs = new Response();
    	System.out.println(password);
    	System.out.println(username);
    	System.out.println(gender);
    	System.out.println(age);
    	System.out.println(major);
    	return rs;
    	
    }


    
    
    
    
    
    
    private User findUser(String username, String password) {
    	User user = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student","app","password");
			PreparedStatement stmt = connection.prepareStatement("select * from student where username = ? and passcode = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			ResultSet result = stmt.executeQuery();
			if(!result.next()) {
				return null;
			}else {
				user = new User();
				user.setName(result.getString("username"));
				user.setId(String.valueOf(result.getInt("id")));
				user.setGender(result.getString("gender"));
				user.setAge(result.getInt("age"));
				user.setMajor(result.getString("major"));
				return user;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
    	return user;
    }
    
}
