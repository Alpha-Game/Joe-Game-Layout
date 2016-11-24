package joe.game.layout;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;

public interface IDrawable {
	/**
	 * The dimensions the drawable required to fit the drawable perfectly
	 * 
	 * @return Returns dimensions. The dimensions should always be a positive value.
	 */
	Dimension2D getDimension();
	
	/**
	 * Draws the current drawable inside the given rectangle
	 * 
	 * @param g The location to draw the drawable
	 * @param rect The rectangle to draw the drawable in. <b>Note:</b> If the rectangle has negative width, reverses the X of the drawable. If the rectangle has negative height, reverses the Y of the drawable. 
	 */
	void draw(Graphics2D g, Rectangle2D rect);
}
