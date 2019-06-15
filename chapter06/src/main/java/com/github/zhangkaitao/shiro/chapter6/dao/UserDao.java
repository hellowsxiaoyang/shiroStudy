package com.github.zhangkaitao.shiro.chapter6.dao;

import com.github.zhangkaitao.shiro.chapter6.entity.User;

import java.util.Set;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-18:35
 */
public interface UserDao {
	/**
	 * 增、删、该
	 * 通过id(唯一)查询用户、通过username(唯一)查询用户
	 * 建立用户表和角色表关系
	 * 通过用户名查询角色
	 * 通过用户名查询权限
	 * */
	public User createUser(User user);
	public void updateUser(User user);
	public void deleteUser(Long userId);

	public void correlationRoles(Long userId, Long... roleIds);
	public void uncorrelationRoles(Long userId, Long... roleIds);

	User findOne(Long userId);

	User findByUsername(String name);

	Set<String> findRoles(String username);
	Set<String> findPermissions(String username);
}
