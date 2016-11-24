package joe.game.layout.implementation;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;

public class SpacableLayout implements IDrawable {
	private IDrawable fItem;
	private double fSpacerLeft;
	private double fSpacerRight;
	private double fSpacerTop;
	private double fSpacerBottom;
	
	public SpacableLayout(IDrawable item, double spacerLeft, double spacerRight, double spacerTop, double spacerBottom) {
		fItem = item;
		setSpacerLeft(spacerLeft);
		setSpacerRight(spacerRight);
		setSpacerTop(spacerTop);
		setSpacerBottom(spacerBottom);
	}

	public void setSpacerLeft(double spacerLeft) {
		fSpacerLeft = Math.abs(spacerLeft);
	}
	
	public void setSpacerRight(double spacerRight) {
		fSpacerRight = Math.abs(spacerRight);
	}
	
	public void setSpacerTop(double spacerTop) {
		fSpacerTop = Math.abs(spacerTop);
	}
	
	public void setSpacerBottom(double spacerBottom) {
		fSpacerBottom = Math.abs(spacerBottom);
	}
	
	@Override
	public Dimension2D getDimension() {
		Dimension2D dimension = fItem.getDimension();
		if (dimension.getWidth() < 0) {
			throw new IllegalStateException("IDrawable does not support the negative Width <" + dimension.getWidth() + "> in getDimension()");
		}
		if (dimension.getHeight() < 0) {
			throw new IllegalStateException("IDrawable does not support the negative Height <" + dimension.getHeight() + "> in getDimension()");
		}
		return LayoutCalculator.createDimension(dimension.getWidth() + (fSpacerLeft + fSpacerRight), dimension.getHeight() + (fSpacerTop + fSpacerBottom));
	}
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		Dimension2D dimensions = getDimension();
		if (dimensions.getWidth() > 0 && dimensions.getHeight() > 0) {
			double xScale = Math.abs(rect.getWidth() / dimensions.getWidth());
			double yScale = Math.abs(rect.getHeight() / dimensions.getHeight());
			fItem.draw(g, LayoutCalculator.getMarginRectangle(rect, fSpacerLeft * xScale, fSpacerRight * xScale, fSpacerTop * yScale, fSpacerBottom * yScale));
		}
		
	}
}
