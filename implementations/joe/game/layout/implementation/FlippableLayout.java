package joe.game.layout.implementation;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;

public class FlippableLayout implements IDrawable {
	private IDrawable fItem;
	private boolean fXFlip;
	private boolean fYFlip;
	
	public FlippableLayout(IDrawable item) {
		this(item, false, false);
	}
	
	public FlippableLayout(IDrawable item, boolean xFlip, boolean yFlip) {
		fItem = item;
		fXFlip = xFlip;
		fYFlip = yFlip;
	}
	
	@Override
	public Dimension2D getDimension() {
		return fItem.getDimension();
	}

	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		draw(g, rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
	}
	
	protected void draw(Graphics2D g, double X, double Y, double width, double height) {
		fItem.draw(g, LayoutCalculator.createRectangle(X + (fXFlip ? (width - 1) : 0), Y + (fYFlip ? (height): 0), width * (fXFlip ? -1 : 1), height * (fYFlip ? -1 : 1)));
	}
	
	public void flipX() {
		fXFlip = !fXFlip;
	}

	public void flipY() {
		fYFlip = !fYFlip;
	}
}
