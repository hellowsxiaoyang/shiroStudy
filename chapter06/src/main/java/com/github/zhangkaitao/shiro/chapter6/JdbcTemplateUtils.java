package com.github.zhangkaitao.shiro.chapter6;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-18:51
 */
public class JdbcTemplateUtils {
	private static JdbcTemplate jdbcTemplate;

	public static JdbcTemplate jdbcTemplate(){
		if (jdbcTemplate == null){
			jdbcTemplate = createJdbcTemplate();
		}
		return jdbcTemplate;
	}

	private static JdbcTemplate createJdbcTemplate(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?useSSL=false&serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		return new JdbcTemplate(dataSource);
	}
}
