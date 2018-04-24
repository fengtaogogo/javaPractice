package com.cn.hnust.pojo;

public class ConditionUser {
	private String name;
	private String password;
	private Integer minAge;
	private Integer maxAge;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
	public ConditionUser(String name, String password, Integer minAge,
			Integer maxAge) {
		super();
		this.name = name;
		this.password = password;
		this.minAge = minAge;
		this.maxAge = maxAge;
	}
	public ConditionUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ConditionUser [name=" + name + ", password=" + password
				+ ", minAge=" + minAge + ", maxAge=" + maxAge + "]";
	}
	
}
