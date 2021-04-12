package com.w2m.examen.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class LoginDTO {
	
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String username;
	
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String password;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDTO [username=" + username + ", password=" + password + "]";
	}
}
