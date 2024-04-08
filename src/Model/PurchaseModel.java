package Model;

import Database.CRUD;
import Database.ConfigDB;
import Entity.Client;
import Entity.Product;
import Entity.Purchase;
import Entity.Store;
import Helpers.ClientMenu;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseModel implements CRUD {
    @Override
    public Object create(Object object) {
        Connection connection = ConfigDB.openConnection();
        Purchase purchase = (Purchase) object;
        try {
            String sql = "INSERT INTO Purchase (purchase_date,amount,id_client,id_product) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, Date.valueOf(purchase.getSell_date()));
            preparedStatement.setInt(2,purchase.getAmount());
            preparedStatement.setInt(3,purchase.getClient().getId());
            preparedStatement.setInt(4,purchase.getProduct().getId());
            preparedStatement.execute();

            ResultSet resultSet = (ResultSet) preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
             purchase.setId(resultSet.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Purchase added successfully! "+ purchase.toString() );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return purchase;
    }

    @Override
    public List<Object> read() {
        Connection connection = ConfigDB.openConnection();
        List<Object> Purchase_list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Purchase JOIN Client ON Client.id_client = Purchase.id_client JOIN Product ON Product.id_product = Purchase.id_purchase JOIN Store ON Store.id_store = Product.id_store";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                Store store = new Store();
                Client client = new Client();
                Purchase purchase = new Purchase();

                client.setId(resultSet.getInt("Client.id_client"));
                client.setName(resultSet.getString("Client.name"));
                client.setLastName(resultSet.getString("Client.last_name"));
                client.setEmail(resultSet.getString("Client.email"));

                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));

                product.setId(resultSet.getInt("Product.id_product"));
                product.setName(resultSet.getString("Product.name"));
                product.setStore(store);
                product.setPrice(resultSet.getDouble("Product.price"));
                product.setStock(resultSet.getInt("Product.stock"));

                purchase.setId(resultSet.getInt("Purchase.id_purchase"));
                purchase.setClient(client);
                purchase.setProduct(product);
                purchase.setAmount(resultSet.getInt("Purchase.amount"));
                purchase.setSell_date(LocalDate.now());
                store.addProducts(product);
                Purchase_list.add(purchase);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Purchase_list;
    }

    @Override
    public boolean update(Object object) {
        Connection connection = ConfigDB.openConnection();

        Purchase purchase = (Purchase) object;
        try {
            String sql = "UPDATE Purchase SET purchase_date = '" + LocalDate.now() + "' ,amount = '" + purchase.getAmount() + "' ,id_client = '" +purchase.getClient().getId() + "' ,id_product = '" + purchase.getProduct().getId() + "' WHERE Purchase.id_purchase = " + purchase.getId() ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.execute();

            ResultSet resultSet = (ResultSet) preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
             purchase.setId(resultSet.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Purchase updated successfully! " + purchase.toString()) ;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
            return false;
        }
        ConfigDB.closeConnection();

        return true;
    }
    public boolean updateProductStock(Object object){
        Connection connection = ConfigDB.openConnection();

        Purchase purchase = (Purchase) object;
        try {
            String sql = "UPDATE Product SET stock = '" +(purchase.getProduct().getStock()-purchase.getAmount())  + "' WHERE Product.id_product = " + purchase.getId();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.execute();

            ResultSet resultSet = (ResultSet) preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                purchase.setId(resultSet.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Purchase updated successfully! " + purchase.toString()) ;
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
            String sql = "DELETE FROM Purchase WHERE Purchase.id_purchase = " + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                JOptionPane.showMessageDialog(null, "Purchase deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null,"Purchase not found");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
    }

    @Override
    public Object findById(int id) {
        Connection connection = ConfigDB.openConnection();
        Purchase purchase = null;
        try{
            String sql = "SELECT * FROM Purchase JOIN Client ON Client.id_client = Purchase.id_client JOIN Product ON Product.id_product = Purchase.id_purchase JOIN Store ON Store.id_store = Product.id_store WHERE Purchase.id_purchase = " + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while(resultSet.next()){
                Product product = new Product();
                Store store = new Store();
                Client client = new Client();
                purchase = new Purchase();

                client.setId(resultSet.getInt("Client.id_client"));
                client.setName(resultSet.getString("Client.name"));
                client.setLastName(resultSet.getString("Client.last_name"));
                client.setEmail(resultSet.getString("Client.email"));

                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));

                product.setId(resultSet.getInt("Product.id_product"));
                product.setName(resultSet.getString("Product.name"));
                product.setStore(store);
                product.setPrice(resultSet.getDouble("Product.price"));
                product.setStock(resultSet.getInt("Product.stock"));

                purchase.setId(resultSet.getInt("Purchase.id_purchase"));
                purchase.setClient(client);
                purchase.setProduct(product);
                purchase.setAmount(resultSet.getInt("Purchase.amount"));
                purchase.setSell_date(LocalDate.now());
                store.addProducts(product);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }

        ConfigDB.closeConnection();
        return purchase;
    }
    public List<Object> filterByName(String name) {
        Connection connection = ConfigDB.openConnection();
        List<Object> Purchases_list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Purchase JOIN Client ON Client.id_client = Purchase.id_client JOIN Product ON Product.id_product = Purchase.id_purchase JOIN Store ON Store.id_store = Product.id_store WHERE Product.name LIKE '%' '" + name + "' '%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();

                Store store = new Store();
                store.setId(resultSet.getInt("Store.id_store"));
                store.setName(resultSet.getString("Store.name"));
                store.setLocation(resultSet.getString("Store.location"));

                product.setId(resultSet.getInt("id_product"));
                product.setName(resultSet.getString("Product.name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStore(store);
                store.addProducts(product);

                Client client = new Client();
                client.setId(resultSet.getInt("Client.id_client"));
                client.setName(resultSet.getString("Client.name"));
                client.setLastName(resultSet.getString("Client.last_name"));
                client.setEmail(resultSet.getString("Client.email"));

                Purchase purchase = new Purchase();
                purchase.setId(resultSet.getInt("Purchase.id_purchase"));
                purchase.setAmount(resultSet.getInt("Purchase.amount"));
                purchase.setSell_date(LocalDate.now());
                purchase.setProduct(product);
                purchase.setClient(client);

                Purchases_list.add(purchase);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Purchases_list;
    }
}
