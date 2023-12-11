package com.mycompany.pt1_jeecrud;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
@ManagedBean(name = "Users", eager = true)

public class User {
    String username;
    String password;
    String firstName;
    String lastName;
    String contactNumber;
    String email;
    int userId;
    int jobId;
    
    Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getJobName(int jobId) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                "root",
                "");

            PreparedStatement statement = con.prepareStatement("SELECT name FROM tbl_jobType WHERE job_id = ? LIMIT 1");
            statement.setInt(1, jobId);
            ResultSet results = statement.executeQuery();
            
            if (results.next()) return results.getString("name");
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Error";
        }
         
         return "NULL";
    }

    public int getJobId() {
        return jobId;
    }
    
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String Delete(int id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                "root",
                "");

            PreparedStatement statement = con.prepareStatement("DELETE FROM tbl_user WHERE user_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();

            return "viewUsers.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Helper/error.xhtml";
        } 
    }
    
    public String Update(User u) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                "root",
                "");

            PreparedStatement statement = con.prepareStatement("UPDATE tbl_user SET job_id = ?, username = ?, password = ?, firstname = ?, lastname = ?, email = ?, contact_number = ? WHERE user_id = ?");
            statement.setInt(1, u.jobId);
            statement.setString(2, u.getUsername());
            
            String hashedPassword = u.getPassword();
            hashedPassword = BCrypt.hashpw(hashedPassword, BCrypt.gensalt());
            statement.setString(3, hashedPassword);
            
            statement.setString(4, u.getFirstName());
            statement.setString(5, u.getLastName());
            statement.setString(6, u.getEmail());
            statement.setString(7, u.getContactNumber());
            statement.setInt(8, (u.getUserId()));
            statement.executeUpdate();

            return "viewUsers.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
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

            PreparedStatement statement = con.prepareStatement("SELECT user_id, firstname, lastname, username, password, email, contact_number, job_id FROM tbl_user WHERE user_id = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            
            if (result.next()) {
                User temp = new User();
                temp.setUserId(result.getInt("user_id"));
                temp.setUsername(result.getString("username"));
                temp.setPassword(result.getString("password"));
                temp.setFirstName(result.getString("firstname"));
                temp.setLastName(result.getString("lastname"));
                temp.setEmail(result.getString("email"));
                temp.setContactNumber(result.getString("contact_number"));
                temp.setJobId(result.getInt("job_id"));
                sessionMap.put("userToEdit", temp);
            }
            
            return "editUser.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            System.out.println(e.getClass().getSimpleName());
            return "Helper/error.xhtml";
        }
    }
    
    public ArrayList GetAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ArrayList<User> userData = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT u.user_id, u.firstname, u.lastname, u.username, u.password, u.email, u.contact_number, u.job_id FROM tbl_user u;");

            while(rs.next())
            {
                User temp = new User();

                temp.setUserId(rs.getInt("user_id"));
                temp.setFirstName(rs.getString("firstname"));
                temp.setLastName(rs.getString("lastname"));
                temp.setUsername(rs.getString("username"));
                temp.setPassword(rs.getString("password"));
                temp.setEmail(rs.getString("email"));
                temp.setContactNumber(rs.getString("contact_number"));
                temp.setJobId(rs.getInt("job_id"));

                userData.add(temp);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
        }

        return userData;
    }

    public String Insert(User u) throws     SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("INSERT INTO tbl_user (job_id, username, password, firstname, lastname, email, contact_number) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, u.jobId);
            statement.setString(2, u.getUsername());
            
            String hashedPassword = u.getPassword();
            hashedPassword = BCrypt.hashpw(hashedPassword, BCrypt.gensalt());
            statement.setString(3, hashedPassword);
            
            statement.setString(4, u.getFirstName());
            statement.setString(5, u.getLastName());
            statement.setString(6, u.getEmail());
            statement.setString(7, u.getContactNumber());
            statement.executeUpdate();

            return "viewUsers.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Helper/error.xhtml";
        }
    }
    
    public String Login(String user, String pass) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement stmt = con.prepareStatement("SELECT u.user_id, u.firstname, u.lastname, u.username, u.password, u.email, u.contact_number, u.job_id FROM tbl_user u WHERE u.username = ?;");
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User temp = new User();
                temp.setUserId(rs.getInt("user_id"));
                temp.setFirstName(rs.getString("firstname"));
                temp.setLastName(rs.getString("lastname"));
                temp.setUsername(rs.getString("username"));
                temp.setPassword(rs.getString("password"));
                temp.setEmail(rs.getString("email"));
                temp.setContactNumber(rs.getString("contact_number"));
                temp.setJobId(rs.getInt("job_id"));
                
                
                // if hash is checked
                if (BCrypt.checkpw(pass, temp.getPassword())) {
                    sessionMap.put("loggedUser", temp);
                
                    switch(temp.getJobId()) {
                        case 1:
                            return "viewProducts.xhtml";

                        case 2:
                            return "supervisorHome.xhtml";

                        case 3:
                            return "adminHome.html";

                        case 4: 
                            return "customerHome.html";

                        default:
                            return "Helper/error.xhtml";
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
             System.out.println(e.getClass().getSimpleName());
            return "login.xhtml";
        }
         
        return "login.xhtml";
    }
    
    public String Logout() {
        sessionMap.remove("loggedUser");
        return "login.xhtml";
    }
}
