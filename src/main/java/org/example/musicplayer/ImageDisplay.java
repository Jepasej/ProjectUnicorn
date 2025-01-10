package org.example.musicplayer;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class ImageDisplay
{

    public ArrayList<Image> images;


    public ImageDisplay()
        {
        this.images = new ArrayList<Image>();

        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn1.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn2.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn3.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn4.jpg"))));
        images.add(new Image(Objects.requireNonNull(ImageDisplay.class.getResourceAsStream("Images/unicorn5.jpg"))));

        System.out.println("Images added: " + images.size());
        }

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
