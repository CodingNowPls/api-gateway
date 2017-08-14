package com.cn.striverfeng.controller;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.striverfeng.core.ApiGatewayHand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.striverfeng.pojo.User;
import com.cn.striverfeng.service.IUserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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