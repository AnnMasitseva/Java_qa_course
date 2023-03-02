package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance() {
        Point p1 = new Point(2, 1);
        Point p2 = new Point(5, 6);
        Assert.assertEquals(p1.distance1(p2), 5.830951894845301);
    }

    @Test
    public void testParams() {
        Point p1 = new Point(3, 0);
        assert p1.getX() == 3;
        assert p1.getY() == 0;
        p1.setX(-1);
        p1.setY(5);
        assert p1.getX() == -1;
        assert p1.getY() == 5;

    }
}
