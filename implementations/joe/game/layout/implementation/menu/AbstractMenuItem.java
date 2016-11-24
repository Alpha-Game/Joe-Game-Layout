package joe.game.layout.implementation.menu;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;
import joe.game.layout.implementation.SpacableLayout;

public abstract class AbstractMenuItem extends SpacableLayout {
	private IDrawable fItem;
	private boolean fIsSelected;
	
	public AbstractMenuItem(IDrawable item, double spacerLeft, double spacerRight, double spacerTop, double spacerBottom) {
		super(item, spacerLeft, spacerRight, spacerTop, spacerBottom);
		fItem = item;
		fIsSelected = false;
	}

	public AbstractMenuItem() {
		this(null, 0, 0, 0, 0);
	}
	
	public void setIsSelected(boolean isSelected) {
		fIsSelected = isSelected;
	}
	
	protected abstract void setSelectedState(IDrawable item, boolean isSelect);
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		setSelectedState(fItem, fIsSelected);
		super.draw(g, rect);
	}
}
