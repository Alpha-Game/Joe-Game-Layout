package joe.game.layout.implementation.image.sprite;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import joe.game.layout.image.sprite.ISpriteFactory;

public class AnimatedSpriteFactory implements ISpriteFactory {
	private List<SpriteFrame> fFrames;
	private long fTotalTime;

	public AnimatedSpriteFactory() {
		fFrames = new LinkedList<SpriteFrame>();
		fTotalTime = 0;
	}
	
	public void addFrame(Sprite sprite, long frameTimeStep) {
		fFrames.add(new SpriteFrame(sprite, frameTimeStep));
		fTotalTime += frameTimeStep;
	}
	
	public AnimatedSprite getSprite() {
		return new AnimatedSprite(this);
	}
	
	protected Iterator<SpriteFrame> getIterator() {
		return fFrames.iterator();
	}
	
	public long getTotalAnimationLength() {
		return fTotalTime;
	}
}
