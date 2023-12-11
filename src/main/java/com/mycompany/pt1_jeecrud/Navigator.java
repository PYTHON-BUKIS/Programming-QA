/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pt1_jeecrud;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author sonik
 */
@ManagedBean(name = "Navigation", eager = true)
public class Navigator {
    public String ViewUsers(int jobId) {
        switch(jobId) {
            case 1:
                return "viewUsers.xhtml";

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
    
    public String ViewProducts(int jobId) {
        switch(jobId) {
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
    
    public String ViewTransactions(int jobId) {
        switch(jobId) {
            case 1:
                return "viewTransactions.xhtml";

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
