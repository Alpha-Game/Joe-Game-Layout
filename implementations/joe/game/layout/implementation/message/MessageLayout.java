package joe.game.layout.implementation.message;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;

public class MessageLayout implements IDrawable {
	protected MessageDrawer fMessage;
	
	protected MessageLayout(MessageDrawer message) {
		fMessage = message;
	}
	
	@Override
	public Dimension2D getDimension() {
		return fMessage.getDimension();
	}

	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		fMessage.draw(g, rect);
	}
}
