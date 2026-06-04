/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project;
import processing.core.PApplet;

/**
 *
 * @author 343479150
 */
public class Sketch extends PApplet {
    //declare needed var for game
    private UserRabbit user;
    private Buttons startButton, helpButton;
    private int stage = 0;

    public void settings(){
        size(800,600);
    }
    public void setup(){
        background(loadImage("images/background_menu.png"));
        startButton = new Buttons(this, 255, 380, "images/button-start.png");
        helpButton = new Buttons(this, 620, 20, "images/button-help.png");
        user = new UserRabbit(this, 58, 100, "images/rabbit1.png");
    }
 
    public void draw(){
        
        switch (stage){
            //title page
            case 0:
                startButton.draw();
                helpButton.draw();
                break;
                
            //help page
            case 1:
                background(255); //clear screen
                textSize(20);
                fill(0);
                text("help screen",100, 100);
                break;
                
            case 2:
                background(loadImage("images/background_moon.png"));
                user.draw();

                if (keyPressed){
                    if (keyCode == LEFT) {
                        user.move(-5, 0);
                        user.display();
                    } else if (keyCode == RIGHT) {
                        user.move(5, 0);
                        user.display();
                    } else if (keyCode == UP) {
                        user.move(0, -5);
                    } else if (keyCode == DOWN) {
                        user.move(0, 5);
                    }
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
