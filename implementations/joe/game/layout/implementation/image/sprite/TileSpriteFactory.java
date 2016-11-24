package joe.game.layout.implementation.image.sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import joe.game.layout.image.sprite.ISprite;
import joe.game.layout.image.sprite.ISpriteFactory;

public class TileSpriteFactory implements ISpriteFactory {
	public static enum TileEdgeType {
		LeftRightTopBottom, 
		RightTopBottom, LeftTopBottom, LeftRightBottom, LeftRightTop,
		LeftRight, LeftTop, LeftBottom, RightTop, RightBottom, TopBottom,
		Left, Right, Top, Bottom,
		None
	}
	
	private Random fRandom;
	private Map<TileEdgeType, List<ISprite>> fEdges;
	
	public TileSpriteFactory() {
		this(null);
	}
	
	public TileSpriteFactory(Integer seed) {
		if (seed == null) {
			fRandom = new Random();
		} else {
			fRandom = new Random(seed);
		}
		fEdges = new HashMap<TileEdgeType, List<ISprite>>();
	}
	
	public void addEdge(TileEdgeType type, ISprite sprite) {
		List<ISprite> list = fEdges.get(type);
		if (list == null) {
			list = new ArrayList<ISprite>();
			fEdges.put(type, list);
		}
		list.add(sprite);
	}
	
	private ISprite getValueInList(List<ISprite> list, int count) {
		return list.get((int)Math.abs(count % list.size()));
	}
	
	private ISprite getValueFromType(TileEdgeType type, int count) {
		List<ISprite> list = fEdges.get(type);
		if (list != null) {
			return getValueInList(list, count);
		}
		return null;
	}
	
	public ISprite getEdge(TileEdgeType type, int count) {
		ISprite drawable = getValueFromType(type, count);
		if (drawable != null) {
			return drawable.clone();
		}
		
		// Try to find a suitable replacement
		if (TileEdgeType.RightTopBottom.equals(type)) {
			// Try opposite flip (LeftTopBottom)
			drawable = getValueFromType(TileEdgeType.LeftTopBottom, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}
		} else if (TileEdgeType.LeftTopBottom.equals(type)) {
			// Try opposite flip (RightTopBottom)
			drawable = getValueFromType(TileEdgeType.RightTopBottom, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}
		} else if (TileEdgeType.LeftRightBottom.equals(type)) {
			// Try opposite flip (LeftRightTop)
			drawable = getValueFromType(TileEdgeType.LeftRightTop, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}
		} else if (TileEdgeType.LeftRightTop.equals(type)) {
			// Try opposite flip (LeftRightBottom)
			drawable = getValueFromType(TileEdgeType.LeftRightBottom, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}
		} else if (TileEdgeType.LeftTop.equals(type)) {
			// Try opposite flip (LeftTop)
			drawable = getValueFromType(TileEdgeType.LeftBottom, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}
			// Try opposite flip (RightTop)
			drawable = getValueFromType(TileEdgeType.RightTop, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}
			// Try opposite flip (RightBottom)
			drawable = getValueFromType(TileEdgeType.RightBottom, count);
			if (drawable != null) {
				return drawable.clone().flipX().flipY();
			}	
		} else if (TileEdgeType.LeftBottom.equals(type)) {
			// Try opposite flip (LeftTop)
			drawable = getValueFromType(TileEdgeType.LeftTop, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}
			// Try opposite flip (RightTop)
			drawable = getValueFromType(TileEdgeType.RightTop, count);
			if (drawable != null) {
				return drawable.clone().flipX().flipY();
			}
			// Try opposite flip (RightBottom)
			drawable = getValueFromType(TileEdgeType.RightBottom, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}	
		} else if (TileEdgeType.RightTop.equals(type)) {
			// Try opposite flip (LeftTop)
			drawable = getValueFromType(TileEdgeType.LeftTop, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}
			// Try opposite flip (LeftBottom)
			drawable = getValueFromType(TileEdgeType.LeftBottom, count);
			if (drawable != null) {
				return drawable.clone().flipX().flipY();
			}
			// Try opposite flip (RightBottom)
			drawable = getValueFromType(TileEdgeType.RightBottom, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}			
		} else if (TileEdgeType.RightBottom.equals(type)) {
			// Try opposite flip (LeftTop)
			drawable = getValueFromType(TileEdgeType.LeftTop, count);
			if (drawable != null) {
				return drawable.clone().flipX().flipY();
			}
			// Try opposite flip (LeftBottom)
			drawable = getValueFromType(TileEdgeType.LeftBottom, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}
			// Try opposite flip (RightTop)
			drawable = getValueFromType(TileEdgeType.RightTop, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}	
		} else if (TileEdgeType.Left.equals(type)) {
			// Try opposite flip (Right)
			drawable = getValueFromType(TileEdgeType.Right, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}
		} else if (TileEdgeType.Right.equals(type)) {
			// Try opposite flip (Left)
			drawable = getValueFromType(TileEdgeType.Left, count);
			if (drawable != null) {
				return drawable.clone().flipX();
			}
		} else if (TileEdgeType.Top.equals(type)) {
			// Try opposite flip (Bottom)
			drawable = getValueFromType(TileEdgeType.Bottom, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}
		} else if (TileEdgeType.Bottom.equals(type)) {
			// Try opposite flip (Top)
			drawable = getValueFromType(TileEdgeType.Top, count);
			if (drawable != null) {
				return drawable.clone().flipY();
			}
		}
		
		// Last Attempt (None)
		drawable = getValueFromType(TileEdgeType.None, count);
		if (drawable != null) {
			return drawable.clone();
		}
		
		return null;
	}

	@Override
	public ISprite getSprite() {
		return new TileSprite(this, fRandom.nextInt());
	}
}
