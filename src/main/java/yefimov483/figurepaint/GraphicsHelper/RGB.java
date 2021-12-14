package yefimov483.figurepaint.GraphicsHelper;

import javafx.scene.paint.Color;

import java.util.Random;

public class RGB {
    private double r;
    private double g;
    private double b;

    public RGB(double r, double g, double b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RGB(double x){
        this.r = x;
        this.g = x;
        this.b = x;
    }

    public RGB(){
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public static RGB getRandom(){
        Random rnd = new Random();
        return new RGB(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
    }

    public Color getColor(){
        r = Math.min(Math.max(r, 0), 1);
        g = Math.min(Math.max(g, 0), 1);
        b = Math.min(Math.max(b, 0), 1);
        return new Color(this.r, this.g, this.b, 1.0);
    }

    public RGB sum(RGB a, RGB b){
        return new RGB(a.r + b.r, a.g + b.g, a.b + b.b);
    }

    public RGB mul(RGB a, double x){
        return new RGB(a.r * x, a.g * x, a.b * x);
    }

}
