package com.citi.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import com.citi.ejb.stock.User;

/**
 * Session Bean implementation class UserList
 * This bean is used to maintain the live users that currently logged in
 * Initially it is empty, whenever there is a new users, this bean is updated by the ControlManager
 */
@Singleton
@LocalBean
public class UserList implements UserListLocal {

    private List<String> userList;
    public UserList() {
        // TODO Auto-generated constructor stub
    	userList = new ArrayList<String>();
    	
    }
	public List<String> getUserList() {
		return userList;
	}
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
    
}
