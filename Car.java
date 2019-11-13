import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The car driven by Player 1
 */
public class Car extends Actor
{
    static final int CAR_TURN_RIGHT = 9;
    static final int CAR_FORWARD_SPEED = 5;
    int rotation = 1 ;
    protected void addedToWorld(World MyWorld){
        GreenfootImage image = getImage(); 
        setImage(image);
        image.rotate(-90);
        image.scale(20, 30);
        
        
        
        
        
        
        
    }
    public void act() 
    {
        
       
        

        // For now, autistic drifting moves
        if(Greenfoot.isKeyDown("left")){
            rotation+= CAR_TURN_RIGHT;
            setRotation(rotation);
            
        }
        if(Greenfoot.isKeyDown("right")){
            rotation-= CAR_TURN_RIGHT;
            setRotation(rotation);
        }
        if(Greenfoot.isKeyDown("up")){
           move(CAR_FORWARD_SPEED);
        }
        if (isTouching(OldLady.class) != false){
            getWorld().removeObject(this);
            
            
        }

    }    
}
