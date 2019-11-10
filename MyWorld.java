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

    public MyWorld()
    {    
        // Create a new world with X by Y cells with a cell size of S pixels.
        super(WORLD_X, WORLD_Y, WORLD_S); 
        
        int[][] tiles = new int[N_TILE][N_TILE];

        for(int j = 0; j < N_TILE; j++){
            
            for(int i = 0; i < N_TILE; i++){
                
                if((i % 4) == 0){
                    drawVerticalRoad(i,j);

                }
                else{if((j % 4 )== 0){
                    drawHorizontalRoad(i, j);
                }
                else{
                    drawBuilding(i,j);
                }
                }
            }
            
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
