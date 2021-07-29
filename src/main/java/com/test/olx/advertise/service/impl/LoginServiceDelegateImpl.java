package com.test.olx.advertise.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.olx.advertise.model.User;
import com.test.olx.advertise.service.LoginServiceDelegate;

@Service
public class LoginServiceDelegateImpl implements LoginServiceDelegate{

	@Autowired
	RestTemplate restTemplate;

	/*@Override
	public User findByUsername(String username, String authToken) {
		return this.restTemplate.getForObject("http://auth-service/user/" + username, User.class);
	}*/
	
	@Override
	public boolean isLoggedInUser(String authToken) {
		System.out.println("inside login delegate isloogedinuser");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authToken);
		HttpEntity entity = new HttpEntity(headers);
		try {
			ResponseEntity<Boolean> response = 
					this.restTemplate.exchange("http://auth-service/token/validate",  HttpMethod.GET, entity, Boolean.class);
			if(response.getStatusCode() == HttpStatus.OK)
				return response.getBody();
			else
				return false;
		}
		catch(Exception e) {
			return false;
		}
	}


	/*@Override
	public List<Map> findByUsernames(String usernames) {
		// TODO Auto-generated method stub
		return null;
	}*/


	@Override
	public User getUserDetails(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
