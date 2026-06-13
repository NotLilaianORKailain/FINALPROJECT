/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project;

//import file needed for graphics
import processing.core.PApplet;
import pkgfinal.project.FoodRelated.*;

//import file needed to wirte to file
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

//import file needed to read from file
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 *
 * @author 343479150
 */
public class Sketch extends PApplet {
    //var needed for structure of game (counter, timing and toggling ending)
    private int stage, intro = 0;
    private int gameStartTime;
    private int gameDuration = 60000; //60sec in milliseconds
    private boolean maxItemsMessage = false;
    private int maxItemsTimer = 0;
    private boolean gameEnded = false;
    
    //var needed to create objects user can interact with
    private Buttons startButton, skipButton, addEgg, addFlour, addSyrup, addWater;
    private UserRabbit user;
    
    //var needed to keep track number of objects the user pushes around
    private FoodItem[] food = new FoodItem[20];
    private FoodItem[][] board = new FoodItem[4][5];
    
    //var needed to postions of objects that get pushed around
    public static final int[] COLS = {107, 253, 399, 545, 691}; //add 146
    public static final int[] ROWS = {93, 231, 369, 490}; //add 138 but the last

    
    //set size to 800 pixel long by 600 pixel high
    public void settings(){
        size(800,600); 
    }
    
    //initializes buttons and rabbit user, as well as clears file for new game
    public void setup(){
        //initialize buttons the user can click and the rabbit user can move
        startButton = new Buttons(this, 255, 380, "1buttons/start.png");
        skipButton = new Buttons(this, 640, 10, "1buttons/skip.png");
        addEgg = new Buttons(this, 52, 518, "1buttons/addEgg.png");
        addFlour = new Buttons(this, 154, 518, "1buttons/addFlour.png");
        addSyrup = new Buttons(this, 246, 518, "1buttons/addSyrup.png");
        addWater = new Buttons(this, 346, 518, "1buttons/addWater.png");
        user = new UserRabbit(this, 620, 400);
        
        //clear game file once game starts up
        try {
            PrintWriter output = new PrintWriter("dataLog.txt");
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not clear file");
        }
        
    }
    
    //main game loop that manages object, user input, and render current frame
    public void draw(){
        switch (stage){
            case 0: //title page - mouse press to move on=======================
                background(loadImage("1backgrounds/mainmenu.png"));
                startButton.draw();
                break;
   
            case 1: //intro page - 20 frame story, counter in mouse press=======
                if (intro==1) background(loadImage("1backgrounds/1.png"));
                else if (intro==2) background(loadImage("1backgrounds/2.png"));
                else if (intro==3) background(loadImage("1backgrounds/3.png"));
                else if (intro==4) background(loadImage("1backgrounds/4.png"));
                else if (intro==5) background(loadImage("1backgrounds/5.png"));
                else if (intro==6) background(loadImage("1backgrounds/6.png"));
                else if (intro==7) background(loadImage("1backgrounds/7.png"));
                else if (intro==8) background(loadImage("1backgrounds/8.png"));
                else if (intro==9) background(loadImage("1backgrounds/9.png"));
                else if (intro==10) background(loadImage("1backgrounds/91.png"));
                else if (intro==11) background(loadImage("1backgrounds/92.png"));
                else if (intro==12) background(loadImage("1backgrounds/93.png"));
                else if (intro==13) background(loadImage("1backgrounds/94.png"));
                else if (intro==14) background(loadImage("1backgrounds/95.png"));
                else if (intro==15) background(loadImage("1backgrounds/96.png"));
                else if (intro==16) background(loadImage("1backgrounds/97.png"));
                else if (intro==17) background(loadImage("1backgrounds/98.png"));
                else if (intro==18) background(loadImage("1backgrounds/99.png"));
                else if (intro==19) background(loadImage("1backgrounds/991.png"));
                else if (intro==20) background(loadImage("1backgrounds/992.png"));
                skipButton.draw();
                break;
                
            case 2: //game page - 60 secs of game play==========================
                //start timer once case 2 is reached
                if (gameStartTime == 0) {gameStartTime = millis();}

                //draw in background and all needed buttons
                background(loadImage("1backgrounds/moon.png"));
                addEgg.draw();
                addFlour.draw();
                addSyrup.draw();
                addWater.draw();

                //calulate how much time is left and change timer every second 
                int timeLeft = gameDuration - (millis() - gameStartTime);
                int secondsLeft = max(0, timeLeft / 1000);
                
                if (secondsLeft <= 5) {fill(255, 0, 0);} // text red if less than 5sec
                else {fill(0);} //if not less than 5sec juts have normal black text
                
                textSize(50); //font size 50 and write out the text
                text("Time Left: " + secondsLeft, 50, 85);
                
                //check user rabbits movement against all other items
                for (int i = 0; i < food.length; i++) {
                    if (keyPressed && food[i] != null) {
                        if (keyCode == LEFT) attemptMove(-5, 0, food[i], -1, 0); 
                        else if (keyCode == RIGHT) attemptMove(5, 0, food[i], 1, 0);
                        else if (keyCode == UP) attemptMove(0, -5, food[i], 0, -1);
                        else if (keyCode == DOWN) attemptMove(0, 5, food[i], 0, 1);
                    }
                }
                user.draw(); //after movement has been calclated draw user rabbit
                
                //also draw all the food that might be within the array
                for (int i = 0; i < food.length; i++) {
                    if (food[i] != null) {food[i].draw();}
                } 
                
                //check if any merging was triggered
                checkFoodCombinations();
                
                //when user trys to spawn in more items pass limit, show max message
                if (maxItemsMessage) {
                    fill(255, 0, 0); textSize(55); //make text red and size 55
                    text("MAX ITEMS SPAWNED", 250, 320);

                    if (millis() - maxItemsTimer > 2000) {
                        maxItemsMessage = false; //keep message around for 2 sec
                    }
                }
                
                
                //given the game hasnt ended yet, check elapsed time is 60sec
                if (!gameEnded && millis()-gameStartTime >= gameDuration) {
                    gameEnded = true;
                    stage = 3; //if 60sec has passed move on to ending page
                } 
                break;

            case 3: //ending page - tells user final score======================
                int score = readFile("Mooncake"); //chck file for score
                background(loadImage("1backgrounds/rabbit.png"));
                
                fill(255); textSize(50); //wirte text in white and size 50
                text("Time's Up!", 265, 280);
                text("Mooncakes Made: " + score, 190, 350);
                break;
                
            default: //fall through=============================================
                break;
        }   
    }

    //has certain action depending on which stage gets clicked
    public void mousePressed() {
        switch (stage){
            case 0: //title page where user clicks the start button tp move on
                if (startButton.isClicked(mouseX, mouseY)) stage = 1;

            case 1: //intro page with storyline, there is 20 frames before game
                if (skipButton.isClicked(mouseX, mouseY)) {stage = 2; break;}
                
                if(intro<20) intro++; //counter for which frame to draw
                else stage = 2;
                
                break;
            
            case 2: //game page where each item makes a different food object
                if (addEgg.isClicked(mouseX, mouseY)) {createFood("egg", "1images/egg.png");} 
                if (addFlour.isClicked(mouseX, mouseY)) {createFood("flour", "1images/flour.png");} 
                if (addSyrup.isClicked(mouseX, mouseY)) {createFood("syrup", "1images/syrup.png");}  
                if (addWater.isClicked(mouseX, mouseY)) {createFood("water", "1images/water.png");}
        }
    }

    /** METHOD: manages user and item movements
     *  ensures user cant push items past wall/cant phase into items
     *@param dx users starting x position
     *@param dy users starting y position
     *@param food  item to check if there is a collision
     *@param stepX depending on if movement is right(+1) or left(-1)
     *@param stepY depending on if movement is down(+1) or up(-1)
     */
    public void attemptMove(int dx, int dy, FoodItem food, int stepX, int stepY) {
        //track rabbits premovement and make the move to track where it goes
        int oldRabbitX = user.x; 
        int oldRabbitY = user.y;
        user.move(dx, dy);

        //after moving, check if there is a collision with food
        if (user.isCollidingWith(food)) {
            int oldFoodX = food.x; int oldFoodY = food.y; //save food's premove

            if (stepX != 0) {food.moveX(stepX);} //stepX: (-1) = left, (+1) = right
            if (stepY != 0) {food.moveY(stepY);} //stepY: (-1) = up, (+1) = down

            //if food didnt change x or y then it's hit the wall, rabbit shouldnt move either
            if (food.x == oldFoodX && food.y == oldFoodY) {
                user.x = oldRabbitX; user.y = oldRabbitY; 
            }
        }
    }
    
    /** METHOD: finds a empty spot in the food storing array
     *  a smaller method that simplifies later methods 
     *@return int index number of a open slot, if (-1) means array is full
     */
    public int getEmptySlot() {
        for (int i = 0; i < food.length; i++) {
            if (food[i] == null) {return i;} //if empty return that index
        } return -1; //if array is full return neg one
    }
    
    /** METHOD: finds first instance of item and empties that array slot
     *  a smaller method that simplifies later methods 
     * @param item given item to look for in array
     */  
    public void removeFood(FoodItem item) {
        for (int i = 0; i < food.length; i++) {
            if (food[i] == item) {food[i] = null; return;} //item found, make null
        }
    }
    
    /** METHOD: creates each food item
     *  ensures food wont spawn on top of other food items
     *@param type    a string of which item type to make
     *@param imgPath a string of the image of the item
     */
    public void createFood(String type, String imgPath) {
        //reset while loop for each new item
        boolean goodSpot = false;

        //first check if there are empty spots before looping to find it
        if (getEmptySlot() == -1) {    
            maxItemsMessage = true; //if no spot show message to user
            maxItemsTimer = millis(); //show for 2sec using timer
            return;
        }

        while (!goodSpot) { //keep checking for new spot till item is made

            //randomly generate a new spot using the grid slots (ie box(1,1))
            int xShift = (int)(Math.random()*COLS.length);
            int yShift = (int)(Math.random()*ROWS.length);

            int x = COLS[xShift]; //find the x coordinate of generated box
            int y = ROWS[yShift]; //find the y coordinate of generated box
            goodSpot = true;      //make the spot true and test if it stays true

            //first check food is not on top of rabbit
            if (user.x == x && user.y == y) {goodSpot = false;}
            
            //loop through and check if new spot is one that is already occupied
            for (int i = 0; i < food.length; i++) {
                if (food[i] != null && food[i].x == x && food[i].y == y) {
                    goodSpot = false; break; //spot matches differnt item so taken
                }
            }

            //if spot is still true then it is valid
            if (goodSpot) {
                int slot = getEmptySlot(); //then look for empty spot
                if (slot == -1) return;    //if -1 then at max count so end method
                
                //if loop has reached this point, create the food item
                if (type.equals("egg")) food[slot] = new Egg(this, x, y, imgPath);
                else if (type.equals("flour")) food[slot] = new Flour(this, x, y, imgPath);
                else if (type.equals("syrup")) food[slot] = new Syrup(this, x, y, imgPath);
                else if (type.equals("water")) food[slot] = new Water(this, x, y, imgPath);
                            
                board[yShift][xShift] = food[slot]; //track food into a 2d array
            }
        }
    }
    
    /** METHOD: checks for merging of food items
     *  manages two recipes, fist removing combined items and creating product
     */
    public void checkFoodCombinations() {
        //start with all needed ingridents being set to null
        Flour flour = null;
        Water water = null;
        Syrup syrup = null;
        Dough dough = null;
        Egg egg = null;

        //loop array and find/categorize all existing items
        for (int i = 0; i < food.length; i++) {
            if (food[i] instanceof Flour) flour = (Flour) food[i];
            if (food[i] instanceof Water) water = (Water) food[i];
            if (food[i] instanceof Syrup) syrup = (Syrup) food[i];
            if (food[i] instanceof Dough) dough = (Dough) food[i];
            if (food[i] instanceof Egg) egg = (Egg) food[i];
        }

        //RECIPE 1: flour + water + syrup = dough -----------------------------
        if (flour != null && water != null && syrup != null) {
            //if the 3 ingredients do exist, check if they share locations:
            //use of math abs is a tolerance checker (ie close enough = works)
            if (Math.abs(flour.x - water.x) < 5
             && Math.abs(flour.x - syrup.x) < 5
             && Math.abs(flour.y - water.y) < 5
             && Math.abs(flour.y - syrup.y) < 5) {

                //since they are in the same spot, track an items x and y 
                int x = flour.x;
                int y = flour.y;
                //then remove the items from being stored in the array
                removeFood(flour);
                removeFood(water);
                removeFood(syrup);
                
                //find a new empty array spot, make the dough and wirte down action
                int slot = getEmptySlot();
                if (slot != -1) {
                    food[slot] = new Dough(this, x, y, "1images/dough.png");
                    saveTransaction("Flour + Water + Syrup = Dough"); //write to file
                } return;
            }
        }


        // RECIPE 2: dough + egg = mooncake ------------------------------------
        if (dough != null && egg != null) {
            //if the 2 ingredients do exist, check if they share the same location:
            if (Math.abs(dough.x - egg.x) < 5 
             && Math.abs(dough.y - egg.y) < 5) {

                //since they are in the same spot, track an items x and y 
                int x = dough.x;
                int y = dough.y;
                //then remove the items from being stored in the array
                removeFood(dough);
                removeFood(egg);

                //find a new empty array spot, make the dough and wirte down action
                int slot = getEmptySlot();
                if (slot != -1) {
                    food[slot] = new Mooncake(this, x, y, "1images/mooncake.png");
                    saveTransaction("Dough + Egg = Mooncake"); //write to file
                }
            }
        }
    }
    
    /** METHOD: writes a string to a file
     *  a smaller method that was used in a earlier method
     * @param text string to be written within file
     */  
    public void saveTransaction(String text) {
        try { //try to create wirter for file and wirte the given string before closing
            PrintWriter output = new PrintWriter(new FileWriter("dataLog.txt", true));
            output.println(text);
            output.close();
        } catch (IOException e) { //catch error with trying to wirte to file
            System.out.println("Error saving");
        }
    } 
    
    /** METHOD: reads and counts how many times a string is in file
     *  a smaller method that simplifies later methods 
     * @param text scans for string within file
     * @return int counts how many times given text is in file
     */  
    public int readFile(String text) {
        int count = 0;
            try { //try to find file and scanner and read lines + count string
                File file = new File("dataLog.txt");
                Scanner input = new Scanner(file);

                while (input.hasNextLine()) { //while there is a next line, read
                    String line = input.nextLine();
                    if (line.contains(text)) {count++;} //if text is found, count
                }
                input.close();
                
            } catch (FileNotFoundException e) { //catch error while trying to read
                System.out.println("File not found");
            }
            return count;
    }
}
