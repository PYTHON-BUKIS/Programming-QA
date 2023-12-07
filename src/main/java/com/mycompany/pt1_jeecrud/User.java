package com.mycompany.pt1_jeecrud;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
@ManagedBean(name = "myuser", eager = true)

public class User {
    String userName;
    String password;
    String firstName;
    String lastName;
    String contactNumber;
    String email;
    int id;
    int job_id;

    ArrayList userData;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  

    //Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        int result = stmt.executeUpdate("delete from `tbl_user`  where user_id="  +(id));
        return "viewUsers.xhtml";
      
    }
    
    public String update(User u) 
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
        stmt.executeUpdate("update `tbl_user` set username=\"" + u.getUserName() + "\", password=\"" + u.getPassword() + "\", firstname=\"" + u.getFirstName()+ "\",lastname=\""+ u.getLastName()+"\",email=\""+ u.getEmail()+"\",contact_number=\""+ u.getContactNumber()+"\" where user_id=" + u.getId());
        return "viewUsers.xhtml";
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
        ResultSet rs = stmt.executeQuery("select * from tbl_user where user_id=" +(id));
        
        if(rs.next())
        {
            User temp = new User();
            temp.id = rs.getInt("user_id");
            temp.userName = rs.getString("username");
            temp.firstName = rs.getString("firstname");
            temp.lastName = rs.getString("lastname");
            temp.email = rs.getString("email");
            temp.contactNumber = rs.getString("contact_number");
            sessionMap.put("editUser", temp);
        }
        // JDBC
        // then, it will load the record into the edit.xhtml
        
        return "editUSer.xhtml";
    }
    
    public ArrayList getAll() 
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        userData = new ArrayList();
        
        // JDBC
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        
        Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                         "root",
                         "");

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from tbl_user");
        
        while(rs.next())
        {
            User temp = new User();
            temp.id = rs.getInt("user_id");
            temp.job_id = rs.getInt("job_id");
            temp.userName = rs.getString("username");
            temp.firstName = rs.getString("firstname");
            temp.lastName = rs.getString("lastname");
            temp.email = rs.getString("email");
            temp.contactNumber = rs.getString("contact_number");
            userData.add(temp);
        }
        // JDBC
        return userData;
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
        String query = "INSERT INTO `tbl_user`(`username`,`password`,`firstname`, `lastname`, `email`, `contact_number`) VALUES " +
                       "('" + this.userName + "','" + this.password + "','" + this.firstName + "','" + this.lastName + "','" + this.email + "','" + this.contactNumber + "')";
        
        int result = stmt.executeUpdate(query);
        
        if (result == 1)
            return true;
        else 
            return false;
        
    }        
    
    public String submit() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
       if (save()) return "viewUsers.xhtml";
       else  return "registerUser.xhtml";
    }
}
