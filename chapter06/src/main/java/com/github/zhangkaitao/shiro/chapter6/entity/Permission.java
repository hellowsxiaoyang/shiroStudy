package com.github.zhangkaitao.shiro.chapter6.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-18:19
 */
public class Permission implements Serializable {
	private Long id;
	private String permission; //权限标识,程序中判断使用,如"user:create"
	private String description; //权限描述,UI界面显示使用
	private Boolean available = Boolean.FALSE; //是否可用,如果不可用将不会添加给用户

	public Permission() {
	}

	public Permission(String permission, String description, Boolean available) {
		this.permission = permission;
		this.description = description;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public String getPermission() {
		return permission;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Permission role = (Permission) o;

		//这里需要对id做非空判断
		if (id != null ? !id.equals(role.id) : role.id != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		//这里需要对id做非空判断
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", permission='" + permission + '\'' +
				", description='" + description + '\'' +
				", available=" + available +
				'}';
	}
}
