package com.ms.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.dto.SearchRequest;
import com.ms.dto.SearchResponse;
import com.ms.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService service;
	
	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlans(){
		List<String> plans = service.getPlans();
		return  new ResponseEntity<>(plans,HttpStatus.OK);
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<String>> getStatus(){
		List<String> status = service.getPlanStatus();
		return  new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> getRecords(
			@RequestBody SearchRequest request){
		List<SearchResponse> response = service.search(request);
		return new ResponseEntity<List<SearchResponse>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + LocalDate.now() + ".xls";
        response.setHeader(headerKey, headerValue);
        
        service.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void generatePdf(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + LocalDate.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        
        service.generatePdf(response);
	}
	
	
}
