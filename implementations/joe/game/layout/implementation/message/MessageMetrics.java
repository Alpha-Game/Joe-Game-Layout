package joe.game.layout.implementation.message;

import java.awt.Font;
import java.awt.font.FontRenderContext;

import joe.classes.geometry2D.Dimension2D;
import joe.game.layout.implementation.LayoutCalculator;

public class MessageMetrics {
	private Font fFont;
	private FontRenderContext fContext;
	
	public MessageMetrics(Font font) {
		setFont(font);
	}
	
	public Dimension2D getDimension(String message) {
		return LayoutCalculator.createDimension(fFont.getStringBounds(message, fContext).getWidth(), fFont.getStringBounds(message, fContext).getHeight());
	}
	
	public void setFont(Font font) {
		fFont = font;
		fContext = new FontRenderContext(fFont.getTransform(), true, true);
	}
	
	public Font getFont() {
		return fFont;
	}
}
