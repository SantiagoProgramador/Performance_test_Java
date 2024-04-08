package Controller;

import Entity.Client;
import Model.ClientModel;

import javax.swing.*;
import java.util.List;

public class ClientController {
    ClientModel clientModel = new ClientModel();

    public boolean isEmpty(String msg){
        if (clientModel.read().isEmpty()){
            JOptionPane.showMessageDialog(null,msg);
            return true;
        }
        return false;
    }
    public void addClient(){
        String name = JOptionPane.showInputDialog("Type the name of the Client");
        String last_name = JOptionPane.showInputDialog("Type the last name of the client");
        String email = JOptionPane.showInputDialog("Type the email of the client");

        Client client = new Client();
        client.setName(name);
        client.setLastName(last_name);
        client.setEmail(email);

        clientModel.create(client);
    }
    public String showClients(){
        if (isEmpty("There is no clients to show"))return "Add clients before you try to list them!";

        String list = "Clients: \n";
        for (Object clientObj : clientModel.read()){
            Client client = (Client) clientObj;
            list += client.toString() + "\n";
        }
        return list;
    }
    public void findByName(){
        if (isEmpty("There is no clients to find"))return;
        String nameToFind = JOptionPane.showInputDialog("Type the name of the client/s you are looking for");
        List<Object> clients =  clientModel.filterByName(nameToFind);
        if (clients.isEmpty()){
            JOptionPane.showMessageDialog(null,"No clients were found with this letters");
        }else {
            String list = "Clients: \n";
            for (Object clientObj : clients){
                Client client = (Client) clientObj;
                list += client.toString() + "\n";
            }
            JOptionPane.showMessageDialog(null,list);
        }
    }
    public void updateClient(){
        if (isEmpty("There is no clients to update"))return;
        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(showClients()+"\nType the id of the client you want to update"));
        Client client = (Client) clientModel.findById(idUpdate);
        if (client==null){
            JOptionPane.showMessageDialog(null,"Client not found, type an existing Id!");
            return;
        }
        String name = JOptionPane.showInputDialog("Type the name of the Client");
        String last_name = JOptionPane.showInputDialog("Type the last name of the client");
        String email = JOptionPane.showInputDialog("Type the email of the client");

        client.setName(name);
        client.setLastName(last_name);
        client.setEmail(email);

        clientModel.update(client);
    }
    public void deleteClient(){
        if (isEmpty("There is no clients to delete"))return;
        int idDelete = Integer.parseInt(JOptionPane.showInputDialog(showClients()+"\nType the id of the client you want to update"));
        Client client = (Client) clientModel.findById(idDelete);
        if (client==null){
            JOptionPane.showMessageDialog(null,"Client not found, type an existing Id!");
            return;
        }
        int question = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this Client? " + client.toString());
        if (question == 0){
            clientModel.delete(idDelete);
        }
    }
}
