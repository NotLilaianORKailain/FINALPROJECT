/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project.FoodRelated;

import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 343479150
 */
public class FoodItem {
    //declare needed var to set up users rabbit
    public int x, y;
    public int width, height;
    private PApplet app;
    public PImage image;
    
    public FoodItem(PApplet p, int x, int y, String imagePath) {
        this.app = p;
        this.image = app.loadImage(imagePath);
        this.x = x; this.y = y; //location of picture
        this.width = image.width; this.height = image.height; //picture size
    }
    
    public void move(int dx, int dy) {
        x += dx; y += dy;
        
        //if frame outline reached then reset the movemnt to border
        x = Math.max(75, Math.min(x, 727 - width));
        y = Math.max(45, Math.min(y, 520 - height));
    }
    
    public void draw() {
        app.image(image, x, y); //draw image at position
    }

}
