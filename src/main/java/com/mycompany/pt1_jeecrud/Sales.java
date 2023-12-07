package com.mycompany.pt1_jeecrud;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
@ManagedBean(name = "mysales", eager = true)

public class Sales {
    int transaction_ID;
    int product_ID;
    int user_id;
    int transaction_type_id;
    String date;
    
    ArrayList salesData;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  

    //Getters and Setters

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }
    
    public int getTransaction_ID() {
        return transaction_ID;
    }

    public void setTransaction_ID(int transaction_ID) {
        this.transaction_ID = transaction_ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    public String delete(int id) 
             throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
      // we will execte an update sql to table user   
      // sending the new values passed to u from the sessionmap  
      
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        
        Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                         "root",
                         "");

        Statement stmt = con.createStatement();
        int result = stmt.executeUpdate("delete from `tbl_transaction` where transaction_ID="  +(id));
        return "viewSales.xhtml";
      
    }
    
    public String update(Sales u) 
             throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
      // we will execte an update sql to table user   
      // sending the new values passed to u from the sessionmap  
      
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        
        Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                         "root",
                         "");

        Statement stmt = con.createStatement();
        stmt.executeUpdate("update `tbl_transaction` set product_ID=\""+ u.getProduct_ID()+"\", date=\""+ u.getDate()+"\", user_id=\""+ u.getUser_id()+"\", transaction_type_id=\""+ u.getTransaction_type_id()+"\" where transaction_ID=" + u.getTransaction_ID());
        return "viewSales.xhtml";
    }
    
    public String edit(int id)
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        // edit will retrive the record from the mysql table
        
         // JDBC
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        
        Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                         "root",
                         "");

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from tbl_transaction where transaction_ID=" +(id));
        
        if(rs.next())
        {
            Sales temp = new Sales();
            temp.transaction_ID = rs.getInt("transaction_ID");
            temp.date = rs.getString("date");
            temp.user_id = rs.getInt("user_id");
            temp.transaction_type_id = rs.getInt("transaction_type_id");
            temp.product_ID = rs.getInt("product_ID");
            sessionMap.put("editSales", temp);
        }
        // JDBC
        // then, it will load the record into the edit.xhtml
        
        return "editSale.xhtml";
    }
    
    public ArrayList getAll() 
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        salesData = new ArrayList();
        
        // JDBC
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        
        Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                         "root",
                         "");

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from tbl_transaction");
        
        while(rs.next())
        {
            Sales temp = new Sales();
            temp.transaction_ID = rs.getInt("transaction_ID");
            temp.product_ID = rs.getInt("product_ID");
            temp.user_id = rs.getInt("user_id");
            temp.transaction_type_id = rs.getInt("transaction_type_id");
            temp.date = rs.getString("date");
            salesData.add(temp);
        }
        // JDBC
        return salesData;
    }
    
    public boolean save() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        // JDBC statements here
        // insert into tbl_user (`un`,`em`) values ('john doe', 'jdoe@gmail.com');
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        
        Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                         "root",
                         "");

        Statement stmt = con.createStatement();
        String query = "INSERT INTO `tbl_transaction`(`product_ID`, `user_id`, `transaction_type_id`, `date`) VALUES " +
                       "('" + this.product_ID + "','" + this.user_id + "','" + this.transaction_type_id + "','" + this.date + "')";
        
        int result = stmt.executeUpdate(query);
        
        if (result == 1)
            return true;
        else 
            return false;
        
    }        
    
    public String submit() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
       if (save()) return "viewSales.xhtml";
       else  return "addSale.xhtml";
    }
}
