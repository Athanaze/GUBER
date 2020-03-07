import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class OldLady extends Actor {
    static final int WORLD_X = 320;
    static final int WORLD_Y = 320;
    static final int WORLD_S = 1;

    static final int N_TILE = 10;
    public static final int TILE_SIZE = WORLD_X / N_TILE;

    static final int ROAD_BLACKNESS = 128;
    static final int N_TYPES = 3;
    // Must be divisible by 2
    static final int ROAD_TO_GRASS_RATIO = 4;

    static final int N_CROSSING_BANDS = 4;
    static final int CROSSING_BANDS_WIDTH = TILE_SIZE / (N_CROSSING_BANDS * 2);

    static final int TILE_TYPE_BUILDING = 0;
    static final int TILE_TYPE_VERTICAL = 1;
    static final int TILE_TYPE_HORIZONTAL = 2;
    static final int TILE_TYPE_CROSSING = 3;
    static final int TILE_TYPE_OLD_LADY = 4;
    static final int TILE_TYPE_CAR = 5;

    // value still to be adjusted
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
