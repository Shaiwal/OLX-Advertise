package com.test.olx.advertise.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.olx.advertise.service.MasterDataServiceDelegate;

@Service
public class MasterDataServiceDelegateImpl implements MasterDataServiceDelegate {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	@Override
	public List<Map> getAllCategories() {
		List<Map> categories = 
						//this.restTemplate.getForObject("http://localhost:9001/category", List.class);
				this.restTemplate.getForObject("http://MASTERDATA-SERVICE/category", List.class);
		return categories;
	}
/*	
	@Override
	public List<Map> getAllCategories() {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		List<Map> categories = 
				circuitBreaker.run(
						()->this.restTemplate.getForObject("http://localhost:9001/category", List.class),
						throwable -> categoryServiceFallback()
				);
		return categories;
	}
*/	
	public List<Map> categoryServiceFallback() {
		System.out.println("CIRCUIT BREAKER ENABLED!!! No Response From Category Service at this moment. " +
                " Service will be back shortly - " + LocalDate.now());
		return null;
	}
	
/*	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}*/

}
