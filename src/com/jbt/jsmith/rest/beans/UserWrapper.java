package com.jbt.jsmith.rest.beans;

/**
 * 
 * @author andrewm
 *
 * Wrapper class for JSON data binding: getting user credentials in a POST request from the client
 * 
 */
public class UserWrapper {

	private String userName;
	private String password;
	private String clientType;
	
	
	
	public UserWrapper(String userName, String password, String clientType) {
		super();
		this.userName = userName;
		this.password = password;
		this.clientType = clientType;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getClientType() {
		return clientType;
	}



	public void setClientType(String clientType) {
		this.clientType = clientType;
	}



	public UserWrapper() {
	}

}
