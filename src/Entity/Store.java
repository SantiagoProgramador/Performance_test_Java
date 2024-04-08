package Entity;

import java.util.ArrayList;
import java.util.List;

public class Store {
    //Attributes
    private int id;
    private String name;
    private String location;
    private List<Object> products = new ArrayList<>();

    //Constructors
    public Store(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.products = new ArrayList<>();
    }
    public Store(){

    }

    //Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Object> getProducts() {
        return products;
    }

    public void setProducts(List<Object> products) {
        this.products = products;
    }
    public void addProducts(Product product){
        this.products.add(product);
    }
    //ToString
    @Override
    public String toString() {

        String listProducts = this.products.isEmpty() ? "The store does not have products yet" : products.toString();
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", products=" + listProducts +
                '}';
    }
}
