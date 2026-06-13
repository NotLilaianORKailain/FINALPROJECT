/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project;

import processing.core.PApplet;
import pkgfinal.project.FoodRelated.FoodItem;
import pkgfinal.project.FoodRelated.Egg;
import pkgfinal.project.FoodRelated.Flour;
import pkgfinal.project.FoodRelated.Syrup;
import pkgfinal.project.FoodRelated.Water;
import pkgfinal.project.FoodRelated.Mooncake;
import pkgfinal.project.FoodRelated.Dough;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 *
 * @author 343479150
 */
public class Sketch extends PApplet {
    private int stage, intro = 0;
    private Buttons startButton, addEgg, addFlour, addSyrup, addWater;
    private UserRabbit user;
    
    FoodItem[] food = new FoodItem[20];
    int foodCount = 0;
    
    public static final int[] COLS = {107, 253, 399, 545, 691}; //add 146
    public static final int[] ROWS = {93, 231, 369, 490}; //add 138 but the last
    FoodItem[][] board = new FoodItem[4][5];
    
    int gameStartTime;
    int gameDuration = 30000; // 30 seconds in milliseconds
    boolean gameEnded = false;
    
    public void settings(){
        size(800,600); //set size to 800 pixel long by 600 pixel high
    }
    public void setup(){
        startButton = new Buttons(this, 255, 380, "1buttons/button-start.png");
        addEgg = new Buttons(this, 52, 518, "1buttons/addEgg.png");
        addFlour = new Buttons(this, 154, 518, "1buttons/addFlour.png");
        addSyrup = new Buttons(this, 246, 518, "1buttons/addSyrup.png");
        addWater = new Buttons(this, 346, 518, "1buttons/addWater.png");
        
        user = new UserRabbit(this, 620, 400);
    }
    
    public void mousePressed() {
        println("x: " + mouseX + ", y: " + mouseY); //debuging
        //depending on where user clicks a new frame is shown
        switch (stage){
            case 0:
                if (startButton.isClicked(mouseX, mouseY)) stage = 1;

            case 1:
                if(intro<20) intro++;
                else stage = 2;
                break;
            
            case 2:
                if (addEgg.isClicked(mouseX, mouseY)) {createFood("egg", "1images/egg.png");} 
                if (addFlour.isClicked(mouseX, mouseY)) {createFood("flour", "1images/flour.png");} 
                if (addSyrup.isClicked(mouseX, mouseY)) {createFood("syrup", "1images/syrup.png");}  
                if (addWater.isClicked(mouseX, mouseY)) {createFood("water", "1images/water.png");}
        }
    }

    public void attemptMove(int dx, int dy, FoodItem food, int stepX, int stepY) {
        //track rabbits premovement and move
        int oldRabbitX = user.x; 
        int oldRabbitY = user.y;
        user.move(dx, dy);

        //after moving is there collsion that is allowed?
        if (user.isCollidingWith(food)) {
            int oldFoodX = food.x; int oldFoodY = food.y; //save food premove

            if (stepX != 0) {food.moveX(stepX);} //if step needed, move x
            if (stepY != 0) {food.moveY(stepY);} //if step needed, move y

            //if food didnt change in x or y then rabbit shouldnt move either
            if (food.x == oldFoodX && food.y == oldFoodY) {
                user.x = oldRabbitX; user.y = oldRabbitY; 
            }
        }
    }
    public void createFood(String type, String imgPath) {

        boolean goodSpot = false;

        while (!goodSpot) {

            int xShift = (int) (Math.random() * COLS.length);
            int yShift = (int) (Math.random() * ROWS.length);

            int x = COLS[xShift];
            int y = ROWS[yShift];

            goodSpot = true;

            for (int i = 0; i < food.length; i++) {
                if (food[i] != null && food[i].x == x && food[i].y == y) {
                    goodSpot = false;
                    break;
                }
            }

            if (goodSpot) {

                int slot = getEmptySlot();
                if (slot == -1) return;
                
                if (type.equals("egg")) food[slot] = new Egg(this, x, y, imgPath);
                else if (type.equals("flour")) food[slot] = new Flour(this, x, y, imgPath);
                else if (type.equals("syrup")) food[slot] = new Syrup(this, x, y, imgPath);
                else if (type.equals("water")) food[slot] = new Water(this, x, y, imgPath);
                else if (type.equals("dough")) food[slot] = new Dough(this, x, y, imgPath);
                else food[slot] = new Mooncake(this, x, y, imgPath);
                            
                board[yShift][xShift] = food[foodCount]; //track food in board
                foodCount++;
            }
        }
    }
    
public void checkFoodCombinations() {

    Flour flour = null;
    Water water = null;
    Syrup syrup = null;
    Dough dough = null;
    Egg egg = null;

    // Find existing items
    for (int i = 0; i < food.length; i++) {
        if (food[i] instanceof Flour) flour = (Flour) food[i];
        if (food[i] instanceof Water) water = (Water) food[i];
        if (food[i] instanceof Syrup) syrup = (Syrup) food[i];
        if (food[i] instanceof Dough) dough = (Dough) food[i];
        if (food[i] instanceof Egg) egg = (Egg) food[i];
    }

    // -------------------------
    // RECIPE 1: Flour + Water + Syrup = Dough
    // -------------------------
    if (flour != null && water != null && syrup != null) {

        if (flour.x == water.x && flour.x == syrup.x &&
            flour.y == water.y && flour.y == syrup.y) {

            int x = flour.x;
            int y = flour.y;

            removeFood(flour);
            removeFood(water);
            removeFood(syrup);

            int slot = getEmptySlot();
            if (slot != -1) {
                food[slot] = new Dough(this, x, y, "1images/dough.png");
                saveTransaction("Flour + Water + Syrup = Dough");
            }

            return;
        }
    }

    // -------------------------
    // RECIPE 2: Dough + Egg = Mooncake
    // -------------------------
    if (dough != null && egg != null) {

        if (dough.x == egg.x && dough.y == egg.y) {

            int x = dough.x;
            int y = dough.y;

            removeFood(dough);
            removeFood(egg);

            int slot = getEmptySlot();
            if (slot != -1) {
                food[slot] = new Mooncake(this, x, y, "1images/mooncake.png");
                saveTransaction("Dough + Egg = Mooncake");
            }
        }
    }
}
    
    public int getEmptySlot() {
        for (int i = 0; i < food.length; i++) {
            if (food[i] == null) {
                return i;
            }
        }
        return -1; // no space left
    }
    public void removeFood(FoodItem item) {

        for (int i = 0; i < food.length; i++) {
            if (food[i] == item) {
                food[i] = null;
            }
        }
    }
    
    public void saveTransaction(String combo) {
        try {
            PrintWriter output = new PrintWriter(new FileWriter("dataLog.txt", true));
            output.println(combo);
            output.close();
        } catch (IOException e) {
            System.out.println("Error saving");
        }
    } 
    
    public int readFile() {
        int count = 0;
            try {
                File file = new File("dataLog.txt");
                Scanner input = new Scanner(file);

                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    if (line.contains("Mooncake")) {count++;}
                }
                input.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
            return count;
    }

    public void draw(){
        switch (stage){
            case 0: //title page================================================
                background(loadImage("1backgrounds/mainmenu.png"));
                startButton.draw();
                break;
   
            case 1: //intro page=================================================
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
                break;
                
            case 2: //game page=================================================
                if (gameStartTime == 0) {
                    gameStartTime = millis(); // start timer once
                }
                System.out.print(foodCount);
                
                background(loadImage("1backgrounds/moon.png"));
                addEgg.draw();
                addFlour.draw();
                addSyrup.draw();
                addWater.draw();

                int timeLeft = gameDuration - (millis() - gameStartTime);
                int secondsLeft = max(0, timeLeft / 1000);
                if (secondsLeft <= 5) {
                    fill(255, 0, 0);
                } else {
                    fill(0);
                }
                textSize(50);
                text("Time Left: " + secondsLeft, 50, 85);
                

                
                //check movement of all items
                for (int i = 0; i < foodCount; i++) {
                    if (keyPressed && food[i] != null) {
                        if (keyCode == LEFT) attemptMove(-5, 0, food[i], -1, 0); 
                        else if (keyCode == RIGHT) attemptMove(5, 0, food[i], 1, 0);
                        else if (keyCode == UP) attemptMove(0, -5, food[i], 0, -1);
                        else if (keyCode == DOWN) attemptMove(0, 5, food[i], 0, 1);
                    }
                }

                user.draw();
                for (int i = 0; i < foodCount; i++) {
                    if (food[i] != null) {food[i].draw();} //draw all food up to counter
                } 
                checkFoodCombinations();
                
                if (!gameEnded && millis() - gameStartTime >= gameDuration) {
                    gameEnded = true;
                    stage = 3;
                }
                
                break;

            case 3:
                int score = readFile();
                background(loadImage("1backgrounds/rabbit.png"));
                fill(255);
                textSize(50);

                text("Time's Up!", 265, 280);
                text("Mooncakes Made: " + score, 190, 350);
                break;
            default: //fall through=============================================
                break;
        }   
    }
}
