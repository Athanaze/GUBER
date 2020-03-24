import greenfoot.*;

// The class used to display the background image and launch the game
public class StartScreen extends Actor {
    public void act() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            if (mouse.getButton() == 1 && mouse.getActor() == this) {
                getWorld().removeObject(this);
            }
        }
    }
}
