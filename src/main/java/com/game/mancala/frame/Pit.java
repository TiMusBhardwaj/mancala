package com.game.mancala.frame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Pit {
	
	private int stoneCount;
	public Pit(int stoneCount) {
		this.stoneCount = stoneCount;
	}
	
	public void increment() {
		addStones(1);
	}
	public boolean isEmpty() {
		return stoneCount == 0;
	}
	
	abstract Pit getNext();
	abstract PitOwner getOwner();
	public int getStoneCount() {
		return stoneCount;
	}
	
	public void addStones(int stones) {
		stoneCount += stones;
	}
	public abstract int getCountAndEmpty();
	
	
}
