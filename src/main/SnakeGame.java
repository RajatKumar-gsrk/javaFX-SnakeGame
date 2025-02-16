package main;
import main.constants;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Duration;//make sure to import proper libraries
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        primaryScene.setOnKeyPressed(new EventHandler<KeyEvent>() {//handles key baord input
            @Override
            public void handle(KeyEvent e){
                KeyCode code = e.getCode();
                if(code == KeyCode.RIGHT || code == KeyCode.D){
                    if(snake.get(0).getCurrent_direction() != constants.LEFT){
                        snake.get(0).setDirections(constants.RIGHT, snake.get(0).getCurrent_direction());
                    }
                }else if(code == KeyCode.LEFT || code == KeyCode.A){
                    if(snake.get(0).getCurrent_direction() != constants.RIGHT){
                        snake.get(0).setDirections(constants.LEFT, snake.get(0).getCurrent_direction());
                    }
                }else if(code == KeyCode.DOWN || code == KeyCode.S){
                    if(snake.get(0).getCurrent_direction() != constants.UP){
                        snake.get(0).setDirections(constants.DOWN, snake.get(0).getCurrent_direction());
                    }
                }else if(code == KeyCode.UP || code == KeyCode.W){
                    if(snake.get(0).getCurrent_direction() != constants.DOWN){
                        snake.get(0).setDirections(constants.UP, snake.get(0).getCurrent_direction());
                    }
                }
            }
        });

        createSnake();

        Timeline mainTimeline = new Timeline(new KeyFrame(Duration.millis(130), e->run()));//animates
        mainTimeline.setCycleCount(Animation.INDEFINITE);
        mainTimeline.play();

    }


    private void run(){
        if(!gameOver){
            drawBackground();
            drawSnake();
            drawFoodItems();
            drawPoisonItems();

            moveSnake();
        }
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

        SnakeBody mid = new SnakeBody(constants.BODY, 4, 4);
        snake.add(mid);

        SnakeBody tail = new SnakeBody(constants.TAIL, 3, 4);
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

    private void moveSnake(){
        for(int i = 1; i < snake.size(); i += 1){
            snake.get(i).setDirections(snake.get(i-1).getPrevious_direction(), snake.get(i).getCurrent_direction());
        }

        for(int i = 0; i < snake.size(); i += 1){
            switch(snake.get(i).getCurrent_direction()){
                case constants.UP: snake.get(i).setY(snake.get(i).getY() - 1); break;
    
                case constants.RIGHT: snake.get(i).setX(snake.get(i).getX() + 1); break;
    
                case constants.DOWN: snake.get(i).setY(snake.get(i).getY() + 1); break;
    
                case constants.LEFT: snake.get(i).setX(snake.get(i).getX() - 1); break; 
            }
        }

        checkGameOver();
    }

    private void checkGameOver(){
        int x = snake.get(0).getX(), y = snake.get(0).getY();

        if(x < 0 || x >= constants.COLS){
            gameOver = true;
            return;
        }else if(y < 0 || y >= constants.ROWS){
            gameOver = true;
            return;
        }

        for(int i = 1; i < snake.size(); i += 1){
            if(x == snake.get(i).getX() && y == snake.get(i).getY()){
                gameOver = true;
                return;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        launch(SnakeGame.class);
    }
}
// make snake move