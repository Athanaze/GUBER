 import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Client extends Actor {
    static final int WORLD_X = 800;
    static final int WORLD_Y = 800;
    static final int WORLD_S = 1;

    static final int N_TILE = 40;
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
    boolean moveright = false;
    
    boolean moveup = false;
    
    boolean moveupbuild;
    boolean moverightbuild;
    
    int color;
    
    boolean dropped = false;
    public void act() {
        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        List cars = getWorld().getObjects(Car.class);
        int positionx = getX();
        int positiony = getY();
        
        if (moveup == true) {
            if (!cars.isEmpty()) {
                Actor Car = (Actor) cars.get(0);
                int cary = Car.getY();
                if (this.getY() == cary) {
                    image.clear();
                    setImage(image);
                    moveup = false;
                }
                if (this.getY() - cary < 0) {
                    setLocation(positionx, positiony + 1);
                } else {
                    setLocation(positionx, positiony - 1);
                }

                setImage(image);
            }
        }
        if (moveright == true) {
            if (!cars.isEmpty()) {
                Actor Car = (Actor) cars.get(0);
                int carx = Car.getX();
                int cary = Car.getY();
                if (this.getX() == carx) {

                    image.clear();
                    setImage(image);
                    moveright = false;
                }
                if (this.getX() - carx < 0) {
                    setLocation(positionx + 1, positiony);
                } else {
                    setLocation(positionx - 1, positiony);
                }

                setImage(image);
            }
        }
        if(dropped == true){
            Position destination = ((MyWorld) getWorld()).destination;

            if (moverightbuild) {
                if(positionx == destination.x){image.clear();
                
                moverightbuild = false;
                dropped = false;
            }
            else if(positionx - destination.x <0){
                    setLocation(positionx +1, positiony);
                } else {setLocation(positionx-1, positiony);
                }}
            if (moveupbuild){
                if(positiony == destination.y){
                moveupbuild = false;
                dropped = false;
                }    
                else if(positiony - destination.y < 0){
                    setLocation(positionx, positiony+1);}
                    else{setLocation(positionx, positiony -1);}
            }   
            }
    }

    public void getInTheCar() {
        // TODO : fancy animation
        // For now, we just make him invisible right away

        Greenfoot.playSound("hop.wav");
        List cars = getWorld().getObjects(Car.class);
        if (!cars.isEmpty()) {
            Actor Car = (Actor) cars.get(0);
            int carx = Car.getX();
            int cary = Car.getY();
            if (this.getX() - carx < 0) {
                moveright = true;
            }

            if (this.getY() - cary < 0) {
                moveup = true;
            }

        }

    }

    // Called when the car is at the right destination
    public void dropOff(int tileX, int tileY) {
        Greenfoot.playSound("success.wav");
        setColor(color);
        List cars = getWorld().getObjects(Car.class);
        Position destination = ((MyWorld) getWorld()).destination;
        
        
        if (!cars.isEmpty()) {
            Actor Car = (Actor) cars.get(0);
            int carx = Car.getX();
            int cary = Car.getY();
            while(this.getX() != carx || this.getY() != cary){
             setLocation(carx , cary);
             dropped = true;
            }
            if(this.getX() - destination.x <0 ){
                moverightbuild = true;
            }
            if(this.getY() - destination.y <0){
                moveupbuild = true;
            }
        }
        
    }


    public void setColor(int colorIndex) {
        setImage("client" + Integer.toString(colorIndex) + ".png");
        color = colorIndex;

    }
}
