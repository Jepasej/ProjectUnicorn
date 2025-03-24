import java.io.IOException;
import java.util.List;

public class Main {

    // Opgave 1: Udvid programmet med en ny metode så man kan få en liste af alle fil-størrelserne returneret.

    // Opgave 2: Udvid programmet med en ny (void) metode så man kan få udprintet al filindholdet af den første fil

    // Opgave 3: Udvid programmet med en ny (void) metode så man kan få udprintet al filindholdet af alle filer efter hinanden


    public static void main(String [] args) throws IOException {

        String workingPath = "C:/temp/data";
        FileSystemAdapter fileSystem = new LocalFileSystemAdapter(workingPath);

        System.out.println("Total number of files: " + fileSystem.getTotalNumberOfFiles());

        List<String> fileNames = fileSystem.getFileNames();
        System.out.println("File Names:");
        for (String name : fileNames) {
            System.out.println(name);
        }

        List<Double> fileSizes = fileSystem.getFileSizes();
        System.out.println("File Sizes:");
        for (Double size : fileSizes) {
            System.out.println((double)size);
        }

        //FileAdapter file = new LocalTextFileAdapter(workingPath + "/" + fileNames.get(0));

        //file.showContent();

        //fileSystem.showAllContents();

    }
}
