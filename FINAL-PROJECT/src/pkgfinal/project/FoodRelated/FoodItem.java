/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project.FoodRelated;

import pkgfinal.project.Sketch;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 343479150
 */
public class FoodItem {
    //declare needed var to set up each food item
    public int x, y;
    public int width, height;
    protected PApplet app;
    public PImage image;

    private final int DEFAULT_X = Sketch.ROWS[0];    
    private final int DEFAULT_Y = Sketch.COLS[0];

    //constructor with set location
    public FoodItem(PApplet p, int x, int y, String imagePath) {
        this.app = p;
        this.image = app.loadImage(imagePath);
        this.x = x; this.y = y; //location of picture
        this.width = image.width; this.height = image.height; //picture size
    }
    
     //constructor with defalut location
    public FoodItem(PApplet p, String imagePath) {
        this.app = p;
        this.image = app.loadImage(imagePath);
        this.x = DEFAULT_X; this.y = DEFAULT_Y; //defalut location
        this.width = image.width; this.height = image.height; //picture size
    }
    
    //method of how food moves while staying within frame (left and right)
    public void moveX(int step) {
        //loop and find current box, check if inbound, then move to next step x
        for (int i = 0; i < Sketch.COLS.length; i++) {
            if (x == Sketch.COLS[i]) {
                int newBox = i + step; //bound to be checked in next if
                if (newBox >= 0 && newBox < Sketch.COLS.length) {
                    x = Sketch.COLS[newBox]; //if in bound change postion
                } return;
            }
       }
    }
    
    //method of how food moves while staying within frame (up and down)
    public void moveY(int step) {
        //loop and find current box, check if inbound, then move to next step y
        for (int i = 0; i < Sketch.ROWS.length; i++) {
            if (y == Sketch.ROWS[i]) {
                int newBox = i + step; //bound to be checked in next if
                if (newBox >= 0 && newBox < Sketch.ROWS.length) {
                    y = Sketch.ROWS[newBox]; //if in bound change postion
                } return;
                        
            }
       }
    }

    //method to draw each food in the center of the gird boxes
    public void draw() {
        app.image(image, x-image.width/2, y-image.height/2); //draw image at position
    }

}
