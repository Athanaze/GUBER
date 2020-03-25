import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


// A class to contain the image of a "special" building, like a fountain or a helicopter pad
public class Building_image extends Actor
{
    static final int N_TILE = 40;
    public static final int TILE_SIZE = 800 / N_TILE;

    protected void addedToWorld(World world) {

        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        setImage(image);
        
    }
}
