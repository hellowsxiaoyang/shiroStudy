package com.github.zhangkaitao.shiro.chapter6.dao;

import com.github.zhangkaitao.shiro.chapter6.JdbcTemplateUtils;
import com.github.zhangkaitao.shiro.chapter6.entity.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-20:47
 */
public class RoleDaoImpl implements RoleDao{
	private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();

	public Role createRole(final Role role) {
		final String sql = "insert into sys_roles(role, description, available) values(?,?,?) ";

		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
				ps.setString(1,role.getRole());
				ps.setString(2,role.getDescription());
				ps.setBoolean(3,role.getAvailable());

				return ps;
			}
		},generatedKeyHolder);

		role.setId(generatedKeyHolder.getKey().longValue());

		return role;
	}

	public void deleteRole(Long roleId) {
		//先把和roleId相关的表数据删除
		String sql = "delete from sys_users_roles where role_id=?";
		jdbcTemplate.update(sql,roleId);

		sql = "delete from sys_roles where id=?";
		jdbcTemplate.update(sql,roleId);
	}

	public void correlationPermissions(Long roleId, Long... permissionIds) {
		if(permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "insert into sys_roles_permissions(role_id, permission_id) values(?,?)";
		for(Long permissionId : permissionIds) {
			if(!exists(roleId, permissionId)) {
				jdbcTemplate.update(sql, roleId, permissionId);
			}
		}
	}

	private boolean exists(Long roleId, Long permissionId) {
		String sql = "select count(1) from sys_roles_permissions where role_id=? and permission_id=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, roleId, permissionId) != 0;
	}

	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
		if(permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "delete from sys_roles_permissions where role_id=? and permission_id=?";
		for(Long permissionId : permissionIds) {
			if(exists(roleId, permissionId)) {
				jdbcTemplate.update(sql, roleId, permissionId);
			}
		}
	}
}
