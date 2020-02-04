import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

// The car driven by Player 1
public class Car extends Actor{
    int x = 0;
    int y = 0;
    
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

    static final int CAR_LEFT_ROTATION = 180;
    static final int CAR_RIGHT_ROTATION = 0;
    static final int CAR_DOWN_ROTATION = 90;
    static final int CAR_UP_ROTATION = -90;

    protected void addedToWorld(World world){

        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        setImage(image);
        image.rotate(90);


    }
    
    public void act(){
       String k  = Greenfoot.getKey();
       
       if(k == null){
        k  = "no_key"; // Just a placeholder so we dont get an null pointer exeption
       }
       
       switch(k){
           case "left":
            setRotation(CAR_LEFT_ROTATION);
            if(isInWorldBoundaries(x - 1)){
                x--;
            }
            break;
            
           case "right":
            setRotation(CAR_RIGHT_ROTATION);
            if(isInWorldBoundaries(x + 1)){
                x++;
            }
            break;
            
           case "up":
            setRotation(CAR_UP_ROTATION);
            if(isInWorldBoundaries(y - 1)){
                y--;
            }
            break;
            
           case "down":
            setRotation(CAR_DOWN_ROTATION);
            if(isInWorldBoundaries(y + 1)){
                y++;
            }
        }
        
        // update visual location (we updated the "logic" location earlier in this method) 
        setLocation(x*TILE_SIZE + (TILE_SIZE/2), y*TILE_SIZE + (TILE_SIZE/2));
    }
    
    // Check if the car is the world's limit
    private boolean isInWorldBoundaries(int v){
        boolean r = true;
        
        if(v < 0){
            r = false;
        }
        
        if(v > (N_TILE - 1)){
            r = false;
        }
        
        return r;
    }
    
    public Position getPosition(){
        return new Position(x,y);
    }

}