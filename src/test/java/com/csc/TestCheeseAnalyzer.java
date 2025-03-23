package com.csc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCheeseAnalyzer {

    @Test
    public void testCheeseQuality() {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        String cheeseData = "This cheese is of high quality.";
        boolean isCheeseGood = analyzer.checkQuality(cheeseData);
        assertTrue(isCheeseGood, "Cheese quality should be good");
    }

    @Test
    public void testCheeseAging() {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        String cheeseData = "This cheese has been aged for 12 months.";
        boolean isCheeseAgedProperly = analyzer.checkAging(cheeseData);
        assertTrue(isCheeseAgedProperly, "Cheese should be aged properly");
    }

    @Test
    public void testCheeseFlavor() {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        String cheeseData = "This cheese has a rich and creamy flavor.";
        boolean isFlavorGood = analyzer.checkFlavor(cheeseData);
        assertTrue(isFlavorGood, "Cheese flavor should be good");
    }

    @Test
    public void testCheeseTexture() {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        String cheeseData = "This cheese has a smooth texture.";
        boolean isTextureGood = analyzer.checkTexture(cheeseData);
        assertTrue(isTextureGood, "Cheese texture should be good");
    }

    @Test
    public void testCheeseColor() {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        String cheeseData = "This cheese has a golden color.";
        boolean isColorGood = analyzer.checkColor(cheeseData);
        assertTrue(isColorGood, "Cheese color should be good");
    }

    @Test
    public void testCheeseSmell() {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        String cheeseData = "This cheese has a pleasant smell.";
        boolean isSmellGood = analyzer.checkSmell(cheeseData);
        assertTrue(isSmellGood, "Cheese smell should be good");
    }

    @Test
    public void testCheeseFailCase() {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        String cheeseData = "This cheese is spoiled.";
        boolean shouldFail = analyzer.checkQuality(cheeseData);
        if (!shouldFail) {
            fail("This test case should fail because the cheese is spoiled");
        }
    }
}