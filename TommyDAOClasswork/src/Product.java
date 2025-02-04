public class Product
{
    private int no;
    private String name;
    private double price;

    public Product () {}

    public Product(int no, String name, double price)
    {
        this.no=no;
        this.name=name;
        this.price=price;
    }

    @Override
    public String toString()
    {
        return this.no + " " + this.name + " " + this.price;
    }

    public int getNo()
    {
        return this.no;
    }

    public String getName()
    {
        return this.name;
    }

    public double getPrice()
    {
        return this.price;
    }

    public void setNo(int newNo)
    {
        this.no = newNo;
    }

    public void setName(String newName)
    {
        this.name = newName;
    }

    public void setPrice(double newPrice)
    {
        this.price = newPrice;
    }

}