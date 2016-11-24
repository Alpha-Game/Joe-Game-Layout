package joe.game.layout.implementation.image.sprite;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.image.sprite.ISprite;

public class Sprite implements ISprite {
	private SpriteFactory fFactory;
	private boolean fXFlip;
	private boolean fYFlip;
	
	public Sprite(SpriteFactory factory) {
		this(factory, false, false);
	}
	
	public Sprite(SpriteFactory factory, boolean xFlip, boolean yFlip) {
		fFactory = factory;
		fXFlip = xFlip;
		fYFlip = yFlip;
	}
	
	@Override
	public Dimension2D getDimension() {
		return fFactory.getImage().getDimension();
	}
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		draw(g, rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
	}
	
	protected void draw(Graphics2D g, double X, double Y, double width, double height) {
		fFactory.getImage().draw(g, X + (fXFlip ? (width - 1) : 0), Y + (fYFlip ? (height - 1): 0), width * (fXFlip ? -1 : 1), height * (fYFlip ? -1 : 1));
	}
	
	@Override
	public boolean isEmpty() {
		return fFactory.getImage().isAllTransparent();
	}

	@Override
	public ISprite flipX(Boolean flip) {
		if (flip == null) {
			fXFlip = !fXFlip;
		} else {
			fXFlip = flip;
		}
		return this;
	}

	@Override
	public ISprite flipY(Boolean flip) {
		if (flip == null) {
			fYFlip = !fYFlip;
		} else {
			fYFlip = flip;
		}
		return this;
	}
	
	@Override
	public ISprite flipX() {
		return flipX(null);
	}

	@Override
	public ISprite flipY() {
		return flipY(null);
	}

	@Override
	public ISprite clone() {
		return new Sprite(fFactory, fXFlip, fYFlip);
	}
}
