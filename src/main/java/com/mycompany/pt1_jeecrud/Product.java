package com.mycompany.pt1_jeecrud;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
@ManagedBean(name = "Products", eager = true)
@SessionScoped
public class Product {
    int productId;
    String name;
    String modelName;
    float price;
    String description;
  
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String Delete(int id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("DELETE FROM tbl_product WHERE product_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();

            return "viewProducts.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Helper/error.xhtml";
        }
    }

    public String Update(Product p) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("UPDATE tbl_product SET name = ?, price = ?, description = ?, model_name = ? WHERE product_id = ?");
            statement.setString(1, p.getName());
            statement.setFloat(2, p.getPrice());
            statement.setString(3, p.getDescription());
            statement.setString(4, p.getModelName());
            statement.setInt(5, p.getProductId());
            statement.executeUpdate();

            return "viewProducts.xhtml";
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

            PreparedStatement statement = con.prepareStatement("SELECT product_id, name, model_name, price, description FROM tbl_product WHERE product_id = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Product temp = new Product();
                temp.setProductId(result.getInt("product_id"));
                temp.setName(result.getString("name"));
                temp.setModelName(result.getString("model_name"));
                temp.setDescription(result.getString("description"));
                temp.setPrice(result.getFloat("price"));
                sessionMap.put("productToEdit", temp);
            }

            return "editProduct.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            System.out.println(e.getClass().getSimpleName());
            return "Helper/error.xhtml";
        }
    }

    public ArrayList GetAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ArrayList<Product> productData = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery("SELECT product_id, name, model_name, price, description FROM tbl_product;");

            while(results.next())
            {
                Product temp = new Product();

                temp.setProductId(results.getInt("product_id"));
                temp.setName(results.getString("name"));
                temp.setModelName(results.getString("model_name"));
                temp.setDescription(results.getString("description"));
                temp.setPrice(results.getFloat("price"));

                productData.add(temp);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
        }

        return productData;
    }

    public String Insert(Product p) throws     SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/program_db?zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root",
                    "");

            PreparedStatement statement = con.prepareStatement("INSERT INTO tbl_product (name, price, description, model_name) VALUES (?, ?, ?, ?)");
            statement.setString(1, p.getName());
            statement.setFloat(2, p.getPrice());
            statement.setString(3, p.getDescription());
            statement.setString(4, p.getModelName());
            statement.executeUpdate();

            return "viewProducts.xhtml";
        } catch (SQLException | ClassNotFoundException | InstantiationError | IllegalAccessError e) {
            e.printStackTrace();
            return "Helper/error.xhtml";
        }
    }
}
