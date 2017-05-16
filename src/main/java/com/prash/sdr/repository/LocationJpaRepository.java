package com.prash.sdr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.prash.sdr.model.Location;

@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Long> {
	List<Location> findByStateIgnoreCaseStartingWith(@RequestParam(name="stateName") @Param("stateName") String stateName);
	Location findFirstByStateIgnoreCaseStartingWith(@RequestParam(name="stateName") @Param("stateName") String stateName);
	List<Location> findByStateNotLikeOrderByStateAsc(@RequestParam(name="stateName") @Param("stateName") String stateName);

	List<Location> findByStateIsOrCountryEquals(String value, String value2);
	List<Location> findByStateNot(@RequestParam(name="stateName") @Param("stateName") String state);
	

	@RestResource(exported = true)
	Location findOne(@RequestParam(name="Id") @Param("Id") Long paramID);

	@RestResource(exported = true)
	List<Location> findAll();





}
