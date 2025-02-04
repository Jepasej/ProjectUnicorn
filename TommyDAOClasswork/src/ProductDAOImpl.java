import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class ProductDAOImpl implements ProductDAO
{
    static final String DATABASEURL = "jdbc:sqlserver://localhost;database=dbTommyDAOExercise";
    static final String USERNAME = "sa";
    static final String PASSWORD = "admin";


    public static Connection getConnection() throws Exception
    {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(DATABASEURL, USERNAME, PASSWORD);
        return conn;
    }

    @Override
    public boolean addProduct(Product p) throws Exception
    {
        if(!(p ==null)) {
            String sql = "INSERT INTO tblProduct VALUES (?,?,?)";
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getNo());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Product added successfully.");
                return true;
            } else {
                System.out.println("Failed to add the product.");
                return false;
            }
        }
        return false;
    }

    @Override
    public Product readProduct(Product p) throws Exception {
        Product product = new Product();
        String sql = "SELECT * FROM tblProduct WHERE no = ? OR name = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,p.getNo());
        ps.setString(2,p.getName());

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            product.setNo(rs.getInt("no"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
        }
        return product;
    }

    @Override
    public List<Product> readAllProducts() throws Exception {
        ArrayList<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM tblProduct";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Product product = new Product();
            product.setNo(rs.getInt("no"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            productList.add(product);
        }
        return productList;
    }

    @Override
    public void updateProduct(Product p) throws Exception {
        if(!(p ==null)) {
            String sql = "UPDATE tblProduct SET no = ?, name = ?, price = ? WHERE no = ? OR name = ?";
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getNo());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getNo());
            ps.setString(5, p.getName());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update the product.");
            }
        }
    }

    @Override
    public void deleteProduct(Product p) throws Exception {
        if(!(p ==null)) {
            String sql = "DELETE FROM tblProduct WHERE no = ? OR name = ?";
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getNo());
            ps.setString(2, p.getName());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete the product.");
            }
        }
    }
}
