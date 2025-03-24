import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileSystemService {
    private File directory;

    public FileSystemService(String directoryPath) {
        this.directory = new File(directoryPath);
    }

    public int countFiles() {
        return directory.listFiles().length;
    }

    public List<String> listFileNames() {
        List<String> fileNames = new ArrayList<>();
        for (File file : directory.listFiles()) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    public List<Double> listFileSizes() {
        List<Double> fileSizes = new ArrayList<>();

        try{
        for (File file : directory.listFiles()) {
            fileSizes.add((double)Files.size(file.toPath()));
        }
    }
        catch (IOException e){}

        return fileSizes;
    }

    public void writeFileContents() {
        Scanner input = null;

        List<String> fileNames = listFileNames();

        for (String fileName : fileNames) {

            File file = new File(directory, fileName);

            try {
                input = new Scanner(file);

                while (input.hasNextLine()) {
                    System.out.println(input.nextLine());
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("No such file");
            }
            finally {
                if (input != null) {
                    input.close();
                }
            }
        }
    }
}

