package com.bdqn.ssm.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.bdqn.ssm.entity.User;
import com.bdqn.ssm.tools.PageEntity;

public interface UserService {
		public User login(String loginParam, String password);

		public PageEntity findUserByCondition(String userName,
                                              Integer userRole, PageEntity pageEntity);
		
		public User findUserById(String userId);
		
		public List<User> allUser();

		public boolean insert(User user, HttpSession session);

		public boolean update(User user, HttpSession session);

		public boolean delete(Integer id);

		public boolean checkIsExiste(String checkType, String password);

		public boolean pwdIsExiste(Integer id, String password);
}
