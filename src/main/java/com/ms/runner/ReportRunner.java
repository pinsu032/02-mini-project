package com.ms.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ms.entity.EligibilityDetails;
import com.ms.repo.EligibilityDetailsRepo;

//@Component
public class ReportRunner implements CommandLineRunner {
	
	@Autowired
	private EligibilityDetailsRepo repo;

	@Override
	public void run(String... args) throws Exception {
		EligibilityDetails entity1 = new EligibilityDetails();
		entity1.setEligId(101);
		entity1.setName("Ujjawal");
		entity1.setEmail("pinsusingh@gmail.com");
		entity1.setGender('M');
		entity1.setSsn(12345676l);
		entity1.setPlanName("Health");
		entity1.setPlanStatus("Approved");
		entity1.setMobile(1234567l);
		repo.save(entity1);
		
		EligibilityDetails entity2 = new EligibilityDetails();
		entity2.setEligId(102);
		entity2.setName("Pinsu");
		entity2.setEmail("pinsusingh@gmail.com");
		entity2.setGender('M');
		entity2.setSsn(8888888l);
		entity2.setPlanName("Bike Insurance");
		entity2.setPlanStatus("Denied");
		entity2.setMobile(2731278l);
		repo.save(entity2);
		
		EligibilityDetails entity3 = new EligibilityDetails();
		entity3.setEligId(103);
		entity3.setName("Pooja_Cutiee");
		entity3.setEmail("pooja@gmail.com");
		entity3.setGender('F');
		entity3.setSsn(826178288l);
		entity3.setPlanName("MakeUp");
		entity3.setPlanStatus("Approved");
		entity3.setMobile(2328172l);
		repo.save(entity3);
		
	}

}
