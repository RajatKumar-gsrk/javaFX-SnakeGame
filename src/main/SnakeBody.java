package main;
import main.constants;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SnakeBody {

    private int type;
    private int posX, posY;

    private int current_direction = constants.RIGHT;
    private int previous_direction = constants.RIGHT;

    public SnakeBody(int type, int x, int y){//java doesn;t have default parameters
        setType(type);
        setPos(x, y);
    }

    public void setPos(int x, int y){
        setX(x);
        setY(y);
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return this.type;
    }

    public void setX(int x){
        this.posX = x*constants.SQUARE_SIZE;
    }

    public int getX(){
        return this.posX/constants.SQUARE_SIZE;
    }

    public void setY(int y){
        this.posY = y*constants.SQUARE_SIZE;
    }

    public int getY(){
        return this.posY/constants.SQUARE_SIZE;
    }

    private void setPevious_direction(int direction){
        this.previous_direction = direction;
    }

    public int getPrevious_direction(){
        return this.previous_direction;
    }

    private void setCurrent_direction(int direction){
        this.current_direction = direction;
    }

    public int getCurrent_direction(){
        return this.current_direction;
    }

    public void setDirections(int cd, int pd){
        setCurrent_direction(cd);
        setPevious_direction(pd);
    }

    public void drawPart(GraphicsContext penGC){
        if(this.type == constants.HEAD){
            drawHead(penGC);
        }else if (this.type == constants.BODY){
            drawBody(penGC);
        }else if(this.type == constants.TAIL){
            drawTail(penGC);
        }
    }

    private void drawHead(GraphicsContext penGC){
        if(current_direction == constants.UP){
            penGC.drawImage(new Image("resources/snake/head_up.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.DOWN){
            penGC.drawImage(new Image("resources/snake/head_down.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.RIGHT){
            penGC.drawImage(new Image("resources/snake/head_right.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.LEFT){
            penGC.drawImage(new Image("resources/snake/head_left.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }
    }

    private void drawBody(GraphicsContext penGC){
        if(current_direction == constants.UP && previous_direction == constants.UP){
            penGC.drawImage(new Image("resources/snake/body_up.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.UP && previous_direction == constants.RIGHT){
            penGC.drawImage(new Image("resources/snake/body_right_up.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.UP && previous_direction == constants.LEFT){
            penGC.drawImage(new Image("resources/snake/body_left_up.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.DOWN && previous_direction == constants.RIGHT){
            penGC.drawImage(new Image("resources/snake/body_right_down.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.DOWN && previous_direction == constants.LEFT){
            penGC.drawImage(new Image("resources/snake/body_left_down.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.DOWN && previous_direction == constants.DOWN){
            penGC.drawImage(new Image("resources/snake/body_down.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.RIGHT && previous_direction == constants.RIGHT){
            penGC.drawImage(new Image("resources/snake/body_right.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.RIGHT && previous_direction == constants.UP){
            penGC.drawImage(new Image("resources/snake/body_up_right.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.RIGHT && previous_direction == constants.DOWN){
            penGC.drawImage(new Image("resources/snake/body_down_right.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.LEFT && previous_direction == constants.LEFT){
            penGC.drawImage(new Image("resources/snake/body_left.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.LEFT && previous_direction == constants.DOWN){
            penGC.drawImage(new Image("resources/snake/body_down_left.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.LEFT && previous_direction == constants.UP){
            penGC.drawImage(new Image("resources/snake/body_up_left.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }
    }

    private void drawTail(GraphicsContext penGC){
        if(current_direction == constants.UP){
            penGC.drawImage(new Image("resources/snake/tail_up.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.DOWN){
            penGC.drawImage(new Image("resources/snake/tail_down.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.RIGHT){
            penGC.drawImage(new Image("resources/snake/tail_right.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }else if(current_direction == constants.LEFT){
            penGC.drawImage(new Image("resources/snake/tail_left.png"), posX, posY, constants.SQUARE_SIZE, constants.SQUARE_SIZE);
        }
    }

    //implements findDirection/update directions

}
