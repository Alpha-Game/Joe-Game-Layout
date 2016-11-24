package joe.game.layout.implementation.image.sprite;

public class SpriteFrame {
	private Sprite fSprite;
	private long fFrameTimeStep;
	
	public SpriteFrame(Sprite sprite, long frameTimeStep) {
		fSprite = sprite;
		fFrameTimeStep = frameTimeStep;
	}

	public Sprite getSprite() {
		return fSprite;
	}
	
	public long getFrameTimeStep() {
		return fFrameTimeStep;
	}
}
