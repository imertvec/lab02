package yefimov483.figurepaint.Figures;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import yefimov483.figurepaint.GraphicsHelper.RGB;
import yefimov483.figurepaint.GraphicsHelper.Vec2;

public class CircleCustom extends Figure{
    private double halfSqX;
    private double halfSqY;
    static public int local_id = 0;

    public CircleCustom(double radius, Color color, Canvas canvas){
        this.color = new RGB(color.getRed(), color.getGreen(), color.getBlue());
        this.param = radius;
        this.canvas = canvas;
        this.pos.x = (canvas.getWidth() / 2);
        this.pos.y = (canvas.getHeight() / 2);
        local_id++;
        this.name = "cirlcecustom" + local_id;

    }

    public void doDrawing(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        if(selected) g.setStroke(selColor);
        else g.setStroke(color.getColor());
        g.setLineWidth(3);
        g.strokeOval(pos.x- param*0.5, pos.y- param*0.5, param, param);
    }

    @Override
    public boolean isSelected(double curX, double curY) {
        double squaredR = param*param*0.25;
        halfSqX = curX - pos.x;
        halfSqY = curY - pos.y;
        if(halfSqX * halfSqX + halfSqY * halfSqY<= squaredR) return true;
        return false;
    }

    @Override
    public double sdf(Vec2 p) {
        return (Vec2.minus(pos, p)).len() - param;
    }
}
