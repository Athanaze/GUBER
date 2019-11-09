import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class MyWorld extends World
{
    static final int WORLD_X = 2048;
    static final int WORLD_Y = 2048;
    static final int WORLD_S = 1;
   
    public MyWorld()
    {    
        // Create a new world with X by Y cells with a cell size of S pixels.
        super(WORLD_X, WORLD_Y, WORLD_S); 
        
        GreenfootImage pic = new GreenfootImage(WORLD_X, WORLD_Y);
        pic.setColor(new Color(Greenfoot.getRandomNumber(255 + 1), Greenfoot.getRandomNumber(255 + 1), Greenfoot.getRandomNumber(255 + 1)));
        pic.fill();
        setBackground(pic);
        
        Car car = new Car();
        Building building = new Building();
        Road road = new Road();
        Coke coke = new Coke();
        
        addObject(road, 0, 0);
        
        addObject(car, 200, 200);
        
        addObject(building, 500, 200);
        
        addObject(coke, 210, 210);
    }
}
