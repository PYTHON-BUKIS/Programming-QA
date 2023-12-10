package com.mycompany.pt1_jeecrud;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
@ManagedBean(name = "mysales", eager = true)

public class Sales {
    int transaction_ID;
    String product_name;
    String model_name;
    String customerName;
    String transaction_type;
    String date;
    
    ArrayList salesData;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  

    //Getters and Setters

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }
    
    public String getProduct_ID() {
        return product_name;
    }

    public void setProduct_ID(String product_ID) {
        this.product_name = product_ID;
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

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transactionType) {
        this.transaction_type = transactionType;
    }

    public String getCustomer_name() {
        return customerName;
    }

    public void setCustomer_name(String customerName) {
        this.customerName = customerName;
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
        //stmt.executeUpdate("update `tbl_transaction` set product_ID=\""+ u.getProduct_ID()+"\", date=\""+ u.getDate()+"\", user_id=\""+ u.getUser_id()+"\", transaction_type_id=\""+ u.getTransaction_type_id()+"\" where transaction_ID=" + u.getTransaction_ID());
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
            temp.customerName = rs.getString("firstname") + " " + rs.getString("lastname");
            temp.transaction_type = rs.getString("transaction_type");
            temp.product_name = rs.getString("name");
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
        ResultSet rs = stmt.executeQuery("SELECT t.transaction_ID, p.name, p.model_name, u.firstname, u.lastname, ty.name as transaction_type, t.date\n" +
            "FROM tbl_transaction t\n" +
            "INNER JOIN tbl_product p ON p.product_ID = t.product_ID\n" +
            "INNER JOIN tbl_user u ON u.user_id = t.user_id\n" +
            "INNER JOIN tbl_transactiontype ty ON ty.transaction_type_id = t.transaction_type_id;");
        
        while(rs.next())
        {
            Sales temp = new Sales();
            temp.transaction_ID = rs.getInt("transaction_ID");
            temp.date = rs.getString("date");
            temp.customerName = rs.getString("firstname") + " " + rs.getString("lastname");
            temp.transaction_type = rs.getString("transaction_type");
            temp.product_name = rs.getString("name");
            temp.model_name = rs.getString("model_name");
            salesData.add(temp);
            
            System.out.println(temp.transaction_ID);
            System.out.println(temp.date);
            System.out.println(temp.customerName);
            System.out.println(temp.transaction_type);
            System.out.println(temp.product_name);
            System.out.println(temp.model_name);
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
                       "('" + this.product_name + "','" + this.customerName + "','" + this.transaction_type + "','" + this.date + "')";
        
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
