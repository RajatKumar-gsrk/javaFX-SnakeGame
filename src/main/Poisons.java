package main;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Poisons {
    private static final String[] imagePaths = new String[]{"/resources/poisons/burger.png","/resources/poisons/gas.png",
    "/resources/poisons/poison.png","/resources/poisons/rotten.png"};

    private int posX, posY;
    private Image item;


    public Poisons(int x, int y){
        this.posX = x*constants.SQUARE_SIZE;
        this.posY = y*constants.SQUARE_SIZE;
        this.item = new Image(getClass().getResourceAsStream(imagePaths[new Random().nextInt(imagePaths.length)]));
    }

    public void drawPoisonItem(GraphicsContext penGC){
        penGC.drawImage(item, posY, posX, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
    }
}
