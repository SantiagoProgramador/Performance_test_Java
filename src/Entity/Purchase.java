package Entity;

import java.time.LocalDate;

public class Purchase {
    //Attributes
    private int id;
    private LocalDate sell_date = LocalDate.now();
    private int amount;
    private Client client;
    private Product product;

    //Constructors
    public Purchase(int id, LocalDate sell_date, int amount, Client client, Product product) {
        this.id = id;
        this.sell_date = sell_date;
        this.amount = amount;
        this.client = client;
        this.product = product;
    }

    public Purchase(){

    }
    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSell_date() {
        return sell_date;
    }

    public void setSell_date(LocalDate sell_date) {
        this.sell_date = sell_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    //ToString

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", Purchase_date=" + sell_date +
                ", amount=" + amount +
                ", client=" + client.getName() +
                ", product=" + product.toString() +
                '}';
    }
}
