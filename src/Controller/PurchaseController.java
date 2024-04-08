package Controller;

import Entity.Client;
import Entity.Product;
import Entity.Purchase;
import Model.PurchaseModel;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class PurchaseController {
    PurchaseModel purchaseModel = new PurchaseModel();
    ProductController productController = new ProductController();
    ClientController clientController= new ClientController();
    public boolean isEmpty(String msg){
        if (purchaseModel.read().isEmpty()){
            JOptionPane.showMessageDialog(null,msg);
            return true;
        }
        return false;
    }
    public void addPurchase(){
        int idClient = Integer.parseInt(JOptionPane.showInputDialog(clientController.showClients()+"\nType the id of the client"));
        Client client = (Client) clientController.clientModel.findById(idClient);
        if (client == null){
            JOptionPane.showMessageDialog(null,"Client not found, type an existing Id!");
            return;
        }
        int idProduct = Integer.parseInt(JOptionPane.showInputDialog(productController.showProducts()+"\nType the id of the product"));
        Product product = (Product) productController.productModel.findById(idProduct);
        if (product == null){
            JOptionPane.showMessageDialog(null,"Product not found, type an existing Id!");
            return;
        }
        int amount = Integer.parseInt(JOptionPane.showInputDialog("Type the amount of " + product.getName() + " You want to buy"));
        if (amount < 0 || amount > product.getStock() ){
            JOptionPane.showMessageDialog(null,"Type a correct amount");
            return;
        }
        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setProduct(product);
        purchase.setAmount(amount);
        purchase.setSell_date(LocalDate.now());
        purchaseModel.create(purchase);
        purchaseModel.updateProductStock(purchase);
        JOptionPane.showMessageDialog(null,"PURCHASE BILL: \n" + "Client: "+client.toString() + "\n Price: " + (amount * product.getPrice()) + "\n Total price (IVA): " + ((amount * product.getPrice())+amount * product.getPrice()*0.19)+"\n Store: " +product.getStore().toString());

    }
    public String showPurchases(){
        if (isEmpty("There is no purchases to show!")) return "Add a purchase before trying to list them!";
        String list = "Purchases: \n";

        for (Object purchaseObj : purchaseModel.read()){
            Purchase purchase = (Purchase) purchaseObj;
            list += purchase.toString() + "\n";
        }

        return list;
    }
    public void findByProduct(){
        if (isEmpty("There is no purchases to show!")) return;
        String productName = JOptionPane.showInputDialog("Type the name of the product/s");
        List<Object> purchase_List = purchaseModel.filterByName(productName);
        if (purchase_List.isEmpty()){
            JOptionPane.showMessageDialog(null,"No coincidences found with the name send");
            return;
        }
        String list = "Purchases: \n";
        for (Object purchaseObj : purchase_List){
            Purchase purchase = (Purchase) purchaseObj;
            list += purchase.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null,list);
    }
    public void updatePurchase(){
        if (isEmpty("There is no purchases to show!")) return;
        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(showPurchases()+"Type the id of the Purchase you want to update"));
        Purchase purchase = (Purchase) purchaseModel.findById(idUpdate);
        if (purchase == null){
            JOptionPane.showMessageDialog(null,"Purchase not found, type an existing Id!");
            return;
        }
        int idClient = Integer.parseInt(JOptionPane.showInputDialog(clientController.showClients()+"\nType the id of the client"));
        Client client = (Client) clientController.clientModel.findById(idClient);
        if (client == null){
            JOptionPane.showMessageDialog(null,"Client not found, type an existing Id!");
            return;
        }
        int idProduct = Integer.parseInt(JOptionPane.showInputDialog(productController.showProducts()+"\nType the id of the product"));
        Product product = (Product) productController.productModel.findById(idProduct);
        if (product == null){
            JOptionPane.showMessageDialog(null,"Product not found, type an existing Id!");
            return;
        }
        int amount = Integer.parseInt(JOptionPane.showInputDialog("Type the amount of " + product.getName() + " You want to buy"));
        if (amount <= 0 || amount > product.getStock()){
            JOptionPane.showMessageDialog(null,"Type a correct amount");
            return;
        }
        purchase.setProduct(product);
        purchase.setClient(client);
        purchase.setAmount(amount);
        purchase.setSell_date(LocalDate.now());
        purchaseModel.update(purchase);
        purchaseModel.updateProductStock(purchase);
        JOptionPane.showMessageDialog(null,"PURCHASE BILL: \n" + "Client: "+client.toString() + "\n Price: " + (amount * product.getPrice()) + "\n Total price (IVA): " + ((amount * product.getPrice())+amount * product.getPrice()*0.19)+"\n Store: " +product.getStore().toString());

    }
    public void deletePurchase(){
        if (isEmpty("There is no purchases to show!")) return;
        int idDelete = Integer.parseInt(JOptionPane.showInputDialog(showPurchases()+"Type the id of the Purchase you want to delete"));
        Purchase purchase = (Purchase) purchaseModel.findById(idDelete);
        if (purchase == null){
            JOptionPane.showMessageDialog(null,"Purchase not found, type an existing Id!");
            return;
        }
        int question = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this Purchase?" + purchase.toString());
        if (question == 0){
            purchaseModel.delete(idDelete);
        }
    }
}
