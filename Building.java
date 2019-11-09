import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Building extends Actor
{

    public void act() 
    {
        // Make the car a correct size
        GreenfootImage image = getImage();   
        image.scale(300, 200);
        setImage(image);
    }    
}
