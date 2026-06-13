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

public class Syrup extends FoodItem {
    //constructor of a subclass item that just feeds into parent via super
    public Syrup(PApplet p, int x, int y, String imagePath) {
        super(p,x,y,imagePath);
    }
}
