import greenfoot.*;

// Non Player Character : car that drives in a straight line. If player 1 hits it, player 2 wins the game
public class CarNpc extends Actor {
    static final int WORLD_X = 800;
    static final int WORLD_Y = 800;

    static final int N_TILE = 40;
    public static final int TILE_SIZE = WORLD_X / N_TILE;

    static final int CAR_LEFT_ROTATION = 180;
    static final int CAR_RIGHT_ROTATION = 0;
    static final int CAR_DOWN_ROTATION = 90;
    static final int CAR_UP_ROTATION = -90;
    static final int MAX_ADDED_DELAY = 20;

    boolean player1Collision = false;

    boolean move = true;
    int timer = 0;
    int delay = 0;

    Position startPosition = new Position(0,0);

    protected void addedToWorld(World world) {

        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        setImage(image);
        image.rotate(90);
    }

    public void setImageProperly(int npcNumber){
        setImage("npc_"+Integer.toString(npcNumber)+".png");
        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        image.rotate(90);
    }

    public void act() {

        if (delay < timer) {
            move = true;
            setRotation(CAR_DOWN_ROTATION);
            delay = timer + (Greenfoot.getRandomNumber(MAX_ADDED_DELAY));
            move = false;
            int newY = getY() + (TILE_SIZE / 2);
            if(newY < WORLD_Y){
                setLocation(getX(), newY);
            }
            else{
                setLocation(startPosition.x, startPosition.y);
            }
        
        }
        timer++;
        player1Collision = isTouching(Car.class);
        removeTouching(OldLady.class);

    }
}
