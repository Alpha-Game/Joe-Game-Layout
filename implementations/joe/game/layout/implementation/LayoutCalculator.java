package joe.game.layout.implementation;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Point2D;
import joe.classes.geometry2D.Rectangle2D;

public class LayoutCalculator {	
	public static Point2D createPoint(double x, double y) {
		return new Point2D(x, y);
	}
	
	public static Dimension2D createDimension(double width, double height) {
		return new Dimension2D(width, height);
	}
	
	public static Rectangle2D createRectangle(double x, double y, double width, double height) {
		return new Rectangle2D(x, y, width, height);
	}
	
	public static Rectangle2D createBox(double left, double right, double top, double bottom) {
		return Rectangle2D.fromBox(left, top, right, bottom);
	}
	
	public static Rectangle2D createRectangle(Point2D point, Dimension2D dimension) {
		return new Rectangle2D(point, dimension);
	}
	
	public static Rectangle2D getMarginRectangle(Rectangle2D outerRectangle, double leftMargin, double rightMargin, double topMargin, double bottomMargin) {
		
		// Make sure the width does not swap from negative to positive or positive to negative
		double width = outerRectangle.getWidth();
		if (Math.abs(width) < leftMargin + rightMargin) {
			width = 0;
		} else {
			width = outerRectangle.getWidth() - (outerRectangle.getWidth() < 0 ? (-1 * (leftMargin + rightMargin)) : (leftMargin + rightMargin));
		}
		
		// Make sure the height does not swap from negative to positive or positive to negative
		double height = outerRectangle.getHeight();
		if (Math.abs(height) < topMargin + bottomMargin) {
			height = 0;
		} else {
			height = outerRectangle.getHeight() - (outerRectangle.getHeight() < 0 ? (-1 * (topMargin + bottomMargin)) : (topMargin + bottomMargin));
		}
		
		// Make sure the left margin does not over extend the outer rectangle
		double left = leftMargin;
		if (Math.abs(outerRectangle.getWidth()) < left) {
			left = outerRectangle.getX() + outerRectangle.getWidth();
		} else {
			left = outerRectangle.getX() + (outerRectangle.getWidth() < 0 ? (-1 * leftMargin) : leftMargin);
		}
		
		// Make sure the top margin does not over extend the outer rectangle
		double top = topMargin;
		if (Math.abs(outerRectangle.getHeight()) < top) {
			top = outerRectangle.getY() + outerRectangle.getHeight();
		} else {
			top = outerRectangle.getY() + (outerRectangle.getHeight() < 0 ? (-1 * topMargin) : topMargin);
		}
		
		return createRectangle(left, top, width, height);
	}
	
	/**
	 * Creates a Rectangle inside of another rectangle using the rectangle's dimensions and position. <br>
	 * <br>
	 * Note: In a negative rectangle, it will get position based upon actual position, rather than the negative positions. This means that Position.Top_Left 
	 * 
	 * @param outerRectangle The rectangle to create the rectangle inside of.
	 * @param innerDimesions The size of the inner rectangle. <b>Note:</b> If the inner dimensions are larger than the outer Rectangle, 
	 * the inner Rectangle will be squished into the outer Rectangle. Also note that the X to Y scale will be kept.
	 * @param position The location to place the inner rectangle inside of the outer rectangle.
	 * @param xOffset If position <i>isLeft()</i>:
	 * <b>Positive values</b> pushes the inner Rectangle left, until there is no more room to push left. Will not squish the inner rectangle. 
	 * <b>Negative values</b> do nothing. <br>
	 * If position <i>isCenter()</i>:
	 * <b>Positive values</b> push the inner Rectangle left, until there is no more room to push left. Will not squish the inner rectangle. 
	 * <b>Negative values</b> push the inner Rectangle right, until there is no more room to push righ. Will not squish the inner rectangle. <br>
	 * If position <i>isRight()</i>:
	 * <b>Positive values</b> do nothing. 
	 * <b>Negative values</b> push the inner Rectangle right, until there is no more room to push right. Will not squish the inner rectangle. <br>
	 * 
	 * @param yOffset If position <i>isTop()</i>: 
	 * <b>Positive values</b> pushes the inner Rectangle down, until there is no more room to push down. Will not squish the inner rectangle. 
	 * <b>Negative values</b> do nothing <br>
	 * If position <i>isMiddle()</i>:
	 * <b>Positive values</b> push the inner Rectangle down, until there is no more room to push down. Will not squish the inner rectangle.
	 * <b>Negative values</b> push the inner Rectangle up, until there is no more room to push up. Will not squish the inner rectangle. <br>
	 * If position <i>isBottom()</i>:
	 * <b>Positive values</b> do nothing.
	 * <b>Negative values</b> push the inner Rectangle up, until there is no more room to push up. Will not squish the inner rectangle. <br>
	 * @return A new rectangle at the given position inside the outer Rectangle.
	 */
	public static Rectangle2D getAbsoluteInnerRectangle(Rectangle2D outerRectangle, Dimension2D innerDimesions, Position position, double xOffset, double yOffset) {
		double xWidthDelta = (Math.abs(outerRectangle.getWidth()) - Math.abs(innerDimesions.getWidth()));
		double yHeightDelta = (Math.abs(outerRectangle.getHeight()) - Math.abs(innerDimesions.getHeight()));
		double xWidthScale = 1;
		double yHeightScale = 1;
		
		// Calculate Width Delta and Width Scale
		if (xWidthDelta < 0) {
			xWidthScale *= Math.abs(outerRectangle.getWidth() / innerDimesions.getWidth());
		}
		// Calculate Height Delta and Height Scale
		if (yHeightDelta < 0) {
			yHeightScale *= Math.abs(outerRectangle.getHeight() / innerDimesions.getHeight());
		}
		
		// Keep the width to height ratio
		if (Math.abs(xWidthScale) < Math.abs(yHeightScale)) {
			yHeightScale = xWidthScale;
		} else {
			xWidthScale = yHeightScale;
		}
		
		// Scaled width and height for the inner rectangle
		double newWidth = xWidthScale * innerDimesions.getWidth();
		double newHeight = yHeightScale * innerDimesions.getHeight();
		
		// Get the new deltas based upon the scale
		xWidthDelta = (Math.abs(outerRectangle.getWidth()) - Math.abs(newWidth));
		yHeightDelta = (Math.abs(outerRectangle.getHeight()) - Math.abs(newHeight));
		
		// Make sure X offset is less than the delta
		if (position.isCenter()) {
			if(xWidthDelta - Math.abs(xOffset * 2) < 0) {
				xOffset = (xOffset < 0 ? -1 : 1) * (xWidthDelta / 2);
			}
		} else {
			if((xWidthDelta - Math.abs(xOffset)) < 0) {
				xOffset = (xOffset < 0 ? -1 : 1) * xWidthDelta;
			}
		}
		
		// Make sure Y offset is less than the delta
		if (position.isMiddle()) {
			if(yHeightDelta - Math.abs(yOffset * 2) < 0) {
				yOffset = (yOffset < 0 ? -1 : 1) * (yHeightDelta / 2);
			}
		} else {
			if((yHeightDelta - Math.abs(yOffset)) < 0) {
				yOffset = (yOffset < 0 ? -1 : 1) * yHeightDelta;
			}
		}
			
		// Get x Position
		double leftPosition = outerRectangle.getMinX();
		if (position.isLeft()) {
			leftPosition += (xOffset > 0 ? xOffset : 0);
		} else if (position.isCenter()) {
			leftPosition += (xWidthDelta / 2) + xOffset;
		} else if (position.isRight()) {
			leftPosition += xWidthDelta + (xOffset < 0 ? xOffset : 0);
		}
		double rightPostion = leftPosition + Math.abs(newWidth);
		
		// Get y Position
		double topPosition = outerRectangle.getMinY();
		if (position.isTop()) {
			topPosition += (yOffset > 0 ? yOffset : 0);
		} else if (position.isMiddle()) {
			topPosition += (yHeightDelta / 2) + yOffset;
		} else if (position.isBottom()) {
			topPosition += yHeightDelta + (yOffset < 0 ? yOffset : 0);
		}
		double bottomPosition = topPosition + Math.abs(newHeight);
		
		// Create the rectangle
		return createBox(newWidth < 0 ? rightPostion : leftPosition, newWidth < 0 ? leftPosition : rightPostion, newHeight < 0 ? bottomPosition : topPosition, newHeight < 0 ? topPosition : bottomPosition);
	}
}
