import java.util.ArrayList;
import java.util.List;

/**
 * En in-memory implementation af en "produktdatabase"
 * Hvert produkt indeholder et unikt ID kaldet no.
 */
public class ProductDAORepository implements ProductDAO
{

    private ArrayList<Product> products;

    public ProductDAORepository()
    {
        products = new ArrayList<>();

        products.add(new Product(1,"kaffe",35.5));

        products.add(new Product(7,"smør",27.0));

        products.add(new Product(16,"12 æg",15.50));
    }

    @Override
    public boolean addProduct(Product p)
    {
        this.products.add(p);
        return true;
    }

    @Override
    public Product readProduct(Product p)
    {
        //for(Product p : products)
        //{
        //    if(p.getNo() == no)
        //    {
        //        return p;
        //    }
        //}
        return null;
    }

    @Override
    public List<Product> readAllProducts()
    {
        return this.products;
    }

    public void updateProduct(Product p)
    {
        for(Product checkProducts : products)
        {
            if(checkProducts.getNo() == p.getNo())
            {
                checkProducts.setName(p.getName());
                checkProducts.setPrice(p.getPrice());
            }
            else if(checkProducts.getName().equalsIgnoreCase(p.getName()))
            {
                checkProducts.setNo(p.getNo());
                checkProducts.setPrice(p.getPrice());
            }
        }
    }

    public void deleteProduct(Product p)
    {
            products.remove(p);
    }
}