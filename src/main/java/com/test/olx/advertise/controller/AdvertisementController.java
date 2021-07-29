package com.test.olx.advertise.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.olx.advertise.model.Advertisement;
import com.test.olx.advertise.service.AdvertisementService;

import io.swagger.annotations.ApiParam;

@RestController
public class AdvertisementController {
	
	@Autowired
	AdvertisementService advertisementService;
	
	@RequestMapping(path = "/advertise", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Advertisement> addAdvertisement(@ApiParam(value = "Advertisement Object") @RequestBody Advertisement advertisement, @RequestHeader("auth-token") String authToken){
		return new ResponseEntity<Advertisement>(this.advertisementService.addAdvertisementSer(advertisement, authToken), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/advertise/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Advertisement> updateAdvertisement(@ApiParam(value = "Path param Ad Id") @PathVariable(name = "id") String id, @ApiParam(value = "Advertisement Object") @RequestBody Advertisement advertisement, @RequestHeader("auth-token") String authToken){
		return new ResponseEntity<Advertisement>(this.advertisementService.updateAdvertisementSer(id, advertisement, authToken), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/user/advertise", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String, List<Advertisement>>> readUserAdvertisements(@RequestHeader("auth-token") String authToken){
		return new ResponseEntity<Map<String, List<Advertisement>>>(this.advertisementService.readUserAdvertisementsSer(authToken), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/user/advertise/{postId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Advertisement> readSpecificUserAdvertisement(@ApiParam(value = "Path param Ad postId") @PathVariable(name = "postId") String postId, @RequestHeader("auth-token") String authToken){
		return new ResponseEntity<Advertisement>(this.advertisementService.readSpecificUserAdvertisementSer(postId, authToken), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/user/advertise/{postId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Boolean> deleteSpecificUserAdvertisement(@ApiParam(value = "Path param Ad post Id")@PathVariable(name = "postId") String postId, @RequestHeader("auth-token") String authToken){
		return new ResponseEntity<Boolean>(this.advertisementService.deleteSpecificUserAdvertisementSer(postId, authToken), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/advertise/search/filtercriteria", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String, List<Advertisement>>> filterAdvertisement(
			@ApiParam(value = "Search Text") @RequestParam(name="searchText") String searchText, 
			@ApiParam(value = "Adv. Category") @RequestParam(name="category") String category,
			@ApiParam(value = "Adv Posted by user") @RequestParam(name="postedBy") String postedBy, 
			@ApiParam(value = "Adv. date condition") @RequestParam(name="dateCondition") String dateCondition, 
			@ApiParam(value = "Adv. date") @RequestParam(name="onDate") String onDate,
			@ApiParam(value = "Adv. from date") @RequestParam(name="fromDate") String fromDate, 
			@ApiParam(value = "Adv. to date") @RequestParam(name="toDate") String toDate, 
			@ApiParam(value = "Sort option for Adv.") @RequestParam(name="sortBy") String sortBy,
			@ApiParam(value = "Adv. start index") @RequestParam(name="startIndex") String startIndex, 
			@ApiParam(value = "Adv. records") @RequestParam(name="records") String records){
		return new ResponseEntity<Map<String, List<Advertisement>>>(this.advertisementService.filterAdvertisementSer(searchText, category, postedBy, dateCondition, onDate, fromDate, toDate, sortBy, startIndex, records), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/advertise/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String, List<Advertisement>>> searchAdvertisement(@ApiParam(value = "Ad search text") @RequestParam(name="searchText") String searchText){
		return new ResponseEntity<Map<String, List<Advertisement>>>(this.advertisementService.searchAdvertisementSer(searchText), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/advertise/{postId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Advertisement> getAdvertisementDetails(@ApiParam(value = "Path param Ad postId") @PathVariable(name = "postId") String postId, @RequestHeader("auth-token") String authToken){
		return new ResponseEntity<Advertisement>(this.advertisementService.getAdvertisementDetailsSer(postId, authToken), HttpStatus.OK);
	}
	
}
