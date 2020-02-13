import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo) 

public class MyWorld extends World
{
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
    static final Color TILE_CLIENT_COLOR = new Color(0,0,0);
    static final int N_TYPES = 3;
    // Must be divisible by 2
    static final int ROAD_TO_GRASS_RATIO = 4;
    
    static final int N_CROSSING_BANDS = 4;
    static final int CROSSING_BANDS_WIDTH = TILE_SIZE / (N_CROSSING_BANDS * 2);
    static final int NB_BUILDINGS = 10;
    
    static final int TILE_TYPE_BUILDING = 0;
    static final int TILE_TYPE_VERTICAL = 1;
    static final int TILE_TYPE_HORIZONTAL = 2;
    static final int TILE_TYPE_CROSSING = 3;
    static final int TILE_TYPE_OLD_LADY = 4;
    static final int TILE_TYPE_CAR = 5;
    static final int TILE_TYPE_GRASS = 6;
    static final int TILE_TYPE_INTERSECTION = 7;
    static final int TILE_TYPE_CLIENT = 8;
    
    private Actor clock = new Actor(){};
    private int clockTime = 50;
    private int clockRegulator;
    
    static final boolean GAME_OVER_BOLD = true;
    static final boolean GAME_OVER_ITALIC = false;
    static final int GAME_OVER_FONT_SIZE = TILE_SIZE;
    static final String GAME_OVER_STRING = "GAME OVER \n PLAYER 2 WON !";
    static final Color GAME_OVER_FOREGROUND = new Color(255, 255, 255);
    static final Color GAME_OVER_BACKGROUND = new Color(0, 0, 0);
    static final Color GAME_OVER_OUTLINE = new Color(255, 0, 0);
    
    boolean gameOver = false;
    int[][] tiles = new int[N_TILE][N_TILE];
    Car car;

    // Clients
    static final int N_CLIENTS = 4;
    Client clients[] = new Client[N_CLIENTS];
    // Red, Green, Blue, Pink
    static final Color[] CLIENTS_COLORS = {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255), new Color(255, 0, 255)};
    int droppedOffClients = 0; // When it is == to N_CLIENTS, player 1 wins the game.
    

    public MyWorld()
    {    
        // Create a new world with X by Y cells with a cell size of S pixels.
        super(WORLD_X, WORLD_Y, WORLD_S);
        clock.setImage(new GreenfootImage("Time: "+clockTime, 20, greenfoot.Color.BLACK, greenfoot.Color.WHITE));;
        addObject(clock, 75, 40);
        
        for(int j = 0; j < N_TILE; j++){

            for(int i = 0; i < N_TILE; i++){
                if ((i % 4) == 0 && (j % 4 )== 0){
                    tiles[i][j] = TILE_TYPE_INTERSECTION;
                    drawIntersection(i,j);
                }
                else {if((i % 4) == 0){
                    tiles[i][j] = TILE_TYPE_VERTICAL;
                    drawVerticalRoad(i,j);

                }
                else{if((j % 4 )== 0){
                    tiles[i][j] = TILE_TYPE_HORIZONTAL;
                    drawHorizontalRoad(i, j);
                    }
                    else{
                        tiles[i][j] = TILE_TYPE_GRASS;
                        drawGrass(i,j);
                    }
                }}
                
            }
            
        }
        placeBuildings(NB_BUILDINGS);
        // Place some crossings on the roads
        // For now, we just use some hard-coded positions
        placeCrossing(2, 4);
        placeCrossing(6, 8);
        //placeClient();
        
        /* TODO :
         *  - Use an interface so we dont duplicate all the constants
         *  - Detect the collision with the old lady just by using the tiles array 
         */
        car = new Car();

        /*
         * 2nd and third arguments here really do not matter
         * We could do : addObject(car, 87, 74); and it would work just fine.
         * Here we put 0, 0 so it's "nice" when the game is not yet running.
        
        */
        addObject(car, 0, 0);
    }
    
   public void act(){
       // Player 1
       Position carPosition = car.getPosition();
       
       boolean isCarNextToClient = false;
       
       // Check if the car position is valid, if it's not, game over => player 2 win the game
       switch(tiles[carPosition.x][carPosition.y]){
           case TILE_TYPE_VERTICAL:
            try{if(tiles[carPosition.x+1][carPosition.y] == TILE_TYPE_CLIENT || tiles[carPosition.x-1][carPosition.y] == TILE_TYPE_CLIENT){
                isCarNextToClient = true;
            }
            }
            // Just here to catch "out of bound" exeptions
            catch(Exception e){}
            break;
           
           case TILE_TYPE_HORIZONTAL:
               try{
                   if(tiles[carPosition.x][carPosition.y+1] == TILE_TYPE_CLIENT || tiles[carPosition.x][carPosition.y-1] == TILE_TYPE_CLIENT){
                    isCarNextToClient = true;
                }
               }
               catch(Exception e){}
            break;
            
           case TILE_TYPE_INTERSECTION:
            break;
            
           case TILE_TYPE_CROSSING:
            gameOver = false;
            break;
            
           case TILE_TYPE_BUILDING:
            gameOver = true;
            
           case TILE_TYPE_GRASS:
            gameOver = true;
            
           case TILE_TYPE_OLD_LADY:
            gameOver = true;
            break;
       }
       
        // Player 2
         MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse!=null){  
                if(mouse.getButton() == 1 && Greenfoot.mouseClicked(null)) 
                {
                    Position tilePosition = getTilePosition(mouse.getX(), mouse.getY());
                    
                    // Check if the clicked tile is a crossing, and spawn an old lady if this is the case
                    if(tiles[tilePosition.x][tilePosition.y] == TILE_TYPE_CROSSING){
                        tiles[tilePosition.x][tilePosition.y] = TILE_TYPE_OLD_LADY;
                        addObject(new OldLady(),tilePosition.x*TILE_SIZE, tilePosition.y*TILE_SIZE);
                    }
                    
                }
            }
       //update clock    
       runClock();
       
       if(gameOver){
           System.out.println("Game over");
           removeObject(car);
           drawGameOver();
       }
       
       if(isCarNextToClient){
           System.out.println("Next to a client");
       }
       
    }
    
   
    
    // Returns the tile position in the global array from the x and y pixel coordinates
    private Position getTilePosition(int x, int y){
        return new Position(getOneTilePosition(x), getOneTilePosition(y));
    }
   
    // Used in getTilePosition
    private int getOneTilePosition(int v){
        if((v % TILE_SIZE) != 0){
            return (v/ TILE_SIZE);
        }else{
            return (v / TILE_SIZE) - 1;
        }
    }
     //used upon world construction
    private void placeBuildings(int n){
        int clientDestinationCounter = 0;
        while(n >0){
            int x = Greenfoot.getRandomNumber(N_TILE-1);
            int y = Greenfoot.getRandomNumber(N_TILE-1);
            
            
            if((tiles[x][y] == TILE_TYPE_GRASS)){
                // The first 4 buildings are set as destination
                if(n < N_CLIENTS){
                    tiles[x][y] = TILE_TYPE_CLIENT;
                    
                    drawDestination(x,y, n);
                }
                else{
                    tiles[x][y] = TILE_TYPE_BUILDING;
                    drawBuilding(x, y);
                }
                
                n--;
            };
        }
        
        
    }
    // Stuff being drawn on the screen
    
    private void drawGameOver(){
        GreenfootImage image = getBackground();
        
        // First, fill the entire screen with a rectangle
        image.setColor(GAME_OVER_BACKGROUND);
        image.fillRect(0, 0, WORLD_X, WORLD_Y);
        
        GreenfootImage g = new GreenfootImage(
            GAME_OVER_STRING,
            GAME_OVER_FONT_SIZE,
            GAME_OVER_FOREGROUND,
            GAME_OVER_BACKGROUND,
            GAME_OVER_OUTLINE
        );
        
        // Draw the text at the center of the screen
        image.drawImage(g, (WORLD_X - g.getWidth()) / 2, (WORLD_Y - g.getHeight()) / 2);
    }
    
    private void drawVerticalRoad(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;
        GreenfootImage image = getBackground();

        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        // Draw the road on top of the grass
        image.setColor(TILE_ROAD_COLOR);

        image.fillRect(x+(TILE_SIZE/ROAD_TO_GRASS_RATIO), y, TILE_SIZE/(ROAD_TO_GRASS_RATIO/ 2), TILE_SIZE);
    }
    

    private void drawHorizontalRoad(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        // Draw the road on top of the grass
        image.setColor(TILE_ROAD_COLOR);

        image.fillRect(x, y+(TILE_SIZE/ROAD_TO_GRASS_RATIO), TILE_SIZE, TILE_SIZE/(ROAD_TO_GRASS_RATIO/2));
    }
    
    private void drawIntersection(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;
        
        GreenfootImage image = getBackground();
        
        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        
        image.setColor(TILE_ROAD_COLOR);
        
        // Draw the road on top of the grass
        image.fillRect(x, y+(TILE_SIZE/ROAD_TO_GRASS_RATIO), TILE_SIZE, TILE_SIZE/(ROAD_TO_GRASS_RATIO/2));
        image.fillRect(x+(TILE_SIZE/ROAD_TO_GRASS_RATIO), y, TILE_SIZE/(ROAD_TO_GRASS_RATIO/ 2), TILE_SIZE);
    }
    
    private void drawBuilding(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_BUILDING_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    private void drawDestination(int i, int j, int colorIndex){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(CLIENTS_COLORS[colorIndex]);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }
    
    private void drawGrass(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }
    
    
    private void drawCrossing(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;

        GreenfootImage image = getBackground();
        image.setColor(TILE_ROAD_COLOR);
        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        
        // Add bands on top
        image.setColor(TILE_CROSSING_COLOR);
        boolean crossing_b = true;
        for(int p = 0; p < TILE_SIZE; p+=CROSSING_BANDS_WIDTH){
            if(crossing_b){
                 image.fillRect(x, y+p, TILE_SIZE, CROSSING_BANDS_WIDTH);
                 crossing_b = false;
            }
            else{
                crossing_b = true;
            }
        }
        
    }
    
    private void drawCar(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;
        tiles[i][j] = TILE_TYPE_CAR;
        GreenfootImage image = getBackground();
        GreenfootImage sprite = new GreenfootImage("car.png");
        sprite.scale(TILE_SIZE, TILE_SIZE);
        image.drawImage(sprite, x, y);
        
    }
    
    private void placeCrossing(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;
        
        tiles[i][j] = TILE_TYPE_CROSSING;
        drawCrossing(i, j);
    }
    
    private void runClock(){
        clockRegulator = (clockRegulator+1)%70;
        if (clockRegulator == 0){
            clockTime--;
            clock.setImage(new GreenfootImage("Time: "+clockTime, 20, greenfoot.Color.BLACK, greenfoot.Color.WHITE));
            if(clockTime == 0){gameOver = true;}
        }
    }
    
    /*
     * Spawn the 4 clients in an existing building, somewhere on the map
     * Choose another building as the destination for each client
     */
    /*
    private void setupClients(){
        for(int i = 0; i <4; i++){
            
                        while(true){ 
             int x = Greenfoot.getRandomNumber(N_TILE-1);
             int y = Greenfoot.getRandomNumber(N_TILE-1);
             if(tiles[x][y] == TILE_TYPE_BUILDING){
                 tiles[x][y] = TILE_TYPE_CLIENT;
                  addObject(new Client(),x*TILE_SIZE, y*TILE_SIZE);
                 break;
                }
           
        }
            
            clients[i].setLocation(tileX*TILE_SIZE + (TILE_SIZE/2), tileY*TILE_SIZE + (TILE_SIZE/2));
        }
    }*/
}
