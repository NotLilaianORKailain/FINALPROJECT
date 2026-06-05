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
    private int x, y;
    private int width, height;
    
    private PApplet app;
    private PImage image;
    
    public static int imageCount = 0;
    private String[] rabbit = {"rabbit1.png", "rabbit2.png", 
                               "rabbit3.png", "rabbit4.png", 
                               "rabbit5.png", "rabbit6.png", "rabbit7.png",};
    private PImage[] rabbitImages = new PImage[rabbit.length];
    
    
    public UserRabbit(PApplet p, int x, int y) {
        this.app = p;
        this.x = x; this.y = y; //location of picture

        //loop through and preload images of rabbits walking
        for (int i = 0; i < rabbit.length; i++) {
            rabbitImages[i] = app.loadImage("images/" + rabbit[i]);
        }
        
        image = rabbitImages[0]; //first picture as rabbit start
        this.width = image.width; this.height = image.height; //picture size
    }
    
    public void move(int dx, int dy) {
        //move rabbit to new spot
        x += dx; y += dy;
        
        //if frame outline reached then reset the movemnt to border
        x = Math.max(33, Math.min(x, 765 - width));
        y = Math.max(-15, Math.min(y, 520 - height));
        
        //load new frame for animation
        imageCount = (imageCount + 1) % rabbitImages.length;
    }
    
    public void display() {
        app.image(rabbitImages[imageCount], x, y);
    }
    
    public void draw() {
        app.image(image, x, y); //draw image at position
    }
    
    public boolean isCollidingWith(FoodItem other) {
        // calculates the center of this image 
        int centerX = x + (width / 2);
        int centerY = y + (height / 2);
        // calculates the center of the other image 
        int otherCenterX = other.x + (other.image.pixelWidth / 2);
        int otherCenterY = other.y + (other.image.pixelHeight / 2);
        // calcalutes the distance between the two center points
        float d = PApplet.dist(otherCenterX, otherCenterY, centerX, centerY);
        
//        app.ellipse(centerX, centerY, width, height);
//        app.ellipse(otherCenterX, otherCenterY, other.image.pixelWidth, other.image.pixelHeight);
        
        // returns true if  the distance between the 2 cener points is
        // less than 32 pixels
        return d < 110;
    }
//
//    public boolean isClicked(int mouseX, int mouseY) {
//        /*calculates distance from mouse click at mouseX and mouseY to center 
//        * of image since (x,y) of image is postioned at the top left corner  
//        * we use x+(image.pixelWidth/2), y+(image.pixelHeight/2)) to get center*/
//        int centerX = x+(image.pixelWidth/2);
//        int centerY = y+(image.pixelHeight/2);        
//        float d = PApplet.dist(mouseX, mouseY, centerX ,centerY );
//
//        //gives us the dimensions of the image 32px by 32px
//        System.out.println("image height"+image.pixelHeight);
//        System.out.println("image width"+image.pixelWidth);
//
//        // returns true if  mouse clicked is within 16px from the center of image
//        // we use 16px because the image is 32px by 32px
//        return d < 16; 
//  }
//    
//    public void displayInfo(PApplet p){
//    app.fill(0);
//    app.text("X and Y: ("+x+" , "+y+")", x, y-30);
//    app.text("Speed: "+speed, x, y-10);
//    }
}
