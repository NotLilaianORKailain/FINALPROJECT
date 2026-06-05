/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project;
import processing.core.PApplet;
import pkgfinal.project.FoodRelated.FoodItem;

/**
 *
 * @author 343479150
 */
public class Sketch extends PApplet {
    //declare needed var for game
    private Buttons startButton, helpButton;
    private UserRabbit user;
    private FoodItem food[] = new FoodItem[19];
    private int stage = 0;
    private int xShift, yShift;

    public void settings(){
        size(800,600); //set size to 800 pixel long by 600 pixel high
    }
    public void setup(){
        //start with main menu and have buttons and user rabbit ready
        background(loadImage("images/background_menu.png"));
        startButton = new Buttons(this, 255, 380, "images/button-start.png");
        helpButton = new Buttons(this, 620, 20, "images/button-help.png");
        user = new UserRabbit(this, 620, 400);

        //create all needed food items, either they will be used or use wont win
        for (int i = 0; i < 19; i++) {
            xShift = (int) (Math.random()*((3)-(-1)+1))+(-1);
            yShift = (int) (Math.random()*((1)-(-2)+1))+(-2);
            food[i] = new FoodItem(this, 507-145*xShift, 325-140*yShift, "images/food1mooncake.png");
        }
    }
 
    public void playGame(){
        
        background(loadImage("images/background_moon.png"));
        user.draw();
        food[1].draw();

        if (keyPressed) {
            if (keyCode == LEFT && !(user.isCollidingWith(food[1]) && food[1].x == 75)) {
                background(loadImage("images/background_moon.png"));
                user.move(-10, 0);
                user.display();
                if (user.isCollidingWith(food[1])){food[1].move(-145, 0);}

            } else if (keyCode == RIGHT && !(user.isCollidingWith(food[1]) && food[1].x == 652)) {
                user.move(5, 0);
                user.display();
                if (user.isCollidingWith(food[1])){food[1].move(145, 0);}
                
            } else if (keyCode == UP && !(user.isCollidingWith(food[1]) && food[1].y == 45)) {
                user.move(0, -5);
                if (user.isCollidingWith(food[1])){food[1].move(0, -140);}
                
            } else if (keyCode == DOWN && !(user.isCollidingWith(food[1]) && food[1].y == 445)) {
                user.move(0, 5);
                if (user.isCollidingWith(food[1])){food[1].move(0, 140);}
            }
            food[1].draw();
        }
    
    
    }
    
    
    public void draw(){
        
        switch (stage){

            case 0: //title page
                startButton.draw();
                helpButton.draw();
                break;
   
            case 1: //help page
                background(255); 
                textSize(20);
                fill(0);
                text("help screen",100, 100);
                break;
                
            case 2: //game page
                playGame();

            default:
                break;
      }   
    }
    
    public void mousePressed(){
        if (stage == 0) {
                if (helpButton.isClicked(mouseX,mouseY)){
                    stage = 1;
                } else if (startButton.isClicked(mouseX,mouseY)){
                    stage = 2;
                }
        }
    }
    
    
    
    
    
}
