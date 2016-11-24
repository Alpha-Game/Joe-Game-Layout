package tests.joe.game.layout.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Point2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.layout.implementation.Position;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LayoutCalculatorTest.createPoint.class,
	LayoutCalculatorTest.createDimension.class,
	LayoutCalculatorTest.createRectangle.class,
	LayoutCalculatorTest.createBox.class,
	LayoutCalculatorTest.getMarginRectangle.class,
	LayoutCalculatorTest.getAbsoluteInnerRectangle.class
})
public class LayoutCalculatorTest {
	public static class createPoint {
		@Test
		public void BasicTest() throws Exception {
			assertPoint(LayoutCalculator.createPoint(1, 2), 1, 2);
		}
	}
	
	public static class createDimension {
		@Test
		public void BasicTest() throws Exception {
			assertDimension(LayoutCalculator.createDimension(1, 2), 1, 2);
		}
	}
	
	public static class createRectangle {
		@Test
		public void FourValues() throws Exception {
			assertRectangle(LayoutCalculator.createRectangle(1, 2, 3, 4), 1, 2, 3, 4);
		}
		
		@Test
		public void TwoValues() throws Exception {
			Point2D point = LayoutCalculator.createPoint(1, 2);
			Dimension2D dimension = LayoutCalculator.createDimension(3, 4);
			
			assertRectangle(LayoutCalculator.createRectangle(point, dimension), 1, 2, 3, 4);
		}
	}
	
	public static class createBox {
		@Test
		public void PositiveDimensions() throws Exception {
			assertRectangle(LayoutCalculator.createBox(1, 2, 3, 4), 1, 3, 1, 1);
		}
		
		@Test
		public void NegativeDimension() throws Exception {
			assertRectangle(LayoutCalculator.createBox(4, 3, 2, 1), 4, 2, -1, -1);
		}
	}
	
	@RunWith(Parameterized.class)
	public static class getMarginRectangle {
	    @Parameter(0)
	    public String fTestName;
	    
	    @Parameter(1)    
	    public Rectangle2D fInputRectangle;
	    
	    @Parameter(2)    
	    public double fMarginLeft;
	    
	    @Parameter(3)    
	    public double fMarginRight;
	    
	    @Parameter(4)    
	    public double fMarginTop;
	    
	    @Parameter(5)    
	    public double fMarginBottom;
	    
	    @Parameter(6)    
	    public Rectangle2D fExpectedRectangle;
		
	    @Parameters(name= "{index}: {0}")
	    public static Collection<Object[]> data() {
	    	return Arrays.asList(new Object[][] {
	    			{ "Positive Points, Positive Dimensions, Zero Margins, Positive Result", LayoutCalculator.createBox(5, 15, 25, 35), 0.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(5, 15, 25, 35) },
	    			{ "Positive Points, Positive Dimensions, Positive Margins, Positive Result", LayoutCalculator.createBox(5, 15, 25, 35), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(9, 12, 27, 34) },
	    			{ "Positive Points, Positive Dimensions, Positive Margins, Zero Result Width", LayoutCalculator.createBox(5, 10, 15, 20), 1.0, 2.0, 3.0, 4.0, LayoutCalculator.createBox(6, 8, 18, 18) },
	    			{ "Positive Points, Positive Dimensions, Positive Margins, Zero Result Height", LayoutCalculator.createBox(5, 10, 15, 20), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(9, 9, 17, 19) },
	    			{ "Positive Points, Positive Dimensions, Positive Margins, Zero Result Size", LayoutCalculator.createBox(5, 10, 15, 20), 4.0, 3.0, 2.0, 3.0, LayoutCalculator.createBox(9, 9, 17, 17) },
	    			{ "Positive Points, Positive Dimensions, Negative Margins, Positive Result", LayoutCalculator.createBox(5, 15, 25, 35), -4.0, -3.0, -2.0, -1.0, LayoutCalculator.createBox(1, 18, 23, 36) },
	    			{ "Positive Points, Negative Dimensions, Zero Margins, Positive Result", LayoutCalculator.createBox(35, 25, 15, 5), 0.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(35, 25, 15, 5) },
	    			{ "Positive Points, Negative Dimensions, Positive Margins, Positive Result", LayoutCalculator.createBox(35, 25, 15, 5), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(31, 28, 13, 6) },
	    			{ "Positive Points, Negative Dimensions, Positive Margins, Zero Result Width", LayoutCalculator.createBox(20, 15, 10, 5), 1.0, 2.0, 3.0, 4.0, LayoutCalculator.createBox(19, 17, 7, 7) },
	    			{ "Positive Points, Negative Dimensions, Positive Margins, Zero Result Height", LayoutCalculator.createBox(20, 15, 10, 5), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(16, 16, 8, 6) },
	    			{ "Positive Points, Negative Dimensions, Positive Margins, Zero Result Size", LayoutCalculator.createBox(20, 15, 10, 5), 4.0, 3.0, 2.0, 3.0, LayoutCalculator.createBox(16, 16, 8, 8) },
	    			{ "Positive Points, Negative Dimensions, Negative Margins, Positive Result", LayoutCalculator.createBox(20, 15, 10, 5), -4.0, -3.0, -2.0, -1.0, LayoutCalculator.createBox(24, 12, 12, 4) },

	    			{ "Negative Points, Positive Dimensions, Zero Margins, Positive Result", LayoutCalculator.createBox(-35, -25, -15, -5), 0.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(-35, -25, -15, -5) },
	    			{ "Negative Points, Positive Dimensions, Positive Margins, Positive Result", LayoutCalculator.createBox(-35, -25, -15, -5), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(-31, -28, -13, -6) },
	    			{ "Negative Points, Positive Dimensions, Positive Margins, Zero Result Width", LayoutCalculator.createBox(-20, -15, -10, -5), 1.0, 2.0, 3.0, 4.0, LayoutCalculator.createBox(-19, -17, -7, -7) },
	    			{ "Negative Points, Positive Dimensions, Positive Margins, Zero Result Height", LayoutCalculator.createBox(-20, -15, -10, -5), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(-16, -16, -8, -6) },
	    			{ "Negative Points, Positive Dimensions, Positive Margins, Zero Result Size", LayoutCalculator.createBox(-20, -15, -10, -5), 4.0, 3.0, 2.0, 3.0, LayoutCalculator.createBox(-16, -16, -8, -8) },
	    			{ "Negative Points, Positive Dimensions, Negative Margins, Positive Result", LayoutCalculator.createBox(-20, -15, -10, -5), -4.0, -3.0, -2.0, -1.0, LayoutCalculator.createBox(-24, -12, -12, -4) },
	    			{ "Negative Points, Negative Dimensions, Zero Margins, Positive Result", LayoutCalculator.createBox(-5, -15, -25, -35), 0.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(-5, -15, -25, -35) },
	    			{ "Negative Points, Negative Dimensions, Positive Margins, Positive Result", LayoutCalculator.createBox(-5, -15, -25, -35), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(-9, -12, -27, -34) },
	    			{ "Negative Points, Negative Dimensions, Positive Margins, Zero Result Width", LayoutCalculator.createBox(-5, -10, -15, -20), 1.0, 2.0, 3.0, 4.0, LayoutCalculator.createBox(-6, -8, -18, -18) },
	    			{ "Negative Points, Negative Dimensions, Positive Margins, Zero Result Height", LayoutCalculator.createBox(-5, -10, -15, -20), 4.0, 3.0, 2.0, 1.0, LayoutCalculator.createBox(-9, -9, -17, -19) },
	    			{ "Negative Points, Negative Dimensions, Positive Margins, Zero Result Size", LayoutCalculator.createBox(-5, -10, -15, -20), 4.0, 3.0, 2.0, 3.0, LayoutCalculator.createBox(-9, -9, -17, -17) },
	    			{ "Negative Points, Negative Dimensions, Negative Margins, Positive Result", LayoutCalculator.createBox(-5, -15, -25, -35), -4.0, -3.0, -2.0, -1.0, LayoutCalculator.createBox(-1, -18, -23, -36) },
	    			
	    			{ "Positive Points, Positive Dimensions, Small Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(5, 15, 25, 35), 3.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(8, 15, 25, 35) },
	    			{ "Positive Points, Positive Dimensions, Small Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(5, 15, 25, 35), 0.0, 3.0, 0.0, 0.0, LayoutCalculator.createBox(5, 12, 25, 35) },
	    			{ "Positive Points, Positive Dimensions, Small Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(5, 15, 25, 35), 0.0, 0.0, 3.0, 0.0, LayoutCalculator.createBox(5, 15, 28, 35) },
	    			{ "Positive Points, Positive Dimensions, Small Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(5, 15, 25, 35), 0.0, 0.0, 0.0, 3.0, LayoutCalculator.createBox(5, 15, 25, 32) },
	    			{ "Positive Points, Negative Dimensions, Small Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(35, 25, 15, 5), 3.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(32, 25, 15, 5) },
	    			{ "Positive Points, Negative Dimensions, Small Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(35, 25, 15, 5), 0.0, 3.0, 0.0, 0.0, LayoutCalculator.createBox(35, 28, 15, 5) },
	    			{ "Positive Points, Negative Dimensions, Small Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(35, 25, 15, 5), 0.0, 0.0, 3.0, 0.0, LayoutCalculator.createBox(35, 25, 12, 5) },
	    			{ "Positive Points, Negative Dimensions, Small Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(35, 25, 15, 5), 0.0, 0.0, 0.0, 3.0, LayoutCalculator.createBox(35, 25, 15, 8) },
	    			{ "Negative Points, Positive Dimensions, Small Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(-35, -25, -15, -5), 3.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(-32, -25, -15, -5) },
	    			{ "Negative Points, Positive Dimensions, Small Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(-35, -25, -15, -5), 0.0, 3.0, 0.0, 0.0, LayoutCalculator.createBox(-35, -28, -15, -5) },
	    			{ "Negative Points, Positive Dimensions, Small Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(-35, -25, -15, -5), 0.0, 0.0, 3.0, 0.0, LayoutCalculator.createBox(-35, -25, -12, -5) },
	    			{ "Negative Points, Positive Dimensions, Small Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(-35, -25, -15, -5), 0.0, 0.0, 0.0, 3.0, LayoutCalculator.createBox(-35, -25, -15, -8) },
	    			{ "Negative Points, Negative Dimensions, Small Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(-5, -15, -25, -35), 3.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(-8, -15, -25, -35) },
	    			{ "Negative Points, Negative Dimensions, Small Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(-5, -15, -25, -35), 0.0, 3.0, 0.0, 0.0, LayoutCalculator.createBox(-5, -12, -25, -35) },
	    			{ "Negative Points, Negative Dimensions, Small Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(-5, -15, -25, -35), 0.0, 0.0, 3.0, 0.0, LayoutCalculator.createBox(-5, -15, -28, -35) },
	    			{ "Negative Points, Negative Dimensions, Small Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(-5, -15, -25, -35), 0.0, 0.0, 0.0, 3.0, LayoutCalculator.createBox(-5, -15, -25, -32) },
	    			
	    			{ "Positive Points, Positive Dimensions, Large Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(5, 15, 25, 35), 11.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(15, 15, 25, 35) },
	    			{ "Positive Points, Positive Dimensions, Large Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(5, 15, 25, 35), 0.0, 11.0, 0.0, 0.0, LayoutCalculator.createBox(5, 5, 25, 35) },
	    			{ "Positive Points, Positive Dimensions, Large Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(5, 15, 25, 35), 0.0, 0.0, 11.0, 0.0, LayoutCalculator.createBox(5, 15, 35, 35) },
	    			{ "Positive Points, Positive Dimensions, Large Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(5, 15, 25, 35), 0.0, 0.0, 0.0, 11.0, LayoutCalculator.createBox(5, 15, 25, 25) },
	    			{ "Positive Points, Negative Dimensions, Large Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(35, 25, 15, 5), 11.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(25, 25, 15, 5) },
	    			{ "Positive Points, Negative Dimensions, Large Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(35, 25, 15, 5), 0.0, 11.0, 0.0, 0.0, LayoutCalculator.createBox(35, 35, 15, 5) },
	    			{ "Positive Points, Negative Dimensions, Large Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(35, 25, 15, 5), 0.0, 0.0, 11.0, 0.0, LayoutCalculator.createBox(35, 25, 5, 5) },
	    			{ "Positive Points, Negative Dimensions, Large Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(35, 25, 15, 5), 0.0, 0.0, 0.0, 11.0, LayoutCalculator.createBox(35, 25, 15, 15) },
	    			{ "Negative Points, Positive Dimensions, Large Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(-35, -25, -15, -5), 11.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(-25, -25, -15, -5) },
	    			{ "Negative Points, Positive Dimensions, Large Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(-35, -25, -15, -5), 0.0, 11.0, 0.0, 0.0, LayoutCalculator.createBox(-35, -35, -15, -5) },
	    			{ "Negative Points, Positive Dimensions, Large Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(-35, -25, -15, -5), 0.0, 0.0, 11.0, 0.0, LayoutCalculator.createBox(-35, -25, -5, -5) },
	    			{ "Negative Points, Positive Dimensions, Large Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(-35, -25, -15, -5), 0.0, 0.0, 0.0, 11.0, LayoutCalculator.createBox(-35, -25, -15, -15) },
	    			{ "Negative Points, Negative Dimensions, Large Positive Left Margins, Zero Result Width", LayoutCalculator.createBox(-5, -15, -25, -35), 11.0, 0.0, 0.0, 0.0, LayoutCalculator.createBox(-15, -15, -25, -35) },
	    			{ "Negative Points, Negative Dimensions, Large Positive Right Margins, Zero Result Width", LayoutCalculator.createBox(-5, -15, -25, -35), 0.0, 11.0, 0.0, 0.0, LayoutCalculator.createBox(-5, -5, -25, -35) },
	    			{ "Negative Points, Negative Dimensions, Large Positive Top Margins, Zero Result Height", LayoutCalculator.createBox(-5, -15, -25, -35), 0.0, 0.0, 11.0, 0.0, LayoutCalculator.createBox(-5, -15, -35, -35) },
	    			{ "Negative Points, Negative Dimensions, Large Positive Bottom Margins, Zero Result Height", LayoutCalculator.createBox(-5, -15, -25, -35), 0.0, 0.0, 0.0, 11.0, LayoutCalculator.createBox(-5, -15, -25, -25) }
	    	});
	    }
	    
	    @Test
	    public void test() throws Exception {
			assertRectangle(fExpectedRectangle, LayoutCalculator.getMarginRectangle(fInputRectangle, fMarginLeft, fMarginRight, fMarginTop, fMarginBottom));
		}
	}
	
	@RunWith(Parameterized.class)
	public static class getAbsoluteInnerRectangle {
	    @Parameter(0)
	    public String fTestName;
	    
	    @Parameter(1)    
	    public Rectangle2D fInputRectangle;
	    
	    @Parameter(2)    
	    public Dimension2D fInputDimension;
	    
	    @Parameter(3)    
	    public Position fInputPosition;
	    
	    @Parameter(4)    
	    public double fInputXOffset;
	    
	    @Parameter(5)    
	    public double fInputYOffset;
	    
	    @Parameter(6)    
	    public Rectangle2D fExpectedRectangle;
		
	    @Parameters(name= "{index}: {0}")
	    public static Collection<Object[]> data() {
	    	return Arrays.asList(new Object[][] {
	    			{ "Top_Left, Positive Rectangle, Small Positive Dimensions, No Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(5, 8, 25, 32) },
	    			{ "Top_Left, Positive Rectangle, Small Positive Dimensions, Small Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(6, 9, 27, 34) },
	    			{ "Top_Left, Positive Rectangle, Small Positive Dimensions, Large Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(12, 15, 28, 35) },
	    			{ "Top_Left, Positive Rectangle, Small Positive Dimensions, Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(5, 8, 25, 32) },
	    			{ "Top_Left, Positive Rectangle, Small Positive Dimensions, Large Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(5, 8, 25, 32) },
	    			{ "Top_Left, Positive Rectangle, Small Negative Dimensions, No Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(8, 5, 32, 25) },
	    			{ "Top_Left, Positive Rectangle, Small Negative Dimensions, Small Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(9, 6, 34, 27) },
	    			{ "Top_Left, Positive Rectangle, Small Negative Dimensions, Large Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(15, 12, 35, 28) },
	    			{ "Top_Left, Positive Rectangle, Small Negative Dimensions, Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(8, 5, 32, 25) },
	    			{ "Top_Left, Positive Rectangle, Small Negative Dimensions, Large Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(8, 5, 32, 25) },
	    			
	    			{ "Top_Left, Positive Rectangle, Large X Positive Dimensions, No Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(5, 15, 25, 28) },
	    			{ "Top_Left, Positive Rectangle, Large X Positive Dimensions, Small Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(5, 15, 27, 30) },
	    			{ "Top_Left, Positive Rectangle, Large X Positive Dimensions, Large Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(5, 15, 32, 35) },
	    			{ "Top_Left, Positive Rectangle, Large X Positive Dimensions, Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(5, 15, 25, 28) },
	    			{ "Top_Left, Positive Rectangle, Large X Positive Dimensions, Large Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(5, 15, 25, 28) },
	    			{ "Top_Left, Positive Rectangle, Large X Negative Dimensions, No Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(15, 5, 28, 25) },
	    			{ "Top_Left, Positive Rectangle, Large X Negative Dimensions, Small Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(15, 5, 30, 27) },
	    			{ "Top_Left, Positive Rectangle, Large X Negative Dimensions, Large Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(15, 5, 35, 32) },
	    			{ "Top_Left, Positive Rectangle, Large X Negative Dimensions, Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(15, 5, 28, 25) },
	    			{ "Top_Left, Positive Rectangle, Large X Negative Dimensions, Large Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(15, 5, 28, 25) },
	    			
	    			{ "Top_Left, Positive Rectangle, Large Y Positive Dimensions, No Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(5, 8, 25, 35) },
	    			{ "Top_Left, Positive Rectangle, Large Y Positive Dimensions, Small Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(6, 9, 25, 35) },
	    			{ "Top_Left, Positive Rectangle, Large Y Positive Dimensions, Large Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(12, 15, 25, 35) },
	    			{ "Top_Left, Positive Rectangle, Large Y Positive Dimensions, Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(5, 8, 25, 35) },
	    			{ "Top_Left, Positive Rectangle, Large Y Positive Dimensions, Large Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(5, 8, 25, 35) },
	    			{ "Top_Left, Positive Rectangle, Large Y Negative Dimensions, No Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(8, 5, 35, 25) },
	    			{ "Top_Left, Positive Rectangle, Large Y Negative Dimensions, Small Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(9, 6, 35, 25) },
	    			{ "Top_Left, Positive Rectangle, Large Y Negative Dimensions, Large Positive Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(15, 12, 35, 25) },
	    			{ "Top_Left, Positive Rectangle, Large Y Negative Dimensions, Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(8, 5, 35, 25) },
	    			{ "Top_Left, Positive Rectangle, Large Y Negative Dimensions, Large Negative Offset", LayoutCalculator.createBox(5, 15, 25, 35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(8, 5, 35, 25) },
	    			
	    			{ "Top_Left, Negative Rectangle, Small Positive Dimensions, No Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(-15, -12, -35, -28) },
	    			{ "Top_Left, Negative Rectangle, Small Positive Dimensions, Small Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(-14, -11, -33, -26) },
	    			{ "Top_Left, Negative Rectangle, Small Positive Dimensions, Large Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(-8, -5, -32, -25) },
	    			{ "Top_Left, Negative Rectangle, Small Positive Dimensions, Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(-15, -12, -35, -28) },
	    			{ "Top_Left, Negative Rectangle, Small Positive Dimensions, Large Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(3, 7), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(-15, -12, -35, -28) },
	    			{ "Top_Left, Negative Rectangle, Small Negative Dimensions, No Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(-12, -15, -28, -35) },
	    			{ "Top_Left, Negative Rectangle, Small Negative Dimensions, Small Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(-11, -14, -26, -33) },
	    			{ "Top_Left, Negative Rectangle, Small Negative Dimensions, Large Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(-5, -8, -25, -32) },
	    			{ "Top_Left, Negative Rectangle, Small Negative Dimensions, Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(-12, -15, -28, -35) },
	    			{ "Top_Left, Negative Rectangle, Small Negative Dimensions, Large Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-3, -7), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(-12, -15, -28, -35) },

	    			{ "Top_Left, Negative Rectangle, Large X Positive Dimensions, No Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(-15, -5, -35, -32) },
	    			{ "Top_Left, Negative Rectangle, Large X Positive Dimensions, Small Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(-15, -5, -33, -30) },
	    			{ "Top_Left, Negative Rectangle, Large X Positive Dimensions, Large Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(-15, -5, -28, -25) },
	    			{ "Top_Left, Negative Rectangle, Large X Positive Dimensions, Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(-15, -5, -35, -32) },
	    			{ "Top_Left, Negative Rectangle, Large X Positive Dimensions, Large Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(20, 6), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(-15, -5, -35, -32) },
	    			{ "Top_Left, Negative Rectangle, Large X Negative Dimensions, No Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(-5, -15, -32, -35) },
	    			{ "Top_Left, Negative Rectangle, Large X Negative Dimensions, Small Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(-5, -15, -30, -33) },
	    			{ "Top_Left, Negative Rectangle, Large X Negative Dimensions, Large Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(-5, -15, -25, -28) },
	    			{ "Top_Left, Negative Rectangle, Large X Negative Dimensions, Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(-5, -15, -32, -35) },
	    			{ "Top_Left, Negative Rectangle, Large X Negative Dimensions, Large Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-20, -6), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(-5, -15, -32, -35) },
	    			
	    			{ "Top_Left, Negative Rectangle, Large Y Positive Dimensions, No Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(-15, -12, -35, -25) },
	    			{ "Top_Left, Negative Rectangle, Large Y Positive Dimensions, Small Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(-14, -11, -35, -25) },
	    			{ "Top_Left, Negative Rectangle, Large Y Positive Dimensions, Large Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(-8, -5, -35, -25) },
	    			{ "Top_Left, Negative Rectangle, Large Y Positive Dimensions, Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(-15, -12, -35, -25) },
	    			{ "Top_Left, Negative Rectangle, Large Y Positive Dimensions, Large Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(6, 20), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(-15, -12, -35, -25) },
	    			{ "Top_Left, Negative Rectangle, Large Y Negative Dimensions, No Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, 0.0, 0.0, LayoutCalculator.createBox(-12, -15, -25, -35) },
	    			{ "Top_Left, Negative Rectangle, Large Y Negative Dimensions, Small Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, 1.0, 2.0, LayoutCalculator.createBox(-11, -14, -25, -35) },
	    			{ "Top_Left, Negative Rectangle, Large Y Negative Dimensions, Large Positive Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, 11.0, 22.0, LayoutCalculator.createBox(-5, -8, -25, -35) },
	    			{ "Top_Left, Negative Rectangle, Large Y Negative Dimensions, Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, -1.0, -2.0, LayoutCalculator.createBox(-12, -15, -25, -35) },
	    			{ "Top_Left, Negative Rectangle, Large Y Negative Dimensions, Large Negative Offset", LayoutCalculator.createBox(-5, -15, -25, -35), LayoutCalculator.createDimension(-6, -20), Position.Top_Left, -11.0, -22.0, LayoutCalculator.createBox(-12, -15, -25, -35) },
	    			
	    			// TODO: Other Positions
	    	});
	    }
	    
	    @Test
	    public void test() throws Exception {
			assertRectangle(fExpectedRectangle, LayoutCalculator.getAbsoluteInnerRectangle(fInputRectangle, fInputDimension, fInputPosition, fInputXOffset, fInputYOffset));
		}
	}
	
	private static void assertPoint(Point2D point, double x, double y) {
		assertEquals(x, point.getX(), 0.0d);
		assertEquals(y, point.getY(), 0.0d);
	}
	
	private static void assertDimension(Dimension2D dimension, double width, double height) {
		assertEquals(width, dimension.getWidth(), 0.0d);
		assertEquals(height, dimension.getHeight(), 0.0d);
	}
	
	private static void assertRectangle(Rectangle2D rectangle, double left, double top, double width, double height) {
		assertEquals(left, rectangle.getX(), 0.0d);
		assertEquals(top, rectangle.getY(), 0.0d);
		assertEquals(width, rectangle.getWidth(), 0.0d);
		assertEquals(height, rectangle.getHeight(), 0.0d);
	}
	
	private static void assertRectangle(Rectangle2D expected, Rectangle2D actual) {
		assertEquals(expected.getX(), actual.getX(), 0.0d);
		assertEquals(expected.getY(), actual.getY(), 0.0d);
		assertEquals(expected.getWidth(), actual.getWidth(), 0.0d);
		assertEquals(expected.getHeight(), actual.getHeight(), 0.0d);
	}
}
