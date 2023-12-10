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
@ManagedBean(name = "Jobs", eager = true)
public class JobType {
    int job_id;
    String name;
    String description;

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public ArrayList GetAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ArrayList<JobType> jobData = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_jobType;");

            while(rs.next())
            {
                JobType temp = new JobType();

                temp.setJob_id(rs.getInt("job_id"));
                temp.setName(rs.getString("name"));
                temp.setDescription(rs.getString("description"));

                jobData.add(temp);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            System.out.println(e.getClass().getSimpleName());
        }

        return jobData;
    }
}
