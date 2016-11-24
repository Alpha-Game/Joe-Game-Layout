package joe.game.layout.implementation.image.sprite;

import joe.game.layout.image.sprite.ISpriteFactory;
import joe.game.layout.implementation.image.ImageDrawer;

public class SpriteFactory implements ISpriteFactory {
	private ImageDrawer fImage;
	
	public SpriteFactory(ImageDrawer image) {
		fImage = image;
	}
	
	protected ImageDrawer getImage() {
		return fImage;
	}

	public Sprite getSprite() {
		return new Sprite(this);
	}
}
