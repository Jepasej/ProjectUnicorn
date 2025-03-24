public class LocalTextFileAdapter implements FileAdapter {

    private FileService textFile;

    public LocalTextFileAdapter(String fileName) {this.textFile = new FileService(fileName);}

    public void showContent() {textFile.readFileToOutput();}
}
