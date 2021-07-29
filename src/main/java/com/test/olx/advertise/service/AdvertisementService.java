package com.test.olx.advertise.service;

import java.util.List;
import java.util.Map;

import com.test.olx.advertise.model.Advertisement;

public interface AdvertisementService {
	
	public Advertisement addAdvertisementSer(Advertisement advertisement, String authToken);
	
	public Map<String, List<Advertisement>> readUserAdvertisementsSer(String authToken);
	
	public Advertisement readSpecificUserAdvertisementSer(String postId, String authToken);
	
	public Boolean deleteSpecificUserAdvertisementSer(String postId, String authToken);
	
	public Map<String, List<Advertisement>> filterAdvertisementSer(
			String searchText, 
			String category,
			String postedBy, 
			String dateCondition, 
			String onDate,
			String fromDate, 
			String toDate, 
			String sortBy,
			String startIndex, 
			String records);
	
	public Map<String, List<Advertisement>> searchAdvertisementSer(String searchText);
	
	public Advertisement getAdvertisementDetailsSer(String postId, String authToken);

	public Advertisement updateAdvertisementSer(String id, Advertisement advertisement, String authToken);
	
}
