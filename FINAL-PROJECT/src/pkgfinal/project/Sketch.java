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
    private UserRabbit user;
    private FoodItem food1;
    private Buttons startButton, helpButton;
    private int stage = 0;

    public void settings(){
        size(800,600);
    }
    public void setup(){
        background(loadImage("images/background_menu.png"));
        startButton = new Buttons(this, 255, 380, "images/button-start.png");
        helpButton = new Buttons(this, 620, 20, "images/button-help.png");
        user = new UserRabbit(this, 620, 400);
        food1 = new FoodItem(this, 300,300, "images/food1 mooncake (small).png"); // 65,450
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
                background(loadImage("images/background_moon.png"));
                user.draw();
                food1.draw();

                if (keyPressed){
                    if (keyCode == LEFT && !(user.isCollidingWith(food1) && food1.x == 33)) {
                        background(loadImage("images/background_moon.png"));
                        user.move(-10, 0);
                        user.display();
                        food1.move(-5,0);
                    } else if (keyCode == RIGHT && !(user.isCollidingWith(food1) && food1.x == 765 - food1.width)) {
                        user.move(5, 0);
                        food1.move(5,0);
                        user.display();
                    } else if (keyCode == UP && !(user.isCollidingWith(food1) && food1.y == 0)) {
                        user.move(0, -5);
                        food1.move(0,5);
                    } else if (keyCode == DOWN && !(user.isCollidingWith(food1) && food1.x == 520 - food1.height)) {
                        user.move(0, 5);
                        food1.move(0,-5);
                    }
                    food1.draw();
                }

                
                
                
                
                
                
                
                
                
                
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
