package com.bdqn.ssm.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdqn.ssm.entity.Bill;
import com.bdqn.ssm.service.BillService;
import com.bdqn.ssm.tools.Page;
import com.sun.org.apache.regexp.internal.recompile;

@Controller
@RequestMapping("/bill")
public class BillController {
	
	@Autowired
	private BillService billService;
	
	@RequestMapping("/billList")
	public String billList(String findParam,
							String findParam1,
							String findParam2,
							Integer pageIndex,
							Model model){
		Page page=new Page();
		if(pageIndex!=null && !"".equals(pageIndex)){
			page.setPageIndex(pageIndex);
		}
		
		page = billService.findBillByCondition(findParam,findParam1,findParam2,page);
		
		model.addAttribute("List", billService.selctToBill());
		model.addAttribute("page", page);
		return "bill/billList";
	}
	
	@RequestMapping("/billAdd")
	public String billAdd(Model model){
		model.addAttribute("list", billService.selctToBill());
		return "bill/billAdd";
	}
	@RequestMapping("/insert")
	public String insert(Bill bill,HttpSession session,HttpServletRequest request){
		boolean flag = billService.insert(bill,session);
		String msg="成功";
		if(flag){
			msg="成功";
		}
		request.setAttribute("msg", msg);
		return "redirect:/bill/billList";
	}
	
	@RequestMapping("/billView/{id}")
	public String billView(@PathVariable Integer id,HttpServletRequest request){
		request.setAttribute("list", billService.findBillById(id));
		
		return "bill/billView";
	}
	
	@RequestMapping("/billUpdate/{id}")
	public String billUpdate(@PathVariable Integer id,HttpServletRequest request){
		request.setAttribute("BillList", billService.selctToBill());
		request.setAttribute("list", billService.findBillById(id));
		return "bill/billUpdate";
	}
	
	@RequestMapping("/update")
	public String update(Bill bill,HttpSession session,HttpServletRequest request){
		boolean flag = billService.update(bill,session);
		String msg="成功";
		if(flag){
			msg="成功";
		}
		request.setAttribute("msg", msg);
		return "redirect:/bill/billList";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam Integer billId){
		boolean flag=billService.delete(billId);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("flag", flag);
		return resultMap;
	}
}
