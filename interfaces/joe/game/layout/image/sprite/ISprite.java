package joe.game.layout.image.sprite;

import joe.game.layout.IDrawable;

public interface ISprite extends IDrawable {
	boolean isEmpty();
	ISprite clone();
	ISprite flipX(Boolean flip);
	ISprite flipY(Boolean flip);
	ISprite flipX();
	ISprite flipY();
}
