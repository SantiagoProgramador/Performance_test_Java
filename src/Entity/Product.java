package Entity;

public class Product {
    //Attributes
    private int id;
    private String name;
    private double price;
    private int stock;
    private Store store;


    //Constructors
    public Product(int id, String name, double price, int stock, Store store) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.store = store;
    }
    public Product(){

    }

    //Getters and setters
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //ToString

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", store=" + store.getName() +
                '}';
    }
}
