import java.util.List;

public class LocalFileSystemAdapter implements FileSystemAdapter {
    private FileSystemService localFileSystem;

    public LocalFileSystemAdapter(String directoryPath) {
        this.localFileSystem = new FileSystemService(directoryPath);
    }

    @Override
    public int getTotalNumberOfFiles() {
        return localFileSystem.countFiles();
    }

    @Override
    public List<String> getFileNames() {
        return localFileSystem.listFileNames();
    }

    @Override
    public List<Double> getFileSizes(){return localFileSystem.listFileSizes(); }

    @Override
    public void showAllContents() {
        localFileSystem.writeFileContents();
    }
}

