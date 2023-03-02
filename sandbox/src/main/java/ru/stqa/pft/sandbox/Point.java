package ru.stqa.pft.sandbox;

public class Point {
    private int x;
    private int y;

    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }


    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double distance1(Point p2) {
        return Math.sqrt((Math.pow((p2.x-this.x),2)+Math.pow((p2.y-this.y),2)));
    }
}

