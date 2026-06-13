/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project;
import pkgfinal.project.FoodRelated.FoodItem;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 343479150
 */
public class UserRabbit {
    //declare needed var to set up users rabbit
    public int x, y;
    private PApplet app;
    private PImage image; private int width, height;
    public static int imageCount = 0; 
    private String[] rabbit = {"rabbit1.png", "rabbit2.png", 
                               "rabbit3.png", "rabbit4.png", 
                               "rabbit5.png", "rabbit6.png", "rabbit7.png",};
    private PImage[] rabbitImages = new PImage[rabbit.length];
    
    //constructor that can handle the looping animation of the rabbit
    public UserRabbit(PApplet p, int x, int y) {
        this.app = p;
        this.x = x; this.y = y; //location of picture

        //loop through and preload images of rabbits walking
        for (int i = 0; i < rabbit.length; i++) {
            rabbitImages[i] = app.loadImage("1images/" + rabbit[i]);}
        
        image = rabbitImages[0]; //first picture as rabbit start
        this.width = image.width; this.height = image.height; //picture size
    }
    
    //method that allows rabbit it move so long that it is within the frame
    public void move(int dx, int dy) {
        //move rabbit to new spot
        x += dx; y += dy;
        
        //if frame outline reached then reset the movemnt to border
        x = Math.max(33, Math.min(x, 765 - width));
        y = Math.max(-15, Math.min(y, 520 - height));
        
        //load new frame for animation
        imageCount = (imageCount + 1) % rabbitImages.length;
    }
    
    //method that display rabbit according to the image counter
    public void draw() {
        app.image(rabbitImages[imageCount], x, y);
    }
    
    //method that check if rabbit is colliding with another food item
    public boolean isCollidingWith(FoodItem other) {
        // calculates the center of rabbit
        int centerX = x + (width / 2);
        int centerY = y + (height / 2);
        
        // calculates the center of the other image 
        int otherCenterX = other.x + (other.image.pixelWidth / 2);
        int otherCenterY = other.y + (other.image.pixelHeight / 2);
        
        // calcalutes the distance between the two center points
        float d = PApplet.dist(otherCenterX, otherCenterY, centerX, centerY);

        // returns true if the distance between is less than 90
        return d < 90;
    }
}
