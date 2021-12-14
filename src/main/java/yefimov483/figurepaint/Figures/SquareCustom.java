package yefimov483.figurepaint.Figures;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import yefimov483.figurepaint.GraphicsHelper.RGB;
import yefimov483.figurepaint.GraphicsHelper.Vec2;

public class SquareCustom extends Figure{
    static public int local_id = 0;

    public SquareCustom(double side, Color color, Canvas canvas){
        this.color = new RGB(color.getRed(), color.getGreen(), color.getBlue());
        this.param = side;
        this.canvas = canvas;
        this.pos.x = (canvas.getWidth() / 2);
        this.pos.y = (canvas.getHeight() / 2);
        local_id++;
        this.name = "squarecustom" + local_id;
    }

    public void doDrawing(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        if(selected) g.setStroke(selColor);
        else g.setStroke(color.getColor());
        g.setLineWidth(3);
        g.strokeRect(pos.x - param*0.5, pos.y - param*0.5, param, param);
    }

    public boolean isSelected(double curX, double curY){
        if(curX >= pos.x - param*0.5 && curX <= pos.x + param*0.5 && curY >= pos.y - param*0.5 && curY <= pos.y + param*0.5) return true;
        return false;
    }

    @Override
    public double sdf(Vec2 p) {
        Vec2 d = Vec2.minus(p, pos).abs();
        Vec2 d1 = Vec2.minus(d, param*0.5);
        double innerD = Math.min(Math.max(d1.x, d1.y), 0);
        double outerD = d1.max(0).len();

        return innerD + outerD;
    }


}
