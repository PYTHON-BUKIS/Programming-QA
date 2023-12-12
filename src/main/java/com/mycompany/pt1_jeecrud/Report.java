/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pt1_jeecrud;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author sonik
 */
@ManagedBean(name = "Reports", eager = true)
public class Report {
    java.util.Date reportDateFrom;
    java.util.Date reportDateTo;

    public java.util.Date getReportDateFrom() {
        return reportDateFrom;
    }

    public void setReportDateFrom(java.util.Date reportDateFrom) {
        this.reportDateFrom = reportDateFrom;
    }

    public java.util.Date getReportDateTo() {
        return reportDateTo;
    }

    public void setReportDateTo(java.util.Date reportDateTo) {
        this.reportDateTo = reportDateTo;
    }
    
    public ArrayList GetTransactionsBetweenDate(java.util.Date from, java.util.Date to) {
        ArrayList<Transaction> transactionData = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement stmt = con.prepareStatement("SELECT transaction_id, product_id, user_id, transaction_type_id, date FROM tbl_transaction WHERE date BETWEEN ? and ?;");
            stmt.setDate(1, new Date(from.getTime()));
            stmt.setDate(2, new Date(to.getTime()));
            ResultSet results = stmt.executeQuery();

            while(results.next())
            {
                Transaction temp = new Transaction();

                temp.setTransactionId(results.getInt("transaction_id"));
                temp.setProductId(results.getInt("product_id"));
                temp.setUserId(results.getInt("user_id"));
                temp.setTransactionTypeId(results.getInt("transaction_type_id"));
                temp.setDate(results.getDate("date"));

                transactionData.add(temp);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
        }

        return transactionData;
    }
    
     public ArrayList GetAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ArrayList<Transaction> transactionData = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery("SELECT transaction_id, product_id, user_id, transaction_type_id, date FROM tbl_transaction;");

            while(results.next())
            {
                Transaction temp = new Transaction();

                temp.setTransactionId(results.getInt("transaction_id"));
                temp.setProductId(results.getInt("product_id"));
                temp.setUserId(results.getInt("user_id"));
                temp.setTransactionTypeId(results.getInt("transaction_type_id"));
                temp.setDate(results.getDate("date"));

                transactionData.add(temp);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
        }

        return transactionData;
    }
    
     public void GenerateReportBetween(java.util.Date from, java.util.Date to) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        // Set content type and headers for PDF download
        externalContext.responseReset();
        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"TRANSACTION_REPORT.pdf\"");

        try (OutputStream outputStream = externalContext.getResponseOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc);

            doc.add(new Paragraph("DATED TRANSACTION REPORT | " + LocalDate.now()));
            doc.add(new Paragraph("From " + from.toString() + " to " + to.toString()));

            Table transactionsTable = new Table(UnitValue.createPercentArray(new float[] {10, 10, 20, 10, 30, 10, 10}));
            transactionsTable.setMarginTop(5);
            transactionsTable.addCell("ID");
            transactionsTable.addCell("Product ID");
            transactionsTable.addCell("Product Name");
            transactionsTable.addCell("Customer ID");
            transactionsTable.addCell("Customer Name");
            transactionsTable.addCell("Transaction Type");
            transactionsTable.addCell("Date");

            ArrayList<Transaction> transactionsBetweenDate = GetTransactionsBetweenDate(from, to);
            for(Transaction t : transactionsBetweenDate) {
                transactionsTable.addCell(String.valueOf(t.getTransactionId()));
                transactionsTable.addCell(String.valueOf(t.getProductId()));
                transactionsTable.addCell(t.getProductName(t.productId));
                transactionsTable.addCell(String.valueOf(t.getUserId()));
                transactionsTable.addCell(t.getCustomerName(t.userId));
                transactionsTable.addCell(t.getTransactionTypeString(t.transactionTypeId));
                transactionsTable.addCell(t.getDate().toString());
            }
            
            doc.add(transactionsTable);

            doc.close();
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        facesContext.responseComplete();
    }
     
    public void GenerateReportAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        // Set content type and headers for PDF download
        externalContext.responseReset();
        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"TRANSACTION_REPORT.pdf\"");

        try (OutputStream outputStream = externalContext.getResponseOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc);

            doc.add(new Paragraph("TRANSACTION REPORT | " + LocalDate.now()));

            Table transactionsTable = new Table(UnitValue.createPercentArray(new float[] {10, 10, 20, 10, 30, 10, 10}));
            transactionsTable.setMarginTop(5);
            transactionsTable.addCell("ID");
            transactionsTable.addCell("Product ID");
            transactionsTable.addCell("Product Name");
            transactionsTable.addCell("Customer ID");
            transactionsTable.addCell("Customer Name");
            transactionsTable.addCell("Transaction Type");
            transactionsTable.addCell("Date");

            ArrayList<Transaction> transactionsBetweenDate = GetAll();
            for(Transaction t : transactionsBetweenDate) {
                transactionsTable.addCell(String.valueOf(t.getTransactionId()));
                transactionsTable.addCell(String.valueOf(t.getProductId()));
                transactionsTable.addCell(t.getProductName(t.productId));
                transactionsTable.addCell(String.valueOf(t.getUserId()));
                transactionsTable.addCell(t.getCustomerName(t.userId));
                transactionsTable.addCell(t.getTransactionTypeString(t.transactionTypeId));
                transactionsTable.addCell(t.getDate().toString());
            }
            
            doc.add(transactionsTable);

            doc.close();
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        facesContext.responseComplete();
    }
}