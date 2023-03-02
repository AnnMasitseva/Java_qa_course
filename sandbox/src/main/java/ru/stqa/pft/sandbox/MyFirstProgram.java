package ru.stqa.pft.sandbox;

public class MyFirstProgram {
    
    public static void main(String[] args) {
        Point p1 = new Point(2, 1);
        Point p2 = new Point(5, 6);
        System.out.println("Расстояние между двумя точками с координатами " + '(' + p1.getX() + ";" + p1.getY() + ')'  + " и " + '(' + p2.getX() + ";" + p2.getY() + ')' + " = " + distance(p1,p2));


        System.out.println("Расстояние между двумя точками p1 и p2 " + p1.distance1(p2));

    }

   public static double distance(Point p1, Point p2){
        return Math.sqrt((Math.pow((p2.getX() - p1.getX()),2)+Math.pow((p2.getY() - p1.getY()),2)));
    }
}
    

