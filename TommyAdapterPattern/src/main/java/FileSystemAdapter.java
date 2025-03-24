import java.io.IOException;
import java.util.List;

public interface FileSystemAdapter {
    public int getTotalNumberOfFiles();
    public List<String> getFileNames();
    public List<Double> getFileSizes();
    public void showAllContents();
}

