package com.mycompany.pt1_jeecrud;

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
