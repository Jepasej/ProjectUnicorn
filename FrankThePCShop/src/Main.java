public class Main {
    public static void main(String[] args) {

        Product product = new Product();
        product.setPrice(100);
        product.setDescription("This is a product");
        System.out.println(product.getAllProductData());

        Hardware hardware = new Hardware();
        hardware.setDescription("This is a hardware, you will love it");
        hardware.setPrice(150);
        hardware.setWeight(23.42);
        hardware.setShippingPrice(29.99);
        System.out.println(hardware.getAllHWProductData());
    }
}