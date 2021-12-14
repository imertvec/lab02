package yefimov483.figurepaint.GraphicsHelper;

public class Vec2 {
    public double x;
    public double y;

    public Vec2(){
        this.x = 0;
        this.y = 0;
    }

    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2 sum(Vec2 a, Vec2 b){
        return new Vec2(a.x + b.x, a.y + b.y);
    }

    public static Vec2 minus(Vec2 a, Vec2 b){
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    public static Vec2 minus(Vec2 a, double p){
        return new Vec2(a.x - p, a.y - p);
    }

    public Vec2 mul(Vec2 a, double p){
        return new Vec2(a.x * p, a.y * p);
    }

    public double len(){
        return Math.sqrt(x*x + y*y);
    }

    public Vec2 abs(){
        return new Vec2(Math.abs(this.x), Math.abs(this.y));
    }

    public Vec2 max(double p){
        return new Vec2(Math.max(x, p), Math.max(y, p));
    }

}
