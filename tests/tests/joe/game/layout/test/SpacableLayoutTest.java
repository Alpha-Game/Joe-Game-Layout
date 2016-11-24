package tests.joe.game.layout.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Collection;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.layout.implementation.SpacableLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;

import tests.joe.game.layout.mock.DrawableMock;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	SpacableLayoutTest.getDimension.class,
	SpacableLayoutTest.draw.class
})
public class SpacableLayoutTest {
	
	@RunWith(Parameterized.class)
	public static class getDimension {
	    @Parameter(0)
	    public String fTestName;
	    
	    @Parameter(1)    
	    public double fDrawableWidth;
	    
	    @Parameter(2)    
	    public double fDrawableHeight;
	    
	    @Parameter(3)    
	    public double fSpacerLeft;
	    
	    @Parameter(4)    
	    public double fSpacerRight;
	    
	    @Parameter(5)    
	    public double fSpacerTop;
	    
	    @Parameter(6)    
	    public double fSpacerBottom;
	    
	    @Parameter(7)    
	    public double fExpectedWidth;
	    
	    @Parameter(8)    
	    public double fExpectedHeight;
	    
	    @Parameters(name= "{index}: {0}")
	    public static Collection<Object[]> data() {
	    	return Arrays.asList(new Object[][] {
	    		{ "No Image Size, No Spacer Size", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
	    		{ "Positive Image Size, No Spacer Size", 10.0, 20.0, 0.0, 0.0, 0.0, 0.0, 10.0, 20.0 },
	    		{ "No Image Size, Positive Spacer Size", 0.0, 0.0, 5.0, 10.0, 15.0, 20.0, 15.0, 35.0 },
	    		{ "Positive Image Size, Positive Spacer Size", 10.0, 20.0, 5.0, 10.0, 15.0, 20.0, 25.0, 55.0 },
	    		{ "No Image Size, Negative Spacer Size", 0.0, 0.0, -5.0, -10.0, -15.0, -20.0, 15.0, 35.0 },
	    		{ "Positive Image Size, Negative Spacer Size", 10.0, 20.0, -5.0, -10.0, -15.0, -20.0, 25.0, 55.0 },
	    		{ "Negative Image Size, Negative Spacer Size", -10.0, -20.0, 0.0, 0.0, 0.0, 0.0, Double.NaN, Double.NaN },
	    		{ "Negative Image X Size, Negative Spacer Size", -10.0, 20.0, 0.0, 0.0, 0.0, 0.0, Double.NaN, Double.NaN },
	    		{ "Negative Image Y Size, Negative Spacer Size", 10.0, -20.0, 0.0, 0.0, 0.0, 0.0, Double.NaN, Double.NaN },
	    		
	    	});
	    }
	    
	    @Test
	    public void test() throws Exception {
			DrawableMock drawable = createDrawable(fDrawableWidth, fDrawableHeight);
			SpacableLayout layout = createLayout(drawable, fSpacerLeft, fSpacerRight, fSpacerTop, fSpacerBottom);
			
			try {
				assertDimension(layout.getDimension(), fExpectedWidth, fExpectedHeight);
				assertTrue(fDrawableWidth >= 0.0);
				assertTrue(fDrawableHeight >= 0.0);
			} catch (IllegalStateException ex) {
				assertTrue(fDrawableWidth < 0.0 || fDrawableHeight < 0.0);
			}
		}
	}
	
	@RunWith(Parameterized.class)
	public static class draw {
	    @Parameter(0)
	    public String fTestName;
	    
	    @Parameter(1)    
	    public double fDrawableWidth;
	    
	    @Parameter(2)    
	    public double fDrawableHeight;
	    
	    @Parameter(3)    
	    public double fSpacerLeft;
	    
	    @Parameter(4)    
	    public double fSpacerRight;
	    
	    @Parameter(5)    
	    public double fSpacerTop;
	    
	    @Parameter(6)    
	    public double fSpacerBottom;
	    
	    @Parameter(7)    
	    public Rectangle2D[] fDrawRectangles;
	    
	    @Parameter(8)    
	    public Rectangle2D[] fOutputtedRectangles;
	    
	    @Parameters(name= "{index}: {0}")
	    public static Collection<Object[]> data() {
	    	return Arrays.asList(new Object[][] {
	    		{ "No Image Size, No Spacer Size, Right Size, No Point", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, 0, 0) }, new Rectangle2D[] { } },
	    		{ "No Image Size, No Spacer Size, Larger Size, No Point", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, 10, 20) }, new Rectangle2D[] { } },
	    		{ "Negative Image Size, No Spacer Size, Right Size, No Point", -10.0, -20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, -10, -20) }, new Rectangle2D[] { } },
	    		{ "Negative Image X Size, No Spacer Size, Right Size, No Point", -10.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, -10, 20) }, new Rectangle2D[] { } },
	    		{ "Negative Image Y Size, No Spacer Size, Right Size, No Point", 10.0, -20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, 10, -20) }, new Rectangle2D[] { } },
	    		
	    		{ "Positive Image Size, No Spacer Size, Positive Right Size, No Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, 16, 20) }, new Rectangle2D[] { createRectangle(0, 0, 16, 20) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Smaller Size, No Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, 8, 5) }, new Rectangle2D[] { createRectangle(0, 0, 8, 5) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Larger Size, No Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, 32, 60) }, new Rectangle2D[] { createRectangle(0, 0, 32, 60) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Right Size, No Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, -16, -20) }, new Rectangle2D[] { createRectangle(0, 0, -16, -20) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Smaller Size, No Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, -8, -5) }, new Rectangle2D[] { createRectangle(0, 0, -8, -5) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Larger Size, No Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(0, 0, -32, -60) }, new Rectangle2D[] { createRectangle(0, 0, -32, -60) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Right Size, Positive Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(4, 5, 16, 20) }, new Rectangle2D[] { createRectangle(4, 5, 16, 20) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Smaller Size, Positive Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(4, 5, 8, 5) }, new Rectangle2D[] { createRectangle(4, 5, 8, 5) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Larger Size, Positive Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(4, 5, 32, 60) }, new Rectangle2D[] { createRectangle(4, 5, 32, 60) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Right Size, Positive Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(4, 5, -16, -20) }, new Rectangle2D[] { createRectangle(4, 5, -16, -20) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Smaller Size, Positive Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(4, 5, -8, -5) }, new Rectangle2D[] { createRectangle(4, 5, -8, -5) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Larger Size, Positive Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(4, 5, -32, -60) }, new Rectangle2D[] { createRectangle(4, 5, -32, -60) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Right Size, Negative Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(-4, -5, 16, 20) }, new Rectangle2D[] { createRectangle(-4, -5, 16, 20) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Smaller Size, Negative Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(-4, -5, 8, 5) }, new Rectangle2D[] { createRectangle(-4, -5, 8, 5) } },
	    		{ "Positive Image Size, No Spacer Size, Positive Larger Size, Negative Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(-4, -5, 32, 60) }, new Rectangle2D[] { createRectangle(-4, -5, 32, 60) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Right Size, Negative Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(-4, -5, -16, -20) }, new Rectangle2D[] { createRectangle(-4, -5, -16, -20) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Smaller Size, Negative Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(-4, -5, -8, -5) }, new Rectangle2D[] { createRectangle(-4, -5, -8, -5) } },
	    		{ "Positive Image Size, No Spacer Size, Negative Larger Size, Negative Point", 16.0, 20.0, 0.0, 0.0, 0.0, 0.0, new Rectangle2D[] { createRectangle(-4, -5, -32, -60) }, new Rectangle2D[] { createRectangle(-4, -5, -32, -60) } },
	    		
	    		{ "Positive Image Size, Positive Spacer Size, Positive Right Size, No Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(0, 0, 22, 36) }, new Rectangle2D[] { createRectangle(2, 6, 16, 20) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Smaller Size, No Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(0, 0, 11, 12) }, new Rectangle2D[] { createRectangle(1, 2, 8, 20.0/3.0) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Larger Size, No Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(0, 0, 66, 72) }, new Rectangle2D[] { createRectangle(6, 12, 48, 40) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Right Size, No Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(0, 0, -22, -36) }, new Rectangle2D[] { createRectangle(-2, -6, -16, -20) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Smaller Size, No Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(0, 0, -11, -12) }, new Rectangle2D[] { createRectangle(-1, -2, -8, -20.0/3.0) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Larger Size, No Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(0, 0, -66, -72) }, new Rectangle2D[] { createRectangle(-6, -12, -48, -40) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Right Size, Positive Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(4, 5, 22, 36) }, new Rectangle2D[] { createRectangle(6, 11, 16, 20) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Smaller Size, Positive Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(4, 5, 11, 12) }, new Rectangle2D[] { createRectangle(5, 7, 8, 20.0/3.0) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Larger Size, Positive Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(4, 5, 66, 72) }, new Rectangle2D[] { createRectangle(10, 17, 48, 40) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Right Size, Positive Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(4, 5, -22, -36) }, new Rectangle2D[] { createRectangle(2, -1, -16, -20) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Smaller Size, Positive Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(4, 5, -11, -12) }, new Rectangle2D[] { createRectangle(3, 3, -8, -20.0/3.0) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Larger Size, Positive Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(4, 5, -66, -72) }, new Rectangle2D[] { createRectangle(-2, -7, -48, -40) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Right Size, Negative Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(-4, -5, 22, 36) }, new Rectangle2D[] { createRectangle(-2, 1, 16, 20) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Smaller Size, Negative Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(-4, -5, 11, 12) }, new Rectangle2D[] { createRectangle(-3, -3, 8, 20.0/3.0) } },
	    		{ "Positive Image Size, Positive Spacer Size, Positive Larger Size, Negative Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(-4, -5, 66, 72) }, new Rectangle2D[] { createRectangle(2, 7, 48, 40) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Right Size, Negative Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(-4, -5, -22, -36) }, new Rectangle2D[] { createRectangle(-6, -11, -16, -20) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Smaller Size, Negative Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(-4, -5, -11, -12) }, new Rectangle2D[] { createRectangle(-5, -7, -8, -20.0/3.0) } },
	    		{ "Positive Image Size, Positive Spacer Size, Negative Larger Size, Negative Point", 16.0, 20.0, 2.0, 4.0, 6.0, 10.0, new Rectangle2D[] { createRectangle(-4, -5, -66, -72) }, new Rectangle2D[] { createRectangle(-10, -17, -48, -40) } },
	    		
	    		{ "Positive Image Size, Negative Spacer Size, Positive Right Size, No Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(0, 0, 22, 36) }, new Rectangle2D[] { createRectangle(2, 6, 16, 20) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Smaller Size, No Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(0, 0, 11, 12) }, new Rectangle2D[] { createRectangle(1, 2, 8, 20.0/3.0) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Larger Size, No Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(0, 0, 66, 72) }, new Rectangle2D[] { createRectangle(6, 12, 48, 40) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Right Size, No Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(0, 0, -22, -36) }, new Rectangle2D[] { createRectangle(-2, -6, -16, -20) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Smaller Size, No Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(0, 0, -11, -12) }, new Rectangle2D[] { createRectangle(-1, -2, -8, -20.0/3.0) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Larger Size, No Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(0, 0, -66, -72) }, new Rectangle2D[] { createRectangle(-6, -12, -48, -40) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Right Size, Positive Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(4, 5, 22, 36) }, new Rectangle2D[] { createRectangle(6, 11, 16, 20) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Smaller Size, Positive Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(4, 5, 11, 12) }, new Rectangle2D[] { createRectangle(5, 7, 8, 20.0/3.0) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Larger Size, Positive Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(4, 5, 66, 72) }, new Rectangle2D[] { createRectangle(10, 17, 48, 40) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Right Size, Positive Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(4, 5, -22, -36) }, new Rectangle2D[] { createRectangle(2, -1, -16, -20) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Smaller Size, Positive Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(4, 5, -11, -12) }, new Rectangle2D[] { createRectangle(3, 3, -8, -20.0/3.0) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Larger Size, Positive Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(4, 5, -66, -72) }, new Rectangle2D[] { createRectangle(-2, -7, -48, -40) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Right Size, Negative Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(-4, -5, 22, 36) }, new Rectangle2D[] { createRectangle(-2, 1, 16, 20) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Smaller Size, Negative Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(-4, -5, 11, 12) }, new Rectangle2D[] { createRectangle(-3, -3, 8, 20.0/3.0) } },
	    		{ "Positive Image Size, Negative Spacer Size, Positive Larger Size, Negative Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(-4, -5, 66, 72) }, new Rectangle2D[] { createRectangle(2, 7, 48, 40) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Right Size, Negative Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(-4, -5, -22, -36) }, new Rectangle2D[] { createRectangle(-6, -11, -16, -20) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Smaller Size, Negative Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(-4, -5, -11, -12) }, new Rectangle2D[] { createRectangle(-5, -7, -8, -20.0/3.0) } },
	    		{ "Positive Image Size, Negative Spacer Size, Negative Larger Size, Negative Point", 16.0, 20.0, -2.0, -4.0, -6.0, -10.0, new Rectangle2D[] { createRectangle(-4, -5, -66, -72) }, new Rectangle2D[] { createRectangle(-10, -17, -48, -40) } },
	    	});
	    }
	    
	    @Test
	    public void test() throws Exception {
	    	DrawableMock drawable = createDrawable(fDrawableWidth, fDrawableHeight);
			SpacableLayout layout = createLayout(drawable, fSpacerLeft, fSpacerRight, fSpacerTop, fSpacerBottom);
			
			for (Rectangle2D drawRectangle : fDrawRectangles) {
				try {
					layout.draw(createGraphic(), drawRectangle);
					assertTrue(fDrawableWidth >= 0.0);
					assertTrue(fDrawableHeight >= 0.0);
				} catch (IllegalStateException ex) {
					assertTrue(fDrawableWidth < 0.0 || fDrawableHeight < 0.0);
				}
			}
			
			assertEquals(fOutputtedRectangles.length, drawable.getTotalUseCount());
			for(int i = 0; i < fOutputtedRectangles.length; i++) {
				assertRectangle(fOutputtedRectangles[i], drawable.getRectangle(i));
			}
		}
	}
	
    private static SpacableLayout createLayout(IDrawable item, double spacerLeft, double spacerRight, double spacerTop, double spacerBottom) {
		return new SpacableLayout(item, spacerLeft, spacerRight, spacerTop, spacerBottom);
	}
	
	private static DrawableMock createDrawable(double width, double height) {
		return new DrawableMock(LayoutCalculator.createDimension(width, height));
	}
    
    private static void assertDimension(Dimension2D dimension, double width, double height) {
		assertEquals(width, dimension.getWidth(), 0.0d);
		assertEquals(height, dimension.getHeight(), 0.0d);
	}
	
	private static Graphics2D createGraphic() {
		return null;
	}
	
	private static Rectangle2D createRectangle(double x, double y, double width, double height) {
		return LayoutCalculator.createRectangle(x, y, width, height);
	}
	
	private static void assertRectangle(Rectangle2D expected, Rectangle2D actual) {
		assertEquals(expected.getX(), actual.getX(), 0.0d);
		assertEquals(expected.getY(), actual.getY(), 0.0d);
		assertEquals(expected.getWidth(), actual.getWidth(), 0.0d);
		assertEquals(expected.getHeight(), actual.getHeight(), 0.0d);
		
		assertEquals(expected.getMinX(), actual.getMinX(), 0.0d);
		assertEquals(expected.getMinY(), actual.getMinY(), 0.0d);
		assertEquals(expected.getCenterX(), actual.getCenterX(), 0.0d);
		assertEquals(expected.getCenterY(), actual.getCenterY(), 0.0d);
		assertEquals(expected.getMaxX(), actual.getMaxX(), 0.0d);
		assertEquals(expected.getMaxY(), actual.getMaxY(), 0.0d);
	}
}
