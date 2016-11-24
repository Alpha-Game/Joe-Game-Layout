package joe.game.layout.implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;
import joe.game.layout.implementation.image.ImageDrawer;

public class RepeatableAndCroppedLayout implements IDrawable {
	private IDrawable fItem;

	public RepeatableAndCroppedLayout(IDrawable item) {
		fItem = item;
	}
	
	@Override
	public Dimension2D getDimension() {
		return fItem.getDimension();
	}

	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		BufferedImage newImage = new BufferedImage((int)Math.abs(rect.getWidth()), (int)Math.abs(rect.getHeight()), BufferedImage.TYPE_INT_ARGB);
		
		Dimension2D dimension = getDimension();
		Graphics2D g2d = newImage.createGraphics();
		
		for (double x = 0; Math.abs(x) < Math.abs(rect.getWidth()); x += dimension.getWidth()) {
			for (double y = 0; Math.abs(y) < Math.abs(rect.getHeight()); y += dimension.getHeight()) {
				fItem.draw(g2d, LayoutCalculator.createRectangle(x, y, dimension.getWidth(), dimension.getHeight()));
			}
		}
		
		ImageDrawer.draw(g, newImage, rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
	}
}
