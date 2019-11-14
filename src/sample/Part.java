package sample;

public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = 0;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public Part setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Part setName(String name) {
        this.name = name;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Part setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Part setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public int getMin() {
        return min;
    }

    public Part setMin(int min) {
        this.min = min;
        return this;
    }

    public int getMax() {
        return max;
    }

    public Part setMax(int max) {
        this.max = max;
        return this;
    }
}
