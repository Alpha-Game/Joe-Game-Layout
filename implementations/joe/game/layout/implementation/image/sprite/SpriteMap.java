package joe.game.layout.implementation.image.sprite;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.implementation.image.ImageDrawer;

public class SpriteMap {
	private ImageDrawer fImage;
	
	public SpriteMap(ImageDrawer image) {
		fImage = image;
	}
	
	public Dimension2D getDimension() {
		return fImage.getDimension();
	}
	
	public SpriteFactory getSprite(Rectangle2D rect) {
		try {
			return new SpriteFactory(fImage.getSubimage(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight()));
		} catch (Throwable t) {
			// Track Thrown Exceptions
			t.printStackTrace(System.err);
			return null;
		}
	}
	
	public boolean isEmpty() {
		return fImage.isAllTransparent();
	}
}
