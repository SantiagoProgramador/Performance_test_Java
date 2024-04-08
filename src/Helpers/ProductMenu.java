package Helpers;

import Controller.ProductController;

import javax.swing.*;

public class ProductMenu {
    public void menu(){
        ProductController productController = new ProductController();
        String option = "";
        do {
            try {
                option = JOptionPane.showInputDialog("""
                        Product Menu
                        Please choose an option:
                        1. Add a Product.
                        2. Show Products.
                        3. Filter Products by name.
                        4. Update a Product.
                        5. Delete a Product.
                        6. Filter by store.
                        7. Show stores.
                        8. EXIT.
                        """);
                switch (option){
                    case "1":
                        productController.addProduct();
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(null,productController.showProducts());
                        break;
                    case "3":
                        productController.findByName();
                        break;
                    case "4":
                        productController.updateProduct();
                        break;
                    case "5":
                        productController.deleteProduct();
                        break;
                    case "6":
                        productController.findByStore();
                        break;
                    case "7":
                        JOptionPane.showMessageDialog(null,productController.showStores());
                        break;
                    case "8":
                        JOptionPane.showMessageDialog(null,"Closing the Products Menu...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,"Please type a valid option!");
                        break;
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Wrong value, try again...");
            }
        }while (!option.equals("8"));
    }
}
