/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.project.FoodRelated;
import processing.core.PApplet;

/**
 *
 * @author 343479150
 */
public class Mooncake extends FoodItem {
    
    public Mooncake(PApplet p, int x, int y, String imagePath) {
        super(p,x,y,imagePath);
    }
    
    @Override
    //a drawn mooncake will have a highlighting circle
    public void draw() {
        app.noStroke();
        app.fill(255, 255, 0, 80);
        app.circle(x, y, 120);
        super.draw();
    }
}
