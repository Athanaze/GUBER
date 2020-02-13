import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class OldLady extends Actor
{
    static final int WORLD_X = 320;
    static final int WORLD_Y = 320;
    static final int WORLD_S = 1;

    static final int N_TILE = 10;
    public static final int TILE_SIZE = WORLD_X / N_TILE;

    static final int ROAD_BLACKNESS = 128;

    static final Color TILE_ROAD_COLOR = new Color(ROAD_BLACKNESS, ROAD_BLACKNESS, ROAD_BLACKNESS);
    static final Color TILE_GRASS_COLOR = new Color(10, 255, 0);
    static final Color TILE_BUILDING_COLOR = new Color(255, 204, 102);
    static final Color TILE_CROSSING_COLOR = new Color(255, 255, 0);
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
    
    public void act() 
    {
        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        setImage(image);
    }    
}
