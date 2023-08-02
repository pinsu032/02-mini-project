package com.ms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms.entity.EligibilityDetails;

public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails,Integer>{

	@Query("SELECT DISTINCT(planName) from EligibilityDetails")
	List<String> getPlans();
	
	@Query("SELECT DISTINCT(planStatus) from EligibilityDetails")
	List<String> getPlanStatus();
}
