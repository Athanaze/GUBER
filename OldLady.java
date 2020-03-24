import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

// The Actor used to represent the old lady in the game
public class OldLady extends Actor {
    static final int N_TILE = 10;
    public static final int TILE_SIZE = 320 / N_TILE;
    static final int TILE_TYPE_CROSSING = 3;

    static final int LIFE_DURATION = 300;

    int timer = 0;
    Position tile_p;

    public OldLady(Position p){
        tile_p = p;

    }
    public void act() {
        timer++;
        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        if (timer > LIFE_DURATION) {
            
            MyWorld myWorld = (MyWorld) getWorld();
            myWorld.setTileType(tile_p, TILE_TYPE_CROSSING);
            image.clear();
            getWorld().removeObject(this);
        }
        setImage(image);
    }
}
