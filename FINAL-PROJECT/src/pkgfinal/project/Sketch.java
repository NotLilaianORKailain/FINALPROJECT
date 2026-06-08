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
/**
 *
 * @author 343479150
 */
public class Sketch extends PApplet {
    private int stage = 0;
    private Buttons startButton, helpButton;
    private Buttons addEgg, addFlour, addSyrup, addWater;
    private UserRabbit user;
    
    FoodItem[] food = new FoodItem[20];
    int foodCount = 1;
    
    public static final int[] COLS = {107, 253, 399, 545, 691}; //add 146
    public static final int[] ROWS = {93, 231, 369, 490}; //add 138 but the last

//line(107, 0, 107, 800); debug movement put in draw
//line(253, 0, 253, 800);
//line(399, 0, 399, 800);
//line(545, 0, 545, 800);
//line(691, 0, 691, 800);
//    line(0, 93, 800, 93);
//    line(0, 231, 800, 231);
//    line(0, 369, 800, 369);
//    line(0, 490, 800, 490);
    
    public void settings(){
        size(800,600); //set size to 800 pixel long by 600 pixel high
    }
    public void setup(){
        startButton = new Buttons(this, 255, 380, "images/button-start.png");
        helpButton = new Buttons(this, 620, 20, "images/button-help.png");
        
        addEgg = new Buttons(this, 52, 518, "images/addEgg.png");
        addFlour = new Buttons(this, 154, 518, "images/addFlour.png");
        addSyrup = new Buttons(this, 246, 518, "images/addSyrup.png");
        addWater = new Buttons(this, 346, 518, "images/addWater.png");
        
        user = new UserRabbit(this, 620, 400);
        createFood("mooncake","images/mooncake.png"); //creates first moon cake
    }
    
    public void mousePressed() {
        println("x: " + mouseX + ", y: " + mouseY); //debuging
        //depending on where user clicks a new frame is shown
        switch (stage){
            case 0:
                if (helpButton.isClicked(mouseX, mouseY)) {
                    stage = 1;
                } else if (startButton.isClicked(mouseX, mouseY)) {
                    stage = 2;
                }
            case 1:
                if (addEgg.isClicked(mouseX, mouseY)) {

                } 
                if (addFlour.isClicked(mouseX, mouseY)) {
                    stage = 2;
                } 
                if (addSyrup.isClicked(mouseX, mouseY)) {
                    stage = 2;
                }  
                if (addWater.isClicked(mouseX, mouseY)) {
                    stage = 2;
                }
                
        }

        
        
    }

    public void attemptMove(int dx, int dy, int stepX, int stepY) {
        //track rabbits premovement and move
        int oldRabbitX = user.x; 
        int oldRabbitY = user.y;
        user.move(dx, dy);

        //after moving is there collsion that is allowed?
        if (user.isCollidingWith(food[0])) {
            int oldFoodX = food[0].x; int oldFoodY = food[0].y; //save food premove

            if (stepX != 0) {food[0].moveX(stepX);} //if step needed, move x
            if (stepY != 0) {food[0].moveY(stepY);} //if step needed, move y

            //if food didnt change in x or y then rabbit shouldnt move either
            if (food[0].x == oldFoodX && food[0].y == oldFoodY) {
                user.x = oldRabbitX; user.y = oldRabbitY; 
            }
        }
    }
    
    
    public void createFood(String type, String imgPath) {
        
        if (type.equals("egg")) food[foodCount] = new Egg(this, 0, 0, imgPath);
        else if (type.equals("flour")) food[foodCount] = new Flour(this, 0, 0, imgPath);
        else if (type.equals("syrup")) food[foodCount] = new Syrup(this, 0, 0, imgPath);
        else if (type.equals("water")) food[foodCount] = new Water(this, 0, 0, imgPath);
        else food[foodCount] = new Mooncake(this, 0, 0, imgPath);
        foodCount++;
    }
    
    
    public void locateFood(int spot, FoodItem type) {
        boolean goodSpot = false; //starts as false to run while loop

        while (!goodSpot) {
            int xShift = (int)(Math.random()*COLS.length); //random col
            int yShift = (int)(Math.random()*ROWS.length); //random row

            //find the value of the col and row and test if spot is valid
            int x = COLS[xShift];
            int y = ROWS[yShift];
            goodSpot = true;

            //check overlap with existing food, if x and y match smth else = bad spot
            for (int i = 0; i < foodCount; i++) {
                if (food[i] != null && food[i].x == x && food[i].y == y) {
                    goodSpot = false; break;
                }
            }

            if (goodSpot == true) { //if checker didnt turn spot to flase then make mooncake
                food[foodCount] = new FoodItem(this, x, y, "images/food1mooncake.png");
                foodCount++;
            }
        }
    }
    
    
    public void draw(){
        switch (stage){
            case 0: //title page================================================
                background(loadImage("images/background_menu.png"));
                startButton.draw();
                helpButton.draw();
                break;
   
            case 1: //help page=================================================
                background(255); 
                textSize(20);
                fill(0);
                text("help screen",100, 100);
                break;
                
            case 2: //game page=================================================
                background(loadImage("images/background_moon.png"));
                addEgg.draw();
                addFlour.draw();
                addSyrup.draw();
                addWater.draw();
                
                if (keyPressed) {
                    if (keyCode == LEFT) {
                        attemptMove(-5, 0, -1, 0);

                    } else if (keyCode == RIGHT) {
                        attemptMove(5, 0, 1, 0);
                        
                    } else if (keyCode == UP) {
                        attemptMove(0, -5, 0, -1);
                        
                        
                    } else if (keyCode == DOWN) {
                        attemptMove(0, 5, 0, 1);

                    }
                }
                user.draw();
                for (int i = 0; i < foodCount; i++) {
                    if (food[i] != null) {food[i].draw();} //draw all food up to counter
                } 
                break;

            default: //fall through=============================================
                break;
        }   
    }
}
