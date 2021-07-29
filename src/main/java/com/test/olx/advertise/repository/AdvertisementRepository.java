package com.test.olx.advertise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.olx.advertise.entity.AdvertisementEntity;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long>{
	
	public List<AdvertisementEntity> findByUsername(String username);
	
	@Query("SELECT a from AdvertisementEntity a WHERE title LIKE %:searchText% OR description LIKE %:searchText%")
    public List<AdvertisementEntity> find(@Param("searchText") String searchText);

}
