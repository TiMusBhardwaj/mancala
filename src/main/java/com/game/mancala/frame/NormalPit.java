package com.game.mancala.frame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NormalPit extends Pit{

	private Pit next;
	private NormalPit oppositePit;
	private PitOwner owner;
	
	
	
	public NormalPit(PitOwner owner) {
		super(GameProperties.STONE_COUNT);
		this.owner = owner;
	}
	
	public int getCountAndEmpty() {
		int stoneCountCopy = getStoneCount();
		setStoneCount(0);
		return stoneCountCopy;
	}
}
