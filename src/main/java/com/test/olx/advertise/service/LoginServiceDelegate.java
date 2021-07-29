package com.test.olx.advertise.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.test.olx.advertise.model.User;

public interface LoginServiceDelegate {
	//public List<Map> findByUsernames(String usernames);
	//public User findByUsername(String username, String authToken);
	public boolean isLoggedInUser(String authToken);
	public User getUserDetails(String authToken);
}

