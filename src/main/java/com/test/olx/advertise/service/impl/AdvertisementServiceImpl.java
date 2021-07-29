package com.test.olx.advertise.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.olx.advertise.entity.AdvertisementEntity;
import com.test.olx.advertise.exception.InvalidAuthorizationTokenException;
import com.test.olx.advertise.model.Advertisement;
import com.test.olx.advertise.model.User;
import com.test.olx.advertise.repository.AdvertisementRepository;
import com.test.olx.advertise.service.AdvertisementService;
import com.test.olx.advertise.service.LoginServiceDelegate;
import com.test.olx.advertise.service.MasterDataServiceDelegate;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{
	
	@Autowired
	private AdvertisementRepository advertiseRepo;

	@Autowired
	private LoginServiceDelegate loginServiceDelegate;
	
	@Autowired
	private MasterDataServiceDelegate masterDataServiceDelegate;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Advertisement addAdvertisementSer(Advertisement advertisement, String authToken) {
		boolean isvalidUser = loginServiceDelegate.isLoggedInUser(authToken);
		System.out.println(isvalidUser);
		if(!isvalidUser)
			throw new InvalidAuthorizationTokenException(authToken);
		User user = loginServiceDelegate.getUserDetails(authToken);
		System.out.println(user);
		advertisement.setUsername(user.getUserName());
		AdvertisementEntity AdvertisementEntity = new AdvertisementEntity(advertisement.getTitle(), advertisement.getDescription(), advertisement.getPrice(), advertisement.getCategory(), new Date(), new Date(), "1", advertisement.getUsername());
		AdvertisementEntity = advertiseRepo.save(AdvertisementEntity);
		return new Advertisement(AdvertisementEntity.getId(), AdvertisementEntity.getTitle(), AdvertisementEntity.getDescription(), AdvertisementEntity.getPrice(), AdvertisementEntity.getCategory(), AdvertisementEntity.getCreatedDate(), AdvertisementEntity.getModifiedDate(), AdvertisementEntity.getStatus(), AdvertisementEntity.getUsername(), advertisement.getPostedBy());

	}

	@Override
	public Advertisement updateAdvertisementSer(String id, Advertisement advertisement, String authToken) {
		boolean isvalidUser = loginServiceDelegate.isLoggedInUser(authToken);
		User user = loginServiceDelegate.getUserDetails(authToken);
		advertisement.setUsername(user.getUserName());
		if(!isvalidUser)
			throw new InvalidAuthorizationTokenException(authToken);
		AdvertisementEntity existingAdvertisement = advertiseRepo.getById(Long.valueOf(id));
		if(existingAdvertisement == null)
			throw new RuntimeException("Advertisement with given Id doesn't exist to update: "+ id);
		existingAdvertisement.setId(Integer.valueOf(id));
		existingAdvertisement.setCategory(advertisement.getCategory());
		existingAdvertisement.setUsername(user.getUserName());
		existingAdvertisement.setDescription(advertisement.getDescription());
		existingAdvertisement.setTitle(advertisement.getTitle());
		existingAdvertisement.setPrice(advertisement.getPrice());
		existingAdvertisement.setModifiedDate(new Date());
		advertiseRepo.save(existingAdvertisement);
		return advertisement;

	}

	@Override
	public Map<String, List<Advertisement>> readUserAdvertisementsSer(String authToken) {
		boolean isvalidUser = loginServiceDelegate.isLoggedInUser(authToken);
		User user = loginServiceDelegate.getUserDetails(authToken);
		if(!isvalidUser)
			throw new InvalidAuthorizationTokenException(authToken);
		List<AdvertisementEntity> advertiseEntities = advertiseRepo.findByUsername(user.getUserName());
		List<Advertisement> advertises = new ArrayList<Advertisement>();
		advertiseEntities.stream().forEach((AdvertisementEntity)-> 
			advertises.add(new Advertisement(AdvertisementEntity.getId(), AdvertisementEntity.getTitle(), AdvertisementEntity.getDescription(), AdvertisementEntity.getPrice(), AdvertisementEntity.getCategory(), AdvertisementEntity.getCreatedDate(), AdvertisementEntity.getModifiedDate(), AdvertisementEntity.getStatus(), AdvertisementEntity.getUsername(), user.getFirstName() + " " + user.getLastName()))
		);
		Map<String, List<Advertisement>> advertisementMap = new HashMap<String, List<Advertisement>>();
		advertisementMap.put("advertises", advertises);
		return advertisementMap;
	}

	@Override
	public Advertisement readSpecificUserAdvertisementSer(String postId, String authToken) {
		boolean isvalidUser = loginServiceDelegate.isLoggedInUser(authToken);
		User user = loginServiceDelegate.getUserDetails(authToken);
		if(!isvalidUser)
			throw new InvalidAuthorizationTokenException(authToken);
		AdvertisementEntity existingAdvertisement = advertiseRepo.getById(Long.valueOf(postId));
		if(existingAdvertisement == null)
			throw new RuntimeException("Advertisement with given Id doesn't exist to update: "+ postId);
		Advertisement advertisement = new Advertisement(existingAdvertisement.getId(), existingAdvertisement.getTitle(), existingAdvertisement.getDescription(), existingAdvertisement.getPrice(), existingAdvertisement.getCategory(), existingAdvertisement.getCreatedDate(), existingAdvertisement.getModifiedDate(), existingAdvertisement.getStatus(), existingAdvertisement.getUsername(), user.getFirstName() + " " + user.getLastName());
		return advertisement;
	}

	@Override
	public Boolean deleteSpecificUserAdvertisementSer(String postId, String authToken) {
		boolean isvalidUser = loginServiceDelegate.isLoggedInUser(authToken);
		User user = loginServiceDelegate.getUserDetails(authToken);
		if(!isvalidUser)
			throw new InvalidAuthorizationTokenException(authToken);
		AdvertisementEntity existingAdvertisement = advertiseRepo.getById(Long.valueOf(postId));
		if(existingAdvertisement == null)
			throw new RuntimeException("Advertisement with given Id doesn't exist to update: "+ postId);
		advertiseRepo.deleteById(Long.valueOf(postId));
		return true;
	}

	@Override
	public Map<String, List<Advertisement>> filterAdvertisementSer(String searchText, String category, String postedBy,
			String dateCondition, String onDate, String fromDate, String toDate, String sortBy, String startIndex,
			String records) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AdvertisementEntity> query = builder.createQuery(AdvertisementEntity.class);
			Root<AdvertisementEntity> root = query.from(AdvertisementEntity.class);
	
			Predicate searchTextTitle = builder.like(root.get("title"), "%" + searchText + "%");
			Predicate searchTextDesc = builder.lessThan(root.get("description"), "%" + searchText + "%");
			Predicate categoryP = builder.equal(root.get("category"), category);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Predicate onDateP = builder.equal(root.get("createdDate"), format.parse(onDate));
			Predicate dateRange = builder.between(root.get("createdDate"), format.parse(fromDate) , format.parse(toDate));
			Order order = builder.asc(root.get("sortBy"));
			query.where(builder.and(builder.or(searchTextTitle, searchTextDesc),categoryP, builder.or(onDateP, dateRange)));
			query.orderBy(order);
			List<AdvertisementEntity> advertiseEntities = em.createQuery(query).setFirstResult(Integer.valueOf(startIndex)).setMaxResults(Integer.valueOf(records)).getResultList();
			List<Advertisement> advertises = new ArrayList<Advertisement>();
			advertiseEntities.stream().forEach((AdvertisementEntity)-> 
				advertises.add(new Advertisement(AdvertisementEntity.getId(), AdvertisementEntity.getTitle(), AdvertisementEntity.getDescription(), AdvertisementEntity.getPrice(), AdvertisementEntity.getCategory(), AdvertisementEntity.getCreatedDate(), AdvertisementEntity.getModifiedDate(), AdvertisementEntity.getStatus(), AdvertisementEntity.getUsername(), " "))
			);
			Map<String, List<Advertisement>> advertisementMap = new HashMap<String, List<Advertisement>>();
			advertisementMap.put("advertises", advertises);
			return advertisementMap;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, List<Advertisement>> searchAdvertisementSer(String searchText) {
		List<AdvertisementEntity> advertiseEntities = advertiseRepo.find(searchText);
		List<Advertisement> advertises = new ArrayList<Advertisement>();
		advertiseEntities.stream().forEach((AdvertisementEntity)-> 
			advertises.add(new Advertisement(AdvertisementEntity.getId(), AdvertisementEntity.getTitle(), AdvertisementEntity.getDescription(), AdvertisementEntity.getPrice(), AdvertisementEntity.getCategory(), AdvertisementEntity.getCreatedDate(), AdvertisementEntity.getModifiedDate(), AdvertisementEntity.getStatus(), AdvertisementEntity.getUsername(), ""))
		);
		Map<String, List<Advertisement>> advertisementMap = new HashMap<String, List<Advertisement>>();
		advertisementMap.put("advertises", advertises);
		return advertisementMap;
	}

	@Override
	public Advertisement getAdvertisementDetailsSer(String postId, String authToken) {
		boolean isvalidUser = loginServiceDelegate.isLoggedInUser(authToken);
		User user = loginServiceDelegate.getUserDetails(authToken);
		if(!isvalidUser)
			throw new InvalidAuthorizationTokenException(authToken);
		AdvertisementEntity existingAdvertisement = advertiseRepo.getById(Long.valueOf(postId));
		if(existingAdvertisement == null)
			throw new RuntimeException("Advertisement with given Id doesn't exist to update: "+ postId);
		Advertisement advertisement = new Advertisement(existingAdvertisement.getId(), existingAdvertisement.getTitle(), existingAdvertisement.getDescription(), existingAdvertisement.getPrice(), existingAdvertisement.getCategory(), existingAdvertisement.getCreatedDate(), existingAdvertisement.getModifiedDate(), existingAdvertisement.getStatus(), existingAdvertisement.getUsername(), user.getFirstName() + " " + user.getLastName());
		return advertisement;
	}

	

}
