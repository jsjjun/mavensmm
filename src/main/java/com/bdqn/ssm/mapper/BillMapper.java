package com.bdqn.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bdqn.ssm.entity.Bill;
import com.bdqn.ssm.entity.Provider;
import com.bdqn.ssm.entity.User;

public interface BillMapper {

	int totalCount(Map<String, Object> paramValues);

	List<Bill> findByCondition(Map<String, Object> paramValues);

	List<Provider> selctToBill();

	List<Bill> selectByCondition(Map<String, Object> paramValues);

	int insert(Bill bill);

	int update(Bill bill);

	int delete(@Param("billId") Integer billId);

}
