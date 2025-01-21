package org.example.musicplayer;
import javafx.scene.image.Image;
import java.io.File;
import java.util.*;

/**
 * Class to enable an ArrayList with all images we need for the application. Loaded images from the resource folder into the array.
 */
public class ImageDisplay
{
    //Declaring the array
    public ArrayList<Image> images;
    private String imageFolderFilepath;


    /**
     * Initializes the array and fills the array with images from ressource folder
     */
    public ImageDisplay()
    {
        //INITIALIZING the Array
        this.images = new ArrayList<>();

        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn1.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn2.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn3.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn4.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn5.jpg"))));
    }


    /**
     * Takes a filepath of a directory, goes through all files in the directory and saves all .jpg, .jpeg and .png
     * files to an image array.
     * @param imageFolderFilepath
     */
    public ImageDisplay(String imageFolderFilepath)
    {
        this.imageFolderFilepath = imageFolderFilepath;
        this.images = new ArrayList<>();

        File imageFolder = new File(imageFolderFilepath);

        //Returns an array of abstract pathnames denoting the files in the directory denoted by this abstract pathname.
        File[] imageFiles = imageFolder.listFiles();

        for (File imageFile : imageFiles)
        {
            String filename = imageFile.getName();
            if(imageFile.isFile()&&filename.endsWith(".jpg")||filename.endsWith(".jpeg")||filename.endsWith(".png"))
            {
                images.add(new Image(imageFile.toURI().toString()));
            }
        }
    }


    /**
     * Method to generate a random image out of the array. Uses the importet Random Class.
     * @return a random image.
     */
    public Image getRandomImage()
    {
        if (!images.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(images.size()); // Get a random index
            return images.get(randomIndex); // Return the random image
        }

        return null;
    }
}
