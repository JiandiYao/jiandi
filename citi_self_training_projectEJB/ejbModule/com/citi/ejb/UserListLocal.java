package com.citi.ejb;

import java.util.List;

import javax.ejb.Local;


@Local
public interface UserListLocal {
	public List<String> getUserList();
	public void setUserList(List<String> userList);
}
