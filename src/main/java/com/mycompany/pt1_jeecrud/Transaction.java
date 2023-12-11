package com.mycompany.pt1_jeecrud;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
@ManagedBean(name = "Transactions", eager = true)

public class Transaction {
    int transactionId;
    int productId;
    int userId;
    int transactionTypeId;
    java.util.Date date;
    
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName(int productId) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                "root",
                "");

            PreparedStatement statement = con.prepareStatement("SELECT name FROM tbl_product WHERE product_id  = ? LIMIT 1");
            statement.setInt(1, productId);
            ResultSet results = statement.executeQuery();
            
            if (results.next()) return results.getString("name");
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Error";
        }
         
         return "NULL";
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName(int customerId) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                "root",
                "");

            PreparedStatement statement = con.prepareStatement("SELECT firstname, lastname FROM tbl_user WHERE user_id = ? LIMIT 1");
            statement.setInt(1, customerId);
            ResultSet results = statement.executeQuery();
            
            if (results.next()) return results.getString("firstname") + " " + results.getString("lastname");
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Error";
        }
         
         return "NULL";
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTransactionTypeString(int transactionTypeId) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                "root",
                "");

            PreparedStatement statement = con.prepareStatement("SELECT name FROM tbl_transactiontype WHERE transaction_type_id = ? LIMIT 1");
            statement.setInt(1, transactionTypeId);
            ResultSet results = statement.executeQuery();
            
            if (results.next()) return results.getString("name");
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Error";
        }
         
         return "NULL";
    }
    
    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }
    
    public String Delete(int transactionId) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("DELETE FROM tbl_transaction WHERE transaction_id = ?");
            statement.setInt(1, transactionId);
            statement.executeUpdate();

            return "viewTransactions.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Helper/error.xhtml";
        }
    }

    public String Update(Transaction t) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("UPDATE tbl_transaction SET product_id = ?, user_id = ?, transaction_type_id = ?, date = ? WHERE transaction_id = ?");
            statement.setInt(1, t.getProductId());
            statement.setInt(2, t.getUserId());
            statement.setInt(3, t.getTransactionTypeId());
            statement.setDate(4, new Date(t.getDate().getTime()));
            statement.setInt(5, t.getTransactionId());
            statement.executeUpdate();

            return "viewTransactions.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            System.out.println(e.getClass().getSimpleName());
            return "Helper/error.xhtml";
        }
    }

    public String BeginEditProcess(int id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("SELECT transaction_id, product_id, user_id, transaction_type_id, date FROM tbl_transaction WHERE transaction_id = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Transaction temp = new Transaction();
                temp.setTransactionId(result.getInt("transaction_id"));
                temp.setProductId(result.getInt("product_id"));
                temp.setUserId(result.getInt("user_id"));
                temp.setTransactionTypeId(result.getInt("transaction_type_id"));
                temp.setDate(result.getDate("date"));
                sessionMap.put("transactionToEdit", temp);
            }

            return "editTransaction.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            System.out.println(e.getClass().getSimpleName());
            return "Helper/error.xhtml";
        }
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

    public String Insert(Transaction t) throws     SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("INSERT INTO tbl_transaction (product_id, user_id, transaction_type_id, date) VALUES (?, ?, ?, ?)");
            statement.setInt(1, t.getProductId());
            statement.setInt(2, t.getUserId());
            statement.setInt(3, t.getTransactionTypeId());
            statement.setDate(4, new Date(t.getDate().getTime()));
            statement.executeUpdate();

            return "viewTransactions.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Helper/error.xhtml";
        }
    }
}
