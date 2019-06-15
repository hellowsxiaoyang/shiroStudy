package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.dao.UserDao;
import com.github.zhangkaitao.shiro.chapter6.dao.UserDaoImpl;
import com.github.zhangkaitao.shiro.chapter6.entity.User;

import java.util.Set;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-21:22
 */
public class UserServiceImpl implements UserService{
	private UserDao userDao = new UserDaoImpl();
	private PasswordHelper passwordHelper = new PasswordHelper();

	/**
	 * 创建用户
	 * */
	public User createUser(User user) {
		//加密密码
		passwordHelper.encryptPassword(user);
		return userDao.createUser(user);
	}

	/**
	 * 修改密码
	 * */
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);

		passwordHelper.encryptPassword(user);

		userDao.updateUser(user);
	}

	/**
	 * 添加用户-角色关系
	 * */
	public void correlationRoles(Long userId, Long... roleIds) {
		userDao.correlationRoles(userId, roleIds);
	}

	/**
	 * 移除用户-角色关系
	 * */
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		userDao.uncorrelationRoles(userId, roleIds);
	}

	/**
	 * 根据用户名查询用户
	 */
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	/**
	 * 根据用户名查询角色
	 * */
	public Set<String> findRoles(String username) {
		return userDao.findRoles(username);
	}

	/**
	 * 根据用户名查询权限
	 * */
	public Set<String> findPermissions(String username) {
		return userDao.findPermissions(username);
	}
}
