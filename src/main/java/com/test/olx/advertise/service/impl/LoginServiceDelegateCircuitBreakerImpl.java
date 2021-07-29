package com.test.olx.advertise.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.CircuitBreaker; - class
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.olx.advertise.model.User;
import com.test.olx.advertise.service.LoginServiceDelegate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker; //annotation

@Service
@Primary
public class LoginServiceDelegateCircuitBreakerImpl implements LoginServiceDelegate{
	
	@Autowired
	CircuitBreakerFactory circuitBreakerFactory;
	
	@Autowired
	RestTemplate restTemplate;

	/*@Override
	public boolean isLoggedInUser(String authToken) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("AUTH_TOKEN_VALIDATION");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<Boolean> result = circuitBreaker.run(()->this.restTemplate.exchange("http://auth-service/token/validate",  HttpMethod.GET, entity, Boolean.class)
				, throwable -> fallbackForIsLoggedInUser(authToken, throwable));
		return true;
	}*/
	
	@Override
	@CircuitBreaker(name = "AUTH_TOKEN_VALIDATION", fallbackMethod = "fallbackForIsLoggedInUser")
	public boolean isLoggedInUser(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<Boolean> result = this.restTemplate.exchange("http://auth-service/token/validate",  HttpMethod.GET, entity, Boolean.class);
		return true;
	}
	
	public boolean fallbackForIsLoggedInUser(String authToken, Throwable throwable) {
		System.out.println("Circuit Breaker: Login Service Failed - " + throwable);
		return false;
	}
	
	@CircuitBreaker(name = "GET_USER_DETAILS", fallbackMethod = "fallbackForGetUserDetails")
	public User getUserDetails(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		headers.set("auth-token", authToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<User> result = this.restTemplate.exchange("http://auth-service/user",  HttpMethod.GET, entity, User.class);
		return result.getBody();
	}
	
	/* WITH CIRCUIT BREAKER CLASS INSTEAD OF ANNOTATION, EITHER CAN BE USED*/
	/*@Override
	public User getUserDetails(String authToken) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("GET_USER_DETAILS");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		headers.set("auth-token", authToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<User> result = circuitBreaker.run(()->this.restTemplate.exchange("http://auth-service/user",  HttpMethod.GET, entity, User.class)
				, throwable -> fallbackForGetUserDetails(authToken, throwable));
		return result.getBody();
	}*/
	
	public User fallbackForGetUserDetails(String authToken, Throwable throwable) {
		System.out.println("Circuit Breaker: Login Service Failed - " + throwable);
		return null;
	}


}
