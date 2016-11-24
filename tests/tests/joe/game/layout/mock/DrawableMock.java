package tests.joe.game.layout.mock;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;

public class DrawableMock implements IDrawable {
	private Dimension2D fDimension;
	private List<Graphics2D> fLastGraphics;
	private List<Rectangle2D> fLastRectangle;
	
	public DrawableMock(Dimension2D dimension) {
		fDimension = dimension;
		fLastGraphics = new LinkedList<Graphics2D>();
		fLastRectangle = new LinkedList<Rectangle2D>();
	}
	
	public void setDimension(Dimension2D dimension) {
		fDimension = dimension;
	}
	
	public void clear() {
		fLastGraphics.clear();
		fLastRectangle.clear();
	}
	
	public int getTotalUseCount() {
		return fLastGraphics.size();
	}
	
	public Graphics2D getGraphic(int useCount) {
		return fLastGraphics.get(useCount);
	}
	
	public Rectangle2D getRectangle(int useCount) {
		return fLastRectangle.get(useCount);
	}

	@Override
	public Dimension2D getDimension() {
		return fDimension;
	}

	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		fLastGraphics.add(g);
		fLastRectangle.add(rect);
	}

}
