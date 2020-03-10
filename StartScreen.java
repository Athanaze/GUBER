import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartScreen extends Actor
{   
    public void act() 
    {   MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse != null){if (mouse.getButton() == 1 && mouse.getActor() == this){
                getWorld().removeObject(this);
                }}
    }    
}
