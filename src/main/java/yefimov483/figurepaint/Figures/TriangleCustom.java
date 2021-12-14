package yefimov483.figurepaint.Figures;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import yefimov483.figurepaint.GraphicsHelper.RGB;
import yefimov483.figurepaint.GraphicsHelper.Vec2;

public class TriangleCustom extends Figure{
    public static int local_id = 0;
    public int side;

    public TriangleCustom(double side, Color color, Canvas canvas){
        param = side / 2;
        this.color = new RGB(color.getRed(), color.getGreen(), color.getBlue());
        this.canvas = canvas;
        this.name = "trianglecustom" + local_id;
        this.pos.x = (canvas.getWidth() / 2);
        this.pos.y = (canvas.getHeight() / 2);
        local_id++;
    }

    public void doDrawing(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        if(selected) g.setStroke(selColor);
        else g.setStroke(color.getColor());
        g.setLineWidth(3);
        g.strokePolygon(
                new double[]{pos.x - param, pos.x, pos.x + param},
                new double[]{pos.y + param, pos.y - param, pos.y + param }, 3);
    }

    @Override
    public boolean isSelected(double x, double y) {
        if(x >= pos.x - param*0.5 && x <= pos.x + param*0.5 && y >= pos.y - param*0.5 && y <= pos.y + param*0.5) return true;
        return false;
    }

    @Override
    public double sdf(Vec2 p){
        Vec2 d = Vec2.minus(p, pos).abs();
        Vec2 d1 = Vec2.minus(d, param*0.5);
        double innerD = Math.min(Math.max(d1.x, d1.y), 0);
        double outerD = d1.max(0).len();

        return innerD + outerD;
    }

}
