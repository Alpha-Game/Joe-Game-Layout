package joe.game.layout.image.sprite;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Rectangle2D;

public interface IAnimatedSprite extends ISprite {
	void setSpeed(double speed);
	long getTotalAnimationLength();
	void draw(Graphics2D g, Rectangle2D rect, long time);
}
