package Model;

import Database.CRUD;
import Database.ConfigDB;
import Entity.Client;
import Entity.Product;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientModel implements CRUD {
    @Override
    public Object create(Object object) {
        Connection connection = ConfigDB.openConnection();
        Client client = (Client) object;
        try {
            String sql = "INSERT INTO Client (name,last_name,email) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,client.getName());
            preparedStatement.setString(2,client.getLastName());
            preparedStatement.setString(3,client.getEmail());
            preparedStatement.execute();

            ResultSet resultSet = (ResultSet) preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                client.setId(resultSet.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Client added successfully! " + client.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }

        ConfigDB.closeConnection();
        return client;
    }

    @Override
    public List<Object> read() {
        Connection connection = ConfigDB.openConnection();
        List<Object> Client_list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Client";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()){
                Client client = new Client();
                client.setId(resultSet.getInt("id_client"));
                client.setName(resultSet.getString("name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setEmail(resultSet.getString("email"));

                Client_list.add(client);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Client_list;
    }

    @Override
    public boolean update(Object object) {
        Connection connection = ConfigDB.openConnection();
        Client client = (Client) object;
        try {
            String sql = "UPDATE Client SET name ='" + client.getName() + "', last_name = '" + client.getLastName() + "',email = '" + client.getEmail() + "' WHERE id_client = " + client.getId();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.execute();

            ResultSet resultSet = (ResultSet) preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
             client.setId(resultSet.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Client updated successfully! " + client.toString());
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
            String sql = "DELETE FROM Client WHERE Client.id_client = " + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                JOptionPane.showMessageDialog(null, "Client deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null,"Client not found...");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
    }

    @Override
    public Object findById(int id) {
        Connection connection = ConfigDB.openConnection();
        Client client = null;
        try{
            String sql = "SELECT * FROM Client WHERE id_client = " + id;
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while(resultSet.next()){
                client = new Client();
                client.setId(resultSet.getInt("id_client"));
                client.setName(resultSet.getString("name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setEmail(resultSet.getString("email"));

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }

        ConfigDB.closeConnection();
        return client;
    }
    public List<Object> filterByName(String name) {
        Connection connection = ConfigDB.openConnection();
        List<Object> Client_list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Client WHERE name LIKE '%' '"  + name + "' '%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()){
                Client client = new Client();
                client.setId(resultSet.getInt("id_client"));
                client.setName(resultSet.getString("name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setEmail(resultSet.getString("email"));

                Client_list.add(client);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error >> " + e);
        }
        ConfigDB.closeConnection();
        return Client_list;
    }
}
