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
public class Buttons {
    //declare needed var to draw and position
    private PApplet p;
    private float x, y;
    private PImage img;

    //constructor that needs (x,y) and path to PNG image file
    public Buttons(PApplet p, float x, float y, String imagePath) {
      this.p = p;
      this.x = x;
      this.y = y;
      this.img = p.loadImage(imagePath); //load the PNG
    }

    public void draw() {
      //draw image at designated x and y coordinates
      p.image(img, x, y);
    }

    //rectangular collision detection using image dimensions
    public boolean isClicked(float mx, float my) {
      return (mx >= x && mx <= x + img.width && my >= y && my <= y + img.height);
    }
    
}
