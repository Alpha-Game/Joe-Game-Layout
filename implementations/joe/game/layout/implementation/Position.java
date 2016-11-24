package joe.game.layout.implementation;

public enum Position {
	Top_Left, Top_Center, Top_Right,
	Middle_Left, Middle_Center, Middle_Right,
	Bottom_Left, Bottom_Center, Bottom_Right;
	
	public static Position create(boolean isTop, boolean isBottom, boolean isLeft, boolean isRight) {
		if (isTop && isBottom) {
			throw new IllegalArgumentException("Top and Bottom cannot both be true");
		} else if (isLeft && isRight) {
			throw new IllegalArgumentException("Left and Right cannot both be true");
		} else if (isTop) {
			if (isLeft) {
				return Top_Left;
			} else if (isRight) {
				return Top_Right;
			} else {
				return Top_Center;
			}
		} else if (isBottom) {
			if (isLeft) {
				return Bottom_Left;
			} else if (isRight) {
				return Bottom_Right;
			} else {
				return Bottom_Center;
			}
		} else if (isLeft) {
			return Middle_Left;
		}  else if (isRight) {
			return Middle_Right;
		} else {
			return Middle_Center;
		}
	}
	
	public boolean isLeft() {
		return Top_Left.equals(this) || Middle_Left.equals(this) || Bottom_Left.equals(this);
	}
	
	public boolean isMiddle() {
		return Middle_Left.equals(this) || Middle_Center.equals(this) || Middle_Right.equals(this);
	}
	
	public boolean isRight() {
		return Top_Right.equals(this) || Middle_Right.equals(this) || Bottom_Right.equals(this);
	}
	
	public boolean isTop() {
		return Top_Left.equals(this) || Top_Center.equals(this) || Top_Right.equals(this);
	}
	
	public boolean isCenter() {
		return Top_Center.equals(this) || Middle_Center.equals(this) || Bottom_Center.equals(this);
	}
	
	public boolean isBottom() {
		return Bottom_Left.equals(this) || Bottom_Center.equals(this) || Bottom_Right.equals(this);
	}
}
