package main;
import main.constants;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SnakeGame extends Application{
    
    

    private Boolean gameOver = false;
    private List<SnakeBody> snake = new ArrayList<>();
    private List<Food> food_items = new ArrayList<>();
    private List<Poisons> poison_items = new ArrayList<>();

    private GraphicsContext penGC;//paintBrush used to draw content on canvas

    public void start(Stage mainWindow){
        
        Group root = new Group();//groups together components 
        Canvas mainCanvas = new Canvas(constants.WIDTH, constants.HEIGHT);
        penGC = mainCanvas.getGraphicsContext2D();//setting brush for main canvas
        root.getChildren().add(mainCanvas);

        Scene primaryScene = new Scene(root);

        mainWindow.setScene(primaryScene);
        mainWindow.setTitle("SnakeGme");
        mainWindow.show();
        run();
    }


    private void run(){
        drawBackground();
        createSnake();
        drawSnake();
        drawFoodItems();
        drawPoisonItems();
    }


    private void drawBackground(){//draws background
        for(int row = 0; row < constants.ROWS; row += 1){
            for(int col = 0; col < constants.COLS; col += 1){
                if((row + col) % 2 == 0){
                    penGC.setFill(Color.web("#76ad45"));
                }else{
                    penGC.setFill(Color.web("#5a9426"));
                }

                penGC.fillRect(row * constants.SQUARE_SIZE, col * constants.SQUARE_SIZE, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
            }
        }
    }

    private void createSnake(){
        SnakeBody head = new SnakeBody(constants.HEAD, 5, 4);
        snake.add(head);

        SnakeBody tail = new SnakeBody(constants.TAIL, 4, 4);
        snake.add(tail);
    }

    private void drawSnake(){
        for(SnakeBody sb : snake){
            sb.drawPart(penGC);
        } 
    }

    private void drawFoodItems(){
        food_items.add(new Food(7, 8));
        for(Food fd: food_items){
            fd.drawFoodItem(penGC);
        }
    }

    private void drawPoisonItems(){
        poison_items.add(new Poisons(9, 2));
        for(Poisons p: poison_items){
            p.drawPoisonItem(penGC);
        }
    }

    public static void main(String[] args) throws Exception {
        launch(SnakeGame.class);
    }
}
//make foods/ make snake move