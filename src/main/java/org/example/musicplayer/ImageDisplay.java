package org.example.musicplayer;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;


public class ImageDisplay
{
    /*
    public void start(Stage primaryStage)
    {
        ArrayList<Image> imageList = new ArrayList<>();
        imageList.add(new Image(ImageDisplay.class.getResourceAsStream("Charlie%27s_Kidney_Taken.webp")));
        imageList.add(new Image(ImageDisplay.class.getResourceAsStream("Unicorn1.webp")));
        imageList.add(new Image(ImageDisplay.class.getResourceAsStream("unicorn4.jpg")));
        imageList.add(new Image(ImageDisplay.class.getResourceAsStream("unicorn_final_full-1.webp")));
        imageList.add(new Image(ImageDisplay.class.getResourceAsStream("Why-Indias-unicorns-have-bolted.jpg")));
    }
    */

    private static ArrayList<Image> images = new ArrayList<>();

    public ImageDisplay()
    {
        images.add(new Image(ImageDisplay.class.getResourceAsStream("Charlie%27s_Kidney_Taken.webp")));
        images.add(new Image(ImageDisplay.class.getResourceAsStream("Unicorn1.webp")));
        images.add(new Image(ImageDisplay.class.getResourceAsStream("unicorn4.jpg")));
        images.add(new Image(ImageDisplay.class.getResourceAsStream("unicorn_final_full-1.webp")));
        images.add(new Image(ImageDisplay.class.getResourceAsStream("Why-Indias-unicorns-have-bolted.jpg")));
    }

    // Method to retrieve a random image
    public static Image getRandomImage()
    {
        if (!images.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(images.size()); // Get a random index
            return images.get(randomIndex); // Return the random image
        }
        return null; // Return null if the list is empty
    }



}
