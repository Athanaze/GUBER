import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

// The car driven by Player 1
public class Car extends Actor {
    int x = 0;
    int y = 0;
    static final int WORLD_X = 800;

    static final int N_TILE = 40;
    public static final int TILE_SIZE = WORLD_X / N_TILE;


    static final int CAR_LEFT_ROTATION = 180;
    static final int CAR_RIGHT_ROTATION = 0;
    static final int CAR_DOWN_ROTATION = 90;
    static final int CAR_UP_ROTATION = -90;
    boolean gameOver = false;
    boolean gameOverLady = false;
    boolean move = false;
    int timer = 0;
    int delay = 0;

    protected void addedToWorld(World world) {

        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        setImage(image);
        image.rotate(90);
    }

    public void act() {
        if (delay < timer) {
            move = true;
        }
        // Listening for arrow keys input, and move the car accordingly
        if (isInWorldBoundaries(x - 1)) {
            if (Greenfoot.isKeyDown("left")) {
                if (move) {
                    setRotation(CAR_LEFT_ROTATION);
                    x--;
                    delay = timer + 10;
                    move = false;
                    setLocation(x * TILE_SIZE + (TILE_SIZE / 2), y * TILE_SIZE + (TILE_SIZE / 2));
                }
            }
        }
        if (isInWorldBoundaries(x + 1)) {
            if (Greenfoot.isKeyDown("right")) {

                if (move) {
                    setRotation(CAR_RIGHT_ROTATION);
                    x++;
                    delay = timer + 10;
                    move = false;
                    setLocation(x * TILE_SIZE + (TILE_SIZE / 2), y * TILE_SIZE + (TILE_SIZE / 2));
                }
            }
        }
        if (isInWorldBoundaries(y - 1)) {
            if (Greenfoot.isKeyDown("up")) {
                if (move) {
                    setRotation(CAR_UP_ROTATION);
                    y--;
                    delay = timer + 10;
                    move = false;
                    setLocation(x * TILE_SIZE + (TILE_SIZE / 2), y * TILE_SIZE + (TILE_SIZE / 2));
                }
            }
        }
        if (isInWorldBoundaries(y + 1)) {
            if (Greenfoot.isKeyDown("down")) {
                if (move) {
                    setRotation(CAR_DOWN_ROTATION);
                    y++;
                    delay = timer + 10;
                    move = false;
                    setLocation(x * TILE_SIZE + (TILE_SIZE / 2), y * TILE_SIZE + (TILE_SIZE / 2));
                }
            }
        }
        timer++;
        
        // update visual location (we updated the "logic" location earlier in this
        // method)
        MyWorld myWorld = (MyWorld) getWorld();
        if (myWorld.getObjects(OldLady.class) != null) {
                
         if (isTouching(OldLady.class) && myWorld.gameOverType == myWorld.GAME_OVER_TYPE_KILLED_OLD_LADY) {
            gameOverLady =true;
            gameOver = true;
        }
    }}

    // Check if the car is the world's limit
    private boolean isInWorldBoundaries(int v) {
        boolean r = true;

        if (v < 0) {
            r = false;
        }

        if (v > (N_TILE - 1)) {
            r = false;
        }

        return r;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

}