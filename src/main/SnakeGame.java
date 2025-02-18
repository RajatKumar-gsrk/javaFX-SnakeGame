package main;
import main.constants;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SnakeGame extends Application{
    
    private int score = 0;

    private Boolean gameOver = false;
    private Boolean gameOverMusic = true;
    private List<SnakeBody> snake = new ArrayList<>();
    private List<Food> food_items = new ArrayList<>();
    private List<Poisons> poison_items = new ArrayList<>();

    private GraphicsContext penGC;//paintBrush used to draw content on canvas
    private MediaPlayer player; //plays music

    public void start(Stage mainWindow){
        
        Group root = new Group();//groups together components 
        Canvas mainCanvas = new Canvas(constants.WIDTH + 200, constants.HEIGHT);
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
                        snake.get(0).setDirections(constants.RIGHT, constants.RIGHT);
                    }
                }else if(code == KeyCode.LEFT || code == KeyCode.A){
                    if(snake.get(0).getCurrent_direction() != constants.RIGHT){
                        snake.get(0).setDirections(constants.LEFT, constants.LEFT);
                    }
                }else if(code == KeyCode.DOWN || code == KeyCode.S){
                    if(snake.get(0).getCurrent_direction() != constants.UP){
                        snake.get(0).setDirections(constants.DOWN, constants.DOWN);
                    }
                }else if(code == KeyCode.UP || code == KeyCode.W){
                    if(snake.get(0).getCurrent_direction() != constants.DOWN){
                        snake.get(0).setDirections(constants.UP, constants.UP);
                    }
                }else if(code == KeyCode.SPACE){
                    gameOver = !gameOver;
                }
            }
        });


        createSnake();
        generateFood();

        Timeline poisonTimeline = new Timeline(new KeyFrame(Duration.millis(15000), e->generatePoison()));
        poisonTimeline.setCycleCount(Animation.INDEFINITE);
        poisonTimeline.play();

        PlayBackgroundMusic();

        Timeline mainTimeline = new Timeline(new KeyFrame(Duration.millis(200), e->run()));//animates
        mainTimeline.setCycleCount(Animation.INDEFINITE);
        mainTimeline.play();

    }

    private void generateFood(){
        begin:
        while(true){
            Random rand = new Random();
            int x = rand.nextInt(constants.COLS), y = rand.nextInt(constants.ROWS);
            
            if(food_items.size() > 10){
                int i = rand.nextInt(poison_items.size());
                food_items.remove(i);
            }

            for(SnakeBody sb: snake){
                if(x == sb.getX() && y == sb.getY()){
                    continue begin;
                }
            }

            for(Poisons p: poison_items){
                if(x == p.getX() && y == p.getY()){
                    continue begin;
                }
            }

            for(Food f: food_items){
                if(x == f.getX() && y == f.getY()){
                    continue begin;
                }
            }

            food_items.add(new Food(x, y));
            return;
        }
    }

    private void generatePoison(){
        
        begin:
        while(true){
            Random rand = new Random();
            int x = rand.nextInt(constants.COLS), y = rand.nextInt(constants.ROWS);
            
            if(poison_items.size() > 4){
                int i = rand.nextInt(poison_items.size());
                poison_items.remove(i);
            }

            for(SnakeBody sb: snake){
                if(x == sb.getX() && y == sb.getY()){
                    continue begin;
                }
            }

            for(Poisons p: poison_items){
                if(x == p.getX() && y == p.getY()){
                    continue begin;
                }
            }

            for(Food f: food_items){
                if(x == f.getX() && y == f.getY()){
                    continue begin;
                }
            }

            poison_items.add(new Poisons(x, y));
            return;
        }
    }


    private void run(){
        if(!gameOver){
            drawBackground();
            moveSnake();
            drawFoodItems();
            drawPoisonItems();
            drawSnake();
            eatFood();
            eatPoison();
            drawScore();
            //drawPoisonItems();
        }else{
            penGC.setFill(Color.web("#eb4034"));
            penGC.setFont(new Font(90));
            penGC.fillText("GAME OVER", constants.WIDTH/12, constants.HEIGHT/2);
            if(gameOverMusic){
                PlayGameOverMusic();
            }
        }
    }


    private void drawBackground(){//draws background

        penGC.setFill(Color.web("#5976b5"));
        penGC.fillRect(0, 0, constants.WIDTH + 200, constants.HEIGHT);

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
        mid = new SnakeBody(constants.BODY, 3, 4);
        snake.add(mid);
        mid = new SnakeBody(constants.BODY, 2, 4);
        snake.add(mid);

        SnakeBody tail = new SnakeBody(constants.TAIL, 1, 4);
        snake.add(tail);
    }

    private void drawSnake(){
        for(int i = snake.size() - 1; i >= 0; i -= 1){
            snake.get(i).drawPart(penGC);
        }
    }

    private void drawFoodItems(){
        for(Food fd: food_items){
            fd.drawFoodItem(penGC);
        }
    }

    private void drawPoisonItems(){
        for(Poisons p: poison_items){
            p.drawPoisonItem(penGC);
        }
    }

    private void moveSnake(){//make movement of body and correct orientation
        //body position update
        for(int i = snake.size() - 1; i > 0; i -= 1){
            snake.get(i).setPos(snake.get(i-1).getX(), snake.get(i-1).getY());
        }
        //head position update
        switch(snake.get(0).getCurrent_direction()){//head position update
            case constants.UP: snake.get(0).setY(snake.get(0).getY() - 1); break;

            case constants.DOWN: snake.get(0).setY(snake.get(0).getY() + 1); break;

            case constants.LEFT: snake.get(0).setX(snake.get(0).getX() - 1); break;

            case constants.RIGHT: snake.get(0).setX(snake.get(0).getX() + 1); break;
        }

        for(int i = snake.size() - 2; i > 0; i -= 1){//body orientation update
            int nextX = snake.get(i - 1).getX(), nextY = snake.get(i - 1).getY();
            int prevX = snake.get(i + 1).getX(), prevY = snake.get(i + 1).getY();
            int x = snake.get(i).getX(), y = snake.get(i).getY();

            if(nextX > x){//right
                if(prevX < x){
                    snake.get(i).setDirections(constants.RIGHT, constants.RIGHT);
                }else if(prevY < y){
                    snake.get(i).setDirections(constants.RIGHT, constants.DOWN);
                }else if(prevY > y){
                    snake.get(i).setDirections(constants.RIGHT, constants.UP);
                }
            }else if(nextX < x){//left
                if(prevX > x){
                    snake.get(i).setDirections(constants.LEFT, constants.LEFT);
                }else if(prevY < y){
                    snake.get(i).setDirections(constants.LEFT, constants.DOWN);
                }else if(prevY > y){
                    snake.get(i).setDirections(constants.LEFT, constants.UP);
                }
            }else if(nextY > y){//down
                if(prevX < x){
                    snake.get(i).setDirections(constants.DOWN, constants.RIGHT);
                }else if(prevY < y){
                    snake.get(i).setDirections(constants.DOWN, constants.DOWN);
                }else if(prevX > x){
                    snake.get(i).setDirections(constants.DOWN, constants.LEFT);
                }
            }else if(nextY < y){//up
                if(prevX < x){
                    snake.get(i).setDirections(constants.UP, constants.RIGHT);
                }else if(prevY > y){
                    snake.get(i).setDirections(constants.UP, constants.UP);
                }else if(prevX > x){
                    snake.get(i).setDirections(constants.UP, constants.LEFT);
                }
            }
        }
        //tail orientation
        snake.getLast().setDirections(snake.get(snake.size() - 2).getPrevious_direction(), snake.getLast().getPrevious_direction());
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

    private void eatFood(){
        int x = snake.getFirst().getX(), y = snake.getFirst().getY();
        Iterator<Food> food_iterator = food_items.iterator();//better use iterator as we need to remove while iterating
        while(food_iterator.hasNext()){
            Food f = food_iterator.next();
            if(f.getX() == x && f.getY() == y){
                food_iterator.remove();
                score += 10;
                snakeGrow();
                generateFood();
                eatMunchiesMusic();
                break;
            }
        }
    }

    private void eatPoison(){
        int x = snake.getFirst().getX(), y = snake.getFirst().getY();
        Iterator<Poisons> poison_iterator = poison_items.iterator();//better use iterator as we need to remove while iterating
        while(poison_iterator.hasNext()){
            Poisons p = poison_iterator.next();
            if(p.getX() == x && p.getY() == y){
                gameOver = true;
                break;
            }
        }
    }

    private void snakeGrow(){
        SnakeBody tail = snake.getLast();
        tail.setType(constants.BODY);

        SnakeBody newTail;

        switch(tail.getCurrent_direction()){
            case constants.UP: newTail = new SnakeBody(constants.TAIL, tail.getX(), tail.getY() - 1); break;

            case constants.DOWN: newTail = new SnakeBody(constants.TAIL, tail.getX(), tail.getY() + 1); break;

            case constants.LEFT: newTail = new SnakeBody(constants.TAIL, tail.getX() + 1, tail.getY()); break;

            default: newTail = new SnakeBody(constants.TAIL, tail.getX() - 1, tail.getY() - 1); break;
        }

        snake.add(newTail);
    }

    private void drawScore(){
        penGC.setFill(Color.web("#ffffff"));
            penGC.setFont(Font.font("Lucida Sans Unicode", FontWeight.BOLD,30));
            penGC.fillText("Score: "+score, 9*(constants.WIDTH + 200)/12+ 30, 2*constants.HEIGHT/20);
    }

    public void PlayBackgroundMusic(){
        String backGround = "/resources/sfx/backGroundMusic.wav";
        URL resource = getClass().getResource(backGround);//as needs to be in jar file/app so get link to file
        Media music = new Media(resource.toString());
        player= new MediaPlayer(music);
        player.setCycleCount(MediaPlayer.INDEFINITE);//plays indefimitely
        player.play();
    }

    public void PlayGameOverMusic(){
        gameOverMusic = false;
        String gameOverMusic = "/resources/sfx/gameOverMusic.wav";
        URL resource = getClass().getResource(gameOverMusic);//as needs to be in jar file/app so get link to file
        Media music = new Media(resource.toString());
        player.stop();
        player = new MediaPlayer(music);
        player.play();
    }

    public void eatMunchiesMusic(){
        String munchingMusic = "/resources/sfx/munchingMusic.wav";
        URL resource = getClass().getResource(munchingMusic);//as needs to be in jar file/app so get link to file
        Media music = new Media(resource.toString());
        MediaPlayer p = new MediaPlayer(music);
        p.play();
    }

    public static void main(String[] args) throws Exception {
        launch(SnakeGame.class);
    }
}
// make snake move