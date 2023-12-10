/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pt1_jeecrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author sonik
 */
@ManagedBean(name = "TransactionTypes", eager = true)
public class TransactionType {
    int transactionTypeId;
    String name;

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList GetAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ArrayList<TransactionType> jobData = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_transactiontype;");

            while(rs.next())
            {
                TransactionType temp = new TransactionType();

                temp.setTransactionTypeId(rs.getInt("transaction_type_id"));
                temp.setName(rs.getString("name"));

                jobData.add(temp);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            System.out.println(e.getClass().getSimpleName());
        }

        return jobData;
    }
}
