package com.bdqn.ssm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.bdqn.ssm.entity.User;
import com.bdqn.ssm.service.UserService;
import com.bdqn.ssm.tools.SysContent;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String login(String loginParam,String password,Model model,HttpSession session){
		User user=userService.login(loginParam, password);
		if(user==null){
			model.addAttribute("logMsg","密码误");
			return "login";
		}
		session.setAttribute(SysContent.LOGINSESSION,user);
		return "welcome";
	}
	
	@RequestMapping("/password")
	public String password(){
		
		return "password";
	}
	
	@RequestMapping("/isExists")
	@ResponseBody
	public String isExists(String password,HttpSession session){
		 User user = (User) session.getAttribute(SysContent.LOGINSESSION);
		 Integer id = Integer.parseInt(String.valueOf(user.getId()));
		 boolean flag=userService.pwdIsExiste(id,password);
		 Map<String,Object> resultMap=new HashMap<String,Object>();
		 resultMap.put("flag", flag);
		return JSONArray.toJSONString(resultMap);
	}
}
