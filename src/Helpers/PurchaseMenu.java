package Helpers;

import Controller.PurchaseController;

import javax.swing.*;

public class PurchaseMenu {
    PurchaseController purchaseController = new PurchaseController();
    public void menu(){
        String option = "";
        do {
            try {
                option = JOptionPane.showInputDialog("""
                        Purchases Menu
                        Please choose an option:
                        1. Add a Purchase.
                        2. Show Purchases.
                        3. Filter Purchases by product.
                        4. Delete a Purchase.
                        5. Update a Purchase.
                        6. EXIT.
                        """);
                switch (option){
                    case "1":
                        purchaseController.addPurchase();
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(null,purchaseController.showPurchases());
                        break;
                    case "3":
                        purchaseController.findByProduct();
                        break;
                    case "4":
                        purchaseController.deletePurchase();
                        break;
                    case "5":
                        purchaseController.updatePurchase();
                        break;
                    case "6":
                        JOptionPane.showMessageDialog(null,"Closing the Purchases Menu...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,"Please type a valid option!");
                        break;
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Wrong value, try again..."+ e);
            }
        }while (!option.equals("6"));
    }
}
