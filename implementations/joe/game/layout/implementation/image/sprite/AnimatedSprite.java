package joe.game.layout.implementation.image.sprite;

import java.awt.Graphics2D;
import java.util.Iterator;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.image.sprite.IAnimatedSprite;
import joe.game.layout.image.sprite.ISprite;
import joe.game.layout.implementation.LayoutCalculator;

public class AnimatedSprite implements IAnimatedSprite {
	private AnimatedSpriteFactory fFactory;
	
	// State holders
	private double fSpeed;
	private long fLastDrawTime;
	private long fLastStartTime;
	private long fAnimationTime;
	private SpriteFrame fCurrentIteration;
	private Iterator<SpriteFrame> fFrameIterator;
	
	// Sprite Modifiers
	private boolean fXFlip;
	private boolean fYFlip;
	
	public AnimatedSprite(AnimatedSpriteFactory factory) {
		this(factory, 1.0, false, false);
	}
	
	public AnimatedSprite(AnimatedSpriteFactory factory, double speed, boolean xFlip, boolean yFlip) {
		this(factory, speed, xFlip, yFlip, 0, 0, 0);
	}
	
	public AnimatedSprite(AnimatedSpriteFactory factory, double speed, boolean xFlip, boolean yFlip, long lastDrawTime, long lastStartTime, long animationTime) {
		fFactory = factory;
		fSpeed = speed;
		fLastDrawTime = lastDrawTime;
		
		fXFlip = xFlip;
		fYFlip = yFlip;
		
		if (reset(lastStartTime) && animationTime != 0) {
			animateTo(lastStartTime + animationTime);
		}
	}
	
	@Override
	public Dimension2D getDimension() {
		Iterator<SpriteFrame> iterator = fFactory.getIterator();
		if (!iterator.hasNext()) {
			return LayoutCalculator.createDimension(0, 0);
		}
		return iterator.next().getSprite().getDimension();
	}
	
	@Override
	public void setSpeed(double speed) {
		fSpeed = 1.0 / speed;
	}
	
	@Override
	public long getTotalAnimationLength() {
		return (long)(fSpeed * fFactory.getTotalAnimationLength());
	}
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		draw(g, rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
	}
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rect, long time) {
		draw(g, rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight(), time);
	}
	
	protected boolean reset(long time) {
		fAnimationTime = 0;
		fLastStartTime = time;
		fFrameIterator = fFactory.getIterator();
		fCurrentIteration = null;
		if (fFrameIterator.hasNext()) {
			fCurrentIteration = fFrameIterator.next();
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean animateTo(long time) {
		long frameTime = (long)Math.floor((time - fLastStartTime) % ((long)Math.floor(fSpeed * (double)fFactory.getTotalAnimationLength())) / fSpeed);
		
		if (frameTime < fAnimationTime) {
			if (!reset(time)) {
				return false;
			}
		}
		
		while(fAnimationTime + fCurrentIteration.getFrameTimeStep() <= frameTime) {
			if (!fFrameIterator.hasNext()) {
				fFrameIterator = fFactory.getIterator();
			}
			
			fAnimationTime += fCurrentIteration.getFrameTimeStep();
			fCurrentIteration = fFrameIterator.next();
		}
		
		return true;
	}
	
	protected long getTime() {
		return System.nanoTime();
	}
	
	protected void draw(Graphics2D g, double X, double Y, double width, double height) {
		draw(g, X, Y, width, height, getTime());
	}
	
	protected void draw(Graphics2D g, double X, double Y, double width, double height, long time) {
		// Reset the iterator if necessary
		if (fFrameIterator == null || (long)(fSpeed * fFactory.getTotalAnimationLength()) < (time - fLastDrawTime)) {
			if (!reset(time)) {
				return;
			}
		}
		
		// Iterate until at the correct animation
		animateTo(time);
		
		// Draw the current sprite
		fLastDrawTime = time;
		
		if (fCurrentIteration != null) {
			fCurrentIteration.getSprite().draw(g, X + (fXFlip ? (width - 1) : 0), Y + (fYFlip ? (height - 1): 0), width * (fXFlip ? -1 : 1), height * (fYFlip ? -1 : 1));
		}
	}
	
	@Override
	public boolean isEmpty() {
		Iterator<SpriteFrame> iterator = fFactory.getIterator();
		while (iterator.hasNext()) {
			if (!iterator.next().getSprite().isEmpty()) {
				return false;
			}
		}
		return true;
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
		return new AnimatedSprite(fFactory, fSpeed, fXFlip, fYFlip, fLastDrawTime, fLastStartTime, (fLastDrawTime - fLastStartTime));
	}
}
