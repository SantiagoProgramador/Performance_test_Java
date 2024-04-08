package Controller;

import Entity.Client;
import Entity.Product;
import Entity.Store;
import Model.ProductModel;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.List;

public class ProductController {
    ProductModel productModel = new ProductModel();

    public boolean isStore(){
        if (productModel.getStores().isEmpty()){
            JOptionPane.showMessageDialog(null,"You cannot use the product menu without Stores!");
            return false;
        }
        return true;
    }
    public boolean isEmpty(String msg){
        if (productModel.read().isEmpty()){
            JOptionPane.showMessageDialog(null,msg);
            return true;
        }
        return false;
    }
    public String showStores(){
        String list = "Stores: \n";
        for (Object storeObj : productModel.getStores()){
            Store store = (Store) storeObj;
            list += store.toString() + "\n";
        }
        return list;
    }

    public void addProduct(){
        int storeId = Integer.parseInt(JOptionPane.showInputDialog(showStores()+" Type the id of the store you want to add the product"));
        Store store = (Store) productModel.findStoreById(storeId);
        if (store == null){
            JOptionPane.showMessageDialog(null,"Store not found, type an existing Id!");
            return;
        }
        String name = JOptionPane.showInputDialog("Type the name of the product");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Type the price of the product"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Type the stock of the product"));
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setStore(store);
        productModel.create(product);

    }
    public String showProducts(){
        if (isEmpty("There is no products to show!")) return "Add products before trying to list them!";
        String list = "Products: \n";
        for (Object productObj : productModel.read()){
            Product product = (Product) productObj;
            list += product.toString() + "\n";
        }
        return list;
    }
    public void findByName(){
        if (isEmpty("There is no products to search!"))return;
        String nameToFind = JOptionPane.showInputDialog("Type the name of the product/s you want to search!");
        List<Object> products =  productModel.filterByName(nameToFind);
        if (products.isEmpty()){
            JOptionPane.showMessageDialog(null,"No products were found with this letters");
        }else {
            String list = "Products: \n";
            for (Object productObj : products){
                Product product = (Product) productObj;
                list += product.toString() + "\n";
            }
            JOptionPane.showMessageDialog(null,list);
        }
    }
    public void findByStore(){
        if (isEmpty("There is no products to search!"))return;
        int search = Integer.parseInt(JOptionPane.showInputDialog(showStores()+" Type the id of the store you want to see the products"));
        Store store = (Store) productModel.findStoreById(search);
        if (store == null){
            JOptionPane.showMessageDialog(null,"Store not found, type an existing Id!");
            return;
        }
        String list = "Products in " + store.getName() + "\n";
        for (Object productObj : productModel.findProductsInStore(search)){
            Product product = (Product) productObj;
            list += product.toString();
        }
        JOptionPane.showMessageDialog(null,list);
    }
    public void updateProduct(){
        if (isEmpty("There is no products to update"))return;
        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(showProducts()+"Type the id of the product you want to update"));
        Product product = (Product) productModel.findById(idUpdate);
        if (product == null){
            JOptionPane.showMessageDialog(null,"Product not found, type an existing Id!");
            return;
        }
        int storeId = Integer.parseInt(JOptionPane.showInputDialog("Type the id of the store"));
        Store store = (Store) productModel.findStoreById(storeId);
        if (store == null){
            JOptionPane.showMessageDialog(null,"Store not found, type an existing Id!");
            return;
        }
        String name = JOptionPane.showInputDialog("Type the name of the product");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Type the price of the product"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Type the stock of the product"));
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setStore(store);
        productModel.update(product);
    }
    public void deleteProduct(){
        if (isEmpty("There is no products to delete"))return;
        int idDelete = Integer.parseInt(JOptionPane.showInputDialog(showProducts()+"Type the id of the product you want to delete"));
        Product product = (Product) productModel.findById(idDelete);
        if (product == null){
            JOptionPane.showMessageDialog(null,"Product not found, type an existing Id!");
            return;
        }
        int question = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this product?" + product.toString());
        if (question == 0){
            productModel.delete(idDelete);
        }
    }
}
