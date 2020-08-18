package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


/**
 * @author fanzk
 * @version 1.8
 * @date 2020/8/17 11:55
 */
@Controller
public class UserController {
	public static final String KEY_USER = "__user__";
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserService userService;


	public ModelAndView handleUnknowException(Exception ex){
		Map<String,String> map = new HashMap();
		map.put("error",ex.getClass().getSimpleName());
		map.put("message",ex.getMessage());
		return new ModelAndView("500.html", map);
	}


	@GetMapping("/")
	public ModelAndView index(HttpSession session){
		User user = (User)session.getAttribute(KEY_USER);
		Map<String,Object> model = new HashMap<>();
		if(user !=null){
			model.put("user",model);
		}
		return new ModelAndView("index.html",model);
	}

	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("register.html");
	}

	@PostMapping("/register")
	public ModelAndView doRegister(@RequestParam("email") String email, @RequestParam("password") String password,
	                               @RequestParam("name") String name) {
		Map map = new HashMap();
		map.put("email",email);
		map.put("error","Register failed");
		try {
			User user = userService.register(email, password, name);
			logger.info("user registered: {}", user.getEmail());
		} catch (RuntimeException e) {
			return new ModelAndView("register.html",map);
		}
		return new ModelAndView("redirect:/signin");
	}

	@PostMapping("/signin")
	public ModelAndView doSignin(@RequestParam("email") String email, @RequestParam("password") String password,
	                             HttpSession session) {
		Map map = new HashMap();
		map.put("email",email);
		map.put("error","Signin failed");
		try {
			User user = userService.signin(email, password);
			session.setAttribute(KEY_USER, user);
		} catch (RuntimeException e) {
			return new ModelAndView("signin.html",map);
		}
		return new ModelAndView("redirect:/profile");
	}

	@GetMapping("/profile")
	public ModelAndView profile(HttpSession session) {
		User user = (User) session.getAttribute(KEY_USER);
		Map map = new HashMap();
		map.put("user",user);
		if (user == null) {
			return new ModelAndView("redirect:/signin");
		}
		return new ModelAndView("profile.html",map);
	}

	@GetMapping("/signout")
	public String signout(HttpSession session) {
		session.removeAttribute(KEY_USER);
		return "redirect:/signin";
	}

	@GetMapping("/resetPassword")
	public ModelAndView resetPassword() {
		throw new UnsupportedOperationException("Not supported yet!");
	}
}
