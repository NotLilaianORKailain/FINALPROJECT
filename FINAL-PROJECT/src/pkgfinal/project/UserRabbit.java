/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project;
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
    
    public UserRabbit(PApplet p, int x, int y, String imagePath) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = app.loadImage(imagePath);
        this.width = image.width;
        this.height = image.height;
    }
    
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        
        imageCount = (imageCount + 1) % rabbit.length;
        
    }
    
    
    public void display() {
        app.image(app.loadImage("images/" + rabbit[imageCount]), x, y);
    }
    
    public void draw() {
        app.image(image, x, y); //draw image at position
    }
    
//    public boolean isCollidingWith(Car other) {
//     
//      // Check if the bounding boxes of the two persons intersect
//      boolean isLeftOfOtherRight = x < other.x + other.width;
//      boolean isRightOfOtherLeft = x + width > other.x;
//      boolean isAboveOtherBottom = y < other.y + other.height;
//      boolean isBelowOtherTop = y + height > other.y;
//      
//      return isLeftOfOtherRight && isRightOfOtherLeft 
//        && isAboveOtherBottom && isBelowOtherTop;
//    }
//
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
