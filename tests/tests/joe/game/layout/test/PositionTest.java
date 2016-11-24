package tests.joe.game.layout.test;

import static org.junit.Assert.assertEquals;
import joe.game.layout.implementation.Position;

import org.junit.Test;

public class PositionTest {
	@Test
	public void Top_Left() throws Exception {
		Position position = Position.Top_Left;
		
		// Test Left <-> Right Position
		assertEquals(true, position.isLeft());
		assertEquals(false, position.isCenter());
		assertEquals(false, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(true, position.isTop());
		assertEquals(false, position.isMiddle());
		assertEquals(false, position.isBottom());
	}
	
	@Test
	public void Top_Center() throws Exception {
		Position position = Position.Top_Center;

		// Test Left <-> Right Position
		assertEquals(false, position.isLeft());
		assertEquals(true, position.isCenter());
		assertEquals(false, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(true, position.isTop());
		assertEquals(false, position.isMiddle());
		assertEquals(false, position.isBottom());
	}
	
	@Test
	public void Top_Right() throws Exception {
		Position position = Position.Top_Right;

		// Test Left <-> Right Position
		assertEquals(false, position.isLeft());
		assertEquals(false, position.isCenter());
		assertEquals(true, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(true, position.isTop());
		assertEquals(false, position.isMiddle());
		assertEquals(false, position.isBottom());
	}
	
	@Test
	public void Middle_Left() throws Exception {
		Position position = Position.Middle_Left;

		// Test Left <-> Right Position
		assertEquals(true, position.isLeft());
		assertEquals(false, position.isCenter());
		assertEquals(false, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(false, position.isTop());
		assertEquals(true, position.isMiddle());
		assertEquals(false, position.isBottom());
	}
	
	@Test
	public void Middle_Center() throws Exception {
		Position position = Position.Middle_Center;

		// Test Left <-> Right Position
		assertEquals(false, position.isLeft());
		assertEquals(true, position.isCenter());
		assertEquals(false, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(false, position.isTop());
		assertEquals(true, position.isMiddle());
		assertEquals(false, position.isBottom());
	}
	
	@Test
	public void Middle_Right() throws Exception {
		Position position = Position.Middle_Right;

		// Test Left <-> Right Position
		assertEquals(false, position.isLeft());
		assertEquals(false, position.isCenter());
		assertEquals(true, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(false, position.isTop());
		assertEquals(true, position.isMiddle());
		assertEquals(false, position.isBottom());
	}
	
	@Test
	public void Bottom_Left() throws Exception {
		Position position = Position.Bottom_Left;

		// Test Left <-> Right Position
		assertEquals(true, position.isLeft());
		assertEquals(false, position.isCenter());
		assertEquals(false, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(false, position.isTop());
		assertEquals(false, position.isMiddle());
		assertEquals(true, position.isBottom());
	}
	
	@Test
	public void Bottom_Center() throws Exception {
		Position position = Position.Bottom_Center;

		// Test Left <-> Right Position
		assertEquals(false, position.isLeft());
		assertEquals(true, position.isCenter());
		assertEquals(false, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(false, position.isTop());
		assertEquals(false, position.isMiddle());
		assertEquals(true, position.isBottom());
	}
	
	@Test
	public void Bottom_Right() throws Exception {
		Position position = Position.Bottom_Right;

		// Test Left <-> Right Position
		assertEquals(false, position.isLeft());
		assertEquals(false, position.isCenter());
		assertEquals(true, position.isRight());
		
		// Test Top <-> Bottom Position
		assertEquals(false, position.isTop());
		assertEquals(false, position.isMiddle());
		assertEquals(true, position.isBottom());
	}
}
