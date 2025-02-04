import java.util.ArrayList;

public class Main
{

    public static void main(String[] args) throws Exception {
        ProductDAO dao = new ProductDAOImpl();

        ArrayList<Product> products = (ArrayList<Product>) dao.readAllProducts();
        for(Product p : products)
        {
            System.out.println(p);
        }
        System.out.println();
    }
}