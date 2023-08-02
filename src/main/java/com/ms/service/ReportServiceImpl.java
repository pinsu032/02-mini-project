package com.ms.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ms.dto.SearchRequest;
import com.ms.dto.SearchResponse;
import com.ms.entity.EligibilityDetails;
import com.ms.repo.EligibilityDetailsRepo;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EligibilityDetailsRepo repo;

	@Override
	public List<String> getPlans() {

		return repo.getPlans();
	}

	@Override
	public List<String> getPlanStatus() {

		return repo.getPlanStatus();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		List<SearchResponse> response = new ArrayList<>();

		EligibilityDetails elgDetails = new EligibilityDetails();

		String planName = request.getPlanName();
		if (planName != null && !planName.equals(" ")) {
			elgDetails.setPlanName(planName);
		}

		String planStatus = request.getPlanStatus();
		if (planStatus != null && planStatus.equals(" ")) {
			elgDetails.setPlanName(planStatus);
		}

		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			elgDetails.setPlanStartDate(planStartDate);
		}

		LocalDate planEnDate = request.getPlanEndDate();
		if (planEnDate != null) {
			elgDetails.setPlanEndDate(planEnDate);
		}

		Example<EligibilityDetails> example = Example.of(elgDetails);

		List<EligibilityDetails> listDetails = repo.findAll(example);

		listDetails.forEach(entity -> {
			SearchResponse sr = new SearchResponse();

			BeanUtils.copyProperties(entity, sr);
			response.add(sr);
		});

		return response;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws IOException {
		List<EligibilityDetails> list = repo.findAll();
		list.forEach(System.out::println);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Report");
		
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("Phone");
		headerRow.createCell(4).setCellValue("SSN");

		
		int rowId = 1;
		for(EligibilityDetails entity : list) {
			
			HSSFRow row = sheet.createRow(rowId);
			row.createCell(0).setCellValue(entity.getName());
			row.createCell(1).setCellValue(entity.getEmail());
			row.createCell(2).setCellValue(String.valueOf(entity.getGender()));
			row.createCell(3).setCellValue(entity.getMobile());
			row.createCell(4).setCellValue(entity.getSsn());
			
			rowId++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}
	

	@Override
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
		List<EligibilityDetails> list = repo.findAll();
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.blue);
		
		Paragraph p = new Paragraph("Search Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		//creating table
		PdfPTable table = new  PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.5f,3.5f,3.0f,1.5f,1.5f});
		table.setSpacingBefore(10);
		
		PdfPCell cell = new  PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		
		cell.setPhrase(new Phrase("Name",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Email",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Gender",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Phone",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("SSN",font));
		table.addCell(cell);
		
		for(EligibilityDetails entity : list) {
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getMobile()));
			table.addCell(String.valueOf(entity.getSsn()));

		}
		
		document.add(table);
		
		document.close();
				
	}

	
}
