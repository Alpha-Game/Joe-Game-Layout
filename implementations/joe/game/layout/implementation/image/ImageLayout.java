package joe.game.layout.implementation.image;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;

public class ImageLayout implements IDrawable {
	protected ImageDrawer fImage;
	
	protected ImageLayout(ImageDrawer image) {
		fImage = image;
	}
	
	@Override
	public Dimension2D getDimension() {
		return fImage.getDimension();
	}

	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		fImage.draw(g, rect);
	}
}
