package Model;

import Database.CRUD;
import Database.ConfigDB;
import Entity.Client;
import Entity.Product;
import Entity.Store;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel implements CRUD {
    @Override
    public Object create(Object object) {
        Connection connection = ConfigDB.openConnection();
        Product product = (Product) object;
        try {
            String sql = "INSERT INTO Product (name,price,stock,id_store) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setDouble(2,product.getPrice());
            preparedStatement.setInt(3,product.getStock());
            preparedStatement.setInt(4,product.getStore().getId());

            preparedStatement.execute();

            ResultSet resultSet = (ResultSet) preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                product.setId(resultSet.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Product added Successfully!" + product.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return product;
    }

    @Override
    public List<Object> read() {
        Connection connection = ConfigDB.openConnection();
        List<Object> Product_list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Product JOIN Store ON Product.id_store = Store.id_store";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                Store store = new Store();
                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));

                product.setId(resultSet.getInt("id_product"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStore(store);
                Product_list.add(product);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Product_list;
    }

    @Override
    public boolean update(Object object) {
        Connection connection = ConfigDB.openConnection();
        Product product = (Product) object;
        try {
            String sql = "UPDATE Product SET name = '" + product.getName() + "' ,price = '" + product.getPrice() + "' ,stock = '" + product.getStock() +"' ,id_store = '" +product.getStore().getId() +"' WHERE id_product = " + product.getId();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.execute();

            ResultSet resultSet = (ResultSet) preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
             product.setId(resultSet.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Product updated successfully! " + product.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
            return false;
        }
        ConfigDB.closeConnection();

        return true;
    }

    @Override
    public void delete(int id) {
        Connection connection = ConfigDB.openConnection();
        try{
            String sql = "DELETE FROM Product WHERE Product.id_product =" + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                JOptionPane.showMessageDialog(null, "Product deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null,"Product not found...");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
    }

    @Override
    public Object findById(int id) {
        Connection connection = ConfigDB.openConnection();
        Product product = null;
        try{
            String sql = "SELECT * FROM Product JOIN Store ON Product.id_store = Store.id_store WHERE id_product = " + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while(resultSet.next()){
                product = new Product();

                Store store = new Store();
                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));

                product.setId(resultSet.getInt("id_product"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStore(store);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }

        ConfigDB.closeConnection();
        return product;
    }
    public List<Object> filterByName(String name) {
        Connection connection = ConfigDB.openConnection();
        List<Object> Product_list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Product JOIN Store ON Product.id_store = Store.id_store  WHERE Product.name LIKE '%' '"  + name + "' '%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();

                Store store = new Store();
                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));

                product.setId(resultSet.getInt("id_product"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStore(store);

                Product_list.add(product);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Product_list;
    }
    public List<Object> getStores(){
        Connection connection = ConfigDB.openConnection();
        List<Object> Stores_list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Store LEFT JOIN Product ON Store.id_store = Product.id_store";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()){
                Store store = new Store();
                store.setId(resultSet.getInt("id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("location"));
                Stores_list.add(store);
                if (resultSet.getInt("id_product")!= 0){
                    Product product = new Product();
                    product.setId(resultSet.getInt("id_product"));
                    product.setName(resultSet.getString("Product.name"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setStock(resultSet.getInt("stock"));
                    product.setStore(store);
                    store.addProducts(product);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Stores_list;
    }
    public Object findStoreById(int id) {
        Connection connection = ConfigDB.openConnection();
        Store store = null;
        try{
            String sql = "SELECT * FROM Store WHERE id_store = " + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while(resultSet.next()){
                store = new Store();
                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }

        ConfigDB.closeConnection();
        return store;
    }
    public List<Object> findProductsInStore(int id){
        Connection connection = ConfigDB.openConnection();
        List<Object> Products_list = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Product JOIN Store ON Store.id_store = Product.id_store WHERE Store.id_store = " + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while(resultSet.next()){
                Product product = new Product();

                Store store = new Store();
                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));

                product.setId(resultSet.getInt("id_product"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStore(store);
                store.setProducts(Products_list);
                store.addProducts(product);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Products_list;
    }
}
