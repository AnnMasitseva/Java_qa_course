package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PoinTests {

    @Test
    public void testDistance(){
        PointMetod p = new PointMetod(2, 1, 5,6);
        assert p.distance() == 5.830951894845301;
        Assert.assertEquals(p.distance(),5.830951894845301);
    }
}
