import greenfoot.*;

// Non Player Character : car that drives in a straight line. If player 1 hits it, player 2 wins the game
public class CarNpc extends Actor {
    static final int WORLD_X = 800;
    static final int WORLD_Y = 800;

    static final int N_TILE = 40;
    public static final int TILE_SIZE = WORLD_X / N_TILE;
    static final int TILE_TYPE_CROSSING = 3;

    static final int CAR_LEFT_ROTATION = 180;
    static final int CAR_RIGHT_ROTATION = 0;
    static final int CAR_DOWN_ROTATION = 90;
    static final int CAR_UP_ROTATION = -90;
    static final int MAX_ADDED_DELAY = 20;
    static final int PLAYER_2_MAX_AMMO = 5;
    static final int TILE_TYPE_OLD_LADY = 4;


    boolean player1Collision = false;

    boolean move = true;
    int timer = 0;
    int delay = 0;

    Position startPosition = new Position(0,0);

    protected void addedToWorld(World world) {

        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        setImage(image);
        image.rotate(90);
    }

    public void setImageProperly(int npcNumber){
        setImage("npc_"+Integer.toString(npcNumber)+".png");
        GreenfootImage image = getImage();
        image.scale(TILE_SIZE, TILE_SIZE);
        image.rotate(90);
    }

    public void act() {
        // The Npc car accelerates randomly, to give it a more organic movement
        if (delay < timer) {
            move = true;
            setRotation(CAR_DOWN_ROTATION);
            delay = timer + (Greenfoot.getRandomNumber(MAX_ADDED_DELAY));
            move = false;
            int newY = getY() + (TILE_SIZE / 2);
            if(newY < WORLD_Y){
                setLocation(getX(), newY);
            }
            else{
                setLocation(startPosition.x, startPosition.y);
            }
        
        }
        timer++;
        player1Collision = isTouching(Car.class);
        if(isTouching(OldLady.class)){
            MyWorld myWorld = (MyWorld) getWorld();
            Position tilePosition = myWorld.getTilePosition(getX(), getY());
            removeTouching(OldLady.class);
            int x = tilePosition.x;
            int y = tilePosition.y;

            /*
                Since the npc car is moving between tiles "smoothly", sometimes getTilePosition() returns us innacurate
                results. So we check three cases : the tile returned by getTilePosition, one above it and one under it.
            */
            boolean successCrossing = false;
            if(myWorld.tiles[x][y] == TILE_TYPE_OLD_LADY){
                myWorld.tiles[x][y] = TILE_TYPE_CROSSING;
                successCrossing = true;
            }
            else{
                
                if(myWorld.tiles[x][y+1] == TILE_TYPE_OLD_LADY){
                    myWorld.tiles[x][y+1] = TILE_TYPE_CROSSING;
                    successCrossing = true;
                }
                else{
                    if(myWorld.tiles[x][y-1] == TILE_TYPE_OLD_LADY){
                        myWorld.tiles[x][y-1] = TILE_TYPE_CROSSING;
                        successCrossing = true;
                    }  
                }
            }
            // This part should not be necessary... Looks like sometimes the CarNpc is simply detecting the collision too early
            // When the car is not even touching the old lady
            // If we still did not manage to find a TILE_TYPE_OLD_LADY, try at + and - 2
            if(!successCrossing){
                if(myWorld.tiles[x][y+2] == TILE_TYPE_OLD_LADY){
                    myWorld.tiles[x][y+2] = TILE_TYPE_CROSSING;
                    successCrossing = true;
                }
                else{
                    if(myWorld.tiles[x][y-2] == TILE_TYPE_OLD_LADY){
                        myWorld.tiles[x][y-2] = TILE_TYPE_CROSSING;
                        successCrossing = true;
                    }
                }
            }

            System.out.println(successCrossing);

            // When an old lady is killed by a npc car, give 1 ammo to player 2
            if (myWorld.player2Ammo < PLAYER_2_MAX_AMMO) {
                myWorld.player2Ammo++;
            }
        }

    }
}
