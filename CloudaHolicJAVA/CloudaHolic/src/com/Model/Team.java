package com.Model;

import java.util.List;

public class Team {
	public String NAME ;
    public Team(String nAME, List<User> uSERS) {
		NAME = nAME;
		USERS = uSERS;
	}
	public List<User> USERS ;
    
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public List<User> getUSERS() {
		return USERS;
	}
	public void setUSERS(List<User> uSERS) {
		USERS = uSERS;
	}
    
    
}
