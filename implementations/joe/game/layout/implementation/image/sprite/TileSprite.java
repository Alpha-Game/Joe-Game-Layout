package joe.game.layout.implementation.image.sprite;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Point2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.image.sprite.IAnimatedSprite;
import joe.game.layout.image.sprite.ISprite;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.layout.implementation.image.sprite.TileSpriteFactory.TileEdgeType;

public class TileSprite implements ISprite {
	private TileSpriteFactory fFactory;
	private int fCount;
	private boolean fXFlip;
	private boolean fYFlip;
	private Dimension2D fLastDimension;
	private List<SpritePointPair> fSpritesList;
	
	public TileSprite(TileSpriteFactory factory, int count) {
		this(factory, count, false, false);
	}
	
	public TileSprite(TileSpriteFactory factory, int count, boolean xFlip, boolean yFlip) {
		fFactory = factory;
		fCount = count;
		fXFlip = xFlip;
		fYFlip = yFlip;
		fSpritesList = new LinkedList<SpritePointPair>();
	}

	@Override
	public Dimension2D getDimension() {
		return fFactory.getEdge(TileEdgeType.LeftRightTopBottom, fCount).getDimension();
	}
	
	protected void createSpriteList(Rectangle2D rect) {
		fLastDimension = LayoutCalculator.createDimension(Math.abs(rect.getWidth()), Math.abs(rect.getHeight()));
		fSpritesList.clear();
		
		ISprite sprite = fFactory.getEdge(TileEdgeType.LeftRightTopBottom, fCount);
		if (sprite.getDimension().getWidth() >= fLastDimension.getWidth() || sprite.getDimension().getHeight() >= fLastDimension.getHeight()) {
			fSpritesList.add(new SpritePointPair(sprite, LayoutCalculator.createPoint(0, 0), LayoutCalculator.createDimension(fLastDimension.getWidth(), fLastDimension.getHeight())));
			return;
		}
		
		// TODO: Have tile set fill in the rest of the rectangle
		sprite = fFactory.getEdge(TileEdgeType.LeftRightTop, fCount);
		if (sprite.getDimension().getWidth() >= fLastDimension.getWidth()) {
			fSpritesList.add(new SpritePointPair(sprite, LayoutCalculator.createPoint(0, 0), LayoutCalculator.createDimension(fLastDimension.getWidth(), sprite.getDimension().getHeight())));
		}
	}

	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		if (fLastDimension.getHeight() != Math.abs(rect.getHeight()) || fLastDimension.getWidth() != Math.abs(rect.getWidth())) {
			createSpriteList(rect);
		}
		
		long time = System.nanoTime();
		for (SpritePointPair pair : fSpritesList) {
			if (pair.sprite instanceof IAnimatedSprite) {
				((IAnimatedSprite)pair.sprite).draw(g, LayoutCalculator.createRectangle(LayoutCalculator.createPoint(pair.point.getX() + rect.getMinX(), pair.point.getY() + rect.getMinY()), pair.dimension), time);
			} else {
				pair.sprite.draw(g, LayoutCalculator.createRectangle(LayoutCalculator.createPoint(pair.point.getX() + rect.getMinX(), pair.point.getY() + rect.getMinY()), pair.dimension));
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
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
		return new TileSprite(fFactory, fCount, fXFlip, fYFlip);
	}
	
	private static class SpritePointPair {
		private ISprite sprite;
		private Point2D point;
		private Dimension2D dimension;
		
		private SpritePointPair(ISprite sprite, Point2D point, Dimension2D dimension) {
			this.sprite = sprite;
			this.point = point;
			this.dimension = dimension;
		}
	}
}
