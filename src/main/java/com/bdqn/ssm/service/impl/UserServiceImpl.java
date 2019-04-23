package com.bdqn.ssm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.bdqn.ssm.entity.User;
import com.bdqn.ssm.mapper.UserMapper;
import com.bdqn.ssm.service.UserService;
import com.bdqn.ssm.tools.PageEntity;
import com.bdqn.ssm.tools.SysContent;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User login(String loginParam, String password) {
		User user=userMapper.login(loginParam);
		if(user==null){
			return null;
		}
		/*String md5Pass=DigestUtils.md5DigestAsHex(password.getBytes());
		if(!md5Pass.equals(user.getUserPassword())){
			return null;
		}*/
		user.setUserPassword(null);
		return user;
	}
	
	public PageEntity findUserByCondition(String userName,Integer userRole,
			PageEntity pageEntity) {
		Map<String, Object> paramValues = new HashMap<String, Object>();
		paramValues.put("userName", userName);
		paramValues.put("userRole", userRole);
		int totalCount = userMapper.totalCount(paramValues);
		pageEntity.setTotalCount(totalCount);
		paramValues.put("start",
				(pageEntity.getPageIndex() - 1) * pageEntity.getPageSize());
		paramValues.put("size", pageEntity.getPageSize());
		List<User> userList = userMapper.findByCondition(paramValues);
		
		Calendar da = Calendar.getInstance();
		int year = da.get(Calendar.YEAR);
		SimpleDateFormat mat = new SimpleDateFormat("yyyy-mm-dd");
		for (User user : userList) {
	        long yy = year-Integer.parseInt(mat.format(user.getBirthday()).substring(0,4));
	        user.setModifyBy(yy);
			}
		pageEntity.setDataList(userList);
		return pageEntity;
	}
	
	public User findUserById(String userId){
		Integer id = 0;
		if(userId!=null && !"".equals(userId)){
			id = Integer.parseInt(userId);
		}
		if(id==0){
			return null;
		}
		Map<String,Object> paramValues = new HashMap<String,Object>();
		paramValues.put("userId", id);
		List<User> userList = userMapper.selectByCondition(paramValues);
		if(userList!=null && userList.size()==1){
			return userList.get(0);
		}
		return null;
	}

	public List<User> allUser() {
		List<User> userList = userMapper.selectByCondition(null);
		
		return userList;
	}

	public boolean insert(User user, HttpSession session) {
		User loginUser = (User) session.getAttribute(SysContent.LOGINSESSION);
		user.setCreatedBy(loginUser.getId());
		user.setCreateDate(new Date());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getUserPassword()
				.getBytes());
		user.setUserPassword(md5Pass);
		int num = userMapper.insert(user);
		if (num > 0) {
			return true;
		}
		return false;
	}

	public boolean update(User user, HttpSession session) {
		User loginUser = (User) session.getAttribute(SysContent.LOGINSESSION);
		if(loginUser!=null){
			user.setModifyBy(loginUser.getId());
		}
		user.setModifyDate(new Date());
		int num = userMapper.update(user);
		if(num>0){
			return true;
		}
		return false;
	}

	public boolean delete(Integer id) {
		int num = userMapper.delete(id);
		if(num>0){
			return true;
		}
		return false;
	}

	public boolean checkIsExiste(String checkType, String password) {
		Integer total = userMapper.checkIsExists(checkType,password);
		if(total!=null && total>0){
			return true;
		}
		return false;
	}

	public boolean pwdIsExiste(Integer id, String password) {
		// TODO Auto-generated method stub
		return userMapper.updateUserPwd(password,id);
	}
}
