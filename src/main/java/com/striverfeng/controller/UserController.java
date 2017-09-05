package com.striverfeng.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.striverfeng.core.ApiGatewayHand;
import com.striverfeng.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.striverfeng.service.IUserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	@Autowired
    private ApiGatewayHand apiHand;
	
	@RequestMapping("/userInfo1")
	public String userInfo(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "userInfo";
	}
	@RequestMapping("/userInfo")
	public String userInfo(@RequestParam int id, Model model){
//		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(id);
		model.addAttribute("user", user);
		return "userInfo";
	}

	@RequestMapping(path="/userInfoPath/{id}")
	public String userInfoPath(@PathVariable int id,Model model){
		User user = this.userService.getUserById(id);
		model.addAttribute("user",user);
		return "userInfo";
	}


}