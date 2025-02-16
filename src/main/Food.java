package main;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Food {
    private static final String[] imagePaths = new String[]{"/resources/food/apple.png","/resources/food/banana.png",
    "/resources/food/orange.png","/resources/food/watermelon.png","/resources/food/cherries.png","/resources/food/grapes.png",
    "/resources/food/pineapple.png","/resources/food/strawberry.png"};

    private int posX, posY;
    private Image item;


    public Food(int x, int y){
        this.posX = x*constants.SQUARE_SIZE;
        this.posY = y*constants.SQUARE_SIZE;
        this.item = new Image(getClass().getResourceAsStream(imagePaths[new Random().nextInt(imagePaths.length)]));
    }

    public int getX(){
        return this.posX/constants.SQUARE_SIZE;
    }

    public int getY(){
        return this.posY/constants.SQUARE_SIZE;
    }

    public void drawFoodItem(GraphicsContext penGC){
        penGC.drawImage(item, posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
    }
}
