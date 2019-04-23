package com.bdqn.ssm.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.bdqn.ssm.entity.Bill;
import com.bdqn.ssm.entity.Provider;
import com.bdqn.ssm.tools.Page;

public interface BillService {
	
	Page findBillByCondition(String findParam, String findParam1,
                             String findParam2, Page page);

	public List<Provider> selctToBill();

	Bill findBillById(Integer id);

	boolean insert(Bill bill, HttpSession session);

	boolean update(Bill bill, HttpSession session);

	boolean delete(Integer billId);

}
