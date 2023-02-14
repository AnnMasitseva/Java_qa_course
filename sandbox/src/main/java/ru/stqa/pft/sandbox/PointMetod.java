package ru.stqa.pft.sandbox;

public class PointMetod {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public PointMetod(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public double distance(){
        return Math.sqrt((Math.pow((x2-x1),2)+Math.pow((y2-y1),2)));
    }
}
