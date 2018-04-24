package com.cn.hnust.controller;


import java.util.Date;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.hnust.pojo.ConditionUser;
import com.cn.hnust.pojo.User;
import com.cn.hnust.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController
{
    @Resource
    private IUserService userService;
    
    @RequestMapping("/showUser")
    public ModelAndView toIndex(Integer userId)
    {
        User user = this.userService.getUserById(userId);
        ModelAndView view = new ModelAndView();
        view.setViewName("showUser");
        view.addObject("user", user);
        return view;
    }
    
    @RequestMapping("/queryUser")
	public String queryUser(
			Model model) throws Exception {
    	List<User> list=this.userService.selectAll();
    	model.addAttribute("list", list);
		return "user/queryUser";
		
	}
    
    @RequestMapping("/searchUsers")
	public String searchUsers(
			ConditionUser conditionUser,Model model) throws Exception {
    	List<User> list=this.userService.searchUsers(conditionUser);
    	model.addAttribute("list", list);
     	return "user/queryUser";
		
	}
    
    @RequestMapping(value="/editUser/{id}",method = RequestMethod.GET)
	public String editstudent(HttpServletRequest request,Model model,@PathVariable int id) throws Exception {
		
		//System.out.println("request.getParameter..id="+request.getParameter("id"));
		System.out.println("id="+id);
		User user = this.userService.getUserById(id);
		model.addAttribute("user", user);
	
		return "user/editUser";
	}
    
    @RequestMapping("/editUserSubmit")
	public String editstudentsubmit(User user)throws Exception{
		
		//System.out.println(user.getId());
		
		this.userService.updateByPrimaryKey(user);
		
		return "forward:queryUser";
		
	}
    
    @RequestMapping("/deleteUser")
	public String deletestu(int[] deleteid)throws Exception{
		
		//System.out.println(deleteid.length);
		for(int id:deleteid){
			this.userService.deleteByPrimaryKey(id);
		}
		return "forward:queryUser";
	}
    
    @RequestMapping("/addUser")
   	public String addUser(User user)throws Exception{
    	this.userService.insertSelective(user);
   		
   		return "forward:queryUser";
   	}
    
    @ResponseBody
    @RequestMapping("/showUser2")
    public User jsonUser(Integer userId)
    {
        User user = this.userService.getUserById(userId);
        return user;
    }
}
