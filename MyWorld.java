import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo) 
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.io.FileWriter;

public class MyWorld extends World {
    static final int WORLD_X = 800;
    static final int WORLD_Y = 800;
    static final int WORLD_S = 1;

    static final int N_TILE = 40;
    public static final int TILE_SIZE = WORLD_X / N_TILE;

    static final int ROAD_BLACKNESS = 128;
    static final int DARK_GRAY = 100;
    static final int LIGHT_GRAY = 150;
    static final int BUILDING_BORDER_SIZE = 2;
    static final int GRAY_VARIATION = 70;

    static final Color TILE_ROAD_COLOR = new Color(ROAD_BLACKNESS, ROAD_BLACKNESS, ROAD_BLACKNESS);
    static final Color TILE_GRASS_COLOR = new Color(10, 150, 0);
    static final Color TILE_BUILDING_COLOR = new Color(255, 204, 102);
    static final Color TILE_BUILDING_COLOR_DARK_GRAY = new Color(DARK_GRAY, DARK_GRAY, DARK_GRAY);
    static final Color TILE_CROSSING_COLOR = new Color(255, 255, 0);
    static final Color TILE_CLIENT_COLOR = new Color(0, 0, 0);
    static final int N_TYPES = 3;
    // Must be divisible by 2
    static final int ROAD_TO_GRASS_RATIO = 4;

    static final int N_CROSSING_BANDS = 4;
    static final int CROSSING_BANDS_WIDTH = TILE_SIZE / (N_CROSSING_BANDS * 2);
    static final int NB_BUILDINGS = 500;
    static final int NB_CROSSINGS = 20;
    static final int BUILDING_IMAGE_OFFSET = 10;

    static final int TILE_TYPE_BUILDING = 0;
    static final int TILE_TYPE_VERTICAL = 1;
    static final int TILE_TYPE_HORIZONTAL = 2;
    static final int TILE_TYPE_CROSSING = 3;
    static final int TILE_TYPE_OLD_LADY = 4;
    static final int TILE_TYPE_CAR = 5;
    static final int TILE_TYPE_GRASS = 6;
    static final int TILE_TYPE_INTERSECTION = 7;

    static final int TILE_TYPE_CLIENT_0 = 8;
    static final int TILE_TYPE_CLIENT_1 = 9;
    static final int TILE_TYPE_CLIENT_2 = 10;
    static final int TILE_TYPE_CLIENT_3 = 11;
    static final int[] TILE_TYPE_CLIENTS = { TILE_TYPE_CLIENT_0, TILE_TYPE_CLIENT_1, TILE_TYPE_CLIENT_2,
            TILE_TYPE_CLIENT_3 };

    static final int TILE_TYPE_CLIENT_0_DESTINATION = 12;
    static final int TILE_TYPE_CLIENT_1_DESTINATION = 13;
    static final int TILE_TYPE_CLIENT_2_DESTINATION = 14;
    static final int TILE_TYPE_CLIENT_3_DESTINATION = 15;

    static final int[] TILE_TYPE_CLIENT_DESTINATIONS = { TILE_TYPE_CLIENT_0_DESTINATION, TILE_TYPE_CLIENT_1_DESTINATION,
            TILE_TYPE_CLIENT_2_DESTINATION, TILE_TYPE_CLIENT_3_DESTINATION };

    private Actor clock = new Actor() {
    };
    private int clockTime = 60;
    private int clockRegulator;

    private Actor clientScore = new Actor() {
    };
    private Actor player2_Ammo = new Actor() {
    };
    static final int FONT_SIZE = 20;
    static final int CLOCK_X = 75;
    static final int CLOCK_Y = 5;

    static final int CLIENT_SCORE_X = 240;
    static final int CLIENT_SCORE_Y = 5;

    static final int PLAYER2_AMMO_X = 370;
    static final int PLAYER2_AMMO_Y = 5;

    static final boolean GAME_OVER_BOLD = true;
    static final boolean GAME_OVER_ITALIC = false;
    static final int GAME_OVER_FONT_SIZE = TILE_SIZE;
    static final String GAME_OVER_STRING = "GAME OVER \n PLAYER 2 WON !";
    static final String GAME_OVER_STRING2 = "GAME OVER \n PLAYER 1 WON !";
    static final Color GAME_OVER_FOREGROUND = new Color(255, 255, 255);
    static final Color GAME_OVER_BACKGROUND = new Color(0, 0, 0);
    static final Color GAME_OVER_OUTLINE = new Color(255, 0, 0);

    static final int PLAYER2_MAX_AMMO = 5;
    int player2Ammo = 5;
    // should be the same as oldlady life duration
    

    private Actor statsActor = new Actor() {
    };
    static final int STATS_X = 400;
    static final int STATS_Y = 200;
    static final String STATS_FILE = "stats.guber";

    static final int SOUND_DELAY = 1000;

    boolean gameOver = false;
    int gameOverType;
    static final int GAME_OVER_TYPE_CRASH = 0;
    static final int GAME_OVER_TYPE_KILLED_OLD_LADY = 1;
    static final int GAME_OVER_TYPE_OUT_OF_TIME = 2;
    static final int GAME_OVER_TYPE_PLAYER_1_WON = 3;
    boolean playGameOver = false;
    boolean soundHasPlayed = false;

    int[][] tiles = new int[N_TILE][N_TILE];
    Car car;

    // Clients
    static final int N_CLIENTS = 4;
    Client clients[] = new Client[N_CLIENTS];
    // Red, Green, Blue, Pink
    static final Color[] CLIENTS_COLORS = { new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255),
            new Color(255, 0, 255) };
    int droppedOffClients = 0; // When it is == to N_CLIENTS, player 1 wins the game.
    int clientInTheCar = -1; // -1 when there is nobody in the car, otherwise is equal to the number of the
    // client (0, 1, 2 or 3)
    int buildingX;
    int buildingY;
    int destinationX;
    int destinationY;
    boolean drawDisplay = false;

    // NPC CARS
    private CarNpc[] npcCars = {new CarNpc(), new CarNpc(), new CarNpc(), new CarNpc()}; 
    // The number of npc cars existing at once in the world
    static final int N_CARS = 4;

    public MyWorld() {
        // Create a new world with X by Y cells with a cell size of S pixels.
        super(WORLD_X, WORLD_Y, WORLD_S);

        for (int j = 0; j < N_TILE; j++) {

            for (int i = 0; i < N_TILE; i++) {
                if ((i % 4) == 0 && (j % 4) == 0) {
                    tiles[i][j] = TILE_TYPE_INTERSECTION;
                    drawIntersection(i, j);
                } else {
                    if ((i % 4) == 0) {
                        tiles[i][j] = TILE_TYPE_VERTICAL;
                        drawVerticalRoad(i, j);

                    } else {
                        if ((j % 4) == 0) {
                            tiles[i][j] = TILE_TYPE_HORIZONTAL;
                            drawHorizontalRoad(i, j);
                        } else {
                            tiles[i][j] = TILE_TYPE_GRASS;
                            drawGrass(i, j);
                        }
                    }
                }

            }

        }
        placeClients();
        placeBuildings(NB_BUILDINGS);
        placeDestinations();
        // Place some crossings on the roads
        placeCrossings(NB_CROSSINGS);
        

        car = new Car();

        /*
         * 2nd and third arguments here really do not matter We could do :
         * addObject(car, 87, 74); and it would work just fine. Here we put 0, 0 so it's
         * "nice" when the game is not yet running.
         */
        addObject(car, TILE_SIZE, TILE_SIZE);

        StartScreen startScreen = new StartScreen();

        addObject(startScreen, WORLD_X / 2, WORLD_Y / 2);

        // Read and show the stats
        showStats(readStats());

        
    }

    // Npc stuff

    private void placeNpcCars(){
        // We spawn the npc cars at the very first line, on vertical roads

        int carCounter = 0;
        for (int i = 4; i < N_TILE; i+=8) {
            if(carCounter < N_CARS){
                if(tiles[0][i] == TILE_TYPE_INTERSECTION){
                    int x = (i*TILE_SIZE)+(TILE_SIZE / 2);
                    int y = 0;
                    addObject(npcCars[carCounter], x, y);
                    npcCars[carCounter].startPosition.x = x;
                    npcCars[carCounter].startPosition.y = y;

                    npcCars[carCounter].setImageProperly(carCounter);
                    carCounter++;
                }
            }

        }
        
    }

    // If one (or more) npc cars is in collision with player1's car, returns true (it's game over)
    private boolean checkCollisionWithNpcCars(){
        boolean r = false;
        for(int i=0; i<N_CARS; i++){
            if(npcCars[i].player1Collision){
                r = true;
                break;
            }
        }
        return r;
    }
    /////////////////////////
    // Statistic stuff

    // reads the stats from the file and returns the information in an array like so :
    // [player1Score, player2Score]
    private int[] readStats() {
        int[] scoreArr = { -1, -1 };
        try {
            File myObj = new File(STATS_FILE);
            Scanner myReader = new Scanner(myObj);
            boolean first = true;

            while (myReader.hasNextLine()) {
                if (first) {
                    scoreArr[0] = Integer.parseInt(myReader.nextLine());
                    first = false;
                } else {
                    scoreArr[1] = Integer.parseInt(myReader.nextLine());
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Could not read the stats from the file");
            e.printStackTrace();
        }
        return scoreArr;
    }

    // Show the stats as percent
    private void showStats(int[] scoreArr) {
        if (scoreArr[0] == -1 && scoreArr[1] == -1) {
            statsActor.setImage(new GreenfootImage("No stats yet. Play some games to see interesting data !", FONT_SIZE,
                    greenfoot.Color.BLACK, greenfoot.Color.WHITE));
        } else {
            float totalGame = (float) (scoreArr[0] + scoreArr[1]);

            float percent_win_player_1 = (scoreArr[0] / totalGame) * 100;
            float percent_win_player_2 = (scoreArr[1] / totalGame) * 100;

            percent_win_player_1 = Math.round(percent_win_player_1 * 100.0) / 100.0f;
            percent_win_player_2 = Math.round(percent_win_player_2 * 100.0) / 100.0f;

            statsActor.setImage(new GreenfootImage(
                    "Player 1 won " + String.valueOf(percent_win_player_1) + " % of games \n Player 2 won "
                            + String.valueOf(percent_win_player_2) + " % of games",
                    FONT_SIZE, greenfoot.Color.BLACK, greenfoot.Color.WHITE));

        }
        addObject(statsActor, STATS_X, STATS_Y);
    }

    // Used at the end of the game, to add the win in the stats
    private void writeNewScores(int[] scoreArr) {
        try {
            FileWriter myWriter = new FileWriter(STATS_FILE);
            myWriter.write(Integer.toString(scoreArr[0]) + "\n" + Integer.toString(scoreArr[1]));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred. Could not write the new scores to the file");
            e.printStackTrace();
        }
    }

    public void act() {
        // to play gameover sound for oldlady
        
        boolean gameOverLady = car.gameOverLady;
       
        if (drawDisplay) {
            placeNpcCars();
            removeObject(statsActor);
            clock.setImage(new GreenfootImage("Time: " + clockTime, FONT_SIZE, greenfoot.Color.BLACK, greenfoot.Color.WHITE));
            clientScore.setImage(
                    new GreenfootImage("Client score: " + clockTime, FONT_SIZE, greenfoot.Color.BLACK, greenfoot.Color.WHITE));

            addObject(clock, CLOCK_X, CLOCK_Y);

            addObject(clientScore, CLIENT_SCORE_X, CLIENT_SCORE_Y);

            player2_Ammo.setImage(
                    new GreenfootImage("Old lady: " + player2Ammo, FONT_SIZE, greenfoot.Color.BLACK, greenfoot.Color.WHITE));

            addObject(player2_Ammo, PLAYER2_AMMO_X, PLAYER2_AMMO_Y);
            drawDisplay = false;
        }
        if (getObjects(StartScreen.class).size() == 0) {
            // Player 1
            drawDisplay = true;
            Position carPosition = car.getPosition();
            // makes P1 wins when he dropped every client;
            
            // Value is -1 if the car is not next to a client, and if it is, the value is
            // the client's number
            int carNextToClient = -1;
            int r = -1;
            boolean dropOffClient = false;

            // Check if the car position is valid, if it's not, game over => player 2 win
            // the game
            gameOver = car.gameOver;

            switch (tiles[carPosition.x][carPosition.y]) {
                case TILE_TYPE_VERTICAL:
                    // if there is noboy in the car, look if there is a client we could pick up
                    if (clientInTheCar == -1) {
                        r = checkIfCarIsNextToClient(carPosition, true);
                        if (r != -1) {
                            carNextToClient = r;
                        }
                    }

                    // If there is a client in the car, check if we can drop him off
                    else {
                        dropOffClient = checkToDropOffClient(carPosition, true, clientInTheCar);

                    }

                    break;

                case TILE_TYPE_HORIZONTAL:
                    // if there is noboy in the car, look if there is a client we could pick up
                    if (clientInTheCar == -1) {
                        r = checkIfCarIsNextToClient(carPosition, false);

                        if (r != -1) {
                            carNextToClient = r;
                        }
                    }

                    // If there is a client in the car, check if we can drop him off
                    else {
                        dropOffClient = checkToDropOffClient(carPosition, false, clientInTheCar);

                    }

                    break;

                case TILE_TYPE_INTERSECTION:
                    break;

                case TILE_TYPE_CROSSING:
                    gameOver = false;
                    break;

                case TILE_TYPE_BUILDING:
                    gameOverType = GAME_OVER_TYPE_CRASH;
                    gameOver = true;
                    break;

                case TILE_TYPE_GRASS:
                    gameOverType = GAME_OVER_TYPE_CRASH;
                    gameOver = true;
                    break;

                
                   
            }
            if (droppedOffClients == N_CLIENTS) {
                gameOverType = GAME_OVER_TYPE_PLAYER_1_WON;
                gameOver = true;

            }
            // Check for collision with npc cars only if it is not already game over for another reason
            if(!gameOver){
                gameOver = checkCollisionWithNpcCars();
                if(gameOver){
                    gameOverType = GAME_OVER_TYPE_CRASH;
                }
            }


            // Player 2
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                Position tilePosition = getTilePosition(mouse.getX(), mouse.getY());
                if (mouse.getButton() == 1 && Greenfoot.mouseClicked(null)) {

                    // Check if the clicked tile is a crossing, and spawn an old lady if this is the
                    // case
                    if (tiles[tilePosition.x][tilePosition.y] == TILE_TYPE_CROSSING
                            && tiles[tilePosition.x][tilePosition.y] != TILE_TYPE_OLD_LADY) {

                        if (player2Ammo > 0) {
                            Greenfoot.playSound("old_lady_spawned.wav");
                            addObject(new OldLady(tilePosition), tilePosition.x * TILE_SIZE  + 10,
                                    tilePosition.y * TILE_SIZE +10);
                            player2Ammo--;
                            tiles[tilePosition.x][tilePosition.y] = TILE_TYPE_OLD_LADY;
                      
                    }

                }

            }
            
            } 
            

            // update clock
            runClock();

            if (gameOver) {
                removeObject(car);
                drawGameOver();
                if (gameOverLady) {
                    gameOverType = GAME_OVER_TYPE_KILLED_OLD_LADY;
                }
                playGameOver = true;

            }
            if (playGameOver) {
                int[] rStats = readStats();
                
                // Play a different sound depending of the situation (is it a crash ? , did player 1 won ?, etc...)
                switch (gameOverType) {
                    case GAME_OVER_TYPE_CRASH:
                        GreenfootSound crash = new GreenfootSound("crash.wav");
                        if (!soundHasPlayed) {
                            crash.play();
                            soundHasPlayed = true;
                        }
                        if (soundHasPlayed) {

                            Greenfoot.delay(SOUND_DELAY);
                            crash.stop();
                            playGameOver = false;
                        }

                        rStats[1]++;
                        writeNewScores(rStats);
                        break;
                    case GAME_OVER_TYPE_KILLED_OLD_LADY:
                        GreenfootSound ladyKilled = new GreenfootSound("old_lady_killed.wav");
                        if (!soundHasPlayed) {
                            ladyKilled.play();
                            soundHasPlayed = true;
                        }
                        if (soundHasPlayed) {

                            Greenfoot.delay(SOUND_DELAY);
                            ladyKilled.stop();
                            playGameOver = false;
                        }
                        rStats[1]++;
                        writeNewScores(rStats);
                        break;

                    case GAME_OVER_TYPE_OUT_OF_TIME:
                        GreenfootSound timeOut = new GreenfootSound("game_over_old_lady.wav");
                        if (!soundHasPlayed) {
                            timeOut.play();
                            soundHasPlayed = true;
                        }
                        if (soundHasPlayed) {

                            Greenfoot.delay(SOUND_DELAY);
                            timeOut.stop();
                            playGameOver = false;
                        }
                        rStats[1]++;
                        writeNewScores(rStats);
                        break;

                    case GAME_OVER_TYPE_PLAYER_1_WON:
                        GreenfootSound guberWins = new GreenfootSound("guber_win.wav");
                        if (!soundHasPlayed) {
                            guberWins.play();
                            soundHasPlayed = true;
                        }
                        if (soundHasPlayed) {

                            Greenfoot.delay(SOUND_DELAY);
                            guberWins.stop();
                            playGameOver = false;
                        }
                        rStats[0]++;
                        writeNewScores(rStats);
                        break;
                }

            }
            // If the car is next to a client, get the client in the car
            if (carNextToClient != -1) {
                if (clientInTheCar == -1) {
                    clients[carNextToClient].getInTheCar();
                    clientInTheCar = carNextToClient;
                }
            }

            // If we dropped of a client, count it
            if (dropOffClient) {
                buildingX = Greenfoot.getRandomNumber(N_TILE - 1);
                buildingY = Greenfoot.getRandomNumber(N_TILE - 1);
                // used in the class CLient, to know in which direction the building is
                while (tiles[buildingX][buildingY] != TILE_TYPE_CLIENT_DESTINATIONS[clientInTheCar]) {
                    buildingX = Greenfoot.getRandomNumber(N_TILE - 1);
                    buildingY = Greenfoot.getRandomNumber(N_TILE - 1);
                }
                ;
                destinationX = buildingX * TILE_SIZE;
                destinationY = buildingY * TILE_SIZE;
                clients[clientInTheCar].dropOff(carPosition.x, carPosition.y);
                droppedOffClients++;

                clientInTheCar = -1;
            }
            clientScore.setImage(new GreenfootImage("Client score: " + Integer.toString(droppedOffClients), 20,
                    greenfoot.Color.BLACK, greenfoot.Color.WHITE));
            player2_Ammo.setImage(
                    new GreenfootImage("Old lady: " + player2Ammo, 20, greenfoot.Color.BLACK, greenfoot.Color.WHITE));
        }
    }

    public void setTileType(Position p, int tile_type) {
        tiles[p.x][p.y] = tile_type;
    }

    // When axis = true, we check on the x axis, otherwise on the y axis
    private int checkIfCarIsNextToClient(Position carPosition, boolean axis) {
        int xValue = 0;
        int yValue = 1;

        if (axis) {
            xValue = 1;
            yValue = 0;
        }

        int r = -1;
        try {
            switch (tiles[carPosition.x + xValue][carPosition.y + yValue]) {
                case TILE_TYPE_CLIENT_0:
                    r = 0;
                    break;
                case TILE_TYPE_CLIENT_1:
                    r = 1;
                    break;
                case TILE_TYPE_CLIENT_2:
                    r = 2;
                    break;
                case TILE_TYPE_CLIENT_3:
                    r = 3;
                    break;
            }
        }
        // Just here to catch "out of bound" exceptions
        catch (Exception e) {
        }

        try {
            switch (tiles[carPosition.x - xValue][carPosition.y - yValue]) {
                case TILE_TYPE_CLIENT_0:
                    r = 0;
                    break;
                case TILE_TYPE_CLIENT_1:
                    r = 1;
                    break;
                case TILE_TYPE_CLIENT_2:
                    r = 2;
                    break;
                case TILE_TYPE_CLIENT_3:
                    r = 3;
                    break;
            }
        }
        // Just here to catch "out of bound" exceptions
        catch (Exception e) {
        }
        return r;
    }

    private boolean checkToDropOffClient(Position carPosition, boolean axis, int clientNumber) {
        int xValue = 0;
        int yValue = 1;

        if (axis) {
            xValue = 1;
            yValue = 0;
        }

        boolean r = false;
        try {
            if (tiles[carPosition.x + xValue][carPosition.y + yValue] == TILE_TYPE_CLIENT_DESTINATIONS[clientNumber]) {

                r = true;
            }
        }
        // Just here to catch "out of bound" exceptions
        catch (Exception e) {
        }

        try {
            if (tiles[carPosition.x - xValue][carPosition.y - yValue] == TILE_TYPE_CLIENT_DESTINATIONS[clientNumber]) {

                r = true;
            }
        }
        // Just here to catch "out of bound" exceptions
        catch (Exception e) {
        }

        return r;
    }

    // Returns the tile position in the global array from the x and y pixel
    // coordinates
    public Position getTilePosition(int x, int y) {
        return new Position(getOneTilePosition(x), getOneTilePosition(y));
    }

    // Used in getTilePosition
    private int getOneTilePosition(int v) {
        if ((v % TILE_SIZE) != 0) {
            return (v / TILE_SIZE);
        } else {
            return (v / TILE_SIZE) - 1;
        }
    }

    // used upon world construction
    private void placeCrossings(int n) {
        while (n >= 0) {
            int x = Greenfoot.getRandomNumber(N_TILE - 1);
            int y = Greenfoot.getRandomNumber(N_TILE - 1);

            if (tiles[x][y] == TILE_TYPE_VERTICAL) {
                tiles[x][y] = TILE_TYPE_CROSSING;
                drawCrossing(x, y, true);
                n--;
            } else if (tiles[x][y] == TILE_TYPE_HORIZONTAL) {
                tiles[x][y] = TILE_TYPE_CROSSING;
                drawCrossing(x, y, false);
                n--;

            }

        }
    }
    // Here we choose the type of building to place
    private void placeBuildings(int n) {
        
        while (n >= 0) {
            int x = Greenfoot.getRandomNumber(N_TILE - 1);
            int y = Greenfoot.getRandomNumber(N_TILE - 1);
                
            if ((tiles[x][y] == TILE_TYPE_GRASS)) {

                tiles[x][y] = TILE_TYPE_BUILDING;
                    if (n % 2 == 0) {
                        drawBuilding(x, y);
                    } else {
                        if (n % 3 == 0) {
                            int r = Greenfoot.getRandomNumber(3);
                            switch (r) {
                                case 0:
                                    drawFountain(x, y);
                                    break;
                                case 1:
                                    drawTree(x, y);
                                    break;
                                case 2:
                                    drawHelicopterPad(x, y);
                                    break;
                            }
                        } else {
                            drawBuilding2(x, y);
                        }
                }

                n--;
            }
            
        }

    }

    // Just a wrapper function
    private void removeAllObjects() {
        removeObjects(getObjects(null));
    }

    // Stuff being drawn on the screen

    private void drawGameOver() {
        GreenfootImage image = getBackground();

        // First, fill the entire screen with a rectangle
        image.setColor(GAME_OVER_BACKGROUND);
        image.fillRect(0, 0, WORLD_X, WORLD_Y);
        if (droppedOffClients != N_CLIENTS) {
            GreenfootImage g = new GreenfootImage(GAME_OVER_STRING, GAME_OVER_FONT_SIZE, GAME_OVER_FOREGROUND,
                    GAME_OVER_BACKGROUND, GAME_OVER_OUTLINE);
            image.drawImage(g, (WORLD_X - g.getWidth()) / 2, (WORLD_Y - g.getHeight()) / 2);
        } else {
            GreenfootImage g = new GreenfootImage(GAME_OVER_STRING2, GAME_OVER_FONT_SIZE, GAME_OVER_FOREGROUND,
                    GAME_OVER_BACKGROUND, GAME_OVER_OUTLINE);
            image.drawImage(g, (WORLD_X - g.getWidth()) / 2, (WORLD_Y - g.getHeight()) / 2);
        }
        removeAllObjects();
    }

    private void drawVerticalRoad(int i, int j) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;
        GreenfootImage image = getBackground();

        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        // Draw the road on top of the grass
        image.setColor(TILE_ROAD_COLOR);

        image.fillRect(x + (TILE_SIZE / ROAD_TO_GRASS_RATIO), y, TILE_SIZE / (ROAD_TO_GRASS_RATIO / 2), TILE_SIZE);
    }

    private void drawHorizontalRoad(int i, int j) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        // Draw the road on top of the grass
        image.setColor(TILE_ROAD_COLOR);

        image.fillRect(x, y + (TILE_SIZE / ROAD_TO_GRASS_RATIO), TILE_SIZE, TILE_SIZE / (ROAD_TO_GRASS_RATIO / 2));
    }

    // Draw the intersection of the roads
    private void drawIntersection(int i, int j) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        image.setColor(TILE_ROAD_COLOR);

        // Draw the road on top of the grass
        image.fillRect(x, y + (TILE_SIZE / ROAD_TO_GRASS_RATIO), TILE_SIZE, TILE_SIZE / (ROAD_TO_GRASS_RATIO / 2));
        image.fillRect(x + (TILE_SIZE / ROAD_TO_GRASS_RATIO), y, TILE_SIZE / (ROAD_TO_GRASS_RATIO / 2), TILE_SIZE);
    }

    // simple orange buildings
    private void drawBuilding(int i, int j) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_BUILDING_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    // More elaborate buildings with colored borders
    private void drawBuilding2(int i, int j) {
        // First, draw the light square, then the dark one inside it
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;

        GreenfootImage image = getBackground();
        int lgrayVariation = LIGHT_GRAY + Greenfoot.getRandomNumber(GRAY_VARIATION);
        image.setColor(new Color(lgrayVariation, lgrayVariation, lgrayVariation));

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        int dgrayVariation = DARK_GRAY + Greenfoot.getRandomNumber(GRAY_VARIATION);
        image.setColor(new Color(dgrayVariation, dgrayVariation, dgrayVariation));

        image.fillRect(x + BUILDING_BORDER_SIZE, y + BUILDING_BORDER_SIZE, TILE_SIZE - (2 * BUILDING_BORDER_SIZE),
                TILE_SIZE - (2 * BUILDING_BORDER_SIZE));
    }

    // A fountain (or a tree, helicopad, etc...) is also a "building", it's just a different representation of the
    // same concept (another view of the same model)
    private void drawFountain(int i, int j) {
        Building_image fountain = new Building_image();
        addObject(fountain, (i * TILE_SIZE) + BUILDING_IMAGE_OFFSET, (j * TILE_SIZE) + BUILDING_IMAGE_OFFSET);
    }

    private void drawTree(int i, int j) {
        Building_image tree = new Building_image();
        tree.setImage("tree.png");
        addObject(tree, (i * TILE_SIZE) + BUILDING_IMAGE_OFFSET, (j * TILE_SIZE) + BUILDING_IMAGE_OFFSET);
    }

    private void drawHelicopterPad(int i, int j) {
        Building_image heli = new Building_image();
        heli.setImage("helicopter_pad.png");
        addObject(heli, (i * TILE_SIZE) + BUILDING_IMAGE_OFFSET, (j * TILE_SIZE) + BUILDING_IMAGE_OFFSET);
    }

    private void drawDestination(int i, int j, int colorIndex) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(CLIENTS_COLORS[colorIndex]);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    private void drawGrass(int i, int j) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;

        GreenfootImage image = getBackground();

        image.setColor(TILE_GRASS_COLOR);

        image.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    private void drawCrossing(int i, int j, boolean vertical) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;

        GreenfootImage image = getBackground();
        image.setColor(TILE_ROAD_COLOR);

        // Add bands on top
        image.setColor(TILE_CROSSING_COLOR);
        if (vertical == false) {
            boolean crossing_b = true;
            for (int p = 0; p < TILE_SIZE; p += CROSSING_BANDS_WIDTH) {
                if (crossing_b) {
                    image.fillRect(x, y + p, TILE_SIZE, CROSSING_BANDS_WIDTH);
                    crossing_b = false;
                } else {
                    crossing_b = true;
                }
            }
        } else {
            boolean crossing_b = true;
            for (int p = 0; p < TILE_SIZE; p += CROSSING_BANDS_WIDTH) {
                if (crossing_b) {
                    image.fillRect(x + p, y, CROSSING_BANDS_WIDTH, TILE_SIZE);
                    crossing_b = false;
                } else {
                    crossing_b = true;
                }
            }
        }

    }

    private void drawCar(int i, int j) {
        int x = i * TILE_SIZE;
        int y = j * TILE_SIZE;
        tiles[i][j] = TILE_TYPE_CAR;
        GreenfootImage image = getBackground();
        GreenfootImage sprite = new GreenfootImage("car.png");
        sprite.scale(TILE_SIZE, TILE_SIZE);
        image.drawImage(sprite, x, y);

    }

    private void runClock() {
        clockRegulator = (clockRegulator + 1) % 70;
        if (clockRegulator == 0) {
            clockTime--;
            clock.setImage(new GreenfootImage("Time: " + clockTime, 20, greenfoot.Color.BLACK, greenfoot.Color.WHITE));
            if (clockTime < 0) {
                gameOverType = GAME_OVER_TYPE_OUT_OF_TIME;
                gameOver = true;
            }

        }
    }
    private void placeDestinations() {
        for (int i = 0; i < N_CLIENTS; i++) {
            while (true) {
                int x = Greenfoot.getRandomNumber(N_TILE - 1);
                int y = Greenfoot.getRandomNumber(N_TILE - 1);
                // check if its a building not next to a crossing, not in an unreachable area
                if (tiles[x][y] == TILE_TYPE_GRASS && tiles[x][y] != TILE_TYPE_VERTICAL && tiles[x][y] != TILE_TYPE_HORIZONTAL
                        && tiles[x][y] != TILE_TYPE_INTERSECTION && tiles[x][y] != TILE_TYPE_CLIENT_0 && tiles[x][y] != TILE_TYPE_CLIENT_0
                        && tiles[x][y] != TILE_TYPE_CLIENT_1 && tiles[x][y] != TILE_TYPE_CLIENT_2 && tiles[x][y] != TILE_TYPE_CLIENT_3
                        && (checkTile(x - 1, y) || checkTile(x + 1, y) || checkTile(x, y + 1) || checkTile(x, y - 1))) {
                    tiles[x][y] = TILE_TYPE_CLIENT_DESTINATIONS[i];
                    //then draws it
                    drawDestination(x, y, i);
                    break;
                }
            }}
        }
    private void placeClients() {
        for (int i = 0; i < 4; i++) {
            while (true) {
                int x = Greenfoot.getRandomNumber(N_TILE - 1);
                int y = Greenfoot.getRandomNumber(N_TILE - 1);
                // check if its a building not next to a crossing, not in an unreachable area
                if (tiles[x][y] != TILE_TYPE_VERTICAL && tiles[x][y] != TILE_TYPE_HORIZONTAL
                        && tiles[x][y] != TILE_TYPE_INTERSECTION
                        && (checkTile(x - 1, y) || checkTile(x + 1, y) || checkTile(x, y + 1) || checkTile(x, y - 1))) {
                    tiles[x][y] = TILE_TYPE_CLIENTS[i];
                    clients[i] = new Client();
                    clients[i].setColor(i);
                    addObject(clients[i], x * TILE_SIZE, y * TILE_SIZE);
                    break;
                }
            }
        }
    }

    // Return true if the tile is of the correct type
    private boolean checkTile(int x, int y) {
        boolean r;
        if (x <= 0 || y <= 0) {
            r = false;
        } else {
            r = (tiles[x][y] == TILE_TYPE_HORIZONTAL || tiles[x][y] == TILE_TYPE_VERTICAL)
                    ;
        }
        return r;
    }
}
