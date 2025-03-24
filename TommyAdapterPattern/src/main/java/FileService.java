import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileService {
    private File file;

    public FileService(String fileName) {this.file = new File(fileName);}

    public void readFileToOutput()
    {
        Scanner input = null;

        try {
             input = new Scanner(file);

            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("No such file");
            }
        finally{
            if(input!=null){
                input.close();
            }
        }
    }
}
