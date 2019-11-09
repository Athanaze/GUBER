import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The car driven by Player 1
 */
public class Car extends Actor
{
    static final int CAR_TURN_RATE = 3;
    static final int CAR_FORWARD_SPEED = 5;
    public void act() 
    {
        
        // Make the car a correct size
        GreenfootImage image = getImage();   
        image.scale(30, 50);
        setImage(image);

        // For now, autistic drifting moves
        if(Greenfoot.isKeyDown("left")){
            turn(CAR_TURN_RATE);
        }
        if(Greenfoot.isKeyDown("right")){
            turn(-CAR_TURN_RATE);
        }
        if(Greenfoot.isKeyDown("up")){
           move(CAR_FORWARD_SPEED);
        }

    }    
}
