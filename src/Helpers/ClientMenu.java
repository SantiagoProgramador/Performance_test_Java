package Helpers;

import Controller.ClientController;

import javax.swing.*;

public class ClientMenu {
    public void menu(){
        ClientController clientController = new ClientController();
        String option = "";
        do {
            try {
                option = JOptionPane.showInputDialog("""
                        Client Menu
                        Please choose an option:
                        1. Add a Client.
                        2. Show Clients.
                        3. Filter Clients.
                        4. Delete a Client.
                        5. Update a Client.
                        6. EXIT.
                        """);
                switch (option){
                    case "1":
                        clientController.addClient();
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(null,clientController.showClients());
                        break;
                    case "3":
                        clientController.findByName();
                        break;
                    case "4":
                        clientController.deleteClient();
                        break;
                    case "5":
                        clientController.updateClient();
                        break;
                    case "6":
                        JOptionPane.showMessageDialog(null,"Closing the Clients Menu...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,"Please type a valid option!");
                        break;
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Wrong value, try again...");
            }
        }while (!option.equals("6"));
    }
}
