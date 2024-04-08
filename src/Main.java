import Controller.ProductController;
import Helpers.ClientMenu;
import Helpers.ProductMenu;
import Helpers.PurchaseMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String option = "";
        do {
            try {
                option = JOptionPane.showInputDialog("""
                        WELCOME TO DeModaOutlet Store administrator!
                        Please choose an option:
                        1. Client Menu.
                        2. Product Menu.
                        3. Purchase menu.
                        4. EXIT.
                        """);
                switch (option){
                    case "1":
                        ClientMenu clientMenu = new ClientMenu();
                        clientMenu.menu();
                        break;
                    case "2":
                        ProductController productController = new ProductController();
                        if (!productController.isStore()) return;
                        ProductMenu productMenu = new ProductMenu();
                        productMenu.menu();
                        break;
                    case "3":
                        PurchaseMenu purchaseMenu = new PurchaseMenu();
                        purchaseMenu.menu();
                        break;
                    case "4":
                        JOptionPane.showMessageDialog(null,"Thanks for using the store administrator! :D");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,"Please type a valid option!");
                        break;
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Wrong value, try again...");
            }
        }while (!option.equals("4"));
    }
}