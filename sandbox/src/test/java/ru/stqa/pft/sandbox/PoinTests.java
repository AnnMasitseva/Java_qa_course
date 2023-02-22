package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PoinTests {

    @Test
    public void testDistance(){
        Point p1 = new Point(2, 1);
        Point p2 = new Point(5, 6);
        Assert.assertEquals(p1.distance1(p2),5.830951894845301);
    }
    @Test
    public void testParams(){
        Point p1 = new Point(2,5);
        Point p2 = new Point(5,6);
        assert p1.x == 2;
        assert p1.y == 5;
        assert p2.x == 5;
        assert p2.y == 6;
    }
}
