package com.mycompany.pt1_jeecrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sonik
 */
@ManagedBean(name = "home", eager = true)
public class Home {
    public String Register() {
        return "register.xhtml";
    }
    
    public String View() {
        return "view.xhtml";
    }
}
