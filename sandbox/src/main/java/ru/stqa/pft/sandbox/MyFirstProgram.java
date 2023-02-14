package ru.stqa.pft.sandbox;

public class MyFirstProgram {
    
    public static void main(String[] args) {
        Point p1 = new Point(2, 1);
        Point p2 = new Point(5, 6);
        System.out.println("Расстояние между двумя точками с координатами " + '(' + p1.x + ";" + p1.y + ')'  + " и " + '(' + p2.x + ";" + p2.y + ')' + " = " + distance(p1,p2));

        PointMetod p = new PointMetod(2, 1, 5,6);
        System.out.println("Расстояние между двумя точками p1 и p2 " + p.distance());

    }

   public static double distance(Point p1, Point p2){
        return Math.sqrt((Math.pow((p2.x-p1.x),2)+Math.pow((p2.y-p1.y),2)));
    }
}
    

