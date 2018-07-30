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
    	//required = false makes the pare optional
    	Response rs = new Response();
    	String result = storeUser(username, password, gender, age, major);
    	
    	rs.setResult(result);
    	return rs;
    	
    }
    
    private User findUser(String username, String password) {
    	User user = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account","root","");
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
    
    private String storeUser(String username, String password, String gender, Integer age, String major) {
    	String returnStr = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account","root","");
			PreparedStatement stmt = connection.prepareStatement("insert into student(username, passcode, age, gender, major) values(?, ?, ?, ?, ?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setInt(3, age);
			stmt.setString(4, gender);
			stmt.setString(5, major);
			
			int result = stmt.executeUpdate();
			
			if(result == 0) {
				returnStr = "Fail";
				return returnStr;
			}else {
				returnStr = "Successfully registered!";
				return returnStr;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
    	
    	return returnStr;
    }
    
}
