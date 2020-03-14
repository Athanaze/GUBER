import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


// A class to contain the image of a "special" building, like a fountain or a helicopter pad
public class Building_image extends Actor
{
    static final int WORLD_X = 800;
    static final int WORLD_Y = 800;
    static final int WORLD_S = 1;

    static final int N_TILE = 40;
    public static final int TILE_SIZE = WORLD_X / N_TILE;

    protected void addedToWorld(World world) {

        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        setImage(image);
        
    }
}
