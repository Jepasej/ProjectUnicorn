package org.example.musicplayer;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Class to enable an ArrayList with all images we need for the application. Loaded images from the resource folder into the array.
 */
public class ImageDisplay
{
    //Declaring the array
    public ArrayList<Image> images;

    public ImageDisplay()
        {
            //INITIALIZING the Array
        this.images = new ArrayList<Image>();

        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn1.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn2.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn3.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn4.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn5.jpg"))));

        System.out.println("Images added: " + images.size());
        }

        //Method to generate a random image out of the array. Uses the importet Random Class.
    public Image getRandomImage()
    {
        if (!images.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(images.size()); // Get a random index
            return images.get(randomIndex); // Return the random image
        }
        System.out.printf("No images found%n");
        return null;
    }
}
