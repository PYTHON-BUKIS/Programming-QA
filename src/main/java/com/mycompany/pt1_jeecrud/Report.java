/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pt1_jeecrud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author sonik
 */
@ManagedBean(name = "Reports", eager = true)
public class Report {
    final String FILEPATH = "/reports/";
    
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
    
    public void GenerateReport() {
        ArrayList<Transaction> transactionsBetweenDate = GetTransactionsBetweenDate(reportDateFrom, reportDateTo);
        
        String path = FILEPATH + "report-" + System.currentTimeMillis() + ".pdf";
        
        String xhtmlContent = "<html><body><h1>Hello, World!</h1></body></html>";
        
        File file = new File(path);
        file.mkdirs();
        
        try (OutputStream os = new FileOutputStream(path)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtmlContent);
            renderer.createPDF(os);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
        }
        
        
        // todo: make pdf file
        // todo: pdf save as
    }
}
