public class Hardware extends Product {

    protected double weight;
    protected double shippingPrice;

    public Hardware(){}

    public String getAllHWProductData()
    {
        double totalPrice = this.price + this.shippingPrice;
        String weightAndShippingPrice = this.weight + "\t" + this.shippingPrice;
        String allHWData = getAllProductData() + "\n" + weightAndShippingPrice + "\n" + "Total Price:\t" + totalPrice;
        return allHWData;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

}
