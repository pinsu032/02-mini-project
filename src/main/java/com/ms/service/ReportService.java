package com.ms.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ms.dto.SearchRequest;
import com.ms.dto.SearchResponse;
import com.ms.entity.EligibilityDetails;

public interface ReportService{

     List<String> getPlans();
     
     List<String> getPlanStatus();
     
     List<SearchResponse> search(SearchRequest request);
     
     void generateExcel(HttpServletResponse response)throws Exception;
     
     void generatePdf(HttpServletResponse response)throws Exception;
}
