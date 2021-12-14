package yefimov483.figurepaint.Figures;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import yefimov483.figurepaint.GraphicsHelper.RGB;
import yefimov483.figurepaint.GraphicsHelper.Vec2;

abstract public class Figure extends Canvas {
    public RGB color = new RGB();
    public Color selColor = Color.RED;
    public Canvas canvas;
    public Vec2 pos = new Vec2();
    public boolean selected = false;
    public String name;
    public double param;

    public abstract void doDrawing();

    public abstract boolean isSelected(double x, double y);

    public abstract double sdf(Vec2 p);
}
