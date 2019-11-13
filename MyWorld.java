import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo) 

public class MyWorld extends World
{
    static final int WORLD_X = 320;
    static final int WORLD_Y = 320;
    static final int WORLD_S = 1;

    static final int N_TILE = 10;
    static final int TILE_SIZE = WORLD_X / N_TILE;

    static final int ROAD_BLACKNESS = 128;

    static final Color TILE_ROAD_COLOR = new Color(ROAD_BLACKNESS, ROAD_BLACKNESS, ROAD_BLACKNESS);
    static final Color TILE_GRASS_COLOR = new Color(10, 255, 0);
    static final Color TILE_BUILDING_COLOR = new Color(255, 204, 102);

    static final int N_TYPES = 3;
    // Must be divisible by 2
    static final int ROAD_TO_GRASS_RATIO = 4;

    static final int TILE_TYPE_BUILDING = 0;
    static final int TILE_TYPE_VERTICAL = 1;
    static final int TILE_TYPE_HORIZONTAL = 2;
    static final int TILE_TYPE_CROSSING = 3;
    static final int TILE_TYPE_OLD_LADY = 4;
    
    boolean gameOver = false;
    int[][] tiles = new int[N_TILE][N_TILE];
    public MyWorld()
    {    
        // Create a new world with X by Y cells with a cell size of S pixels.
        super(WORLD_X, WORLD_Y, WORLD_S); 


        for(int j = 0; j < N_TILE; j++){

            for(int i = 0; i < N_TILE; i++){

                if((i % 4) == 0){
                    tiles[i][j] = TILE_TYPE_VERTICAL;
                    drawVerticalRoad(i,j);

                }
                else{if((j % 4 )== 0){
                    tiles[i][j] = TILE_TYPE_HORIZONTAL;
                    drawHorizontalRoad(i, j);
                    }
                    else{
                        tiles[i][j] = TILE_TYPE_BUILDING;
                        drawBuilding(i,j);
                    }
                }
            }
        }
    }
    
   public void act(){
         MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse!=null){  
                if(mouse.getButton() == 1 && Greenfoot.mouseClicked(null)) 
                {
                    Position tilePosition = getTilePosition(mouse.getX(), mouse.getY());
                    
                    // Check if the clicked tile is a passage pieton, so we can start spawning some Vieille
                    // For now quick and dirty => just vertical roads
                    if(tiles[tilePosition.x][tilePosition.y] == TILE_TYPE_VERTICAL){
                        System.out.println("VERTICAL ROAD");
                        tiles[tilePosition.x][tilePosition.y] = TILE_TYPE_OLD_LADY;
                        
                        addObject(new OldLady(),tilePosition.x*TILE_SIZE, tilePosition.y*TILE_SIZE);
                    }
                    
                }
            }
            if(Greenfoot.isKeyDown("q")){
                gameOver = true;
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

    private void drawBuilding(int i, int j){
        int x = i*TILE_SIZE;
        int y = j*TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_BUILDING_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }
}
