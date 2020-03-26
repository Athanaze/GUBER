import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
// The Actor used to represent the client in the game
// This class also contains the animation for the client entering the car

public class Client extends Actor {
    static final int WORLD_X = 800;
    static final int WORLD_Y = 800;

    static final int N_TILE = 40;
    public static final int TILE_SIZE = WORLD_X / N_TILE;

    // Used to do the animation
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
        //movement to get in the car
        if (moveup) {
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
        if (moveright) {
            if (!cars.isEmpty()) {
                Actor Car = (Actor) cars.get(0);
                int carx = Car.getX();
                int cary = Car.getY();
                if (positionx == carx) {

                    image.clear();
                    setImage(image);
                    moveright = false;
                }
                if (positionx - carx < 0) {
                    setLocation(positionx + 1, positiony);
                } else {
                    setLocation(positionx - 1, positiony);
                }

                setImage(image);
            }
        }
        //movement once he gets out of the car
        if (dropped) {
            int destinationX = ((MyWorld) getWorld()).destinationX;
            int destinationY = ((MyWorld) getWorld()).destinationY;
            //check if he's at destination
            if (positionx == destinationX && positiony == destinationY) {
                image.clear();
                setImage(image);
                dropped = false;
            }
            if (positionx == destinationX) {
            } else if (destinationX - positionx < 0) {

                setLocation(positionx - 1, positiony);
            } else {
                setLocation(positionx + 1, positiony);
            }
            if (positiony == destinationY) {
            } else if (destinationY - positiony < 0) {

                setLocation(positionx, positiony - 1);
            } else {
                setLocation(positionx, positiony + 1);
            }
        }
    }

    public void getInTheCar() {
        Greenfoot.playSound("hop.wav");
        //gets the car position to know in which direction to go
     
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
        if(((MyWorld) getWorld()).droppedOffClients < 3){
        Greenfoot.playSound("success.wav");}
        setColor(color);
        List cars = getWorld().getObjects(Car.class);

        if (!cars.isEmpty()) {
            Actor Car = (Actor) cars.get(0);
            int carx = Car.getX();
            int cary = Car.getY();
            if (this.getX() != carx || this.getY() != cary) {
                setLocation(carx, cary);
                dropped = true;
            }
        }
    }

    public void setColor(int colorIndex) {
        setImage("client" + Integer.toString(colorIndex) + ".png");
        color = colorIndex;

    }
}
