package org.example.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.example.model.Invoice;

@Service
public class ExcelExportService {

    public ByteArrayInputStream exportInvoicesToExcel(List<Invoice> invoices) throws IOException {
        String[] columns = {"ID", "Amount", "Description", "Customer Name"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Invoices");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (Invoice invoice : invoices) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(invoice.getId());
                row.createCell(1).setCellValue(invoice.getAmount());
                row.createCell(2).setCellValue(invoice.getDescription());
                row.createCell(3).setCellValue(invoice.getCustomerName());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
