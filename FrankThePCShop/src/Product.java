public class Product {

    protected double price;
    protected String description;

    public Product(){}

    public String getAllProductData()
    {
        String allData = this.description + "\nPrice: " + this.price;
        return allData;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
